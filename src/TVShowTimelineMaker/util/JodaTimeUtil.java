/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.BidiMap;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;


public class JodaTimeUtil {
    
    private static final JodaTimeUtil mInstance = new JodaTimeUtil();
    private static final Logger LOG = Logger.getLogger(JodaTimeUtil.class.getName());
    
    public static final JodaTimeUtil getInstance(){
        return mInstance;
    }
    
    private final Map<String,DateTimeFieldType> StandardDateTimeFieldTypeMap;
    private final BidiMap<String,Class<? extends Chronology>> ChronologybidiMap;
    private final Map<String,Chronology> ChronologyInstanceMap;
    private final List<DateTimeFieldType> DateTimeFieldTypeList;
    
    private JodaTimeUtil(){
        this.StandardDateTimeFieldTypeMap = new java.util.HashMap<>(23);
        this.ChronologyInstanceMap = new java.util.HashMap<>(8);
        this.ChronologybidiMap = new org.apache.commons.collections4.bidimap.DualHashBidiMap<>();
        this.init();
        DateTimeFieldTypeList = org.apache.commons.collections4.ListUtils.unmodifiableList(new ArrayList<>(StandardDateTimeFieldTypeMap.values()));
    }
    
    private void init() {
        // <editor-fold defaultstate="collapsed" desc="StandardDateTimeFieldTypeMap setup">
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.centuryOfEra().getName(), DateTimeFieldType.centuryOfEra());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.clockhourOfDay().getName(), DateTimeFieldType.clockhourOfDay());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.clockhourOfHalfday().getName(), DateTimeFieldType.clockhourOfHalfday());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.dayOfMonth().getName(), DateTimeFieldType.dayOfMonth());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.dayOfWeek().getName(), DateTimeFieldType.dayOfWeek());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.dayOfYear().getName(), DateTimeFieldType.dayOfYear());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.era().getName(), DateTimeFieldType.era());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.halfdayOfDay().getName(), DateTimeFieldType.halfdayOfDay());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.hourOfDay().getName(), DateTimeFieldType.hourOfDay());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.hourOfHalfday().getName(), DateTimeFieldType.hourOfHalfday());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.millisOfDay().getName(), DateTimeFieldType.millisOfDay());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.millisOfSecond().getName(), DateTimeFieldType.millisOfSecond());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.minuteOfDay().getName(), DateTimeFieldType.minuteOfDay());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.minuteOfHour().getName(), DateTimeFieldType.minuteOfHour());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.monthOfYear().getName(), DateTimeFieldType.monthOfYear());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.secondOfDay().getName(), DateTimeFieldType.secondOfDay());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.secondOfMinute().getName(), DateTimeFieldType.secondOfMinute());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.weekOfWeekyear().getName(), DateTimeFieldType.weekOfWeekyear());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.weekyear().getName(), DateTimeFieldType.weekyear());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.weekyearOfCentury().getName(), DateTimeFieldType.weekyearOfCentury());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.year().getName(), DateTimeFieldType.year());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.yearOfCentury().getName(), DateTimeFieldType.yearOfCentury());
        this.StandardDateTimeFieldTypeMap.put(DateTimeFieldType.yearOfEra().getName(), DateTimeFieldType.yearOfEra());
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ChronologyMap setup">
        this.ChronologybidiMap.put("BuddhistChronology", org.joda.time.chrono.BuddhistChronology.class);
        this.ChronologybidiMap.put("CopticChronology", org.joda.time.chrono.CopticChronology.class);
        this.ChronologybidiMap.put("EthiopicChronology", org.joda.time.chrono.EthiopicChronology.class);
        this.ChronologybidiMap.put("GJChronology", org.joda.time.chrono.GJChronology.class);
        this.ChronologybidiMap.put("GregorianChronology", org.joda.time.chrono.GregorianChronology.class);
        this.ChronologybidiMap.put("ISOChronology", org.joda.time.chrono.ISOChronology.class);
        this.ChronologybidiMap.put("IslamicChronology", org.joda.time.chrono.IslamicChronology.class);
        this.ChronologybidiMap.put("JulianChronology", org.joda.time.chrono.JulianChronology.class);
        
        this.ChronologyInstanceMap.put("BuddhistChronology", org.joda.time.chrono.BuddhistChronology.getInstance());
        this.ChronologyInstanceMap.put("CopticChronology", org.joda.time.chrono.CopticChronology.getInstance());
        this.ChronologyInstanceMap.put("EthiopicChronology", org.joda.time.chrono.EthiopicChronology.getInstance());
        this.ChronologyInstanceMap.put("GJChronology", org.joda.time.chrono.GJChronology.getInstance());
        this.ChronologyInstanceMap.put("GregorianChronology", org.joda.time.chrono.GregorianChronology.getInstance());
        this.ChronologyInstanceMap.put("ISOChronology", org.joda.time.chrono.ISOChronology.getInstance());
        this.ChronologyInstanceMap.put("IslamicChronology", org.joda.time.chrono.IslamicChronology.getInstance());
        this.ChronologyInstanceMap.put("JulianChronology", org.joda.time.chrono.JulianChronology.getInstance());
        // </editor-fold>
    }
    
    public DateTimeFieldType getDateTimeFieldTypeForString(String inName){
        return this.StandardDateTimeFieldTypeMap.get(inName);
    }
    
    public String getStringForDateTimeFieldType(DateTimeFieldType inType){
        return inType.getName();
    }
    
    public Chronology getChronologyForString(String inName, String TimeZoneID){
        return this.getChronologyForString(inName,this.getDateTimeZoneForString(TimeZoneID));
    }
    
    public Chronology getChronologyForString(String inName, DateTimeZone inTimeZone){
        Class<? extends Chronology> ChronologyClass = this.ChronologybidiMap.get(inName);
        Chronology rChronology = null;
        try {
            Method getInstanceMethod = ChronologyClass.getMethod("getInstance", DateTimeZone.class);
            rChronology = (Chronology)getInstanceMethod.invoke(null, inTimeZone);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(JodaTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (rChronology == null){
            rChronology = this.ChronologyInstanceMap.get(inName).withZone(inTimeZone);
        }
        return rChronology;
    }
    
    public String getStringForChronology(Chronology inType){
        return this.ChronologybidiMap.getKey(inType.getClass());
    }
    
    public DateTimeZone getDateTimeZoneForString(String id){
        return DateTimeZone.forID(id);
    }
    
    public String getStringForDateTimeZone(DateTimeZone inType){
        return inType.getID();
    }
    
    public String writeTimeString(DateTime ObjectToWrite){
        DateTime withChronology = ObjectToWrite.withChronology(null);
        return withChronology.toString();
    }
    
    public DateTime readTimeString(String timeString, Chronology inType){
        DateTime parse = DateTime.parse(timeString);
        return parse.withChronology(inType);
    }
    
    public List<DateTimeFieldType> getDateTimeFieldTypeAsList(){
        return DateTimeFieldTypeList;
    }
}
