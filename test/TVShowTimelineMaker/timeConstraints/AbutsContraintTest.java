/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.Placement;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class AbutsContraintTest {
    private static final Logger LOG = Logger.getLogger(AbutsContraintTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public AbutsContraintTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class AbutsContraint.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        AbutsContraint.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstEvent method, of class AbutsContraint.
     */
    @Test
    public void testGetFirstEvent() {
        System.out.println("getFirstEvent");
        AbutsContraint instance = null;
        Event expResult = null;
        Event result = instance.getFirstEvent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSecondEvent method, of class AbutsContraint.
     */
    @Test
    public void testGetSecondEvent() {
        System.out.println("getSecondEvent");
        AbutsContraint instance = null;
        Event expResult = null;
        Event result = instance.getSecondEvent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStrictlyBefore method, of class AbutsContraint.
     */
    @Test
    public void testIsStrictlyBefore() {
        System.out.println("isStrictlyBefore");
        AbutsContraint instance = null;
        boolean expResult = false;
        boolean result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstrainedEvents method, of class AbutsContraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        AbutsContraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstraintSatisfied method, of class AbutsContraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        AbutsContraint instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraint method, of class AbutsContraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        AbutsContraint instance = null;
        boolean expResult = false;
        boolean result = instance.applyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complexApplyConstraint method, of class AbutsContraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        AbutsContraint instance = null;
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class AbutsContraint.
     */
    @Test
    public void testConsistentWithConstraint() {
        System.out.println("consistentWithConstraint");
        Placement[] inValues = null;
        AbutsContraint instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseWhat method, of class AbutsContraint.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        Placement[] inValues = null;
        AbutsContraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStrict method, of class AbutsContraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        AbutsContraint instance = null;
        boolean expResult = false;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
