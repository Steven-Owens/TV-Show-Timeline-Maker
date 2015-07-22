/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.TimeConstraintImp;
import TVShowTimelineMaker.timeConstraints.interfaces.OneEventTimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.logging.Logger;
import org.jdom2.Element;

//TODO:fill in
//TODO:change to AbstractTimeConstraint
//TODO:change to new xml reader model
public class DurationContraint extends TimeConstraintImp implements OneEventTimeConstraint {
    private static final Logger LOG = Logger.getLogger(DurationContraint.class.getName());
    private static final long serialVersionUID = 1L;
    
    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("DurationContraint", DurationContraint.class);
    }

    public DurationContraint(boolean isSynthetic) {
        super(isSynthetic);
    }
    
    @Override
    public boolean inBeta(){
        return true;
    }

    @Override
    public Event[] getConstrainedEvents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean applyConstraint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean complexApplyConstraint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ConstraintSatisfied() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean consistentWithConstraint(Placement<?>[] inValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Event[] increaseWhat(Placement<?>[] inValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isStrict() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Event getEvent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static class DurationContraintXMLWriter extends XMLWriterImp<DurationContraint> {
        
        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(DurationContraint.class, new DurationContraintXMLWriter());
        }

        @Override
        public Element writeElements(DurationContraint ObjectToWrite) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DurationContraint readElements(Element root) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
