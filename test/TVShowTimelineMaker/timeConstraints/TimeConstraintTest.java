/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.Placement;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class TimeConstraintTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public TimeConstraintTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConstrainedEvents method, of class TimeConstraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        TimeConstraint instance = new TimeConstraintImpl();
        Event[] expResult = null;
        Event[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of applyConstraint method, of class TimeConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        TimeConstraint instance = new TimeConstraintImpl();
        boolean expResult = false;
        boolean result = instance.applyConstraint();
        assertEquals(expResult, result);
    }

    /**
     * Test of complexApplyConstraint method, of class TimeConstraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        TimeConstraint instance = new TimeConstraintImpl();
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
    }

    /**
     * Test of ConstraintSatisfied method, of class TimeConstraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        TimeConstraint instance = new TimeConstraintImpl();
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
    }

    /**
     * Test of consistentWithConstraint method, of class TimeConstraint.
     */
    @Test
    public void testConsistentWithConstraint() {
        System.out.println("consistentWithConstraint");
        Placement[] inValues = null;
        TimeConstraint instance = new TimeConstraintImpl();
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
    }

    /**
     * Test of increaseWhat method, of class TimeConstraint.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        Placement[] inValues = null;
        TimeConstraint instance = new TimeConstraintImpl();
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of isStrict method, of class TimeConstraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        TimeConstraint instance = new TimeConstraintImpl();
        boolean expResult = false;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
    }

    public class TimeConstraintImpl implements TimeConstraint {

        @Override
        public Event[] getConstrainedEvents() {
            return null;
        }

        @Override
        public boolean applyConstraint() {
            return false;
        }

        @Override
        public boolean complexApplyConstraint() {
            return false;
        }

        @Override
        public boolean ConstraintSatisfied() {
            return false;
        }

        @Override
        public boolean consistentWithConstraint(Placement[] inValues) {
            return false;
        }

        @Override
        public Event[] increaseWhat(Placement[] inValues) {
            return null;
        }

        @Override
        public boolean isStrict() {
            return false;
        }

        @Override
        public boolean inBeta() {
            return false;
        }

        @Override
        public boolean isSynthetic() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
