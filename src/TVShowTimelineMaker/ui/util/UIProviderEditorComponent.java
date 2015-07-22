/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.ui.util;

import java.awt.Component;

/**
 *
 * @author Steven Owens
 */
public interface UIProviderEditorComponent<T> extends EditorComponent<T> {
    public Component getBaseComponent();
}
