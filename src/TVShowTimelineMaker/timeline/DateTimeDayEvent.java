/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 */
public interface DateTimeDayEvent<T extends DayEvent.DayPlacement<DateTime>> extends OnceEvent<T>, DayEvent<DateTime,T> {
    
}
