/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.testUtil;

import java.util.logging.Logger;
import org.joda.time.DateTime;


public class DateTimeMaker {
    private static final java.util.Random rnd = new java.util.Random();
    public static final DateTime startTime = new DateTime(-10,1,1,12,0,0);
    public static final DateTime endTime = new DateTime(10,12,31,12,0,0);
    private static final Logger LOG = Logger.getLogger(DateTimeMaker.class.getName());
    public static DateTime randomDateTime(){
         return randomDateTimeBetweenTimes(startTime,endTime);
    }
    public static DateTime randomDateTimeAfterTime(DateTime inDay){
        return randomDateTimeBetweenTimes(inDay,endTime);
    }
    

    public static DateTime randomDateTimeAfterTimeSure(DateTime inDay) {
        return randomDateTimeBetweenTimes(inDay.plusDays(1),endTime.plusDays(1));
    }
    
    public static DateTime randomDateTimeBeforeTime(DateTime inDay){
        return randomDateTimeBetweenTimes(startTime,inDay);
    }
    
    public static DateTime randomDateTimeBeforeTimeSure(DateTime inDay) {
        return randomDateTimeBetweenTimes(startTime.minusDays(1),inDay.minusDays(1));
    }
    
    public static DateTime randomDateTimeBetweenTimes(DateTime inAfterDay,DateTime inBeforeDay){
        int startYear = inAfterDay.getYear();
        int diffYear = inBeforeDay.getYear() - startYear;
        if ((diffYear + 1) <= 0){
            int a = 1 +2;
        }
        int newYear = startYear + rnd.nextInt(diffYear + 1);
        boolean alreadyDiffernt = false;
        int newMonth;
        if ((newYear != startYear) && (newYear != inBeforeDay.getYear())){
            alreadyDiffernt = true;
            newMonth = 1 + rnd.nextInt(12);
        } else {
            if (diffYear == 0){
                int startMonth = inAfterDay.getMonthOfYear();
                int diffMonth = inBeforeDay.getMonthOfYear() - startMonth;
                newMonth = startMonth + rnd.nextInt(diffMonth + 1);
            } else if (newYear == startYear){
                int startMonth = inAfterDay.getMonthOfYear();
                int diffMonth = 12 - startMonth;
                newMonth = startMonth + rnd.nextInt(diffMonth + 1);
            } else {
                int startMonth = 1;
                int diffMonth = inBeforeDay.getMonthOfYear() - startMonth;
                newMonth = startMonth + rnd.nextInt(diffMonth + 1);
            }
        }
        int newDay;
        if (alreadyDiffernt || ((!((newMonth == inAfterDay.getMonthOfYear()) && (newYear == startYear))) && (!((newMonth == inBeforeDay.getMonthOfYear()) && (newYear == inBeforeDay.getYear()))))){
            alreadyDiffernt = true;
            newDay = 1 + rnd.nextInt(28);
        } else {
            if ((startYear == inBeforeDay.getYear()) && (inAfterDay.getMonthOfYear() == inBeforeDay.getMonthOfYear())){
                int startDay = inAfterDay.getDayOfMonth();
                int diffDay = inBeforeDay.getDayOfMonth() - startDay;
                newDay = startDay + rnd.nextInt(diffDay + 1);
            } else if ((newMonth == inAfterDay.getMonthOfYear()) && (newYear == startYear)){
                int startDay = inAfterDay.getDayOfMonth();
                int diffDay = Math.max(0, 28 - startDay);
                newDay = startDay + rnd.nextInt(diffDay + 1);
            } else {
                int startDay = 1;
                int diffDay = inBeforeDay.getDayOfMonth() - startDay;
                newDay = startDay + rnd.nextInt(diffDay + 1);
            }
        }
        return new DateTime(newYear,newMonth,newDay,12,0,0);
    }
    
    public static DateTime averageTime(DateTime inAfterDay,DateTime inBeforeDay){
       int days = new org.joda.time.Interval(inAfterDay, inBeforeDay).toDuration().toStandardDays().getDays();
       return inAfterDay.plusDays(days/2);
    }

    

    private DateTimeMaker() {
    }
}
