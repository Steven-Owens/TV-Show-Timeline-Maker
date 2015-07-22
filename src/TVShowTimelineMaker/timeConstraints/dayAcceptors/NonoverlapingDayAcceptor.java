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


public class NonoverlapingDayAcceptor implements DayAcceptor {
    private static final Logger LOG = Logger.getLogger(NonoverlapingDayAcceptor.class.getName());
    private final PeriodEvent mEvent;
    public NonoverlapingDayAcceptor(PeriodEvent inEvent){
        this.mEvent = inEvent;
    }

    @Override
    public boolean accept(DateTime inDateTime) {
        return !this.mEvent.containsSure(inDateTime);
    }

    @Override
    public boolean accept(DayOfYear inDateTime) {
        return !this.mEvent.containsSure(inDateTime);
    }

    @Override
    public double divFactor() {
        double div = 1- (this.mEvent.size()/365.0);
        if (div > 1.0){
            div = 1.0;
        } else if (div <= 0){
            div = 1.0;
        }
        return div;
    }
}
