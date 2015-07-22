/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.ReadablePeriod;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class RelationTest {
    private static final Logger LOG = Logger.getLogger(RelationTest.class.getName());

    @BeforeClass
    public static void setUpClass() {
        Relation.init();
    }

    @AfterClass
    public static void tearDownClass() {
    }

        public RelationTest() {
        }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of cullDays method, of class AgeConstraint.
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        DateTime center = new DateTime(0, 1, 1, 1, 0, 0);
        center = center.withHourOfDay(12);
        int limit = 10;
        ReadablePeriod testPeriod = org.joda.time.Days.days(limit);
        for (Relation.RelationKind curKind : Relation.RelationKind.values()) {
            System.out.println(curKind.toString());
            testEvent1.reset();
            testEvent2.reset();
            if (curKind != Relation.RelationKind.SAME_DAY_OF_YEAR) {
                int testLowerBound = 1;
                int testUpperbound = java.lang.Integer.MAX_VALUE;
                if (curKind == Relation.RelationKind.BEFORE_SAME_DAY) {
                    testLowerBound = 0;
                } else if (curKind == Relation.RelationKind.FIXED_TIME_BEFORE) {
                    testLowerBound = limit;
                    testUpperbound = limit;
                } else if (curKind == Relation.RelationKind.LESS_THEN_BEFORE) {
                    testUpperbound = limit;
                } else if (curKind == Relation.RelationKind.MORE_THEN_BEFORE) {
                    testLowerBound = limit;
                } else if (curKind == Relation.RelationKind.SAME_TIME_AS) {
                    testLowerBound = 0;
                    testUpperbound = 0;
                }
                Relation instance = new Relation(testEvent1, testEvent2, curKind, testPeriod);
                testEvent2.setEarliestPossibleDate(center);
                testEvent2.setLatestPossibleDate(center);
                instance.applyConstraint();
                testEvent2.setUpForComplexEval();
                testEvent1.setUpForComplexEval();
                boolean expResult = false;
                boolean result = instance.complexApplyConstraint();
                assertEquals(expResult, result);
                if (curKind == Relation.RelationKind.FIXED_TIME_BEFORE) {
                    for (DateTime curDay : testEvent2.getPossibleDays()) {
                        testEvent1.getPossibleDays().contains(curDay.minusDays(limit));
                    }
                    for (DateTime curDay : testEvent1.getPossibleDays()) {
                        testEvent2.getPossibleDays().contains(curDay.plusDays(limit));
                    }
                } else if (curKind == Relation.RelationKind.SAME_TIME_AS) {
                    assertTrue(testEvent1.getPossibleDays().containsAll(testEvent2.getPossibleDays()));
                    assertTrue(testEvent2.getPossibleDays().containsAll(testEvent1.getPossibleDays()));
                }
                if ((curKind == Relation.RelationKind.FIXED_TIME_BEFORE) || (curKind == Relation.RelationKind.LESS_THEN_BEFORE) || (curKind == Relation.RelationKind.SAME_TIME_AS)) {
                    assertEquals(testEvent1.getPossibleDays().first().withHourOfDay(12), center.minusDays(testUpperbound).withHourOfDay(12));
                    testEvent1.getPossibleDays().add(center.minusDays(1).minusDays(testUpperbound).withHourOfDay(12));
                }
                assertEquals(testEvent1.getPossibleDays().last().withHourOfDay(12), center.minusDays(testLowerBound).withHourOfDay(12));
                testEvent1.getPossibleDays().add(center.plusDays(3).minusDays(testLowerBound).withHourOfDay(12));
                expResult = true;
                result = instance.complexApplyConstraint();
                assertEquals(expResult, result);
                if (curKind == Relation.RelationKind.FIXED_TIME_BEFORE) {
                    for (DateTime curDay : testEvent2.getPossibleDays()) {
                        testEvent1.getPossibleDays().contains(curDay.minusDays(limit));
                    }
                    for (DateTime curDay : testEvent1.getPossibleDays()) {
                        testEvent2.getPossibleDays().contains(curDay.plusDays(limit));
                    }
                } else if (curKind == Relation.RelationKind.SAME_TIME_AS) {
                    assertTrue(testEvent1.getPossibleDays().containsAll(testEvent2.getPossibleDays()));
                    assertTrue(testEvent2.getPossibleDays().containsAll(testEvent1.getPossibleDays()));
                }
                if ((curKind == Relation.RelationKind.FIXED_TIME_BEFORE) || (curKind == Relation.RelationKind.LESS_THEN_BEFORE) || (curKind == Relation.RelationKind.SAME_TIME_AS)) {
                    assertEquals(testEvent1.getPossibleDays().first().withHourOfDay(12), center.minusDays(testUpperbound).withHourOfDay(12));
                }
                assertEquals(testEvent1.getPossibleDays().last().withHourOfDay(12), center.minusDays(testLowerBound).withHourOfDay(12));
                
                DateTime newcenter;
                if (testUpperbound < (java.lang.Integer.MAX_VALUE/2)) {
                        newcenter = center.plusDays(2 * testUpperbound);
                } else {
                    newcenter = center.plusDays(2);
                }
                testEvent1.setLatestPossibleDate(newcenter);
                testEvent2.getPossibleDays().add(newcenter);
                testEvent1.addPossibleDays();
                instance.complexApplyConstraint();

                if (curKind == Relation.RelationKind.FIXED_TIME_BEFORE) {
                    for (DateTime curDay : testEvent2.getPossibleDays()) {
                        testEvent1.getPossibleDays().contains(curDay.minusDays(limit));
                    }
                    for (DateTime curDay : testEvent1.getPossibleDays()) {
                        testEvent2.getPossibleDays().contains(curDay.plusDays(limit));
                    }
                } else if (curKind == Relation.RelationKind.SAME_TIME_AS) {
                    assertTrue(testEvent1.getPossibleDays().containsAll(testEvent2.getPossibleDays()));
                    assertTrue(testEvent2.getPossibleDays().containsAll(testEvent1.getPossibleDays()));
                }
                if ((curKind == Relation.RelationKind.FIXED_TIME_BEFORE) || (curKind == Relation.RelationKind.LESS_THEN_BEFORE) || (curKind == Relation.RelationKind.SAME_TIME_AS)) {
                    assertEquals(testEvent1.getPossibleDays().first().withHourOfDay(12), center.minusDays(testUpperbound).withHourOfDay(12));
                    assertTrue(testEvent1.getPossibleDays().contains(newcenter.minusDays(testUpperbound).withHourOfDay(12)));
                    assertFalse(testEvent1.getPossibleDays().contains(newcenter.minusDays(1).minusDays(testUpperbound).withHourOfDay(12)));
                    assertFalse(testEvent1.getPossibleDays().contains(center.plusDays(1).minusDays(testLowerBound).withHourOfDay(12)));
                }
                assertEquals(testEvent1.getPossibleDays().last().withHourOfDay(12), newcenter.minusDays(testLowerBound).withHourOfDay(12));
                assertTrue(testEvent1.getPossibleDays().contains(center.minusDays(testLowerBound).withHourOfDay(12)));
            }
        }
    }

    /**
     * Test of applyConstraint method, of class AgeConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        DateTime center = new DateTime(0, 1, 1, 1, 0, 0);
        center = center.withHourOfDay(12);
        int limit = 5;
        ReadablePeriod testPeriod = org.joda.time.Days.days(limit);
        for (Relation.RelationKind curKind : Relation.RelationKind.values()) {
            System.out.println(curKind.toString());
            testEvent1.reset();
            testEvent2.reset();
            if (curKind != Relation.RelationKind.SAME_DAY_OF_YEAR) {
                int testLowerBound = 1;
                int testUpperbound = java.lang.Integer.MAX_VALUE;
                if (curKind == Relation.RelationKind.BEFORE_SAME_DAY) {
                    testLowerBound = 0;
                } else if (curKind == Relation.RelationKind.FIXED_TIME_BEFORE) {
                    testLowerBound = limit;
                    testUpperbound = limit;
                } else if (curKind == Relation.RelationKind.LESS_THEN_BEFORE) {
                    testUpperbound = limit;
                } else if (curKind == Relation.RelationKind.MORE_THEN_BEFORE) {
                    testLowerBound = limit;
                } else if (curKind == Relation.RelationKind.SAME_TIME_AS) {
                    testLowerBound = 0;
                    testUpperbound = 0;
                }
                Relation instance = new Relation(testEvent1, testEvent2, curKind, testPeriod);
                testEvent2.setEarliestPossibleDate(center);
                testEvent2.setLatestPossibleDate(center);
                instance.applyConstraint();
                if ((curKind == Relation.RelationKind.FIXED_TIME_BEFORE) || (curKind == Relation.RelationKind.LESS_THEN_BEFORE) || (curKind == Relation.RelationKind.SAME_TIME_AS)) {
                    assertEquals(testEvent1.getEarliestPossibleDate(), center.minusDays(testUpperbound).withHourOfDay(10));
                }
                assertEquals(testEvent1.getLatestPossibleDate(), center.minusDays(testLowerBound).withHourOfDay(14));
                assertEquals(testEvent2.getEarliestPossibleDate(), center.withHourOfDay(10));
                assertEquals(testEvent2.getLatestPossibleDate(), center.withHourOfDay(14));
                testEvent2.reset();
                testEvent1.setEarliestPossibleDate(center);
                testEvent1.setLatestPossibleDate(center);
                instance.applyConstraint();
                assertEquals(testEvent1.getEarliestPossibleDate(), center.withHourOfDay(10));
                assertEquals(testEvent1.getLatestPossibleDate(), center.withHourOfDay(14));
                assertEquals(testEvent2.getEarliestPossibleDate(), center.plusDays(testLowerBound).withHourOfDay(10));
                if ((curKind == Relation.RelationKind.FIXED_TIME_BEFORE) || (curKind == Relation.RelationKind.LESS_THEN_BEFORE) || (curKind == Relation.RelationKind.SAME_TIME_AS)) {
                    assertEquals(testEvent2.getLatestPossibleDate(), center.plusDays(testUpperbound).withHourOfDay(14));
                }
            } else {
                
            }
        }
    }

    /**
     * Test of isStrictlyBefore method, of class Relation.
     */
    @Test
    public void testIsStrictlyBefore() {
        System.out.println("isStrictlyBefore");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        Relation instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.BEFORE);
        boolean expResult = true;
        boolean result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.BEFORE_SAME_DAY);
        expResult = true;
        result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.FIXED_TIME_BEFORE);
        expResult = true;
        result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.IMMEDIATELY_BEFORE);
        expResult = true;
        result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.LESS_THEN_BEFORE);
        expResult = true;
        result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.MORE_THEN_BEFORE);
        expResult = true;
        result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.SAME_DAY_OF_YEAR);
        expResult = false;
        result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.SAME_TIME_AS);
        expResult = false;
        result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFirstEvent method, of class Relation.
     */
    @Test
    public void testGetFirstEvent() {
        System.out.println("getFirstEvent");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        Relation instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.BEFORE);
        Event expResult = testEvent1;
        Event result = instance.getFirstEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSecondEvent method, of class Relation.
     */
    @Test
    public void testGetSecondEvent() {
        System.out.println("getSecondEvent");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        Relation instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.BEFORE);
        Event expResult = testEvent2;
        Event result = instance.getSecondEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getKind method, of class Relation.
     */
    @Test
    public void testGetKind() {
        System.out.println("getKind");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        Relation instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.BEFORE);
        Relation.RelationKind expResult = Relation.RelationKind.BEFORE;
        Relation.RelationKind result = instance.getKind();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTime method, of class Relation.
     */
    @Test
    public void testGetTime() {
        System.out.println("getTime");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        ReadablePeriod expResult = new org.joda.time.MutablePeriod();
        Relation instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.FIXED_TIME_BEFORE, expResult);
        ReadablePeriod result = instance.getTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrict method, of class Relation.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        ReadablePeriod mPeriod = new org.joda.time.MutablePeriod();
        Relation instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.BEFORE);
        boolean expResult = false;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.BEFORE_SAME_DAY);
        expResult = true;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.FIXED_TIME_BEFORE,mPeriod);
        expResult = true;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.IMMEDIATELY_BEFORE);
        expResult = false;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.LESS_THEN_BEFORE,mPeriod);
        expResult = true;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.MORE_THEN_BEFORE,mPeriod);
        expResult = false;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.SAME_DAY_OF_YEAR);
        expResult = true;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.SAME_TIME_AS);
        expResult = true;
        result = instance.isStrict();
        assertEquals(expResult, result);
    }

    /**
     * Test of consistentWithConstraint method, of class Relation.
     */
    @Test
    public void testConsistentWithConstraint_DateTime_DateTime() {
        System.out.println("consistentWithConstraint");
        /*DateTime inFirstDay = null;
        DateTime inSecondDay = null;
        Relation instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of getConstrainedEvents method, of class Relation.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        OnceDayEvent testEvent1 = new OnceDayEvent("star point");
        OnceDayEvent testEvent2 = new OnceDayEvent("ref point");
        Relation instance = new Relation(testEvent1,testEvent2,Relation.RelationKind.BEFORE);
        Event[] expResult = new Event[]{testEvent1, testEvent2};
        Event[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of ConstraintSatisfied method, of class Relation.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        Relation instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class Relation.
     */
    @Test
    public void testConsistentWithConstraint_PlacementArr() {
        System.out.println("consistentWithConstraint");
        /*Placement[] inValues = null;
        Relation instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of increaseWhat method, of class Relation.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        /*Placement[] inValues = null;
        Relation instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

}
