/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.AbstractTwoEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.DayAcceptorTimeConstraint;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.DayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.SameTimeAsDayAcceptor;
import TVShowTimelineMaker.timeline.DateTimeDayEvent;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.DayEvent.DayPlacement;
import TVShowTimelineMaker.timeline.DayOfYearDayEvent;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.timeline.YearlyDayEvent;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Iterator;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;

//TODO:change to new xml reader model
public class SameTimeOfYearConstraint<T extends DayPlacement<?>,S extends DayPlacement<?>> extends AbstractTwoEventTimeConstraint<T,S> implements DayAcceptorTimeConstraint {

    private static final long serialVersionUID = 1211L;
    private static final Logger LOG = Logger.getLogger(SameTimeOfYearConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("SameTimeOfYearConstraint", SameTimeOfYearConstraint.class);
    }

    private final DayEvent<?,T> firstEpisode;
    private final DayEvent<?,S> secondEpisode;
    private final Event rConstrainedEvents[];
    
    private DayAcceptor curWorkDayAcceptor;
    
    public SameTimeOfYearConstraint(DayEvent<?,T> inFirstEpisode, DayEvent<?,S> inSecondEpisode) {
        this(false, inFirstEpisode, inSecondEpisode);
    }

    public SameTimeOfYearConstraint(boolean isSynthetic, DayEvent<?,T> inFirstEpisode, DayEvent<?,S> inSecondEpisode) {
        super(isSynthetic);
        this.firstEpisode = inFirstEpisode;
        this.secondEpisode = inSecondEpisode;
        this.rConstrainedEvents = new DayEvent[2];
        this.rConstrainedEvents[0] = this.firstEpisode;
        this.rConstrainedEvents[1] = this.secondEpisode;
        this.curWorkDayAcceptor = new SameTimeAsDayAcceptor();
    }
    
    @Override
    public boolean inBeta(){
        return false;
    }

    @Override
    public boolean isStrictlyBefore() {
        return false;
    }

    /**
     * @return the firstEpisode
     */
    @Override
    public Event getFirstEvent() {
        return this.firstEpisode;
    }

    /**
     * @return the secondEpisode
     */
    @Override
    public Event getSecondEvent() {
        return this.secondEpisode;
    }

    @Override
    public Event[] getConstrainedEvents() {
        return this.rConstrainedEvents;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        DayEvent<?,T> firstEpisodeDayEvent = this.firstEpisode;
        DayEvent<?,S> secondEpisodeDayEvent = this.secondEpisode;
        SameTimeAsDayAcceptor mSameTimeAsDayAcceptor1 = firstEpisodeDayEvent.getDayAcceptor().getDayAcceptorOfKind(SameTimeAsDayAcceptor.class);
        SameTimeAsDayAcceptor mSameTimeAsDayAcceptor2 = secondEpisodeDayEvent.getDayAcceptor().getDayAcceptorOfKind(SameTimeAsDayAcceptor.class);
        SameTimeAsDayAcceptor workSameTimeAsDayAcceptor;
        if (mSameTimeAsDayAcceptor1 != null) {
            workSameTimeAsDayAcceptor = mSameTimeAsDayAcceptor1;
            if (mSameTimeAsDayAcceptor1 == mSameTimeAsDayAcceptor2) {

            } else if (mSameTimeAsDayAcceptor2 != null) {
                workSameTimeAsDayAcceptor.addAll(mSameTimeAsDayAcceptor2);
                secondEpisodeDayEvent.getDayAcceptor().remove(mSameTimeAsDayAcceptor2);
                secondEpisodeDayEvent.getDayAcceptor().add(workSameTimeAsDayAcceptor);
            } else {
                workSameTimeAsDayAcceptor.add(secondEpisodeDayEvent);
                secondEpisodeDayEvent.getDayAcceptor().add(workSameTimeAsDayAcceptor);
            }
        } else if (mSameTimeAsDayAcceptor2 != null) {
            workSameTimeAsDayAcceptor = mSameTimeAsDayAcceptor2;
            workSameTimeAsDayAcceptor.add(firstEpisodeDayEvent);
            firstEpisodeDayEvent.getDayAcceptor().add(workSameTimeAsDayAcceptor);
        } else {
            workSameTimeAsDayAcceptor = new SameTimeAsDayAcceptor();
            workSameTimeAsDayAcceptor.add(firstEpisodeDayEvent);
            workSameTimeAsDayAcceptor.add(secondEpisodeDayEvent);
            firstEpisodeDayEvent.getDayAcceptor().add(workSameTimeAsDayAcceptor);
            secondEpisodeDayEvent.getDayAcceptor().add(workSameTimeAsDayAcceptor);
        }
        this.curWorkDayAcceptor = workSameTimeAsDayAcceptor;
        return changed;
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = this.applyConstraint();
        if ((!this.firstEpisode.isMarkedForComplexEval()) && this.secondEpisode.isMarkedForComplexEval()) {
            this.firstEpisode.setUpForComplexEval();
        }
        if (this.firstEpisode.isMarkedForComplexEval() && (!this.secondEpisode.isMarkedForComplexEval())) {
            this.secondEpisode.setUpForComplexEval();
        }
        if (this.firstEpisode.isMarkedForComplexEval() && this.secondEpisode.isMarkedForComplexEval()) {
            SameTimeAsDayAcceptor mSameTimeAsDayAcceptor1 = this.firstEpisode.getDayAcceptor().getDayAcceptorOfKind(SameTimeAsDayAcceptor.class);
            SameTimeAsDayAcceptor mSameTimeAsDayAcceptor2 = this.secondEpisode.getDayAcceptor().getDayAcceptorOfKind(SameTimeAsDayAcceptor.class);
            SameTimeAsDayAcceptor workSameTimeAsDayAcceptor;
            if (mSameTimeAsDayAcceptor1 != null) {
                workSameTimeAsDayAcceptor = mSameTimeAsDayAcceptor1;
                if (mSameTimeAsDayAcceptor1 == mSameTimeAsDayAcceptor2) {

                } else if (mSameTimeAsDayAcceptor2 != null) {
                    workSameTimeAsDayAcceptor.addAll(mSameTimeAsDayAcceptor2);
                    this.secondEpisode.getDayAcceptor().remove(mSameTimeAsDayAcceptor2);
                    this.secondEpisode.getDayAcceptor().add(workSameTimeAsDayAcceptor);
                } else {
                    workSameTimeAsDayAcceptor.add(this.secondEpisode);
                    this.secondEpisode.getDayAcceptor().add(workSameTimeAsDayAcceptor);
                }
            } else if (mSameTimeAsDayAcceptor2 != null) {
                workSameTimeAsDayAcceptor = mSameTimeAsDayAcceptor2;
                workSameTimeAsDayAcceptor.add(this.firstEpisode);
                this.firstEpisode.getDayAcceptor().add(workSameTimeAsDayAcceptor);
            } else {
                workSameTimeAsDayAcceptor = new SameTimeAsDayAcceptor();
                workSameTimeAsDayAcceptor.add(this.firstEpisode);
                workSameTimeAsDayAcceptor.add(this.secondEpisode);
                this.firstEpisode.getDayAcceptor().add(workSameTimeAsDayAcceptor);
                this.secondEpisode.getDayAcceptor().add(workSameTimeAsDayAcceptor);
            }

            if (this.firstEpisode instanceof DateTimeDayEvent) {
                Iterator<DateTime> iterator = ((DateTimeDayEvent<?>) this.firstEpisode).getPossibleDays().iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!workSameTimeAsDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    } else if (!workSameTimeAsDayAcceptor.accept2(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
            } else if (this.firstEpisode instanceof DayOfYearDayEvent) {
                Iterator<DayOfYear> iterator = ((DayOfYearDayEvent<?>) this.firstEpisode).getPossibleDays().iterator();
                while (iterator.hasNext()) {
                    DayOfYear next = iterator.next();
                    if (!workSameTimeAsDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    } else if (!workSameTimeAsDayAcceptor.accept2(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
            }
            if (this.secondEpisode instanceof DateTimeDayEvent) {
                Iterator<DateTime> iterator = ((DateTimeDayEvent<?>) this.secondEpisode).getPossibleDays().iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!workSameTimeAsDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    } else if (!workSameTimeAsDayAcceptor.accept2(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
            } else if (this.secondEpisode instanceof DayOfYearDayEvent) {
                Iterator<DayOfYear> iterator = ((DayOfYearDayEvent<?>) this.secondEpisode).getPossibleDays().iterator();
                while (iterator.hasNext()) {
                    DayOfYear next = iterator.next();
                    if (!workSameTimeAsDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    } else if (!workSameTimeAsDayAcceptor.accept2(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    @Override
    public boolean ConstraintSatisfied() {
        boolean satisfied = true;
        SameTimeAsDayAcceptor mSameTimeAsDayAcceptor1 = this.firstEpisode.getDayAcceptor().getDayAcceptorOfKind(SameTimeAsDayAcceptor.class);
        SameTimeAsDayAcceptor mSameTimeAsDayAcceptor2 = this.secondEpisode.getDayAcceptor().getDayAcceptorOfKind(SameTimeAsDayAcceptor.class);
        satisfied = (mSameTimeAsDayAcceptor1 != null) && (mSameTimeAsDayAcceptor2 != null) && (mSameTimeAsDayAcceptor1 == mSameTimeAsDayAcceptor2);
        return satisfied;
    }

    public boolean consistentWithConstraint(DateTime inFirstDay, DateTime inSecondDay) {
        return (inFirstDay.getMonthOfYear() == inSecondDay.getMonthOfYear())
                && (inFirstDay.getDayOfMonth() == inSecondDay.getDayOfMonth());
    }
    
    @Override
    public boolean consistentWithConstraint(T inFirstPlacement, S inSecondPlacement) {
        int monthOfYear1;
        int monthOfYear2;
        int dayOfMonth1;
        int dayOfMonth2;
        if (this.firstEpisode instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inFirstPlacement;
            monthOfYear1 = curOnceDayEventPlacement.day.getMonthOfYear();
            dayOfMonth1 = curOnceDayEventPlacement.day.getDayOfMonth();
        } else {
            YearlyDayEvent.YearlyDayEventPlacement curYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inFirstPlacement;
            monthOfYear1 = curYearlyDayEventPlacement.day.getMonth();
            dayOfMonth1 = curYearlyDayEventPlacement.day.getMonth();
        }
        if (this.secondEpisode instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inSecondPlacement;
            monthOfYear2 = curOnceDayEventPlacement.day.getMonthOfYear();
            dayOfMonth2 = curOnceDayEventPlacement.day.getDayOfMonth();
        } else {
            YearlyDayEvent.YearlyDayEventPlacement curYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inSecondPlacement;
            monthOfYear2 = curYearlyDayEventPlacement.day.getMonth();
            dayOfMonth2 = curYearlyDayEventPlacement.day.getMonth();
        }
        return (monthOfYear1 == monthOfYear2)
                    && (dayOfMonth1 == dayOfMonth2);
    }
    
    @Override
    public Event[] increaseWhat(Placement<?>[] inValues) {
        int monthOfYear1;
        int monthOfYear2;
        int dayOfMonth1;
        int dayOfMonth2;
        if (this.firstEpisode instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[0];
            monthOfYear1 = curOnceDayEventPlacement.day.getMonthOfYear();
            dayOfMonth1 = curOnceDayEventPlacement.day.getDayOfMonth();
        } else {
            YearlyDayEvent.YearlyDayEventPlacement curYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inValues[0];
            monthOfYear1 = curYearlyDayEventPlacement.day.getMonth();
            dayOfMonth1 = curYearlyDayEventPlacement.day.getMonth();
        }
        if (this.secondEpisode instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[1];
            monthOfYear2 = curOnceDayEventPlacement.day.getMonthOfYear();
            dayOfMonth2 = curOnceDayEventPlacement.day.getDayOfMonth();
        } else {
            YearlyDayEvent.YearlyDayEventPlacement curYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inValues[1];
            monthOfYear2 = curYearlyDayEventPlacement.day.getMonth();
            dayOfMonth2 = curYearlyDayEventPlacement.day.getMonth();
        }
        if ((monthOfYear1 != monthOfYear2)
                    || (dayOfMonth1 != dayOfMonth2)){
            return new Event[]{this.firstEpisode, this.secondEpisode};
        } else {
            return new Event[]{};
        }
    }
    
    @Override
    public boolean isStrict() {
        return true;
    }
    
    @Override
    public String toString() {
        String rString = this.firstEpisode.toString();
        rString += " same time of year as ";
        rString += this.secondEpisode.toString();
        return rString;
    }

    @Override
    public DayAcceptor getDayAcceptor() {
        return this.curWorkDayAcceptor;
    }

    public static class SameTimeOfYearConstraintXMLWriter extends XMLWriterImp<SameTimeOfYearConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(SameTimeOfYearConstraint.class, new SameTimeOfYearConstraintXMLWriter());
        }

        @Override
        public Element writeElements(SameTimeOfYearConstraint ObjectToWrite) {
            Element newElement = new Element("Relation");
            Element firstEpisodeElement = new Element("firstEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.firstEpisode,firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            Element secondEpisodeElement = new Element("secondEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.secondEpisode,secondEpisodeElement);
            newElement.addContent(secondEpisodeElement);
            return newElement;
        }

        @Override
        public SameTimeOfYearConstraint readElements(org.jdom2.Element root) {
            Element firstEpisodeElement = root.getChild("firstEventByID");
            OnceDayEvent firstEpisode = (OnceDayEvent) EventImp.EventIDXMLWriter.instance.readElements(firstEpisodeElement);
            Element secondEpisodeElement = root.getChild("secondEventByID");
            OnceDayEvent secondEpisode = (OnceDayEvent) EventImp.EventIDXMLWriter.instance.readElements(secondEpisodeElement);
            SameTimeOfYearConstraint newSameTimeOfYearConstraint = new SameTimeOfYearConstraint(firstEpisode, secondEpisode);
            return newSameTimeOfYearConstraint;
        }
    }
}
