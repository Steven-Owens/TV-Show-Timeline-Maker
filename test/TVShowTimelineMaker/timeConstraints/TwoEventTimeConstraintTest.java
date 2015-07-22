/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.interfaces.TwoEventTimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.Placement;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TwoEventTimeConstraintTest {
    private static final Logger LOG = Logger.getLogger(TwoEventTimeConstraintTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public TwoEventTimeConstraintTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getFirstEvent method, of class TwoEventTimeConstraint.
     */
    @Test
    public void testGetFirstEvent() {
        System.out.println("getFirstEvent");
        TwoEventTimeConstraint instance = new TwoEventTimeConstraintImpl();
        Event expResult = null;
        Event result = instance.getFirstEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSecondEvent method, of class TwoEventTimeConstraint.
     */
    @Test
    public void testGetSecondEvent() {
        System.out.println("getSecondEvent");
        TwoEventTimeConstraint instance = new TwoEventTimeConstraintImpl();
        Event expResult = null;
        Event result = instance.getSecondEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrictlyBefore method, of class TwoEventTimeConstraint.
     */
    @Test
    public void testIsStrictlyBefore() {
        System.out.println("isStrictlyBefore");
        TwoEventTimeConstraint instance = new TwoEventTimeConstraintImpl();
        boolean expResult = false;
        boolean result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
    }

    public class TwoEventTimeConstraintImpl implements TwoEventTimeConstraint {

        @Override
        public Event getFirstEvent() {
            return null;
        }

        @Override
        public Event getSecondEvent() {
            return null;
        }

        @Override
        public boolean isStrictlyBefore() {
            return false;
        }

        @Override
        public Event[] getConstrainedEvents() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean applyConstraint() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean complexApplyConstraint() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean ConstraintSatisfied() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean consistentWithConstraint(Placement[] inValues) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Event[] increaseWhat(Placement[] inValues) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isStrict() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean inBeta() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isSynthetic() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
