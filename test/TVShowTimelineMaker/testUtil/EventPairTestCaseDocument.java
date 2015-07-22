/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.testUtil;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import com.civprod.io.DirectoryWalkers.LoadingDirectoryWalker;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.xml.sax.InputSource;


public class EventPairTestCaseDocument {


    private static final Logger LOG = Logger.getLogger(EventPairTestCaseDocument.class.getName());

    public static void init() {
        java.util.Collection<Class<?>> classes = new java.util.ArrayList<>();
        LoadingDirectoryWalker mWalker = new LoadingDirectoryWalker("TVShowTimelineMaker");
        try {
            mWalker.run(new File("build\\classes"), classes);
            com.civprod.dynamicClassLoading.Initializer.initialize(classes);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeXMLFile(String Path, Collection<EventPair> inEvents) throws IOException {
        writeXMLFile(new File(Path), inEvents);
    }

    public static void writeXMLFile(File inFile, Collection<EventPair> inEvents) throws IOException {
        writeXMLFile(new java.io.BufferedOutputStream(new java.io.FileOutputStream(inFile)), inEvents);
    }
    public static void writeXMLFile(java.io.OutputStream inFile, Collection<EventPair> inEvents) throws IOException {
        writeXMLFile(new java.io.BufferedOutputStream(inFile), inEvents);
    }

    public static void writeXMLFile(java.io.BufferedOutputStream inFile, Collection<EventPair> inEvents) throws IOException {
        org.jdom2.Document newDocument = createDocument(inEvents);
        org.jdom2.output.XMLOutputter myXMLOutputter = new org.jdom2.output.XMLOutputter(org.jdom2.output.Format.getPrettyFormat());
        myXMLOutputter.output(newDocument, inFile);
    }

    public static void writeXMLFile(java.io.Writer inFile, Collection<EventPair> inEvents) throws IOException {
        writeXMLFile(new java.io.BufferedWriter(inFile), inEvents);
    }

    public static void writeXMLFile(java.io.BufferedWriter inFile, Collection<EventPair> inEvents) throws IOException {
        org.jdom2.Document newDocument = createDocument(inEvents);
        org.jdom2.output.XMLOutputter myXMLOutputter = new org.jdom2.output.XMLOutputter(org.jdom2.output.Format.getPrettyFormat());
        myXMLOutputter.output(newDocument, inFile);
    }

    @SuppressWarnings("unchecked")
    private static org.jdom2.Document createDocument(Collection<EventPair> inEvents) {
        Element rootElement = new Element("testcases");
        XMLWriterImp<EventPair> EventPairXMLWriter = XMLWriterImp.getXMLWriter(EventPair.class);
        for (EventPair curEventPair : inEvents) {
            rootElement.addContent(EventPairXMLWriter.writeElements(curEventPair));
        }
        org.jdom2.Document newDocument = new org.jdom2.Document(rootElement);
        return newDocument;
    }

    public static Collection<EventPair> readXMLFile(String Path) throws JDOMException, IOException {
        return readXMLFile(new File(Path));
    }

    public static Collection<EventPair> readXMLFile(File inFile) throws JDOMException, IOException {
        org.jdom2.input.SAXBuilder newSAXBuilder = new org.jdom2.input.SAXBuilder();
        Document ShowDoc = newSAXBuilder.build(inFile);
        return createShowFromDocument(ShowDoc);
    }

    public static Collection<EventPair> readXMLFile(java.io.InputStream inFile) throws JDOMException, IOException {
        return readXMLFile(new java.io.BufferedInputStream(inFile));
    }

    public static Collection<EventPair> readXMLFile(java.io.BufferedInputStream inFile) throws JDOMException, IOException {
        org.jdom2.input.SAXBuilder newSAXBuilder = new org.jdom2.input.SAXBuilder();
        Document ShowDoc = newSAXBuilder.build(inFile);
        return createShowFromDocument(ShowDoc);
    }

    public static Collection<EventPair> readXMLFile(java.io.Reader inFile) throws JDOMException, IOException {
        return readXMLFile(new java.io.BufferedReader(inFile));
    }

    public static Collection<EventPair> readXMLFile(java.io.BufferedReader inFile) throws JDOMException, IOException {
        org.jdom2.input.SAXBuilder newSAXBuilder = new org.jdom2.input.SAXBuilder();
        Document ShowDoc = newSAXBuilder.build(inFile);
        return createShowFromDocument(ShowDoc);
    }

    public static Collection<EventPair> readXMLFile(InputSource inFile) throws JDOMException, IOException {
        org.jdom2.input.SAXBuilder newSAXBuilder = new org.jdom2.input.SAXBuilder();
        Document ShowDoc = newSAXBuilder.build(inFile);
        return createShowFromDocument(ShowDoc);
    }

    // </editor-fold>
    private static Collection<EventPair> createShowFromDocument(Document inShowDocument) {
        Collection<EventPair> newCollection = new ArrayList<>();
        Element rootElement = inShowDocument.getRootElement();
        XMLWriterImp<EventPair> EventPairXMLWriter = XMLWriterImp.getXMLWriter(EventPair.class);
        for (Element curEventElement : rootElement.getChildren()) {
            newCollection.add(EventPairXMLWriter.readElements(curEventElement));
        }
        return newCollection;
    }
    private File mFile;

    public EventPairTestCaseDocument(String Path) {
        this(new File(Path));
    }

    public EventPairTestCaseDocument(File inFile) {
        this.mFile = inFile;
    }
    public void writeXMLFile(Collection<EventPair> inEvents) throws IOException {
        writeXMLFile(this.mFile, inEvents);
    }

    // <editor-fold defaultstate="collapsed" desc="readXMLFile methods">
    public Collection<EventPair> readXMLFile() throws JDOMException, IOException {
        return readXMLFile(this.mFile);
    }
}
