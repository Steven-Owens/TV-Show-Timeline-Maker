/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OnceEvent;
import TVShowTimelineMaker.timeline.YearlyDayEvent;
import TVShowTimelineMaker.util.DayOfYear;
import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;

/**
 *
 * @author Steven Owens
 */
public class SameTimeAsDayAcceptor implements DayAcceptor, Set<DayEvent<?,?>> {

    private static final Days yearOfDays = Days.days(365);
    private static final DateTime resetStartTime = new DateTime(0, 1, 1, 1, 0);
    private static final DateTime resetEndTime = new DateTime(1, 12, 31, 23, 59);
    private static final Logger LOG = Logger.getLogger(SameTimeAsDayAcceptor.class.getName());

    Set<DayEvent<?,?>> Events;
    private long lastmodifyed;

    private int startMonth;
    private int endMonth;
    private int startDay;
    private int endDay;
    private boolean overYearBound;

    public SameTimeAsDayAcceptor() {
        this.Events = new java.util.HashSet<>(2);
        this.lastmodifyed = -1;
    }
    
     @Override
    public double divFactor() {
        for (DayEvent<?,?> curEvent : this.Events) {
            if (curEvent.getLastmodifyed() > this.lastmodifyed) {
                this.build();
                break;
            }
        }
        DateTime startDate = new DateTime(0, this.startMonth, this.startDay,0,0);
        DateTime endDate;
        if (this.overYearBound) {
            endDate = new DateTime(1, this.endMonth, this.endDay,0,0);
        } else {
            endDate = new DateTime(0, this.endMonth, this.endDay,0,0);
        }
        if (startDate.isBefore(endDate)){
            long standardDays = new org.joda.time.Interval(startDate,endDate).toDuration().getStandardDays();
        return standardDays/365.0;
        } else {
            return 1/365.0;
        }
    }

    @Override
    public boolean accept(DateTime inDateTime) {
        for (DayEvent<?,?> curEvent : this.Events) {
            if (curEvent.getLastmodifyed() > this.lastmodifyed) {
                this.build();
                break;
            }
        }
        int monthOfYear = inDateTime.get(org.joda.time.DateTimeFieldType.monthOfYear());
        boolean rAccept;
        if ((!this.overYearBound) && (monthOfYear > this.startMonth) && (monthOfYear < this.endMonth)) {
            rAccept = true;
        } else if ((this.overYearBound) && ((monthOfYear > this.startMonth) || (monthOfYear < this.endMonth))) {
            rAccept = true;
        } else if ((monthOfYear == this.startMonth) && (monthOfYear == this.endMonth)) {
            rAccept = (inDateTime.get(org.joda.time.DateTimeFieldType.dayOfMonth()) >= this.startDay)
                    && (inDateTime.get(org.joda.time.DateTimeFieldType.dayOfMonth()) <= this.endDay);
        } else if ((monthOfYear == this.startMonth) && (inDateTime.get(org.joda.time.DateTimeFieldType.dayOfMonth()) >= this.startDay)) {
            rAccept = true;
        } else if ((monthOfYear == this.endMonth) && (inDateTime.get(org.joda.time.DateTimeFieldType.dayOfMonth()) <= this.endDay)) {
            rAccept = true;
        } else {
            rAccept = false;
        }
        return rAccept;
    }

    @Override
    public boolean accept(DayOfYear inDateTime) {
        for (DayEvent<?,?> curEvent : this.Events) {
            if (curEvent.getLastmodifyed() > this.lastmodifyed) {
                this.build();
                break;
            }
        }
        int monthOfYear = inDateTime.getMonth();
        boolean rAccept;
        if ((!this.overYearBound) && (monthOfYear > this.startMonth) && (monthOfYear < this.endMonth)) {
            rAccept = true;
        } else if ((this.overYearBound) && ((monthOfYear > this.startMonth) || (monthOfYear < this.endMonth))) {
            rAccept = true;
        } else if ((monthOfYear == this.startMonth) && (monthOfYear == this.endMonth)) {
            rAccept = (inDateTime.getDay() >= this.startDay)
                    && (inDateTime.getDay() <= this.endDay);
        } else if ((monthOfYear == this.startMonth) && (inDateTime.getDay() >= this.startDay)) {
            rAccept = true;
        } else if ((monthOfYear == this.endMonth) && (inDateTime.getDay() <= this.endDay)) {
            rAccept = true;
        } else {
            rAccept = false;
        }
        return rAccept;
    }

    public boolean accept2(DateTime inDateTime) {
        boolean rValue = true;
        for (DayEvent<?,?> curEvent : this.Events) {
            boolean foundMatch = false;
            if (curEvent instanceof OnceDayEvent) {
                NavigableSet<DateTime> curPossibleDays = ((OnceDayEvent) curEvent).getPossibleDays();
                for (DateTime curDateTime : curPossibleDays) {
                    if ((inDateTime.monthOfYear().get() == curDateTime.monthOfYear().get())
                            && (inDateTime.dayOfMonth().get() == curDateTime.dayOfMonth().get())) {
                        foundMatch = true;
                        break;
                    }
                }
            } else if (curEvent instanceof YearlyDayEvent) {
                NavigableSet<DayOfYear> curPossibleDays = ((YearlyDayEvent) curEvent).getPossibleDays();
                for (DayOfYear curDateTime : curPossibleDays) {
                    if ((inDateTime.monthOfYear().get() == curDateTime.getMonth())
                            && (inDateTime.dayOfMonth().get() == curDateTime.getDay())) {
                        foundMatch = true;
                        break;
                    }
                }
            }
            if (!foundMatch) {
                rValue = false;
                break;
            }
        }
        return rValue;
    }

    public boolean accept2(DayOfYear inDateTime) {
        boolean rValue = true;
        for (DayEvent<?,?> curEvent : this.Events) {
            boolean foundMatch = false;
            if (curEvent instanceof OnceDayEvent) {
                NavigableSet<DateTime> curPossibleDays = ((OnceDayEvent) curEvent).getPossibleDays();
                for (DateTime curDateTime : curPossibleDays) {
                    if ((inDateTime.getMonth() == curDateTime.monthOfYear().get())
                            && (inDateTime.getDay() == curDateTime.dayOfMonth().get())) {
                        foundMatch = true;
                        break;
                    }
                }

            } else if (curEvent instanceof YearlyDayEvent) {
                NavigableSet<DayOfYear> curPossibleDays = ((YearlyDayEvent) curEvent).getPossibleDays();
                for (DayOfYear curDateTime : curPossibleDays) {
                    if ((inDateTime.getMonth() == curDateTime.getMonth())
                            && (inDateTime.getDay() == curDateTime.getDay())) {
                        foundMatch = true;
                        break;
                    }
                }
            }
            if (!foundMatch) {
                rValue = false;
                break;
            }
        }
        return rValue;
    }

    public void build() {
        DateTime buildFromStart = resetStartTime;
        DateTime buildFromEnd = resetEndTime;
        for (DayEvent<?,?> curEvent : this.Events) {
            if (curEvent.isValid()) {
                if (curEvent instanceof OnceEvent) {
                    OnceEvent<?> curOnceEvent = (OnceEvent<?>) curEvent;
                    Interval curEventInterval = new Interval(curOnceEvent.getEarliestPossibleStartTime(), curOnceEvent.getLatestPossibleEndTime());
                    if (curEventInterval.toDuration().isShorterThan(yearOfDays.toStandardDuration())) {
                        int startYear = curOnceEvent.getEarliestPossibleStartTime().get(org.joda.time.DateTimeFieldType.year());
                        DateTime newStart = curOnceEvent.getEarliestPossibleStartTime().minusYears(startYear);
                        DateTime newEnd = curOnceEvent.getLatestPossibleEndTime().minusYears(startYear);
                        if (newStart.isAfter(buildFromStart)) {
                            buildFromStart = newStart;
                        }
                        if (newEnd.isBefore(buildFromEnd)) {
                            buildFromEnd = newEnd;
                        }
                    }
                } else if (curEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent curYearlyDayEvent = (YearlyDayEvent) curEvent;
                }
            }
        }
        this.overYearBound = buildFromEnd.get(org.joda.time.DateTimeFieldType.year()) > 0;
        this.startMonth = buildFromStart.get(org.joda.time.DateTimeFieldType.monthOfYear());
        this.startDay = buildFromStart.get(org.joda.time.DateTimeFieldType.dayOfMonth());
        this.endMonth = buildFromEnd.get(org.joda.time.DateTimeFieldType.monthOfYear());
        this.endDay = buildFromEnd.get(org.joda.time.DateTimeFieldType.dayOfMonth());
        this.lastmodifyed = System.currentTimeMillis();
    }

    @Override
    public int size() {
        return this.Events.size();
    }

    @Override
    public boolean isEmpty() {
        return this.Events.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.Events.contains(o);
    }

    @Override
    public Iterator<DayEvent<?,?>> iterator() {
        return this.Events.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.Events.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.Events.toArray(a);
    }

    @Override
    public boolean add(DayEvent<?,?> e) {
        this.lastmodifyed = -1; //the cached data is now invalid
        return this.Events.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.Events.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.Events.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends DayEvent<?,?>> c) {
        return this.Events.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.Events.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.Events.removeAll(c);
    }

    @Override
    public void clear() {
        this.Events.clear();
    }
}
