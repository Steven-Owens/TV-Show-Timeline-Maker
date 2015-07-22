/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.util.DayOfYear;
import java.util.Objects;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.Partial;


public class PartialDayAcceptor implements DayAcceptor{
    private static final Logger LOG = Logger.getLogger(PartialDayAcceptor.class.getName());
    private final Partial incompleteDate;
    
    public PartialDayAcceptor(Partial inPartial){
        this.incompleteDate = inPartial;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof PartialDayAcceptor){
            return this.getIncompleteDate().equals(((PartialDayAcceptor)o).getIncompleteDate());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.getIncompleteDate());
        return hash;
    }

    @Override
    public boolean accept(DateTime inDateTime) {
        return this.getIncompleteDate().isMatch(inDateTime);
    }
    
    @Override
    public boolean accept(DayOfYear inDateTime) {
        return this.accept(new DateTime(0, inDateTime.getMonth(),inDateTime.getDay(),1, 0));
    }
    
     @Override
    public double divFactor() {
        int factor = 1;
        for (DateTimeField curDateTimeField : this.incompleteDate.getFields()){
            int tempfactor = curDateTimeField.getMaximumValue() - curDateTimeField.getMinimumValue();
            factor = Math.max(factor, tempfactor);
        }
        return 1.0/factor;
    }

    /**
     * @return the incompleteDate
     */
    public Partial getIncompleteDate() {
        return this.incompleteDate;
    }
}
