/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.interfaces.TwoOnceDayEventTimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.Placement;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TwoOnceDayEventTimeConstraintTest extends TwoEventTimeConstraintTest {
    private static final Logger LOG = Logger.getLogger(TwoOnceDayEventTimeConstraintTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public TwoOnceDayEventTimeConstraintTest() {
    }
    
    @Before
    @Override
    public void setUp() {
    }
    
    @After
    @Override
    public void tearDown() {
    }

    /**
     * Test of consistentWithConstraint method, of class TwoOnceDayEventTimeConstraint.
     */
    @Test
    public void testConsistentWithConstraint() {
        System.out.println("consistentWithConstraint");
        DateTime inFirstDay = null;
        DateTime inSecondDay = null;
        TwoOnceDayEventTimeConstraint instance = new TwoOnceDayEventTimeConstraintImpl();
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
    }

    public class TwoOnceDayEventTimeConstraintImpl implements TwoOnceDayEventTimeConstraint {

        @Override
        public boolean consistentWithConstraint(DateTime inFirstDay, DateTime inSecondDay) {
            return false;
        }

        @Override
        public Event getFirstEvent() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Event getSecondEvent() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isStrictlyBefore() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
