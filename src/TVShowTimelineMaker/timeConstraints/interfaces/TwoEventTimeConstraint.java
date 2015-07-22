/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.interfaces;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;

/**
 *
 * @author Steven Owens
 */
public interface TwoEventTimeConstraint<T extends Placement<?>,S extends Placement<?>> extends TimeConstraint {
    Event getFirstEvent();
    Event getSecondEvent();
    boolean isStrictlyBefore();
    
    public boolean consistentWithConstraint(T inFirstPlacement, S inSecondPlacement);
}
