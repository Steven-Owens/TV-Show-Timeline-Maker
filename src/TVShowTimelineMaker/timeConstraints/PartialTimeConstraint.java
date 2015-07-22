/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.interfaces.OneEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.DayAcceptorOneEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.DayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.PartialDayAcceptor;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.Partial;


public class PartialTimeConstraint extends DayAcceptorOneEventTimeConstraint implements OneEventTimeConstraint {

    private static final long serialVersionUID = 8L;
    private static final Logger LOG = Logger.getLogger(PartialTimeConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("PartialTimeConstraint", PartialTimeConstraint.class);
    }
    
    private static DayAcceptor createDayAcceptor(Element root){
            XMLWriter<Partial> PartialWriter = XMLWriterImp.getXMLWriter(Partial.class);
        Element mPartialElement = root.getChild("incompleteDate");
            Partial mPartial = PartialWriter.readElements(mPartialElement.getChildren().get(0));
            return new PartialDayAcceptor(mPartial);
    }

    private final Partial incompleteDate;
    
     public PartialTimeConstraint(DayEvent<?,?> inEpisode, Partial inPartial) {
         this(false, inEpisode, inPartial);
     }

    public PartialTimeConstraint(boolean isSynthetic, DayEvent<?,?> inEpisode, Partial inPartial) {
        super(isSynthetic, inEpisode, new PartialDayAcceptor(inPartial));
        this.incompleteDate = inPartial;
    }
    
    protected PartialTimeConstraint(Element root){
        super(root,createDayAcceptor(root));
        this.incompleteDate = ((PartialDayAcceptor) this.mDayAcceptor).getIncompleteDate();
    }
    
    @Override
    public boolean inBeta(){
        return false;
    }
    
    @Override
    public boolean isStrict() {
        return true;
    }

    /**
     * @return the incompleteDate
     */
    public Partial getIncompleteDate() {
        return this.incompleteDate;
    }

    @Override
    public String toString() {
        return this.mEvent.toString() + " matches " + this.incompleteDate.toString();
    }

    public static class PartialTimeConstraintXMLWriter extends DayAcceptorOneEventTimeConstraintXMLWriter<PartialTimeConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(PartialTimeConstraint.class, new PartialTimeConstraintXMLWriter());
        }

        @Override
        public Element writeElements(PartialTimeConstraint ObjectToWrite) {
            Element newElement = new Element("PartialTimeConstraint");
            writeElements(ObjectToWrite, newElement);
            XMLWriter<Partial> PartialWriter = XMLWriterImp.getXMLWriter(Partial.class);
            Element mPartialElement = new Element("incompleteDate");
            mPartialElement.addContent(PartialWriter.writeElements(ObjectToWrite.incompleteDate));
            newElement.addContent(mPartialElement);
            return newElement;
        }

        @Override
        public PartialTimeConstraint readElements(org.jdom2.Element root) {
            return new PartialTimeConstraint(root);
        }
    }
}
