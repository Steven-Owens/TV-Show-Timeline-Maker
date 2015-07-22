/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class JodaTimeUtilTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public JodaTimeUtilTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class JodaTimeUtil.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        JodaTimeUtil expResult = null;
        JodaTimeUtil result = JodaTimeUtil.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDateTimeFieldTypeForString method, of class JodaTimeUtil.
     */
    @Test
    public void testGetDateTimeFieldTypeForString() {
        System.out.println("getDateTimeFieldTypeForString");
        String inName = "";
        JodaTimeUtil instance = null;
        DateTimeFieldType expResult = null;
        DateTimeFieldType result = instance.getDateTimeFieldTypeForString(inName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringForDateTimeFieldType method, of class JodaTimeUtil.
     */
    @Test
    public void testGetStringForDateTimeFieldType() {
        System.out.println("getStringForDateTimeFieldType");
        DateTimeFieldType inType = null;
        JodaTimeUtil instance = null;
        String expResult = "";
        String result = instance.getStringForDateTimeFieldType(inType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChronologyForString method, of class JodaTimeUtil.
     */
    @Test
    public void testGetChronologyForString_String_String() {
        System.out.println("getChronologyForString");
        String inName = "";
        String TimeZoneID = "";
        JodaTimeUtil instance = null;
        Chronology expResult = null;
        Chronology result = instance.getChronologyForString(inName, TimeZoneID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChronologyForString method, of class JodaTimeUtil.
     */
    @Test
    public void testGetChronologyForString_String_DateTimeZone() {
        System.out.println("getChronologyForString");
        String inName = "";
        DateTimeZone inTimeZone = null;
        JodaTimeUtil instance = null;
        Chronology expResult = null;
        Chronology result = instance.getChronologyForString(inName, inTimeZone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringForChronology method, of class JodaTimeUtil.
     */
    @Test
    public void testGetStringForChronology() {
        System.out.println("getStringForChronology");
        Chronology inType = null;
        JodaTimeUtil instance = null;
        String expResult = "";
        String result = instance.getStringForChronology(inType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDateTimeZoneForString method, of class JodaTimeUtil.
     */
    @Test
    public void testGetDateTimeZoneForString() {
        System.out.println("getDateTimeZoneForString");
        String id = "";
        JodaTimeUtil instance = null;
        DateTimeZone expResult = null;
        DateTimeZone result = instance.getDateTimeZoneForString(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStringForDateTimeZone method, of class JodaTimeUtil.
     */
    @Test
    public void testGetStringForDateTimeZone() {
        System.out.println("getStringForDateTimeZone");
        DateTimeZone inType = null;
        JodaTimeUtil instance = null;
        String expResult = "";
        String result = instance.getStringForDateTimeZone(inType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeTimeString method, of class JodaTimeUtil.
     */
    @Test
    public void testWriteTimeString() {
        System.out.println("writeTimeString");
        DateTime ObjectToWrite = null;
        JodaTimeUtil instance = null;
        String expResult = "";
        String result = instance.writeTimeString(ObjectToWrite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readTimeString method, of class JodaTimeUtil.
     */
    @Test
    public void testReadTimeString() {
        System.out.println("readTimeString");
        String timeString = "";
        Chronology inType = null;
        JodaTimeUtil instance = null;
        DateTime expResult = null;
        DateTime result = instance.readTimeString(timeString, inType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
