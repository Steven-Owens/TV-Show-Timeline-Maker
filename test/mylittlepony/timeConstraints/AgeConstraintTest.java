/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mylittlepony.timeConstraints;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeConstraints.AgeConstraint;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import org.joda.time.DateTime;
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
public class AgeConstraintTest {
    
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
     * Test of cullDays method, of class AgeConstraint.
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testCullDays() {
        System.out.println("cullDays");
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

    
}
