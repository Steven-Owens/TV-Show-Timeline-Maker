/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.util.DayOfYear;
import java.util.logging.Logger;
import org.joda.time.DateTime;


public class NotDayAcceptor implements DayAcceptor{
    private static final Logger LOG = Logger.getLogger(NotDayAcceptor.class.getName());
    private final DayAcceptor mDayAcceptor;
    
    public NotDayAcceptor(DayAcceptor inDayAcceptor){
        this.mDayAcceptor = inDayAcceptor;
    }

    @Override
    public boolean accept(DateTime inDateTime) {
        return !this.mDayAcceptor.accept(inDateTime);
    }

    @Override
    public boolean accept(DayOfYear inDateTime) {
        return !this.mDayAcceptor.accept(inDateTime);
    }

    @Override
    public double divFactor() {
        return 1-this.mDayAcceptor.divFactor();
    }

    /**
     * @return the mDayAcceptor
     */
    public DayAcceptor getInterDayAcceptor() {
        return mDayAcceptor;
    }
}
