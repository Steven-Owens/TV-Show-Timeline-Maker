/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.ui.ConstraintEditors;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.ui.EditorWindow;
import TVShowTimelineMaker.ui.util.EditorComponent;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import java.util.Map;

/**
 *
 * @author Steven Owens
 */
@SuppressWarnings("serial")
public abstract class ConstraintEditorWindow<T extends TimeConstraint> extends EditorWindow implements EditorComponent<T> {
    
    private static final Map<Class<? extends TimeConstraint>, Class<? extends ConstraintEditorWindow>> ConstraintEditors = new java.util.HashMap<>();
    
    public static void addConstraintEditor(Class<? extends TimeConstraint> inConstraintClass, Class<? extends ConstraintEditorWindow> inEditorWindow){
        ConstraintEditors.put(inConstraintClass, inEditorWindow);
    }
    
    public static Class<? extends ConstraintEditorWindow> getConstraintEditor(Class<? extends TimeConstraint> inConstraintClass){
        return ConstraintEditors.get(inConstraintClass);
    }
    
    public static Class<? extends ConstraintEditorWindow> getConstraintEditor(String inFriendlyString){
        return getConstraintEditor(MyLittePonyMaps.getConstraintClassForFriendlyString(inFriendlyString));
    }
    
    public abstract T getValue();
}
