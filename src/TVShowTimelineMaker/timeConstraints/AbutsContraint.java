/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.AbstractTwoEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.TwoEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.AutomaticMetaData;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent.PeriodPlacement;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.Primary;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;


public class AbutsContraint<T extends PeriodPlacement<?>,S extends PeriodPlacement<?>> extends AbstractTwoEventTimeConstraint<T,S> {

    private static final long serialVersionUID = 1201L;
    private static final Logger LOG = Logger.getLogger(AbutsContraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("AbutsContraint", AbutsContraint.class);
        MyLittePonyMaps.addTimeConstraintMetaData(AbutsContraint.class,AutomaticMetaData.constuctFromClass(AbutsContraint.class));
    }
    private final PeriodEvent<?,?> mFirstPeriodEvent;
    private final PeriodEvent<?,?> mSecondPeriodEvent;
    private final Event rConstrainedEvents[];
    
    @Primary
    public AbutsContraint(PeriodEvent<?,?> inFirstEpisode, PeriodEvent<?,?> inSecondEpisode) 
    {
        this(false,inFirstEpisode,inSecondEpisode);
    }

    public AbutsContraint(boolean isSynthetic,PeriodEvent<?,?> inFirstEpisode, PeriodEvent<?,?> inSecondEpisode) 
    {
        super(isSynthetic);
        this.mFirstPeriodEvent = inFirstEpisode;
        this.mSecondPeriodEvent = inSecondEpisode;
        this.rConstrainedEvents = new Event[2];
        this.rConstrainedEvents[0] = this.mFirstPeriodEvent;
        this.rConstrainedEvents[1] = this.mSecondPeriodEvent;
    }
    
    public AbutsContraint(Element root){
        super(root);
        EventImp.EventIDXMLWriter appEventIDXMLWriter = EventImp.EventIDXMLWriter.instance;
            Element firstEpisodeElement = root.getChild("FirstPeriodEventByID");
            this.mFirstPeriodEvent = (PeriodEvent<?,?>) appEventIDXMLWriter.readElements(firstEpisodeElement);
            Element secondEpisodeElement = root.getChild("SecondPeriodEventByID");
            this.mSecondPeriodEvent = (PeriodEvent<?,?>) appEventIDXMLWriter.readElements(secondEpisodeElement);
            this.rConstrainedEvents = new Event[2];
        this.rConstrainedEvents[0] = this.mFirstPeriodEvent;
        this.rConstrainedEvents[1] = this.mSecondPeriodEvent;
    }
    
    @Override
    public boolean inBeta(){
        return true;
    }

    @Override
    public Event getFirstEvent() {
        return this.mFirstPeriodEvent;
    }

    @Override
    public Event getSecondEvent() {
        return this.mSecondPeriodEvent;
    }

    @Override
    public boolean isStrictlyBefore() {
        return (this.mFirstPeriodEvent instanceof OncePeriodEvent) && (this.mSecondPeriodEvent instanceof OncePeriodEvent);
    }

    @Override
    public Event[] getConstrainedEvents() {
        return this.rConstrainedEvents;
    }

    @Override
    public boolean ConstraintSatisfied() {
        boolean con = true;
        if (this.mFirstPeriodEvent instanceof OncePeriodEvent) {
            OncePeriodEvent mFirstOncePeriodEvent = (OncePeriodEvent) this.mFirstPeriodEvent;
            if (this.mSecondPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mSecondOncePeriodEvent = (OncePeriodEvent) this.mSecondPeriodEvent;
                DateTime earyistForStart = mFirstOncePeriodEvent.getEarliestPossibleEndTime().plusDays(1).withHourOfDay(1);
                DateTime lateistForStart = mFirstOncePeriodEvent.getLatestPossibleEndTime().plusDays(1).withHourOfDay(23);
                DateTime earyistForEnd = mSecondOncePeriodEvent.getEarliestPossibleEndTime().minusDays(1).withHourOfDay(1);
                DateTime lateistForEnd = mSecondOncePeriodEvent.getLatestPossibleStartTime().minusDays(1).withHourOfDay(23);
                if (mSecondOncePeriodEvent.getEarliestPossibleStartTime().isBefore(earyistForStart)) {
                    con = false;
                }
                if (mSecondOncePeriodEvent.getLatestPossibleEndTime().isAfter(lateistForStart)) {
                    con = false;
                }
                if (mFirstOncePeriodEvent.getEarliestPossibleStartTime().isBefore(earyistForEnd)) {
                    con = false;
                }
                if (mFirstOncePeriodEvent.getLatestPossibleEndTime().isAfter(lateistForEnd)) {
                    con = false;
                }
            } else if (this.mSecondPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSecondSeason = (YearlyPeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> days = new java.util.HashSet<>(31);
                DateTime endDay = mFirstOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(23);
                for (DateTime curDay = mFirstOncePeriodEvent.getEarliestPossibleEndTime().withHourOfDay(1);
                        curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                    days.add(DayOfYear.fromDateTime(curDay.plusDays(1)));
                }
                if (!days.containsAll(mSecondSeason.getStartPossibleDays())) {
                    con = false;
                }
                //todo: change mFirstOncePeriodEvent
            }
        } else if (this.mFirstPeriodEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent mFirstSeason = (YearlyPeriodEvent) this.mFirstPeriodEvent;
            Set<DayOfYear> startDays1 = new java.util.HashSet<>(31);
            for (DayOfYear curDay : mFirstSeason.getEndPossibleDays()) {
                startDays1.add(curDay.plusDays(1));
            }
            if (this.mSecondPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mSecondOncePeriodEvent = (OncePeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> days = new java.util.HashSet<>(31);
                DateTime endDay = mSecondOncePeriodEvent.getLatestPossibleStartTime().withHourOfDay(23);
                for (DateTime curDay = mSecondOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                        curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                    days.add(DayOfYear.fromDateTime(curDay.minusDays(1)));
                }
                if (!days.containsAll(mFirstSeason.getEndPossibleDays())) {
                    con = false;
                }
                //todo: change mSecondOncePeriodEvent
            } else if (this.mSecondPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSecondSeason = (YearlyPeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> endDays1 = new java.util.HashSet<>(31);
                for (DayOfYear curDay : mSecondSeason.getStartPossibleDays()) {
                    endDays1.add(curDay.plusDays(-1));
                }
                if (!endDays1.containsAll(mFirstSeason.getEndPossibleDays())) {
                    con = false;
                }
                if (!startDays1.containsAll(mSecondSeason.getStartPossibleDays())) {
                    con = false;
                }
            }
        }
        return con;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        if (this.mFirstPeriodEvent instanceof OncePeriodEvent) {
            OncePeriodEvent mFirstOncePeriodEvent = (OncePeriodEvent) this.mFirstPeriodEvent;
            if (this.mSecondPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mSecondOncePeriodEvent = (OncePeriodEvent) this.mSecondPeriodEvent;
                DateTime earyistForStart = mFirstOncePeriodEvent.getEarliestPossibleEndTime().plusDays(1).withHourOfDay(1);
                DateTime lateistForStart = mFirstOncePeriodEvent.getLatestPossibleEndTime().plusDays(1).withHourOfDay(23);
                DateTime earyistForEnd = mSecondOncePeriodEvent.getEarliestPossibleEndTime().minusDays(1).withHourOfDay(1);
                DateTime lateistForEnd = mSecondOncePeriodEvent.getLatestPossibleStartTime().minusDays(1).withHourOfDay(23);
                if (mSecondOncePeriodEvent.getEarliestPossibleStartTime().isBefore(earyistForStart)) {
                    mSecondOncePeriodEvent.setEarliestPossibleDateForStart(earyistForStart);
                    changed = true;
                }
                if (mSecondOncePeriodEvent.getLatestPossibleEndTime().isAfter(lateistForStart)) {
                    mSecondOncePeriodEvent.setLatestPossibleDateForEnd(lateistForStart);
                    changed = true;
                }
                if (mFirstOncePeriodEvent.getEarliestPossibleStartTime().isBefore(earyistForEnd)) {
                    mFirstOncePeriodEvent.setEarliestPossibleDateForStart(earyistForEnd);
                    changed = true;
                }
                if (mFirstOncePeriodEvent.getLatestPossibleEndTime().isAfter(lateistForEnd)) {
                    mFirstOncePeriodEvent.setLatestPossibleDateForEnd(lateistForEnd);
                    changed = true;
                }
            } else if (this.mSecondPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSecondSeason = (YearlyPeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> days = new java.util.HashSet<>(31);
                DateTime endDay = mFirstOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(23);
                for (DateTime curDay = mFirstOncePeriodEvent.getEarliestPossibleEndTime().withHourOfDay(1);
                        curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                    days.add(DayOfYear.fromDateTime(curDay.plusDays(1)));
                }
                if (!days.containsAll(mSecondSeason.getStartPossibleDays())) {
                    mSecondSeason.getStartPossibleDays().retainAll(days);
                    changed = true;
                }
                //todo: change mFirstOncePeriodEvent
            }
        } else if (this.mFirstPeriodEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent mFirstSeason = (YearlyPeriodEvent) this.mFirstPeriodEvent;
            Set<DayOfYear> startDays1 = new java.util.HashSet<>(31);
            for (DayOfYear curDay : mFirstSeason.getEndPossibleDays()) {
                startDays1.add(curDay.plusDays(1));
            }
            if (this.mSecondPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mSecondOncePeriodEvent = (OncePeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> days = new java.util.HashSet<>(31);
                DateTime endDay = mSecondOncePeriodEvent.getLatestPossibleStartTime().withHourOfDay(23);
                for (DateTime curDay = mSecondOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                        curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                    days.add(DayOfYear.fromDateTime(curDay.minusDays(1)));
                }
                if (!days.containsAll(mFirstSeason.getEndPossibleDays())) {
                    mFirstSeason.getEndPossibleDays().retainAll(days);
                    changed = true;
                }
                //todo: change mSecondOncePeriodEvent
            } else if (this.mSecondPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSecondSeason = (YearlyPeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> endDays1 = new java.util.HashSet<>(31);
                for (DayOfYear curDay : mSecondSeason.getStartPossibleDays()) {
                    endDays1.add(curDay.plusDays(-1));
                }
                if (!endDays1.containsAll(mFirstSeason.getEndPossibleDays())) {
                    mFirstSeason.getEndPossibleDays().retainAll(endDays1);
                    changed = true;
                }
                if (!startDays1.containsAll(mSecondSeason.getStartPossibleDays())) {
                    mSecondSeason.getStartPossibleDays().retainAll(startDays1);
                    changed = true;
                }
            }
        }
        return changed;
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = this.applyConstraint();
        if (this.mFirstPeriodEvent instanceof OncePeriodEvent) {
            OncePeriodEvent mFirstOncePeriodEvent = (OncePeriodEvent) this.mFirstPeriodEvent;
            if (this.mSecondPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mSecondOncePeriodEvent = (OncePeriodEvent) this.mSecondPeriodEvent;
                Set<DateTime> startDays1 = new java.util.HashSet<>(mFirstOncePeriodEvent.getEndPossibleDays());
                for (DateTime curDay : mFirstOncePeriodEvent.getEndPossibleDays()) {
                    startDays1.add(curDay.plusDays(1));
                }
                Set<DateTime> endDays1 = new java.util.HashSet<>(mSecondOncePeriodEvent.getStartPossibleDays().size());
                for (DateTime curDay : mSecondOncePeriodEvent.getStartPossibleDays()) {
                    endDays1.add(curDay.minusDays(1));
                }
                if (!endDays1.containsAll(mFirstOncePeriodEvent.getEndPossibleDays())) {
                    mFirstOncePeriodEvent.getEndPossibleDays().retainAll(endDays1);
                    changed = true;
                }
                if (!startDays1.containsAll(mSecondOncePeriodEvent.getStartPossibleDays())) {
                    mSecondOncePeriodEvent.getStartPossibleDays().retainAll(startDays1);
                    changed = true;
                }
            } else if (this.mSecondPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSecondSeason = (YearlyPeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> secondDays = new java.util.HashSet<>(31);
                DateTime endDay = mFirstOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(23);
                for (DateTime curDay = mFirstOncePeriodEvent.getEarliestPossibleEndTime().withHourOfDay(1);
                        curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                    secondDays.add(DayOfYear.fromDateTime(curDay.plusDays(1)));
                }
                if (!secondDays.containsAll(mSecondSeason.getStartPossibleDays())) {
                    mSecondSeason.getStartPossibleDays().retainAll(secondDays);
                    changed = true;
                }
                
                Set<DayOfYear> beforeSecondEvent = new java.util.HashSet<>(31);
                for (DayOfYear curDay : mSecondSeason.getStartPossibleDays()){
                    beforeSecondEvent.add(curDay.plusDays(-1));
                }
                Iterator<DateTime> firstEndTimesIterator = mFirstOncePeriodEvent.getEndPossibleDays().iterator();
                while (firstEndTimesIterator.hasNext()){
                    DateTime curDateTime = firstEndTimesIterator.next();
                    DayOfYear curDayOfYear = DayOfYear.fromDateTime(curDateTime);
                    if (!beforeSecondEvent.contains(curDayOfYear)){
                        firstEndTimesIterator.remove();
                    }
                }
            }
        } else if (this.mFirstPeriodEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent mFirstSeason = (YearlyPeriodEvent) this.mFirstPeriodEvent;
            Set<DayOfYear> startDays1 = new java.util.HashSet<>(mFirstSeason.getEndPossibleDays().size());
            for (DayOfYear curDay : mFirstSeason.getEndPossibleDays()) {
                startDays1.add(curDay.plusDays(1));
            }
            if (this.mSecondPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mSecondOncePeriodEvent = (OncePeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> days = new java.util.HashSet<>(31);
                DateTime endDay = mSecondOncePeriodEvent.getLatestPossibleStartTime().withHourOfDay(23);
                for (DateTime curDay = mSecondOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                        curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                    days.add(DayOfYear.fromDateTime(curDay.minusDays(1)));
                }
                if (!days.containsAll(mFirstSeason.getEndPossibleDays())) {
                    mFirstSeason.getEndPossibleDays().retainAll(days);
                    changed = true;
                }
                
                Set<DayOfYear> afterFirstEvent = new java.util.HashSet<>(31);
                for (DayOfYear curDay : mFirstSeason.getEndPossibleDays()){
                    afterFirstEvent.add(curDay.plusDays(1));
                }
                Iterator<DateTime> secondStartTimesIterator = mSecondOncePeriodEvent.getStartPossibleDays().iterator();
                while (secondStartTimesIterator.hasNext()){
                    DateTime curDateTime = secondStartTimesIterator.next();
                    DayOfYear curDayOfYear = DayOfYear.fromDateTime(curDateTime);
                    if (!afterFirstEvent.contains(curDayOfYear)){
                        secondStartTimesIterator.remove();
                    }
                }
            } else if (this.mSecondPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSecondSeason = (YearlyPeriodEvent) this.mSecondPeriodEvent;
                Set<DayOfYear> endDays1 = new java.util.HashSet<>(mSecondSeason.getStartPossibleDays().size());
                for (DayOfYear curDay : mSecondSeason.getStartPossibleDays()) {
                    endDays1.add(curDay.plusDays(-1));
                }
                if (!endDays1.containsAll(mFirstSeason.getEndPossibleDays())) {
                    mFirstSeason.getEndPossibleDays().retainAll(endDays1);
                    changed = true;
                }
                if (!startDays1.containsAll(mSecondSeason.getStartPossibleDays())) {
                    mSecondSeason.getStartPossibleDays().retainAll(startDays1);
                    changed = true;
                }
            }
        }
        return changed;
    }
    
    @Override
    public boolean consistentWithConstraint(PeriodPlacement inFirstPlacement, PeriodPlacement inSecondPlacement){
        if (inFirstPlacement instanceof OncePeriodEvent.OncePeriodEventPlacement){
            OncePeriodEvent.OncePeriodEventPlacement inOncePeriodEventPlacement1 = (OncePeriodEvent.OncePeriodEventPlacement)inFirstPlacement;
            if (inSecondPlacement instanceof OncePeriodEvent.OncePeriodEventPlacement){
                OncePeriodEvent.OncePeriodEventPlacement inOncePeriodEventPlacement2 = (OncePeriodEvent.OncePeriodEventPlacement)inSecondPlacement;
                return inOncePeriodEventPlacement1.endDay.plusDays(1).equals(inOncePeriodEventPlacement2.startDay);
            } else {
                YearlyPeriodEvent.YearlyPeriodEventPlacement inSeasonPlacement2 = (YearlyPeriodEvent.YearlyPeriodEventPlacement)inSecondPlacement;
                DateTime comparedate1 = inOncePeriodEventPlacement1.endDay.plusDays(1);
                return inSeasonPlacement2.startDay.equals(DayOfYear.fromDateTime(comparedate1));
            }
        } else {
            YearlyPeriodEvent.YearlyPeriodEventPlacement inSeasonPlacement1 = (YearlyPeriodEvent.YearlyPeriodEventPlacement)inFirstPlacement;
            if (inSecondPlacement instanceof OncePeriodEvent.OncePeriodEventPlacement){
                OncePeriodEvent.OncePeriodEventPlacement inOncePeriodEventPlacement2 = (OncePeriodEvent.OncePeriodEventPlacement)inSecondPlacement;
                DayOfYear comparedate2 = DayOfYear.fromDateTime(inOncePeriodEventPlacement2.startDay.plusDays(-1));
                return inSeasonPlacement1.endDay.equals(comparedate2);
            } else {
                YearlyPeriodEvent.YearlyPeriodEventPlacement inSeasonPlacement2 = (YearlyPeriodEvent.YearlyPeriodEventPlacement)inSecondPlacement;
                return inSeasonPlacement1.endDay.plusDays(1).equals(inSeasonPlacement2.startDay);
            }
        }
    }

    

    @Override
    public Event[] increaseWhat(Placement inValues[]) {
        return this.rConstrainedEvents;
    }

    @Override
    public boolean isStrict() {
        return true;
    }

    @Override
    public boolean isSynthetic() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class AbutsContraintXMLWriter
            extends IDedObjectXMLWriter<AbutsContraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(AbutsContraint.class, new AbutsContraint.AbutsContraintXMLWriter());
        }

        @Override
        public Element writeElements(AbutsContraint ObjectToWrite) {
            EventImp.EventIDXMLWriter appEventIDXMLWriter = EventImp.EventIDXMLWriter.instance;
            Element newElement = new Element("AbutsContraint");
            this.writeElements(ObjectToWrite, newElement);
            Element firstEpisodeElement = new Element("FirstPeriodEventByID");
            appEventIDXMLWriter.writeElements(ObjectToWrite.mFirstPeriodEvent, firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            Element secondEpisodeElement = new Element("SecondPeriodEventByID");
            appEventIDXMLWriter.writeElements(ObjectToWrite.mSecondPeriodEvent, secondEpisodeElement);
            newElement.addContent(secondEpisodeElement);
            return newElement;
        }

        @Override
        public AbutsContraint<?,?> readElements(Element root) {
            return new AbutsContraint<>(root);
        }
    }

}
