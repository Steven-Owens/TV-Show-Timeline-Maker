/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.interfaces;

import TVShowTimelineMaker.timeline.PlacementEvent;
import org.joda.time.DateTime;

/**
 * @deprecated use TwoEventTimeConstraint instead
 * @author Steven Owens
 */
@Deprecated
public interface TwoOnceDayEventTimeConstraint<T extends PlacementEvent.Placement<DateTime>,S extends PlacementEvent.Placement<DateTime>> extends TwoEventTimeConstraint<T,S> {
    boolean consistentWithConstraint(DateTime inFirstDay, DateTime inSecondDay);
}
