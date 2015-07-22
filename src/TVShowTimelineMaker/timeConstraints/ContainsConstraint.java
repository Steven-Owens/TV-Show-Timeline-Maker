/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.AbstractTwoEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.TimeConstraintImp;
import TVShowTimelineMaker.timeConstraints.interfaces.TwoEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.DayAcceptorTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.AutomaticMetaData;
import TVShowTimelineMaker.timeConstraints.core.ConstraintMetaDataImp;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.DayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.NonoverlapingDayAcceptor;
import TVShowTimelineMaker.timeline.DateTimeDayEvent;
import TVShowTimelineMaker.timeline.DateTimePeriodEvent;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.DayOfYearDayEvent;
import TVShowTimelineMaker.timeline.DayOfYearPeriodEvent;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OnceEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.timeline.YearlyDayEvent;
import TVShowTimelineMaker.timeline.YearlyEvent;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.Primary;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.awt.Component;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Element;
import org.joda.time.DateTime;

public class ContainsConstraint<T extends PeriodEvent.PeriodPlacement<?>,S extends Placement<?>> extends AbstractTwoEventTimeConstraint<T,S> implements DayAcceptorTimeConstraint {

    private static final long serialVersionUID = 1204L;
    private static final Logger LOG = Logger.getLogger(ContainsConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("ContainsConstraint", ContainsConstraint.class);
        Map<String,ContainsType> myVerbMapping = new java.util.HashMap<>(2);
        myVerbMapping.put("Contains", ContainsType.CONTAINS);
        myVerbMapping.put("does not overlap with", ContainsType.NONOVERLAPING);
        MyLittePonyMaps.addTimeConstraintMetaData(ContainsConstraint.class,AutomaticMetaData.constuctFromClass(ContainsConstraint.class, myVerbMapping));
    }

    public static boolean contains(PeriodEvent<?,?> inContainsEvent, Event inContainedEvent) {
        if (inContainedEvent instanceof PeriodEvent) {
            PeriodEvent<?,?> inPeriodEvent = (PeriodEvent<?,?>) inContainedEvent;
            if (inContainsEvent.getMinDuration() < inPeriodEvent.getMinDuration()) {
                return false;
            }
            if (inPeriodEvent.getMaxDuration() > inContainsEvent.getMaxDuration()) {
                return false;
            }
        }
        if (inContainsEvent instanceof OncePeriodEvent) {
            OncePeriodEvent inContainsOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
            if (inContainedEvent instanceof OnceEvent) {
                OnceEvent inOnceEvent = (OnceEvent) inContainedEvent;
                if (!inOnceEvent.isAfter(inContainsOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(1))) {
                    return false;
                }
                if (!inOnceEvent.isBefore(inContainsOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(23))) {
                    return false;
                }
                if (inContainsOncePeriodEvent.getEarliestPossibleEndTime().isBefore(inOnceEvent.getEarliestPossibleStartTime().withHourOfDay(1))) {
                    return false;
                }
                if (inContainsOncePeriodEvent.getLatestPossibleStartTime().isAfter(inOnceEvent.getLatestPossibleEndTime().withHourOfDay(23))) {
                    return false;
                }
                if (inOnceEvent.getEarliestPossibleStartTime().isBefore(inContainsOncePeriodEvent.getLatestPossibleStartTime())) {
                    return false;
                }
                if (inOnceEvent.getLatestPossibleEndTime().isAfter(inContainsOncePeriodEvent.getEarliestPossibleEndTime())) {
                    return false;
                }
            } else if (inContainedEvent instanceof YearlyEvent) {
                YearlyEvent inYearlyEvent = (YearlyEvent) inContainedEvent;
                int endyear = inContainsOncePeriodEvent.getEarliestPossibleEndTime().getYear();
                int startyear = inContainsOncePeriodEvent.getLatestPossibleStartTime().getYear();
                OnceEvent lateInOnceEvent = inYearlyEvent.getInstance(endyear);
                OnceEvent eralyInOnceEvent = inYearlyEvent.getInstance(startyear);
                if (inContainsOncePeriodEvent.getEarliestPossibleEndTime().isBefore(lateInOnceEvent.getEarliestPossibleStartTime())) {
                    return false;
                }
                if (inContainsOncePeriodEvent.getLatestPossibleStartTime().isAfter(eralyInOnceEvent.getLatestPossibleEndTime())) {
                    return false;
                }
            }
        } else if (inContainsEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent inContainsSeason = (YearlyPeriodEvent) inContainsEvent;
            if (inContainedEvent instanceof OncePeriodEvent) {
                OncePeriodEvent inOnceEvent = (OncePeriodEvent) inContainedEvent;
                int startyear = inOnceEvent.getEarliestPossibleStartTime().getYear();
                int endyear = inOnceEvent.getLatestPossibleEndTime().getYear();
                OnceEvent lateInOnceEvent = inContainsSeason.getInstance(endyear);
                OnceEvent eralyInOnceEvent = inContainsSeason.getInstance(startyear);
                if (inOnceEvent.getEarliestPossibleEndTime().isBefore(lateInOnceEvent.getEarliestPossibleStartTime())) {
                    return false;
                }
                if (inOnceEvent.getLatestPossibleStartTime().isAfter(eralyInOnceEvent.getLatestPossibleEndTime())) {
                    return false;
                }
            } else if (inContainedEvent instanceof YearlyDayEvent) {
                if (!inContainsSeason.getContainsCouldDays().containsAll(((YearlyDayEvent) inContainedEvent).getPossibleDays())) {
                    return false;
                }
            } else if (inContainedEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent inSeason = (YearlyPeriodEvent) inContainedEvent;
                if (!inContainsSeason.getContainsSureDays().containsAll(inSeason.getContainsSureDays())) {
                    return false;
                }
                if (!inContainsSeason.getContainsCouldDays().containsAll(inSeason.getContainsCouldDays())) {
                    return false;
                }
            } else if (inContainedEvent instanceof OnceDayEvent) {
                OnceDayEvent inOnceDayEvent = (OnceDayEvent) inContainedEvent;
                int startyear = inOnceDayEvent.getEarliestPossibleStartTime().getYear();
                int endyear = inOnceDayEvent.getLatestPossibleEndTime().getYear();
                OncePeriodEvent lateInOnceEvent = inContainsSeason.getInstance(endyear);
                OncePeriodEvent eralyInOnceEvent = inContainsSeason.getInstance(startyear);
                if (eralyInOnceEvent.getEarliestPossibleStartTime().isAfter(inOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1))) {
                    return false;
                }
                if (lateInOnceEvent.getLatestPossibleEndTime().isBefore(inOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean couldContain(PeriodEvent<?,?> inContainsEvent, Event inContainedEvent) {
        if (inContainedEvent instanceof PeriodEvent) {
            PeriodEvent<?,?> inPeriodEvent = (PeriodEvent<?,?>) inContainedEvent;
            if (inContainsEvent.getMaxDuration() < inPeriodEvent.getMinDuration()) {
                return false;
            }
        }
        if (inContainsEvent instanceof OncePeriodEvent) {
            OncePeriodEvent inContainsOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
            if (inContainedEvent instanceof OnceEvent) {
                OnceEvent inOnceEvent = (OnceEvent) inContainedEvent;
                if (!inOnceEvent.getLatestPossibleStartTime().isAfter(inContainsOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(1))) {
                    return false;
                }
                if (!inOnceEvent.getEarliestPossibleEndTime().isBefore(inContainsOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(23))) {
                    return false;
                }
                if (inContainsOncePeriodEvent.getLatestPossibleEndTime().isBefore(inOnceEvent.getEarliestPossibleStartTime().withHourOfDay(1))) {
                    return false;
                }
                if (inContainsOncePeriodEvent.getEarliestPossibleStartTime().isAfter(inOnceEvent.getLatestPossibleEndTime().withHourOfDay(23))) {
                    return false;
                }
            } else if (inContainedEvent instanceof YearlyEvent) {
                YearlyEvent inYearlyEvent = (YearlyEvent) inContainedEvent;
                int endyear = inContainsOncePeriodEvent.getEarliestPossibleEndTime().getYear();
                int startyear = inContainsOncePeriodEvent.getLatestPossibleStartTime().getYear();
                OnceEvent lateInOnceEvent = inYearlyEvent.getInstance(endyear);
                OnceEvent eralyInOnceEvent = inYearlyEvent.getInstance(startyear);
                if (inContainsOncePeriodEvent.getLatestPossibleEndTime().isBefore(lateInOnceEvent.getEarliestPossibleStartTime())) {
                    return false;
                }
                if (inContainsOncePeriodEvent.getEarliestPossibleStartTime().isAfter(eralyInOnceEvent.getLatestPossibleEndTime())) {
                    return false;
                }
            }
        } else if (inContainsEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent inContainsSeason = (YearlyPeriodEvent) inContainsEvent;
            if (inContainedEvent instanceof OncePeriodEvent) {
                OncePeriodEvent inOnceEvent = (OncePeriodEvent) inContainedEvent;
                int startyear = inOnceEvent.getEarliestPossibleStartTime().getYear();
                int endyear = inOnceEvent.getLatestPossibleEndTime().getYear();
                OnceEvent lateInOnceEvent = inContainsSeason.getInstance(endyear);
                OnceEvent eralyInOnceEvent = inContainsSeason.getInstance(startyear);
                if (inOnceEvent.getLatestPossibleEndTime().isBefore(lateInOnceEvent.getEarliestPossibleStartTime())) {
                    return false;
                }
                if (inOnceEvent.getEarliestPossibleStartTime().isAfter(eralyInOnceEvent.getLatestPossibleEndTime())) {
                    return false;
                }
            } else if (inContainedEvent instanceof YearlyDayEvent) {
                if (!inContainsSeason.getContainsCouldDays().containsAll(((YearlyDayEvent) inContainedEvent).getPossibleDays())) {
                    return false;
                }
            } else if (inContainedEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent inSeason = (YearlyPeriodEvent) inContainedEvent;
                if (!inContainsSeason.getContainsCouldDays().containsAll(inSeason.getContainsSureDays())) {
                    return false;
                }
            } else if (inContainedEvent instanceof OnceDayEvent) {
                OnceDayEvent inOnceDayEvent = (OnceDayEvent) inContainedEvent;
                int startyear = inOnceDayEvent.getEarliestPossibleStartTime().getYear();
                int endyear = inOnceDayEvent.getLatestPossibleEndTime().getYear();
                OncePeriodEvent lateInOnceEvent = inContainsSeason.getInstance(endyear);
                OncePeriodEvent eralyInOnceEvent = inContainsSeason.getInstance(startyear);
                if (eralyInOnceEvent.getLatestPossibleStartTime().isAfter(inOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1))) {
                    return false;
                }
                if (lateInOnceEvent.getEarliestPossibleEndTime().isBefore(inOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean couldOverlap(PeriodEvent<?,?> inContainsEvent, PeriodEvent<?,?> inContainedEvent) {
        boolean bothOnceEvents = true;
        if ((!(inContainsEvent instanceof OnceEvent)) || (!(inContainedEvent instanceof OnceEvent))) {
            bothOnceEvents = false;
        }

        if (bothOnceEvents) {
            DateTime startDate;
            DateTime endDate;
            if (inContainedEvent instanceof OncePeriodEvent) {
                OncePeriodEvent inOncePeriodEvent = (OncePeriodEvent) inContainedEvent;
                startDate = inOncePeriodEvent.getEarliestPossibleStartTime().minusDays(1).withHourOfDay(11);
                endDate = inOncePeriodEvent.getLatestPossibleEndTime().plusDays(1).withHourOfDay(13);
            } else {
                OncePeriodEvent inOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
                startDate = inOncePeriodEvent.getEarliestPossibleStartTime().minusDays(1).withHourOfDay(11);
                endDate = inOncePeriodEvent.getLatestPossibleEndTime().plusDays(1).withHourOfDay(13);
            }
            for (DateTime curDay = startDate; curDay.isBefore(endDate); curDay = curDay.plusDays(1)) {
                if (inContainsEvent.containsCould(curDay) && inContainedEvent.containsCould(curDay)) {
                    return true;
                }
            }
        } else {
            for (DayOfYear curDay = new DayOfYear(1, 1); curDay != null; curDay = curDay.plusDaysNoWrap(1)) {
                if (inContainsEvent.containsCould(curDay) && inContainedEvent.containsCould(curDay)) {
                    return true;
                }
            }
        }
        /*if (inContainsEvent instanceof OncePeriodEvent) {
         OncePeriodEvent inContainsOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
         org.joda.time.Interval newInterval = inContainsOncePeriodEvent.toLongInterval();
         OncePeriodEvent inEralyOncePeriodEvent;
         OncePeriodEvent inLateOncePeriodEvent;
         if (inContainedEvent instanceof OncePeriodEvent) {
         inEralyOncePeriodEvent = (OncePeriodEvent) inContainedEvent;
         inLateOncePeriodEvent = (OncePeriodEvent) inContainedEvent;
         } else if (inContainedEvent instanceof Season) {
         Period daysPeriod = newInterval.toPeriod(org.joda.time.PeriodType.days());
         if (daysPeriod.getDays() < 366) {
         inEralyOncePeriodEvent = ((Season) inContainedEvent).getInstance(inContainsOncePeriodEvent.getEarliestPossibleStartTime().getYear());
         inLateOncePeriodEvent = ((Season) inContainedEvent).getInstance(inContainsOncePeriodEvent.getLatestPossibleEndTime().getYear());
         } else {
         return true;
         }
         } else {
         throw new UnsupportedOperationException(OncePeriodEvent.class.getSimpleName() + ".overlaps not supported for Events other than OncePeriodEvent and Season");
         }
         return newInterval.overlaps(inEralyOncePeriodEvent.toLongInterval()) || newInterval.overlaps(inLateOncePeriodEvent.toLongInterval());
         } else {
         //put more checks here
         }*/
        return false;
    }

    public static boolean overlaps(PeriodEvent<?,?> inContainsEvent, PeriodEvent<?,?> inContainedEvent) {
        boolean bothOnceEvents = true;
        if ((!(inContainsEvent instanceof OnceEvent)) || (!(inContainedEvent instanceof OnceEvent))) {
            bothOnceEvents = false;
        }

        if (!couldOverlap(inContainsEvent, inContainedEvent)) {
            return false;
        }

        if (bothOnceEvents) {
            OncePeriodEvent inContainedOncePeriodEvent = (OncePeriodEvent) inContainedEvent;
            OncePeriodEvent inContainsOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
            DateTime startDate;
            DateTime endDate;
            startDate = inContainedOncePeriodEvent.getEarliestPossibleStartTime().minusDays(1).withHourOfDay(11);
            endDate = inContainedOncePeriodEvent.getLatestPossibleEndTime().plusDays(1).withHourOfDay(13);

            for (DateTime curDay = startDate; curDay.isBefore(endDate); curDay = curDay.plusDays(1)) {
                if (inContainsEvent.containsSure(curDay) && inContainedEvent.containsSure(curDay)) {
                    return true;
                }
            }
            boolean foundCouldDay = false;
            for (DateTime curDay = startDate; curDay.isBefore(endDate); curDay = curDay.plusDays(1)) {
                if (inContainsEvent.containsCould(curDay) && inContainedEvent.containsCould(curDay)) {
                    foundCouldDay = true;
                }
            }
            if (!foundCouldDay) {
                return false;
            }
            startDate = inContainedOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(11);
            endDate = inContainedOncePeriodEvent.getLatestPossibleStartTime().withHourOfDay(13);
            boolean cont = true;
            for (DateTime curDay = startDate; curDay.isBefore(endDate); curDay = curDay.plusDays(1)) {
                if (inContainedEvent.containsCould(curDay) && (!inContainsEvent.containsSure(curDay))) {
                    cont = false;
                    break;
                }
            }
            if (!cont) {
                startDate = inContainedOncePeriodEvent.getEarliestPossibleEndTime().withHourOfDay(11);
                endDate = inContainedOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(13);
                cont = true;
                for (DateTime curDay = startDate; curDay.isBefore(endDate); curDay = curDay.plusDays(1)) {
                    if (inContainedEvent.containsCould(curDay) && (!inContainsEvent.containsSure(curDay))) {
                        cont = false;
                        break;
                    }
                }
            }
            if (!cont) {
                startDate = inContainsOncePeriodEvent.getEarliestPossibleStartTime().withHourOfDay(11);
                endDate = inContainsOncePeriodEvent.getLatestPossibleStartTime().withHourOfDay(13);
                cont = true;
                for (DateTime curDay = startDate; curDay.isBefore(endDate); curDay = curDay.plusDays(1)) {
                    if (inContainsEvent.containsCould(curDay) && (!inContainedEvent.containsSure(curDay))) {
                        cont = false;
                        break;
                    }
                }
            }
            if (!cont) {
                startDate = inContainsOncePeriodEvent.getEarliestPossibleEndTime().withHourOfDay(11);
                endDate = inContainsOncePeriodEvent.getLatestPossibleEndTime().withHourOfDay(13);
                cont = true;
                for (DateTime curDay = startDate; curDay.isBefore(endDate); curDay = curDay.plusDays(1)) {
                    if (inContainsEvent.containsCould(curDay) && (!inContainedEvent.containsSure(curDay))) {
                        cont = false;
                        break;
                    }
                }
            }
            return cont;
        } else {
            for (DayOfYear curDay = new DayOfYear(1, 1); curDay != null; curDay = curDay.plusDaysNoWrap(1)) {
                if (inContainsEvent.containsSure(curDay) && inContainedEvent.containsSure(curDay)) {
                    return true;
                }
            }
        }
        /*if (inContainsEvent instanceof OncePeriodEvent) {
         OncePeriodEvent inContainsOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
         org.joda.time.Interval newInterval = inContainsOncePeriodEvent.toLongInterval();
         OncePeriodEvent inEralyOncePeriodEvent;
         OncePeriodEvent inLateOncePeriodEvent;
         if (inContainedEvent instanceof OncePeriodEvent) {
         inEralyOncePeriodEvent = (OncePeriodEvent) inContainedEvent;
         inLateOncePeriodEvent = (OncePeriodEvent) inContainedEvent;
         } else if (inContainedEvent instanceof Season) {
         Period daysPeriod = newInterval.toPeriod(org.joda.time.PeriodType.days());
         if (daysPeriod.getDays() < 366) {
         inEralyOncePeriodEvent = ((Season) inContainedEvent).getInstance(inContainsOncePeriodEvent.getEarliestPossibleStartTime().getYear());
         inLateOncePeriodEvent = ((Season) inContainedEvent).getInstance(inContainsOncePeriodEvent.getLatestPossibleEndTime().getYear());
         } else {
         return true;
         }
         } else {
         throw new UnsupportedOperationException(OncePeriodEvent.class.getSimpleName() + ".overlaps not supported for Events other than OncePeriodEvent and Season");
         }
         return newInterval.overlaps(inEralyOncePeriodEvent.toLongInterval()) || newInterval.overlaps(inLateOncePeriodEvent.toLongInterval());
         } else {
         //put more checks here
         }*/
        return false;
    }

    public static boolean outside(PeriodEvent<?,?> inContainsEvent, Event inContainedEvent) {
        if (inContainedEvent instanceof PeriodEvent) {
            PeriodEvent<?,?> inContainedPeriodEvent = (PeriodEvent<?,?>) inContainedEvent;
            if (overlaps(inContainsEvent, inContainedPeriodEvent)) {
                return false;
            }
            if (!couldOverlap(inContainsEvent, inContainedPeriodEvent)) {
                return true;
            }
            boolean bothOnceEvents = true;
            if ((!(inContainsEvent instanceof OnceEvent)) || (!(inContainedEvent instanceof OnceEvent))) {
                bothOnceEvents = false;
            }

            if (bothOnceEvents) {
                DateTime startDate;
                DateTime endDate;
                if (inContainedEvent instanceof OncePeriodEvent) {
                    OncePeriodEvent inOncePeriodEvent = (OncePeriodEvent) inContainedEvent;
                    startDate = inOncePeriodEvent.getEarliestPossibleStartTime().minusDays(1).withHourOfDay(1);
                    endDate = inOncePeriodEvent.getLatestPossibleEndTime().plusDays(1).withHourOfDay(23);
                } else {
                    OncePeriodEvent inOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
                    startDate = inOncePeriodEvent.getEarliestPossibleStartTime().minusDays(1).withHourOfDay(1);
                    endDate = inOncePeriodEvent.getLatestPossibleEndTime().plusDays(1).withHourOfDay(23);
                }
                for (DateTime curDay = startDate; curDay.isBefore(endDate); curDay = curDay.plusDays(1)) {
                    if (inContainsEvent.containsSure(curDay) && inContainedPeriodEvent.containsCould(curDay)) {
                        return false;
                    }
                    if (inContainsEvent.containsCould(curDay) && inContainedPeriodEvent.containsSure(curDay)) {
                        return false;
                    }
                }
            } else {
                for (DayOfYear curDay = new DayOfYear(1, 1); curDay != null; curDay = curDay.plusDaysNoWrap(1)) {
                    if (inContainsEvent.containsSure(curDay) && inContainedPeriodEvent.containsCould(curDay)) {
                        return false;
                    }
                    if (inContainsEvent.containsCould(curDay) && inContainedPeriodEvent.containsSure(curDay)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            DayEvent<?,?> inDayEvent = (DayEvent<?,?>) inContainedEvent;
            if (inDayEvent instanceof OnceDayEvent) {
                OnceDayEvent inOnceDayEvent = (OnceDayEvent) inDayEvent;
                DateTime endTime = inOnceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                for (DateTime curDay = inOnceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1); curDay.isBefore(endTime); curDay = curDay.plusDays(1)) {
                    if (inContainsEvent.containsSure(curDay)) {
                        return false;
                    }
                }
                return true;
            } else if (inDayEvent instanceof YearlyDayEvent) {
                YearlyDayEvent inYearlyDayEvent = (YearlyDayEvent) inDayEvent;
                for (DayOfYear curDay = new DayOfYear(1, 1); curDay != null; curDay = curDay.plusDaysNoWrap(1)) {
                    if (inContainsEvent.containsSure(curDay) && inYearlyDayEvent.getPossibleDays().contains(curDay)) {
                        return false;
                    }
                }
                return true;
            } else {
                throw new UnsupportedOperationException("ContainsConstraint.outside not supported for Events other than OnceDayEvent, YearlyDayEvent, OncePeriodEvent and Season");
            }
        }
    }

    public static boolean setNonoverlaping(PeriodEvent<?,?> inContainsEvent, Event inContainedEvent) {
        boolean changedSomeThing = false;
        //trade NonoverlapingDayAcceptors
        if (inContainedEvent instanceof PeriodEvent) {
            PeriodEvent<?,?> inPeriodEvent = (PeriodEvent<?,?>) inContainedEvent;
            if (!inPeriodEvent.getStartDayAcceptor().contains(inContainsEvent.getNonoverlapingDayAcceptor())) {
                inPeriodEvent.getStartDayAcceptor().add(inContainsEvent.getNonoverlapingDayAcceptor());
            }
            if (!inPeriodEvent.getEndDayAcceptor().contains(inContainsEvent.getNonoverlapingDayAcceptor())) {
                inPeriodEvent.getEndDayAcceptor().add(inContainsEvent.getNonoverlapingDayAcceptor());
            }
            NonoverlapingDayAcceptor nonoverlapingDayAcceptor = inPeriodEvent.getNonoverlapingDayAcceptor();
            if (!inContainsEvent.getStartDayAcceptor().contains(nonoverlapingDayAcceptor)) {
                inContainsEvent.getStartDayAcceptor().add(nonoverlapingDayAcceptor);
            }
            if (!inContainsEvent.getEndDayAcceptor().contains(nonoverlapingDayAcceptor)) {
                inContainsEvent.getEndDayAcceptor().add(nonoverlapingDayAcceptor);
            }
        } else if (inContainedEvent instanceof DayEvent) {
            DayEvent<?,?> inDayEvent = (DayEvent<?,?>) inContainedEvent;
            if (!inDayEvent.getDayAcceptor().contains(inContainsEvent.getNonoverlapingDayAcceptor())) {
                inDayEvent.getDayAcceptor().add(inContainsEvent.getNonoverlapingDayAcceptor());
            }
        }
        if (!outside(inContainsEvent, inContainedEvent)) {
            if (inContainsEvent instanceof OncePeriodEvent) {
                OncePeriodEvent inContainsOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
                if (inContainedEvent instanceof OnceEvent) {
                    OnceEvent inContainedOnceEvent = (OnceEvent) inContainedEvent;
                    //todo:change inContainedOnceEvent
                    if (inContainedOnceEvent.getLatestPossibleEndTime().isBefore(inContainsOncePeriodEvent.getEarliestPossibleEndTime())) {
                        changedSomeThing = changedSomeThing || inContainedOnceEvent.setBefore(inContainsOncePeriodEvent.getLatestPossibleStartTime());
                        if (inContainedEvent instanceof OnceDayEvent) {
                            if (inContainedOnceEvent.size() == 1) {
                                if (inContainsOncePeriodEvent.getEarliestPossibleStartTime().isBefore(inContainedOnceEvent.getLatestPossibleEndTime())) {
                                    changedSomeThing = true;
                                    inContainsOncePeriodEvent.setEarliestPossibleDateForStart(inContainedOnceEvent.getLatestPossibleEndTime());
                                }
                            }
                        } else if (inContainedEvent instanceof OncePeriodEvent) {
                            OncePeriodEvent inContainedOncePeriodEvent = (OncePeriodEvent) inContainedEvent;

                        }
                    } else if (inContainedOnceEvent.getEarliestPossibleStartTime().isAfter(inContainsOncePeriodEvent.getLatestPossibleStartTime())) {
                        changedSomeThing = changedSomeThing || inContainedOnceEvent.setAfter(inContainsOncePeriodEvent.getEarliestPossibleEndTime());
                        if (inContainedEvent instanceof OnceDayEvent) {
                            if (inContainedOnceEvent.size() == 1) {
                                if (inContainsOncePeriodEvent.getLatestPossibleEndTime().isAfter(inContainedOnceEvent.getEarliestPossibleStartTime())) {
                                    inContainsOncePeriodEvent.setLatestPossibleDateForEnd(inContainedOnceEvent.getEarliestPossibleStartTime());
                                }
                            }
                        } else if (inContainedEvent instanceof OncePeriodEvent) {
                            OncePeriodEvent inContainedOncePeriodEvent = (OncePeriodEvent) inContainedEvent;

                        }
                    } else {
                        //todo: change inContainedOnceEvent 
                    }
                } else if (inContainedEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent inContainedYearlyDayEvent = (YearlyDayEvent) inContainedEvent;
                    //todo: change inContainedEvent
                }
            }
        }
        if (inContainsEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent inContainsSeason = (YearlyPeriodEvent) inContainsEvent;
            inContainsSeason.addNonoverlapingEvent(inContainedEvent);
        }
        return changedSomeThing;
    }

    public static boolean setContains(PeriodEvent<?,?> inContainsEvent, Event inContainedEvent) {
        boolean changed = false;
        if (inContainedEvent instanceof PeriodEvent) {
            PeriodEvent<?,?> inPeriodEvent = (PeriodEvent<?,?>) inContainedEvent;
            if (inContainsEvent.getMinDuration() < inPeriodEvent.getMinDuration()) {
                inContainsEvent.setMinDuration(inPeriodEvent.getMinDuration());
                changed = true;
            }
            if (inPeriodEvent.getMaxDuration() > inContainsEvent.getMaxDuration()) {
                inPeriodEvent.setMaxDuration(inContainsEvent.getMaxDuration());
                changed = true;
            }
            if (!inPeriodEvent.getStartDayAcceptor().contains(inContainsEvent.getContainsDayAcceptor())) {
                inPeriodEvent.getStartDayAcceptor().add(inContainsEvent.getContainsDayAcceptor());
            }
            if (!inPeriodEvent.getEndDayAcceptor().contains(inContainsEvent.getContainsDayAcceptor())) {
                inPeriodEvent.getEndDayAcceptor().add(inContainsEvent.getContainsDayAcceptor());
            }
        } else if (inContainedEvent instanceof DayEvent) {
            DayEvent<?,?> inDayEvent = (DayEvent<?,?>) inContainedEvent;
            if (!inDayEvent.getDayAcceptor().contains(inContainsEvent.getContainsDayAcceptor())) {
                inDayEvent.getDayAcceptor().add(inContainsEvent.getContainsDayAcceptor());
            }
        }
        if (inContainsEvent instanceof OncePeriodEvent) {
            OncePeriodEvent inContainsOncePeriodEvent = (OncePeriodEvent) inContainsEvent;
            if (inContainedEvent instanceof OnceEvent) {
                OnceEvent<?> inOnceEvent = (OnceEvent<?>) inContainedEvent;
                if (inOnceEvent.setAfter(inContainsOncePeriodEvent.getEarliestPossibleStartTime())) {
                    changed = true;
                }
                if (inOnceEvent.setBefore(inContainsOncePeriodEvent.getLatestPossibleEndTime())) {
                    changed = true;
                }
                if (inContainsOncePeriodEvent.getEarliestPossibleEndTime().isBefore(inOnceEvent.getEarliestPossibleStartTime())) {
                    inContainsOncePeriodEvent.setEarliestPossibleDateForEnd(inOnceEvent.getEarliestPossibleStartTime());
                    changed = true;
                }
                if (inContainsOncePeriodEvent.getLatestPossibleStartTime().isAfter(inOnceEvent.getLatestPossibleEndTime())) {
                    inContainsOncePeriodEvent.setLatestPossibleDateForStart(inOnceEvent.getLatestPossibleEndTime());
                    changed = true;
                }
            } else if (inContainedEvent instanceof YearlyEvent) {
                YearlyEvent inYearlyEvent = (YearlyEvent) inContainedEvent;
                int endyear = inContainsOncePeriodEvent.getEarliestPossibleEndTime().getYear();
                int startyear = inContainsOncePeriodEvent.getLatestPossibleStartTime().getYear();
                OnceEvent lateInOnceEvent = inYearlyEvent.getInstance(endyear);
                OnceEvent eralyInOnceEvent = inYearlyEvent.getInstance(startyear);
                if (inContainsOncePeriodEvent.getEarliestPossibleEndTime().isBefore(lateInOnceEvent.getEarliestPossibleStartTime())) {
                    inContainsOncePeriodEvent.setEarliestPossibleDateForEnd(lateInOnceEvent.getEarliestPossibleStartTime());
                    changed = true;
                }
                if (inContainsOncePeriodEvent.getLatestPossibleStartTime().isAfter(eralyInOnceEvent.getLatestPossibleEndTime())) {
                    inContainsOncePeriodEvent.setLatestPossibleDateForStart(eralyInOnceEvent.getLatestPossibleEndTime());
                    changed = true;
                }
            }
        }
        if (inContainsEvent instanceof YearlyPeriodEvent) {
            YearlyPeriodEvent inContainsSeason = (YearlyPeriodEvent) inContainsEvent;
            inContainsSeason.addContainedEvent(inContainedEvent);
        }
        return changed;
    }
    private final PeriodEvent<?,?> mContainsEvent;
    private final Event mContainedEvent;
    private final Event rConstrainedEvents[];
    private final ContainsType mContainsType;
    
    @Primary
    public ContainsConstraint(PeriodEvent<?,?> inContainsEvent, Event inContainedEvent, ContainsType inContainsType) {
        this(false, inContainsEvent, inContainedEvent, inContainsType);
    }

    public ContainsConstraint(boolean isSynthetic, PeriodEvent<?,?> inContainsEvent, Event inContainedEvent, ContainsType inContainsType) {
        super(isSynthetic);
        this.mContainsEvent = inContainsEvent;
        this.mContainedEvent = inContainedEvent;
        this.mContainsType = inContainsType;
        this.rConstrainedEvents = new Event[2];
        this.rConstrainedEvents[0] = inContainsEvent;
        this.rConstrainedEvents[1] = inContainedEvent;
    }
    
    public ContainsConstraint(Element root){
        super(root);
        Element firstEpisodeElement = root.getChild("ContainsEventByID");
            this.mContainsEvent = (PeriodEvent<?,?>) EventImp.EventIDXMLWriter.instance.readElements(firstEpisodeElement);
            Element secondEpisodeElement = root.getChild("ContainedEventByID");
            this.mContainedEvent = (EventImp) EventImp.EventIDXMLWriter.instance.readElements(secondEpisodeElement);
            Element kindElement = root.getChild("type");
            this.mContainsType = ContainsConstraint.ContainsType.valueOf(kindElement.getTextNormalize());
            this.rConstrainedEvents = new Event[2];
        this.rConstrainedEvents[0] = this.mContainsEvent;
        this.rConstrainedEvents[1] = this.mContainedEvent;
    }

    @Override
    public DayAcceptor getDayAcceptor() {
        DayAcceptor rDayAcceptor;
        switch (this.mContainsType) {
            case CONTAINS:
                rDayAcceptor = this.mContainsEvent.getContainsDayAcceptor();
                break;
            case NONOVERLAPING:
                rDayAcceptor = this.mContainsEvent.getNonoverlapingDayAcceptor();
                break;
            default:
                rDayAcceptor = null;
        }
        return rDayAcceptor;
    }

    @Override
    public boolean inBeta() {
        return true;
    }

    @Override
    public boolean isStrict() {
        return true;
    }

    @Override
    public Event getFirstEvent() {
        return this.mContainsEvent;
    }

    @Override
    public Event getSecondEvent() {
        return this.mContainedEvent;
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
        if (this.getContainsType() == ContainsType.CONTAINS) {
            con = con && contains(this.mContainsEvent, this.mContainedEvent);
        } else if (this.getContainsType() == ContainsType.NONOVERLAPING) {
            con = con && (!contains(this.mContainsEvent, this.mContainedEvent));
            con = con && outside(this.mContainsEvent, this.mContainedEvent);
            if (this.mContainedEvent instanceof PeriodEvent) {
                PeriodEvent<?,?> mPeriodEvent = (PeriodEvent<?,?>) this.mContainedEvent;
                con = con && (!overlaps(this.mContainsEvent, mPeriodEvent));
                con = con && (!overlaps(mPeriodEvent, this.mContainsEvent));
            }
        }
        return con;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        if (this.getContainsType() == ContainsType.CONTAINS) {
            changed = setContains(this.mContainsEvent, this.mContainedEvent) || changed;
        } else if (this.getContainsType() == ContainsType.NONOVERLAPING) {
            changed = setNonoverlaping(this.mContainsEvent, this.mContainedEvent) || changed;
            if (this.mContainedEvent instanceof PeriodEvent) {
                PeriodEvent<?,?> mPeriodEvent = (PeriodEvent<?,?>) this.mContainedEvent;
                changed = setNonoverlaping(mPeriodEvent, this.mContainsEvent) || changed;
            }
        }
        return changed;
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = this.applyConstraint();
        //Todo: fill in
        if (this.getContainsType() == ContainsType.CONTAINS) {
            if (this.mContainedEvent instanceof DateTimeDayEvent) {
                java.util.Collection<DateTime> toRemove = new java.util.ArrayList<>(0);
                NavigableSet<DateTime> possibleDays = ((DateTimeDayEvent) this.mContainedEvent).getPossibleDays();
                toRemove.addAll(possibleDays.stream().filter((curDay) -> (!this.mContainsEvent.getContainsDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                possibleDays.removeAll(toRemove);
            } else if (this.mContainedEvent instanceof DayOfYearDayEvent) {
                java.util.Collection<DayOfYear> toRemove = new java.util.ArrayList<>(0);
                NavigableSet<DayOfYear> possibleDays = ((DayOfYearDayEvent) this.mContainedEvent).getPossibleDays();
                toRemove.addAll(possibleDays.stream().filter((curDay) -> (!this.mContainsEvent.getContainsDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                possibleDays.removeAll(toRemove);
            } else if (this.mContainedEvent instanceof DateTimePeriodEvent) {
                DateTimePeriodEvent mDateTimePeriodEvent = (DateTimePeriodEvent) this.mContainedEvent;
                java.util.Collection<DateTime> toRemove = new java.util.ArrayList<>(0);
                NavigableSet<DateTime> possibleStartDays = mDateTimePeriodEvent.getStartPossibleDays();
                toRemove.addAll(possibleStartDays.stream().filter((curDay) -> (!this.mContainsEvent.getContainsDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                possibleStartDays.removeAll(toRemove);
                toRemove.clear();
                NavigableSet<DateTime> possibleEndDays = mDateTimePeriodEvent.getEndPossibleDays();
                toRemove.addAll(possibleEndDays.stream().filter((curDay) -> (!this.mContainsEvent.getContainsDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                possibleEndDays.removeAll(toRemove);
            } else if (this.mContainedEvent instanceof DayOfYearPeriodEvent) {
                DayOfYearPeriodEvent mDateTimePeriodEvent = (DayOfYearPeriodEvent) this.mContainedEvent;
                java.util.Collection<DayOfYear> toRemove = new java.util.ArrayList<>(0);
                NavigableSet<DayOfYear> possibleStartDays = mDateTimePeriodEvent.getStartPossibleDays();
                toRemove.addAll(possibleStartDays.stream().filter((curDay) -> (!this.mContainsEvent.getContainsDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                possibleStartDays.removeAll(toRemove);
                toRemove.clear();
                NavigableSet<DayOfYear> possibleEndDays = mDateTimePeriodEvent.getEndPossibleDays();
                toRemove.addAll(possibleEndDays.stream().filter((curDay) -> (!this.mContainsEvent.getContainsDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                possibleEndDays.removeAll(toRemove);
                possibleEndDays.removeAll(toRemove);
            }
        } else if (this.getContainsType() == ContainsType.NONOVERLAPING) {
            if (this.mContainedEvent instanceof PeriodEvent) {
                PeriodEvent<?,?> mPeriodEvent = (PeriodEvent<?,?>) this.mContainedEvent;
                if (this.mContainedEvent instanceof DateTimePeriodEvent) {
                    DateTimePeriodEvent<? extends PeriodEvent.PeriodPlacement<DateTime>> mDateTimePeriodEvent = (DateTimePeriodEvent<? extends PeriodEvent.PeriodPlacement<DateTime>>) this.mContainedEvent;
                    java.util.Collection<DateTime> toRemove = new java.util.ArrayList<>(0);
                    NavigableSet<DateTime> possibleStartDays = mDateTimePeriodEvent.getStartPossibleDays();
                    toRemove.addAll(possibleStartDays.stream().filter((curDay) -> (!this.mContainsEvent.getNonoverlapingDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleStartDays.removeAll(toRemove);
                    toRemove.clear();
                    NavigableSet<DateTime> possibleEndDays = mDateTimePeriodEvent.getEndPossibleDays();
                    toRemove.addAll(possibleEndDays.stream().filter((curDay) -> (!this.mContainsEvent.getNonoverlapingDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleEndDays.removeAll(toRemove);
                } else if (this.mContainedEvent instanceof DayOfYearPeriodEvent) {
                    DayOfYearPeriodEvent mDateTimePeriodEvent = (DayOfYearPeriodEvent) this.mContainedEvent;
                    java.util.Collection<DayOfYear> toRemove = new java.util.ArrayList<>(0);
                    NavigableSet<DayOfYear> possibleStartDays = mDateTimePeriodEvent.getStartPossibleDays();
                    toRemove.addAll(possibleStartDays.stream().filter((curDay) -> (!this.mContainsEvent.getNonoverlapingDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleStartDays.removeAll(toRemove);
                    toRemove.clear();
                    NavigableSet<DayOfYear> possibleEndDays = mDateTimePeriodEvent.getEndPossibleDays();
                    toRemove.addAll(possibleEndDays.stream().filter((curDay) -> (!this.mContainsEvent.getNonoverlapingDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleEndDays.removeAll(toRemove);
                }
                if (this.mContainsEvent instanceof DateTimePeriodEvent) {
                    DateTimePeriodEvent mDateTimePeriodEvent = (DateTimePeriodEvent) this.mContainsEvent;
                    java.util.Collection<DateTime> toRemove = new java.util.ArrayList<>(0);
                    NavigableSet<DateTime> possibleStartDays = mDateTimePeriodEvent.getStartPossibleDays();
                    toRemove.addAll(possibleStartDays.stream().filter((curDay) -> (!mPeriodEvent.getNonoverlapingDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleStartDays.removeAll(toRemove);
                    toRemove.clear();
                    NavigableSet<DateTime> possibleEndDays = mDateTimePeriodEvent.getEndPossibleDays();
                    toRemove.addAll(possibleEndDays.stream().filter((curDay) -> (!mPeriodEvent.getNonoverlapingDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleEndDays.removeAll(toRemove);

                } else if (this.mContainsEvent instanceof DayOfYearPeriodEvent) {
                    DayOfYearPeriodEvent mDateTimePeriodEvent = (DayOfYearPeriodEvent) this.mContainsEvent;
                    java.util.Collection<DayOfYear> toRemove = new java.util.ArrayList<>(0);
                    NavigableSet<DayOfYear> possibleStartDays = mDateTimePeriodEvent.getStartPossibleDays();
                    toRemove.addAll(possibleStartDays.stream().filter((curDay) -> (!mPeriodEvent.getNonoverlapingDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleStartDays.removeAll(toRemove);
                    toRemove.clear();
                    NavigableSet<DayOfYear> possibleEndDays = mDateTimePeriodEvent.getEndPossibleDays();
                    toRemove.addAll(possibleEndDays.stream().filter((curDay) -> (!mPeriodEvent.getNonoverlapingDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleEndDays.removeAll(toRemove);

                }
            } else {
                if (this.mContainedEvent instanceof DateTimeDayEvent) {
                    java.util.Collection<DateTime> toRemove = new java.util.ArrayList<>(0);
                    NavigableSet<DateTime> possibleDays = ((DateTimeDayEvent) this.mContainedEvent).getPossibleDays();
                    toRemove.addAll(possibleDays.stream().filter((curDay) -> (!this.mContainsEvent.getContainsDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleDays.removeAll(toRemove);
                } else if (this.mContainedEvent instanceof DayOfYearDayEvent) {
                    java.util.Collection<DayOfYear> toRemove = new java.util.ArrayList<>(0);
                    NavigableSet<DayOfYear> possibleDays = ((DayOfYearDayEvent) this.mContainedEvent).getPossibleDays();
                    toRemove.addAll(possibleDays.stream().filter((curDay) -> (!this.mContainsEvent.getContainsDayAcceptor().accept(curDay))).collect(Collectors.toList()));
                    possibleDays.removeAll(toRemove);
                }
            }
        }
        return changed;
    }
    
    @Override
    public boolean consistentWithConstraint(T inFirstPlacement, S inSecondPlacement) {
        if (this.getContainsType().equals(ContainsType.CONTAINS)) {
            if (this.mContainsEvent instanceof OncePeriodEvent) {
                OncePeriodEvent.OncePeriodEventPlacement firstOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inFirstPlacement;
                DateTime firstStartDate = firstOncePeriodEventPlacement.startDay.withHourOfDay(1);
                DateTime firstEndDate = firstOncePeriodEventPlacement.endDay.withHourOfDay(23);
                if (this.mContainedEvent instanceof OnceDayEvent) {
                    OnceDayEvent.OnceDayEventPlacement secondOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inSecondPlacement;
                    return firstStartDate.isBefore(secondOnceDayEventPlacement.day) && firstEndDate.isAfter(secondOnceDayEventPlacement.day);
                } else if (this.mContainedEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent.YearlyDayEventPlacement secondYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inSecondPlacement;
                    long lengh = new org.joda.time.Interval(firstStartDate, firstEndDate).toDuration().getStandardDays();
                    if (lengh > 366) {
                        return true;
                    } else {
                        DateTime lowDateTime = secondYearlyDayEventPlacement.day.toDateTime().withYear(firstStartDate.getYear());
                        DateTime highDateTime = secondYearlyDayEventPlacement.day.toDateTime().withYear(firstEndDate.getYear());
                        return (firstStartDate.isBefore(lowDateTime) && firstEndDate.isAfter(lowDateTime))
                                || (firstStartDate.isBefore(highDateTime) && firstEndDate.isAfter(highDateTime));
                    }
                } else if (this.mContainedEvent instanceof OncePeriodEvent) {
                    OncePeriodEvent.OncePeriodEventPlacement secondOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inSecondPlacement;
                    return firstStartDate.isBefore(secondOncePeriodEventPlacement.startDay) && firstEndDate.isAfter(secondOncePeriodEventPlacement.endDay);
                } else if (this.mContainedEvent instanceof YearlyPeriodEvent) {
                    YearlyPeriodEvent.YearlyPeriodEventPlacement secondSeasonPlacement = (YearlyPeriodEvent.YearlyPeriodEventPlacement) inSecondPlacement;
                    long lengh = new org.joda.time.Interval(firstStartDate, firstEndDate).toDuration().getStandardDays();
                    if (lengh > 366) {
                        return true;
                    } else {
                        DateTime lowStartDateTime = secondSeasonPlacement.startDay.toDateTime().withYear(firstStartDate.getYear());
                        DateTime lowEndDateTime = secondSeasonPlacement.endDay.toDateTime().withYear(firstStartDate.getYear());
                        DateTime highStartDateTime = secondSeasonPlacement.startDay.toDateTime().withYear(firstEndDate.getYear());
                        DateTime highEndDateTime = secondSeasonPlacement.endDay.toDateTime().withYear(firstEndDate.getYear());
                        return (firstStartDate.isBefore(lowStartDateTime) && firstEndDate.isAfter(lowEndDateTime))
                                || (firstStartDate.isBefore(highStartDateTime) && firstEndDate.isAfter(highEndDateTime));
                    }
                } else {
                    return true;
                }
            } else if (this.mContainsEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent.YearlyPeriodEventPlacement firstSeasonPlacement = (YearlyPeriodEvent.YearlyPeriodEventPlacement) inFirstPlacement;
                if (this.mContainedEvent instanceof OnceDayEvent) {
                    OnceDayEvent.OnceDayEventPlacement secondOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inSecondPlacement;
                    DayOfYear tempDay = DayOfYear.fromDateTime(secondOnceDayEventPlacement.day);
                    return firstSeasonPlacement.startDay.isEqualOrBefore(tempDay) && firstSeasonPlacement.endDay.isEqualOrAfter(tempDay);
                } else if (this.mContainedEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent.YearlyDayEventPlacement secondYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inSecondPlacement;
                    return firstSeasonPlacement.startDay.isEqualOrBefore(secondYearlyDayEventPlacement.day) && firstSeasonPlacement.endDay.isEqualOrAfter(secondYearlyDayEventPlacement.day);
                } else if (this.mContainedEvent instanceof OncePeriodEvent) {
                    OncePeriodEvent.OncePeriodEventPlacement secondOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inSecondPlacement;
                    long lengh = new org.joda.time.Interval(secondOncePeriodEventPlacement.startDay, secondOncePeriodEventPlacement.endDay).toDuration().getStandardDays();
                    if (lengh > 366) {
                        return true;
                    } else {
                        DayOfYear tempStartDay = DayOfYear.fromDateTime(secondOncePeriodEventPlacement.startDay);
                        DayOfYear tempEndDay = DayOfYear.fromDateTime(secondOncePeriodEventPlacement.endDay);
                        return firstSeasonPlacement.startDay.isEqualOrBefore(tempStartDay) && firstSeasonPlacement.endDay.isEqualOrAfter(tempEndDay);
                    }
                } else if (this.mContainedEvent instanceof YearlyPeriodEvent) {
                    YearlyPeriodEvent.YearlyPeriodEventPlacement secondSeasonPlacement = (YearlyPeriodEvent.YearlyPeriodEventPlacement) inSecondPlacement;
                    return firstSeasonPlacement.startDay.isEqualOrBefore(secondSeasonPlacement.startDay) && firstSeasonPlacement.endDay.isEqualOrAfter(secondSeasonPlacement.endDay);
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            //todo: fill in
            if (this.mContainsEvent instanceof OncePeriodEvent) {
                OncePeriodEvent.OncePeriodEventPlacement firstOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inFirstPlacement;
                DateTime firstStartDate = firstOncePeriodEventPlacement.startDay.withHourOfDay(23);
                DateTime firstEndDate = firstOncePeriodEventPlacement.endDay.withHourOfDay(2);
                if (this.mContainedEvent instanceof YearlyEvent) {
                    org.joda.time.Interval newInterval = new org.joda.time.Interval(firstStartDate, firstEndDate);
                    if (newInterval.toDuration().toStandardDays().getDays() >= 366) {
                        return false;
                    }
                }
                if (this.mContainedEvent instanceof OnceDayEvent) {
                    OnceDayEvent.OnceDayEventPlacement secondOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inSecondPlacement;
                    return secondOnceDayEventPlacement.day.isBefore(firstStartDate) || secondOnceDayEventPlacement.day.isAfter(firstEndDate);
                } else if (this.mContainedEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent.YearlyDayEventPlacement secondYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inSecondPlacement;
                    for (DateTime curDateTime = firstStartDate.withHourOfDay(1); curDateTime.isBefore(firstEndDate); curDateTime = curDateTime.plusDays(1)) {
                        DayOfYear curDayOfYear = DayOfYear.fromDateTime(curDateTime);
                        if (curDayOfYear.equals(secondYearlyDayEventPlacement.day)) {
                            return false;
                        }
                    }
                } else if (this.mContainedEvent instanceof OncePeriodEvent) {
                    OncePeriodEvent.OncePeriodEventPlacement secondOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inSecondPlacement;
                    return secondOncePeriodEventPlacement.endDay.isBefore(firstStartDate) || secondOncePeriodEventPlacement.startDay.isAfter(firstEndDate);
                } else if (this.mContainedEvent instanceof YearlyPeriodEvent) {
                    YearlyPeriodEvent.YearlyPeriodEventPlacement secondSeasonPlacement = (YearlyPeriodEvent.YearlyPeriodEventPlacement) inSecondPlacement;
                    java.util.Collection<DayOfYear> firstSet = new java.util.HashSet<>(1);
                    java.util.Collection<DayOfYear> secondSet = new java.util.HashSet<>(1);
                    for (DateTime curDateTime = firstStartDate.withHourOfDay(1); curDateTime.isBefore(firstEndDate); curDateTime = curDateTime.plusDays(1)) {
                        DayOfYear curDayOfYear = DayOfYear.fromDateTime(curDateTime);
                        firstSet.add(curDayOfYear);
                    }
                    for (DayOfYear curDayOfYear = secondSeasonPlacement.startDay; !curDayOfYear.equals(secondSeasonPlacement.endDay); curDayOfYear.plusDays(1)) {
                        secondSet.add(curDayOfYear);
                    }
                    secondSet.add(secondSeasonPlacement.endDay);
                    firstSet.retainAll(secondSet);
                    if (!firstSet.isEmpty()) {
                        return false;
                    }
                } else {
                    return true;
                }
            } else if (this.mContainsEvent instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent.YearlyPeriodEventPlacement firstSeasonPlacement = (YearlyPeriodEvent.YearlyPeriodEventPlacement) inFirstPlacement;
                java.util.Collection<DayOfYear> firstSet = new java.util.HashSet<>(1);
                for (DayOfYear curDayOfYear = firstSeasonPlacement.startDay; !curDayOfYear.equals(firstSeasonPlacement.endDay); curDayOfYear.plusDays(1)) {
                    firstSet.add(curDayOfYear);
                }
                if (this.mContainedEvent instanceof OnceDayEvent) {
                    OnceDayEvent.OnceDayEventPlacement secondOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inSecondPlacement;
                    if (firstSet.contains(DayOfYear.fromDateTime(secondOnceDayEventPlacement.day))) {
                        return false;
                    }
                } else if (this.mContainedEvent instanceof YearlyDayEvent) {
                    YearlyDayEvent.YearlyDayEventPlacement secondYearlyDayEventPlacement = (YearlyDayEvent.YearlyDayEventPlacement) inSecondPlacement;
                    if (firstSet.contains(secondYearlyDayEventPlacement.day)) {
                        return false;
                    }
                } else if (this.mContainedEvent instanceof OncePeriodEvent) {
                    OncePeriodEvent.OncePeriodEventPlacement secondOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inSecondPlacement;
                    java.util.Collection<DayOfYear> secondSet = new java.util.HashSet<>(1);
                    DateTime endDateTime = secondOncePeriodEventPlacement.endDay.withHourOfDay(23);
                    for (DateTime curDateTime = secondOncePeriodEventPlacement.startDay.withHourOfDay(1); curDateTime.isBefore(endDateTime); curDateTime = curDateTime.plusDays(1)) {
                        DayOfYear curDayOfYear = DayOfYear.fromDateTime(curDateTime);
                        secondSet.add(curDayOfYear);
                    }
                    firstSet.retainAll(secondSet);
                    if (!firstSet.isEmpty()) {
                        return false;
                    }
                } else if (this.mContainedEvent instanceof YearlyPeriodEvent) {
                    YearlyPeriodEvent.YearlyPeriodEventPlacement secondSeasonPlacement = (YearlyPeriodEvent.YearlyPeriodEventPlacement) inSecondPlacement;
                    java.util.Collection<DayOfYear> secondSet = new java.util.HashSet<>(1);
                    for (DayOfYear curDayOfYear = secondSeasonPlacement.startDay; !curDayOfYear.equals(secondSeasonPlacement.endDay); curDayOfYear.plusDays(1)) {
                        secondSet.add(curDayOfYear);
                    }
                    secondSet.add(secondSeasonPlacement.endDay);
                    firstSet.retainAll(secondSet);
                    if (!firstSet.isEmpty()) {
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean consistentWithConstraint(Placement<?>[] inValues) {
        return consistentWithConstraint((T)inValues[0],(S)inValues[1]);
    }

    public Event[] increaseWhat(Placement inValues[]) {
        return this.rConstrainedEvents;
    }

    /**
     * @return the mContainsType
     */
    public ContainsType getContainsType() {
        return this.mContainsType;
    }

    @Override
    public String toString() {
        String rString = this.mContainsEvent.getName();
        switch (this.mContainsType) {
            case CONTAINS:
                rString += " Contains ";
                break;
            case NONOVERLAPING:
                rString += " does not overlap with ";
                break;
        }
        rString += this.mContainedEvent.getName();
        return rString;
    }

    public static enum ContainsType {

        CONTAINS, NONOVERLAPING
    }

    public static class ContainsConstraintXMLWriter
            extends IDedObjectXMLWriter<ContainsConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(ContainsConstraint.class, new ContainsConstraint.ContainsConstraintXMLWriter());
        }

        @Override
        public Element writeElements(ContainsConstraint ObjectToWrite) {
            Element newElement = new Element("ContainsConstraint");
            this.writeElements(ObjectToWrite, newElement);
            Element firstEpisodeElement = new Element("ContainsEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.mContainedEvent, firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            Element secondEpisodeElement = new Element("ContainedEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.mContainedEvent,secondEpisodeElement);
            newElement.addContent(secondEpisodeElement);
            Element kindElement = new Element("type");
            kindElement.setText(ObjectToWrite.getContainsType().name());
            newElement.addContent(kindElement);
            return newElement;
        }

        @Override
        public ContainsConstraint<?,?> readElements(Element root) {
            return new ContainsConstraint<>(root);
        }
    }

    public static class MetaData extends ConstraintMetaDataImp<ContainsConstraint> {

        private static Class<? extends Event>[] createClassList() {
            @SuppressWarnings("unchecked")
            Class<? extends Event> rArray[] = new Class[2];
            rArray[0] = PeriodEvent.class;
            rArray[1] = Event.class;
            return rArray;
        }

        public MetaData() {
            super(createClassList(), new String[]{"Contains", "does not overlap with"});
        }

        @Override
        public ContainsConstraint constuct(List<Event> inEvents, String verb, List<Component> inComponents) {
            ContainsType curType;
            if (verb.equalsIgnoreCase("Contains")) {
                curType = ContainsType.CONTAINS;
            } else {
                curType = ContainsType.NONOVERLAPING;
            }
            return new ContainsConstraint((PeriodEvent<?,?>) inEvents.get(0), inEvents.get(1), curType);
        }
    }
}
