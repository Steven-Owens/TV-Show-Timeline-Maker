/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.TrimMasterListCapableTimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.TwoEventTimeConstraint;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Steven Owens
 */
public abstract class EventContainer {

    public static final int SUCCESS = 0;
    public static final int UNSATISFIABLE_CONSTRAINTS = 1;
    public static final int FAILURE = 2;

    static {
        Logger.getLogger(EventContainer.class.getCanonicalName()).setParent(Main.AppGlobalLogger);
        Logger.getLogger(EventContainer.class.getCanonicalName()).setUseParentHandlers(true);
    }

    protected final Set<TimeConstraint> Constraints;
    protected final Set<Event> Events;
    boolean setUp = false;
    protected String label;

    public EventContainer(Set<TimeConstraint> inConstraints, Set<Event> inEvents) {
        this.Constraints = new java.util.HashSet<>(inConstraints);
        this.Events = new java.util.HashSet<>(inEvents);
        this.label = "";
    }

    public EventContainer() {
        this.Constraints = new java.util.HashSet<>(1);
        this.Events = new java.util.HashSet<>(2);
        this.label = "";
    }

    public int getNumberOfEvents() {
        return this.Events.size();
    }

    public int getNumberOfConstraints() {
        return this.Constraints.size();
    }

    /**
     * BigO(Constraints.getSize())
     *
     * @return true if a Constraint is unsatfiable on a best effort
     */
    public boolean applyConstraints() {
        boolean unsatfiableConstraint = false;
        for (TimeConstraint curTimeConstraint : this.Constraints) {
            if (!curTimeConstraint.inBeta()) {
                try {
                    for (int i = 0; (i < 4) && (!curTimeConstraint.ConstraintSatisfied()); i++) {
                        if (i >= 1) {
                            int a = 1 + 6;
                        }
                        curTimeConstraint.applyConstraint();
                    }
                } catch (Exception ex) {
                    unsatfiableConstraint = true;
                }
                if (!curTimeConstraint.ConstraintSatisfied()) {
                    String errString = curTimeConstraint.toString();
                    for (Event curEvents : curTimeConstraint.getConstrainedEvents()) {
                        errString += "\n" + curEvents.toString();
                    }
                    Logger.getLogger(this.getClass().getCanonicalName()).severe(errString);
                    unsatfiableConstraint = true;
                }
            }
        }
        return unsatfiableConstraint;
    }

    /**
     * BigO(Constraints.getSize())
     *
     * @return true if all Constraints are Satisfied
     */
    public boolean ConstraintsSatisfied() {
        boolean satisfied = true;
        //todo make this mutithreaded
        for (TimeConstraint curTimeConstraint : this.Constraints) {
            if (!curTimeConstraint.inBeta()) {
                satisfied = satisfied && curTimeConstraint.ConstraintSatisfied();
                if (!satisfied) {
                    break;
                }
            }
        }
        return satisfied;
    }

    public boolean applyConstraintsLevel1Sure() {
        return this.applyConstraintsLevel1Sure(null);
    }

    public boolean applyConstraintsLevel1Sure(java.util.logging.Logger inLogger) {
        while (!this.ConstraintsSatisfied()) {
            //BigO(Constraints.getSize())
            if (this.applyConstraints()) {
                return true;
            }
            this.normalizeEvents();
            //todo make this mutithreaded
            for (Event curEvent : this.getEvents()) {
                if (!curEvent.isValid()) {
                    if (inLogger != null) {
                        inLogger.log(Level.INFO, "Invalid: {0}", curEvent.getName());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public int applyConstraintsLevel2Sure() {
        return this.applyConstraintsLevel2Sure(null, false);
    }

    public int applyConstraintsLevel2Sure(java.util.logging.Logger inLogger, boolean onlyChanged) {
        if (!this.setUp) {
            this.setUpForComplexEval();
        }
        int error = 0;
        boolean con = true;
        while (con) {
            con = false;
            for (TimeConstraint curTimeConstraint : this.Constraints) {
                if (!curTimeConstraint.inBeta()) {
                    if ((inLogger != null) && (!onlyChanged)) {
                        inLogger.log(Level.INFO, "applying {0}", curTimeConstraint.toString());
                    }
                    boolean changedSomething;
                    try {
                        changedSomething = curTimeConstraint.complexApplyConstraint();
                    } catch (java.lang.Exception ex) {
                        if (error == 0) {
                            error = FAILURE;
                        }
                        con = false;
                        if (inLogger != null) {
                            inLogger.log(Level.SEVERE, "error in {0}. Error follows:", curTimeConstraint.toString());
                            inLogger.severe(ex.getMessage());
                        }
                        break;
                    }
                    con = con || changedSomething;
                    if ((inLogger != null) && (changedSomething) && (onlyChanged)) {
                        inLogger.log(Level.INFO, "applied {0}", curTimeConstraint.toString());
                    }
                }
            }
            this.normalizeEvents();
            //todo make this mutithreaded
            for (Event curEvent : this.getEvents()) {
                if (!curEvent.isValid()) {
                    if (inLogger != null) {
                        inLogger.log(Level.INFO, "Invalid: {0}", curEvent.getName());
                    }
                    if (error == 0) {
                        error = 1;
                    }
                }
            }
        }
        return error;
    }

    public boolean applyConstraintsLevel3Sure() {
        return this.applyConstraintsUptoLevel3Sure(null);
    }

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

    public final boolean applyConstraintsLevel3Sure(java.util.logging.Logger inLogger, Collection<Event> inEvents) {
        return this.applyConstraintsLevel3Branch(inLogger, inEvents);
    }

    public void normalizeEvents() {
        this.Events.parallelStream().forEach((curEvent) -> {
            curEvent.normalize();
        });
    }

    public void resetEvents() {
        this.Events.parallelStream().forEach((curEvent) -> {
            curEvent.reset();
        });
        this.setUp = false;
    }

    public void setUpForComplexEval() {
        this.Events.parallelStream().forEach((curEvent) -> {
            curEvent.setUpForComplexEval();
        });
        this.setUp = true;
    }

    public boolean addEvent(Event inEvent) {
        boolean rvalue = this.Events.add(inEvent);
        return rvalue;
    }

    public void addEvents(List<? extends Event> collectEvents) {
        this.Events.addAll(collectEvents);
    }

    public boolean addTimeConstraint(TimeConstraint inTimeConstraint) {
        boolean rvalue = this.Constraints.add(inTimeConstraint);
        return rvalue;
    }

    void addTimeConstraints(List<? extends TimeConstraint> inTimeConstraints) {
        this.Constraints.addAll(inTimeConstraints);
    }

    public boolean removeEvent(Event inEvent) {
        boolean rvalue = this.Events.remove(inEvent);
        return rvalue;
    }

    public boolean removeTimeConstraint(TimeConstraint inTimeConstraint) {
        boolean rvalue = this.Constraints.remove(inTimeConstraint);
        return rvalue;
    }

    /**
     * @return the Events
     */
    public Collection<Event> getEvents() {
        return Collections.unmodifiableCollection(this.Events);
    }

    /**
     * @return the Constraints
     */
    public Collection<TimeConstraint> getConstraints() {
        return Collections.unmodifiableCollection(this.Constraints);
    }

    public abstract SubSet RandomSubSet(int size);

    public abstract SubSet ConstructNewSubSet(Event... seen);

    public abstract SubSet ConstructNewSubSet(java.util.Collection<Event> seen);

    public abstract boolean isConnected(SubSet newSubSet);

    public abstract java.util.Collection<TimeConstraint> getConstraintsOnEvent(Event inEvent);

    /**
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }

    //<editor-fold defaultstate="collapsed" desc="branch and bound">
    public final boolean applyConstraintsLevel3Branch(java.util.logging.Logger inLogger) {
        return this.applyConstraintsLevel3Branch(inLogger, this.Events);
    }

    public final boolean applyConstraintsLevel3Branch(java.util.logging.Logger inLogger, Collection<Event> inEvents) {
        boolean fail = false;

        if (!fail) {
            List<Event> EventList = java.util.Collections.unmodifiableList(new java.util.ArrayList<>(inEvents));
            Map<Event, Set<Placement<?>>> safeList = new java.util.HashMap<>(this.Events.size());
            Map<Event, List<? extends Placement<?>>> masterList = new java.util.HashMap<>(this.Events.size());
            //build masterlist
            for (Event curMasterBuildEvent : EventList) {
                List<? extends Placement<?>> placements = new java.util.ArrayList<>(curMasterBuildEvent.getPlacements());
                masterList.put(curMasterBuildEvent, placements);
                //synchronized so it safe to modify
                safeList.put(curMasterBuildEvent, java.util.Collections.synchronizedSet(new java.util.HashSet<Placement<?>>(0)));
            }
            //these don't need to be modifyed from here on out 
            safeList = java.util.Collections.unmodifiableMap(safeList);
            masterList = java.util.Collections.unmodifiableMap(masterList);
            for (Event curEvent : EventList) {
                //create copy of event list minus the curEvent
                List<Event> newEventList = new java.util.ArrayList<>(EventList);
                newEventList.remove(curEvent);
                newEventList = java.util.Collections.unmodifiableList(newEventList);
                Map<Event, Placement<?>> curEventPlacements = new HashMap<>(inEvents.size());
                List<? extends Placement<?>> placements = new java.util.ArrayList<>(masterList.get(curEvent));
                //no need to check Placements we already know are safe
                placements.removeAll(safeList.get(curEvent));
                Collection<TimeConstraint> constraintsOnEvent = this.getConstraintsOnEvent(curEvent);
                for (Placement<?> curPlacement : placements) {
                    curEventPlacements.put(curEvent, curPlacement);
                    //create copy of masterList for Events in newEventList
                    Map<Event, List<? extends Placement<?>>> newMasterList = this.copyMasterList(masterList, newEventList, curEventPlacements);
                    //trim newMasterList
                    this.trimMasterList(curEvent, curPlacement, constraintsOnEvent, newMasterList);
                    //check to see if the Placements so far are still valid
                    boolean stillVaild = this.validMasterList(newMasterList);
                    if (stillVaild) {
                        boolean failedToFindSolution = this.applyConstraintsLevel3Branch(inLogger, newEventList, newMasterList, safeList, curEventPlacements);
                        if (!failedToFindSolution) {
                            safeList.get(curEvent).add(curPlacement);
                        } else {
                            int debug = 1 + 2;
                        }
                    }
                }
                //retain safelist in masterList.get(curEvent) and curEvent.setTo(safelist)
                masterList.get(curEvent).retainAll(safeList.get(curEvent));
                curEvent.setTo(safeList.get(curEvent));
            }
            for (Event checkingEvent : EventList) {
                if (masterList.get(checkingEvent).isEmpty()) {
                    fail = true;
                }
            }
        }

        return fail;
    }

    protected boolean applyConstraintsLevel3Branch(Logger inLogger, List<Event> inEventList, Map<Event, List<? extends Placement<?>>> inMasterList, Map<Event, Set<Placement<?>>> safeList, Map<Event, Placement<?>> curEventPlacements) {
        //if there no more Events to place then check all placements
        if (inEventList.isEmpty()) {
            boolean passed = true;
            for (TimeConstraint curConstraintOnEvent : this.Constraints) {
                if (!curConstraintOnEvent.inBeta()) {
                    Event[] constrainedEvents = curConstraintOnEvent.getConstrainedEvents();
                    Placement<?> values[] = new Placement<?>[constrainedEvents.length];
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
            //this will hold the event with the smallest number of placements. In case of ties first found wins
            Event smallestEvent = java.util.Collections.min(inEventList, (Event o1, Event o2) -> java.lang.Integer.compare(inMasterList.get(o1).size(), inMasterList.get(o2).size()));
            List<Event> newEventList = new java.util.ArrayList<>(inEventList);
            newEventList.remove(smallestEvent);
            newEventList = java.util.Collections.unmodifiableList(newEventList);
            List<? extends Placement<?>> placements = new java.util.ArrayList<>(inMasterList.get(smallestEvent));

            Collection<TimeConstraint> constraintsOnEvent = this.getConstraintsOnEvent(smallestEvent);
            for (Placement<?> curPlacement : placements) {
                curEventPlacements.put(smallestEvent, curPlacement);
                //create copy of masterList for Events in newEventList
                Map<Event, List<? extends Placement<?>>> newMasterList = this.copyMasterList(inMasterList, newEventList, curEventPlacements);
                //trim newMasterList
                this.trimMasterList(smallestEvent, curPlacement, constraintsOnEvent, newMasterList);
                //check to see if the Placements so far are still valid
                boolean stillVaild = this.validMasterList(newMasterList);
                if (stillVaild) {
                    boolean failedToFindSolution = this.applyConstraintsLevel3Branch(inLogger, newEventList, newMasterList, safeList, curEventPlacements);
                    if (!failedToFindSolution) {
                        safeList.get(smallestEvent).add(curPlacement);
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

    //TODO: comment
    protected final Map<Event, List<? extends Placement<?>>> copyMasterList(Map<Event, List<? extends Placement<?>>> inMasterList) {
        return this.copyMasterList(inMasterList, inMasterList.keySet());
    }

    //TODO: comment
    protected final Map<Event, List<? extends Placement<?>>> copyMasterList(Map<Event, List<? extends Placement<?>>> inMasterList, Collection<Event> inEventList) {
        return inEventList.parallelStream().collect(HashMap<Event, List<? extends Placement<?>>>::new, (HashMap<Event, List<? extends Placement<?>>> t, Event u) -> {
            t.put(u, new java.util.ArrayList<>(inMasterList.get(u)));
        }, HashMap::putAll);
    }

    protected final Map<Event, List<? extends Placement<?>>> copyMasterList(Map<Event, List<? extends Placement<?>>> inMasterList, Collection<Event> inEventList, Map<Event, Placement<?>> curEventPlacements) {
        Map<Event, List<? extends Placement<?>>> newMasterList = this.copyMasterList(inMasterList, inEventList);
        //todo:make mutithreaded
        newMasterList.putAll(curEventPlacements.entrySet().parallelStream()
                .filter((Map.Entry<Event, Placement<?>> curEventPlacement) -> !newMasterList.containsKey(curEventPlacement.getKey()))
                .collect(HashMap<Event, List<? extends Placement<?>>>::new, (HashMap<Event, List<? extends Placement<?>>> t,
                                Map.Entry<Event, Placement<?>> curEventPlacement) -> {
                    List<Placement<?>> newList = new java.util.ArrayList<>(1);
                    newList.add(curEventPlacement.getValue());
                    t.put(curEventPlacement.getKey(), newList);
                }, HashMap::putAll));
        return newMasterList;
    }

    //TODO: comment
    protected final void trimMasterList(Event curEvent, Placement<?> curPlacement, Map<Event, List<? extends Placement<?>>> inMasterList) {
        this.trimMasterList(curEvent, curPlacement, this.getConstraintsOnEvent(curEvent), inMasterList);
    }

    //TODO: comment
    protected final void trimMasterList(final Event curEvent, final Placement<?> curPlacement, Collection<TimeConstraint> constraintsOnEvent, Map<Event, List<? extends Placement<?>>> inMasterList) {

        //todo: make this mutithreaded
        for (TimeConstraint curConstraintOnEvent : constraintsOnEvent) {
            if (!curConstraintOnEvent.inBeta()) {
                Event[] constrainedEvents = curConstraintOnEvent.getConstrainedEvents();
                int indexOfCurrentEvent = -1;
                for (int i = 0; i < constrainedEvents.length; i++) {
                    if (curEvent.equals(constrainedEvents[i])) {
                        indexOfCurrentEvent = i;
                    }
                }

                if (curConstraintOnEvent instanceof TrimMasterListCapableTimeConstraint) {
                    //TrimMasterListCapableTimeConstraint do it themselves 
                    TrimMasterListCapableTimeConstraint curAdvancedTimeConstraint = (TrimMasterListCapableTimeConstraint) curConstraintOnEvent;
                    curAdvancedTimeConstraint.trimMasterList(curEvent, curPlacement, inMasterList);
                } else if (curConstraintOnEvent instanceof TwoEventTimeConstraint) {
                    int indexToRemoveFrom = 1 - indexOfCurrentEvent;
                    Event curEventToRemoveFrom = constrainedEvents[indexToRemoveFrom];
                    if (inMasterList.containsKey(curEventToRemoveFrom)) {
                        Map<Event, Placement<?>> tempPlacements = new java.util.HashMap<>(this.Events.size());
                        tempPlacements.put(curEvent, curPlacement);
                        Set<Placement<?>> PlacementsToRemove = new HashSet<>(0);
                        //make mutithreaded
                        int finalIndexOfCurrentEvent = indexOfCurrentEvent;
                        PlacementsToRemove.addAll(inMasterList.get(curEventToRemoveFrom).parallelStream().filter((Placement<?> otherPlacement) -> {
                            Placement<?> values[] = new Placement<?>[2];
                            values[finalIndexOfCurrentEvent] = curPlacement;
                            values[indexToRemoveFrom] = otherPlacement;
                            return !curConstraintOnEvent.consistentWithConstraint(values);
                        }).collect(HashSet<Placement<?>>::new,Set<Placement<?>>::add,Set<Placement<?>>::addAll));
                        inMasterList.get(curEventToRemoveFrom).removeAll(PlacementsToRemove);
                    }
                } else {
                    Map<Event, Set<Placement<?>>> safeList = new HashMap<>(constrainedEvents.length);
                    List<Event> keyOrder = new ArrayList<>(java.util.Arrays.asList(constrainedEvents));
                    keyOrder.remove(curEvent);
                    Map<Event, Integer> iters = keyOrder.parallelStream().collect(HashMap<Event, Integer>::new, (Map<Event, Integer> coll, Event cuEvent) -> coll.put(cuEvent, 0), Map<Event, Integer>::putAll);
                    Event firstEvent = keyOrder.get(0);
                    Event lastEvent = keyOrder.get(keyOrder.size() - 1);
                    while (iters.get(lastEvent) < inMasterList.get(lastEvent).size()) {
                        Map<Event, Placement<?>> tempPlacements = iters.entrySet().parallelStream().collect(HashMap<Event, Placement<?>>::new,
                                (Map<Event, Placement<?>> coll, Entry<Event, Integer> curEntry) -> coll.put(curEntry.getKey(), inMasterList.get(curEntry.getKey()).get(curEntry.getValue())), Map<Event, Placement<?>>::putAll);
                        tempPlacements.put(curEvent, curPlacement);
                        Placement<?> orderedPlacements[] = new Placement<?>[constrainedEvents.length];
                        for (int i = 0; i < constrainedEvents.length; i++) {
                            orderedPlacements[i] = tempPlacements.get(constrainedEvents[i]);
                        }
                        if (curConstraintOnEvent.consistentWithConstraint(orderedPlacements)) {
                            tempPlacements.entrySet().parallelStream().forEach((Entry<Event, Placement<?>> curPlacementEntry) -> safeList.get(curPlacementEntry.getKey()).add(curPlacementEntry.getValue()));
                        }
                        iters.put(firstEvent, iters.get(firstEvent) + 1);
                        int i = 0;
                        while ((iters.get(keyOrder.get(i)) >= inMasterList.get(keyOrder.get(i)).size()) && (i < (constrainedEvents.length - 1))) {
                            iters.put(keyOrder.get(i), 0);
                            i++;
                            iters.put(keyOrder.get(i), iters.get(keyOrder.get(i)) + 1);
                        }
                    }
                    inMasterList.entrySet().parallelStream().forEach((Entry<Event, List<? extends Placement<?>>> curEntry) -> {
                        Set<? extends Placement<?>> safe = safeList.get(curEntry.getKey());
                        List<? extends Placement<?>> curList = curEntry.getValue();
                        curList.retainAll(safe);
                    });
                    //end one check
                }
            }
        }
        //propgateTrimming(seen, eventQueue, inMasterList);
    }

    //TODO: comment
    protected final void propgateTrimming(Map<Event, List<? extends Placement<?>>> inMasterList, List<Event> inEventList) {
        Set<Event> seen = new HashSet<>(inEventList.size());
        java.util.Queue<Event> eventQueue = new java.util.ArrayDeque<>(1);
        seen.addAll(inMasterList.keySet());
        seen.removeAll(inEventList);
        eventQueue.addAll(seen);
        this.propgateTrimming(seen, eventQueue, inMasterList);
    }

    //TODO: comment
    protected final void propgateTrimming(Set<Event> seen, java.util.Queue<Event> eventQueue, Map<Event, List<? extends Placement<?>>> inMasterList) {
        //propgate trimming
        while (!eventQueue.isEmpty()) {
            Event curPropEvent = eventQueue.remove();
            Collection<TimeConstraint> constraintsOnPropEvent = this.getConstraintsOnEvent(curPropEvent);
            for (TimeConstraint curConstraintOnEvent : constraintsOnPropEvent) {
                if (!curConstraintOnEvent.inBeta()) {
                    Event[] constrainedEvents = curConstraintOnEvent.getConstrainedEvents();
                    int indexOfCurrentEvent = -1;
                    //Map<Event, java.lang.Integer> EventToIndexMap = new java.util.HashMap<>(constrainedEvents.length);
                    for (int i = 0; i < constrainedEvents.length; i++) {
                        //EventToIndexMap.put(constrainedEvents[i], i);
                        if (curPropEvent.equals(constrainedEvents[i])) {
                            indexOfCurrentEvent = i;
                        }
                        if (!seen.contains(constrainedEvents[i])) {
                            seen.add(constrainedEvents[i]);
                            eventQueue.add(constrainedEvents[i]);
                        }
                    }
                    if (curConstraintOnEvent instanceof TrimMasterListCapableTimeConstraint) {
                        //TrimMasterListCapableTimeConstraint do it themselves 
                        TrimMasterListCapableTimeConstraint curAdvancedTimeConstraint = (TrimMasterListCapableTimeConstraint) curConstraintOnEvent;
                        curAdvancedTimeConstraint.trimMasterList(curPropEvent, inMasterList);
                    } else if (curConstraintOnEvent instanceof TwoEventTimeConstraint) {
                        final int indexToRemoveFrom = 1 - indexOfCurrentEvent;
                        final int finalIndexOfCurrentEvent = indexOfCurrentEvent;
                        Event curEventToRemoveFrom = constrainedEvents[indexToRemoveFrom];
                        if (inMasterList.containsKey(curEventToRemoveFrom) && inMasterList.containsKey(curPropEvent)) {
                            Set<Placement<?>> PlacementsToRemove = new HashSet<>(0);
                            PlacementsToRemove.addAll(inMasterList.get(curEventToRemoveFrom).parallelStream().filter((Placement<?> otherPlacement) -> {
                                boolean pass = false;
                                Placement<?> values[] = new Placement<?>[constrainedEvents.length];
                                values[indexToRemoveFrom] = otherPlacement;
                                for (Placement<?> curPlacement : inMasterList.get(curPropEvent)) {
                                    values[finalIndexOfCurrentEvent] = curPlacement;
                                    pass = pass || curConstraintOnEvent.consistentWithConstraint(values);
                                    //it only takes one. i.e. no point contining the loop after pass is true
                                    if (pass) {
                                        break;
                                    }
                                }
                                return !pass;
                            }).collect(HashSet<Placement<?>>::new,Set<Placement<?>>::add,Set<Placement<?>>::addAll));
                            try {
                                if (inMasterList.get(curEventToRemoveFrom) instanceof java.util.ArrayList) {
                                    inMasterList.get(curEventToRemoveFrom).removeAll(PlacementsToRemove);
                                }
                            } catch (java.lang.UnsupportedOperationException ex) {
                                int debug = 1 + 3;
                            }
                            if (inMasterList.get(curEventToRemoveFrom).isEmpty()) {
                                int debbug = 1 + 3;
                            }
                        }
                    } else {
                        Map<Event, Set<Placement<?>>> safeList = new HashMap<>(constrainedEvents.length);
                        List<Event> keyOrder = new ArrayList<>(java.util.Arrays.asList(constrainedEvents));
                        Map<Event, Integer> iters = Stream.of(constrainedEvents).collect(HashMap<Event, Integer>::new, (Map<Event, Integer> coll, Event cuEvent) -> coll.put(cuEvent, 0), Map<Event, Integer>::putAll);
                        Event firstEvent = keyOrder.get(0);
                        Event lastEvent = keyOrder.get(keyOrder.size() - 1);
                        while (iters.get(lastEvent) < inMasterList.get(lastEvent).size()) {
                            Map<Event, Placement<?>> tempPlacements = iters.entrySet().parallelStream().collect(HashMap<Event, Placement<?>>::new,
                                    (Map<Event, Placement<?>> coll, Entry<Event, Integer> curEntry) -> coll.put(curEntry.getKey(), inMasterList.get(curEntry.getKey()).get(curEntry.getValue())), Map<Event, Placement<?>>::putAll);
                            Placement<?> orderedPlacements[] = new Placement<?>[constrainedEvents.length];
                            for (int i = 0; i < constrainedEvents.length; i++) {
                                orderedPlacements[i] = tempPlacements.get(constrainedEvents[i]);
                            }
                            if (curConstraintOnEvent.consistentWithConstraint(orderedPlacements)) {
                                tempPlacements.entrySet().parallelStream().forEach((Entry<Event, Placement<?>> curPlacementEntry) -> safeList.get(curPlacementEntry.getKey()).add(curPlacementEntry.getValue()));
                            }
                            iters.put(firstEvent, iters.get(firstEvent) + 1);
                            int i = 0;
                            while ((iters.get(keyOrder.get(i)) >= inMasterList.get(keyOrder.get(i)).size()) && (i < (constrainedEvents.length - 1))) {
                                iters.put(keyOrder.get(i), 0);
                                i++;
                                iters.put(keyOrder.get(i), iters.get(keyOrder.get(i)) + 1);
                            }
                        }
                        inMasterList.entrySet().parallelStream().forEach((Entry<Event, List<? extends Placement<?>>> curEntry) -> {
                            Set<? extends Placement<?>> safe = safeList.get(curEntry.getKey());
                            List<? extends Placement<?>> curList = curEntry.getValue();
                            curList.retainAll(safe);
                        });
                    }
                }
            }
        }
    }

    //TODO: comment
    protected final boolean validMasterList(Map<Event, List<? extends Placement<?>>> inMasterList) {
        return inMasterList.values().parallelStream().noneMatch((List<? extends Placement<?>> curList) -> curList.isEmpty());
    }
    //</editor-fold>

    public static class EventsComparator extends com.civprod.Comparers.IncompleteComparator<EventContainer> {

        private static final long serialVersionUID = 13101L;

        @Override
        public int compare(EventContainer o1, EventContainer o2) {
            return java.lang.Integer.compare(o1.Events.size(), o2.Events.size());
        }
    }

    public static class ConstraintsComparator extends com.civprod.Comparers.IncompleteComparator<EventContainer> {

        private static final long serialVersionUID = 13102L;

        @Override
        public int compare(EventContainer o1, EventContainer o2) {
            return java.lang.Integer.compare(o1.Constraints.size(), o2.Constraints.size());
        }
    }

    public static class MultSizeComparator extends com.civprod.Comparers.IncompleteComparator<EventContainer> {

        private static final long serialVersionUID = 13103L;

        @Override
        public int compare(EventContainer o1, EventContainer o2) {
            long total1 = 1;
            long total2 = 1;
            Set<Event> EventsCopy1 = new java.util.HashSet<>(o1.Events);
            EventsCopy1.removeAll(o2.Events);
            Set<Event> EventsCopy2 = new java.util.HashSet<>(o2.Events);
            EventsCopy2.removeAll(o1.Events);
            total1 = EventsCopy1.stream().map((curEvent) -> (long) curEvent.size()).reduce(total1, (accumulator, _item) -> accumulator * _item);
            total2 = EventsCopy2.stream().map((curEvent) -> (long) curEvent.size()).reduce(total2, (accumulator, _item) -> accumulator * _item);
            return java.lang.Long.compare(total1, total2);
        }
    }

    public static class AddSizeComparator extends com.civprod.Comparers.IncompleteComparator<EventContainer> {

        private static final long serialVersionUID = 13104L;

        @Override
        public int compare(EventContainer o1, EventContainer o2) {
            int total1 = 1;
            int total2 = 1;
            total1 = o1.Events.stream().map((curEvent) -> curEvent.size()).reduce(total1, Integer::sum);
            total2 = o2.Events.stream().map((curEvent) -> curEvent.size()).reduce(total2, Integer::sum);
            return java.lang.Integer.compare(total1, total2);
        }
    }
}
