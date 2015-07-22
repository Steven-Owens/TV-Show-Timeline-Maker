/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;


public class SubSet extends EventContainer {
    private static final Logger LOG = Logger.getLogger(SubSet.class.getName());
    
    private final Timeline mParent;
    
    
    public SubSet(Timeline inParent, Set<TimeConstraint> inConstraints, Set<Event> inEvents) {
        super(inConstraints,inEvents);
        this.mParent = inParent;
    }
    
     public SubSet(Timeline inParent) {
        super();
        this.mParent = inParent;
     }
     
     @Override
     public boolean equals(Object o){
         if (o instanceof SubSet){
             SubSet SubSeto = (SubSet)o;
             return ((SubSeto.Events.size() == this.Events.size()) &&
                     (SubSeto.Constraints.size() == this.Constraints.size()) && 
                     SubSeto.Events.containsAll(this.Events) && 
                     this.Events.containsAll(SubSeto.Events) && 
                     SubSeto.Constraints.containsAll(this.Constraints) && 
                     this.Constraints.containsAll(SubSeto.Constraints));
         } else {
             return false;
         }
     }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 2*hash + this.Events.hashCode();
        hash = 3*hash + this.Constraints.hashCode();
        return hash;
    }
    
    @Override
    public SubSet RandomSubSet(int size){
        java.util.Random rnd = Main.rnd;
        java.util.List<Event> tempEvents = new java.util.ArrayList<>(this.Events);
        java.util.List<Event> newSet = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++){
            Event tranEvent = tempEvents.get(rnd.nextInt(tempEvents.size()));
            tempEvents.remove(tranEvent);
            newSet.add(tranEvent);
        }
        SubSet newSubSet = this.mParent.ConstructNewSubSet(newSet);
        if (!this.mParent.isConnected(newSubSet)){
            newSubSet = this.RandomSubSet(size);
        }
        return newSubSet;
    }

    @Override
    public SubSet ConstructNewSubSet(Event... seen) {
        return this.mParent.ConstructNewSubSet(seen);
    }

    @Override
    public SubSet ConstructNewSubSet(Collection<Event> seen) {
        return this.mParent.ConstructNewSubSet(seen);
    }

    @Override
    public boolean isConnected(SubSet newSubSet) {
       return this.mParent.isConnected(newSubSet);
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public Collection<TimeConstraint> getConstraintsOnEvent(Event inEvent) {
        Collection<TimeConstraint> constraintsOnEvent = new java.util.ArrayList<>(this.mParent.getConstraintsOnEvent(inEvent));
        constraintsOnEvent.retainAll(this.Constraints);
        return constraintsOnEvent;
    }
}
