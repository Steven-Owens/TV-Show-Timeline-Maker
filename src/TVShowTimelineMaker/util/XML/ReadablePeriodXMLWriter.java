/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util.XML;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.ReadablePeriod;


public class ReadablePeriodXMLWriter extends XMLWriterImp<ReadablePeriod> {
    
    private static final Logger LOG = Logger.getLogger(ReadablePeriodXMLWriter.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        XMLWriterImp.addXMLWriter(ReadablePeriod.class, new ReadablePeriodXMLWriter());
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Element writeElements(ReadablePeriod ObjectToWrite) {
        return XMLWriterImp.getXMLWriterUnsafe(ObjectToWrite.getClass()).writeElements(ObjectToWrite);
    }

    @Override
    public ReadablePeriod readElements(org.jdom2.Element root) {
        try {
            return (ReadablePeriod) XMLWriterImp.getXMLWriter(Class.forName(root.getAttributeValue("class"))).readElements(root);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadablePeriodXMLWriter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
