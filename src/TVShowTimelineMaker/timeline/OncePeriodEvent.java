/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.ContainsDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.NonoverlapingDayAcceptor;
import TVShowTimelineMaker.timeline.OncePeriodEvent.OncePeriodEventPlacement;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

/**
 *
 * @author Steven Owens
 */
public class OncePeriodEvent extends EventImp implements DateTimePeriodEvent<OncePeriodEventPlacement> {

    private static final long serialVersionUID = 54L;
    private static final org.joda.time.Interval nullInterval = new org.joda.time.Interval(new DateTime(0, 1, 1, 23, 59, 59), new DateTime(0, 1, 2, 1, 0, 0));
    private static final Logger LOG = Logger.getLogger(OncePeriodEvent.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init2() {
        MyLittePonyMaps.putEvent("OncePeriodEvent", OncePeriodEvent.class);
    }

    private DateTime earliestPossibleDateForStart;
    private DateTime latestPossibleDateForStart;
    private DateTime earliestPossibleDateForEnd;
    private DateTime latestPossibleDateForEnd;
    private int minDuration;
    private int maxDuration;
    private AndDayAcceptor startAndDayAcceptor;
    private AndDayAcceptor endAndDayAcceptor;
    protected final NavigableSet<DateTime> startPossibleDays = new java.util.TreeSet<>();;
    protected final NavigableSet<DateTime> endPossibleDays = new java.util.TreeSet<>();;

    private final ContainsDayAcceptor mContainsDayAcceptor = new ContainsDayAcceptor(this);
    private final NonoverlapingDayAcceptor mNonoverlapingDayAcceptor = new NonoverlapingDayAcceptor(this);

    private Interval mLongInterval;
    private long longIntervalLastModifyed = -1000;

    private Interval mShortInterval;
    private long shortIntervalLastModifyed = -1000;

    private List<OncePeriodEventPlacement> mVaildPlacements = new java.util.ArrayList<>(1);

    public OncePeriodEvent() {
        this("New Event");
    }

    public OncePeriodEvent(String inName) {
        super(inName);
        this.startAndDayAcceptor = new AndDayAcceptor();
        this.endAndDayAcceptor = new AndDayAcceptor();
    }

    protected OncePeriodEvent(String inName, boolean tempEvent) {
        this(inName, tempEvent, new AndDayAcceptor(), new AndDayAcceptor());
    }

    protected OncePeriodEvent(String inName, boolean tempEvent, AndDayAcceptor inStartAndDayAcceptor, AndDayAcceptor inEndAndDayAcceptor) {
        super(inName, tempEvent);
        this.startAndDayAcceptor = inStartAndDayAcceptor;
        this.endAndDayAcceptor = inEndAndDayAcceptor;
    }

    private OncePeriodEvent(String inName, String inRootNamespace, int inID) {
        super(inName, inRootNamespace, inID);
        this.startAndDayAcceptor = new AndDayAcceptor();
        this.endAndDayAcceptor = new AndDayAcceptor();
    }
    
    private OncePeriodEvent(Element root) {
        super(root);
        XMLWriter<DateTime> DateTimeWriter = XMLWriterImp.getXMLWriter(DateTime.class);
            XMLWriter<AndDayAcceptor> AndDayAcceptorWriter = XMLWriterImp.getXMLWriter(AndDayAcceptor.class);
            Element earliestPossibleDateForStartElement = root.getChild("earliestPossibleDateForStart");
            DateTime newEarliestPossibleDateForStart = DateTimeWriter.readElements(earliestPossibleDateForStartElement.getChildren().get(0));
            Element latestPossibleDateForStartElement = root.getChild("latestPossibleDateForStart");
            DateTime newLatestPossibleDateForStart = DateTimeWriter.readElements(latestPossibleDateForStartElement.getChildren().get(0));
            Element earliestPossibleDateForEndElement = root.getChild("earliestPossibleDateForEnd");
            DateTime newEarliestPossibleDateForEnd = DateTimeWriter.readElements(earliestPossibleDateForEndElement.getChildren().get(0));
            Element latestPossibleDateForEndElement = root.getChild("latestPossibleDateForEnd");
            DateTime newLatestPossibleDateForEnd = DateTimeWriter.readElements(latestPossibleDateForEndElement.getChildren().get(0));
            Element minDurationElement = root.getChild("minDuration");
            int newMinDuration = java.lang.Integer.parseInt(minDurationElement.getTextNormalize());
            Element maxDurationElement = root.getChild("maxDuration");
            int newMaxDuration = java.lang.Integer.parseInt(maxDurationElement.getTextNormalize());
            earliestPossibleDateForStart = newEarliestPossibleDateForStart;
            latestPossibleDateForStart = newLatestPossibleDateForStart;
            earliestPossibleDateForEnd = newEarliestPossibleDateForEnd;
            latestPossibleDateForEnd = newLatestPossibleDateForEnd;
            minDuration = newMinDuration;
            maxDuration = newMaxDuration;
    }

    @Override
    public final void reset() {
        super.reset();
        this.earliestPossibleDateForStart = new DateTime(-1 * Main.YearRange, 1, 1, 10, 0);
        this.latestPossibleDateForStart = new DateTime(Main.YearRange, 12, 31, 13, 59);
        this.earliestPossibleDateForEnd = new DateTime(-1 * Main.YearRange, 1, 1, 11, 0);
        this.latestPossibleDateForEnd = new DateTime(Main.YearRange, 12, 31, 14, 59);
        this.setMinDuration(0);
        this.setMaxDuration(java.lang.Integer.MAX_VALUE);
        if (this.startPossibleDays != null) {
            this.startPossibleDays.clear();
            this.endPossibleDays.clear();
        }
        if (this.mVaildPlacements != null) {
            this.mVaildPlacements.clear();
        }
    }

    public void addPossibleDays() {
        this.startPossibleDays.clear();
        DateTime endDate = this.getLatestPossibleStartTime().withHourOfDay(23);
        //BigO(endDate - eventToPlace.getEarliestPossibleDate())
        for (DateTime curDate = this.getEarliestPossibleStartTime().withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0); curDate.isBefore(endDate); curDate = curDate.plusDays(1)) {
            if (this.startAndDayAcceptor.accept(curDate)) {
                this.startPossibleDays.add(curDate);
            }
        }
        this.endPossibleDays.clear();
        endDate = this.getLatestPossibleEndTime().withHourOfDay(23);
        //BigO(endDate - eventToPlace.getEarliestPossibleDate())
        for (DateTime curDate = this.getEarliestPossibleEndTime().withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0); curDate.isBefore(endDate); curDate = curDate.plusDays(1)) {
            if (this.endAndDayAcceptor.accept(curDate)) {
                this.endPossibleDays.add(curDate);
            }
        }
    }

    @Override
    public void setUpForComplexEval() {
        this.addPossibleDays();
        this.mVaildPlacements = this.getPlacements();
        super.setUpForComplexEval();
    }

    @Override
    public void normalize() {
        if (this.isValid()) {
            if (this.earliestPossibleDateForStart.isBefore(this.earliestPossibleDateForEnd)) {
                int diffStarts = new Interval(this.earliestPossibleDateForStart, this.earliestPossibleDateForEnd).toPeriod(org.joda.time.PeriodType.days()).getDays();
                if (diffStarts < this.getMinDuration()) {
                    this.setEarliestPossibleDateForEnd(this.earliestPossibleDateForStart.plusDays(this.getMinDuration()));
                } else if (diffStarts > this.getMaxDuration()) {
                    int addjust = diffStarts - this.getMaxDuration();
                    this.setEarliestPossibleDateForStart(this.earliestPossibleDateForStart.plusDays(addjust));
                }
                if (!this.earliestPossibleDateForStart.isBefore(this.latestPossibleDateForStart)) {
                    int a = 1 + 3;
                }
            }
            if (this.latestPossibleDateForStart.isBefore(this.latestPossibleDateForEnd)) {
                int diffEnds = new Interval(this.latestPossibleDateForStart, this.latestPossibleDateForEnd).toPeriod(org.joda.time.PeriodType.days()).getDays();
                if (diffEnds < this.getMinDuration()) {
                    this.setLatestPossibleDateForStart(this.latestPossibleDateForEnd.minusDays(this.getMinDuration()));
                } else if (diffEnds > this.getMaxDuration()) {
                    int addjust = diffEnds - this.getMaxDuration();
                    this.setLatestPossibleDateForEnd(this.latestPossibleDateForEnd.minusDays(addjust));
                }
                if (!this.earliestPossibleDateForStart.isBefore(this.latestPossibleDateForEnd)) {
                    int a = 1 + 1;
                }
            }
            if (this.latestPossibleDateForStart.isBefore(this.earliestPossibleDateForEnd)) {
                long shortestTimeLong = new Interval(this.latestPossibleDateForStart, this.earliestPossibleDateForEnd).toDuration().getStandardDays();
                int shortestTime = (int)Math.min(shortestTimeLong, java.lang.Integer.MAX_VALUE);
                if (shortestTime > this.minDuration) {
                    this.setMinDuration(shortestTime);
                }
            }
            //todo: remove. debug
            if (!this.earliestPossibleDateForStart.isBefore(this.latestPossibleDateForEnd)) {
                int a = 1 + 1;
            }
            //end debug
            int longestTime = new Interval(this.earliestPossibleDateForStart.withHourOfDay(1), this.latestPossibleDateForEnd.withHourOfDay(23)).toPeriod(org.joda.time.PeriodType.days()).getDays();
            if (longestTime < this.maxDuration) {
                this.setMaxDuration(longestTime);
            }
            if (this.isMarkedForComplexEval()) {
                if (this.startPossibleDays.first().isAfter(this.earliestPossibleDateForStart)) {
                    this.setEarliestPossibleDateForStart(this.startPossibleDays.first());
                }
                if (this.startPossibleDays.last().isBefore(this.latestPossibleDateForStart)) {
                    this.setLatestPossibleDateForStart(this.startPossibleDays.last());
                }
                if (this.endPossibleDays.first().isAfter(this.earliestPossibleDateForEnd)) {
                    this.setEarliestPossibleDateForEnd(this.endPossibleDays.first());
                }
                if (this.endPossibleDays.last().isBefore(this.latestPossibleDateForEnd)) {
                    this.setLatestPossibleDateForEnd(this.endPossibleDays.last());
                }
                while (this.startPossibleDays.first().isBefore(this.earliestPossibleDateForStart)) {
                    this.startPossibleDays.pollFirst();
                }
                while (this.startPossibleDays.last().isAfter(this.earliestPossibleDateForStart)) {
                    this.startPossibleDays.pollLast();
                }
                while (this.endPossibleDays.first().isBefore(this.earliestPossibleDateForEnd)) {
                    this.endPossibleDays.pollFirst();
                }
                while (this.endPossibleDays.last().isAfter(this.earliestPossibleDateForEnd)) {
                    this.endPossibleDays.pollLast();
                }
                Iterator<OncePeriodEventPlacement> PlacementIterator = this.mVaildPlacements.iterator();
                Collection<DateTime> retainStartDays = new java.util.HashSet<>(1);
                Collection<DateTime> retainEndDays = new java.util.HashSet<>(1);
                while (PlacementIterator.hasNext()) {
                    OncePeriodEventPlacement curPlacement = PlacementIterator.next();
                    if ((!this.startPossibleDays.contains(curPlacement.startDay))
                            || (!this.endPossibleDays.contains(curPlacement.endDay))) {
                        PlacementIterator.remove();
                    } else {
                        long curSeasonPlacementDuration = curPlacement.getDurationDays();
                        if ((curSeasonPlacementDuration < this.minDuration) || (curSeasonPlacementDuration > this.maxDuration)) {
                            PlacementIterator.remove();
                        } else {
                            retainStartDays.add(curPlacement.startDay);
                            retainEndDays.add(curPlacement.endDay);
                        }
                    }
                }
                this.startPossibleDays.retainAll(retainStartDays);
                this.endPossibleDays.retainAll(retainEndDays);
            } else {
                DateTime endDate = this.latestPossibleDateForStart.withHourOfDay(23);

                DateTime iTime = this.earliestPossibleDateForStart.withHourOfDay(11);
                while ((!this.startAndDayAcceptor.accept(iTime)) && (iTime.isBefore(endDate))) {
                    iTime = iTime.plusDays(1);
                }
                this.setEarliestPossibleDateForStart(iTime);

                DateTime startDate = this.earliestPossibleDateForStart.withHourOfDay(1);
                iTime = this.latestPossibleDateForStart.withHourOfDay(13);
                while ((!this.startAndDayAcceptor.accept(iTime)) && (iTime.isAfter(startDate))) {
                    iTime = iTime.minusDays(1);
                }
                this.setLatestPossibleDateForStart(iTime);
                endDate = this.latestPossibleDateForEnd.withHourOfDay(23);

                iTime = this.earliestPossibleDateForEnd.withHourOfDay(11);
                while ((!this.endAndDayAcceptor.accept(iTime)) && (iTime.isBefore(endDate))) {
                    iTime = iTime.plusDays(1);
                }
                this.setEarliestPossibleDateForEnd(iTime);

                startDate = this.earliestPossibleDateForEnd.withHourOfDay(1);
                iTime = this.latestPossibleDateForEnd.withHourOfDay(13);
                while ((!this.endAndDayAcceptor.accept(iTime)) && (iTime.isAfter(startDate))) {
                    iTime = iTime.minusDays(1);
                }
                this.setLatestPossibleDateForEnd(iTime);
            }
        }
    }

    @Override
    public boolean isValid() {
        return this.earliestPossibleDateForStart.isBefore(this.latestPossibleDateForStart)
                && this.earliestPossibleDateForEnd.isBefore(this.latestPossibleDateForEnd)
                && this.earliestPossibleDateForStart.isBefore(this.earliestPossibleDateForEnd)
                && this.latestPossibleDateForStart.isBefore(this.latestPossibleDateForEnd);
    }

    public void setEarliestPossibleDateForStart(DateTime earliestPossibleDate) {
        this.earliestPossibleDateForStart = earliestPossibleDate.withHourOfDay(10);
        this.lastmodifyed = System.currentTimeMillis();
    }

    /**
     * @param latestPossibleDate the latestPossibleDate to set
     */
    public void setLatestPossibleDateForStart(DateTime latestPossibleDate) {
        this.latestPossibleDateForStart = latestPossibleDate.withHourOfDay(13);
        this.lastmodifyed = System.currentTimeMillis();
    }

    public void setEarliestPossibleDateForEnd(DateTime earliestPossibleDate) {
        this.earliestPossibleDateForEnd = earliestPossibleDate.withHourOfDay(11);
        this.lastmodifyed = System.currentTimeMillis();
    }

    /**
     * @param latestPossibleDate
     */
    public void setLatestPossibleDateForEnd(DateTime latestPossibleDate) {
        this.latestPossibleDateForEnd = latestPossibleDate.withHourOfDay(14);
        this.lastmodifyed = System.currentTimeMillis();
    }

    @Override
    public DateTime getEarliestPossibleStartTime() {
        return this.earliestPossibleDateForStart;
    }

    @Override
    public DateTime getLatestPossibleEndTime() {
        return this.latestPossibleDateForEnd;
    }

    @Override
    public DateTime getLatestPossibleStartTime() {
        return this.latestPossibleDateForStart;
    }

    @Override
    public DateTime getEarliestPossibleEndTime() {
        return this.earliestPossibleDateForEnd;
    }

    @Override
    public boolean isAfter(DateTime inTime) {
        return this.earliestPossibleDateForStart.isAfter(inTime);
    }

    @Override
    public boolean isBefore(DateTime inTime) {
        return this.latestPossibleDateForEnd.isBefore(inTime);
    }

    @Override
    public boolean setAfter(DateTime inTime) {
        boolean changed = false;
        if (this.earliestPossibleDateForStart.isBefore(inTime)) {
            this.setEarliestPossibleDateForStart(inTime);
            changed = true;
        }
        if (this.earliestPossibleDateForEnd.isBefore(inTime)) {
            this.setEarliestPossibleDateForEnd(inTime);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean setBefore(DateTime inTime) {
        boolean changed = false;
        if (this.latestPossibleDateForStart.isAfter(inTime)) {
            this.setLatestPossibleDateForStart(inTime);
            changed = true;
        }
        if (this.latestPossibleDateForEnd.isAfter(inTime)) {
            this.setLatestPossibleDateForEnd(inTime);
            changed = true;
        }
        return changed;
    }

    /**
     * @return the minDuration
     */
    @Override
    public int getMinDuration() {
        return this.minDuration;
    }

    /**
     * @param minDuration the minDuration to set
     */
    @Override
    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    /**
     * @return the maxDuration
     */
    @Override
    public int getMaxDuration() {
        return this.maxDuration;
    }

    /**
     * @param maxDuration the maxDuration to set
     */
    @Override
    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    public OnceDayEvent getStart() {
        OnceDayEvent newOnceDayEvent = new OnceDayEvent("Start of " + this.getName(), true, this.startAndDayAcceptor);
        newOnceDayEvent.setEarliestPossibleDate(this.earliestPossibleDateForStart);
        newOnceDayEvent.setLatestPossibleDate(this.latestPossibleDateForStart);
        return newOnceDayEvent;
    }

    @Override
    public OnceDayEvent getEnd() {
        OnceDayEvent newOnceDayEvent = new OnceDayEvent("end of " + this.getName(), true, this.endAndDayAcceptor);
        newOnceDayEvent.setEarliestPossibleDate(this.earliestPossibleDateForEnd);
        newOnceDayEvent.setLatestPossibleDate(this.latestPossibleDateForEnd);
        return newOnceDayEvent;
    }

    @Override
    public boolean containsCould(DateTime inDateTime) {
        return this.earliestPossibleDateForStart.withHourOfDay(1).isBefore(inDateTime) && this.latestPossibleDateForEnd.withHourOfDay(23).isAfter(inDateTime);
    }

    @Override
    public boolean containsSure(DateTime inDateTime) {
        return this.toShortInterval().contains(inDateTime);
    }

    @Override
    public boolean containsCould(DayOfYear inDateTime) {
        int monthOfYear = inDateTime.getMonth();
        boolean rAccept;
        org.joda.time.Interval newInterval = this.toLongInterval();
        Period daysPeriod = newInterval.toPeriod(org.joda.time.PeriodType.days());
        if (daysPeriod.getDays() < 366) {
            boolean overYearBound = this.earliestPossibleDateForStart.getYear() < this.latestPossibleDateForEnd.getYear();
            int startMonth = this.earliestPossibleDateForStart.getMonthOfYear();
            int endMonth = this.latestPossibleDateForEnd.getMonthOfYear();
            int startDay = this.earliestPossibleDateForStart.getDayOfMonth();
            int endDay = this.latestPossibleDateForEnd.getDayOfMonth();
            if ((!overYearBound) && (monthOfYear > startMonth) && (monthOfYear < endMonth)) {
                rAccept = true;
            } else if ((overYearBound) && ((monthOfYear > startMonth) || (monthOfYear < endMonth))) {
                rAccept = true;
            } else if ((monthOfYear == startMonth) && (inDateTime.getDay() >= startDay)) {
                rAccept = true;
            } else if ((monthOfYear == endMonth) && (inDateTime.getDay() <= endDay)) {
                rAccept = true;
            } else {
                rAccept = false;
            }
        } else {
            rAccept = true;
        }
        return rAccept;
    }

    @Override
    public boolean containsSure(DayOfYear inDateTime) {
        int monthOfYear = inDateTime.getMonth();
        boolean rAccept;
        org.joda.time.Interval newInterval = this.toShortInterval();
        Period daysPeriod = newInterval.toPeriod(org.joda.time.PeriodType.days());
        if (daysPeriod.getDays() < 366) {
            boolean overYearBound = this.earliestPossibleDateForStart.getYear() < this.latestPossibleDateForEnd.getYear();
            int startMonth = this.earliestPossibleDateForStart.getMonthOfYear();
            int endMonth = this.latestPossibleDateForEnd.getMonthOfYear();
            int startDay = this.earliestPossibleDateForStart.getDayOfMonth();
            int endDay = this.latestPossibleDateForEnd.getDayOfMonth();
            if ((!overYearBound) && (monthOfYear > startMonth) && (monthOfYear < endMonth)) {
                rAccept = true;
            } else if ((overYearBound) && ((monthOfYear > startMonth) || (monthOfYear < endMonth))) {
                rAccept = true;
            } else if ((monthOfYear == startMonth) && (inDateTime.getDay() >= startDay)) {
                rAccept = true;
            } else if ((monthOfYear == endMonth) && (inDateTime.getDay() <= endDay)) {
                rAccept = true;
            } else {
                rAccept = false;
            }
        } else {
            rAccept = true;
        }
        return rAccept;
    }

    public org.joda.time.Interval toLongInterval() {
        if (this.longIntervalLastModifyed < this.lastmodifyed) {
            this.mLongInterval = new org.joda.time.Interval(this.earliestPossibleDateForStart, this.latestPossibleDateForEnd);
            this.longIntervalLastModifyed = this.lastmodifyed;
        }
        return this.mLongInterval;
    }

    public org.joda.time.Interval toShortInterval() {
        if (this.shortIntervalLastModifyed < this.lastmodifyed) {
            if (this.latestPossibleDateForStart.isBefore(this.earliestPossibleDateForEnd)) {
                this.mShortInterval = new org.joda.time.Interval(this.latestPossibleDateForStart.withHourOfDay(1), this.earliestPossibleDateForEnd.withHourOfDay(23));
            } else {
                this.mShortInterval = nullInterval;
            }
            this.shortIntervalLastModifyed = this.lastmodifyed;
        }
        return this.mShortInterval;
    }

    @Override
    public boolean abutsBefore(PeriodEvent<?,?> inPeriodEvent) {
        DateTime minEarliestPossibleDateForEnd = this.earliestPossibleDateForStart.minusDays(1).withHourOfDay(1);
        DateTime maxEarliestPossibleDateForEnd = minEarliestPossibleDateForEnd.withHourOfDay(23);
        DateTime minLatestPossibleDateForEnd = this.latestPossibleDateForStart.minusDays(1).withHourOfDay(1);
        DateTime maxLatestPossibleDateForEnd = minLatestPossibleDateForEnd.withHourOfDay(23);
        OncePeriodEvent inOncePeriodEvent;
        if (inPeriodEvent instanceof OncePeriodEvent) {
            inOncePeriodEvent = (OncePeriodEvent) inPeriodEvent;
            return minEarliestPossibleDateForEnd.isBefore(inOncePeriodEvent.earliestPossibleDateForEnd)
                    && maxEarliestPossibleDateForEnd.isAfter(inOncePeriodEvent.earliestPossibleDateForEnd)
                    && minLatestPossibleDateForEnd.isBefore(inOncePeriodEvent.latestPossibleDateForEnd)
                    && maxLatestPossibleDateForEnd.isAfter(inOncePeriodEvent.latestPossibleDateForEnd);
        } else if (inPeriodEvent instanceof YearlyPeriodEvent) {
            throw new UnsupportedOperationException("Seasons not supported yet");
        } else {
            throw new UnsupportedOperationException(inPeriodEvent.getClass().getCanonicalName() + "Not supported yet");
        }
    }

    @Override
    public boolean abutsAfter(PeriodEvent<?,?> inPeriodEvent) {
        DateTime minEarliestPossibleDateForStart = this.earliestPossibleDateForEnd.plusDays(1).withHourOfDay(1);
        DateTime maxEarliestPossibleDateForStart = minEarliestPossibleDateForStart.withHourOfDay(23);
        DateTime minLatestPossibleDateForStart = this.latestPossibleDateForEnd.plusDays(1).withHourOfDay(1);
        DateTime maxLatestPossibleDateForStart = minLatestPossibleDateForStart.withHourOfDay(23);
        OncePeriodEvent inOncePeriodEvent;
        if (inPeriodEvent instanceof OncePeriodEvent) {
            inOncePeriodEvent = (OncePeriodEvent) inPeriodEvent;
        } else if (inPeriodEvent instanceof YearlyPeriodEvent) {
            inOncePeriodEvent = ((YearlyPeriodEvent) inPeriodEvent).getInstance(this.latestPossibleDateForEnd.getYear());
        } else {
            throw new UnsupportedOperationException(inPeriodEvent.getClass().getCanonicalName() + "Not supported yet");
        }
        return minEarliestPossibleDateForStart.isBefore(inOncePeriodEvent.earliestPossibleDateForStart)
                && maxEarliestPossibleDateForStart.isAfter(inOncePeriodEvent.earliestPossibleDateForStart)
                && minLatestPossibleDateForStart.isBefore(inOncePeriodEvent.latestPossibleDateForStart)
                && maxLatestPossibleDateForStart.isAfter(inOncePeriodEvent.latestPossibleDateForStart);
    }

    @Override
    public boolean setAbutsBefore(PeriodEvent<?,?> inPeriodEvent) {
        boolean changed = false;
        DateTime minEarliestPossibleDateForEnd = this.earliestPossibleDateForStart.minusDays(1).withHourOfDay(1);
        DateTime maxLatestPossibleDateForEnd = this.latestPossibleDateForStart.minusDays(1).withHourOfDay(23);
        OncePeriodEvent inOncePeriodEvent;
        if (inPeriodEvent instanceof OncePeriodEvent) {
            inOncePeriodEvent = (OncePeriodEvent) inPeriodEvent;
            if (minEarliestPossibleDateForEnd.isAfter(inOncePeriodEvent.earliestPossibleDateForEnd)) {
                inOncePeriodEvent.setEarliestPossibleDateForEnd(minEarliestPossibleDateForEnd);
                changed = true;
            }
            if (maxLatestPossibleDateForEnd.isBefore(inOncePeriodEvent.latestPossibleDateForEnd)) {
                inOncePeriodEvent.setLatestPossibleDateForEnd(maxLatestPossibleDateForEnd);
                changed = true;
            }
        } else if (inPeriodEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent inSeason = (YearlyPeriodEvent) inPeriodEvent;
            DayOfYear lastDay = DayOfYear.fromDateTime(maxLatestPossibleDateForEnd);
            NavigableSet<DayOfYear> oendPossibleDays = inSeason.getEndPossibleDays();
            for (DayOfYear curDate = DayOfYear.fromDateTime(minEarliestPossibleDateForEnd); !curDate.equals(lastDay); curDate = curDate.plusDays(1)) {
                oendPossibleDays.remove(curDate);
            }
            throw new UnsupportedOperationException("Seasons not supported yet");
        } else {
            throw new UnsupportedOperationException(inPeriodEvent.getClass().getCanonicalName() + "Not supported yet");
        }
        DateTime minEarliestPossibleDateForStart = inOncePeriodEvent.earliestPossibleDateForEnd.plusDays(1).withHourOfDay(1);
        DateTime maxLatestPossibleDateForStart = inOncePeriodEvent.latestPossibleDateForEnd.plusDays(1).withHourOfDay(23);
        if (minEarliestPossibleDateForStart.isAfter(this.earliestPossibleDateForStart)) {
            this.setEarliestPossibleDateForStart(minEarliestPossibleDateForStart);
            changed = true;
        }
        if (maxLatestPossibleDateForStart.isBefore(this.latestPossibleDateForStart)) {
            this.setLatestPossibleDateForStart(maxLatestPossibleDateForStart);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean setAbutsAfter(PeriodEvent<?,?> inPeriodEvent) {
        boolean changed = false;
        DateTime minEarliestPossibleDateForStart = this.earliestPossibleDateForEnd.plusDays(1).withHourOfDay(1);
        DateTime maxLatestPossibleDateForStart = this.latestPossibleDateForEnd.plusDays(1).withHourOfDay(23);
        OncePeriodEvent inOncePeriodEvent;
        if (inPeriodEvent instanceof OncePeriodEvent) {
            inOncePeriodEvent = (OncePeriodEvent) inPeriodEvent;
            if (minEarliestPossibleDateForStart.isAfter(inOncePeriodEvent.earliestPossibleDateForStart)) {
                inOncePeriodEvent.setEarliestPossibleDateForStart(minEarliestPossibleDateForStart);
                changed = true;
            }
            if (maxLatestPossibleDateForStart.isBefore(inOncePeriodEvent.latestPossibleDateForStart)) {
                inOncePeriodEvent.setLatestPossibleDateForStart(maxLatestPossibleDateForStart);
                changed = true;
            }
        } else if (inPeriodEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent inSeason = (YearlyPeriodEvent) inPeriodEvent;
            inOncePeriodEvent = inSeason.getInstance(this.latestPossibleDateForEnd.getYear());
            DayOfYear lastDay = DayOfYear.fromDateTime(maxLatestPossibleDateForStart);
            NavigableSet<DayOfYear> ostartPossibleDays = inSeason.getStartPossibleDays();
            for (DayOfYear curDate = DayOfYear.fromDateTime(minEarliestPossibleDateForStart); !curDate.equals(lastDay); curDate = curDate.plusDays(1)) {
                ostartPossibleDays.remove(curDate);
            }
        } else {
            throw new UnsupportedOperationException(inPeriodEvent.getClass().getCanonicalName() + "Not supported yet");
        }
        DateTime minEarliestPossibleDateForEnd = inOncePeriodEvent.earliestPossibleDateForStart.minusDays(1).withHourOfDay(1);
        DateTime maxLatestPossibleDateForEnd = inOncePeriodEvent.latestPossibleDateForStart.minusDays(1).withHourOfDay(23);
        if (minEarliestPossibleDateForEnd.isAfter(this.earliestPossibleDateForEnd)) {
            this.setEarliestPossibleDateForEnd(minEarliestPossibleDateForEnd);
            changed = true;
        }
        if (maxLatestPossibleDateForEnd.isBefore(this.latestPossibleDateForEnd)) {
            this.setLatestPossibleDateForEnd(maxLatestPossibleDateForEnd);
            changed = true;
        }
        return changed;
    }

    @Override
    public int size() {
        //TODO: refine
        int tSize;
        if (this.isMarkedForComplexEval()) {
            tSize = this.endPossibleDays.size() * this.startPossibleDays.size();
        } else {
            org.joda.time.Interval startInterval = new org.joda.time.Interval(this.earliestPossibleDateForStart, this.latestPossibleDateForStart);
            Period startPeriod = startInterval.toPeriod(org.joda.time.PeriodType.days());
            org.joda.time.Interval endInterval = new org.joda.time.Interval(this.earliestPossibleDateForEnd, this.latestPossibleDateForEnd);
            Period endPeriod = endInterval.toPeriod(org.joda.time.PeriodType.days());
            tSize = (startPeriod.getDays() + 1) * (endPeriod.getDays() + 1);
        }
        return tSize;
    }

    @Override
    public double sizeAdj() {
        double rValue = this.size();
        if (!this.isMarkedForComplexEval()) {
            rValue *= this.startAndDayAcceptor.divFactor() * this.endAndDayAcceptor.divFactor();
        }
        return rValue;
    }

    @Override
    public String toTimeFrameString() {
        return "between " + this.earliestPossibleDateForStart.toString() + " and " + this.latestPossibleDateForEnd.toString();
    }

    @Override
    public AndDayAcceptor getStartDayAcceptor() {
        return this.startAndDayAcceptor;
    }

    @Override
    public AndDayAcceptor getEndDayAcceptor() {
        return this.endAndDayAcceptor;
    }

    /**
     * @return the startPossibleDays
     */
    @Override
    public NavigableSet<DateTime> getStartPossibleDays() {
        return this.startPossibleDays;
    }

    /**
     * @return the endPossibleDays
     */
    @Override
    public NavigableSet<DateTime> getEndPossibleDays() {
        return this.endPossibleDays;
    }

    /**
     * @return the mContainsDayAcceptor
     */
    @Override
    public ContainsDayAcceptor getContainsDayAcceptor() {
        return this.mContainsDayAcceptor;
    }

    /**
     * @return the mNonoverlapingDayAcceptor
     */
    @Override
    public NonoverlapingDayAcceptor getNonoverlapingDayAcceptor() {
        return this.mNonoverlapingDayAcceptor;
    }

    @Override
    public String toLongString() {
        return this.getLeastSignificantIDPart() + ":" + this.getName() + ":"
                + this.earliestPossibleDateForStart.toString() + "\\" + this.latestPossibleDateForStart + ":"
                + this.earliestPossibleDateForEnd.toString() + "\\" + this.latestPossibleDateForEnd;
    }

    @Override
    public List<OncePeriodEventPlacement> getPlacements() {
        if (this.isMarkedForComplexEval()) {
            return this.mVaildPlacements;
        } else {
            List<OncePeriodEventPlacement> vaildPlacements = new java.util.ArrayList<>(Math.max(this.startPossibleDays.size(), this.endPossibleDays.size()));
            //Todo:make this mutithreaded
            vaildPlacements.addAll(startPossibleDays.parallelStream()
                    .map((DateTime curStartDay) -> endPossibleDays.parallelStream()
                            .filter((DateTime curEndDay) -> curStartDay.isBefore(curEndDay))
                            .map((DateTime curEndDay) -> new OncePeriodEventPlacement(curStartDay, curEndDay))
                            .collect(java.util.stream.Collectors.toList()))
                    .collect(java.util.ArrayList<OncePeriodEventPlacement>::new, List<OncePeriodEventPlacement>::addAll, List<OncePeriodEventPlacement>::addAll));
            return vaildPlacements;
        }
    }
    
    @Override
    public List<OncePeriodEventPlacement> getSuggestedPlacements(){
        return java.util.Collections.EMPTY_LIST;
    }

    @Override
    public void setViaPlacement(OncePeriodEventPlacement inPlacement) {
        this.setEarliestPossibleDateForStart(inPlacement.startDay);
        this.setLatestPossibleDateForStart(inPlacement.startDay);
        this.setEarliestPossibleDateForEnd(inPlacement.endDay);
        this.setLatestPossibleDateForEnd(inPlacement.endDay);
        this.startPossibleDays.clear();
        this.endPossibleDays.clear();
        this.mVaildPlacements.clear();
        this.startPossibleDays.add(inPlacement.startDay);
        this.endPossibleDays.add(inPlacement.endDay);
        this.mVaildPlacements.add(inPlacement);
    }
    
    @Override
    public void setTo(Placement inPlacement){
        if (inPlacement instanceof OncePeriodEventPlacement){
            this.setViaPlacement((OncePeriodEventPlacement)inPlacement);
        }
    }

    @Override
    public void setTo(Collection<? extends  Placement<?>> inPlacements) {
        this.mVaildPlacements.clear();
        for (Placement curPlacement : inPlacements) {
            this.mVaildPlacements.add((OncePeriodEventPlacement) curPlacement);
        }
    }

    public static class OncePeriodEventPlacement implements PeriodEvent.PeriodPlacement<DateTime> {

        public final DateTime startDay;
        public final DateTime endDay;

        public OncePeriodEventPlacement(DateTime inStartDay, DateTime inEndDay) {
            this.startDay = inStartDay;
            this.endDay = inEndDay;
        }

        private long getDurationDays() {
            return new Interval(this.startDay, this.endDay).toDuration().getStandardDays();
        }

        @Override
        public DateTime getStartDay() {
            return startDay;
        }

        @Override
        public DateTime getEndDay() {
            return endDay;
        }
    }

    public static class PeriodEventXMLWriter
            extends EventXMLWriter<OncePeriodEvent> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(OncePeriodEvent.class, new PeriodEventXMLWriter());
        }

        @Override
        public Element writeElements(OncePeriodEvent ObjectToWrite) {
            Element newElement = new Element("PeriodEvent");
            this.writeElements(ObjectToWrite, newElement);
            return newElement;
        }
            
        @Override
        public void writeElements(OncePeriodEvent ObjectToWrite, Element newElement) {
            super.writeElements(ObjectToWrite, newElement);
            XMLWriter<DateTime> DateTimeWriter = XMLWriterImp.getXMLWriter(DateTime.class);
            Element earliestPossibleDateForStartElement = new Element("earliestPossibleDateForStart");
            earliestPossibleDateForStartElement.setContent(DateTimeWriter.writeElements(ObjectToWrite.earliestPossibleDateForStart));
            newElement.addContent(earliestPossibleDateForStartElement);
            Element latestPossibleDateForStartElement = new Element("latestPossibleDateForStart");
            latestPossibleDateForStartElement.setContent(DateTimeWriter.writeElements(ObjectToWrite.latestPossibleDateForStart));
            newElement.addContent(latestPossibleDateForStartElement);
            Element earliestPossibleDateForEndElement = new Element("earliestPossibleDateForEnd");
            earliestPossibleDateForEndElement.setContent(DateTimeWriter.writeElements(ObjectToWrite.earliestPossibleDateForEnd));
            newElement.addContent(earliestPossibleDateForEndElement);
            Element latestPossibleDateForEndElement = new Element("latestPossibleDateForEnd");
            latestPossibleDateForEndElement.setContent(DateTimeWriter.writeElements(ObjectToWrite.latestPossibleDateForEnd));
            newElement.addContent(latestPossibleDateForEndElement);
            Element minDurationElement = new Element("minDuration");
            minDurationElement.setText(java.lang.Integer.toString(ObjectToWrite.minDuration));
            newElement.addContent(minDurationElement);
            Element maxDurationElement = new Element("maxDuration");
            maxDurationElement.setText(java.lang.Integer.toString(ObjectToWrite.maxDuration));
            newElement.addContent(maxDurationElement);
        }

        @Override
        public OncePeriodEvent readElements(org.jdom2.Element root) {
            return new OncePeriodEvent(root);
        }

    }
}
