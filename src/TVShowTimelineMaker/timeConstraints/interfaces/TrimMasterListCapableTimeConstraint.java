/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.interfaces;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Steven Owens
 */
public interface TrimMasterListCapableTimeConstraint extends TimeConstraint {
    Set<Event> trimMasterList(final Event inEvent, final Placement<?> curPlacementForInEvent, Map<Event, List<? extends Placement<?>>> masterList);

    Set<Event> trimMasterList(final Event curPropEvent, Map<Event, List<? extends Placement<?>>> inMasterList);
    Set<Event> trimMasterList(Map<Event, List<? extends Placement<?>>> inMasterList);
}
