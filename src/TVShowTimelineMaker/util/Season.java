/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util;

import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 */
public enum Season {

    SPRING(2, 1, 6, 20), SUMMER(5, 1, 8, 30), FALL(8, 8, 11, 30), WINTER(10, 14, 3, 20),
    START_SPRING(2, 1, 3, 21), START_SUMMER(5, 1, 6, 21), START_FALL(8, 8, 8, 31), START_WINTER(10, 14, 12, 1),
    END_WINTER(1, 31, 3, 22), END_SPRING(4, 30, 6, 21), END_SUMMER(8, 7, 8, 30), END_FALL(10, 13, 11, 30);

    private final int startMonth;
    private final int endMonth;
    private final int startDay;
    private final int endDay;
    private final boolean overYearBound;

    Season(int inStartMonth, int inStartDay, int inEndMonth, int inEndDay) {
        this.overYearBound = inEndMonth < inStartMonth;
        this.startMonth = inStartMonth;
        this.endMonth = inEndMonth;
        this.startDay = inStartDay;
        this.endDay = inEndDay;
    }

    public boolean inSeason(DateTime inDateTime) {
        int monthOfYear = inDateTime.get(org.joda.time.DateTimeFieldType.monthOfYear());
        boolean rAccept;
        if ((!this.overYearBound) && (monthOfYear > this.startMonth) && (monthOfYear < this.endMonth)) {
            rAccept = true;
        } else if ((this.overYearBound) && ((monthOfYear > this.startMonth) || (monthOfYear < this.endMonth))) {
            rAccept = true;
            } else if ((this.startMonth == this.endMonth) && (monthOfYear == this.startMonth) && 
                    (inDateTime.get(org.joda.time.DateTimeFieldType.dayOfMonth()) >= this.startDay) && 
                    (inDateTime.get(org.joda.time.DateTimeFieldType.dayOfMonth()) <= this.endDay)){ 
                rAccept = true;
        } else if ((this.startMonth != this.endMonth) && (monthOfYear == this.startMonth) && (inDateTime.get(org.joda.time.DateTimeFieldType.dayOfMonth()) >= this.startDay)) {
            rAccept = true;
        } else if ((this.startMonth != this.endMonth) && (monthOfYear == this.endMonth) && (inDateTime.get(org.joda.time.DateTimeFieldType.dayOfMonth()) <= this.endDay)) {
            rAccept = true;
        } else {
            rAccept = false;
        }
        return rAccept;
    }

    public boolean inSeason(DayOfYear inDateTime) {
        int monthOfYear = inDateTime.getMonth();
        boolean rAccept;
        if ((!this.overYearBound) && (monthOfYear > this.startMonth) && (monthOfYear < this.endMonth)) {
            rAccept = true;
        } else if ((this.overYearBound) && ((monthOfYear > this.startMonth) || (monthOfYear < this.endMonth))) {
            rAccept = true;
        } else if ((this.startMonth == this.endMonth) && (monthOfYear == this.startMonth) && (inDateTime.getDay() >= this.startDay) && (inDateTime.getDay() <= this.endDay)){
            rAccept = true;
        } else if ((this.startMonth != this.endMonth) && (monthOfYear == this.startMonth) && (inDateTime.getDay() >= this.startDay)) {
            rAccept = true;
        } else if ((this.startMonth != this.endMonth) &&(monthOfYear == this.endMonth) && (inDateTime.getDay() <= this.endDay)) {
            rAccept = true;
        } else {
            rAccept = false;
        }
        return rAccept;
    }
    
    public DayOfYear getStartDay(){
        return new DayOfYear(this.startMonth,this.startDay);
    }
    
    public DayOfYear getEndDay(){
        return new DayOfYear(this.endMonth,this.endDay);
    }
}
