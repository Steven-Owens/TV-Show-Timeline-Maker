/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util.XML;

import TVShowTimelineMaker.util.JodaTimeUtil;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTimeFieldType;


public class DateTimeFieldTypeXMLWriter extends XMLWriterImp<DateTimeFieldType> {
    
    private static final Logger LOG = Logger.getLogger(DateTimeFieldTypeXMLWriter.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        XMLWriterImp.addXMLWriter(DateTimeFieldType.class, new DateTimeFieldTypeXMLWriter());
    }

    @Override
    public Element writeElements(DateTimeFieldType ObjectToWrite) {
        Element newElement = new Element("DateTimeFieldType");
        newElement.setText(JodaTimeUtil.getInstance().getStringForDateTimeFieldType(ObjectToWrite));
        return newElement;
    }

    @Override
    public DateTimeFieldType readElements(Element root) {
        return JodaTimeUtil.getInstance().getDateTimeFieldTypeForString(root.getTextNormalize());
    }
    
}
