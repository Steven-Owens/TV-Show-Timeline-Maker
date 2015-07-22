/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.AbstractTwoEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.SameTimeAsDayAcceptor;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.DayEvent.DayPlacement;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent.PeriodPlacement;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.timeline.YearlyDayEvent;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Interval;

//TODO:change to new xml reader model
public class PeriodToDayConstraint<T extends PeriodPlacement<?>,S extends DayPlacement<?>> extends AbstractTwoEventTimeConstraint<T,S> {

    private static final long serialVersionUID = 1208L;
    private static final Logger LOG = Logger.getLogger(PeriodToDayConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("PeriodToDayConstraint", PeriodToDayConstraint.class);
    }
    private final PeriodEvent<?,?> mPeriodEvent;
    private final DayEvent<?,?> mDayEvent;
    private final Event rConstrainedEvents[];
    private final PeriodToDayType mType;

    public PeriodToDayConstraint(PeriodEvent<?,?> inPeriodEvent, DayEvent<?,?> inDayEvent, PeriodToDayType inContainsType) {
        this(false, inPeriodEvent, inDayEvent, inContainsType);
    }
    
    public PeriodToDayConstraint(boolean isSynthetic, PeriodEvent<?,?> inPeriodEvent, DayEvent<?,?> inDayEvent, PeriodToDayType inContainsType) {
        super(isSynthetic);
        this.mPeriodEvent = inPeriodEvent;
        this.mDayEvent = inDayEvent;
        this.mType = inContainsType;
        this.rConstrainedEvents = new Event[2];
        this.rConstrainedEvents[0] = inPeriodEvent;
        this.rConstrainedEvents[1] = inDayEvent;
    }
    
    @Override
    public boolean inBeta(){
        return true;
    }

    @Override
    public boolean isStrict() {
        return true;
    }

    @Override
    public Event getFirstEvent() {
        return this.mPeriodEvent;
    }

    @Override
    public Event getSecondEvent() {
        return this.mDayEvent;
    }

    @Override
    public boolean isStrictlyBefore() {
        return false;
    }

    @Override
    public Event[] getConstrainedEvents() {
        return this.rConstrainedEvents;
    }

    @Override
    public boolean ConstraintSatisfied() {
        boolean con = true;
        if (this.getType() == PeriodToDayType.SAME_AS_START) {
            if (this.mPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mOncePeriodEvent = (OncePeriodEvent) this.mPeriodEvent;
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    con = con
                            && (mOncePeriodEvent.getEarliestPossibleStartTime().equals(mOnceDayEvent.getEarliestPossibleStartTime()))
                            && (mOncePeriodEvent.getLatestPossibleStartTime().equals(mOnceDayEvent.getLatestPossibleEndTime()));
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = mOncePeriodEvent.getLatestPossibleStartTime().withHourOfDay(23);
                    for (DateTime curDay = mOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    con = con && days.containsAll(mYearlyDayEvent.getPossibleDays());
                }
            } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSeason = (YearlyPeriodEvent) this.mPeriodEvent;
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = mOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    for (DateTime curDay = mOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    con = con && days.containsAll(mSeason.getStartPossibleDays());
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    con = con
                            && mSeason.getStartPossibleDays().containsAll(mYearlyDayEvent.getPossibleDays())
                            && mYearlyDayEvent.getPossibleDays().containsAll(mSeason.getStartPossibleDays());
                }
            }
        } else if (this.getType() == PeriodToDayType.SAME_AS_END) {
            if (this.mPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mOncePeriodEvent = (OncePeriodEvent) this.mPeriodEvent;
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    con = con
                            && (mOncePeriodEvent.getEarliestPossibleEndTime().equals(mOnceDayEvent.getEarliestPossibleStartTime()))
                            && (mOncePeriodEvent.getLatestPossibleEndTime().equals(mOnceDayEvent.getLatestPossibleEndTime()));
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = mOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    for (DateTime curDay = mOncePeriodEvent.getEarliestPossibleEndTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    con = con && days.containsAll(mYearlyDayEvent.getPossibleDays());
                }
            } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSeason = (YearlyPeriodEvent) this.mPeriodEvent;
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = mOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    for (DateTime curDay = mOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    con = con && days.containsAll(mSeason.getEndPossibleDays());
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    con = con
                            && mSeason.getEndPossibleDays().containsAll(mYearlyDayEvent.getPossibleDays())
                            && mYearlyDayEvent.getPossibleDays().containsAll(mSeason.getEndPossibleDays());
                }
            }
        } else if (this.getType() == PeriodToDayType.MIDDLE) {
            if (this.mPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mOncePeriodEvent = (OncePeriodEvent) this.mPeriodEvent;
                org.joda.time.Interval mMiddleStartInterval = new org.joda.time.Interval(mOncePeriodEvent.getEarliestPossibleStartTime(), mOncePeriodEvent.getEarliestPossibleEndTime());
                org.joda.time.Interval mMiddleEndInterval = new org.joda.time.Interval(mOncePeriodEvent.getLatestPossibleStartTime(), mOncePeriodEvent.getLatestPossibleEndTime());
                int startDays = mMiddleStartInterval.toDuration().toStandardDays().getDays();
                int endDays = mMiddleEndInterval.toDuration().toStandardDays().getDays();
                DateTime MiddleStartTime = mOncePeriodEvent.getEarliestPossibleStartTime().plusDays(startDays / 2).withHourOfDay(1);
                DateTime MiddleEndTime = mOncePeriodEvent.getLatestPossibleEndTime().minusDays(endDays / 2).withHourOfDay(23);
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    con = con
                            && (MiddleStartTime.equals(mOnceDayEvent.getEarliestPossibleStartTime()))
                            && (MiddleEndTime.equals(mOnceDayEvent.getLatestPossibleEndTime()));
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = MiddleEndTime.withHourOfDay(23);
                    for (DateTime curDay = MiddleStartTime.withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    con = con && days.containsAll(mYearlyDayEvent.getPossibleDays());
                }
            } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSeason = (YearlyPeriodEvent) this.mPeriodEvent;
                Set<DayOfYear> days1 = new java.util.HashSet<>(Math.max(mSeason.getStartPossibleDays().size(), mSeason.getEndPossibleDays().size()));
                //todo:make this mutiThreaded
                for (DayOfYear curDay1 : mSeason.getStartPossibleDays()) {
                    for (DayOfYear curDay2 : mSeason.getEndPossibleDays()) {
                        int diff = curDay1.diff(curDay2);
                        int move = diff / 2;
                        int rem = diff % 2;
                        days1.add(curDay1.plusDays(move));
                        if (rem == 1) {
                            days1.add(curDay1.plusDays(move + 1));
                        }
                    }
                }
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    Set<DayOfYear> days2 = new java.util.HashSet<>(31);
                    DateTime endDay = mOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    for (DateTime curDay = mOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days2.add(DayOfYear.fromDateTime(curDay));
                    }
                    con = con && days2.containsAll(days1);
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    con = con
                            && days1.containsAll(mYearlyDayEvent.getPossibleDays())
                            && mYearlyDayEvent.getPossibleDays().containsAll(days1);
                }
            }
        }
        return con;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        if (this.getType() == PeriodToDayType.SAME_AS_START) {
            if (this.mPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mOncePeriodEvent = (OncePeriodEvent) this.mPeriodEvent;
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    if (mOncePeriodEvent.getEarliestPossibleStartTime().isBefore(mOnceDayEvent.getEarliestPossibleStartTime())) {
                        mOncePeriodEvent.setEarliestPossibleDateForStart(mOnceDayEvent.getEarliestPossibleStartTime());
                        changed = true;
                    }
                    if (mOnceDayEvent.getEarliestPossibleStartTime().isBefore(mOncePeriodEvent.getEarliestPossibleStartTime())) {
                        mOnceDayEvent.setEarliestPossibleDate(mOncePeriodEvent.getEarliestPossibleStartTime());
                        changed = true;
                    }
                    if (mOncePeriodEvent.getLatestPossibleStartTime().isAfter(mOnceDayEvent.getLatestPossibleEndTime())) {
                        mOncePeriodEvent.setLatestPossibleDateForStart(mOnceDayEvent.getLatestPossibleEndTime());
                        changed = true;
                    }
                    if (mOnceDayEvent.getLatestPossibleEndTime().isAfter(mOncePeriodEvent.getLatestPossibleStartTime())) {
                        mOnceDayEvent.setLatestPossibleDate(mOncePeriodEvent.getLatestPossibleStartTime());
                        changed = true;
                    }
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = mOncePeriodEvent.getLatestPossibleStartTime().withHourOfDay(23);
                    for (DateTime curDay = mOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    if (!days.containsAll(mYearlyDayEvent.getPossibleDays())) {
                        mYearlyDayEvent.getPossibleDays().retainAll(days);
                        changed = true;
                    }
                }
            } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSeason = (YearlyPeriodEvent) this.mPeriodEvent;
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = mOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    for (DateTime curDay = mOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    if (!days.containsAll(mSeason.getStartPossibleDays())) {
                        mSeason.getStartPossibleDays().retainAll(days);
                        changed = true;
                    }
                    if (!mSeason.getStartPossibleDays().isEmpty()) {
                        int startYear = mOnceDayEvent.getEarliestPossibleStartTime().getYear();
                        DayOfYear curDayOfYear = DayOfYear.fromDateTime(mOnceDayEvent.getEarliestPossibleStartTime());
                        while (!mSeason.getStartPossibleDays().contains(curDayOfYear)) {
                            curDayOfYear = curDayOfYear.plusDays(1);
                            if (curDayOfYear.equals(DayOfYear.startOfYear)) {
                                startYear++;
                            }
                        }
                        DateTime oldDateTime = mOnceDayEvent.getEarliestPossibleStartTime();
                        mOnceDayEvent.setEarliestPossibleDate(curDayOfYear.toDateTime().withYear(startYear));
                        if (!mOnceDayEvent.getEarliestPossibleStartTime().equals(oldDateTime)) {
                            changed = true;
                        }
                        int endYear = mOnceDayEvent.getLatestPossibleEndTime().getYear();
                        curDayOfYear = DayOfYear.fromDateTime(mOnceDayEvent.getLatestPossibleEndTime());
                        while (!mSeason.getStartPossibleDays().contains(curDayOfYear)) {
                            curDayOfYear = curDayOfYear.plusDays(-1);
                            if (curDayOfYear.equals(DayOfYear.endOfYear)) {
                                endYear--;
                            }
                        }
                        oldDateTime = mOnceDayEvent.getLatestPossibleEndTime();
                        mOnceDayEvent.setLatestPossibleDate(curDayOfYear.toDateTime().withYear(endYear));
                        if (!mOnceDayEvent.getLatestPossibleEndTime().equals(oldDateTime)) {
                            changed = true;
                        }
                    }
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    if (mSeason.getStartPossibleDays().containsAll(mYearlyDayEvent.getPossibleDays())) {
                        mYearlyDayEvent.getPossibleDays().retainAll(mSeason.getStartPossibleDays());
                        changed = true;
                    }
                    if (mYearlyDayEvent.getPossibleDays().containsAll(mSeason.getStartPossibleDays())) {
                        mSeason.getStartPossibleDays().retainAll(mYearlyDayEvent.getPossibleDays());
                        changed = true;
                    }
                }
            }
        } else if (this.getType() == PeriodToDayType.SAME_AS_END) {
            if (this.mPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mOncePeriodEvent = (OncePeriodEvent) this.mPeriodEvent;
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    if (mOncePeriodEvent.getEarliestPossibleEndTime().isBefore(mOnceDayEvent.getEarliestPossibleStartTime())) {
                        mOncePeriodEvent.setEarliestPossibleDateForEnd(mOnceDayEvent.getEarliestPossibleStartTime());
                        changed = true;
                    }
                    if (mOnceDayEvent.getEarliestPossibleStartTime().isBefore(mOncePeriodEvent.getEarliestPossibleEndTime())) {
                        mOnceDayEvent.setEarliestPossibleDate(mOncePeriodEvent.getEarliestPossibleEndTime());
                        changed = true;
                    }
                    if (mOncePeriodEvent.getLatestPossibleEndTime().isAfter(mOnceDayEvent.getLatestPossibleEndTime())) {
                        mOncePeriodEvent.setLatestPossibleDateForEnd(mOnceDayEvent.getLatestPossibleEndTime());
                        changed = true;
                    }
                    if (mOnceDayEvent.getLatestPossibleEndTime().isAfter(mOncePeriodEvent.getLatestPossibleEndTime())) {
                        mOnceDayEvent.setLatestPossibleDate(mOncePeriodEvent.getLatestPossibleEndTime());
                        changed = true;
                    }
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = mOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    for (DateTime curDay = mOncePeriodEvent.getEarliestPossibleEndTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    if (!days.containsAll(mYearlyDayEvent.getPossibleDays())) {
                        mYearlyDayEvent.getPossibleDays().retainAll(days);
                        changed = true;
                    }
                }
            } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSeason = (YearlyPeriodEvent) this.mPeriodEvent;
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = mOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    for (DateTime curDay = mOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    if (!days.containsAll(mSeason.getEndPossibleDays())) {
                        mSeason.getEndPossibleDays().retainAll(days);
                        changed = true;
                    }
                    if (!mSeason.getEndPossibleDays().isEmpty()) {
                        int startYear = mOnceDayEvent.getEarliestPossibleStartTime().getYear();
                        DayOfYear curDayOfYear = DayOfYear.fromDateTime(mOnceDayEvent.getEarliestPossibleStartTime());
                        while (!mSeason.getEndPossibleDays().contains(curDayOfYear)) {
                            curDayOfYear = curDayOfYear.plusDays(1);
                            if (curDayOfYear.equals(DayOfYear.startOfYear)) {
                                startYear++;
                            }
                        }
                        DateTime oldDateTime = mOnceDayEvent.getEarliestPossibleStartTime();
                        mOnceDayEvent.setEarliestPossibleDate(curDayOfYear.toDateTime().withYear(startYear));
                        if (!mOnceDayEvent.getEarliestPossibleStartTime().equals(oldDateTime)) {
                            changed = true;
                        }
                        int endYear = mOnceDayEvent.getLatestPossibleEndTime().getYear();
                        curDayOfYear = DayOfYear.fromDateTime(mOnceDayEvent.getLatestPossibleEndTime());
                        while (!mSeason.getEndPossibleDays().contains(curDayOfYear)) {
                            curDayOfYear = curDayOfYear.plusDays(-1);
                            if (curDayOfYear.equals(DayOfYear.endOfYear)) {
                                endYear--;
                            }
                        }
                        oldDateTime = mOnceDayEvent.getLatestPossibleEndTime();
                        mOnceDayEvent.setLatestPossibleDate(curDayOfYear.toDateTime().withYear(endYear));
                        if (!mOnceDayEvent.getLatestPossibleEndTime().equals(oldDateTime)) {
                            changed = true;
                        }
                    }
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    if (mSeason.getEndPossibleDays().containsAll(mYearlyDayEvent.getPossibleDays())) {
                        mYearlyDayEvent.getPossibleDays().retainAll(mSeason.getEndPossibleDays());
                        changed = true;
                    }
                    if (mYearlyDayEvent.getPossibleDays().containsAll(mSeason.getEndPossibleDays())) {
                        mSeason.getEndPossibleDays().retainAll(mYearlyDayEvent.getPossibleDays());
                        changed = true;
                    }
                }
            }
        } else if (this.getType() == PeriodToDayType.MIDDLE) {
            if (this.mPeriodEvent instanceof OncePeriodEvent) {
                OncePeriodEvent mOncePeriodEvent = (OncePeriodEvent) this.mPeriodEvent;
                org.joda.time.Interval mMiddleStartInterval = new org.joda.time.Interval(mOncePeriodEvent.getEarliestPossibleStartTime(), mOncePeriodEvent.getEarliestPossibleEndTime());
                org.joda.time.Interval mMiddleEndInterval = new org.joda.time.Interval(mOncePeriodEvent.getLatestPossibleStartTime(), mOncePeriodEvent.getLatestPossibleEndTime());
                int startDays = mMiddleStartInterval.toDuration().toStandardDays().getDays();
                int endDays = mMiddleEndInterval.toDuration().toStandardDays().getDays();
                DateTime MiddleStartTime = mOncePeriodEvent.getEarliestPossibleStartTime().plusDays(startDays / 2).withHourOfDay(1);
                DateTime MiddleEndTime = mOncePeriodEvent.getLatestPossibleEndTime().minusDays(endDays / 2).withHourOfDay(23);
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    if (mOnceDayEvent.getEarliestPossibleStartTime().isBefore(MiddleStartTime)) {
                        mOnceDayEvent.setEarliestPossibleDate(MiddleStartTime);
                        changed = true;
                    }
                    if (mOnceDayEvent.getLatestPossibleEndTime().isAfter(MiddleEndTime)) {
                        mOnceDayEvent.setLatestPossibleDate(MiddleEndTime);
                        changed = true;
                    }
                    //Todo: change mOncePeriodEvent
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    Set<DayOfYear> days = new java.util.HashSet<>(31);
                    DateTime endDay = MiddleEndTime.withHourOfDay(23);
                    for (DateTime curDay = MiddleStartTime.withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days.add(DayOfYear.fromDateTime(curDay));
                    }
                    if (days.containsAll(mYearlyDayEvent.getPossibleDays())) {
                        mYearlyDayEvent.getPossibleDays().retainAll(days);
                        changed = true;
                    }
                    //Todo: change mOncePeriodEvent
                }
            } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent mSeason = (YearlyPeriodEvent) this.mPeriodEvent;
                Set<DayOfYear> days1 = new java.util.HashSet<>(Math.max(mSeason.getStartPossibleDays().size(), mSeason.getEndPossibleDays().size()));
                //todo:make this mutiThreaded
                for (DayOfYear curDay1 : mSeason.getStartPossibleDays()) {
                    for (DayOfYear curDay2 : mSeason.getEndPossibleDays()) {
                        int diff = curDay1.diff(curDay2);
                        int move = diff / 2;
                        int rem = diff % 2;
                        days1.add(curDay1.plusDays(move));
                        if (rem == 1) {
                            days1.add(curDay1.plusDays(move + 1));
                        }
                    }
                }
                if (this.mDayEvent instanceof OnceDayEvent) {
                    OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                    Set<DayOfYear> days2 = new java.util.HashSet<>(31);
                    DateTime endDay = mOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    for (DateTime curDay = mOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1);
                            curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                        days2.add(DayOfYear.fromDateTime(curDay));
                    }
                    //TODO: change mSeason
                    if (!days1.containsAll(days2)) {
                        if (!days1.isEmpty()) {
                            int startYear = mOnceDayEvent.getEarliestPossibleStartTime().getYear();
                            DayOfYear curDayOfYear = DayOfYear.fromDateTime(mOnceDayEvent.getEarliestPossibleStartTime());
                            while (!days1.contains(curDayOfYear)) {
                                curDayOfYear = curDayOfYear.plusDays(1);
                                if (curDayOfYear.equals(DayOfYear.startOfYear)) {
                                    startYear++;
                                }
                            }
                            DateTime oldDateTime = mOnceDayEvent.getEarliestPossibleStartTime();
                            mOnceDayEvent.setEarliestPossibleDate(curDayOfYear.toDateTime().withYear(startYear));
                            if (!mOnceDayEvent.getEarliestPossibleStartTime().equals(oldDateTime)) {
                                changed = true;
                            }
                            int endYear = mOnceDayEvent.getLatestPossibleEndTime().getYear();
                            curDayOfYear = DayOfYear.fromDateTime(mOnceDayEvent.getLatestPossibleEndTime());
                            while (!days1.contains(curDayOfYear)) {
                                curDayOfYear = curDayOfYear.plusDays(-1);
                                if (curDayOfYear.equals(DayOfYear.endOfYear)) {
                                    endYear--;
                                }
                            }
                            oldDateTime = mOnceDayEvent.getLatestPossibleEndTime();
                            mOnceDayEvent.setLatestPossibleDate(curDayOfYear.toDateTime().withYear(endYear));
                            if (!mOnceDayEvent.getLatestPossibleEndTime().equals(oldDateTime)) {
                                changed = true;
                            }
                        }
                    }

                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                    if (!days1.containsAll(mYearlyDayEvent.getPossibleDays())) {
                        mYearlyDayEvent.getPossibleDays().retainAll(days1);
                        changed = true;
                    }
                    //TODO: change mSeason
                    if (mYearlyDayEvent.getPossibleDays().containsAll(days1)) {

                    }
                }
            }
        }
        return changed;
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = this.applyConstraint();
        if (this.mDayEvent.isMarkedForComplexEval() && (!this.mPeriodEvent.isMarkedForComplexEval())) {
            this.mPeriodEvent.setUpForComplexEval();
        }
        if ((!this.mDayEvent.isMarkedForComplexEval()) && (this.mPeriodEvent.isMarkedForComplexEval())) {
            this.mDayEvent.setUpForComplexEval();
        }
        if (this.mDayEvent.isMarkedForComplexEval() && this.mPeriodEvent.isMarkedForComplexEval()) {
            if (this.getType() == PeriodToDayType.SAME_AS_START) {
                SameTimeAsDayAcceptor startAcceptor = new SameTimeAsDayAcceptor();
                startAcceptor.add(this.mDayEvent);
                startAcceptor.add(this.mPeriodEvent.getStart());
                if (this.mPeriodEvent instanceof OncePeriodEvent) {
                    java.util.Iterator<DateTime> i = ((OncePeriodEvent) this.mPeriodEvent).getStartPossibleDays().iterator();
                    while (i.hasNext()) {
                        DateTime curDay = i.next();
                        if (!startAcceptor.accept(curDay)) {
                            i.remove();
                        } else if (!startAcceptor.accept2(curDay)) {
                            i.remove();
                        }
                    }
                } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                    java.util.Iterator<DayOfYear> i = ((YearlyPeriodEvent) this.mPeriodEvent).getStartPossibleDays().iterator();
                    while (i.hasNext()) {
                        DayOfYear curDay = i.next();
                        if (!startAcceptor.accept(curDay)) {
                            i.remove();
                        } else if (!startAcceptor.accept2(curDay)) {
                            i.remove();
                        }
                    }
                }
                if (this.mDayEvent instanceof OnceDayEvent) {
                    java.util.Iterator<DateTime> i = ((OnceDayEvent) this.mDayEvent).getPossibleDays().iterator();
                    while (i.hasNext()) {
                        DateTime curDay = i.next();
                        if (!startAcceptor.accept(curDay)) {
                            i.remove();
                        } else if (!startAcceptor.accept2(curDay)) {
                            i.remove();
                        }
                    }
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    java.util.Iterator<DayOfYear> i = ((YearlyDayEvent) this.mDayEvent).getPossibleDays().iterator();
                    while (i.hasNext()) {
                        DayOfYear curDay = i.next();
                        if (!startAcceptor.accept(curDay)) {
                            i.remove();
                        } else if (!startAcceptor.accept2(curDay)) {
                            i.remove();
                        }
                    }
                }
            } else if (this.getType() == PeriodToDayType.SAME_AS_END) {
                SameTimeAsDayAcceptor endAcceptor = new SameTimeAsDayAcceptor();
                endAcceptor.add(this.mDayEvent);
                endAcceptor.add(this.mPeriodEvent.getEnd());
                if (this.mPeriodEvent instanceof OncePeriodEvent) {
                    java.util.Iterator<DateTime> i = ((OncePeriodEvent) this.mPeriodEvent).getEndPossibleDays().iterator();
                    while (i.hasNext()) {
                        DateTime curDay = i.next();
                        if (!endAcceptor.accept(curDay)) {
                            i.remove();
                        } else if (!endAcceptor.accept2(curDay)) {
                            i.remove();
                        }
                    }
                } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                    java.util.Iterator<DayOfYear> i = ((YearlyPeriodEvent) this.mPeriodEvent).getEndPossibleDays().iterator();
                    while (i.hasNext()) {
                        DayOfYear curDay = i.next();
                        if (!endAcceptor.accept(curDay)) {
                            i.remove();
                        } else if (!endAcceptor.accept2(curDay)) {
                            i.remove();
                        }
                    }
                }
                if (this.mDayEvent instanceof OnceDayEvent) {
                    java.util.Iterator<DateTime> i = ((OnceDayEvent) this.mDayEvent).getPossibleDays().iterator();
                    while (i.hasNext()) {
                        DateTime curDay = i.next();
                        if (!endAcceptor.accept(curDay)) {
                            i.remove();
                        } else if (!endAcceptor.accept2(curDay)) {
                            i.remove();
                        }
                    }
                } else if (this.mDayEvent instanceof YearlyDayEvent) {
                    java.util.Iterator<DayOfYear> i = ((YearlyDayEvent) this.mDayEvent).getPossibleDays().iterator();
                    while (i.hasNext()) {
                        DayOfYear curDay = i.next();
                        if (!endAcceptor.accept(curDay)) {
                            i.remove();
                        } else if (!endAcceptor.accept2(curDay)) {
                            i.remove();
                        }
                    }
                }
            } else if (this.getType() == PeriodToDayType.MIDDLE) {
                if (this.mPeriodEvent instanceof OncePeriodEvent) {
                    OncePeriodEvent mOncePeriodEvent = (OncePeriodEvent) this.mPeriodEvent;
                    org.joda.time.Interval mMiddleStartInterval = new org.joda.time.Interval(mOncePeriodEvent.getEarliestPossibleStartTime(), mOncePeriodEvent.getEarliestPossibleEndTime());
                    org.joda.time.Interval mMiddleEndInterval = new org.joda.time.Interval(mOncePeriodEvent.getLatestPossibleStartTime(), mOncePeriodEvent.getLatestPossibleEndTime());
                    int startDays = mMiddleStartInterval.toDuration().toStandardDays().getDays();
                    int endDays = mMiddleEndInterval.toDuration().toStandardDays().getDays();
                    DateTime MiddleStartTime = mOncePeriodEvent.getEarliestPossibleStartTime().plusDays(startDays / 2).withHourOfDay(1);
                    DateTime MiddleEndTime = mOncePeriodEvent.getLatestPossibleEndTime().minusDays(endDays / 2).withHourOfDay(23);
                    Set<DateTime> days1 = new java.util.HashSet<>(31);
                    for (DateTime curDay = MiddleStartTime; curDay.isBefore(MiddleEndTime); curDay = curDay.plusDays(1)) {
                        days1.add(curDay);
                    }
                    if (this.mDayEvent instanceof OnceDayEvent) {
                        OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                        if (!days1.containsAll(mOnceDayEvent.getPossibleDays())) {
                            mOnceDayEvent.getPossibleDays().retainAll(days1);
                        }
                        //Todo: change mOncePeriodEvent
                    } else if (this.mDayEvent instanceof YearlyDayEvent) {
                        YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                        Set<DayOfYear> days2 = new java.util.HashSet<>(31);
                        DateTime endDay = MiddleEndTime.withHourOfDay(23);
                        for (DateTime curDay = MiddleStartTime.withHourOfDay(1);
                                curDay.isBefore(endDay); curDay = curDay.plusDays(1)) {
                            days2.add(DayOfYear.fromDateTime(curDay));
                        }
                        if (days2.containsAll(mYearlyDayEvent.getPossibleDays())) {
                            mYearlyDayEvent.getPossibleDays().retainAll(days2);
                        }
                        //Todo: change mOncePeriodEvent
                    }
                } else if (this.mPeriodEvent instanceof YearlyPeriodEvent) {
                    YearlyPeriodEvent mSeason = (YearlyPeriodEvent) this.mPeriodEvent;
                    Set<DayOfYear> days1 = new java.util.HashSet<>(Math.max(mSeason.getStartPossibleDays().size(), mSeason.getEndPossibleDays().size()));
                    //todo:make this mutiThreaded
                    for (DayOfYear curDay1 : mSeason.getStartPossibleDays()) {
                        for (DayOfYear curDay2 : mSeason.getEndPossibleDays()) {
                            int diff = curDay1.diff(curDay2);
                            int move = diff / 2;
                            int rem = diff % 2;
                            days1.add(curDay1.plusDays(move));
                            if (rem == 1) {
                                days1.add(curDay1.plusDays(move + 1));
                            }
                        }
                    }
                    if (this.mDayEvent instanceof OnceDayEvent) {
                        OnceDayEvent mOnceDayEvent = (OnceDayEvent) this.mDayEvent;
                        Set<DayOfYear> days2 = new java.util.HashSet<>(31);
                        for (DateTime curDay : mOnceDayEvent.getPossibleDays()) {
                            days2.add(DayOfYear.fromDateTime(curDay));
                        }
                        //TODO: change mSeason
                        if (!days1.containsAll(days2)) {
                            Iterator<DateTime> onceEventIterator = mOnceDayEvent.getPossibleDays().iterator();
                            while (onceEventIterator.hasNext()){
                                DateTime curDay = onceEventIterator.next();
                                if (!days1.contains(DayOfYear.fromDateTime(curDay))){
                                    onceEventIterator.remove();
                                }
                            }
                        }
                    } else if (this.mDayEvent instanceof YearlyDayEvent) {
                        YearlyDayEvent mYearlyDayEvent = (YearlyDayEvent) this.mDayEvent;
                        if (days1.containsAll(mYearlyDayEvent.getPossibleDays())) {
                            mYearlyDayEvent.getPossibleDays().retainAll(days1);
                        }
                        //TODO: change mSeason
                        if (mYearlyDayEvent.getPossibleDays().containsAll(days1)) {

                        }
                    }
                }
            }
        }
        return changed;
    }
    
    @Override
    public boolean consistentWithConstraint(T inFirstPlacement, S inSecondPlacement) {
        if (this.mType == PeriodToDayType.SAME_AS_START){
            if (inFirstPlacement instanceof OncePeriodEvent.OncePeriodEventPlacement){
                OncePeriodEvent.OncePeriodEventPlacement inOncePeriodEventPlacement1 = (OncePeriodEvent.OncePeriodEventPlacement)inFirstPlacement;
                if (inFirstPlacement instanceof OnceDayEvent.OnceDayEventPlacement){
                    OnceDayEvent.OnceDayEventPlacement inOnceDayEventPlacement2 = (OnceDayEvent.OnceDayEventPlacement)inSecondPlacement;
                    return inOncePeriodEventPlacement1.startDay.equals(inOnceDayEventPlacement2.day);
                } else {
                    YearlyDayEvent.YearlyDayEventPlacement inYearlyDayEventPlacement2 = (YearlyDayEvent.YearlyDayEventPlacement)inSecondPlacement;
                    return DayOfYear.fromDateTime(inOncePeriodEventPlacement1.startDay).equals(inYearlyDayEventPlacement2.day);
                }
            } else {
                YearlyPeriodEvent.YearlyPeriodEventPlacement inSeasonPlacement1 = (YearlyPeriodEvent.YearlyPeriodEventPlacement)inFirstPlacement; 
                if (inFirstPlacement instanceof OnceDayEvent.OnceDayEventPlacement){
                    OnceDayEvent.OnceDayEventPlacement inOnceDayEventPlacement2 = (OnceDayEvent.OnceDayEventPlacement)inSecondPlacement;
                    return inSeasonPlacement1.startDay.equals(DayOfYear.fromDateTime(inOnceDayEventPlacement2.day));
                } else {
                    YearlyDayEvent.YearlyDayEventPlacement inYearlyDayEventPlacement2 = (YearlyDayEvent.YearlyDayEventPlacement)inSecondPlacement;
                    return inSeasonPlacement1.startDay.equals(inYearlyDayEventPlacement2.day);
                }
            }
        } else if (this.mType == PeriodToDayType.MIDDLE){
            if (inFirstPlacement instanceof OncePeriodEvent.OncePeriodEventPlacement){
                OncePeriodEvent.OncePeriodEventPlacement inOncePeriodEventPlacement1 = (OncePeriodEvent.OncePeriodEventPlacement)inFirstPlacement;
                long diff = new Interval(inOncePeriodEventPlacement1.startDay, inOncePeriodEventPlacement1.endDay).toDuration().getStandardDays();
                DateTime middle1 = inOncePeriodEventPlacement1.startDay.plusDays((int) (diff/2));
                DateTime middle2;
                if ((diff % 2) == 1) {
                    middle2 = inOncePeriodEventPlacement1.startDay.plusDays((int) ((diff/2) + 1));
                } else {
                    middle2 = middle1;
                }
                if (inFirstPlacement instanceof OnceDayEvent.OnceDayEventPlacement){
                    OnceDayEvent.OnceDayEventPlacement inOnceDayEventPlacement2 = (OnceDayEvent.OnceDayEventPlacement)inSecondPlacement;
                    return (inOnceDayEventPlacement2.day.equals(middle1) || inOnceDayEventPlacement2.day.equals(middle2));
                } else {
                    YearlyDayEvent.YearlyDayEventPlacement inYearlyDayEventPlacement2 = (YearlyDayEvent.YearlyDayEventPlacement)inSecondPlacement;
                    return (inYearlyDayEventPlacement2.day.equals(DayOfYear.fromDateTime(middle1)) || inYearlyDayEventPlacement2.day.equals(DayOfYear.fromDateTime(middle2)));
                }
            } else {
                YearlyPeriodEvent.YearlyPeriodEventPlacement inSeasonPlacement1 = (YearlyPeriodEvent.YearlyPeriodEventPlacement)inFirstPlacement;
                int diff = inSeasonPlacement1.startDay.diff(inSeasonPlacement1.endDay);
                DayOfYear middle1 = inSeasonPlacement1.startDay.plusDays(diff/2);
                DayOfYear middle2;
                if ((diff % 2) == 1) {
                    middle2 = inSeasonPlacement1.startDay.plusDays((diff/2) + 1);
                } else {
                    middle2 = middle1;
                }
                if (inFirstPlacement instanceof OnceDayEvent.OnceDayEventPlacement){
                    OnceDayEvent.OnceDayEventPlacement inOnceDayEventPlacement2 = (OnceDayEvent.OnceDayEventPlacement)inSecondPlacement;
                    DayOfYear testDay = DayOfYear.fromDateTime(inOnceDayEventPlacement2.day);
                    return (testDay.equals(middle1) || testDay.equals(middle2));
                } else {
                    YearlyDayEvent.YearlyDayEventPlacement inYearlyDayEventPlacement2 = (YearlyDayEvent.YearlyDayEventPlacement)inSecondPlacement;
                    return (inYearlyDayEventPlacement2.day.equals(middle1) || inYearlyDayEventPlacement2.day.equals(middle2));
                }
            }
        } else {
            if (inFirstPlacement instanceof OncePeriodEvent.OncePeriodEventPlacement){
                OncePeriodEvent.OncePeriodEventPlacement inOncePeriodEventPlacement1 = (OncePeriodEvent.OncePeriodEventPlacement)inFirstPlacement;
                if (inFirstPlacement instanceof OnceDayEvent.OnceDayEventPlacement){
                    OnceDayEvent.OnceDayEventPlacement inOnceDayEventPlacement2 = (OnceDayEvent.OnceDayEventPlacement)inSecondPlacement;
                    return inOncePeriodEventPlacement1.endDay.equals(inOnceDayEventPlacement2.day);
                } else {
                    YearlyDayEvent.YearlyDayEventPlacement inYearlyDayEventPlacement2 = (YearlyDayEvent.YearlyDayEventPlacement)inSecondPlacement;
                    return DayOfYear.fromDateTime(inOncePeriodEventPlacement1.endDay).equals(inYearlyDayEventPlacement2.day);
                }
            } else {
                YearlyPeriodEvent.YearlyPeriodEventPlacement inSeasonPlacement1 = (YearlyPeriodEvent.YearlyPeriodEventPlacement)inFirstPlacement; 
                if (inFirstPlacement instanceof OnceDayEvent.OnceDayEventPlacement){
                    OnceDayEvent.OnceDayEventPlacement inOnceDayEventPlacement2 = (OnceDayEvent.OnceDayEventPlacement)inSecondPlacement;
                    return inSeasonPlacement1.endDay.equals(DayOfYear.fromDateTime(inOnceDayEventPlacement2.day));
                } else {
                    YearlyDayEvent.YearlyDayEventPlacement inYearlyDayEventPlacement2 = (YearlyDayEvent.YearlyDayEventPlacement)inSecondPlacement;
                    return inSeasonPlacement1.endDay.equals(inYearlyDayEventPlacement2.day);
                }
            }
        }
    }

    @Override
    public Event[] increaseWhat(Placement inValues[]) {
        //ToDo: replace "Not supported yet." Exception with proper implemention
        return new Event[2];
    }

    /**
     * @return the mType
     */
    public PeriodToDayType getType() {
        return this.mType;
    }
    
    @Override
    public String toString() {
        String rString =  this.mDayEvent.getName();
        switch (this.mType){
            case SAME_AS_START:
                rString += " happens at the start of ";
                break;
            case SAME_AS_END:
                rString += " happens at the end of ";
                break;
            case MIDDLE:
                rString += " happens in the middle of ";
                break;
        }
        rString += this.mPeriodEvent.getName();
        return rString;
    }

    public static enum PeriodToDayType {

        SAME_AS_START, SAME_AS_END, MIDDLE
    }

    public static class PeriodToDayConstraintXMLWriter
            extends XMLWriterImp<PeriodToDayConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(PeriodToDayConstraint.class, new PeriodToDayConstraint.PeriodToDayConstraintXMLWriter());
        }

        @Override
        public Element writeElements(PeriodToDayConstraint ObjectToWrite) {
            Element newElement = new Element(MyLittePonyMaps.getFriendlyStringForConstraintClass(PeriodToDayConstraint.class));
            Element firstEpisodeElement = new Element("PeriodEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.mPeriodEvent,firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            Element secondEpisodeElement = new Element("DayEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.mDayEvent,secondEpisodeElement);
            newElement.addContent(secondEpisodeElement);
            Element kindElement = new Element("type");
            kindElement.setText(ObjectToWrite.getType().name());
            newElement.addContent(kindElement);
            return newElement;
        }

        @Override
        public PeriodToDayConstraint readElements(Element root) {
            Element firstEpisodeElement = root.getChild("PeriodEventByID");
            PeriodEvent<?,?> firstEpisode = (PeriodEvent<?,?>) EventImp.EventIDXMLWriter.instance.readElements(firstEpisodeElement);
            Element secondEpisodeElement = root.getChild("DayEventByID");
            DayEvent<?,?> secondEpisode = (DayEvent<?,?>) EventImp.EventIDXMLWriter.instance.readElements(secondEpisodeElement);
            Element kindElement = root.getChild("type");
            PeriodToDayConstraint.PeriodToDayType kind = PeriodToDayConstraint.PeriodToDayType.valueOf(kindElement.getTextNormalize());
            PeriodToDayConstraint newRelation;
            newRelation = new PeriodToDayConstraint(firstEpisode, secondEpisode, kind);
            return newRelation;
        }
    }
}
