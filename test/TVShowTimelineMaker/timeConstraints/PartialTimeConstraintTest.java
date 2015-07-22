/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.Placement;
import java.util.logging.Logger;
import org.joda.time.Partial;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class PartialTimeConstraintTest {
    private static final Logger LOG = Logger.getLogger(PartialTimeConstraintTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public PartialTimeConstraintTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isStrict method, of class PartialTimeConstraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        PartialTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstrainedEvents method, of class PartialTimeConstraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        PartialTimeConstraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraint method, of class PartialTimeConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        PartialTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.applyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIncompleteDate method, of class PartialTimeConstraint.
     */
    @Test
    public void testGetIncompleteDate() {
        System.out.println("getIncompleteDate");
        PartialTimeConstraint instance = null;
        Partial expResult = null;
        Partial result = instance.getIncompleteDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstraintSatisfied method, of class PartialTimeConstraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        PartialTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complexApplyConstraint method, of class PartialTimeConstraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        PartialTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class PartialTimeConstraint.
     */
    @Test
    public void testConsistentWithConstraint() {
        System.out.println("consistentWithConstraint");
        Placement[] inValues = null;
        PartialTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseWhat method, of class PartialTimeConstraint.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        /*Placement[] inValues = null;
        PartialTimeConstraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }
    
}
