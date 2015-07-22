/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 */
public interface OnceEvent<T extends Placement<DateTime>> extends PlacementEvent<DateTime,T>{
    DateTime getEarliestPossibleStartTime();
    DateTime getLatestPossibleStartTime();
    DateTime getLatestPossibleEndTime();
    DateTime getEarliestPossibleEndTime();
    
    boolean isAfter(DateTime inTime);
    boolean isBefore(DateTime inTime);
    
    boolean setAfter(DateTime inTime);
    boolean setBefore(DateTime inTime);
}
