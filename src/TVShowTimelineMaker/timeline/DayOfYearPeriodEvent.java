/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.util.DayOfYear;

/**
 *
 * @author Steven Owens
 */
public interface DayOfYearPeriodEvent<T extends PeriodEvent.PeriodPlacement<DayOfYear>> extends YearlyEvent<T>, PeriodEvent<DayOfYear,T>{
    
}
