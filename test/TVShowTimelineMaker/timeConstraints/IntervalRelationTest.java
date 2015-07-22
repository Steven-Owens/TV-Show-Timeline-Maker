/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.Placement;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class IntervalRelationTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public IntervalRelationTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class IntervalRelation.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        IntervalRelation.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStrict method, of class IntervalRelation.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        IntervalRelation instance = null;
        boolean expResult = false;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CalculateInter method, of class IntervalRelation.
     */
    @Test
    public void testCalculateInter() {
        System.out.println("CalculateInter");
        IntervalRelation instance = null;
        instance.CalculateInter();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstrainedEvents method, of class IntervalRelation.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        IntervalRelation instance = null;
        OnceDayEvent[] expResult = null;
        OnceDayEvent[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraint method, of class IntervalRelation.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        IntervalRelation instance = null;
        boolean expResult = false;
        boolean result = instance.applyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstraintSatisfied method, of class IntervalRelation.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        IntervalRelation instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getKind method, of class IntervalRelation.
     */
    @Test
    public void testGetKind() {
        System.out.println("getKind");
        IntervalRelation instance = null;
        IntervalRelation.IntervalRelationKind expResult = null;
        IntervalRelation.IntervalRelationKind result = instance.getKind();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class IntervalRelation.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        IntervalRelation instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complexApplyConstraint method, of class IntervalRelation.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        IntervalRelation instance = null;
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class IntervalRelation.
     */
    @Test
    public void testConsistentWithConstraint_4args() {
        System.out.println("consistentWithConstraint");
        DateTime inPairOneFirstDay = null;
        DateTime inPairOneSecondDay = null;
        DateTime inPairTwoFirstDay = null;
        DateTime inPairTwoSecondDay = null;
        IntervalRelation instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inPairOneFirstDay, inPairOneSecondDay, inPairTwoFirstDay, inPairTwoSecondDay);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class IntervalRelation.
     */
    @Test
    public void testConsistentWithConstraint_PlacementArr() {
        System.out.println("consistentWithConstraint");
        Placement[] inValues = null;
        IntervalRelation instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseWhat method, of class IntervalRelation.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        Placement[] inValues = null;
        IntervalRelation instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
