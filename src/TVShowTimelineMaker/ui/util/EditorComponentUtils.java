/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.ui.util;

import TVShowTimelineMaker.util.JodaTimeUtil;
import com.civprod.swing.CollectionListModel;
import java.awt.Component;
import org.joda.time.DateTimeFieldType;

/**
 *
 * @author Steven Owens
 */
public class EditorComponentUtils {
    private EditorComponentUtils(){}
    
    public static <T>  UIProviderEditorComponent<? extends T> createEditorComponentForType(Class<T> inClass){
        UIProviderEditorComponent<? extends T> returnedEditorComponent = null;
        if (DateTimeFieldType.class.isAssignableFrom(inClass)){
                    CollectionListModel<DateTimeFieldType> newCollectionListModel = new CollectionListModel<>(JodaTimeUtil.getInstance().getDateTimeFieldTypeAsList());
                    final javax.swing.JList<org.joda.time.DateTimeFieldType> newSwingList = new javax.swing.JList<>(newCollectionListModel);
                    returnedEditorComponent = (UIProviderEditorComponent<? extends T>) new UIProviderEditorComponent<org.joda.time.DateTimeFieldType>(){

                        @Override
                        public Component getBaseComponent() {
                            return newSwingList;
                        }

                        @Override
                        public org.joda.time.DateTimeFieldType getValue() {
                            return newSwingList.getSelectedValue();
                        }
                        
                    };
                }
                if (inClass.isPrimitive()){
                    if (inClass.getName().equals("int")){
                        javax.swing.SpinnerNumberModel model = new javax.swing.SpinnerNumberModel();
                        javax.swing.JSpinner newSpinner = new javax.swing.JSpinner();
                        newSpinner.setModel(model);
                        returnedEditorComponent = (UIProviderEditorComponent<? extends T>) new UIProviderEditorComponent<Integer>(){

                            @Override
                            public Component getBaseComponent() {
                                return newSpinner;
                            }

                            @Override
                            public Integer getValue() {
                                return (Integer) newSpinner.getValue();
                            }
                            
                        };
                    }
                }
                return returnedEditorComponent;
    }
}
