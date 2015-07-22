/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.ui.Event;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.ui.EditorWindow;
import TVShowTimelineMaker.ui.util.EditorComponent;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import java.util.Map;

/**
 *
 * @author Steven Owens
 */
public abstract class EventEditorWindow<T extends Event> extends EditorWindow implements EditorComponent<T> {
    private static final long serialVersionUID = 1510L;
    
    private static final Map<Class<? extends Event>, Class<? extends EventEditorWindow>> EventEditors = new java.util.HashMap<>();
    
    public static void addEventEditor(Class<? extends Event> inEventClass, Class<? extends EventEditorWindow> inEditorWindow){
        EventEditors.put(inEventClass, inEditorWindow);
    }
    
    public static Class<? extends EventEditorWindow> getEventEditor(Class<? extends Event> inEventClass){
        return EventEditors.get(inEventClass);
    }
    
    public static Class<? extends EventEditorWindow> getEventEditor(String inFriendlyString){
        return getEventEditor(MyLittePonyMaps.getEventClassForFriendlyString(inFriendlyString));
    }
    
    public abstract T getValue();
}
