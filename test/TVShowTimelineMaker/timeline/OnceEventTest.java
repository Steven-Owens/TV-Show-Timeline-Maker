/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import java.util.Collection;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class OnceEventTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public OnceEventTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEarliestPossibleStartTime method, of class OnceEvent.
     */
    @Test
    public void testGetEarliestPossibleStartTime() {
        System.out.println("getEarliestPossibleStartTime");
        OnceEvent instance = new OnceEventImpl();
        DateTime expResult = null;
        DateTime result = instance.getEarliestPossibleStartTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLatestPossibleEndTime method, of class OnceEvent.
     */
    @Test
    public void testGetLatestPossibleEndTime() {
        System.out.println("getLatestPossibleEndTime");
        OnceEvent instance = new OnceEventImpl();
        DateTime expResult = null;
        DateTime result = instance.getLatestPossibleEndTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAfter method, of class OnceEvent.
     */
    @Test
    public void testIsAfter() {
        System.out.println("isAfter");
        DateTime inTime = null;
        OnceEvent instance = new OnceEventImpl();
        boolean expResult = false;
        boolean result = instance.isAfter(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isBefore method, of class OnceEvent.
     */
    @Test
    public void testIsBefore() {
        System.out.println("isBefore");
        DateTime inTime = null;
        OnceEvent instance = new OnceEventImpl();
        boolean expResult = false;
        boolean result = instance.isBefore(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAfter method, of class OnceEvent.
     */
    @Test
    public void testSetAfter() {
        System.out.println("setAfter");
        DateTime inTime = null;
        OnceEvent instance = new OnceEventImpl();
        boolean expResult = false;
        boolean result = instance.setAfter(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBefore method, of class OnceEvent.
     */
    @Test
    public void testSetBefore() {
        System.out.println("setBefore");
        DateTime inTime = null;
        OnceEvent instance = new OnceEventImpl();
        boolean expResult = false;
        boolean result = instance.setBefore(inTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class OnceEventImpl implements OnceEvent {

        public DateTime getEarliestPossibleStartTime() {
            return null;
        }

        public DateTime getLatestPossibleEndTime() {
            return null;
        }

        public boolean isAfter(DateTime inTime) {
            return false;
        }

        public boolean isBefore(DateTime inTime) {
            return false;
        }

        public boolean setAfter(DateTime inTime) {
            return false;
        }

        public boolean setBefore(DateTime inTime) {
            return false;
        }

        @Override
        public void reset() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isValid() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getLeastSignificantIDPart() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setName(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public long getLastmodifyed() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void normalize() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String toLongString() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double sizeAdj() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String toTimeFrameString() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isMarkedForComplexEval() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setUpForComplexEval() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<? extends Placement> getPlacements() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setTo(Collection<? extends  Placement> inPlacements) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int compareTo(Event o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DateTime getLatestPossibleStartTime() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public DateTime getEarliestPossibleEndTime() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setTo(Placement inPlacement) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<? extends Placement> getSuggestedPlacements() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
