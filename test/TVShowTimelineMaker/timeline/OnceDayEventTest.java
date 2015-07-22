/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.List;
import java.util.NavigableSet;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class OnceDayEventTest {

    @BeforeClass
    public static void setUpClass() {
        OnceDayEvent.init2();
        OnceDayEvent.OnceDayEventXMLWriter.init();
    }

    @AfterClass
    public static void tearDownClass() {
    }

        public OnceDayEventTest() {
        }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addSuggestedDay method, of class OnceDayEvent.
     */
    @Test
    public void testAddSuggestedDay() {
        System.out.println("addSuggestedDay");
        DateTime inDate = null;
        OnceDayEvent instance = new OnceDayEvent();
        boolean expResult = false;
        boolean result = instance.addSuggestedDay(inDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeSuggestedDay method, of class OnceDayEvent.
     */
    @Test
    public void testRemoveSuggestedDay() {
        System.out.println("removeSuggestedDay");
        DateTime inDate = null;
        OnceDayEvent instance = new OnceDayEvent();
        boolean expResult = false;
        boolean result = instance.removeSuggestedDay(inDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuggestedDays method, of class OnceDayEvent.
     */
    @Test
    public void testGetSuggestedDays() {
        System.out.println("getSuggestedDays");
        OnceDayEvent instance = new OnceDayEvent();
        Collection<DateTime> expResult = null;
        Collection<DateTime> result = instance.getSuggestedDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class OnceDayEvent.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        OnceDayEvent instance = new OnceDayEvent();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalize method, of class OnceDayEvent.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        OnceDayEvent instance = new OnceDayEvent();
        instance.normalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class OnceDayEvent.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        OnceDayEvent instance = new OnceDayEvent();
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEarliestPossibleDate method, of class OnceDayEvent.
     */
    @Test
    public void testGetEarliestPossibleDate() {
        System.out.println("getEarliestPossibleDate");
        OnceDayEvent instance = new OnceDayEvent();
        DateTime expResult = null;
        DateTime result = instance.getEarliestPossibleDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLatestPossibleDate method, of class OnceDayEvent.
     */
    @Test
    public void testGetLatestPossibleDate() {
        System.out.println("getLatestPossibleDate");
        OnceDayEvent instance = new OnceDayEvent();
        DateTime expResult = null;
        DateTime result = instance.getLatestPossibleDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEarliestPossibleDate method, of class OnceDayEvent.
     */
    @Test
    public void testSetEarliestPossibleDate() {
        System.out.println("setEarliestPossibleDate");
        DateTime earliestPossibleDate = null;
        OnceDayEvent instance = new OnceDayEvent();
        instance.setEarliestPossibleDate(earliestPossibleDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLatestPossibleDate method, of class OnceDayEvent.
     */
    @Test
    public void testSetLatestPossibleDate() {
        System.out.println("setLatestPossibleDate");
        DateTime latestPossibleDate = null;
        OnceDayEvent instance = new OnceDayEvent();
        instance.setLatestPossibleDate(latestPossibleDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDayAcceptor method, of class OnceDayEvent.
     */
    @Test
    public void testGetDayAcceptor() {
        System.out.println("getDayAcceptor");
        OnceDayEvent instance = new OnceDayEvent();
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLongString method, of class OnceDayEvent.
     */
    @Test
    public void testToLongString() {
        System.out.println("toLongString");
        OnceDayEvent instance = new OnceDayEvent();
        String expResult = "";
        String result = instance.toLongString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPossibleDays method, of class OnceDayEvent.
     */
    @Test
    public void testGetPossibleDays() {
        System.out.println("getPossibleDays");
        OnceDayEvent instance = new OnceDayEvent();
        NavigableSet<DateTime> expResult = null;
        NavigableSet<DateTime> result = instance.getPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPossibleDays method, of class OnceDayEvent.
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testAddPossibleDays() {
        System.out.println("addPossibleDays");
        OnceDayEvent instance = new OnceDayEvent();
        instance.addPossibleDays();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpForComplexEval method, of class OnceDayEvent.
     */
    @Test
    public void testSetUpForComplexEval() {
        System.out.println("setUpForComplexEval");
        OnceDayEvent instance = new OnceDayEvent();
        instance.setUpForComplexEval();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEarliestPossibleStartTime method, of class OnceDayEvent.
     */
    @Test
    public void testGetEarliestPossibleStartTime() {
        System.out.println("getEarliestPossibleStartTime");
        OnceDayEvent instance = new OnceDayEvent();
        DateTime expResult = null;
        DateTime result = instance.getEarliestPossibleStartTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLatestPossibleEndTime method, of class OnceDayEvent.
     */
    @Test
    public void testGetLatestPossibleEndTime() {
        System.out.println("getLatestPossibleEndTime");
        OnceDayEvent instance = new OnceDayEvent();
        DateTime expResult = null;
        DateTime result = instance.getLatestPossibleEndTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAfter method, of class OnceDayEvent.
     */
    @Test
    public void testIsAfter() {
        System.out.println("isAfter");
        DateTime inTime = null;
        OnceDayEvent instance = new OnceDayEvent();
        boolean expResult = false;
        boolean result = instance.isAfter(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isBefore method, of class OnceDayEvent.
     */
    @Test
    public void testIsBefore() {
        System.out.println("isBefore");
        DateTime inTime = null;
        OnceDayEvent instance = new OnceDayEvent();
        boolean expResult = false;
        boolean result = instance.isBefore(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAfter method, of class OnceDayEvent.
     */
    @Test
    public void testSetAfter() {
        System.out.println("setAfter");
        DateTime inTime = null;
        OnceDayEvent instance = new OnceDayEvent();
        boolean expResult = false;
        boolean result = instance.setAfter(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBefore method, of class OnceDayEvent.
     */
    @Test
    public void testSetBefore() {
        System.out.println("setBefore");
        DateTime inTime = null;
        OnceDayEvent instance = new OnceDayEvent();
        boolean expResult = false;
        boolean result = instance.setBefore(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class OnceDayEvent.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        OnceDayEvent instance = new OnceDayEvent();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sizeAdj method, of class OnceDayEvent.
     */
    @Test
    public void testSizeAdj() {
        System.out.println("sizeAdj");
        OnceDayEvent instance = new OnceDayEvent();
        double expResult = 0.0;
        double result = instance.sizeAdj();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTimeFrameString method, of class OnceDayEvent.
     */
    @Test
    public void testToTimeFrameString() {
        System.out.println("toTimeFrameString");
        OnceDayEvent instance = new OnceDayEvent();
        String expResult = "";
        String result = instance.toTimeFrameString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testXmlWriter() {
        System.out.println("testXmlWriter");
        XMLWriterImp<OnceDayEvent> writer = XMLWriterImp.getXMLWriter(OnceDayEvent.class);
        for (int i = 0; i < 365; i++) {
            OnceDayEvent instance = new OnceDayEvent(java.lang.Integer.toString(i));
            // TODO:set up instance randomly
            Element newElement = writer.writeElements(instance);
            OnceDayEvent result = writer.readElements(newElement);
            assertEquals(instance.getEarliestPossibleDate(), result.getEarliestPossibleDate());
            assertEquals(instance.getEarliestPossibleStartTime(), result.getEarliestPossibleStartTime());
            assertEquals(instance.getLeastSignificantIDPart(), result.getLeastSignificantIDPart());
            assertEquals(instance.getLatestPossibleDate(), result.getLatestPossibleDate());
            assertEquals(instance.getLatestPossibleEndTime(), result.getLatestPossibleEndTime());
            assertEquals(instance.getName(), result.getName());
            assertTrue(instance.getSuggestedDays().containsAll(result.getSuggestedDays()));
            assertTrue(result.getSuggestedDays().containsAll(instance.getSuggestedDays()));
        }
    }

    /**
     * Test of init2 method, of class OnceDayEvent.
     */
    @Test
    public void testInit2() {
        System.out.println("init2");
        OnceDayEvent.init2();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlacements method, of class OnceDayEvent.
     */
    @Test
    public void testGetPlacements() {
        System.out.println("getPlacements");
        OnceDayEvent instance = new OnceDayEvent();
        List<OnceDayEvent.OnceDayEventPlacement> expResult = null;
        List<OnceDayEvent.OnceDayEventPlacement> result = instance.getPlacements();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setViaPlacement method, of class OnceDayEvent.
     */
    @Test
    public void testSetViaPlacement() {
        System.out.println("setViaPlacement");
        OnceDayEvent.OnceDayEventPlacement inPlacement = null;
        OnceDayEvent instance = new OnceDayEvent();
        instance.setViaPlacement(inPlacement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTo method, of class OnceDayEvent.
     */
    @Test
    public void testSetTo() {
        System.out.println("setTo");
        Collection<Placement> inPlacements = null;
        OnceDayEvent instance = new OnceDayEvent();
        instance.setTo(inPlacements);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
