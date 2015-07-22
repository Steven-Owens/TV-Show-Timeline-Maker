/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.testUtil;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.logging.Logger;
import org.jdom2.Element;


public class EventPair<T extends Event, S extends Event> {
    private static final Logger LOG = Logger.getLogger(EventPair.class.getName());
    private final T firstEvent;
    private final S secondEvent;

    public EventPair(T inFirstEvent, S inSecondEvent) {
        this.firstEvent = inFirstEvent;
        this.secondEvent = inSecondEvent;
    }

    /**
     * @return the firstEvent
     */
    public T getFirstEvent() {
        return this.firstEvent;
    }

    /**
     * @return the secondEvent
     */
    public S getSecondEvent() {
        return this.secondEvent;
    }
    
    public static class EventPairXMLWriter extends XMLWriterImp<EventPair> {
    
        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(EventPair.class, new EventPair.EventPairXMLWriter());
        }

        @Override
        public Element writeElements(EventPair ObjectToWrite) {
            Element root = new Element("EventPair");
            Element first = new Element("first");
            XMLWriterImp firstWriter = XMLWriterImp.getXMLWriter(ObjectToWrite.getFirstEvent().getClass());
            first.addContent(firstWriter.writeElements(ObjectToWrite.getFirstEvent()));
            Element second = new Element("second");
            XMLWriterImp secondWriter = XMLWriterImp.getXMLWriter(ObjectToWrite.getSecondEvent().getClass());
            second.addContent(secondWriter.writeElements(ObjectToWrite.getSecondEvent()));
            root.addContent(first);
            root.addContent(second);
            return root;
        }

        @Override
        public EventPair readElements(Element root) {
            Element first = root.getChild("first");
            Event firstEvent = null;
            for (Element curEventElement : first.getChildren()){
                XMLWriterImp EventXMLWriter = XMLWriterImp.getXMLWriter(MyLittePonyMaps.getEventClassForFriendlyString(curEventElement.getName()));
                firstEvent = (Event) EventXMLWriter.readElements(curEventElement);
            }
            Element second = root.getChild("second");
            Event secondEvent = null;
            for (Element curEventElement : second.getChildren()){
                XMLWriterImp EventXMLWriter = XMLWriterImp.getXMLWriter(MyLittePonyMaps.getEventClassForFriendlyString(curEventElement.getName()));
                secondEvent = (Event) EventXMLWriter.readElements(curEventElement);
            }
            return new EventPair(firstEvent,secondEvent);
        }
        
    }
}
