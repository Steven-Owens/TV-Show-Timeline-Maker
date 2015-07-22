/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.timeline.PeriodEvent;
import TVShowTimelineMaker.util.DayOfYear;
import java.util.logging.Logger;
import org.joda.time.DateTime;


public class ContainsDayAcceptor implements DayAcceptor{
    private static final Logger LOG = Logger.getLogger(ContainsDayAcceptor.class.getName());
    private final PeriodEvent mEvent;
    public ContainsDayAcceptor(PeriodEvent inEvent){
        this.mEvent = inEvent;
    }

    @Override
    public boolean accept(DateTime inDateTime) {
        return this.mEvent.containsCould(inDateTime);
    }

    @Override
    public boolean accept(DayOfYear inDateTime) {
        return this.mEvent.containsCould(inDateTime);
    }

    @Override
    public double divFactor() {
        double div = this.mEvent.getMaxDuration()/365.0;
        if (div > 1.0){
            div = 1.0;
        }
        return div;
    }
}
