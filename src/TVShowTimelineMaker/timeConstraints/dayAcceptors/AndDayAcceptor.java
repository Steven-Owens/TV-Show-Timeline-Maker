/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 */
public class AndDayAcceptor implements DayAcceptor, Collection<DayAcceptor>{
    private static final Logger LOG = Logger.getLogger(AndDayAcceptor.class.getName());
    
    private final Collection<DayAcceptor> mDayAcceptors;
    
    public AndDayAcceptor(){
        this.mDayAcceptors = new java.util.HashSet<>(2);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends DayAcceptor> T getDayAcceptorOfKind(Class<T> inClass){
        T rDayAcceptor = null;
        for (DayAcceptor curDayAcceptor : this.mDayAcceptors){
            if (inClass.isAssignableFrom(curDayAcceptor.getClass())){
                rDayAcceptor = (T) curDayAcceptor;
                break;
            }
        }
        return rDayAcceptor;
    }

    @Override
    public boolean accept(DateTime inDateTime) {
        boolean rAccept = true;
        for (DayAcceptor curDayAcceptor : this.mDayAcceptors){
            if (!curDayAcceptor.accept(inDateTime)){
                rAccept = false;
                break;
            }
        }
        return rAccept;
    }
    
    @Override
    public boolean accept(DayOfYear inDateTime) {
        boolean rAccept = true;
        for (DayAcceptor curDayAcceptor : this.mDayAcceptors){
            if (!curDayAcceptor.accept(inDateTime)){
                rAccept = false;
                break;
            }
        }
        return rAccept;
    }
    
    @Override
    public double divFactor() {
        double rDiv = 1.0;
        //make mutithreaded
        for (DayAcceptor curDayAcceptor : this.mDayAcceptors){
            rDiv *= curDayAcceptor.divFactor();
        }
        return rDiv;
    }

    @Override
    public int size() {
        return this.mDayAcceptors.size();
    }

    @Override
    public boolean isEmpty() {
        return this.mDayAcceptors.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.mDayAcceptors.contains(o);
    }

    @Override
    public Iterator<DayAcceptor> iterator() {
        return this.mDayAcceptors.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.mDayAcceptors.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.mDayAcceptors.toArray(a);
    }

    @Override
    public boolean add(DayAcceptor e) {
        return this.mDayAcceptors.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.mDayAcceptors.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.mDayAcceptors.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends DayAcceptor> c) {
        return this.mDayAcceptors.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.mDayAcceptors.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.mDayAcceptors.retainAll(c);
    }

    @Override
    public void clear() {
        this.mDayAcceptors.clear();
    }
    
    public static class AndDayAcceptorXMLWriter extends XMLWriterImp<AndDayAcceptor>{
        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(AndDayAcceptor.class, new AndDayAcceptorXMLWriter());
        }

        @Override
        public Element writeElements(AndDayAcceptor ObjectToWrite) {
            return new Element("AndDayAcceptor");
        }

        @Override
        public AndDayAcceptor readElements(Element root) {
            return new AndDayAcceptor();
        }
    }
}
