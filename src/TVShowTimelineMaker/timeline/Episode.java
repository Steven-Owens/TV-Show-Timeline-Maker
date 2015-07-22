/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeline.EventImp.EventIDXMLWriter;
import TVShowTimelineMaker.util.IDedObject;
import TVShowTimelineMaker.util.IDedObjectImp;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Element;

/**
 *
 * @author Steven Owens
 */
public class Episode extends IDedObjectImp
        implements Collection<Event>, Serializable, IDedObject {

    private static final long serialVersionUID = 11L;

    private static int count = 0;
    private static final Logger LOG = Logger.getLogger(Episode.class.getName());

    private final java.util.Set<Event> EventsCovered;
    private String name;

    public Episode() {
        this("new Episode");
    }

    public Episode(String inName) {
        super(Main.myShow.getNameSpace(),"Episode",count);
        count++;
        this.EventsCovered = new java.util.HashSet<>(1);
        this.name = inName;
    }
    
    private Episode(Element root) {
        super(root,"Episode");
        EventImp.EventIDXMLWriter appEventIDXMLWriter = EventImp.EventIDXMLWriter.instance;
        
        Element nameElement = root.getChild("name");
        this.name = nameElement.getTextNormalize();
        if (this.getLeastSignificantIDPart() >= count) {
            count = this.getLeastSignificantIDPart() + 1;
        }
        Element EventsElement = root.getChild("Events");
        this.EventsCovered = new java.util.HashSet<>(EventsElement.getChildren("EventID").parallelStream()
                    .map(appEventIDXMLWriter::readElements)
                    .collect(Collectors.toList()));
    }

    @Override
    public int size() {
        return this.EventsCovered.size();
    }

    @Override
    public boolean isEmpty() {
        return this.EventsCovered.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.EventsCovered.contains(o);
    }

    @Override
    public Iterator<Event> iterator() {
        return this.EventsCovered.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.EventsCovered.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.EventsCovered.toArray(a);
    }

    @Override
    public boolean add(Event e) {
        return this.EventsCovered.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.EventsCovered.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.EventsCovered.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Event> c) {
        return this.EventsCovered.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.EventsCovered.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.EventsCovered.retainAll(c);
    }

    @Override
    public void clear() {
        this.EventsCovered.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Episode) {
            Episode EpisodeO = (Episode) o;
            boolean con = this.name.equals(EpisodeO.name);
            con = con && (this.EventsCovered.containsAll(EpisodeO.EventsCovered));
            con = con && (EpisodeO.EventsCovered.containsAll(this.EventsCovered));
            return con;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.EventsCovered);
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.hashCode();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    public static class EpisodeXMLWriter extends IDedObjectXMLWriter<Episode> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(Episode.class, new EpisodeXMLWriter());
        }

        @Override
        public Element writeElements(Episode ObjectToWrite) {
            EventIDXMLWriter appEventIDXMLWriter = EventIDXMLWriter.instance;
            Element newElement = new Element("Episode");
            super.writeElements(ObjectToWrite, newElement);
            Element nameElement = new Element("name");
            nameElement.setText(ObjectToWrite.getName());
            newElement.addContent(nameElement);
            Element EventsElement = new Element("Events");
            EventsElement.addContent(ObjectToWrite.EventsCovered.parallelStream().map(appEventIDXMLWriter::writeElements).collect(Collectors.toList()));
            newElement.addContent(EventsElement);
            return newElement;
        }

        @Override
        public Episode readElements(Element root) {
            return new Episode(root);
        }
    }
}
