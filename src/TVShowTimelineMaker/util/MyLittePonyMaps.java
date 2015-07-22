/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.commons.collections4.BidiMap;


public final class MyLittePonyMaps {
    
    private static final BidiMap<String, Class<? extends TimeConstraint>> mConstraintClassMap = new org.apache.commons.collections4.bidimap.DualHashBidiMap<>();
    private static final BidiMap<String, Class<? extends EventImp>> mEventClassMap = new org.apache.commons.collections4.bidimap.DualHashBidiMap<>();
    
    private static final Collection<TimeConstraint.MetaData> allConstraintMetaData = new java.util.HashSet<>();
    private static final Map<String, TimeConstraint.MetaData> verbMap = new java.util.HashMap<>();
    private static final Logger LOG = Logger.getLogger(MyLittePonyMaps.class.getName());
    
    public static void putConstraint(String inFriendlyString, Class<? extends TimeConstraint> inConstraintClass){
        mConstraintClassMap.put(inFriendlyString, inConstraintClass);
    }
    
    public static Class<? extends TimeConstraint> getConstraintClassForFriendlyString(String inFriendlyString){
        return mConstraintClassMap.get(inFriendlyString);
    }
    
    public static String getFriendlyStringForConstraintClass(Class<? extends TimeConstraint> inConstraintClass){
        return mConstraintClassMap.getKey(inConstraintClass);
    }
    
    public static Collection<String> getFriendlyStringsForConstraintClasses(){
        return mConstraintClassMap.keySet();
    }
    
    public static Collection<Class<? extends TimeConstraint>> getConstraintClasses(){
        return mConstraintClassMap.values();
    }
    
    
    public static void putEvent(String inFriendlyString, Class<? extends EventImp> inEventClass){
        mEventClassMap.put(inFriendlyString, inEventClass);
    }
    
    public static Class<? extends EventImp> getEventClassForFriendlyString(String inFriendlyString){
        return mEventClassMap.get(inFriendlyString);
    }
    
    public static String getFriendlyStringForEventClass(Class<? extends EventImp> inEventClass){
        return mEventClassMap.getKey(inEventClass);
    }
    
    public static Collection<String> getFriendlyStringsForEventClasses(){
        return mEventClassMap.keySet();
    }
    
    public static Collection<Class<? extends EventImp>> getEventClasses(){
        return mEventClassMap.values();
    }
    
    public static <T extends TimeConstraint> void addTimeConstraintMetaData(Class<T> inClass, TimeConstraint.MetaData<T> inConstraintMetaData){
        allConstraintMetaData.add(inConstraintMetaData);
        for (String curVerb : inConstraintMetaData.getVerbs()){
            verbMap.put(curVerb, inConstraintMetaData);
        }
    }
    
    public static Collection<TimeConstraint.MetaData> getTimeConstraintMetaData(){
        return java.util.Collections.unmodifiableCollection(allConstraintMetaData);
    }
    
    public static Collection<TimeConstraint.MetaData> filterTimeConstraintMetaData(Event inEvents[]){
        Collection<TimeConstraint.MetaData> filteredMetaData = new java.util.HashSet<>();
        int inEventsLength = inEvents.length;
        //todo:Make this mutithreaded 
        for (TimeConstraint.MetaData curConstraintMetaData : allConstraintMetaData){
            if (curConstraintMetaData.getEventTypes().length == inEventsLength){
                boolean accept = true;
                for (int i = 0; (i < inEventsLength) && accept; i++){
                    accept &= curConstraintMetaData.getEventTypes()[i].isInstance(inEvents[i]);
                }
                if (accept){
                    filteredMetaData.add(curConstraintMetaData);
                }
            }
        }
        return filteredMetaData;
    }
    
    public static TimeConstraint.MetaData getTimeConstraintMetaDataForVerb(String inVerb){
        return verbMap.get(inVerb);
    }
}
