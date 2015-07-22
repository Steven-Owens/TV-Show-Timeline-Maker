/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.core;

import TVShowTimelineMaker.timeConstraints.interfaces.OneEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.DayAcceptorTimeConstraint;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.DayAcceptor;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.timeline.YearlyDayEvent;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent;
import TVShowTimelineMaker.util.DayOfYear;
import java.util.Iterator;
import java.util.NavigableSet;
import org.jdom2.Element;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 */
public abstract class DayAcceptorOneEventTimeConstraint extends AbstractTimeConstraint implements OneEventTimeConstraint, DayAcceptorTimeConstraint{
    private static final long serialVersionUID = 356L;
    
    protected final DayAcceptor mDayAcceptor;
    protected final Event mEvent;
    private final Event rConstrainedEvents[];
    
    public DayAcceptorOneEventTimeConstraint(DayEvent<?,?> inEvent, DayAcceptor inDayAcceptor){
        this(false,inEvent,inDayAcceptor);
    }
    
    public DayAcceptorOneEventTimeConstraint(boolean isSynthetic, DayEvent<?,?> inEvent, DayAcceptor inDayAcceptor){
        super(isSynthetic);
        this.mDayAcceptor = inDayAcceptor;
        this.mEvent = inEvent;
        this.rConstrainedEvents = new DayEvent<?,?>[1];
        this.rConstrainedEvents[0] = this.mEvent;
    }
    
    protected DayAcceptorOneEventTimeConstraint(boolean isSynthetic, DayEvent<?,?> inEvent, DayAcceptor inDayAcceptor, String inRootNamespace, int inMyID){
        super(isSynthetic, inRootNamespace, inMyID);
        this.mDayAcceptor = inDayAcceptor;
        this.mEvent = inEvent;
        this.rConstrainedEvents = new DayEvent<?,?>[1];
        this.rConstrainedEvents[0] = this.mEvent;
    }
    
    protected DayAcceptorOneEventTimeConstraint(Element root, DayAcceptor inDayAcceptor){
        super(root);
        EventImp.EventIDXMLWriter appEventIDXMLWriter = EventImp.EventIDXMLWriter.instance;
        this.mDayAcceptor = inDayAcceptor;
        Element EventElement = root.getChild("EventByID");
        this.mEvent = (OnceDayEvent) appEventIDXMLWriter.readElements(EventElement);
        this.rConstrainedEvents = new DayEvent<?,?>[1];
        this.rConstrainedEvents[0] = this.mEvent;
    }
    
    /**
     * @return the mDayAcceptor
     */
    @Override
    public DayAcceptor getDayAcceptor() {
        return this.mDayAcceptor;
    }

    @Override
    public Event[] getConstrainedEvents() {
        return this.rConstrainedEvents;
    }

    @Override
    public boolean applyConstraint() {
        if (this.mEvent instanceof DayEvent){
            DayEvent<?,?> mDayEvent = (DayEvent<?,?>) this.mEvent;
            if (!mDayEvent.getDayAcceptor().contains(this.mDayAcceptor)) {
                mDayEvent.getDayAcceptor().add(this.mDayAcceptor);
            }
        } else if (this.mEvent instanceof PeriodEvent) {
            PeriodEvent<?,?> mPeriodEvent = (PeriodEvent<?,?>) this.mEvent;
            if (!mPeriodEvent.getStartDayAcceptor().contains(this.mDayAcceptor)) {
                mPeriodEvent.getStartDayAcceptor().add(this.mDayAcceptor);
            }
            if (!mPeriodEvent.getEndDayAcceptor().contains(this.mDayAcceptor)) {
                mPeriodEvent.getEndDayAcceptor().add(this.mDayAcceptor);
            }
        }
        return false;
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = false;
        this.applyConstraint(); //always returns false but does other things so still needs to be called;
        if (this.mEvent.isMarkedForComplexEval()) {
            if (this.mEvent instanceof OnceDayEvent) {
                NavigableSet<DateTime> EventDates = ((OnceDayEvent) this.mEvent).getPossibleDays();
                Iterator<DateTime> iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!this.mDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
            } else if (this.mEvent instanceof YearlyDayEvent) {
                NavigableSet<DayOfYear> EventDates = ((YearlyDayEvent) this.mEvent).getPossibleDays();
                Iterator<DayOfYear> iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DayOfYear next = iterator.next();
                    if (!this.mDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
            } else if (this.mEvent instanceof OncePeriodEvent) {
                NavigableSet<DateTime> EventDates = ((OncePeriodEvent) this.mEvent).getStartPossibleDays();
                Iterator<DateTime> iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!this.mDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
                EventDates = ((OncePeriodEvent) this.mEvent).getEndPossibleDays();
                iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!this.mDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
            } else if (this.mEvent instanceof YearlyPeriodEvent) {
                NavigableSet<DayOfYear> EventDates = ((YearlyPeriodEvent) this.mEvent).getStartPossibleDays();
                Iterator<DayOfYear> iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DayOfYear next = iterator.next();
                    if (!this.mDayAcceptor.accept(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
                EventDates = ((YearlyPeriodEvent) this.mEvent).getEndPossibleDays();
                iterator = EventDates.iterator();
                while (iterator.hasNext()) {
                    DayOfYear next = iterator.next();
                    if (!this.mDayAcceptor.accept(next)) {
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
        if (this.mEvent instanceof DayEvent){
            DayEvent<?,?> mDayEvent = (DayEvent<?,?>) this.mEvent;
            satisfied &= mDayEvent.getDayAcceptor().contains(this.mDayAcceptor);
        } else if (this.mEvent instanceof PeriodEvent) {
            PeriodEvent<?,?> mPeriodEvent = (PeriodEvent<?,?>) this.mEvent;
            satisfied &= mPeriodEvent.getStartDayAcceptor().contains(this.mDayAcceptor);
            satisfied &= mPeriodEvent.getEndDayAcceptor().contains(this.mDayAcceptor);
        }
        return satisfied;
    }

    @Override
    public boolean consistentWithConstraint(Placement<?>[] inValues) {
        boolean consistentWith = true;
        if (this.mEvent instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[0];
            consistentWith &= this.mDayAcceptor.accept(curOnceDayEventPlacement.day);
        } else if (this.mEvent instanceof YearlyDayEvent) {
            YearlyDayEvent.YearlyDayEventPlacement curYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inValues[0];
            consistentWith &= this.mDayAcceptor.accept(curYearlyDayEventPlacement.day);
        } else if (this.mEvent instanceof OncePeriodEvent) {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inValues[0];
            consistentWith &= this.mDayAcceptor.accept(curOncePeriodEventPlacement.startDay);
            consistentWith &= this.mDayAcceptor.accept(curOncePeriodEventPlacement.endDay);
        } else if (this.mEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent.YearlyPeriodEventPlacement curYearlyPeriodEventPlacement = (YearlyPeriodEvent.YearlyPeriodEventPlacement) inValues[0];
            consistentWith &= this.mDayAcceptor.accept(curYearlyPeriodEventPlacement.startDay);
            consistentWith &= this.mDayAcceptor.accept(curYearlyPeriodEventPlacement.endDay);
        }
        return consistentWith;
    }

    @Override
    public Event[] increaseWhat(Placement<?>[] inValues) {
        if (!this.consistentWithConstraint(inValues)){
            return new Event[]{this.mEvent};
        } else {
            return new Event[]{};
        }
    }

    @Override
    public Event getEvent() {
        return this.mEvent;
    }
    
    public static abstract class DayAcceptorOneEventTimeConstraintXMLWriter<T extends DayAcceptorOneEventTimeConstraint> extends IDedObjectXMLWriter<T>{
        
        @Override
        public void writeElements(T ObjectToWrite, Element newRoot) {
            Element eventElement = new Element("EventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.mEvent,eventElement);
            newRoot.addContent(eventElement);
            super.writeElements(ObjectToWrite, newRoot);
        }
    }
    
}
