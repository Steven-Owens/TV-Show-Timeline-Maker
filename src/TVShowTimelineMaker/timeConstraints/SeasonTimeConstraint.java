/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.interfaces.OneEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.DayAcceptorOneEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.DayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.NotDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.SeasonDayAcceptor;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.Season;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.logging.Logger;
import org.jdom2.Element;

public class SeasonTimeConstraint extends DayAcceptorOneEventTimeConstraint implements OneEventTimeConstraint {

    private static final long serialVersionUID = 36L;
    private static final Logger LOG = Logger.getLogger(SeasonTimeConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init2() {
        MyLittePonyMaps.putConstraint("SeasonTimeConstraint", SeasonTimeConstraint.class);
    }
    
    private static DayAcceptor createDayAcceptor(Element root){
        Element SeasonElement = root.getChild("Season");
            Season mSeason = Season.valueOf(SeasonElement.getTextNormalize());
            boolean inside = true;
            Element insideElement = root.getChild("inside");
            if (insideElement != null){
                inside = java.lang.Boolean.parseBoolean(insideElement.getTextNormalize());
            }
            return createDayAcceptor(mSeason,inside);
    }

    private static DayAcceptor createDayAcceptor(Season inSeason, boolean inside) {
        if (inside) {
            return new SeasonDayAcceptor(inSeason);
        } else {
            return new NotDayAcceptor(new SeasonDayAcceptor(inSeason));
        }
    }
    private final Season mSeason;
    private final boolean mInside;
    
    public SeasonTimeConstraint(DayEvent inEvent, Season inSeason) {
        this(inEvent, inSeason, true);
    }
    
    public SeasonTimeConstraint(DayEvent inEvent, Season inSeason, boolean inside) {
        this(false, inEvent, inSeason, inside);
    }

    public SeasonTimeConstraint(boolean isSynthetic, DayEvent inEvent, Season inSeason) {
        this(isSynthetic, inEvent, inSeason, true);
    }

    public SeasonTimeConstraint(boolean isSynthetic, DayEvent inEvent, Season inSeason, boolean inside) {
        super(isSynthetic, inEvent, createDayAcceptor(inSeason, inside));
        this.mSeason = inSeason;
        this.mInside = inside;

    }
    
    protected SeasonTimeConstraint(Element root){
        super(root,createDayAcceptor(root));
        final SeasonDayAcceptor inSeasonDayAcceptor;
        if (this.mDayAcceptor instanceof NotDayAcceptor){
            this.mInside = false;
            inSeasonDayAcceptor = (SeasonDayAcceptor) ((NotDayAcceptor)mDayAcceptor).getInterDayAcceptor();
        } else if (this.mDayAcceptor instanceof SeasonDayAcceptor) {
            this.mInside = true;
            inSeasonDayAcceptor = (SeasonDayAcceptor) mDayAcceptor;
        } else {
            this.mInside = true;
            inSeasonDayAcceptor = (SeasonDayAcceptor) mDayAcceptor;
        }
        this.mSeason = inSeasonDayAcceptor.getSeason();
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
     * @return the mSeason
     */
    public Season getSeason() {
        return this.mSeason;
    }

    @Override
    public String toString() {
        String rString = this.mEvent.getName() + " Happens ";
        switch (this.mSeason) {
            case END_FALL:
            case END_SPRING:
            case END_SUMMER:
            case END_WINTER:
                if (this.mInside) {
                    rString += "at the end of ";
                } else {
                    rString += "not during the end of ";
                }
                break;
            case FALL:
            case SPRING:
            case SUMMER:
            case WINTER:
                if (this.mInside) {
                    rString += "during ";
                } else {
                    rString += "not during ";
                }
                break;
            case START_FALL:
            case START_SPRING:
            case START_SUMMER:
            case START_WINTER:
                if (this.mInside) {
                    rString += "at the start of ";
                } else {
                    rString += "not during the start of ";
                }
                break;
        }
        switch (this.mSeason) {
            case START_FALL:
            case FALL:
            case END_FALL:
                rString += "fall";
                break;
            case START_SPRING:
            case SPRING:
            case END_SPRING:
                rString += "spring";
                break;
            case START_SUMMER:
            case SUMMER:
            case END_SUMMER:
                rString += "summer";
                break;
            case START_WINTER:
            case WINTER:
            case END_WINTER:
                rString += "winter";
                break;
            default:
                rString += this.mSeason.toString();
        }
        return rString;
    }

    @Override
    public Event getEvent() {
        return this.mEvent;
    }

    /**
     * @return the mInside
     */
    public boolean isInside() {
        return this.mInside;
    }


    public static class SeasonTimeConstraintXMLWriter extends DayAcceptorOneEventTimeConstraintXMLWriter<SeasonTimeConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(SeasonTimeConstraint.class, new SeasonTimeConstraintXMLWriter());
        }

        @Override
        public Element writeElements(SeasonTimeConstraint ObjectToWrite) {
            Element newElement = new Element("SeasonTimeConstraint");
            writeElements(ObjectToWrite, newElement);
            Element kindElement = new Element("Season");
            kindElement.setText(ObjectToWrite.mSeason.name());
            newElement.addContent(kindElement);
            Element insideElement = new Element("inside");
            insideElement.setText(java.lang.Boolean.toString(ObjectToWrite.mInside));
            newElement.addContent(insideElement);
            return newElement;
        }

        @Override
        public SeasonTimeConstraint readElements(Element root) {
            return new SeasonTimeConstraint(root);
        }
    }
}
