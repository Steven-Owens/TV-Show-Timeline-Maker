/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.ContainsDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.NonoverlapingDayAcceptor;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
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
public class YearlyPeriodEventTest {

    public static Random rnd;

    @BeforeClass
    public static void setUpClass() {
        YearlyPeriodEvent.init2();
        YearlyPeriodEvent.SeasonXMLWriter.init();
        rnd = new Random();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public YearlyPeriodEventTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of reset method, of class Season.
     */
    @Test
    public void testPlacement() {

        YearlyPeriodEvent eventToPlaceSeason = new YearlyPeriodEvent();
        eventToPlaceSeason.reset();
        eventToPlaceSeason.setUpForComplexEval();
        List<YearlyPeriodEvent.YearlyPeriodEventPlacement> possiblePlacements = new java.util.ArrayList<>();
        for (YearlyPeriodEvent.YearlyPeriodEventPlacement curPlacement : eventToPlaceSeason.getPlacements()) {
            if (eventToPlaceSeason.getStartDayAcceptor().accept(curPlacement.startDay)
                    && eventToPlaceSeason.getEndDayAcceptor().accept(curPlacement.endDay)
                    && eventToPlaceSeason.isValid()) {
                possiblePlacements.add(curPlacement);
            }
        }
        YearlyPeriodEvent.YearlyPeriodEventPlacement selectedPlacement = possiblePlacements.get(rnd.nextInt(possiblePlacements.size()));
        eventToPlaceSeason.setViaPlacement(selectedPlacement);
        eventToPlaceSeason.normalize();
        assertTrue(eventToPlaceSeason.isValid());
    }

    /**
     * Test of reset method, of class Season.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        Set<DayOfYear> containsCouldDays = instance.getContainsCouldDays();
        int containsCouldDaysSize = containsCouldDays.size();
        int containsCouldDaysHashCode = containsCouldDays.hashCode();
        Set<DayOfYear> containsSureDays = instance.getContainsSureDays();
        int containsSureDaysSize = containsSureDays.size();
        int containsSureDaysHashCode = containsSureDays.hashCode();
        YearlyDayEvent end = instance.getEnd();
        NavigableSet<DayOfYear> endPossibleDays = instance.getEndPossibleDays();
        int endPossibleDaysSize = endPossibleDays.size();
        int endPossibleDaysHashCode = endPossibleDays.hashCode();
        int id = instance.getLeastSignificantIDPart();
        int maxDuration = instance.getMaxDuration();
        int minDuration = instance.getMinDuration();
        String name = instance.getName();
        List<YearlyPeriodEvent.YearlyPeriodEventPlacement> placements = instance.getPlacements();
        int placementsSize = placements.size();
        int placementsHashCode = placements.hashCode();
        YearlyDayEvent start = instance.getStart();
        NavigableSet<DayOfYear> startPossibleDays = instance.getStartPossibleDays();
        int startPossibleDaysSize = startPossibleDays.size();
        int startPossibleDaysHashCode = startPossibleDays.hashCode();
        int size = instance.size();
        int hashCode = instance.hashCode();
        instance.setMaxDuration(rnd.nextInt());
        instance.setMinDuration(rnd.nextInt());
        
        instance.reset();
        assertEquals(containsCouldDays, instance.getContainsCouldDays());
        containsCouldDays = instance.getContainsCouldDays();
        assertEquals(containsCouldDaysSize, containsCouldDays.size());
        assertEquals(containsCouldDaysHashCode, containsCouldDays.hashCode());
        assertEquals(containsSureDays, instance.getContainsSureDays());
        containsSureDays = instance.getContainsSureDays();
        assertEquals(containsSureDaysSize, containsSureDays.size());
        assertEquals(containsSureDaysHashCode, containsSureDays.hashCode());
        assertEquals(end, instance.getEnd());
        assertEquals(endPossibleDays, instance.getEndPossibleDays());
        endPossibleDays = instance.getEndPossibleDays();
        assertEquals(endPossibleDaysSize, endPossibleDays.size());
        assertEquals(endPossibleDaysHashCode, endPossibleDays.hashCode());
        assertEquals(id, instance.getLeastSignificantIDPart());
        assertEquals(maxDuration, instance.getMaxDuration());
        assertEquals(minDuration, instance.getMinDuration());
        assertEquals(name, instance.getName());
        assertEquals(placements, instance.getPlacements());
        placements = instance.getPlacements();
        assertEquals(placementsSize, placements.size());
        assertEquals(placementsHashCode, placements.hashCode());
        assertEquals(start, instance.getStart());
        assertEquals(startPossibleDays, instance.getStartPossibleDays());
        startPossibleDays = instance.getStartPossibleDays();
        assertEquals(startPossibleDaysSize, startPossibleDays.size());
        assertEquals(startPossibleDaysHashCode, startPossibleDays.hashCode());
        assertEquals(size, instance.size());
        assertEquals(hashCode, instance.hashCode());
    }

    /**
     * Test of normalize method, of class Season.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        instance.normalize();
        assertTrue(instance.isValid());
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class Season.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        instance.setMaxDuration(1);
        instance.setMinDuration(2);
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        instance.setMaxDuration(2);
        instance.setMinDuration(1);
        expResult = true;
        result = instance.isValid();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinDuration method, of class Season.
     */
    @Test
    public void testGetMinDuration() {
        System.out.println("getMinDuration");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        for (int i = 0; i < 100; i++) {
            int expResult = rnd.nextInt();
            instance.setMinDuration(expResult);
            int result = instance.getMinDuration();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getMaxDuration method, of class Season.
     */
    @Test
    public void testGetMaxDuration() {
        System.out.println("getMaxDuration");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        for (int i = 0; i < 100; i++) {
            int expResult = rnd.nextInt();
            instance.setMaxDuration(expResult);
            int result = instance.getMaxDuration();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getStart method, of class Season.
     */
    @Test
    public void testGetStart() {
        System.out.println("getStart");
        YearlyPeriodEvent instance = null;
        YearlyDayEvent expResult = null;
        YearlyDayEvent result = instance.getStart();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnd method, of class Season.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        YearlyPeriodEvent instance = null;
        YearlyDayEvent expResult = null;
        YearlyDayEvent result = instance.getEnd();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsCould method, of class Season.
     */
    @Test
    public void testContainsCould_DateTime() {
        System.out.println("containsCould");
        DateTime inDateTime = null;
        YearlyPeriodEvent instance = null;
        boolean expResult = false;
        boolean result = instance.containsCould(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsCould method, of class Season.
     */
    @Test
    public void testContainsCould_DayOfYear() {
        System.out.println("containsCould");
        DayOfYear inDateTime = null;
        YearlyPeriodEvent instance = null;
        boolean expResult = false;
        boolean result = instance.containsCould(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsSure method, of class Season.
     */
    @Test
    public void testContainsSure_DateTime() {
        System.out.println("containsSure");
        DateTime inDateTime = null;
        YearlyPeriodEvent instance = null;
        boolean expResult = false;
        boolean result = instance.containsSure(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsSure method, of class Season.
     */
    @Test
    public void testContainsSure_DayOfYear() {
        System.out.println("containsSure");
        DayOfYear inDateTime = null;
        YearlyPeriodEvent instance = null;
        boolean expResult = false;
        boolean result = instance.containsSure(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abutsBefore method, of class Season.
     */
    @Test
    public void testAbutsBefore() {
        System.out.println("abutsBefore");
        PeriodEvent inPeriodEvent = null;
        YearlyPeriodEvent instance = null;
        boolean expResult = false;
        boolean result = instance.abutsBefore(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abutsAfter method, of class Season.
     */
    @Test
    public void testAbutsAfter() {
        System.out.println("abutsAfter");
        PeriodEvent inPeriodEvent = null;
        YearlyPeriodEvent instance = null;
        boolean expResult = false;
        boolean result = instance.abutsAfter(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAbutsBefore method, of class Season.
     */
    @Test
    public void testSetAbutsBefore() {
        System.out.println("setAbutsBefore");
        PeriodEvent inPeriodEvent = null;
        YearlyPeriodEvent instance = null;
        boolean expResult = false;
        boolean result = instance.setAbutsBefore(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAbutsAfter method, of class Season.
     */
    @Test
    public void testSetAbutsAfter() {
        System.out.println("setAbutsAfter");
        PeriodEvent inPeriodEvent = null;
        YearlyPeriodEvent instance = null;
        boolean expResult = false;
        boolean result = instance.setAbutsAfter(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstance method, of class Season.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        int year = 0;
        YearlyPeriodEvent instance = null;
        OncePeriodEvent expResult = null;
        OncePeriodEvent result = instance.getInstance(year);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class Season.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        YearlyPeriodEvent instance = null;
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sizeAdj method, of class Season.
     */
    @Test
    public void testSizeAdj() {
        System.out.println("sizeAdj");
        YearlyPeriodEvent instance = null;
        double expResult = 0.0;
        double result = instance.sizeAdj();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTimeFrameString method, of class Season.
     */
    @Test
    public void testToTimeFrameString() {
        System.out.println("toTimeFrameString");
        YearlyPeriodEvent instance = null;
        String expResult = "";
        String result = instance.toTimeFrameString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartDayAcceptor method, of class Season.
     */
    @Test
    public void testGetStartDayAcceptor() {
        System.out.println("getStartDayAcceptor");
        YearlyPeriodEvent instance = null;
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getStartDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndDayAcceptor method, of class Season.
     */
    @Test
    public void testGetEndDayAcceptor() {
        System.out.println("getEndDayAcceptor");
        YearlyPeriodEvent instance = null;
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getEndDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartPossibleDays method, of class Season.
     */
    @Test
    public void testGetStartPossibleDays() {
        System.out.println("getStartPossibleDays");
        YearlyPeriodEvent instance = null;
        NavigableSet<DayOfYear> expResult = null;
        NavigableSet<DayOfYear> result = instance.getStartPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndPossibleDays method, of class Season.
     */
    @Test
    public void testGetEndPossibleDays() {
        System.out.println("getEndPossibleDays");
        YearlyPeriodEvent instance = null;
        NavigableSet<DayOfYear> expResult = null;
        NavigableSet<DayOfYear> result = instance.getEndPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContainsDayAcceptor method, of class Season.
     */
    @Test
    public void testGetContainsDayAcceptor() {
        System.out.println("getContainsDayAcceptor");
        YearlyPeriodEvent instance = null;
        ContainsDayAcceptor expResult = null;
        ContainsDayAcceptor result = instance.getContainsDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNonoverlapingDayAcceptor method, of class Season.
     */
    @Test
    public void testGetNonoverlapingDayAcceptor() {
        System.out.println("getNonoverlapingDayAcceptor");
        YearlyPeriodEvent instance = null;
        NonoverlapingDayAcceptor expResult = null;
        NonoverlapingDayAcceptor result = instance.getNonoverlapingDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testXmlWriter() {
        System.out.println("testXmlWriter");
        XMLWriterImp<YearlyPeriodEvent> writer = XMLWriterImp.getXMLWriter(YearlyPeriodEvent.class);
        for (int i = 0; i < 365; i++) {
            YearlyPeriodEvent instance = new YearlyPeriodEvent(java.lang.Integer.toString(i));
            // TODO:set up instance randomly
            Element newElement = writer.writeElements(instance);
            YearlyPeriodEvent result = writer.readElements(newElement);
            assertEquals(instance.getLeastSignificantIDPart(), result.getLeastSignificantIDPart());
            assertEquals(instance.getName(), result.getName());
            assertEquals(instance.getMaxDuration(), result.getMaxDuration());
            assertEquals(instance.getMinDuration(), result.getMinDuration());
            assertTrue(instance.getEndPossibleDays().containsAll(result.getEndPossibleDays()));
            assertTrue(result.getEndPossibleDays().containsAll(instance.getEndPossibleDays()));
            assertTrue(instance.getStartPossibleDays().containsAll(result.getStartPossibleDays()));
            assertTrue(result.getStartPossibleDays().containsAll(instance.getStartPossibleDays()));
        }
    }

    /**
     * Test of getContainsSureDays method, of class Season.
     */
    @Test
    public void testGetContainsSureDays() {
        System.out.println("getContainsSureDays");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        Set<DayOfYear> expResult = null;
        Set<DayOfYear> result = instance.getContainsSureDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContainsCouldDays method, of class Season.
     */
    @Test
    public void testGetContainsCouldDays() {
        System.out.println("getContainsCouldDays");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        Set<DayOfYear> expResult = null;
        Set<DayOfYear> result = instance.getContainsCouldDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlacements method, of class Season.
     */
    @Test
    public void testGetPlacements() {
        System.out.println("getPlacements");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        List<YearlyPeriodEvent.YearlyPeriodEventPlacement> expResult = null;
        List<YearlyPeriodEvent.YearlyPeriodEventPlacement> result = instance.getPlacements();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpForComplexEval method, of class Season.
     */
    @Test
    public void testSetUpForComplexEval() {
        System.out.println("setUpForComplexEval");
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        instance.setUpForComplexEval();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setViaPlacement method, of class Season.
     */
    @Test
    public void testSetViaPlacement() {
        System.out.println("setViaPlacement");
        YearlyPeriodEvent.YearlyPeriodEventPlacement inPlacement = null;
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        instance.setViaPlacement(inPlacement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTo method, of class Season.
     */
    @Test
    public void testSetTo() {
        System.out.println("setTo");
        Collection<Placement> inPlacements = null;
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        instance.setTo(inPlacements);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addContainedEvent method, of class Season.
     */
    @Test
    public void testAddContainedEvent() {
        System.out.println("addContainedEvent");
        Event inEvent = null;
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        instance.addContainedEvent(inEvent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNonoverlapingEvent method, of class Season.
     */
    @Test
    public void testAddNonoverlapingEvent() {
        System.out.println("addNonoverlapingEvent");
        Event inEvent = null;
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        instance.addNonoverlapingEvent(inEvent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of init2 method, of class YearlyPeriodEvent.
     */
    @Test
    public void testInit2() {
        System.out.println("init2");
        YearlyPeriodEvent.init2();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinDuration method, of class YearlyPeriodEvent.
     */
    @Test
    public void testSetMinDuration() {
        System.out.println("setMinDuration");
        
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        for (int i = 0; i < 100; i ++){
        int minDuration = rnd.nextInt();
        instance.setMinDuration(minDuration);
        assertEquals(minDuration, instance.getMinDuration());
        }
    }

    /**
     * Test of setMaxDuration method, of class YearlyPeriodEvent.
     */
    @Test
    public void testSetMaxDuration() {
        System.out.println("setMaxDuration");
        int maxDuration = 0;
        YearlyPeriodEvent instance = new YearlyPeriodEvent();
        instance.setMaxDuration(maxDuration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
