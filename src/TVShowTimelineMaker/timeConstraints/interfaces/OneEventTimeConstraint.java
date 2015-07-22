/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.interfaces;

import TVShowTimelineMaker.timeline.Event;

/**
 *
 * @author Steven Owens
 */
public interface OneEventTimeConstraint extends TimeConstraint {
    Event getEvent();
}
