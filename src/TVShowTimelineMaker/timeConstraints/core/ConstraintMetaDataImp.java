/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.core;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 * @author Steven Owens
 */
public abstract class ConstraintMetaDataImp<T extends TimeConstraint> implements TimeConstraint.MetaData<T> {

    private final Class<? extends Event> mEventTypes[];
    private final String mVerbs[];

    public ConstraintMetaDataImp(Class<? extends Event> inEventType, String inVerb) {
        this(inEventType, new String[]{inVerb});
    }

    public ConstraintMetaDataImp(Class<? extends Event> inEventTypes[], String inVerb) {
        this(inEventTypes, new String[]{inVerb});
    }

    @SuppressWarnings("unchecked")
    public ConstraintMetaDataImp(Class<? extends Event> inEventType, String inVerbs[]) {
        this(new Class[]{inEventType}, inVerbs);
    }

    public ConstraintMetaDataImp(Class<? extends Event> inEventTypes[], String inVerbs[]) {
        this.mEventTypes = inEventTypes;
        this.mVerbs = inVerbs;
    }

    @Override
    public abstract T constuct(List<Event> inEvents, String verb, List<Component> inComponents) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    /**
     * @return the mEventTypes
     */
    @Override
    public Class<? extends Event>[] getEventTypes() {
        return this.mEventTypes;
    }

    /**
     * @return the mVerbs
     */
    @Override
    public String[] getVerbs() {
        return this.mVerbs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Component> getUIElements() {
        return java.util.Collections.EMPTY_LIST;
    }
}
