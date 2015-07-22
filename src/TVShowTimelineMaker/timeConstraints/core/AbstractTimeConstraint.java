/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.core;

import TVShowTimelineMaker.timeConstraints.interfaces.TrimMasterListCapableTimeConstraint;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import org.jdom2.Element;

/**
 *
 * @author Steven Owens
 */
public abstract class AbstractTimeConstraint extends TimeConstraintImp implements TimeConstraint, TrimMasterListCapableTimeConstraint {

    public AbstractTimeConstraint(boolean isSynthetic) {
        super(isSynthetic);
    }
    
    protected AbstractTimeConstraint(boolean isSynthetic, String inRootNamespace, int inMyID){
        super(isSynthetic, inRootNamespace, inMyID);
    }
    
    protected AbstractTimeConstraint(Element root){
        super(root);
    }

    @Override
    public abstract Event[] getConstrainedEvents();

    @Override
    public boolean applyConstraint() {
        return false;
    }

    /**
     * todo: document
     *
     * this implemtashion constructs a masterList from the Events in
     * getConstrainedEvents() by mapping each Event to the return of that
     * Event's getPlacements(). Then calls trimMasterList(Event curPropEvent,
     * Map<Event, List<? extends Placement>> inMasterList) then return the
     * values to the events by calling setTo(Collection<? extends  Placement>
     * inPlacements) on each Event
     *
     * @return
     */
    @Override
    public boolean complexApplyConstraint() {
        Event[] constrainedEvents = getConstrainedEvents();
        Map<Event, List<? extends Placement<?>>> newMasterList = new HashMap<>();
        boolean changed = false;
        for (Event curEvent : constrainedEvents) {
            newMasterList.put(curEvent, curEvent.getPlacements());
        }
        for (Event curEvent : constrainedEvents) {
            changed |= (!trimMasterList(curEvent, newMasterList).isEmpty());
        }
        return changed;
    }

    @Override
    public boolean ConstraintSatisfied() {
        return true;
    }

    @Override
    public abstract boolean consistentWithConstraint(Placement<?>[] inValues);

    public abstract Event[] increaseWhat(Placement<?>[] inValues);

    @Override
    public boolean isStrict() {
        return false;
    }

    /**
     * TODO: document
     *
     * this implemtashion calls this.consistentWithConstraint(Placement[]
     * inValues) and puts the current placements in a safe list if it returns
     * true. Then calls retainAll on each event in the masterList that also in
     * this.getConstrainedEvents()
     *
     * @param inEvent
     * @param curPlacementForInEvent
     * @param masterList
     */
    @Override
    public Set<Event> trimMasterList(Event inEvent, Placement<?> curPlacementForInEvent, Map<Event, List<? extends Placement<?>>> masterList) {
        Event[] constrainedEvents = getConstrainedEvents();
        Map<Event, Set<Placement>> safeList = new HashMap<>(constrainedEvents.length);
        List<Event> keyOrder = new ArrayList<>(java.util.Arrays.asList(constrainedEvents));
        keyOrder.remove(inEvent);
        Map<Event, Integer> iters = keyOrder.parallelStream().collect(HashMap<Event, Integer>::new, (Map<Event, Integer> coll, Event cuEvent) -> coll.put(cuEvent, 0), Map<Event, Integer>::putAll);
        Event firstEvent = keyOrder.get(0);
        Event lastEvent = keyOrder.get(keyOrder.size() - 1);
        while (iters.get(lastEvent) < masterList.get(lastEvent).size()) {
            Map<Event, Placement> tempPlacements = iters.entrySet().parallelStream().collect(HashMap<Event, Placement>::new,
                    (Map<Event, Placement> coll, Map.Entry<Event, Integer> curEntry) -> coll.put(curEntry.getKey(), masterList.get(curEntry.getKey()).get(curEntry.getValue())), Map<Event, Placement>::putAll);
            tempPlacements.put(inEvent, curPlacementForInEvent);
            Placement orderedPlacements[] = new Placement[constrainedEvents.length];
            for (int i = 0; i < constrainedEvents.length; i++) {
                orderedPlacements[i] = tempPlacements.get(constrainedEvents[i]);
            }
            if (consistentWithConstraint(orderedPlacements)) {
                tempPlacements.entrySet().parallelStream().forEach((Map.Entry<Event, Placement> curPlacementEntry) -> safeList.get(curPlacementEntry.getKey()).add(curPlacementEntry.getValue()));
            }
            iters.put(firstEvent, iters.get(firstEvent) + 1);
            int i = 0;
            while ((iters.get(keyOrder.get(i)) >= masterList.get(keyOrder.get(i)).size()) && (i < (constrainedEvents.length - 1))) {
                iters.put(keyOrder.get(i), 0);
                i++;
                iters.put(keyOrder.get(i), iters.get(keyOrder.get(i)) + 1);
            }
        }
        return masterList.entrySet().parallelStream().filter((Map.Entry<Event, List<? extends Placement<?>>> curEntry) -> {
            Set<? extends Placement> safe = safeList.get(curEntry.getKey());
            List<? extends Placement> curList = curEntry.getValue();
            return curList.retainAll(safe);
        }).map((Map.Entry<Event, List<? extends Placement<?>>> curEntry) -> curEntry.getKey())
                .collect(java.util.HashSet<Event>::new,Set<Event>::add,Set<Event>::addAll);
                
    }
    
    /**
     * delagates to trimMasterList(Map<Event, List<? extends Placement>> inMasterList)
     */
    @Override
    public Set<Event> trimMasterList(Event curPropEvent, Map<Event, List<? extends Placement<?>>> inMasterList) {
        return trimMasterList(inMasterList);
    }
    
    /**
     * TODO: document
     *
     * this implemtashion calls this.consistentWithConstraint(Placement[]
     * inValues) and puts the current placements in a safe list if it returns
     * true. Then calls retainAll on each event in the masterList that also in
     * this.getConstrainedEvents()
     *
     * @param curPropEvent
     * @param inMasterList
     */
    @Override
    public Set<Event> trimMasterList(Map<Event, List<? extends Placement<?>>> inMasterList) {
        Event[] constrainedEvents = getConstrainedEvents();
        Map<Event, Set<Placement>> safeList = new HashMap<>(constrainedEvents.length);
        List<Event> keyOrder = new ArrayList<>(java.util.Arrays.asList(constrainedEvents));
        Map<Event, Integer> iters = keyOrder.parallelStream().collect(HashMap<Event, Integer>::new, (Map<Event, Integer> coll, Event cuEvent) -> coll.put(cuEvent, 0), Map<Event, Integer>::putAll);
        Event firstEvent = keyOrder.get(0);
        Event lastEvent = keyOrder.get(keyOrder.size() - 1);
        while (iters.get(lastEvent) < inMasterList.get(lastEvent).size()) {
            Map<Event, Placement> tempPlacements = iters.entrySet().parallelStream().collect(HashMap<Event, Placement>::new,
                    (Map<Event, Placement> coll, Map.Entry<Event, Integer> curEntry) -> coll.put(curEntry.getKey(), inMasterList.get(curEntry.getKey()).get(curEntry.getValue())), Map<Event, Placement>::putAll);
            Placement orderedPlacements[] = new Placement[constrainedEvents.length];
            for (int i = 0; i < constrainedEvents.length; i++) {
                orderedPlacements[i] = tempPlacements.get(constrainedEvents[i]);
            }
            if (consistentWithConstraint(orderedPlacements)) {
                tempPlacements.entrySet().parallelStream().forEach((Map.Entry<Event, Placement> curPlacementEntry) -> safeList.get(curPlacementEntry.getKey()).add(curPlacementEntry.getValue()));
            }
            iters.put(firstEvent, iters.get(firstEvent) + 1);
            int i = 0;
            while ((iters.get(keyOrder.get(i)) >= inMasterList.get(keyOrder.get(i)).size()) && (i < (constrainedEvents.length - 1))) {
                iters.put(keyOrder.get(i), 0);
                i++;
                iters.put(keyOrder.get(i), iters.get(keyOrder.get(i)) + 1);
            }
        }
        return inMasterList.entrySet().parallelStream().filter(((Map.Entry<Event, List<? extends Placement<?>>> curEntry) -> {
            Set<? extends Placement> safe = safeList.get(curEntry.getKey());
            List<? extends Placement> curList = curEntry.getValue();
            return curList.retainAll(safe);
        })).map((Map.Entry<Event, List<? extends Placement<?>>> curEntry) -> curEntry.getKey())
                .collect(java.util.HashSet<Event>::new,Set<Event>::add,Set<Event>::addAll);
    }

}
