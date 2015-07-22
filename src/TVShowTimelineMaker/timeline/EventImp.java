/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.IDedObjectImp;
import TVShowTimelineMaker.util.XML.SubXMLWriter;
import TVShowTimelineMaker.util.XML.TopLevelXMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jdom2.Element;

/**
 *
 * @author Steven Owens
 */
public abstract class EventImp extends IDedObjectImp implements Event, Serializable {

    private static final long serialVersionUID = 53L;

    protected static int count = 0;
    protected static final Map<String, Event> mEventMap = new java.util.HashMap<>();

    public static void addEventToEventMap(Event inEvent) {
        mEventMap.put(inEvent.getID(), inEvent);
        //TODO: remove once all documents have been loaded and saved
        mEventMap.put(java.lang.Integer.toString(inEvent.getLeastSignificantIDPart()), inEvent);
    }

    public static void addEventsToEventMap(Collection<Event> inEvents) {
        mEventMap.putAll(inEvents.parallelStream().collect(HashMap<String, Event>::new, (HashMap<String, Event> t, Event curEvent) -> {
            t.put(curEvent.getID(), curEvent);
            //TODO: remove once all documents have been loaded and saved
            t.put(java.lang.Integer.toString(curEvent.getLeastSignificantIDPart()), curEvent);
        }, HashMap<String, Event>::putAll));
    }

    public static Event getEventByID(String ID) {
        return mEventMap.get(ID);
    }

    private static int setIDForEvent(boolean tempEvent) {
        int rid = -1;
        if (!tempEvent) {
            rid = count;
            count++;
        }
        return rid;
    }
    private String name;
    protected long lastmodifyed;

    private boolean markedForComplexEval;

    public EventImp(String inName) {
        this(inName, false);
    }

    protected EventImp(String inName, boolean tempEvent) {
        this(inName, Main.myShow.getNameSpace(), setIDForEvent(tempEvent));
        this.reset();
    }

    protected EventImp(String inName, String inRootNamespace, int inMyID) {
        super(inRootNamespace,"event",inMyID);
        this.name = inName;
        this.lastmodifyed = System.currentTimeMillis();
    }

    protected EventImp(Element root) {
        super(root,"event");
        this.name = root.getChild("name").getTextNormalize();
        if (this.getLeastSignificantIDPart() >= count) {
            count = getLeastSignificantIDPart() + 1;
        }
    }

    @Override
    public void reset() {
        this.lastmodifyed = System.currentTimeMillis();
        this.markedForComplexEval = false;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
        this.lastmodifyed = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EventImp) {
            return this.getLeastSignificantIDPart() == ((EventImp) o).getLeastSignificantIDPart();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.getLeastSignificantIDPart();
        return hash;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @return the lastmodifyed
     */
    @Override
    public long getLastmodifyed() {
        return this.lastmodifyed;
    }

    @Override
    public int compareTo(Event o) {
        if (this.getID().equals(o.getID())) {
            return 0;
        }
        int cValue = 0;
        if ((this instanceof OnceEvent) && (o instanceof OnceEvent)) {
            cValue = ((OnceEvent) this).getEarliestPossibleStartTime().compareTo(((OnceEvent) o).getEarliestPossibleStartTime());
            if (cValue != 0) {
                return cValue;
            }
            cValue = ((OnceEvent) this).getLatestPossibleEndTime().compareTo(((OnceEvent) o).getLatestPossibleEndTime());
            if (cValue != 0) {
                return cValue;
            }
            cValue = this.name.compareTo(o.getName());
            if (cValue != 0) {
                return cValue;
            }
        }
        return this.getID().compareTo(o.getID());
    }

    @Override
    public void normalize() {
    }

    @Override
    public String toLongString() {
        return this.getLeastSignificantIDPart() + ":" + this.getName();
    }

    /**
     * @return the markedForComplexEval
     */
    @Override
    public boolean isMarkedForComplexEval() {
        return this.markedForComplexEval;
    }

    @Override
    public void setUpForComplexEval() {
        this.markedForComplexEval = true;
    }

    @Override
    public abstract boolean isValid();

    @Override
    public abstract void setTo(Collection<? extends Placement<?>> inPlacements);

    public static class EventIDXMLWriter extends XMLWriterImp<Event> implements SubXMLWriter<Event> {

        @Override
        public Element writeElements(Event ObjectToWrite) {
            Element newRoot = new Element("EventID");
            writeElements(ObjectToWrite, newRoot);
            return newRoot;
        }
        
        @Override
        public void writeElements(Event ObjectToWrite, Element newRoot){
            newRoot.setText(ObjectToWrite.getID());
        }

        @Override
        public Event readElements(Element root) {
            return EventImp.getEventByID(root.getTextNormalize());
        }
        
        public final static EventIDXMLWriter instance = new EventIDXMLWriter();

    }

    public static abstract class EventXMLWriter<T extends Event> extends IDedObjectXMLWriter<T>  {

        @Override
        public void writeElements(T ObjectToWrite, Element newRoot) {
            Element nameElement = new Element("name");
            nameElement.setText(ObjectToWrite.getName());
            newRoot.addContent(nameElement);
            super.writeElements(ObjectToWrite, newRoot);
        }

    }
}
