/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeline.DayEvent.DayPlacement;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import java.util.Collection;
import java.util.NavigableSet;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 * @param <T>
 */
public interface DayEvent<S, T extends DayPlacement<S>> extends Event {
    //public void addPossibleDays();
    NavigableSet<S> getPossibleDays();
    
    /**
     * @return the incompleteDate
     */
    AndDayAcceptor getDayAcceptor();
    boolean addSuggestedDay(S inDate);
    boolean removeSuggestedDay(S inDate);
    Collection<S> getSuggestedDays();
    
    public interface DayPlacement<S> extends Placement<S> {
        public S getDay();
    }
}
