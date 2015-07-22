/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import TVShowTimelineMaker.util.XML.DateTimeXMLWriter;
import java.util.Random;
import org.jdom2.Element;
import org.joda.time.DateTime;
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
public class DateTimeXMLWriterTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
        public DateTimeXMLWriterTest() {
        }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of writeElements method, of class DateTimeXMLWriter.
     */
    @Test
    public void testBasicWriteElements() {
        System.out.println("writeElements");
        Random rnd = new Random();
        for (int i = 0; i < 365; i++) {
        DateTime ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60));
        DateTimeXMLWriter instance = new DateTimeXMLWriter();
        Element result = instance.writeElements(ObjectToWrite);
        DateTime backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        }
    }
    
    @Test
    public void testISOLikeChronologyWriteElements() {
        System.out.println("writeElementsISOLike");
        Random rnd = new Random();
        DateTime ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.GJChronology.getInstance());
        DateTimeXMLWriter instance = new DateTimeXMLWriter();
        Element result = instance.writeElements(ObjectToWrite);
        DateTime backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.GregorianChronology.getInstance());
        result = instance.writeElements(ObjectToWrite);
        backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.ISOChronology.getInstance());
        result = instance.writeElements(ObjectToWrite);
        backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
    }

    @Test
    public void testNotISOLikeChronologyWriteElements() {
        System.out.println("writeElementsNotISOLike");
        Random rnd = new Random();
        DateTime ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.BuddhistChronology.getInstance());
        DateTimeXMLWriter instance = new DateTimeXMLWriter();
        Element result = instance.writeElements(ObjectToWrite);
        DateTime backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.CopticChronology.getInstance());
        result = instance.writeElements(ObjectToWrite);
        backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.EthiopicChronology.getInstance());
        result = instance.writeElements(ObjectToWrite);
        backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.IslamicChronology.getInstance());
        result = instance.writeElements(ObjectToWrite);
        backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.JulianChronology.getInstance());
        result = instance.writeElements(ObjectToWrite);
        backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
    }
    
    @Test
    public void testTimeZonesWriteElements() {
        System.out.println("writeElementsTimeZones");
        Random rnd = new Random();
        DateTime ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.ISOChronology.getInstanceUTC());
        DateTimeXMLWriter instance = new DateTimeXMLWriter();
        Element result = instance.writeElements(ObjectToWrite);
        DateTime backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        for (int i = 0; i < 12; i++) {
        ObjectToWrite = new DateTime(1900 + rnd.nextInt(150),1+ rnd.nextInt(12),1+rnd.nextInt(28),1+rnd.nextInt(12),rnd.nextInt(60), org.joda.time.chrono.ISOChronology.getInstance(DateTimeZone.forOffsetHours(i)));
        result = instance.writeElements(ObjectToWrite);
        backDateTime = instance.readElements(result);
        assertEquals(ObjectToWrite.getChronology(), backDateTime.getChronology());
        assertEquals(ObjectToWrite.getZone(),backDateTime.getZone());
        assertEquals(ObjectToWrite, backDateTime);
        }
    }

    /**
     * Test of init method, of class DateTimeXMLWriter.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        DateTimeXMLWriter.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeElements method, of class DateTimeXMLWriter.
     */
    @Test
    public void testWriteElements() {
        System.out.println("writeElements");
        DateTime ObjectToWrite = null;
        DateTimeXMLWriter instance = new DateTimeXMLWriter();
        Element expResult = null;
        Element result = instance.writeElements(ObjectToWrite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readElements method, of class DateTimeXMLWriter.
     */
    @Test
    public void testReadElements() {
        System.out.println("readElements");
        Element root = null;
        DateTimeXMLWriter instance = new DateTimeXMLWriter();
        DateTime expResult = null;
        DateTime result = instance.readElements(root);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
