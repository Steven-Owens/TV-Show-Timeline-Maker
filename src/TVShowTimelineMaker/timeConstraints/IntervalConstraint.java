/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.TimeConstraintImp;
import TVShowTimelineMaker.timeConstraints.interfaces.OneEventTimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OnceEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Interval;

//TODO:change to new xml reader model
public class IntervalConstraint
        extends TimeConstraintImp
        implements OneEventTimeConstraint {

    private static final long serialVersionUID = 7L;
    private static final Logger LOG = Logger.getLogger(IntervalConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("IntervalConstraint", IntervalConstraint.class);
    }

    private final org.joda.time.Interval mInterval;
    private final OnceEvent firstEpisode;
    private final Event rConstrainedEvents[];
    
    public IntervalConstraint(OnceEvent inEpisode, Interval inInterval) {
        this(false, inEpisode, inInterval);
    }

    public IntervalConstraint(boolean isSynthetic, OnceEvent inEpisode, Interval inInterval) {
        super(isSynthetic);
        this.mInterval = inInterval;
        this.firstEpisode = inEpisode;
        this.rConstrainedEvents = new EventImp[1];
        this.rConstrainedEvents[0] = this.firstEpisode;
    }
    
    @Override
    public boolean inBeta(){
        return false;
    }
    
    @Override
    public boolean isStrict() {
        return true;
    }

    @Override
    public Event[] getConstrainedEvents() {
        return this.rConstrainedEvents;
    }
    
    @Override
    public boolean consistentWithConstraint(Placement<?>[] inValues) {
        if (this.firstEpisode instanceof OnceDayEvent){
            OnceDayEvent.OnceDayEventPlacement curValue = (OnceDayEvent.OnceDayEventPlacement) inValues[0];
            return this.mInterval.contains(curValue.day);
        } else if (this.firstEpisode instanceof OncePeriodEvent) {
            OncePeriodEvent.OncePeriodEventPlacement curValue = (OncePeriodEvent.OncePeriodEventPlacement) inValues[0];
            return this.mInterval.contains(curValue.startDay) && this.mInterval.contains(curValue.endDay);
        } else {
            return true;
        }
    }
    
    public Event[] increaseWhat(Placement inValues[]){
         if (this.firstEpisode instanceof OnceDayEvent){
            OnceDayEvent.OnceDayEventPlacement curValue = (OnceDayEvent.OnceDayEventPlacement) inValues[0];
            if (!this.mInterval.contains(curValue.day)){
                return new Event[]{this.firstEpisode};
            }
        } else if (this.firstEpisode instanceof OncePeriodEvent) {
            OncePeriodEvent.OncePeriodEventPlacement curValue = (OncePeriodEvent.OncePeriodEventPlacement) inValues[0];
            if ((!this.mInterval.contains(curValue.startDay)) || (!this.mInterval.contains(curValue.endDay))){
                return new Event[]{this.firstEpisode};
            }
        } 
            return new Event[]{};
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = this.applyConstraint();
        if (this.firstEpisode.isMarkedForComplexEval()) {
            if (this.firstEpisode instanceof OnceDayEvent) {
                NavigableSet<DateTime> EventDates = ((OnceDayEvent) this.firstEpisode).getPossibleDays();
                Iterator<DateTime> iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (this.mInterval.getStart().isAfter(next)) {
                        iterator.remove();
                        changed = true;
                    } else if (next.isAfter(this.mInterval.getEnd())) {
                        iterator.remove();
                        changed = true;
                    }
                }
            } else if (this.firstEpisode instanceof OncePeriodEvent) {
                OncePeriodEvent OncePeriodEventfirstEpisode = (OncePeriodEvent) this.firstEpisode;
                NavigableSet<DateTime> EventDates = OncePeriodEventfirstEpisode.getStartPossibleDays();
                Iterator<DateTime> iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (this.mInterval.getStart().isAfter(next)) {
                        iterator.remove();
                        changed = true;
                    } else if (next.isAfter(this.mInterval.getEnd())) {
                        iterator.remove();
                        changed = true;
                    }
                }
                EventDates = OncePeriodEventfirstEpisode.getEndPossibleDays();
                iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (this.mInterval.getStart().isAfter(next)) {
                        iterator.remove();
                        changed = true;
                    } else if (next.isAfter(this.mInterval.getEnd())) {
                        iterator.remove();
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        /*if (dayAfterFirstEpisode.isAfter(firstEpisode.getEarliestPossibleDate())) {
         firstEpisode.setEarliestPossibleDate(dayAfterFirstEpisode);
         }
         if (firstEpisode.getLatestPossibleDate().isAfter(dayBeforeSecondEpisode)){
         firstEpisode.setLatestPossibleDate(dayBeforeSecondEpisode);
         }*/
        changed = this.firstEpisode.setAfter(this.mInterval.getStart()) || changed;
        changed = this.firstEpisode.setBefore(this.mInterval.getEnd()) || changed;
        return changed;
    }

    /**
     * @return the mInterval
     */
    public org.joda.time.Interval getmInterval() {
        return this.mInterval;
    }

    @Override
    public boolean ConstraintSatisfied() {
        return this.mInterval.contains(this.firstEpisode.getEarliestPossibleStartTime()) && (!this.firstEpisode.getLatestPossibleEndTime().isAfter(this.mInterval.getEnd()));
    }

    @Override
    public String toString() {
        return this.firstEpisode.toString() + " is between " + this.mInterval.getStart().toString() + " and " + this.mInterval.getEnd().toString();
    }

    @Override
    public Event getEvent() {
        return this.firstEpisode;
    }

    

    public static class IntervalConstraintXMLWriter
            extends XMLWriterImp<IntervalConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(IntervalConstraint.class, new IntervalConstraint.IntervalConstraintXMLWriter());
        }

        @Override
        public Element writeElements(IntervalConstraint ObjectToWrite) {
            Element newElement = new Element("IntervalConstraint");
            Element firstEpisodeElement = new Element("firstEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.firstEpisode, firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            XMLWriter<Interval> IntervalWriter = XMLWriterImp.getXMLWriter(Interval.class);
            Element mIntervalElement = new Element("IntervalConstraintInterval");
            mIntervalElement.addContent(IntervalWriter.writeElements(ObjectToWrite.mInterval));
            newElement.addContent(mIntervalElement);
            return newElement;
        }

        @Override
        public IntervalConstraint readElements(org.jdom2.Element root) {
            XMLWriter<Interval> IntervalWriter = XMLWriterImp.getXMLWriter(Interval.class);
            Element firstEpisodeElement = root.getChild("firstEventByID");
            Event firstEpisode = EventImp.EventIDXMLWriter.instance.readElements(firstEpisodeElement);
            Element mIntervalElement = root.getChild("IntervalConstraintInterval");
            Interval mInterval = IntervalWriter.readElements(mIntervalElement.getChildren().get(0));
            IntervalConstraint newIntervalConstraint = new IntervalConstraint((OnceEvent) firstEpisode, mInterval);
            return newIntervalConstraint;
        }
    }
}
