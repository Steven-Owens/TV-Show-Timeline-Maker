/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import java.util.List;

/**
 *
 * @author Steven Owens
 * @param <T>
 */
public interface PlacementEvent<S, T extends Placement<S>> extends Event{
    @Override
     List<T> getPlacements();
     @Override
     List<T> getSuggestedPlacements();
    void setViaPlacement(T inPlacement);
    
    public interface Placement<S> {
    
    }
}
