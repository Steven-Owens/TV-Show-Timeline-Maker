/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util;

import TVShowTimelineMaker.util.XML.SubXMLWriter;
import TVShowTimelineMaker.util.XML.TopLevelXMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import org.jdom2.Element;

/**
 *
 * @author Steven Owens
 */
public class IDedObjectImp implements IDedObject {
    private final int leastSignificantIDPart;
    private final String rootNamespace;
    private final String fullID;
    
    protected static String loadRootNameSpace(Element root) {
        Element rootNameSpaceElement = root.getChild("rootNameSpace");
        final String rootNamespace;
        if (rootNameSpaceElement != null) {
            rootNamespace = rootNameSpaceElement.getTextNormalize();
        } else {
            rootNamespace = TopLevelXMLWriter.defualtNameSpace;
        }
        return rootNamespace;
    }
    
    public IDedObjectImp(Element root,String inSubTag){
        this(loadRootNameSpace(root),inSubTag,java.lang.Integer.parseInt(root.getChild("ID").getTextNormalize()));
    }
    
    public IDedObjectImp(String inRootNamespace, String inSubTag){
        this(inRootNamespace,inSubTag,-1);
    }
    
    public IDedObjectImp(String inRootNamespace, String inSubTag, int inLeastSignificantIDPart){
        leastSignificantIDPart = inLeastSignificantIDPart;
        rootNamespace = inRootNamespace;
        fullID = rootNamespace + "." + inSubTag + "." + Integer.toString(leastSignificantIDPart);
    }

    @Override
    public String getID() {
        return fullID;
    }

    /**
     * @return the leastSignificantIDPart
     */
    @Override
    public int getLeastSignificantIDPart() {
        return leastSignificantIDPart;
    }

    @Override
    public String getRootNameSpace() {
        return rootNamespace;
    }
    
    public static abstract class IDedObjectXMLWriter<T extends IDedObject> extends XMLWriterImp<T> implements SubXMLWriter<T>{
        
        @Override
        public void writeElements(T ObjectToWrite, Element newRoot) {
            Element IDElement = new Element("ID");
            IDElement.setText(java.lang.Integer.toString(ObjectToWrite.getLeastSignificantIDPart()));
            newRoot.addContent(IDElement);
            Element rootNameSpaceElement = new Element("rootNameSpace");
            rootNameSpaceElement.setText(ObjectToWrite.getRootNameSpace());
            newRoot.addContent(rootNameSpaceElement);
        }
    }
}
