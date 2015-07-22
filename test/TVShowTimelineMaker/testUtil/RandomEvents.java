/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.testUtil;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OnceEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent;
import TVShowTimelineMaker.timeline.YearlyDayEvent;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent;
import TVShowTimelineMaker.util.DayOfYear;
import com.civprod.io.DirectoryWalkers.LoadingDirectoryWalker;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.NavigableSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;


public class RandomEvents {

    private static java.util.Collection<Class<?>> EventTypes = new java.util.ArrayList<>();
    private static final java.util.Random rnd = new java.util.Random();
    private static final Logger LOG = Logger.getLogger(RandomEvents.class.getName());

    public static void init() {
        if (EventTypes == null) {
            EventTypes = new java.util.ArrayList<>();
        }
        if (EventTypes.isEmpty()) {
            String pathJar = "build\\classes";
            LoadingDirectoryWalker mWalker = new LoadingDirectoryWalker("TVShowTimelineMaker");
            try {
                mWalker.run(new File(pathJar), EventTypes);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static <T extends Event> Class<? extends T> selectConcreteSubclass(Class<T> inClass) {
        init();
        if (!Modifier.isAbstract(inClass.getModifiers())) {
            return inClass;
        }
        java.util.List<Class<? extends T>> possibleSubClasses = new java.util.ArrayList<>();
        for (Class curClass : EventTypes) {
            if (inClass.isAssignableFrom(curClass)) {
                possibleSubClasses.add(curClass);
            }
        }
        return selectConcreteSubclass(possibleSubClasses.get(rnd.nextInt(possibleSubClasses.size())));
    }

    public static <T extends Event> T randomEventOfType(Class<T> inType) throws InstantiationException, IllegalAccessException {
        return randomEventOfType(inType, 0);
    }

    public static <T extends Event> T randomEventOfType(Class<T> inType, int mod) throws InstantiationException, IllegalAccessException {
        Class<? extends T> concreteSubclass = selectConcreteSubclass(inType);
        T rValue = concreteSubclass.newInstance();
        if (rValue instanceof OnceEvent) {
            OnceEvent newOnceEvent = (OnceEvent) rValue;
            DateTime EarliestStartTime = DateTimeMaker.randomDateTimeBetweenTimes(DateTimeMaker.startTime.minusDays(mod), DateTimeMaker.endTime.plusDays(mod - 1));
            DateTime LatestEndTime = DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, DateTimeMaker.endTime.plusDays(mod));

            newOnceEvent.setAfter(EarliestStartTime);
            newOnceEvent.setBefore(LatestEndTime);
        }
        if (concreteSubclass.equals(OncePeriodEvent.class)) {
            OncePeriodEvent newOncePeriodEvent = (OncePeriodEvent) rValue;
            newOncePeriodEvent.setEarliestPossibleDateForEnd(DateTimeMaker.randomDateTimeBetweenTimes(newOncePeriodEvent.getEarliestPossibleStartTime(), newOncePeriodEvent.getLatestPossibleEndTime()));
            newOncePeriodEvent.setLatestPossibleDateForStart(DateTimeMaker.randomDateTimeBetweenTimes(newOncePeriodEvent.getEarliestPossibleStartTime(), newOncePeriodEvent.getLatestPossibleEndTime()));
            int maxDuration = 1 + rnd.nextInt(java.lang.Integer.MAX_VALUE);
            newOncePeriodEvent.setMaxDuration(maxDuration);
        } else if (concreteSubclass.equals(YearlyDayEvent.class)) {
            YearlyDayEvent newYearlyDayEvent = (YearlyDayEvent) rValue;
            trimDays(newYearlyDayEvent.getPossibleDays());
        } else if (concreteSubclass.equals(YearlyPeriodEvent.class)) {
            YearlyPeriodEvent newYearlyPeriodEvent = (YearlyPeriodEvent) rValue;
            trimDays(newYearlyPeriodEvent.getStartPossibleDays());
            trimDays(newYearlyPeriodEvent.getEndPossibleDays());
            int maxDuration = 1 + rnd.nextInt(366);
            newYearlyPeriodEvent.setMaxDuration(maxDuration);
        }
        if (rValue instanceof PeriodEvent) {
            PeriodEvent newPeriodEvent = (PeriodEvent) rValue;
            newPeriodEvent.normalize();
            if (newPeriodEvent.getMaxDuration() >= 1) {
                newPeriodEvent.setMinDuration(rnd.nextInt(newPeriodEvent.getMaxDuration()));
                newPeriodEvent.normalize();
            }
        }
        rValue.normalize();
        return rValue;
    }

    //todo: add year events and change the input to PeriodEvent
    public static Event randomContainsEvent(OncePeriodEvent inOncePeriodEvent) {
        try {
            int method = Main.rnd.nextInt(4);
            if (method == 0) {
                return randomContainsEvent(inOncePeriodEvent, OnceDayEvent.class);
            } else if (method == 1) {
                return randomContainsEvent(inOncePeriodEvent, OncePeriodEvent.class);
            } else if (method == 2) {
                return randomContainsEvent(inOncePeriodEvent, YearlyDayEvent.class);
            } else {
                return randomContainsEvent(inOncePeriodEvent, YearlyPeriodEvent.class);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(RandomEvents.class.getName()).log(Level.SEVERE, null, ex);
            return randomEvent();
        }
    }

    //todo: add year events and change the input to PeriodEvent
    public static <T extends Event> T randomContainsEvent(OncePeriodEvent inOncePeriodEvent, Class<T> inType) throws InstantiationException, IllegalAccessException {
        Class<? extends T> concreteSubclass = selectConcreteSubclass(inType);
        inOncePeriodEvent.normalize();
        T rEvent = concreteSubclass.newInstance();
        if (inOncePeriodEvent.getLatestPossibleStartTime().isAfter(inOncePeriodEvent.getEarliestPossibleEndTime())) {
            DateTime mid1 = inOncePeriodEvent.getEarliestPossibleEndTime();
            DateTime mid2 = inOncePeriodEvent.getLatestPossibleStartTime();
            inOncePeriodEvent.setLatestPossibleDateForStart(mid1);
            inOncePeriodEvent.setEarliestPossibleDateForEnd(mid2);
        }
        DateTime EarliestStartTime = DateTimeMaker.randomDateTimeBetweenTimes(inOncePeriodEvent.getLatestPossibleStartTime(), inOncePeriodEvent.getEarliestPossibleEndTime());
        DateTime LatestEndTime = DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, inOncePeriodEvent.getEarliestPossibleEndTime());
        if (rEvent instanceof OnceDayEvent) {
            OnceDayEvent newOnceDayEvent = (OnceDayEvent) rEvent;
            newOnceDayEvent.setEarliestPossibleDate(EarliestStartTime);
            newOnceDayEvent.setLatestPossibleDate(LatestEndTime);
        } else if (rEvent instanceof OncePeriodEvent) {
            OncePeriodEvent newOncePeriodEvent = (OncePeriodEvent) rEvent;
            newOncePeriodEvent.setEarliestPossibleDateForStart(EarliestStartTime);
            newOncePeriodEvent.setEarliestPossibleDateForEnd(DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime));
            newOncePeriodEvent.setLatestPossibleDateForStart(DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime));
            newOncePeriodEvent.setLatestPossibleDateForEnd(LatestEndTime);
            newOncePeriodEvent.normalize();
            int maxDuration;
            if (inOncePeriodEvent.getMaxDuration() >= 1) {
                int minMax = newOncePeriodEvent.getMinDuration() + 1;
                maxDuration = minMax + rnd.nextInt(inOncePeriodEvent.getMaxDuration() - Math.max(minMax - 1, 0));
            } else {
                maxDuration = 1;
            }
            newOncePeriodEvent.setMaxDuration(maxDuration);
            newOncePeriodEvent.normalize();
            if ((newOncePeriodEvent.getMaxDuration() >= 1) && (inOncePeriodEvent.getMinDuration() >= 1)) {
                newOncePeriodEvent.setMinDuration(rnd.nextInt(Math.min(newOncePeriodEvent.getMaxDuration(), inOncePeriodEvent.getMinDuration())));
            } else {
                newOncePeriodEvent.setMinDuration(0);
            }
            newOncePeriodEvent.normalize();
        } else if (rEvent instanceof YearlyDayEvent) {
            YearlyDayEvent newYearlyDayEvent = (YearlyDayEvent) rEvent;
            OnceDayEvent newOnceDayEvent = new OnceDayEvent();
            newOnceDayEvent.setEarliestPossibleDate(EarliestStartTime);
            newOnceDayEvent.setLatestPossibleDate(LatestEndTime);
            newOnceDayEvent.addPossibleDays();
            for (DateTime curDay : newOnceDayEvent.getPossibleDays()) {
                newYearlyDayEvent.getPossibleDays().add(DayOfYear.fromDateTime(curDay));
            }
        } else {
            YearlyPeriodEvent newYearlyPeriodEvent = (YearlyPeriodEvent) rEvent;
            OncePeriodEvent newOncePeriodEvent = new OncePeriodEvent();
            newOncePeriodEvent.setEarliestPossibleDateForStart(EarliestStartTime);
            newOncePeriodEvent.setEarliestPossibleDateForEnd(DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime));
            newOncePeriodEvent.setLatestPossibleDateForStart(DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime));
            newOncePeriodEvent.setLatestPossibleDateForEnd(LatestEndTime);
            int maxDuration;
            newOncePeriodEvent.normalize();
            if (inOncePeriodEvent.getMaxDuration() >= 1) {
                int minMax = newOncePeriodEvent.getMinDuration() + 1;
                maxDuration = minMax + rnd.nextInt(inOncePeriodEvent.getMaxDuration() - Math.max(minMax - 1, 0));
            } else {
                maxDuration = 1;
            }
            newOncePeriodEvent.setMaxDuration(maxDuration);
            newOncePeriodEvent.normalize();
            if ((newOncePeriodEvent.getMaxDuration() >= 1) && (inOncePeriodEvent.getMinDuration() >= 1)) {
                newOncePeriodEvent.setMinDuration(rnd.nextInt(Math.min(newOncePeriodEvent.getMaxDuration(), inOncePeriodEvent.getMinDuration())));
            } else {
                newOncePeriodEvent.setMinDuration(0);
            }
            newOncePeriodEvent.normalize();
            for (DateTime curDay : newOncePeriodEvent.getStartPossibleDays()) {
                newYearlyPeriodEvent.getStartPossibleDays().add(DayOfYear.fromDateTime(curDay));
            }
            for (DateTime curDay : newOncePeriodEvent.getEndPossibleDays()) {
                newYearlyPeriodEvent.getEndPossibleDays().add(DayOfYear.fromDateTime(curDay));
            }
            newYearlyPeriodEvent.setMaxDuration(newOncePeriodEvent.getMaxDuration());
            newYearlyPeriodEvent.setMinDuration(newOncePeriodEvent.getMinDuration());
            newYearlyPeriodEvent.normalize();
        }
        rEvent.normalize();
        return rEvent;
    }

    //todo: add year events and change the input to PeriodEvent
    public static Event randomOutsideEvent(OncePeriodEvent inOncePeriodEvent) {
        try {
            int method = Main.rnd.nextInt(4);
            if (method == 0) {
                return randomOutsideEvent(inOncePeriodEvent, OnceDayEvent.class);
            } else if (method == 1) {
                return randomOutsideEvent(inOncePeriodEvent, OncePeriodEvent.class);
            } else if (method == 2) {
                return randomOutsideEvent(inOncePeriodEvent, YearlyDayEvent.class);
            } else {
                return randomOutsideEvent(inOncePeriodEvent, YearlyPeriodEvent.class);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(RandomEvents.class.getName()).log(Level.SEVERE, null, ex);
            return randomEvent();
        }
    }

    //todo: add year events and change the input to PeriodEvent
    public static <T extends Event> T randomOutsideEvent(OncePeriodEvent inOncePeriodEvent, Class<T> inType) throws InstantiationException, IllegalAccessException {
        Class<? extends T> concreteSubclass = selectConcreteSubclass(inType);
        T rEvent = concreteSubclass.newInstance();
        DateTime EarliestStartTime;
        DateTime LatestEndTime;
        if (rnd.nextBoolean()) {
            DateTime beforeTime = inOncePeriodEvent.getEarliestPossibleStartTime().minusDays(1);
            LatestEndTime = DateTimeMaker.randomDateTimeBeforeTime(beforeTime);
            EarliestStartTime = DateTimeMaker.randomDateTimeBeforeTime(LatestEndTime);
        } else {
            DateTime afterTime = inOncePeriodEvent.getLatestPossibleEndTime().plusDays(1);
            EarliestStartTime = DateTimeMaker.randomDateTimeAfterTime(afterTime);
            LatestEndTime = DateTimeMaker.randomDateTimeAfterTime(EarliestStartTime);
        }
        if (inType.equals(OnceDayEvent.class)) {
            OnceDayEvent newOnceDayEvent = (OnceDayEvent) rEvent;
            newOnceDayEvent.setEarliestPossibleDate(EarliestStartTime);
            newOnceDayEvent.setLatestPossibleDate(LatestEndTime);
        } else if (rEvent instanceof OncePeriodEvent) {
            OncePeriodEvent newOncePeriodEvent = (OncePeriodEvent) rEvent;
            newOncePeriodEvent.setEarliestPossibleDateForStart(EarliestStartTime);
            newOncePeriodEvent.setEarliestPossibleDateForEnd(DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime));
            newOncePeriodEvent.setLatestPossibleDateForStart(DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime));
            newOncePeriodEvent.setLatestPossibleDateForEnd(LatestEndTime);
            int maxDuration = 1 + rnd.nextInt(java.lang.Integer.MAX_VALUE);
            newOncePeriodEvent.setMaxDuration(maxDuration);
            newOncePeriodEvent.normalize();
            if (newOncePeriodEvent.getMaxDuration() >= 1) {
                newOncePeriodEvent.setMinDuration(rnd.nextInt(newOncePeriodEvent.getMaxDuration()));
                newOncePeriodEvent.normalize();
            }
        } else if (rEvent instanceof YearlyDayEvent) {
            YearlyDayEvent newYearlyDayEvent = (YearlyDayEvent) rEvent;
            newYearlyDayEvent.setUpForComplexEval();
            for (DayOfYear curDay = DayOfYear.startOfYear; curDay != null; curDay = curDay.plusDaysNoWrap(1)) {
                if (inOncePeriodEvent.containsCould(curDay)) {
                    newYearlyDayEvent.getPossibleDays().remove(curDay);
                }
            }
        } else {
            YearlyPeriodEvent newYearlyPeriodEvent = (YearlyPeriodEvent) rEvent;
            newYearlyPeriodEvent.setUpForComplexEval();
            for (DayOfYear curDay = DayOfYear.startOfYear; curDay != null; curDay = curDay.plusDaysNoWrap(1)) {
                if (inOncePeriodEvent.containsCould(curDay)) {
                    newYearlyPeriodEvent.getStartPossibleDays().remove(curDay);
                    newYearlyPeriodEvent.getEndPossibleDays().remove(curDay);
                }
            }
        }

        return rEvent;
    }

    //todo: add year events and change the input to PeriodEvent
    public static OncePeriodEvent randomOverlapsEvent(OncePeriodEvent inOncePeriodEvent) {
        DateTime EarliestStartTime;
        DateTime LatestEndTime;
        DateTime middle1;
        DateTime middle2;
        if (rnd.nextBoolean()) {
            DateTime interTime = inOncePeriodEvent.getEarliestPossibleEndTime();
            if (interTime.isAfter(inOncePeriodEvent.getLatestPossibleStartTime())) {
                interTime = inOncePeriodEvent.getLatestPossibleStartTime();
            }
            LatestEndTime = DateTimeMaker.randomDateTimeBetweenTimes(interTime.plusDays(1), inOncePeriodEvent.getLatestPossibleEndTime().plusDays(1));
            EarliestStartTime = DateTimeMaker.randomDateTimeBeforeTime(inOncePeriodEvent.getEarliestPossibleStartTime().minusDays(1));
            middle1 = DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime);
            middle2 = DateTimeMaker.randomDateTimeBetweenTimes(interTime, LatestEndTime);
        } else {
            DateTime interTime = inOncePeriodEvent.getLatestPossibleStartTime();
            if (interTime.isBefore(inOncePeriodEvent.getEarliestPossibleEndTime())) {
                interTime = inOncePeriodEvent.getEarliestPossibleEndTime();
            }
            EarliestStartTime = DateTimeMaker.randomDateTimeBetweenTimes(inOncePeriodEvent.getEarliestPossibleStartTime().minusDays(1), interTime.minusDays(1));
            LatestEndTime = DateTimeMaker.randomDateTimeAfterTime(inOncePeriodEvent.getLatestPossibleEndTime().plusDays(1));
            middle1 = DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, interTime);
            middle2 = DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime);
        }
        OncePeriodEvent newOncePeriodEvent = new OncePeriodEvent();
        newOncePeriodEvent.setEarliestPossibleDateForStart(EarliestStartTime);
        newOncePeriodEvent.setLatestPossibleDateForStart(middle1);
        newOncePeriodEvent.setEarliestPossibleDateForEnd(middle2);
        newOncePeriodEvent.setLatestPossibleDateForEnd(LatestEndTime);
        int maxDuration = 1 + rnd.nextInt(java.lang.Integer.MAX_VALUE);
        newOncePeriodEvent.setMaxDuration(maxDuration);
        newOncePeriodEvent.normalize();
        if (newOncePeriodEvent.getMaxDuration() >= 1) {
            newOncePeriodEvent.setMinDuration(rnd.nextInt(newOncePeriodEvent.getMaxDuration()));
            newOncePeriodEvent.normalize();
        }
        return newOncePeriodEvent;
    }

    //todo: add year events and change the input to PeriodEvent
    public static Event randomCouldOverlapsEvent(OncePeriodEvent inOncePeriodEvent) {
        int method = Main.rnd.nextInt(2);
        if (method == 0) {
            return randomCouldOverlapsEvent(inOncePeriodEvent, OnceDayEvent.class);
        } else {
            return randomCouldOverlapsEvent(inOncePeriodEvent, OncePeriodEvent.class);
        }
    }

    //todo: add year events and change the input to PeriodEvent
    public static <T extends Event> T randomCouldOverlapsEvent(OncePeriodEvent inOncePeriodEvent, Class<T> inType) {
        Event rEvent;
        DateTime EarliestStartTime;
        DateTime LatestEndTime;
        if (rnd.nextBoolean()) {
            LatestEndTime = DateTimeMaker.randomDateTimeBetweenTimes(inOncePeriodEvent.getEarliestPossibleStartTime(), inOncePeriodEvent.getLatestPossibleStartTime());
            EarliestStartTime = DateTimeMaker.randomDateTimeBeforeTime(LatestEndTime);
        } else {
            EarliestStartTime = DateTimeMaker.randomDateTimeBetweenTimes(inOncePeriodEvent.getEarliestPossibleEndTime(), inOncePeriodEvent.getLatestPossibleEndTime());
            LatestEndTime = DateTimeMaker.randomDateTimeAfterTime(EarliestStartTime);
        }
        if (inType.equals(OnceDayEvent.class)) {
            OnceDayEvent newOnceDayEvent = new OnceDayEvent();
            newOnceDayEvent.setEarliestPossibleDate(EarliestStartTime);
            newOnceDayEvent.setLatestPossibleDate(LatestEndTime);
            rEvent = newOnceDayEvent;
        } else {
            OncePeriodEvent newOncePeriodEvent = new OncePeriodEvent();
            newOncePeriodEvent.setEarliestPossibleDateForStart(EarliestStartTime);
            newOncePeriodEvent.setEarliestPossibleDateForEnd(DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime));
            newOncePeriodEvent.setLatestPossibleDateForStart(DateTimeMaker.randomDateTimeBetweenTimes(EarliestStartTime, LatestEndTime));
            newOncePeriodEvent.setLatestPossibleDateForEnd(LatestEndTime);
            int maxDuration = 1 + rnd.nextInt(java.lang.Integer.MAX_VALUE);
            newOncePeriodEvent.setMaxDuration(maxDuration);
            newOncePeriodEvent.normalize();
            if (newOncePeriodEvent.getMaxDuration() >= 1) {
                newOncePeriodEvent.setMinDuration(rnd.nextInt(newOncePeriodEvent.getMaxDuration()));
                newOncePeriodEvent.normalize();
            }
            rEvent = newOncePeriodEvent;
        }
        return (T) rEvent;
    }

    private static void trimDays(NavigableSet<DayOfYear> possibleDays) {
        DayOfYear startDay = new DayOfYear(1 + rnd.nextInt(12), 1 + rnd.nextInt(28));
        DayOfYear endDay = new DayOfYear(1 + rnd.nextInt(12), 1 + rnd.nextInt(28));
        possibleDays.clear();
        for (DayOfYear curDay = startDay; !curDay.equals(endDay); curDay = curDay.plusDays(1)) {
            possibleDays.add(curDay);
        }
    }

    public static OncePeriodEvent randomOncePeriodEvent() {
        try {
            return randomEventOfType(OncePeriodEvent.class);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(RandomEvents.class.getName()).log(Level.SEVERE, null, ex);
            return new OncePeriodEvent();
        }
    }

    public static OncePeriodEvent randomOncePeriodEvent(int mod) {
        try {
            return randomEventOfType(OncePeriodEvent.class, mod);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(RandomEvents.class.getName()).log(Level.SEVERE, null, ex);
            return new OncePeriodEvent();
        }
    }

    public static Event randomEvent() {
        try {
            return randomEventOfType(Event.class);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(RandomEvents.class.getName()).log(Level.SEVERE, null, ex);
            return new OncePeriodEvent();
        }
    }
}
