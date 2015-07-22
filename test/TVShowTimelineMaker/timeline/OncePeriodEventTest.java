/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.testUtil.DateTimeMaker;
import TVShowTimelineMaker.testUtil.RandomEvents;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.ContainsDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.NonoverlapingDayAcceptor;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.List;
import java.util.NavigableSet;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Interval;
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
public class OncePeriodEventTest {

    @BeforeClass
    public static void setUpClass() {
        OncePeriodEvent.init2();
        OncePeriodEvent.PeriodEventXMLWriter.init();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public OncePeriodEventTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of init2 method, of class OncePeriodEvent.
     */
    @Test
    public void testInit2() {
        System.out.println("init2");
    }

    /**
     * Test of reset method, of class OncePeriodEvent.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        OncePeriodEvent instance = new OncePeriodEvent();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPossibleDays method, of class OncePeriodEvent.
     */
    @Test
    public void testAddPossibleDays() {
        System.out.println("addPossibleDays");
        OncePeriodEvent instance = new OncePeriodEvent();
        instance.addPossibleDays();
        org.junit.Assert.assertTrue(!instance.getStartPossibleDays().isEmpty());
    }

    /**
     * Test of setUpForComplexEval method, of class OncePeriodEvent.
     */
    @Test
    public void testSetUpForComplexEval() {
        System.out.println("setUpForComplexEval");
        OncePeriodEvent instance = new OncePeriodEvent();
        instance.setUpForComplexEval();
        org.junit.Assert.assertTrue(!instance.getStartPossibleDays().isEmpty());
        org.junit.Assert.assertTrue(instance.isMarkedForComplexEval());
    }

    /**
     * Test of normalize method, of class OncePeriodEvent.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        OncePeriodEvent instance = new OncePeriodEvent();
        instance.normalize();
        assertEquals(instance.getEarliestPossibleStartTime().getHourOfDay(), 10);
        assertEquals(instance.getLatestPossibleStartTime().getHourOfDay(), 13);
        assertEquals(instance.getEarliestPossibleEndTime().getHourOfDay(), 11);
        assertEquals(instance.getLatestPossibleEndTime().getHourOfDay(), 14);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class OncePeriodEvent.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAfter method, of class OncePeriodEvent.
     */
    @Test
    public void testIsAfter() {
        System.out.println("isAfter");
        DateTime inTime = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.isAfter(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isBefore method, of class OncePeriodEvent.
     */
    @Test
    public void testIsBefore() {
        System.out.println("isBefore");
        DateTime inTime = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.isBefore(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAfter method, of class OncePeriodEvent.
     */
    @Test
    public void testSetAfter() {
        System.out.println("setAfter");
        DateTime inTime = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.setAfter(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBefore method, of class OncePeriodEvent.
     */
    @Test
    public void testSetBefore() {
        System.out.println("setBefore");
        DateTime inTime = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.setBefore(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinDuration method, of class OncePeriodEvent.
     */
    @Test
    public void testGetMinDuration() {
        System.out.println("getMinDuration");
        OncePeriodEvent instance = new OncePeriodEvent();
        int expResult = 0;
        int result = instance.getMinDuration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinDuration method, of class OncePeriodEvent.
     */
    @Test
    public void testSetMinDuration() {
        System.out.println("setMinDuration");
        int minDuration = 0;
        OncePeriodEvent instance = new OncePeriodEvent();
        instance.setMinDuration(minDuration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxDuration method, of class OncePeriodEvent.
     */
    @Test
    public void testGetMaxDuration() {
        System.out.println("getMaxDuration");
        OncePeriodEvent instance = new OncePeriodEvent();
        int expResult = 0;
        int result = instance.getMaxDuration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMaxDuration method, of class OncePeriodEvent.
     */
    @Test
    public void testSetMaxDuration() {
        System.out.println("setMaxDuration");
        int maxDuration = 0;
        OncePeriodEvent instance = new OncePeriodEvent();
        instance.setMaxDuration(maxDuration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStart method, of class OncePeriodEvent.
     */
    @Test
    public void testGetStart() {
        System.out.println("getStart");
        OncePeriodEvent instance = new OncePeriodEvent();
        OnceDayEvent expResult = null;
        OnceDayEvent result = instance.getStart();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnd method, of class OncePeriodEvent.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        OncePeriodEvent instance = new OncePeriodEvent();
        OnceDayEvent expResult = null;
        OnceDayEvent result = instance.getEnd();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsCould method, of class OncePeriodEvent.
     */
    @Test
    public void testContainsCould_DateTime() {
        System.out.println("containsCould");
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent();
            DateTime inDateTime = DateTimeMaker.randomDateTimeBetweenTimes(instance.getEarliestPossibleStartTime(), instance.getLatestPossibleEndTime());
            boolean expResult = false;
            boolean result = instance.containsCould(inDateTime);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of containsSure method, of class OncePeriodEvent.
     */
    @Test
    public void testContainsSure_DateTime() {
        System.out.println("containsSure");
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent();
            if (instance.getLatestPossibleStartTime().isBefore(instance.getEarliestPossibleEndTime())) {
                DateTime inDateTime = DateTimeMaker.randomDateTimeBetweenTimes(instance.getLatestPossibleStartTime(), instance.getEarliestPossibleEndTime());
                boolean expResult = false;
                boolean result = instance.containsSure(inDateTime);
                assertEquals(expResult, result);
            } else {
                System.out.println(i);
            }
        }
    }

    /**
     * Test of containsCould method, of class OncePeriodEvent.
     */
    @Test
    public void testContainsCould_DayOfYear() {
        System.out.println("containsCould");
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent();
            DayOfYear inDateTime = DayOfYear.fromDateTime(DateTimeMaker.randomDateTimeBetweenTimes(instance.getEarliestPossibleStartTime(), instance.getLatestPossibleEndTime()));
            boolean expResult = false;
            boolean result = instance.containsCould(inDateTime);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of containsSure method, of class OncePeriodEvent.
     */
    @Test
    public void testContainsSure_DayOfYear() {
        System.out.println("containsSure");
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent();
            if (instance.getLatestPossibleStartTime().isBefore(instance.getEarliestPossibleEndTime())) {
                DayOfYear inDateTime = DayOfYear.fromDateTime(DateTimeMaker.randomDateTimeBetweenTimes(instance.getLatestPossibleStartTime(), instance.getEarliestPossibleEndTime()));
                boolean expResult = false;
                boolean result = instance.containsSure(inDateTime);
                assertEquals(expResult, result);
            } else {
                System.out.println(i);
            }
        }
    }

    /**
     * Test of toLongInterval method, of class OncePeriodEvent.
     */
    @Test
    public void testToLongInterval() {
        System.out.println("toLongInterval");
        OncePeriodEvent instance = new OncePeriodEvent();
        Interval expResult = null;
        Interval result = instance.toLongInterval();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toShortInterval method, of class OncePeriodEvent.
     */
    @Test
    public void testToShortInterval() {
        System.out.println("toShortInterval");
        OncePeriodEvent instance = new OncePeriodEvent();
        Interval expResult = null;
        Interval result = instance.toShortInterval();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abutsBefore method, of class OncePeriodEvent.
     */
    @Test
    public void testAbutsBefore() {
        System.out.println("abutsBefore");
        PeriodEvent inPeriodEvent = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.abutsBefore(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abutsAfter method, of class OncePeriodEvent.
     */
    @Test
    public void testAbutsAfter() {
        System.out.println("abutsAfter");
        PeriodEvent inPeriodEvent = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.abutsAfter(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAbutsBefore method, of class OncePeriodEvent.
     */
    @Test
    public void testSetAbutsBefore() {
        System.out.println("setAbutsBefore");
        PeriodEvent inPeriodEvent = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.setAbutsBefore(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAbutsAfter method, of class OncePeriodEvent.
     */
    @Test
    public void testSetAbutsAfter() {
        System.out.println("setAbutsAfter");
        PeriodEvent inPeriodEvent = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        boolean expResult = false;
        boolean result = instance.setAbutsAfter(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class OncePeriodEvent.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        OncePeriodEvent instance = new OncePeriodEvent();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sizeAdj method, of class OncePeriodEvent.
     */
    @Test
    public void testSizeAdj() {
        System.out.println("sizeAdj");
        OncePeriodEvent instance = new OncePeriodEvent();
        double expResult = 0.0;
        double result = instance.sizeAdj();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTimeFrameString method, of class OncePeriodEvent.
     */
    @Test
    public void testToTimeFrameString() {
        System.out.println("toTimeFrameString");
        OncePeriodEvent instance = new OncePeriodEvent();
        String expResult = "";
        String result = instance.toTimeFrameString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartDayAcceptor method, of class OncePeriodEvent.
     */
    @Test
    public void testGetStartDayAcceptor() {
        System.out.println("getStartDayAcceptor");
        OncePeriodEvent instance = new OncePeriodEvent();
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getStartDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndDayAcceptor method, of class OncePeriodEvent.
     */
    @Test
    public void testGetEndDayAcceptor() {
        System.out.println("getEndDayAcceptor");
        OncePeriodEvent instance = new OncePeriodEvent();
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getEndDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartPossibleDays method, of class OncePeriodEvent.
     */
    @Test
    public void testGetStartPossibleDays() {
        System.out.println("getStartPossibleDays");
        OncePeriodEvent instance = new OncePeriodEvent();
        NavigableSet<DateTime> expResult = null;
        NavigableSet<DateTime> result = instance.getStartPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndPossibleDays method, of class OncePeriodEvent.
     */
    @Test
    public void testGetEndPossibleDays() {
        System.out.println("getEndPossibleDays");
        OncePeriodEvent instance = new OncePeriodEvent();
        NavigableSet<DateTime> expResult = null;
        NavigableSet<DateTime> result = instance.getEndPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContainsDayAcceptor method, of class OncePeriodEvent.
     */
    @Test
    public void testGetContainsDayAcceptor() {
        System.out.println("getContainsDayAcceptor");
        OncePeriodEvent instance = new OncePeriodEvent();
        ContainsDayAcceptor expResult = null;
        ContainsDayAcceptor result = instance.getContainsDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNonoverlapingDayAcceptor method, of class OncePeriodEvent.
     */
    @Test
    public void testGetNonoverlapingDayAcceptor() {
        System.out.println("getNonoverlapingDayAcceptor");
        OncePeriodEvent instance = new OncePeriodEvent();
        NonoverlapingDayAcceptor expResult = null;
        NonoverlapingDayAcceptor result = instance.getNonoverlapingDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLongString method, of class OncePeriodEvent.
     */
    @Test
    public void testToLongString() {
        System.out.println("toLongString");
        OncePeriodEvent instance = new OncePeriodEvent();
        String expResult = "";
        String result = instance.toLongString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testXmlWriter() {
        System.out.println("testXmlWriter");
        XMLWriterImp<OncePeriodEvent> writer = XMLWriterImp.getXMLWriter(OncePeriodEvent.class);
        for (int i = 0; i < 365; i++) {
            OncePeriodEvent instance = new OncePeriodEvent(java.lang.Integer.toString(i));
            // TODO:set up instance randomly
            Element newElement = writer.writeElements(instance);
            OncePeriodEvent result = writer.readElements(newElement);
            assertEquals(instance.getEarliestPossibleEndTime(), result.getEarliestPossibleEndTime());
            assertEquals(instance.getEarliestPossibleStartTime(), result.getEarliestPossibleStartTime());
            assertEquals(instance.getLeastSignificantIDPart(), result.getLeastSignificantIDPart());
            assertEquals(instance.getLatestPossibleStartTime(), result.getLatestPossibleStartTime());
            assertEquals(instance.getLatestPossibleEndTime(), result.getLatestPossibleEndTime());
            assertEquals(instance.getName(), result.getName());
            assertEquals(instance.getMaxDuration(), result.getMaxDuration());
            assertEquals(instance.getMinDuration(), result.getMinDuration());
        }
    }

    /**
     * Test of getPlacements method, of class OncePeriodEvent.
     */
    @Test
    public void testGetPlacements() {
        System.out.println("getPlacements");
        OncePeriodEvent instance = new OncePeriodEvent();
        List<OncePeriodEvent.OncePeriodEventPlacement> expResult = null;
        List<OncePeriodEvent.OncePeriodEventPlacement> result = instance.getPlacements();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setViaPlacement method, of class OncePeriodEvent.
     */
    @Test
    public void testSetViaPlacement() {
        System.out.println("setViaPlacement");
        OncePeriodEvent.OncePeriodEventPlacement inPlacement = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        instance.setViaPlacement(inPlacement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTo method, of class OncePeriodEvent.
     */
    @Test
    public void testSetTo() {
        System.out.println("setTo");
        Collection<Placement> inPlacements = null;
        OncePeriodEvent instance = new OncePeriodEvent();
        instance.setTo(inPlacements);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
