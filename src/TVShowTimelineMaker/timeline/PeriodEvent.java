/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.ContainsDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.NonoverlapingDayAcceptor;
import TVShowTimelineMaker.timeline.DayEvent.DayPlacement;
import TVShowTimelineMaker.timeline.PeriodEvent.PeriodPlacement;
import TVShowTimelineMaker.util.DayOfYear;
import java.util.NavigableSet;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 * @param <S>
 */
public interface PeriodEvent<S, T extends PeriodPlacement<S>> extends Event, PlacementEvent<S,T> {
    int getMinDuration();
    void setMinDuration(int minDuration);
    int getMaxDuration();
     void setMaxDuration(int maxDuration);
     DayEvent<S, ? extends DayPlacement<S>> getStart();
     DayEvent<S, ? extends DayPlacement<S>> getEnd();
     AndDayAcceptor getStartDayAcceptor();
     AndDayAcceptor getEndDayAcceptor();
     NavigableSet<S> getStartPossibleDays();
     NavigableSet<S> getEndPossibleDays();
     ContainsDayAcceptor getContainsDayAcceptor();
     NonoverlapingDayAcceptor getNonoverlapingDayAcceptor();
     
     boolean containsCould(DateTime inDateTime);
     boolean containsCould(DayOfYear inDateTime);
     boolean containsSure(DateTime inDateTime);
     boolean containsSure(DayOfYear inDateTime);
     //todo: move to AbutsContraint
     boolean abutsBefore(PeriodEvent<?,?> inPeriodEvent);
     //todo: move to AbutsContraint
     boolean abutsAfter(PeriodEvent<?,?> inPeriodEvent);
     
     //todo: move to AbutsContraint
     boolean setAbutsBefore(PeriodEvent<?,?> inPeriodEvent);
     //todo: move to AbutsContraint
     boolean setAbutsAfter(PeriodEvent<?,?> inPeriodEvent);
     
    public interface PeriodPlacement<S> extends Placement<S> {
        public S getStartDay();
        public S getEndDay();
    }
}
