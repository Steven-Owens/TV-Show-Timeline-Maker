/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util.XML;

import java.util.Map;
import org.jdom2.Element;

/**
 *
 * @author Steven Owens
 * @param <T>
 */
public abstract class XMLWriterImp<T> implements XMLWriter<T>{

    private static final Map<Class<? extends Object>, XMLWriter> XMLWriterMap = new java.util.HashMap<>(10);

    public static <S> void addXMLWriter(Class<S> klass, XMLWriter<S> thing) {
        XMLWriterMap.put(klass, thing);
    }
    
    @SuppressWarnings("unchecked")
    public static <S> XMLWriter<S> getXMLWriter(Class<S> klass){
        return XMLWriterMap.get(klass);
    }
    
    public static XMLWriter getXMLWriterUnsafe(Class klass){
        return XMLWriterMap.get(klass);
    }

    @Override
    public abstract Element writeElements(T ObjectToWrite);

    @Override
    public abstract T readElements(Element root);
}
