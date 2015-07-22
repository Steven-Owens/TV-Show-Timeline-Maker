/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.util.DayOfYear;
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
public class YearlyDayEventTest {
    
    @BeforeClass
    public static void setUpClass() {
        YearlyDayEvent.init2();
        YearlyDayEvent.YearlyDayEventXMLWriter.init();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
        public YearlyDayEventTest() {
        }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init2 method, of class YearlyDayEvent.
     */
    @Test
    public void testInit2() {
        System.out.println("init2");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalize method, of class YearlyDayEvent.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        YearlyDayEvent instance = new YearlyDayEvent();
        instance.normalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSuggestedDay method, of class YearlyDayEvent.
     */
    @Test
    public void testAddSuggestedDay_DateTime() {
        System.out.println("addSuggestedDay");
        DateTime inDate = null;
        YearlyDayEvent instance = new YearlyDayEvent();
        boolean expResult = false;
        boolean result = instance.addSuggestedDay(inDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSuggestedDay method, of class YearlyDayEvent.
     */
    @Test
    public void testAddSuggestedDay_DayOfYear() {
        System.out.println("addSuggestedDay");
        DayOfYear inDate = null;
        YearlyDayEvent instance = new YearlyDayEvent();
        boolean expResult = false;
        boolean result = instance.addSuggestedDay(inDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeSuggestedDay method, of class YearlyDayEvent.
     */
    @Test
    public void testRemoveSuggestedDay() {
        System.out.println("removeSuggestedDay");
        DayOfYear inDate = null;
        YearlyDayEvent instance = new YearlyDayEvent();
        boolean expResult = false;
        boolean result = instance.removeSuggestedDay(inDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuggestedDays method, of class YearlyDayEvent.
     */
    @Test
    public void testGetSuggestedDays() {
        System.out.println("getSuggestedDays");
        YearlyDayEvent instance = new YearlyDayEvent();
        Collection<DayOfYear> expResult = null;
        Collection<DayOfYear> result = instance.getSuggestedDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class YearlyDayEvent.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        YearlyDayEvent instance = new YearlyDayEvent();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class YearlyDayEvent.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        YearlyDayEvent instance = new YearlyDayEvent();
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPossibleDays method, of class YearlyDayEvent.
     */
    @Test
    public void testAddPossibleDays() {
        System.out.println("addPossibleDays");
        YearlyDayEvent instance = new YearlyDayEvent();
        instance.addPossibleDays();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstance method, of class YearlyDayEvent.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        int year = 0;
        YearlyDayEvent instance = new YearlyDayEvent();
        OnceEvent expResult = null;
        OnceEvent result = instance.getInstance(year);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class YearlyDayEvent.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        YearlyDayEvent instance = new YearlyDayEvent();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sizeAdj method, of class YearlyDayEvent.
     */
    @Test
    public void testSizeAdj() {
        System.out.println("sizeAdj");
        YearlyDayEvent instance = new YearlyDayEvent();
        double expResult = 0.0;
        double result = instance.sizeAdj();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTimeFrameString method, of class YearlyDayEvent.
     */
    @Test
    public void testToTimeFrameString() {
        System.out.println("toTimeFrameString");
        YearlyDayEvent instance = new YearlyDayEvent();
        String expResult = "";
        String result = instance.toTimeFrameString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDayAcceptor method, of class YearlyDayEvent.
     */
    @Test
    public void testGetDayAcceptor() {
        System.out.println("getDayAcceptor");
        YearlyDayEvent instance = new YearlyDayEvent();
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPossibleDays method, of class YearlyDayEvent.
     */
    @Test
    public void testGetPossibleDays() {
        System.out.println("getPossibleDays");
        YearlyDayEvent instance = new YearlyDayEvent();
        NavigableSet<DayOfYear> expResult = null;
        NavigableSet<DayOfYear> result = instance.getPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testXmlWriter() {
        System.out.println("testXmlWriter");
        XMLWriterImp<YearlyDayEvent> writer = XMLWriterImp.getXMLWriter(YearlyDayEvent.class);
        for (int i = 0; i < 365; i++) {
            YearlyDayEvent instance = new YearlyDayEvent(java.lang.Integer.toString(i));
            // TODO:set up instance randomly
            Element newElement = writer.writeElements(instance);
            YearlyDayEvent result = writer.readElements(newElement);
            assertEquals(instance.getLeastSignificantIDPart(), result.getLeastSignificantIDPart());
            assertEquals(instance.getName(), result.getName());
            assertTrue(instance.getSuggestedDays().containsAll(result.getSuggestedDays()));
            assertTrue(result.getSuggestedDays().containsAll(instance.getSuggestedDays()));
            assertTrue(instance.getPossibleDays().containsAll(result.getPossibleDays()));
            assertTrue(result.getPossibleDays().containsAll(instance.getPossibleDays()));
        }
    }

    /**
     * Test of getPlacements method, of class YearlyDayEvent.
     */
    @Test
    public void testGetPlacements() {
        System.out.println("getPlacements");
        YearlyDayEvent instance = new YearlyDayEvent();
        List<YearlyDayEvent.YearlyDayEventPlacement> expResult = null;
        List<YearlyDayEvent.YearlyDayEventPlacement> result = instance.getPlacements();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setViaPlacement method, of class YearlyDayEvent.
     */
    @Test
    public void testSetViaPlacement() {
        System.out.println("setViaPlacement");
        YearlyDayEvent.YearlyDayEventPlacement inPlacement = null;
        YearlyDayEvent instance = new YearlyDayEvent();
        instance.setViaPlacement(inPlacement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTo method, of class YearlyDayEvent.
     */
    @Test
    public void testSetTo() {
        System.out.println("setTo");
        Collection<Placement> inPlacements = null;
        YearlyDayEvent instance = new YearlyDayEvent();
        instance.setTo(inPlacements);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
