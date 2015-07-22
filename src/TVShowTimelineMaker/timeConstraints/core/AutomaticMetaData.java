/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.core;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.ConstraintMetaDataImp;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.util.JodaTimeUtil;
import TVShowTimelineMaker.util.Primary;
import com.civprod.swing.CollectionListModel;
import java.awt.Component;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTimeFieldType;

/**
 *
 * @author Steven Owens
 */
public class AutomaticMetaData<T extends TimeConstraint,S extends Enum<S>> extends ConstraintMetaDataImp<T> {
    public static <T extends TimeConstraint,S extends Enum<S>> AutomaticMetaData<T,S> constuctFromClass(Class<T> inClass) {
        return constuctFromClass(inClass, null);
    }
    
    public static <T extends TimeConstraint,S extends Enum<S>> AutomaticMetaData<T,S> constuctFromClassSingleVerb(Class<T> inClass, String verb) {
        Map<String,S> newVerbMapping = new HashMap<>(1);
        newVerbMapping.put(verb, null);
        return constuctFromClass(inClass, newVerbMapping);
    }
    
    
    @SuppressWarnings({"unchecked", "unchecked"})
    public static <T extends TimeConstraint,S extends Enum<S>> AutomaticMetaData<T,S> constuctFromClass(Class<T> inClass, Map<String,S> inVerbMapping) {
        Constructor<?>[] constructors = inClass.getDeclaredConstructors();
        Constructor<T> mainConstructor = null;
        if (constructors.length == 1){
           mainConstructor = (Constructor<T>) constructors[0]; 
        } else {
            for (Constructor<?> curConstructor : constructors){
                if (curConstructor.isAnnotationPresent(Primary.class)){
                    if (mainConstructor != null){
                        throw new MetaDataConstuctError("More than one constructor delcared Primary");
                    }
                    mainConstructor = (Constructor<T>) curConstructor;
                }
            }
            if (mainConstructor == null) {
                throw new MetaDataConstuctError("unable to rezolve Primary constructor. Mark one using the Primary Annotation");
            }
        }
        List<Class<? extends Event>> newEventTypes = new ArrayList<>(1);
        Class<? extends Enum<S>> kindClass = null;
        for (Class<?> curParameterType : mainConstructor.getParameterTypes()){
            if (Event.class.isAssignableFrom(curParameterType)){
                newEventTypes.add((Class<? extends Event>) curParameterType);
            }
            if (curParameterType.isEnum()){
                kindClass = (Class<? extends Enum<S>>) curParameterType;
            }
        }
        final String newVerbs[];
        Map<String,S> newVerbMapping = null;
        if (inVerbMapping == null){
            if (kindClass != null){
               Enum<S>[] enumConstants = kindClass.getEnumConstants();
               newVerbs = new String[enumConstants.length];
                newVerbMapping = new HashMap<>(enumConstants.length); 
               for (int i = 0; i < enumConstants.length; i++){
                   newVerbs[i] = enumConstants[i].toString();
                   newVerbMapping.put(enumConstants[i].toString(), (S) enumConstants[i]);
               }
            } else {
                StringBuilder verbGuess = new StringBuilder(inClass.getSimpleName());
                //String verbGuess = inClass.getSimpleName();
                int startVerbGuessIndex = verbGuess.lastIndexOf(".");
                if (startVerbGuessIndex < 0){
                   startVerbGuessIndex = 0; 
                }
                verbGuess.substring(startVerbGuessIndex);
                if (verbGuess.lastIndexOf("TimeConstraint") >= (verbGuess.length() - "TimeConstraint".length())){
                    verbGuess.substring(0, verbGuess.lastIndexOf("TimeConstraint"));
                } else if (verbGuess.lastIndexOf("Constraint") >= (verbGuess.length() - "Constraint".length())){
                    verbGuess.substring(0, verbGuess.lastIndexOf("Constraint"));
                }
                StringBuilder newGuess = new StringBuilder(verbGuess.length());
                newGuess.append(Character.toLowerCase(verbGuess.charAt(0)));
                for (int i = 1; i < verbGuess.length(); i++){
                    char curChar = verbGuess.charAt(i);
                    if (Character.isUpperCase(curChar)){
                        newGuess.append(' ');
                        newGuess.append(Character.toLowerCase(curChar));
                    } else {
                        newGuess.append(curChar);
                    }
                }
                verbGuess = newGuess;
                newVerbs = new String[]{verbGuess.toString()};
            }
        } else {
            newVerbMapping = inVerbMapping;
            Set<String> keySet = inVerbMapping.keySet();
            newVerbs = keySet.toArray(new String[keySet.size()]); 
        }
        Class<? extends Event>[] eventTypesArr = new Class[newEventTypes.size()];
        for (int i = 0; i < newEventTypes.size(); i++){
            eventTypesArr[i] = newEventTypes.get(i);
        }
        return new AutomaticMetaData<>(eventTypesArr,newVerbs,mainConstructor, newVerbMapping);
    }
    
    private final Constructor<T> mConstructor;
    private final Map<String,S> mVerbMapping;
    
    private AutomaticMetaData(Class<? extends Event> inEventTypes[], String inVerbs[], Constructor<T> inConstructor, Map<String,S> inVerbMapping){
        super(inEventTypes,inVerbs);
        mConstructor = inConstructor;
        mVerbMapping = inVerbMapping;
    }
    
    @Override
    public List<Component> getUIElements() {
        Class<?>[] parameterTypes = mConstructor.getParameterTypes();
        List<Component> newComponents = new ArrayList<>();
        for (Class<?> curParameterType : parameterTypes){
            if (Event.class.isAssignableFrom(curParameterType)){
                
            } else {
                if (DateTimeFieldType.class.isAssignableFrom(curParameterType)){
                    CollectionListModel<DateTimeFieldType> newCollectionListModel = new CollectionListModel<>(JodaTimeUtil.getInstance().getDateTimeFieldTypeAsList());
                    javax.swing.JList<org.joda.time.DateTimeFieldType> newSwingList = new javax.swing.JList<>(newCollectionListModel);
                    newComponents.add(newSwingList);
                }
                if (curParameterType.isPrimitive()){
                    if (curParameterType.getName().equals("int")){
                        javax.swing.SpinnerNumberModel model = new javax.swing.SpinnerNumberModel();
                        javax.swing.JSpinner newSpinner = new javax.swing.JSpinner();
                        newSpinner.setModel(model);
                        newComponents.add(newSpinner);
                    }
                }
            }
        }
        return newComponents;
    }

    @Override
    public T constuct(List<Event> inEvents, String verb, List<Component> inComponents) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?>[] parameterTypes = mConstructor.getParameterTypes();
        Object[] initargs = new Object[parameterTypes.length];
        int eventIndex = 0;
        int extraIndex = 0;
        for (int i = 0; i < parameterTypes.length; i++){
            Class<?> curParameterType = parameterTypes[i];
            if (Event.class.isAssignableFrom(curParameterType)){
                initargs[i] = inEvents.get(eventIndex);
                eventIndex++;
            } else if (curParameterType.isEnum()){
                initargs[i] = mVerbMapping.get(verb);
            } else {
                Component curComponent = inComponents.get(extraIndex);
                extraIndex++;
                if (DateTimeFieldType.class.isAssignableFrom(curParameterType)){
                    @SuppressWarnings("unchecked")
                    javax.swing.JList<org.joda.time.DateTimeFieldType> dateSwingList = (javax.swing.JList<org.joda.time.DateTimeFieldType>) curComponent;
                    initargs[i] = dateSwingList.getSelectedValue();
                }
                if (curParameterType.isPrimitive()){
                    if (curParameterType.getName().equals("int")){
                        javax.swing.JSpinner intSpinner = (javax.swing.JSpinner) curComponent;
                        initargs[i] = intSpinner.getValue();
                    }
                }
            }
        }
        return mConstructor.newInstance(initargs);
    }
    
    public static class MetaDataConstuctError extends java.lang.Error{
        private static final long serialVersionUID = 34754L;
        public MetaDataConstuctError(String message){
            super(message);
        }
    }
}
