/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.util.DayOfYear;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 * @param <T>
 */
public interface YearlyEvent<T extends PlacementEvent.Placement<DayOfYear>> extends PlacementEvent<DayOfYear, T> {
    /**
     * returns a OnceEvent for compare purposes
     * the returned OnceEvent may or may not have connection to YearlyEvent that spawned it
     * @param year
     * @return 
     */
    OnceEvent<? extends PlacementEvent.Placement<DateTime>>  getInstance(int year);
}
