/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.Season;
import java.util.logging.Logger;
import org.joda.time.DateTime;


public class SeasonDayAcceptor implements DayAcceptor {
    private static final Logger LOG = Logger.getLogger(SeasonDayAcceptor.class.getName());

    private final Season mSeason;
    
    public SeasonDayAcceptor(Season inSeason){
        this.mSeason = inSeason;
    }

    @Override
    public boolean accept(DateTime inDateTime) {
        return this.mSeason.inSeason(inDateTime);
    }
    
    @Override
    public boolean accept(DayOfYear inDateTime) {
        return this.mSeason.inSeason(inDateTime);
    }

    @Override
    public double divFactor() {
        return 1.0/4.0;
    }

    /**
     * @return the mSeason
     */
    public Season getSeason() {
        return mSeason;
    }

}
