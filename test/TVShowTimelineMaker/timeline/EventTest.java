/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import java.util.Collection;
import java.util.List;
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
public class EventTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public EventTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of reset method, of class Event.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        Event instance = new EventImpl();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class Event.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        Event instance = new EventImpl();
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Event.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Event instance = new EventImpl();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class Event.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Event instance = new EventImpl();
        int expResult = 0;
        int result = instance.getLeastSignificantIDPart();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Event.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Event instance = new EventImpl();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastmodifyed method, of class Event.
     */
    @Test
    public void testGetLastmodifyed() {
        System.out.println("getLastmodifyed");
        Event instance = new EventImpl();
        long expResult = 0L;
        long result = instance.getLastmodifyed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalize method, of class Event.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        Event instance = new EventImpl();
        instance.normalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLongString method, of class Event.
     */
    @Test
    public void testToLongString() {
        System.out.println("toLongString");
        Event instance = new EventImpl();
        String expResult = "";
        String result = instance.toLongString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class Event.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        Event instance = new EventImpl();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sizeAdj method, of class Event.
     */
    @Test
    public void testSizeAdj() {
        System.out.println("sizeAdj");
        Event instance = new EventImpl();
        double expResult = 0.0;
        double result = instance.sizeAdj();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toTimeFrameString method, of class Event.
     */
    @Test
    public void testToTimeFrameString() {
        System.out.println("toTimeFrameString");
        Event instance = new EventImpl();
        String expResult = "";
        String result = instance.toTimeFrameString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMarkedForComplexEval method, of class Event.
     */
    @Test
    public void testIsMarkedForComplexEval() {
        System.out.println("isMarkedForComplexEval");
        Event instance = new EventImpl();
        boolean expResult = false;
        boolean result = instance.isMarkedForComplexEval();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpForComplexEval method, of class Event.
     */
    @Test
    public void testSetUpForComplexEval() {
        System.out.println("setUpForComplexEval");
        Event instance = new EventImpl();
        instance.setUpForComplexEval();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlacements method, of class Event.
     */
    @Test
    public void testGetPlacements() {
        System.out.println("getPlacements");
        Event instance = new EventImpl();
        List<? extends Placement> expResult = null;
        List<? extends Placement> result = instance.getPlacements();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTo method, of class Event.
     */
    @Test
    public void testSetTo() {
        System.out.println("setTo");
        Collection<Placement> inPlacements = null;
        Event instance = new EventImpl();
        instance.setTo(inPlacements);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class EventImpl implements Event {

        public void reset() {
        }

        public boolean isValid() {
            return false;
        }

        public String getName() {
            return "";
        }

        public int getLeastSignificantIDPart() {
            return 0;
        }

        public void setName(String name) {
        }

        public long getLastmodifyed() {
            return 0L;
        }

        public void normalize() {
        }

        public String toLongString() {
            return "";
        }

        public int size() {
            return 0;
        }

        public double sizeAdj() {
            return 0.0;
        }

        public String toTimeFrameString() {
            return "";
        }

        public boolean isMarkedForComplexEval() {
            return false;
        }

        public void setUpForComplexEval() {
        }

        public List<? extends Placement> getPlacements() {
            return null;
        }

        public void setTo(Collection<? extends  Placement> inPlacements) {
        }

        @Override
        public int compareTo(Event o) {
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
