/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util.XML;

import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.MutablePeriod;


public class MutablePeriodXMLWriter extends XMLWriterImp<MutablePeriod> {
    
    private static final Logger LOG = Logger.getLogger(MutablePeriodXMLWriter.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        XMLWriterImp.addXMLWriter(MutablePeriod.class, new MutablePeriodXMLWriter());
    }

    @Override
    public Element writeElements(MutablePeriod ObjectToWrite) {
        Element newElement = new Element("ReadablePeriod");
        newElement.setAttribute("class", ObjectToWrite.getClass().getCanonicalName());
        newElement.setText(ObjectToWrite.toString());
        return newElement;
    }

    @Override
    public MutablePeriod readElements(Element root) {
        MutablePeriod newMutablePeriod = MutablePeriod.parse(root.getTextNormalize());
        return newMutablePeriod;
    }
}
