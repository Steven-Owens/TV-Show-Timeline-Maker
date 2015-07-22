/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.TimeConstraintImp;
import TVShowTimelineMaker.timeConstraints.interfaces.TrimMasterListCapableTimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.PeriodType;

//TODO:change to new xml reader model
public class IntervalRelation extends TimeConstraintImp implements TimeConstraint, TrimMasterListCapableTimeConstraint {
    private static final long serialVersionUID = 34L;
    private static final Logger LOG = Logger.getLogger(IntervalRelation.class.getName());
    
    @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            MyLittePonyMaps.putConstraint("IntervalRelation", IntervalRelation.class);
        }

    private final OnceDayEvent FirstPairOne;
    private final OnceDayEvent FirstPairTwo;
    private final OnceDayEvent SecondPairOne;
    private final OnceDayEvent SecondPairTwo;
    private final IntervalRelationKind kind;

    private final OnceDayEvent ConstrainedEvents[];

    private int maxDiff;
    private int minDiff;
    private long lastmodifyed;
    
    public IntervalRelation(OnceDayEvent inFirstPairOne, OnceDayEvent inFirstPairTwo, OnceDayEvent inSecondPairOne, OnceDayEvent inSecondPairTwo, IntervalRelationKind inKind) {
        this(false, inFirstPairOne, inFirstPairTwo, inSecondPairOne, inSecondPairTwo, inKind);
    }

    public IntervalRelation(boolean isSynthetic, OnceDayEvent inFirstPairOne, OnceDayEvent inFirstPairTwo, OnceDayEvent inSecondPairOne, OnceDayEvent inSecondPairTwo, IntervalRelationKind inKind) {
        super(isSynthetic);
        this.FirstPairOne = inFirstPairOne;
        this.FirstPairTwo = inFirstPairTwo;
        this.SecondPairOne = inSecondPairOne;
        this.SecondPairTwo = inSecondPairTwo;
        this.ConstrainedEvents = new OnceDayEvent[4];
        this.ConstrainedEvents[0] = this.FirstPairOne;
        this.ConstrainedEvents[1] = this.FirstPairTwo;
        this.ConstrainedEvents[2] = this.SecondPairOne;
        this.ConstrainedEvents[3] = this.SecondPairTwo;
        this.kind = inKind;
        this.lastmodifyed = -1;
    }
    
    @Override
    public boolean inBeta(){
        return true;
    }
    
    @Override
    public boolean isStrict() {
        return true;
    }

    public void CalculateInter() {
        boolean needToRebuild = false;
        for (OnceDayEvent curEvent : this.ConstrainedEvents) {
            if (curEvent.getLastmodifyed() > this.lastmodifyed) {
                needToRebuild = true;
                this.lastmodifyed = Math.max(this.lastmodifyed, curEvent.getLastmodifyed());
            }
        }
        if (needToRebuild) {
            
            if (this.kind == IntervalRelationKind.LESS_THAN_OR_EQUAL) {
                Interval SecondPairInterval = new Interval(this.SecondPairOne.getEarliestPossibleDate(),this.SecondPairTwo.getLatestPossibleDate());
                this.maxDiff = SecondPairInterval.toPeriod(PeriodType.days()).getDays();
            } else if (this.kind == IntervalRelationKind.EQUAL) {
                if (this.SecondPairOne.getLatestPossibleDate().isBefore(this.SecondPairTwo.getEarliestPossibleDate())){
                    Interval SecondPairInterval = new Interval(this.SecondPairOne.getLatestPossibleDate(),this.SecondPairTwo.getEarliestPossibleDate());
                    this.minDiff = SecondPairInterval.toPeriod(PeriodType.days()).getDays();
                } else {
                    this.minDiff = 0;
                }
            } else if (this.kind == IntervalRelationKind.MORE_THAN_OR_EQUAL) {
                Interval FirstPairIntervalMax = new Interval(this.FirstPairOne.getEarliestPossibleDate(),this.FirstPairTwo.getLatestPossibleDate());
                Interval SecondPairIntervalMax = new Interval(this.SecondPairOne.getEarliestPossibleDate(),this.SecondPairTwo.getLatestPossibleDate());
                this.maxDiff = Math.max(FirstPairIntervalMax.toPeriod(PeriodType.days()).getDays(), SecondPairIntervalMax.toPeriod(PeriodType.days()).getDays());
                this.minDiff = 0;
                if (this.FirstPairOne.getLatestPossibleDate().isBefore(this.FirstPairTwo.getEarliestPossibleDate())){
                    Interval FirstPairIntervalMin = new Interval(this.FirstPairOne.getLatestPossibleDate(),this.FirstPairTwo.getEarliestPossibleDate());
                    this.minDiff = Math.max(this.minDiff, FirstPairIntervalMin.toPeriod(PeriodType.days()).getDays());
                } else {
                    this.minDiff = 0;
                }
                if (this.SecondPairOne.getLatestPossibleDate().isBefore(this.SecondPairTwo.getEarliestPossibleDate())){
                    Interval SecondPairIntervalMin = new Interval(this.SecondPairOne.getLatestPossibleDate(),this.SecondPairTwo.getEarliestPossibleDate());
                    this.minDiff = Math.max(this.minDiff, SecondPairIntervalMin.toPeriod(PeriodType.days()).getDays());
                } else {
                    this.minDiff = 0;
                }
            }
        }
    }

    @Override
    public OnceDayEvent[] getConstrainedEvents() {
        return this.ConstrainedEvents;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        this.CalculateInter();
        DateTime dayAfterFirstEpisode = this.FirstPairOne.getEarliestPossibleDate().plusDays(1).withHourOfDay(1);
        DateTime dayBeforeSecondEpisode = this.FirstPairTwo.getLatestPossibleDate().minusDays(1).withHourOfDay(23);
        if (this.kind == IntervalRelationKind.LESS_THAN_OR_EQUAL) {
            DateTime tempEarliestPossibleDateForFirstEpisode = this.FirstPairTwo.getEarliestPossibleDate().minusDays(this.maxDiff).withHourOfDay(1);
            DateTime tempLatestPossibleDateForSecondEpisode = this.FirstPairOne.getLatestPossibleDate().plusDays(this.maxDiff).withHourOfDay(23);
            if (dayAfterFirstEpisode.isAfter(this.FirstPairTwo.getEarliestPossibleDate())) {
                this.FirstPairTwo.setEarliestPossibleDate(dayAfterFirstEpisode);
               changed = true;
            }
            if (this.FirstPairOne.getLatestPossibleDate().isAfter(dayBeforeSecondEpisode)){
                this.FirstPairOne.setLatestPossibleDate(dayBeforeSecondEpisode);
                changed = true;
            }
            if (tempEarliestPossibleDateForFirstEpisode.isAfter(this.FirstPairOne.getEarliestPossibleDate())) {
                this.FirstPairOne.setEarliestPossibleDate(tempEarliestPossibleDateForFirstEpisode);
                changed = true;
            }
            if (this.FirstPairTwo.getLatestPossibleDate().isAfter(tempLatestPossibleDateForSecondEpisode)){
                this.FirstPairTwo.setLatestPossibleDate(tempLatestPossibleDateForSecondEpisode);
                changed = true;
            }
        } else if (this.kind == IntervalRelationKind.EQUAL) {
            DateTime FirstEarliestPossibleDateForFirstEpisode = this.FirstPairTwo.getEarliestPossibleDate().minusDays(this.maxDiff).withHourOfDay(1);
            DateTime FirstLatestPossibleDateForSecondEpisode = this.FirstPairOne.getLatestPossibleDate().plusDays(this.maxDiff).withHourOfDay(23);
            DateTime FirstEarliestPossibleDateForSecondEpisode = this.FirstPairOne.getEarliestPossibleDate().plusDays(this.minDiff).withHourOfDay(1);
            DateTime FirstLatestPossibleDateForFirstEpisode = this.FirstPairTwo.getLatestPossibleDate().minusDays(this.minDiff).withHourOfDay(23);
            if (FirstEarliestPossibleDateForFirstEpisode.isAfter(this.FirstPairOne.getEarliestPossibleDate())) {
                this.FirstPairOne.setEarliestPossibleDate(FirstEarliestPossibleDateForFirstEpisode);
                changed = true;
            }
            if (this.FirstPairTwo.getLatestPossibleDate().isAfter(FirstLatestPossibleDateForSecondEpisode)){
                this.FirstPairTwo.setLatestPossibleDate(FirstLatestPossibleDateForSecondEpisode);
                changed = true;
            }
            if (FirstEarliestPossibleDateForSecondEpisode.isAfter(this.FirstPairTwo.getEarliestPossibleDate())) {
                this.FirstPairTwo.setEarliestPossibleDate(FirstEarliestPossibleDateForSecondEpisode);
               changed = true;
            }
            if (this.FirstPairOne.getLatestPossibleDate().isAfter(FirstLatestPossibleDateForFirstEpisode)){
                this.FirstPairOne.setLatestPossibleDate(FirstLatestPossibleDateForFirstEpisode);
                changed = true;
            }
            DateTime SecondEarliestPossibleDateForFirstEpisode = this.SecondPairTwo.getEarliestPossibleDate().minusDays(this.maxDiff).withHourOfDay(1);
            DateTime SecondLatestPossibleDateForSecondEpisode = this.SecondPairOne.getLatestPossibleDate().plusDays(this.maxDiff).withHourOfDay(23);
            DateTime SecondEarliestPossibleDateForSecondEpisode = this.SecondPairOne.getEarliestPossibleDate().plusDays(this.minDiff).withHourOfDay(1);
            DateTime SecondLatestPossibleDateForFirstEpisode = this.SecondPairTwo.getLatestPossibleDate().minusDays(this.minDiff).withHourOfDay(23);
            if (SecondEarliestPossibleDateForFirstEpisode.isAfter(this.SecondPairOne.getEarliestPossibleDate())) {
                this.SecondPairOne.setEarliestPossibleDate(SecondEarliestPossibleDateForFirstEpisode);
                changed = true;
            }
            if (this.SecondPairTwo.getLatestPossibleDate().isAfter(SecondLatestPossibleDateForSecondEpisode)){
                this.SecondPairTwo.setLatestPossibleDate(SecondLatestPossibleDateForSecondEpisode);
                changed = true;
            }
            if (SecondEarliestPossibleDateForSecondEpisode.isAfter(this.SecondPairTwo.getEarliestPossibleDate())) {
                this.SecondPairTwo.setEarliestPossibleDate(SecondEarliestPossibleDateForSecondEpisode);
               changed = true;
            }
            if (this.SecondPairOne.getLatestPossibleDate().isAfter(SecondLatestPossibleDateForFirstEpisode)){
                this.SecondPairOne.setLatestPossibleDate(SecondLatestPossibleDateForFirstEpisode);
                changed = true;
            }
        } else if (this.kind == IntervalRelationKind.MORE_THAN_OR_EQUAL) {
            DateTime tempEarliestPossibleDateForSecondEpisode = this.FirstPairOne.getEarliestPossibleDate().plusDays(this.minDiff).withHourOfDay(1);
            DateTime tempLatestPossibleDateForFirstEpisode = this.FirstPairTwo.getLatestPossibleDate().minusDays(this.minDiff).withHourOfDay(23);
            if (dayAfterFirstEpisode.isAfter(this.FirstPairTwo.getEarliestPossibleDate())) {
                this.FirstPairTwo.setEarliestPossibleDate(dayAfterFirstEpisode);
               changed = true;
            }
            if (this.FirstPairOne.getLatestPossibleDate().isAfter(dayBeforeSecondEpisode)){
                this.FirstPairOne.setLatestPossibleDate(dayBeforeSecondEpisode);
                changed = true;
            }
            if (tempEarliestPossibleDateForSecondEpisode.isAfter(this.FirstPairTwo.getEarliestPossibleDate())) {
                this.FirstPairTwo.setEarliestPossibleDate(tempEarliestPossibleDateForSecondEpisode);
               changed = true;
            }
            if (this.FirstPairOne.getLatestPossibleDate().isAfter(tempLatestPossibleDateForFirstEpisode)){
                this.FirstPairOne.setLatestPossibleDate(tempLatestPossibleDateForFirstEpisode);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean ConstraintSatisfied() {
        this.CalculateInter();
        boolean satisfied = true;
        DateTime dayAfterFirstEpisode = this.FirstPairOne.getEarliestPossibleDate().plusDays(1).withHourOfDay(1);
        DateTime dayBeforeSecondEpisode = this.FirstPairTwo.getLatestPossibleDate().minusDays(1).withHourOfDay(23);
        if (this.kind == IntervalRelationKind.LESS_THAN_OR_EQUAL) {
            DateTime tempEarliestPossibleDateForFirstEpisode = this.FirstPairTwo.getEarliestPossibleDate().minusDays(this.maxDiff).withHourOfDay(1);
            DateTime tempLatestPossibleDateForSecondEpisode = this.FirstPairOne.getLatestPossibleDate().plusDays(this.maxDiff).withHourOfDay(23);
            if (dayAfterFirstEpisode.isAfter(this.FirstPairTwo.getEarliestPossibleDate())) {
               satisfied = false;
            }
            if (this.FirstPairOne.getLatestPossibleDate().isAfter(dayBeforeSecondEpisode)){
                satisfied = false;
            }
            if (tempEarliestPossibleDateForFirstEpisode.isAfter(this.FirstPairOne.getEarliestPossibleDate())) {
                satisfied = false;
            }
            if (this.FirstPairTwo.getLatestPossibleDate().isAfter(tempLatestPossibleDateForSecondEpisode)){
                satisfied = false;
            }
        } else if (this.kind == IntervalRelationKind.EQUAL) {
            DateTime FirstEarliestPossibleDateForFirstEpisode = this.FirstPairTwo.getEarliestPossibleDate().minusDays(this.maxDiff).withHourOfDay(1);
            DateTime FirstLatestPossibleDateForSecondEpisode = this.FirstPairOne.getLatestPossibleDate().plusDays(this.maxDiff).withHourOfDay(23);
            DateTime FirstEarliestPossibleDateForSecondEpisode = this.FirstPairOne.getEarliestPossibleDate().plus(this.minDiff).withHourOfDay(1);
            DateTime FirstLatestPossibleDateForFirstEpisode = this.FirstPairTwo.getLatestPossibleDate().minus(this.minDiff).withHourOfDay(23);
            if (FirstEarliestPossibleDateForFirstEpisode.isAfter(this.FirstPairOne.getEarliestPossibleDate())) {
                satisfied = false;
            }
            if (this.FirstPairTwo.getLatestPossibleDate().isAfter(FirstLatestPossibleDateForSecondEpisode)){
                satisfied = false;
            }
            if (FirstEarliestPossibleDateForSecondEpisode.isAfter(this.FirstPairTwo.getEarliestPossibleDate())) {
               satisfied = false;
            }
            if (this.FirstPairOne.getLatestPossibleDate().isAfter(FirstLatestPossibleDateForFirstEpisode)){
                satisfied = false;
            }
            DateTime SecondEarliestPossibleDateForFirstEpisode = this.SecondPairTwo.getEarliestPossibleDate().minusDays(this.maxDiff).withHourOfDay(1);
            DateTime SecondLatestPossibleDateForSecondEpisode = this.SecondPairOne.getLatestPossibleDate().plusDays(this.maxDiff).withHourOfDay(23);
            DateTime SecondEarliestPossibleDateForSecondEpisode = this.SecondPairOne.getEarliestPossibleDate().plus(this.minDiff).withHourOfDay(1);
            DateTime SecondLatestPossibleDateForFirstEpisode = this.SecondPairTwo.getLatestPossibleDate().minus(this.minDiff).withHourOfDay(23);
            if (SecondEarliestPossibleDateForFirstEpisode.isAfter(this.SecondPairOne.getEarliestPossibleDate())) {
                satisfied = false;
            }
            if (this.SecondPairTwo.getLatestPossibleDate().isAfter(SecondLatestPossibleDateForSecondEpisode)){
                satisfied = false;
            }
            if (SecondEarliestPossibleDateForSecondEpisode.isAfter(this.SecondPairTwo.getEarliestPossibleDate())) {
               satisfied = false;
            }
            if (this.SecondPairOne.getLatestPossibleDate().isAfter(SecondLatestPossibleDateForFirstEpisode)){
                satisfied = false;
            }
        } else if (this.kind == IntervalRelationKind.MORE_THAN_OR_EQUAL) {
            DateTime tempEarliestPossibleDateForSecondEpisode = this.FirstPairOne.getEarliestPossibleDate().plus(this.minDiff).withHourOfDay(1);
            DateTime tempLatestPossibleDateForFirstEpisode = this.FirstPairTwo.getLatestPossibleDate().minus(this.minDiff).withHourOfDay(23);
            if (dayAfterFirstEpisode.isAfter(this.FirstPairTwo.getEarliestPossibleDate())) {
               satisfied = false;
            }
            if (this.FirstPairOne.getLatestPossibleDate().isAfter(dayBeforeSecondEpisode)){
                satisfied = false;
            }
            if (tempEarliestPossibleDateForSecondEpisode.isAfter(this.FirstPairTwo.getEarliestPossibleDate())) {
               satisfied = false;
            }
            if (this.FirstPairOne.getLatestPossibleDate().isAfter(tempLatestPossibleDateForFirstEpisode)){
                satisfied = false;
            }
        }
        return satisfied;
    }

    /**
     * @return the kind
     */
    public IntervalRelationKind getKind() {
        return this.kind;
    }
    
    @Override
    public String toString(){
        String rString = "The time between '" + this.FirstPairOne.getName() + "' and '" + this.FirstPairTwo.getName() + "' ";
        if (this.kind == IntervalRelationKind.LESS_THAN_OR_EQUAL) {
            rString += "is less than or equal to";
        } else if (this.kind == IntervalRelationKind.EQUAL) {
            rString += "is equal to";
        } else if (this.kind == IntervalRelationKind.MORE_THAN_OR_EQUAL) {
            rString += "is more than or equal to";
        } else {
            rString += this.kind.toString();
        }
         rString = rString + " The time between '" + this.SecondPairOne.getName() + "' and '" + this.SecondPairTwo.getName() + "' ";
         return rString;
    }

    @Override
    public boolean complexApplyConstraint() {
        this.CalculateInter();
        boolean changed = this.applyConstraint();
        //ToDo: replace "Not supported yet." Exception with proper implemention
        return changed;
    }
    
    public boolean consistentWithConstraint(DateTime inPairOneFirstDay, DateTime inPairOneSecondDay, DateTime inPairTwoFirstDay, DateTime inPairTwoSecondDay) {
        int diffOne;
        int diffTwo;
        if (inPairOneFirstDay.isBefore(inPairOneSecondDay)){
            diffOne = new Interval(inPairOneFirstDay,inPairOneSecondDay).toPeriod(PeriodType.days()).getDays();
        } else {
            diffOne = 0;
        }
        if (inPairTwoFirstDay.isBefore(inPairTwoSecondDay)){
            diffTwo = new Interval(inPairTwoFirstDay,inPairTwoSecondDay).toPeriod(PeriodType.days()).getDays();
        } else {
            diffTwo = 0;
        }
        if (this.kind == IntervalRelationKind.LESS_THAN_OR_EQUAL) {
            return diffOne <= diffTwo;
        } else if (this.kind == IntervalRelationKind.EQUAL) {
            return diffOne == diffTwo;
        } else if (this.kind == IntervalRelationKind.MORE_THAN_OR_EQUAL) {
            return diffOne >= diffTwo;
        } else {
            return true;
        }
    }

    @Override
    public boolean consistentWithConstraint(Placement<?>[] inValues) {
        //ToDo: replace "Not supported yet." Exception with proper implemention
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Event[] increaseWhat(Placement<?> inValues[]){
        //ToDo: replace "Not supported yet." Exception with proper implemention
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Event> trimMasterList(Event inEvent, Placement<?> curPlacementForInEvent, Map<Event, List<? extends Placement<?>>> masterList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Event> trimMasterList(Event curPropEvent, Map<Event, List<? extends Placement<?>>> inMasterList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Event> trimMasterList(Map<Event, List<? extends Placement<?>>> inMasterList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static enum IntervalRelationKind {
        LESS_THAN_OR_EQUAL, MORE_THAN_OR_EQUAL, EQUAL
    }
    
    public static class IntervalRelationXMLWriter extends XMLWriterImp<IntervalRelation>{
        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(IntervalRelation.class, new IntervalRelationXMLWriter());
        }

        @Override
        public Element writeElements(IntervalRelation ObjectToWrite) {
            Element newElement = new Element("IntervalRelation");
            Element FirstPairOneEventElement = new Element("FirstPairOneEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.FirstPairOne,FirstPairOneEventElement);
            newElement.addContent(FirstPairOneEventElement);
            Element FirstPairTwoEventElement = new Element("FirstPairTwoEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.FirstPairTwo, FirstPairTwoEventElement);
            newElement.addContent(FirstPairTwoEventElement);
            Element SecondPairOneEventElement = new Element("SecondPairOneEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.SecondPairOne, SecondPairOneEventElement);
            newElement.addContent(SecondPairOneEventElement);
            Element SecondPairTwoEventElement = new Element("SecondPairTwoEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.SecondPairTwo, SecondPairTwoEventElement);
            newElement.addContent(SecondPairTwoEventElement);
            Element kindElement = new Element("kind");
            kindElement.setText(ObjectToWrite.kind.name());
            newElement.addContent(kindElement);
            return newElement;
        }

        @Override
        public IntervalRelation readElements(org.jdom2.Element root) {
            Element FirstPairOneEventElement = root.getChild("FirstPairOneEventByID");
            OnceDayEvent FirstPairOneEvent = (OnceDayEvent)EventImp.EventIDXMLWriter.instance.readElements(FirstPairOneEventElement);
            Element FirstPairTwoEventElement = root.getChild("FirstPairTwoEventByID");
            OnceDayEvent FirstPairTwoEvent = (OnceDayEvent)EventImp.EventIDXMLWriter.instance.readElements(FirstPairTwoEventElement);
            Element SecondPairOneEventElement = root.getChild("SecondPairOneEventByID");
            OnceDayEvent SecondPairOneEvent = (OnceDayEvent)EventImp.EventIDXMLWriter.instance.readElements(SecondPairOneEventElement);
            Element SecondPairTwoEventElement = root.getChild("SecondPairTwoEventByID");
            OnceDayEvent SecondPairTwoEvent = (OnceDayEvent)EventImp.EventIDXMLWriter.instance.readElements(SecondPairTwoEventElement);
            Element kindElement = root.getChild("kind");
            IntervalRelation.IntervalRelationKind kind = IntervalRelation.IntervalRelationKind.valueOf(kindElement.getTextNormalize());
            IntervalRelation rIntervalRelation = new IntervalRelation(FirstPairOneEvent,FirstPairTwoEvent,SecondPairOneEvent,SecondPairTwoEvent,kind);
            return rIntervalRelation;
        }
    }
}
