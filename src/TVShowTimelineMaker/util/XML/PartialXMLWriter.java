/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util.XML;

import TVShowTimelineMaker.util.JodaTimeUtil;
import java.util.List;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.Chronology;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Partial;


public class PartialXMLWriter extends XMLWriterImp<Partial> {
    
    private static final Logger LOG = Logger.getLogger(PartialXMLWriter.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        XMLWriterImp.addXMLWriter(Partial.class, new PartialXMLWriter());
    }

    @Override
    public Element writeElements(Partial ObjectToWrite) {
        Element newElement = new Element("Partial");
        JodaTimeUtil timeUtil = JodaTimeUtil.getInstance();
        DateTimeFieldType[] fieldTypes = ObjectToWrite.getFieldTypes();
        int[] values = ObjectToWrite.getValues();
        DateTimeZone zone = ObjectToWrite.getChronology().getZone();
        Element TimeZoneElement = new Element("TimeZone");
        TimeZoneElement.setText(timeUtil.getStringForDateTimeZone(zone));
        newElement.addContent(TimeZoneElement);
        Chronology chronology = ObjectToWrite.getChronology();
        Element ChronologyElement = new Element("Chronology");
        ChronologyElement.setText(timeUtil.getStringForChronology(chronology));
        newElement.addContent(ChronologyElement);
        Element FieldsElement = new Element("Fields");
        for (int i = 0; i <fieldTypes.length; i++){
            Element KeyValuePairElement = new Element("KeyValuePair");
            Element KeyElement = new Element("Key");
            KeyElement.setText(timeUtil.getStringForDateTimeFieldType(fieldTypes[i]));
            Element ValueElement = new Element("Value");
            ValueElement.setText(java.lang.Integer.toString(values[i]));
            KeyValuePairElement.addContent(KeyElement);
            KeyValuePairElement.addContent(ValueElement);
            FieldsElement.addContent(KeyValuePairElement);
        }
        newElement.addContent(FieldsElement);
        return newElement;
    }

    @Override
    public Partial readElements(org.jdom2.Element root) {
        JodaTimeUtil timeUtil = JodaTimeUtil.getInstance();
        Element TimeZoneElement = root.getChild("TimeZone");
        Element ChronologyElement = root.getChild("Chronology");
        Chronology myChronology = timeUtil.getChronologyForString(ChronologyElement.getTextNormalize(), TimeZoneElement.getTextNormalize());
        Element FieldsElement = root.getChild("Fields");
        List<Element> FieldsChildren = FieldsElement.getChildren();
        List<DateTimeFieldType> fieldTypes = new java.util.ArrayList<>(FieldsChildren.size());
        List<Integer> values = new java.util.ArrayList<>(FieldsChildren.size());
        for (Element curKeyValue : FieldsChildren){
            Element KeyElement = curKeyValue.getChild("Key");
            Element ValueElement = curKeyValue.getChild("Value");
            fieldTypes.add(timeUtil.getDateTimeFieldTypeForString(KeyElement.getTextNormalize()));
            values.add(Integer.parseInt(ValueElement.getTextNormalize()));
        }
        int intValues[] = new int[values.size()];
        int i = 0;
        for (Integer curValue : values){
            intValues[i] = curValue;
            i++;
        }
        return new Partial(fieldTypes.toArray(new DateTimeFieldType[fieldTypes.size()]), intValues,myChronology);
    }
    
}
