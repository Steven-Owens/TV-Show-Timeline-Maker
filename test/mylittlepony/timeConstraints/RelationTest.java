/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylittlepony.timeConstraints;

import TVShowTimelineMaker.timeConstraints.Relation;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import org.joda.time.DateTime;
import org.joda.time.ReadablePeriod;
import org.junit.After;
import org.junit.AfterClass;
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
public class RelationTest {

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
    public void testCullDays() {
        System.out.println("cullDays");
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

}
