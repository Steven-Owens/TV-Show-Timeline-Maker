/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.core;

import TVShowTimelineMaker.timeConstraints.interfaces.OneEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.ConstraintMetaDataImp;
import TVShowTimelineMaker.timeline.Event;
import java.awt.Component;
import java.lang.reflect.Field;
import java.util.List;

/**
 *
 * @author Steven Owens
 */
public abstract class OneEventConstraintMetaData<T extends OneEventTimeConstraint> extends ConstraintMetaDataImp<T>{
    @SuppressWarnings("unchecked")
    private static Class<? extends Event> deriveEventTypeFromClass(Class<? extends OneEventTimeConstraint> inTimeConstraintClass){
        Class<? extends Event> eventType = null;
        for (Field curField : inTimeConstraintClass.getFields()){
            Class curClass = curField.getType();
            if (Event.class.isAssignableFrom(curClass)){
                eventType = curClass;
            }
        }
        return eventType;
    }
    
    public OneEventConstraintMetaData(String inVerb, Class<T> inTimeConstraintClass){
        this(deriveEventTypeFromClass(inTimeConstraintClass), inVerb);
    }
    
    public OneEventConstraintMetaData(String inVerbs[], Class<T> inTimeConstraintClass){
        this(deriveEventTypeFromClass(inTimeConstraintClass), inVerbs);
    }
    
    public OneEventConstraintMetaData(Class<? extends Event> inEventType, String inVerb){
        this(inEventType,new String[]{inVerb});
    }
    
    public OneEventConstraintMetaData(Class<? extends Event> inEventType, String inVerbs[]){
        super(inEventType, inVerbs);
    }
}
