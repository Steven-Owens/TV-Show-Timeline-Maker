/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.util.DayOfYear;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 */
public interface DayAcceptor {
    boolean accept(DateTime inDateTime);
    boolean accept(DayOfYear inDateTime);
    double divFactor();
}
