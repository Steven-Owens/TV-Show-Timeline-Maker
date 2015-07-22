/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util;

import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Set;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author Steven Owens
 */
public final class DayOfYear implements Comparable<DayOfYear> {

    public static final Set<DayOfYear> fullYear;
    
    public static final DayOfYear startOfYear = new DayOfYear(1,1);
    public static final DayOfYear endOfYear = new DayOfYear(12,31);
    private static final Logger LOG = Logger.getLogger(DayOfYear.class.getName());

    static {
        java.util.Set<DayOfYear> tempFullYear = new java.util.HashSet<>(365);
        for (DayOfYear curDay = new DayOfYear(1, 1); curDay != null; curDay = curDay.plusDaysNoWrap(1)) {
            tempFullYear.add(curDay);
        }
        fullYear = java.util.Collections.unmodifiableSet(tempFullYear);
    }

    public static DayOfYear fromDateTime(DateTime inDateTime) {
        return new DayOfYear(inDateTime.getMonthOfYear(), inDateTime.getDayOfMonth());
    }
    private final int mMonth;
    private final int mDay;

    public DayOfYear(int inMonth, int inDay) {
        this.mMonth = inMonth;
        this.mDay = inDay;
    }

    /**
     * @return the Month
     */
    public int getMonth() {
        return this.mMonth;
    }

    /**
     * @return the Day
     */
    public int getDay() {
        return this.mDay;
    }

    public boolean isBefore(DayOfYear inDayOfYear) {
        if (this.mMonth < inDayOfYear.mMonth) {
            return true;
        } else if (this.mMonth > inDayOfYear.mMonth) {
            return false;
        } else if (this.mDay < inDayOfYear.mDay) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEqualOrBefore(DayOfYear inDayOfYear) {
        return this.isBefore(inDayOfYear) || this.equals(inDayOfYear);
    }

    public boolean isAfter(DayOfYear inDayOfYear) {
        if (this.mMonth < inDayOfYear.mMonth) {
            return false;
        } else if (this.mMonth > inDayOfYear.mMonth) {
            return true;
        } else if (this.mDay < inDayOfYear.mDay) {
            return false;
        } else if (this.mDay > inDayOfYear.mDay) {
            return true;
        } else {
            return false;
        }
    }

        public boolean isEqualOrAfter(DayOfYear inDayOfYear) {
            return this.isAfter(inDayOfYear) || this.equals(inDayOfYear);
        }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DayOfYear) {
            DayOfYear inO = (DayOfYear) o;
            return (this.mMonth == inO.mMonth) && (this.mDay == inO.mDay);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.mMonth;
        hash = 41 * hash + this.mDay;
        return hash;
    }

    public DayOfYear plusDays(int i) {
        DateTime curDateTime = this.toDateTime();
        curDateTime = curDateTime.plusDays(i);
        return fromDateTime(curDateTime);
    }

    public DayOfYear plusDaysNoWrap(int i) {
        DateTime curDateTime = this.toDateTime();
        curDateTime = curDateTime.plusDays(i);
        if (curDateTime.getYear() == 0) {
            return fromDateTime(curDateTime);
        } else {
            return null;
        }
    }

    public DateTime toDateTime() {
        return new DateTime(0, this.mMonth, this.mDay, 12, 0);
    }

    @Override
    public int compareTo(DayOfYear o) {
        int cValue = java.lang.Integer.compare(this.mMonth, o.mMonth);
        if (cValue != 0) {
            return cValue;
        }
        cValue = java.lang.Integer.compare(this.mDay, o.mDay);
        return cValue;
    }

    public int diff(DayOfYear inDayOfYear) {
        int rValue;
        if (this.equals(inDayOfYear)) {
            rValue = 0;
        } else if (this.isBefore(inDayOfYear)) {
            rValue = new Interval(this.toDateTime(), inDayOfYear.toDateTime()).toPeriod(org.joda.time.PeriodType.days()).getDays();
        } else if (this.isAfter(inDayOfYear)) {
            rValue = new Interval(this.toDateTime(), inDayOfYear.toDateTime().withYear(1)).toPeriod(org.joda.time.PeriodType.days()).getDays();
        } else {
            rValue = 366;
        }
        return rValue;
    }

    @Override
    public String toString() {
        return this.mMonth + "\\" + this.mDay;
    }

    public static class DayOfYearXMLWriter
            extends XMLWriterImp<DayOfYear> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(DayOfYear.class, new DayOfYearXMLWriter());
        }

        @Override
        public Element writeElements(DayOfYear ObjectToWrite) {
            Element newElement = new Element("DayOfYear");
            Element MonthElement = new Element("Month");
            MonthElement.setText(java.lang.Integer.toString(ObjectToWrite.mMonth));
            newElement.addContent(MonthElement);
            Element DayElement = new Element("Day");
            DayElement.setText(java.lang.Integer.toString(ObjectToWrite.mDay));
            newElement.addContent(DayElement);
            return newElement;
        }

        @Override
        public DayOfYear readElements(Element root) {
            Element MonthElement = root.getChild("Month");
            if (MonthElement == null){
                int a = 1 + 2;
            }
            int tempMonth = java.lang.Integer.parseInt(MonthElement.getTextNormalize());
            Element DayElement = root.getChild("Day");
            int tempDay = java.lang.Integer.parseInt(DayElement.getTextNormalize());
            return new DayOfYear(tempMonth, tempDay);
        }
    }

}
