/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OnceEvent;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class IntervalConstraintTest {
    private static final Logger LOG = Logger.getLogger(IntervalConstraintTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
        IntervalConstraint.init();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public IntervalConstraintTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isStrict method, of class IntervalConstraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        OnceEvent inEpisode = new OnceDayEvent("test Event");
        Interval mInterval = new Interval(new DateTime(-1,1,1,1,1,1), new DateTime(1,12,31,23,59,59));
        IntervalConstraint instance = new IntervalConstraint(inEpisode, mInterval);
        boolean expResult = true;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getConstrainedEvents method, of class IntervalConstraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        OnceEvent inEpisode = new OnceDayEvent("test Event");
        Interval mInterval = new Interval(new DateTime(-1,1,1,1,1,1), new DateTime(1,12,31,23,59,59));
        IntervalConstraint instance = new IntervalConstraint(inEpisode, mInterval);
        Event[] expResult = new Event[]{inEpisode};
        Event[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of consistentWithConstraint method, of class IntervalConstraint.
     */
    @Test
    public void testConsistentWithConstraint() {
        System.out.println("consistentWithConstraint");
        /*Placement[] inValues = null;
        IntervalConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of increaseWhat method, of class IntervalConstraint.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        /*Placement[] inValues = null;
        IntervalConstraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of complexApplyConstraint method, of class IntervalConstraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        IntervalConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraint method, of class IntervalConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        IntervalConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.applyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getmInterval method, of class IntervalConstraint.
     */
    @Test
    public void testGetmInterval() {
        System.out.println("getmInterval");
        OnceEvent inEpisode = new OnceDayEvent("test Event");
        Interval mInterval = new Interval(new DateTime(-1,1,1,1,1,1), new DateTime(1,12,31,23,59,59));
        IntervalConstraint instance = new IntervalConstraint(inEpisode, mInterval);
        Interval expResult = mInterval;
        Interval result = instance.getmInterval();
        assertEquals(expResult, result);
    }

    /**
     * Test of ConstraintSatisfied method, of class IntervalConstraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        IntervalConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }  
}
