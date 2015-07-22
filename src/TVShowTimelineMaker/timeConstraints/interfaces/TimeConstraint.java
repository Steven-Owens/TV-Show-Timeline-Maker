/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.interfaces;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.IDedObject;
import java.awt.Component;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author Steven Owens
 */
public interface TimeConstraint
        extends Serializable, IDedObject {

    Event[] getConstrainedEvents();

    boolean applyConstraint();

    boolean complexApplyConstraint();

    boolean ConstraintSatisfied();

    boolean consistentWithConstraint(Placement<?> inValues[]);

    boolean isStrict();

    boolean inBeta();
    
    boolean isSynthetic();

    public static interface MetaData<T extends TimeConstraint> {

        List<Component> getUIElements();

        T constuct(List<Event> inEvents, String verb, List<Component> inComponents) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

        /**
         * @return the mEventTypes
         */
        Class<? extends Event>[] getEventTypes();

        /**
         * @return the mVerbs
         */
        String[] getVerbs();
        
    }
}
