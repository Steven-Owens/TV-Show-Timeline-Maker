/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.Placement;
import TVShowTimelineMaker.util.Season;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class SeasonTimeConstraintTest {
    private static final Logger LOG = Logger.getLogger(SeasonTimeConstraintTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public SeasonTimeConstraintTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isStrict method, of class SeasonTimeConstraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        SeasonTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSeason method, of class SeasonTimeConstraint.
     */
    @Test
    public void testGetSeason() {
        System.out.println("getSeason");
        SeasonTimeConstraint instance = null;
        Season expResult = null;
        Season result = instance.getSeason();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstrainedEvents method, of class SeasonTimeConstraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        SeasonTimeConstraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraint method, of class SeasonTimeConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        SeasonTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.applyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstraintSatisfied method, of class SeasonTimeConstraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        SeasonTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complexApplyConstraint method, of class SeasonTimeConstraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        SeasonTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class SeasonTimeConstraint.
     */
    @Test
    public void testConsistentWithConstraint() {
        System.out.println("consistentWithConstraint");
        Placement[] inValues = null;
        SeasonTimeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseWhat method, of class SeasonTimeConstraint.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        Placement[] inValues = null;
        SeasonTimeConstraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
