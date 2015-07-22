/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.Relation;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.TwoEventTimeConstraint;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.DayOfYear;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.joda.time.DateTime;


//TODO: add redundant Contraints
/**
 *
 * @author Steven Owens
 */
public class Timeline extends EventContainer
        implements Serializable {

    private static final long serialVersionUID = 13L;
    private static final Logger LOG = Logger.getLogger(Timeline.class.getName());

    //marker Collections not necessarily how the data will actually be stored 
    private final Collection<TimeConstraint> unmodifiableConstraints;
    private final Collection<Event> unmodifiableEvents;
    private final java.util.Map<Event, Set<TimeConstraint>> EventConstraintMap;
    public final java.util.Comparator<EventContainer> mComparator;

    {
        org.apache.commons.collections4.comparators.ComparatorChain<EventContainer> newComparatorChain = new org.apache.commons.collections4.comparators.ComparatorChain<>();
        newComparatorChain.addComparator(new EventContainer.MultSizeComparator());
        newComparatorChain.addComparator(new EventContainer.ConstraintsComparator());
        newComparatorChain.addComparator(new EventContainer.EventsComparator());
        newComparatorChain.addComparator(new com.civprod.Comparers.RandomSortComparer<>());
        this.mComparator = newComparatorChain;
    }

    public Timeline() {
        this.EventConstraintMap = new java.util.HashMap<>(this.Events.size());
        this.unmodifiableEvents = java.util.Collections.unmodifiableCollection(this.Events);
        this.unmodifiableConstraints = java.util.Collections.unmodifiableCollection(this.Constraints);
        this.label = "master";
        java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).setParent(Main.AppGlobalLogger);
        java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).setUseParentHandlers(true);
    }

    @Override
    public java.util.Collection<TimeConstraint> getConstraintsOnEvent(Event inEvent) {
        java.util.Collection<TimeConstraint> rCollection = this.EventConstraintMap.get(inEvent);
        if (rCollection == null) {
            rCollection = new java.util.ArrayList<>(0);
        }
        return rCollection;
    }

    public java.util.Collection<OnceEvent> afterEventAre(OnceEvent inEvent) {
        //An event is after another Event if theres a Relation between them with firstEpisode == inEvent or 
        //if the earliestPossibleDate of the possible Event is after latestPossibleDate of inEvent
        int initcap = this.Events.size() / 4;
        Collection<OnceEvent> rCollection = new java.util.ArrayList<>(initcap);
        rCollection.add(inEvent);
        Collection<TimeConstraint> PossibleConstraints = this.getConstraintsOnEvent(inEvent);
        while (!PossibleConstraints.isEmpty()) {
            Collection<TimeConstraint> newConstraints = new java.util.ArrayList<>(PossibleConstraints.size());
            for (TimeConstraint curTimeConstraint : PossibleConstraints) {
                if (curTimeConstraint instanceof TwoEventTimeConstraint) {
                    TwoEventTimeConstraint curTwoEventTimeConstraint = (TwoEventTimeConstraint) curTimeConstraint;
                    if (curTwoEventTimeConstraint.isStrictlyBefore() && (curTwoEventTimeConstraint.getFirstEvent() instanceof OnceEvent) && (curTwoEventTimeConstraint.getFirstEvent() instanceof OnceEvent)) {
                        OnceEvent firstEvent = (OnceEvent) curTwoEventTimeConstraint.getFirstEvent();
                        OnceEvent secondEvent = (OnceEvent) curTwoEventTimeConstraint.getSecondEvent();
                        if (rCollection.contains(firstEvent)) {
                            if (!rCollection.contains(secondEvent)) {
                                rCollection.add(secondEvent);
                                newConstraints.addAll(this.getConstraintsOnEvent(curTwoEventTimeConstraint.getSecondEvent()));
                            }
                        }
                    }
                }
            }
            PossibleConstraints = newConstraints;
        }
        rCollection.addAll(this.Events.stream().filter((curEvent) -> (curEvent instanceof OnceEvent)).map((curEvent) -> (OnceEvent) curEvent).filter((curOnceEvent) -> (curOnceEvent.getEarliestPossibleStartTime().isAfter(inEvent.getLatestPossibleEndTime()))).filter((curOnceEvent) -> (!rCollection.contains(curOnceEvent))).collect(Collectors.toList()));
        rCollection.remove(inEvent);
        return rCollection;
    }

    public java.util.Collection<OnceEvent> beforeEventAre(OnceEvent inEvent) {
        //An event is before another Event if theres a Relation between them with secondEpisode == inEvent or 
        //if the latestPossibleDate of the possible Event is before earliestPossibleDate of inEvent
        int initcap = this.Events.size() / 4;
        Collection<OnceEvent> rCollection = new java.util.ArrayList<>(initcap);
        rCollection.add(inEvent);
        Collection<TimeConstraint> PossibleConstraints = this.getConstraintsOnEvent(inEvent);
        while (!PossibleConstraints.isEmpty()) {
            Collection<TimeConstraint> newConstraints = new java.util.ArrayList<>(PossibleConstraints.size());
            for (TimeConstraint curTimeConstraint : PossibleConstraints) {
                if (curTimeConstraint instanceof TwoEventTimeConstraint) {
                    TwoEventTimeConstraint curTwoEventTimeConstraint = (TwoEventTimeConstraint) curTimeConstraint;
                    if (curTwoEventTimeConstraint.isStrictlyBefore() && (curTwoEventTimeConstraint.getFirstEvent() instanceof OnceEvent) && (curTwoEventTimeConstraint.getFirstEvent() instanceof OnceEvent)) {
                        OnceEvent firstEvent = (OnceEvent) curTwoEventTimeConstraint.getFirstEvent();
                        OnceEvent secondEvent = (OnceEvent) curTwoEventTimeConstraint.getSecondEvent();
                        if (rCollection.contains(secondEvent)) {
                            if (!rCollection.contains(firstEvent)) {
                                rCollection.add(firstEvent);
                                newConstraints.addAll(this.getConstraintsOnEvent(curTwoEventTimeConstraint.getFirstEvent()));
                            }
                        }
                    }
                }
            }
            PossibleConstraints = newConstraints;
        }
        rCollection.addAll(this.Events.stream().filter((curEvent) -> (curEvent instanceof OnceEvent)).map((curEvent) -> (OnceEvent) curEvent).filter((curOnceEvent) -> (curOnceEvent.getLatestPossibleEndTime().isBefore(inEvent.getEarliestPossibleStartTime()))).filter((curOnceEvent) -> (!rCollection.contains(curOnceEvent))).collect(Collectors.toList()));
        rCollection.remove(inEvent);
        return rCollection;
    }

    public java.util.Collection<OnceEvent> betweenEventsAre(OnceEvent inEvent1, OnceEvent inEvent2) {
        //build a Collection(afterEvent1) events proveably after inEvent1 via Relations. optzime by not adding Events whos earliestPossibleDate is after latestPossibleDate of inEvent2

        //build a Collection(beforeEvent2) events proveably before inEvent2 via Relations. optzime by not adding Events whos latestPossibleDate is before earliestPossibleDate of inEvent1
        //add events in the interction(via retainall) of the two privous Collections
        //add events if the latestPossibleDate of the possible Event is before earliestPossibleDate of inEvent2 and are either in afterEvent1 or the earliestPossibleDate is after latestPossibleDate of inEvent1
        //add events if the earliestPossibleDate of the possible Event is after latestPossibleDate of inEvent1 and are either in beforeEvent2 of he latestPossibleDate is before earliestPossibleDate of inEvent2
        java.util.Collection<OnceEvent> rCollection = this.afterEventAre(inEvent1);
        rCollection.retainAll(this.beforeEventAre(inEvent2));
        return rCollection;
    }

    public java.util.Collection<OnceEvent> afterEventCouldBe(OnceEvent inEvent) {
        //An event is after another Event if there isn't Relation between them with secondEpisode == inEvent and
        //the latestPossibleDate of the possible Event is after earliestPossibleDate of inEvent
        int initcap = this.Events.size() / 3;
        Collection<OnceEvent> notCollection = new java.util.ArrayList<>(initcap);
        notCollection.add(inEvent);
        Collection<TimeConstraint> PossibleConstraints = this.getConstraintsOnEvent(inEvent);
        while (!PossibleConstraints.isEmpty()) {
            Collection<TimeConstraint> newConstraints = new java.util.ArrayList<>(PossibleConstraints.size());
            for (TimeConstraint curTimeConstraint : PossibleConstraints) {
                if (curTimeConstraint instanceof TwoEventTimeConstraint) {
                    TwoEventTimeConstraint curTwoEventTimeConstraint = (TwoEventTimeConstraint) curTimeConstraint;
                    if (curTwoEventTimeConstraint.isStrictlyBefore() && (curTwoEventTimeConstraint.getFirstEvent() instanceof OnceEvent) && (curTwoEventTimeConstraint.getFirstEvent() instanceof OnceEvent)) {
                        OnceEvent firstEvent = (OnceEvent) curTwoEventTimeConstraint.getFirstEvent();
                        OnceEvent secondEvent = (OnceEvent) curTwoEventTimeConstraint.getSecondEvent();
                        if (notCollection.contains(secondEvent)) {
                            if (!notCollection.contains(firstEvent)) {
                                notCollection.add(firstEvent);
                                newConstraints.addAll(this.getConstraintsOnEvent(curTwoEventTimeConstraint.getFirstEvent()));
                            }
                        }
                    }
                }
            }
            PossibleConstraints = newConstraints;
        }
        notCollection.remove(inEvent);
        Collection<OnceEvent> rCollection = new java.util.ArrayList<>(initcap);
        rCollection.addAll(this.Events.stream().filter((curEvent) -> (curEvent instanceof OnceEvent)).map((curEvent) -> (OnceEvent) curEvent).filter((curOnceEvent) -> (curOnceEvent.getLatestPossibleEndTime().isAfter(inEvent.getEarliestPossibleStartTime()))).filter((curOnceEvent) -> (!rCollection.contains(curOnceEvent))).collect(Collectors.toList()));
        rCollection.removeAll(notCollection);
        return rCollection;
    }

    public java.util.Collection<OnceEvent> beforeEventCouldBe(OnceEvent inEvent) {
        //An event is after another Event if there isn't Relation between them with firstEpisode == inEvent and
        //the earliestPossibleDate of the possible Event is before latestPossibleDate of inEvent
        int initcap = this.Events.size() / 3;
        Collection<OnceEvent> notCollection = new java.util.ArrayList<>(initcap);
        notCollection.add(inEvent);
        Collection<TimeConstraint> PossibleConstraints = this.getConstraintsOnEvent(inEvent);
        while (!PossibleConstraints.isEmpty()) {
            Collection<TimeConstraint> newConstraints = new java.util.ArrayList<>(PossibleConstraints.size());
            for (TimeConstraint curTimeConstraint : PossibleConstraints) {
                if (curTimeConstraint instanceof TwoEventTimeConstraint) {
                    TwoEventTimeConstraint curRelation = (TwoEventTimeConstraint) curTimeConstraint;
                    if (curRelation.isStrictlyBefore() && notCollection.contains(curRelation.getFirstEvent()) && !notCollection.contains(curRelation.getSecondEvent())) {
                        notCollection.add((OnceEvent) curRelation.getSecondEvent());
                        newConstraints.addAll(this.getConstraintsOnEvent(curRelation.getSecondEvent()));
                    }
                }
            }
            PossibleConstraints = newConstraints;
        }
        notCollection.remove(inEvent);
        Collection<OnceEvent> rCollection = new java.util.ArrayList<>(initcap);
        rCollection.addAll(this.Events.stream().filter((curEvent) -> (curEvent instanceof OnceEvent)).map((curEvent) -> (OnceEvent) curEvent).filter((curOnceEvent) -> (curOnceEvent.getEarliestPossibleStartTime().isBefore(inEvent.getLatestPossibleEndTime()))).filter((curOnceEvent) -> (!rCollection.contains(curOnceEvent))).collect(Collectors.toList()));
        rCollection.removeAll(notCollection);
        return rCollection;
    }

    public java.util.Collection<OnceEvent> betweenEventsCouldBe(OnceEvent inEvent1, OnceEvent inEvent2) {
        java.util.Collection<OnceEvent> rCollection = this.afterEventCouldBe(inEvent1);
        rCollection.retainAll(this.beforeEventCouldBe(inEvent2));
        return rCollection;
    }

    /**
     * worst case BigO(Events.getSize() * (#Relation in Constraints)^3 *
     * Constraints.getSize()) best case BigO(Events.getSize() *
     * Constraints.getSize())
     *
     * @return
     */
    @Deprecated
    public int placeEventsOld() {
        int state = SUCCESS;
        //BigO((#Relation in Constraints)^3 * Constraints.getSize())
        while (!this.ConstraintsSatisfied()) {
            //BigO(Constraints.getSize())
            if (this.applyConstraints()) {
                return UNSATISFIABLE_CONSTRAINTS;
            }
            this.normalizeEvents();
            for (Event curEvent : this.getEvents()) {
                if (!curEvent.isValid()) {
                    return UNSATISFIABLE_CONSTRAINTS;
                }
            }
        }
        //BigO(Events.getSize())
        for (Event curEvent : this.Events) {
            if (curEvent instanceof OnceEvent) {
                OnceEvent curOnceEvent = (OnceEvent) curEvent;
                if (curOnceEvent.getEarliestPossibleStartTime().isAfter(curOnceEvent.getLatestPossibleEndTime())) {
                    java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).severe(curEvent.toLongString());
                    return UNSATISFIABLE_CONSTRAINTS;
                }
            }
            if (!curEvent.isValid()) {
                java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).severe(curEvent.toLongString());
                return UNSATISFIABLE_CONSTRAINTS;
            }
        }

        List<Event> EventsCopy = new java.util.ArrayList<>(this.Events);
        java.util.Random rnd = new java.util.Random();
        //BigO(Events.getSize() * (#Relation in Constraints)^3 * Constraints.getSize())
        while (!EventsCopy.isEmpty()) {
            Event eventToPlace = EventsCopy.get(rnd.nextInt(EventsCopy.size()));
            double minSize = 365 * 8000;
            for (Event curEvent : EventsCopy) {
                if (!curEvent.isValid()) {
                    java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).severe(curEvent.toLongString());
                    return FAILURE;
                } else {
                    double curSize = curEvent.sizeAdj();
                    if (curSize < minSize) {
                        minSize = curSize;
                        eventToPlace = curEvent;
                    }
                }
            }
            EventsCopy.remove(eventToPlace);
            java.util.logging.Logger.getLogger(Timeline.class.getName()).log(Level.INFO, "placing {0}", eventToPlace.getName());
            if (eventToPlace instanceof OnceDayEvent) {
                OnceDayEvent eventToPlaceDayEvent = (OnceDayEvent) eventToPlace;
                DateTime selectedDateTime = null;
                List<DateTime> possibleDays = new java.util.ArrayList<>(1);
                possibleDays.addAll(eventToPlaceDayEvent.getSuggestedDays().stream().map((curDate) -> curDate.withHourOfDay(12)).filter((curDatemod) -> (curDatemod.isAfter(eventToPlaceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1))
                        && curDatemod.isBefore(eventToPlaceDayEvent.getLatestPossibleEndTime().withHourOfDay(23))
                        && eventToPlaceDayEvent.getDayAcceptor().accept(curDatemod))).collect(Collectors.toList()));
                if (possibleDays.isEmpty()) {
                    DateTime endDate = eventToPlaceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                    //BigO(endDate - eventToPlace.getEarliestPossibleStartTime())
                    for (DateTime curDate = eventToPlaceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1); curDate.isBefore(endDate); curDate = curDate.plusDays(1)) {
                        if (eventToPlaceDayEvent.getDayAcceptor().accept(curDate)) {
                            possibleDays.add(curDate);
                        }
                    }
                }
                if (possibleDays.isEmpty()) {
                    /*
                     DateTime endDate = eventToPlaceDayEvent.getLatestPossibleEndTime().withHourOfDay(23);
                     //BigO(endDate - eventToPlace.getEarliestPossibleStartTime())
                     for (DateTime curDate = eventToPlaceDayEvent.getEarliestPossibleStartTime().withHourOfDay(1); curDate.isBefore(endDate); curDate = curDate.plusDays(1)) {
                     if (eventToPlaceDayEvent.getDayAcceptor().accept(curDate)) {
                     possibleDays.add(curDate);
                     }
                     }
                     */
                    java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).severe(eventToPlace.toLongString());
                    return FAILURE;
                }
                selectedDateTime = possibleDays.get(rnd.nextInt(possibleDays.size()));
                eventToPlaceDayEvent.setEarliestPossibleDate(selectedDateTime);
                eventToPlaceDayEvent.setLatestPossibleDate(selectedDateTime);
            } else if (eventToPlace instanceof YearlyDayEvent) {
                YearlyDayEvent eventToPlaceDayEvent = (YearlyDayEvent) eventToPlace;
                DayOfYear selectedDateTime = null;
                List<DayOfYear> possibleDays = new java.util.ArrayList<>(1);
                possibleDays.addAll(eventToPlaceDayEvent.getSuggestedDays().stream().filter((curDate) -> (eventToPlaceDayEvent.getPossibleDays().contains(curDate)
                        && eventToPlaceDayEvent.getDayAcceptor().accept(curDate))).collect(Collectors.toList()));
                if (possibleDays.isEmpty()) {
                    possibleDays.addAll(eventToPlaceDayEvent.getPossibleDays().stream().filter((curDate) -> (eventToPlaceDayEvent.getDayAcceptor().accept(curDate))).collect(Collectors.toList()));
                }
                if (possibleDays.isEmpty()) {
                    java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).severe(eventToPlace.toLongString());
                    return FAILURE;
                }
                selectedDateTime = possibleDays.get(rnd.nextInt(possibleDays.size()));
                eventToPlaceDayEvent.getPossibleDays().clear();
                eventToPlaceDayEvent.getPossibleDays().add(selectedDateTime);
            } else if (eventToPlace instanceof OncePeriodEvent) {
                //ToDo: fillin
            } else if (eventToPlace instanceof YearlyPeriodEvent) {
                //ToDo: fillin
            }
            //BigO((#Relation in Constraints)^3 * Constraints.getSize())
            while (!this.ConstraintsSatisfied()) {
                //BigO(Constraints.getSize())
                if (this.applyConstraints()) {
                    return FAILURE;
                }
                this.normalizeEvents();
                for (Event curEvent : this.getEvents()) {
                    if (!curEvent.isValid()) {
                        return FAILURE;
                    }
                }
            }
        }
        return state;
    }

    @SuppressWarnings("unchecked")
    public int placeEvents() {
        int state = SUCCESS;
        java.util.logging.Logger.getLogger(Timeline.class.getName()).info("applyConstraints");
        //BigO((#Relation in Constraints)^3 * Constraints.getSize())
        if (this.applyConstraintsLevel1Sure()) {
            return UNSATISFIABLE_CONSTRAINTS;
        }
        //BigO(Events.getSize())
        for (Event curEvent : this.Events) {
            if (curEvent instanceof OnceEvent) {
                OnceEvent curOnceEvent = (OnceEvent) curEvent;
                if (curOnceEvent.getEarliestPossibleStartTime().isAfter(curOnceEvent.getLatestPossibleEndTime())) {
                    java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).severe(curEvent.toLongString());
                    return UNSATISFIABLE_CONSTRAINTS;
                }
            }
            if (!curEvent.isValid()) {
                java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).severe(curEvent.toLongString());
                return UNSATISFIABLE_CONSTRAINTS;
            }
        }

        List<Event> EventsCopy = new java.util.ArrayList<>(this.Events);
        java.util.Random rnd = new java.util.Random();

        java.util.logging.Logger.getLogger(Timeline.class.getName()).info("addPossibleDays");
        //BigO(Events.getSize())
        this.setUpForComplexEval();
        //java.util.logging.Logger.getLogger(Timeline.class.getName()).info("limiting days");
        /*int rState = applyConstraintsLevel2Sure(java.util.logging.Logger.getLogger(Timeline.class.getName()), false);
         if (rState == 1) {
         return UNSATISFIABLE_CONSTRAINTS;
         } else if (rState == FAILURE) {
         return FAILURE;
         }*/
        java.util.logging.Logger.getLogger(Timeline.class.getName()).info("placing events");
        boolean eventplaced = false;
        while (!EventsCopy.isEmpty()) {
            int rState = this.applyConstraintsLevel2Sure(java.util.logging.Logger.getLogger(Timeline.class.getName()), eventplaced);
            if (rState == 1) {
                if (eventplaced) {
                    return FAILURE;
                } else {
                    return UNSATISFIABLE_CONSTRAINTS;
                }
            } else if (rState == FAILURE) {
                return FAILURE;
            }
            Event eventToPlace = EventsCopy.get(rnd.nextInt(EventsCopy.size()));
            double minDays = 365 * 8000;
            boolean foundPref = false;
            for (Event curEvent : EventsCopy) {
                double PossiblePlacesToPlaceEvent;
                if (curEvent instanceof DayEvent) {
                    DayEvent curDayEvent = (DayEvent) curEvent;
                    NavigableSet<? extends Object> curPossibleDays = curDayEvent.getPossibleDays();
                    if (curPossibleDays.isEmpty()) {
                        java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).severe(curEvent.toLongString());
                        if (eventplaced) {
                            return FAILURE;
                        } else {
                            return UNSATISFIABLE_CONSTRAINTS;
                        }
                    }
                    java.util.Collection<Object> temp = new java.util.ArrayList<>(curDayEvent.getSuggestedDays());
                    temp.retainAll(curPossibleDays);
                    if (!temp.isEmpty()) {
                        if (!foundPref) {
                            minDays = 365 * 8000;
                        }
                        foundPref = true;
                        if (temp.size() < minDays) {
                            minDays = temp.size();
                            eventToPlace = curEvent;
                        } else if ((temp.size() == minDays) && rnd.nextBoolean()) {
                            eventToPlace = curEvent;
                        }
                    }
                }
                PossiblePlacesToPlaceEvent = curEvent.sizeAdj();
                if ((!foundPref) && (PossiblePlacesToPlaceEvent < minDays)) {
                    minDays = PossiblePlacesToPlaceEvent;
                    eventToPlace = curEvent;
                } else if ((!foundPref) && (PossiblePlacesToPlaceEvent == minDays) && rnd.nextBoolean()) {
                    eventToPlace = curEvent;
                }
            }

            EventsCopy.remove(eventToPlace);
            java.util.logging.Logger.getLogger(Timeline.class.getName()).log(Level.INFO, "Placing ''{0}''", eventToPlace.getName());
            eventToPlace.normalize();
            if (eventToPlace instanceof OnceDayEvent) {
                OnceDayEvent eventToPlaceDayEvent = ((OnceDayEvent) eventToPlace);
                DateTime selectedDateTime = null;
                List<DateTime> possibleDays = new java.util.ArrayList<>();
                for (DateTime curDate : eventToPlaceDayEvent.getSuggestedDays()) {
                    if (eventToPlaceDayEvent.getPossibleDays().contains(curDate)) {
                        possibleDays.add(curDate);
                    }
                }
                if (possibleDays.isEmpty()) {
                    possibleDays.addAll(eventToPlaceDayEvent.getPossibleDays());
                }
                selectedDateTime = possibleDays.get(rnd.nextInt(possibleDays.size()));
                eventToPlaceDayEvent.setEarliestPossibleDate(selectedDateTime);
                eventToPlaceDayEvent.setLatestPossibleDate(selectedDateTime);
            } else if (eventToPlace instanceof YearlyDayEvent) {
                YearlyDayEvent eventToPlaceDayEvent = ((YearlyDayEvent) eventToPlace);
                DayOfYear selectedDayOfYear = null;
                List<DayOfYear> possibleDays = new java.util.ArrayList<>();
                for (DayOfYear curDate : eventToPlaceDayEvent.getSuggestedDays()) {
                    if (eventToPlaceDayEvent.getPossibleDays().contains(curDate)) {
                        possibleDays.add(curDate);
                    }
                }
                if (possibleDays.isEmpty()) {
                    possibleDays.addAll(eventToPlaceDayEvent.getPossibleDays());
                }
                selectedDayOfYear = possibleDays.get(rnd.nextInt(possibleDays.size()));
                eventToPlaceDayEvent.getPossibleDays().clear();
                eventToPlaceDayEvent.getPossibleDays().add(selectedDayOfYear);
            } else if (eventToPlace instanceof OncePeriodEvent) {
                OncePeriodEvent eventToPlaceOncePeriodEvent = (OncePeriodEvent) eventToPlace;
                List<OncePeriodEvent.OncePeriodEventPlacement> possiblePlacements = new java.util.ArrayList<>();
                for (OncePeriodEvent.OncePeriodEventPlacement curPlacement : eventToPlaceOncePeriodEvent.getPlacements()) {
                    if (eventToPlaceOncePeriodEvent.getStartDayAcceptor().accept(curPlacement.startDay)
                            && eventToPlaceOncePeriodEvent.getEndDayAcceptor().accept(curPlacement.endDay)
                            && eventToPlaceOncePeriodEvent.isValid()) {
                        possiblePlacements.add(curPlacement);
                    }
                }
                OncePeriodEvent.OncePeriodEventPlacement selectedPlacement = possiblePlacements.get(rnd.nextInt(possiblePlacements.size()));
                eventToPlaceOncePeriodEvent.setViaPlacement(selectedPlacement);
            } else if (eventToPlace instanceof YearlyPeriodEvent) {
                YearlyPeriodEvent eventToPlaceSeason = (YearlyPeriodEvent) eventToPlace;
                List<YearlyPeriodEvent.YearlyPeriodEventPlacement> possiblePlacements = new java.util.ArrayList<>();
                for (YearlyPeriodEvent.YearlyPeriodEventPlacement curPlacement : eventToPlaceSeason.getPlacements()) {
                    if (eventToPlaceSeason.getStartDayAcceptor().accept(curPlacement.startDay)
                            && eventToPlaceSeason.getEndDayAcceptor().accept(curPlacement.endDay)
                            && eventToPlaceSeason.isValid()) {
                        possiblePlacements.add(curPlacement);
                    }
                }
                YearlyPeriodEvent.YearlyPeriodEventPlacement selectedPlacement = possiblePlacements.get(rnd.nextInt(possiblePlacements.size()));
                eventToPlaceSeason.setViaPlacement(selectedPlacement);
            }
            eventplaced = true;
        }
        return state;
    }

    public int placeEventsNew() {
        int state = SUCCESS;
        boolean applyConstraintsLevel1SureFail = this.applyConstraintsLevel1Sure(java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()));
        if (!applyConstraintsLevel1SureFail) {
            int applyConstraintsLevel2Sure = this.applyConstraintsLevel2Sure(java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()), true);
            if (applyConstraintsLevel2Sure == 0) {
                List<Event> EventList = java.util.Collections.unmodifiableList(new java.util.ArrayList<>(this.Events));
                Map<Event, List<? extends Placement<?>>> masterList = new java.util.HashMap<>(this.Events.size());
                Map<Event, Placement<?>> curEventPlacements = new HashMap<>(EventList.size());
                for (Event curMasterBuildEvent : EventList) {
                    List<? extends Placement<?>> placements = new java.util.ArrayList<>(curMasterBuildEvent.getPlacements());
                    masterList.put(curMasterBuildEvent, placements);
                }
                boolean failedToFindSolution = this.placeEventsBranch(java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()), EventList, masterList, curEventPlacements);
                if (failedToFindSolution) {
                    state = UNSATISFIABLE_CONSTRAINTS;
                } else {
                    this.Events.stream().forEach((curEvent) -> {
                        curEvent.setTo(curEventPlacements.get(curEvent));
                    });
                }
            } else {
                state = applyConstraintsLevel2Sure;
            }
        } else {
            state = UNSATISFIABLE_CONSTRAINTS;
        }
        return state;
    }

    private boolean placeEventsBranch(Logger inLogger, List<Event> inEventList, Map<Event, List<? extends Placement<?>>> inMasterList, Map<Event, Placement<?>> curEventPlacements) {
        //if there no more Events to place then check all placements
        if (inEventList.isEmpty()) {
            boolean passed = true;
            for (TimeConstraint curConstraintOnEvent : this.Constraints) {
                if (!curConstraintOnEvent.inBeta()) {
                    Event[] constrainedEvents = curConstraintOnEvent.getConstrainedEvents();
                    Placement values[] = new Placement[constrainedEvents.length];
                    for (int i = 0; i < constrainedEvents.length; i++) {
                        values[i] = curEventPlacements.get(constrainedEvents[i]);
                    }
                    passed = passed && curConstraintOnEvent.consistentWithConstraint(values);
                    //once false always be false
                    if (!passed) {
                        break;
                    }
                }
            }
            return !passed;
        } else {
            boolean failed = true;
            boolean propgatedTrimming = false;
            Event pickedEvent = inEventList.get(Main.rnd.nextInt(inEventList.size()));
            List<? extends Placement<?>> placements = inMasterList.get(pickedEvent);
            List<Event> newEventList = new java.util.ArrayList<>(inEventList);
            newEventList.remove(pickedEvent);
            newEventList = java.util.Collections.unmodifiableList(newEventList);
            Collection<TimeConstraint> constraintsOnEvent = this.getConstraintsOnEvent(pickedEvent);
            List<? extends Placement<?>> suggestedPlacements = pickedEvent.getSuggestedPlacements();
            suggestedPlacements.retainAll(placements);
            //create copy of masterList for Events in newEventList
            Map<Event, List<? extends Placement<?>>> newMasterList = this.copyMasterList(inMasterList, newEventList, curEventPlacements);
            List<Placement<?>> pickedEventList = new ArrayList<>(1);
            newMasterList.put(pickedEvent, pickedEventList);
            while (failed && (!placements.isEmpty())) {
                Placement curPlacement;
                if (suggestedPlacements.isEmpty()) {
                    curPlacement = placements.get(Main.rnd.nextInt(placements.size()));
                } else {
                    curPlacement = suggestedPlacements.get(Main.rnd.nextInt(suggestedPlacements.size()));
                    suggestedPlacements.remove(curPlacement);
                }
                placements.remove(curPlacement);
                curEventPlacements.put(pickedEvent, curPlacement);
                pickedEventList.clear();
                pickedEventList.add(curPlacement);
                //trim newMasterList
                this.trimMasterList(pickedEvent, curPlacement, constraintsOnEvent, newMasterList);
                //check to see if the Placements so far are still valid
                boolean stillVaild = this.validMasterList(newMasterList);
                if (stillVaild) {
                    boolean failedToFindSolution = this.placeEventsBranch(inLogger, newEventList, newMasterList, curEventPlacements);
                    if (!failedToFindSolution) {
                        failed = false;
                        //found one don't need to find more
                        break;
                    } else {
                        if (!propgatedTrimming) {
                            this.propgateTrimming(inMasterList, inEventList);
                            propgatedTrimming = true;
                            if (!this.validMasterList(inMasterList)) {
                                return true;
                            }
                        }
                    }
                } else {
                    if (!propgatedTrimming) {
                        this.propgateTrimming(inMasterList, inEventList);
                        propgatedTrimming = true;
                        if (!this.validMasterList(inMasterList)) {
                            return true;
                        }
                    }
                }
            }
            return failed;
        }
    }

    @Override
    public boolean addEvent(Event inEvent) {
        boolean rvalue = super.addEvent(inEvent);
        this.EventConstraintMap.put(inEvent, new java.util.HashSet<>(1));
        return rvalue;
    }

    @Override
    public void addEvents(List<? extends Event> collectEvents) {
        super.addEvents(collectEvents);
        //TODO: collect map
        this.EventConstraintMap.putAll(collectEvents.parallelStream().collect(HashMap<Event, Set<TimeConstraint>>::new, (HashMap<Event, Set<TimeConstraint>> t, Event curEvent) -> t.put(curEvent, new java.util.HashSet<>(1)), HashMap<Event, Set<TimeConstraint>>::putAll));
    }

    @Override
    public boolean addTimeConstraint(TimeConstraint inTimeConstraint) {
        boolean rvalue = super.addTimeConstraint(inTimeConstraint);
        Event[] constrainedEvents = inTimeConstraint.getConstrainedEvents();
        for (Event constrainedEvent : constrainedEvents) {
            rvalue = rvalue && this.EventConstraintMap.get(constrainedEvent).add(inTimeConstraint);
        }
        return rvalue;
    }

    @Override
    public void addTimeConstraints(List<? extends TimeConstraint> inTimeConstraints) {
        //super.addTimeConstraints(inTimeConstraints);
        //todo: make mutithreadable
        for (TimeConstraint curTimeConstraint : inTimeConstraints) {
            this.addTimeConstraint(curTimeConstraint);
        }
    }

    @Override
    public boolean removeEvent(Event inEvent) {
        boolean rvalue = super.removeEvent(inEvent);
        this.EventConstraintMap.remove(inEvent);
        return rvalue;
    }

    @Override
    public boolean removeTimeConstraint(TimeConstraint inTimeConstraint) {
        boolean rvalue = super.removeTimeConstraint(inTimeConstraint);
        Event[] constrainedEvents = inTimeConstraint.getConstrainedEvents();
        for (Event constrainedEvent : constrainedEvents) {
            rvalue = rvalue && this.EventConstraintMap.get(constrainedEvent).remove(inTimeConstraint);
        }
        return rvalue;
    }

    /**
     * @return the Events
     */
    @Override
    public Collection<Event> getEvents() {
        return this.unmodifiableEvents;
    }

    /**
     * @return the Constraints
     */
    @Override
    public Collection<TimeConstraint> getConstraints() {
        return this.unmodifiableConstraints;
    }

    @SuppressWarnings("unchecked")
    public <T> T anyEventOfType(final Class<T> inClass) {
        Optional<T> findAny = this.Events.parallelStream()
                .filter((Event curEvent) -> inClass.isInstance(curEvent))
                .map((Event curEvent) -> inClass.cast(curEvent))
                .findAny();
        if (findAny.isPresent()) {
            return findAny.get();
        } else {
            return null;
        }
    }

    @Override
    public SubSet RandomSubSet(int size) {
        java.util.Random rnd = Main.rnd;
        java.util.List<Event> tempEvents = new java.util.ArrayList<>(this.Events);
        java.util.List<Event> newSet = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Event tranEvent = tempEvents.get(rnd.nextInt(tempEvents.size()));
            tempEvents.remove(tranEvent);
            newSet.add(tranEvent);
        }
        SubSet newSubSet = this.ConstructNewSubSet(newSet);
        if (!this.isConnected(newSubSet)) {
            newSubSet = this.RandomSubSet(size);
        }
        return newSubSet;
    }

    public Collection<SubSet> findSubSets(int Size) {
        Set<SubSet> SubSets = new java.util.HashSet<>(this.Events.size());
        List<Event> events = new java.util.ArrayList<>(this.Events);
        int is[] = new int[Size];
        Event selectedEvents[] = new Event[Size];
        for (int i2 = 0; i2 < is.length; i2++) {
            is[i2] = i2;
        }
        while (is[Size - 1] < events.size()) {
            for (int i2 = 0; i2 < is.length; i2++) {
                selectedEvents[i2] = events.get(is[i2]);
            }
            SubSet newSubSet = this.ConstructNewSubSet(selectedEvents);
            if (newSubSet.Constraints.size() >= Size) {
                int twoOrMoreCount = 0;
                twoOrMoreCount = newSubSet.Constraints.stream().filter((curTimeConstraint) -> (!curTimeConstraint.inBeta())).filter((curTimeConstraint) -> (curTimeConstraint.getConstrainedEvents().length >= 2)).map((_item) -> 1).reduce(twoOrMoreCount, Integer::sum);
                if (twoOrMoreCount >= Size) {
                    if (this.isConnected(newSubSet)) {
                        SubSets.add(newSubSet);
                        java.util.logging.Logger.getLogger(Timeline.class.getName()).log(Level.INFO, "SubSets {0} of size {1}", new Object[]{SubSets.size(), Size});
                    }
                }
            }
            is[Size - 1]++;
            int i2 = 0;
            while (((Size - 2 - i2) >= 0) && ((is[Size - 1 - i2] + i2) >= events.size())) {
                i2++;
                is[Size - 1 - i2]++;
            }
            for (int i3 = Size - i2; i3 < is.length; i3++) {
                is[i3] = is[i3 - 1] + 1;
            }
        }
        /*if (Size == this.Events.size()) {
         SubSets.add(new SubSet(this.Constraints, this.Events));
         }*/
        return SubSets;
    }

    public List<SubSet> createEventFromSameTimeOfYearPairs() {
        List<SubSet> rList = new java.util.ArrayList<>(this.Events.size());
        this.createEventFromSameTimeOfYearPairs(rList);
        return rList;
    }

    public void createEventFromSameTimeOfYearPairs(Collection<SubSet> inCollection) {
        //todo:make this mutithreaded
        for (TimeConstraint curTimeConstraint : this.Constraints) {
            if (curTimeConstraint instanceof Relation) {
                Relation curRelation = (Relation) curTimeConstraint;
                if (curRelation.getKind() == Relation.RelationKind.SAME_DAY_OF_YEAR) {
                    SubSet newSubSet = this.ConstructNewSubSet(this.createEventFromEventPair(curRelation.getFirstEvent(), curRelation.getSecondEvent(), curRelation, true, true));
                    newSubSet.setLabel("SL");
                    if (!inCollection.contains(newSubSet)) {
                        inCollection.add(newSubSet);
                    }
                    newSubSet = this.ConstructNewSubSet(this.createEventFromEventPair(curRelation.getFirstEvent(), curRelation.getSecondEvent(), curRelation, true, false));
                    newSubSet.setLabel("S");
                    if (!inCollection.contains(newSubSet)) {
                        inCollection.add(newSubSet);
                    }
                    newSubSet = this.ConstructNewSubSet(this.createEventFromEventPair(curRelation.getFirstEvent(), curRelation.getSecondEvent(), curRelation, false, true));
                    newSubSet.setLabel("L");
                    if (!inCollection.contains(newSubSet)) {
                        inCollection.add(newSubSet);
                    }
                    newSubSet = this.ConstructNewSubSet(this.createEventFromEventPair(curRelation.getFirstEvent(), curRelation.getSecondEvent(), curRelation, false, false));
                    newSubSet.setLabel("");
                    if (!inCollection.contains(newSubSet)) {
                        inCollection.add(newSubSet);
                    }
                }
            }
        }
    }

    public Set<TimeConstraint> findConstraintsBetweenEventPair(Event EventOne, Event EventTwo) {
        Set<TimeConstraint> rSet = new java.util.HashSet<>(0);
        for (TimeConstraint curTimeConstraint : this.getConstraintsOnEvent(EventOne)) {
            if (!curTimeConstraint.inBeta()) {
                List<Event> ConstrainedEvents = java.util.Arrays.asList(curTimeConstraint.getConstrainedEvents());
                if (ConstrainedEvents.contains(EventOne) && ConstrainedEvents.contains(EventTwo)) {
                    rSet.add(curTimeConstraint);
                }
            }
        }
        return rSet;
    }

    public Set<Event> createEventFromEventPair(Event EventOne, Event EventTwo, boolean strict, boolean ordered) {
        return this.createEventFromEventPair(EventOne, EventTwo, new java.util.ArrayList<>(0), strict, ordered);
    }

    public Set<Event> createEventFromEventPair(Event EventOne, Event EventTwo, TimeConstraint exculdeConstraint, boolean strict, boolean ordered) {
        return this.createEventFromEventPair(EventOne, EventTwo, java.util.Collections.singleton(exculdeConstraint), strict, ordered);
    }

    public Set<Event> createEventFromEventPair(Event EventOne, Event EventTwo, Collection<TimeConstraint> exculdeConstraints, boolean strict, boolean ordered) {
        Set<Event> seen = new java.util.HashSet<>(this.Events.size());
        java.util.Queue<EventNode> EventQueue = new java.util.ArrayDeque<>(10);
        seen.add(EventOne);
        EventQueue.add(new EventNode(EventOne, null));
        while (!EventQueue.isEmpty()) {
            EventNode curEventNode = EventQueue.remove();
            Event curEvent = curEventNode.mEvent;
            Set<Event> newEvents = new java.util.HashSet<>(10);
            //todo:make mutithreaded
            for (TimeConstraint curTimeConstraint : this.getConstraintsOnEvent(curEvent)) {
                if (!curTimeConstraint.inBeta()) {
                    if (!exculdeConstraints.contains(curTimeConstraint)) {
                        if (curTimeConstraint.isStrict() || (!strict)) {
                            if (!ordered) {
                                newEvents.addAll(Arrays.asList(curTimeConstraint.getConstrainedEvents()));
                            } else if (curTimeConstraint instanceof TwoEventTimeConstraint) {
                                TwoEventTimeConstraint curTwoEventTimeConstraint = (TwoEventTimeConstraint) curTimeConstraint;
                                if (curTwoEventTimeConstraint.isStrictlyBefore() && curEvent.equals(curTwoEventTimeConstraint.getFirstEvent())) {
                                    newEvents.addAll(Arrays.asList(curTimeConstraint.getConstrainedEvents()));
                                }
                            }
                        }
                    }
                }
            }
            newEvents.removeAll(seen);
            if (newEvents.contains(EventTwo)) {
                Set<Event> newSet = new java.util.HashSet<>(3);
                newSet.add(EventOne);
                newSet.add(EventTwo);
                while (curEventNode != null) {
                    newSet.add(curEventNode.mEvent);
                    curEventNode = curEventNode.mParent;
                }
                return newSet;
            }
            for (Event curNewEvent : newEvents) {
                EventQueue.add(new EventNode(curNewEvent, curEventNode));
                seen.add(curNewEvent);
            }
        }
        return new java.util.HashSet<>(0);
    }

    public List<SubSet> createEventsFromSeedEvents() {
        List<SubSet> rList = new java.util.ArrayList<>(this.Events.size());
        this.createEventsFromSeedEvents(rList);
        return rList;
    }

    public void createEventsFromSeedEvents(Collection<SubSet> inCollection) {
        //todo:make mutithreaded
        for (Event curEvent : this.Events) {
            SubSet newSubSet = this.createSubSetFromSeedEvent(curEvent);
            if (!inCollection.contains(newSubSet)) {
                inCollection.add(newSubSet);
            }
        }
    }

    public SubSet createSubSetFromSeedEvent(Event inEvent) {
        Set<Event> EventSet = new java.util.HashSet<>(10);
        for (TimeConstraint curTimeConstraint : this.getConstraintsOnEvent(inEvent)) {
            EventSet.addAll(Arrays.asList(curTimeConstraint.getConstrainedEvents()));
        }
        return this.ConstructNewSubSet(EventSet);
    }

    @Override
    public SubSet ConstructNewSubSet(Event... seen) {
        SubSet newSubSet = new SubSet(this);
        newSubSet.Events.addAll(Arrays.asList(seen));
        return this.ConstructNewSubSet(newSubSet);
    }

    @Override
    public SubSet ConstructNewSubSet(java.util.Collection<Event> seen) {
        SubSet newSubSet = new SubSet(this);
        newSubSet.Events.addAll(seen);
        return this.ConstructNewSubSet(newSubSet);
    }

    private SubSet ConstructNewSubSet(SubSet inSubSet) {
        inSubSet.Constraints.addAll(inSubSet.Events.parallelStream().map((Event curEvent) -> {
            return this.getConstraintsOnEvent(curEvent).parallelStream()
                    .filter((TimeConstraint curTimeConstraint) -> !curTimeConstraint.inBeta())
                    .filter((TimeConstraint curTimeConstraint) -> {
                        boolean containAll = true;
                        for (Event curConstrainedEvent : curTimeConstraint.getConstrainedEvents()) {
                            if (!inSubSet.Events.contains(curConstrainedEvent)) {
                                containAll = false;
                                break;
                            }
                        }
                        return containAll;
                    }).collect(Collectors.toSet());
        }).collect(HashSet<TimeConstraint>::new, (HashSet<TimeConstraint> t, Set<TimeConstraint> u) -> t.addAll(u), HashSet<TimeConstraint>::addAll));
        return inSubSet;
    }

    @Override
    public boolean isConnected(SubSet newSubSet) {
        Set<Event> seen = new java.util.HashSet<>(0);
        Stack<Event> toVisit = new Stack<>();
        toVisit.add(newSubSet.Events.iterator().next());
        while (!toVisit.empty()) {
            Event curEvent = toVisit.pop();
            for (TimeConstraint curTimeConstraint : this.getConstraintsOnEvent(curEvent)) {
                if (!curTimeConstraint.inBeta()) {
                    boolean containAll = true;
                    for (Event curConstrainedEvent : curTimeConstraint.getConstrainedEvents()) {
                        if (!newSubSet.Events.contains(curConstrainedEvent)) {
                            containAll = false;
                            break;
                        }
                    }
                    if (containAll) {
                        for (Event curConstrainedEvent : curTimeConstraint.getConstrainedEvents()) {
                            if (!curEvent.equals(curConstrainedEvent)) {
                                if (seen.contains(curConstrainedEvent)) {
                                    return true;
                                } else {
                                    if (!toVisit.contains(curConstrainedEvent)) {
                                        toVisit.push(curConstrainedEvent);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            seen.add(curEvent);
        }
        return false;
    }

    @Override
    public boolean applyConstraintsUptoLevel3Sure(java.util.logging.Logger inLogger) {
        boolean fail = this.applyConstraintsLevel1Sure(inLogger);
        if (!fail) {
            fail = (this.applyConstraintsLevel2Sure(inLogger, true) != 0);
        }
        if (!fail) {
            fail = this.applyConstraintsLevel3Sure(inLogger, this.Events);
        }
        return fail;
    }

    private static class EventNode {

        final Event mEvent;
        final EventNode mParent;

        EventNode(Event inEvent, EventNode inParent) {
            this.mEvent = inEvent;
            this.mParent = inParent;
        }
    }
}
