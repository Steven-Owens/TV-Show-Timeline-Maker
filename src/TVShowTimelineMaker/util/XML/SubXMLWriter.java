/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util.XML;

import org.jdom2.Element;

/**
 *
 * @author Steven Owens
 */
public interface SubXMLWriter<T> extends XMLWriter<T> {
    public void writeElements(T ObjectToWrite, Element newRoot);
}
