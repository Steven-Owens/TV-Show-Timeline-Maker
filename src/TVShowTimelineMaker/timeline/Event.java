/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.IDedObject;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Steven Owens
 */
public interface Event extends Comparable<Event>, IDedObject {
    
    void reset();
    boolean isValid();
    String getName();
    void setName(String name);
    long getLastmodifyed();
    void normalize();
    String toLongString();
    int size();
    double sizeAdj();
    String toTimeFrameString();
    boolean isMarkedForComplexEval();
    void setUpForComplexEval();
    
    List<? extends Placement<?>> getPlacements();
    List<? extends Placement<?>> getSuggestedPlacements();
    void setTo(Collection<? extends  Placement<?>> inPlacements);
    void setTo(Placement<?> inPlacement);

    
}
