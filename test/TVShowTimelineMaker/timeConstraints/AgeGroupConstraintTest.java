/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
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
public class AgeGroupConstraintTest {
    private static final Logger LOG = Logger.getLogger(AgeGroupConstraintTest.class.getName());

    @BeforeClass
    public static void setUpClass() {
        AgeGroupConstraint.init2();
    }

    @AfterClass
    public static void tearDownClass() {
    }

        public AgeGroupConstraintTest() {
        }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of complexApplyConstraint method, of class AgeGroupConstraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        DateTime center = new DateTime(0, 1, 1, 1, 0, 0);
        center = center.withHourOfDay(12);
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            testCharacter.getBirthday().reset();
            testEvent.reset();
            int testLowerBound = curAgeGroup.getLowerBound();
            int testUpperbound = curAgeGroup.getUpperBound();
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
            testEvent.setEarliestPossibleDate(center.minusDays(2));
            testEvent.setLatestPossibleDate(center.plusDays(2));
            instance.applyConstraint();
            testEvent.setUpForComplexEval();
            testCharacter.getBirthday().setUpForComplexEval();
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
    }

    /**
     * Test of applyConstraint method, of class AgeConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        DateTime center = new DateTime(0, 1, 1, 1, 0, 0);
        center = center.withHourOfDay(12);
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            System.out.println(curAgeGroup.toString());
            testCharacter.getBirthday().reset();
            int testLowerBound = curAgeGroup.getLowerBound();
            int testUpperbound = curAgeGroup.getUpperBound();
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
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
    }

    /**
     * Test of isStrict method, of class AgeGroupConstraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
            boolean expResult = true;
            boolean result = instance.isStrict();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getFirstEvent method, of class AgeGroupConstraint.
     */
    @Test
    public void testGetFirstEvent() {
        System.out.println("getFirstEvent");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        EventImp expResult = testCharacter.getBirthday();
        EventImp result = instance.getFirstEvent();
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of getSecondEvent method, of class AgeGroupConstraint.
     */
    @Test
    public void testGetSecondEvent() {
        System.out.println("getSecondEvent");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        EventImp expResult = testEvent;
        EventImp result = instance.getSecondEvent();
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of isStrictlyBefore method, of class AgeGroupConstraint.
     */
    @Test
    public void testIsStrictlyBefore() {
        System.out.println("isStrictlyBefore");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        boolean expResult = true;
        boolean result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of getmCharacter method, of class AgeGroupConstraint.
     */
    @Test
    public void testGetmCharacter() {
        System.out.println("getmCharacter");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        NamedCharacter expResult = testCharacter;
        NamedCharacter result = instance.getmCharacter();
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of getmAgeGroup method, of class AgeGroupConstraint.
     */
    @Test
    public void testGetmAgeGroup() {
        System.out.println("getmAgeGroup");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        AgeGroupConstraint.AgeGroup expResult = curAgeGroup;
        AgeGroupConstraint.AgeGroup result = instance.getmAgeGroup();
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of getmAtEvent method, of class AgeGroupConstraint.
     */
    @Test
    public void testGetmAtEvent() {
        System.out.println("getmAtEvent");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        OnceDayEvent expResult = testEvent;
        OnceDayEvent result = instance.getmAtEvent();
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of getConstrainedEvents method, of class AgeGroupConstraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        OnceDayEvent[] expResult = new OnceDayEvent[] {testCharacter.getBirthday(), testEvent};
        OnceDayEvent[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
        }
    }

    /**
     * Test of consistentWithConstraint method, of class AgeGroupConstraint.
     */
    @Test
    public void testConsistentWithConstraint_DateTime_DateTime() {
        System.out.println("consistentWithConstraint");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        DateTime inFirstDay = new DateTime(1,1,1,12,1,1);
        DateTime inSecondDay = inFirstDay.plusYears(curAgeGroup.getLowerBound());
        boolean expResult = true;
        boolean result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(curAgeGroup.getUpperBound());
        expResult = true;
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(curAgeGroup.getUpperBound()).plusDays(1);
        expResult = false;
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        inSecondDay = inFirstDay.plusYears(curAgeGroup.getLowerBound()).minusDays(1);
        expResult = false;
        result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of increaseWhat method, of class AgeGroupConstraint.
     */
    @Test
    public void testIncreaseWhat() {
        /*System.out.println("increaseWhat");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        Placement[] inValues = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }*/
    }

    /**
     * Test of ConstraintSatisfied method, of class AgeGroupConstraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied"); 
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        DateTime center = new DateTime(0, 1, 1, 12, 0, 0);
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
            testCharacter.getBirthday().setEarliestPossibleDate(center);
            testCharacter.getBirthday().setLatestPossibleDate(center.plusMonths(1));
            testEvent.setEarliestPossibleDate(testCharacter.getBirthday().getEarliestPossibleDate().plusYears(curAgeGroup.getLowerBound()));
            testEvent.setLatestPossibleDate(testCharacter.getBirthday().getLatestPossibleDate().plusYears(curAgeGroup.getLowerBound()));
        boolean expResult = true;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        testEvent.setEarliestPossibleDate(testCharacter.getBirthday().getEarliestPossibleDate().plusYears(curAgeGroup.getUpperBound()));
            testEvent.setLatestPossibleDate(testCharacter.getBirthday().getLatestPossibleDate().plusYears(curAgeGroup.getUpperBound()));
        expResult = true;
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        testEvent.setEarliestPossibleDate(testCharacter.getBirthday().getEarliestPossibleDate().plusYears(curAgeGroup.getUpperBound()));
            testEvent.setLatestPossibleDate(testCharacter.getBirthday().getLatestPossibleDate().plusYears(curAgeGroup.getUpperBound()).plusDays(1));
        expResult = false;
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        testEvent.setEarliestPossibleDate(testCharacter.getBirthday().getEarliestPossibleDate().plusYears(curAgeGroup.getLowerBound()).minusDays(1));
            testEvent.setLatestPossibleDate(testCharacter.getBirthday().getLatestPossibleDate().plusYears(curAgeGroup.getLowerBound()));
        expResult = false;
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of consistentWithConstraint method, of class AgeGroupConstraint.
     */
    @Test
    public void testConsistentWithConstraint_PlacementArr() {
        /*System.out.println("consistentWithConstraint");
        NamedCharacter testCharacter = new NamedCharacter("ann");
        OnceDayEvent testEvent = new OnceDayEvent("ref point");
        for (AgeGroupConstraint.AgeGroup curAgeGroup : AgeGroupConstraint.AgeGroup.values()) {
            AgeGroupConstraint instance = new AgeGroupConstraint(testCharacter, curAgeGroup, testEvent);
        Placement[] inValues = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }*/
    }

}
