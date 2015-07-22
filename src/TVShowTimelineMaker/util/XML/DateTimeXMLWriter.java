/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util.XML;

import TVShowTimelineMaker.util.JodaTimeUtil;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


public class DateTimeXMLWriter extends XMLWriterImp<DateTime> {
    
    private static final Logger LOG = Logger.getLogger(DateTimeXMLWriter.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        XMLWriterImp.addXMLWriter(DateTime.class, new DateTimeXMLWriter());
    }

    @Override
    public Element writeElements(DateTime ObjectToWrite) {
        Element newElement = new Element("DateTime");
        JodaTimeUtil timeUtil = JodaTimeUtil.getInstance();
        DateTimeZone zone = ObjectToWrite.getZone();
        Element TimeZoneElement = new Element("TimeZone");
        TimeZoneElement.setText(timeUtil.getStringForDateTimeZone(zone));
        newElement.addContent(TimeZoneElement);
        Chronology chronology = ObjectToWrite.getChronology();
        Element ChronologyElement = new Element("Chronology");
        ChronologyElement.setText(timeUtil.getStringForChronology(chronology));
        newElement.addContent(ChronologyElement);
        Element DateElement = new Element("Date");
        DateElement.setText(timeUtil.writeTimeString(ObjectToWrite));
        newElement.addContent(DateElement);
        return newElement;
    }

    @Override
    public DateTime readElements(org.jdom2.Element root) {
        JodaTimeUtil timeUtil = JodaTimeUtil.getInstance();
        Element TimeZoneElement = root.getChild("TimeZone");
        Element ChronologyElement = root.getChild("Chronology");
        Chronology chronology = timeUtil.getChronologyForString(ChronologyElement.getTextNormalize(), TimeZoneElement.getTextNormalize());
        Element DateElement = root.getChild("Date");
        DateTime newDateTime = timeUtil.readTimeString(DateElement.getTextNormalize(),chronology);
        return newDateTime;
    }
    
}
