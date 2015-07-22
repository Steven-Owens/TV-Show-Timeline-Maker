/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.Placement;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class AgeConstraintTest {
    private static final Logger LOG = Logger.getLogger(AgeConstraintTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
        AgeConstraint.init2();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
        public AgeConstraintTest() {
        }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of applyConstraint method, of class AgeConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        DateTime center = new DateTime(0,1,1,1,0,0);
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        testEvent.setEarliestPossibleDate(center);
        testEvent.setLatestPossibleDate(center);
        instance.applyConstraint();
        assertEquals(testCharacter.getBirthday().getEarliestPossibleDate(), center.minusYears(testUpperbound).withHourOfDay(10));
        assertEquals(testCharacter.getBirthday().getLatestPossibleDate(), center.minusYears(testLowerBound).withHourOfDay(14));
        assertEquals(testEvent.getEarliestPossibleDate(), center.withHourOfDay(10));
        assertEquals(testEvent.getLatestPossibleDate(), center.withHourOfDay(14));
        testEvent.reset();
        testCharacter.getBirthday().setEarliestPossibleDate(center);
        testCharacter.getBirthday().setLatestPossibleDate(center);
        instance.applyConstraint();
        assertEquals(testCharacter.getBirthday().getEarliestPossibleDate(), center.withHourOfDay(10));
        assertEquals(testCharacter.getBirthday().getLatestPossibleDate(), center.withHourOfDay(14));
        assertEquals(testEvent.getEarliestPossibleDate(), center.plusYears(testLowerBound).withHourOfDay(10));
        assertEquals(testEvent.getLatestPossibleDate(), center.plusYears(testUpperbound).withHourOfDay(14));
    }

    /**
     * Test of init2 method, of class AgeConstraint.
     */
    @Test
    public void testInit2() {
        System.out.println("init2");
        AgeConstraint.init2();
        String name = "AgeConstraint";
        assertEquals(MyLittePonyMaps.getConstraintClassForFriendlyString(name), AgeConstraint.class);
        assertEquals(MyLittePonyMaps.getFriendlyStringForConstraintClass(AgeConstraint.class), name);
    }

    /**
     * Test of getFirstEvent method, of class AgeConstraint.
     */
    @Test
    public void testGetFirstEvent() {
        System.out.println("getFirstEvent");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        EventImp expResult = testCharacter.getBirthday();
        EventImp result = instance.getFirstEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSecondEvent method, of class AgeConstraint.
     */
    @Test
    public void testGetSecondEvent() {
        System.out.println("getSecondEvent");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        EventImp expResult = testEvent;
        EventImp result = instance.getSecondEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrictlyBefore method, of class AgeConstraint.
     */
    @Test
    public void testIsStrictlyBefore() {
        System.out.println("isStrictlyBefore");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        boolean expResult = true;
        boolean result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getmCharacter method, of class AgeConstraint.
     */
    @Test
    public void testGetmCharacter() {
        System.out.println("getmCharacter");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        NamedCharacter expResult = testCharacter;
        NamedCharacter result = instance.getmCharacter();
        assertEquals(expResult, result);
    }

    /**
     * Test of getmAtEvent method, of class AgeConstraint.
     */
    @Test
    public void testGetmAtEvent() {
        System.out.println("getmAtEvent");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        OnceDayEvent expResult = testEvent;
        OnceDayEvent result = instance.getmAtEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getConstrainedEvents method, of class AgeConstraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        OnceDayEvent[] expResult = new OnceDayEvent[]{testCharacter.getBirthday(), testEvent};
        OnceDayEvent[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of consistentWithConstraint method, of class AgeConstraint.
     */
    @Test
    public void testConsistentWithConstraint_DateTime_DateTime() {
        System.out.println("consistentWithConstraint");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        DateTime inFirstDay = new DateTime(0,1,1,1,0,0);
        DateTime inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        boolean expResult = true;
        boolean result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        expResult = false;
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inFirstDay = new DateTime(3,1,1,1,0,0);
        inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        expResult = true;
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        expResult = false;
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        testLowerBound = 6;
        testUpperbound = 9;
        instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        inFirstDay = new DateTime(0,1,1,1,0,0);
        inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        expResult = true;
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        expResult = false;
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        testLowerBound = 2;
        testUpperbound = 20;
        instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        inFirstDay = new DateTime(0,1,1,1,0,0);
        inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        expResult = true;
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        expResult = false;
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
    }

    /**
     * Test of complexApplyConstraint method, of class AgeConstraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        DateTime center = new DateTime(0,1,1,1,0,0);
        center = center.withHourOfDay(12);
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        testEvent.setEarliestPossibleDate(center.minusDays(2));
        testEvent.setLatestPossibleDate(center.plusDays(2));
        instance.applyConstraint();
        testCharacter.getBirthday().setUpForComplexEval();
        testEvent.setUpForComplexEval();
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        assertEquals(testCharacter.getBirthday().getPossibleDays().first().withHourOfDay(12), center.minusDays(2).minusYears(testUpperbound).withHourOfDay(12));
        assertEquals(testCharacter.getBirthday().getPossibleDays().last().withHourOfDay(12), center.plusDays(2).minusYears(testLowerBound).withHourOfDay(12));
        testCharacter.getBirthday().getPossibleDays().add(center.minusDays(3).minusYears(testUpperbound).withHourOfDay(12));
        testCharacter.getBirthday().getPossibleDays().add(center.plusDays(3).minusYears(testLowerBound).withHourOfDay(12));
        expResult = true;
        result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        assertEquals(testCharacter.getBirthday().getPossibleDays().first().withHourOfDay(12), center.minusDays(2).minusYears(testUpperbound).withHourOfDay(12));
        assertEquals(testCharacter.getBirthday().getPossibleDays().last().withHourOfDay(12), center.plusDays(2).minusYears(testLowerBound).withHourOfDay(12));
        
        DateTime newcenter = center.plusYears(2 * testUpperbound);
        testCharacter.getBirthday().setLatestPossibleDate(newcenter);
        testEvent.getPossibleDays().add(newcenter);
        testCharacter.getBirthday().addPossibleDays();
        instance.complexApplyConstraint();
        
        assertEquals(testCharacter.getBirthday().getPossibleDays().first().withHourOfDay(12), center.minusDays(2).minusYears(testUpperbound).withHourOfDay(12));
        assertTrue(testCharacter.getBirthday().getPossibleDays().contains(center.plusDays(2).minusYears(testLowerBound).withHourOfDay(12)));
        assertTrue(testCharacter.getBirthday().getPossibleDays().contains(newcenter.minusYears(testUpperbound).withHourOfDay(12)));
        assertEquals(testCharacter.getBirthday().getPossibleDays().last().withHourOfDay(12), newcenter.minusYears(testLowerBound).withHourOfDay(12));
        assertFalse(testCharacter.getBirthday().getPossibleDays().contains(center.plusDays(3).minusYears(testLowerBound).withHourOfDay(12)));
        assertFalse(testCharacter.getBirthday().getPossibleDays().contains(newcenter.minusDays(1).minusYears(testUpperbound).withHourOfDay(12)));
    }

    /**
     * Test of ConstraintSatisfied method, of class AgeConstraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        /*
        AgeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        */
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        DateTime center = new DateTime(0,1,1,1,0,0);
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        testEvent.setEarliestPossibleDate(center);
        testEvent.setLatestPossibleDate(center);
        testCharacter.getBirthday().setEarliestPossibleDate(center.minusYears(testUpperbound).withHourOfDay(10));
        testCharacter.getBirthday().setLatestPossibleDate(center.minusYears(testLowerBound).withHourOfDay(14));
        boolean expResult = true;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        testCharacter.getBirthday().setEarliestPossibleDate(center.minusYears(testUpperbound).minusDays(1).withHourOfDay(10));
        testCharacter.getBirthday().setLatestPossibleDate(center.minusYears(testLowerBound).withHourOfDay(14));
        expResult = false;
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        testCharacter.getBirthday().setEarliestPossibleDate(center.minusYears(testUpperbound).withHourOfDay(10));
        testCharacter.getBirthday().setLatestPossibleDate(center.minusYears(testLowerBound).plusDays(1).withHourOfDay(14));
        expResult = false;
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        testEvent.reset();
        expResult = true;
        testCharacter.getBirthday().setEarliestPossibleDate(center);
        testCharacter.getBirthday().setLatestPossibleDate(center);
        testEvent.setEarliestPossibleDate(center.plusYears(testLowerBound));
        testEvent.setLatestPossibleDate(center.plusYears(testUpperbound));
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        expResult = false;
        testEvent.setEarliestPossibleDate(center.plusYears(testLowerBound).minusDays(1));
        testEvent.setLatestPossibleDate(center.plusYears(testUpperbound));
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        testEvent.setEarliestPossibleDate(center.plusYears(testLowerBound));
        testEvent.setLatestPossibleDate(center.plusYears(testUpperbound).plusDays(1));
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
    }

    /**
     * Test of consistentWithConstraint method, of class AgeConstraint.
     */
    @Test
    public void testConsistentWithConstraint_PlacementArr() {
        System.out.println("consistentWithConstraint");
        /*Placement[] inValues = null;
        AgeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);*/
        
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        DateTime inFirstDay = new DateTime(0,1,1,1,0,0);
        DateTime inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        Placement[] inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        boolean expResult = true;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        expResult = false;
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inFirstDay = new DateTime(3,1,1,1,0,0);
        inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        expResult = true;
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        expResult = false;
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        testLowerBound = 6;
        testUpperbound = 9;
        instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        inFirstDay = new DateTime(0,1,1,1,0,0);
        inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        expResult = true;
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        expResult = false;
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        testLowerBound = 2;
        testUpperbound = 20;
        instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        inFirstDay = new DateTime(0,1,1,1,0,0);
        inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        expResult = true;
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        expResult = false;
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
    }

    /**
     * Test of increaseWhat method, of class AgeConstraint.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        DateTime inFirstDay = new DateTime(0,1,1,1,0,0);
        DateTime inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        Placement[] inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        Event[] expResult = new Event[] {};
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        expResult = new Event[] {testEvent};
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        expResult = new Event[] {testCharacter.getBirthday()};
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        inFirstDay = new DateTime(3,1,1,1,0,0);
        inSecondDay = inFirstDay.plusYears(testLowerBound).plusDays(1);
        expResult = new Event[] {};
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(testUpperbound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        expResult = new Event[] {testEvent};
        inSecondDay = inFirstDay.plusYears(testLowerBound).minusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        expResult = new Event[] {testCharacter.getBirthday()};
        inSecondDay = inFirstDay.plusYears(testUpperbound).plusDays(1);
        inValues = new Placement[2];
        inValues[0] = new OnceDayEvent.OnceDayEventPlacement(inFirstDay);
        inValues[1] = new OnceDayEvent.OnceDayEventPlacement(inSecondDay);
        result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of isStrict method, of class AgeConstraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        int testLowerBound = 5;
        int testUpperbound = 10;
        AgeConstraint instance = new AgeConstraint(testCharacter,testLowerBound,testUpperbound,testEvent);
        boolean expResult = true;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
    }

    
}
