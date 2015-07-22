/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util.XML;

import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Interval;


public class IntervalXMLWriter extends XMLWriterImp<Interval> {
    
    private static final Logger LOG = Logger.getLogger(IntervalXMLWriter.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        XMLWriterImp.addXMLWriter(Interval.class, new IntervalXMLWriter());
    }

    @Override
    public Element writeElements(Interval ObjectToWrite) {
        Element newElement = new Element("Interval");
        XMLWriter<DateTime> DateTimeWriter = XMLWriterImp.getXMLWriter(DateTime.class);
        Element startElement = new Element("startTime");
        startElement.addContent(DateTimeWriter.writeElements(ObjectToWrite.getStart()));
        newElement.addContent(startElement);
        Element endElement = new Element("endTime");
        endElement.addContent(DateTimeWriter.writeElements(ObjectToWrite.getEnd()));
        newElement.addContent(endElement);
        return newElement;
    }

    @Override
    public Interval readElements(org.jdom2.Element root) {
        XMLWriter<DateTime> DateTimeWriter = XMLWriterImp.getXMLWriter(DateTime.class);
        Element startElement = root.getChild("startTime");
        DateTime startTime = DateTimeWriter.readElements(startElement.getChildren().get(0));
        Element endElement = root.getChild("endTime");
        DateTime endTime = DateTimeWriter.readElements(endElement.getChildren().get(0));
        Interval newInterval = new Interval(startTime,endTime);
        return newInterval;
    }
    
}
