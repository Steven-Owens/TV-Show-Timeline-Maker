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
public class EventImpTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public EventImpTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEventByID method, of class EventImp.
     */
    @Test
    public void testGetEventByID() {
        System.out.println("getEventByID");
        int ID = 0;
        Event expResult = null;
        Event result = EventImp.getEventByID(ID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class EventImp.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        EventImp instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class EventImp.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        EventImp instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class EventImp.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        EventImp instance = null;
        int expResult = 0;
        int result = instance.getLeastSignificantIDPart();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class EventImp.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        EventImp instance = null;
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class EventImp.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        EventImp instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class EventImp.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        EventImp instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class EventImp.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        EventImp instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastmodifyed method, of class EventImp.
     */
    @Test
    public void testGetLastmodifyed() {
        System.out.println("getLastmodifyed");
        EventImp instance = null;
        long expResult = 0L;
        long result = instance.getLastmodifyed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class EventImp.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Event o = null;
        EventImp instance = null;
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalize method, of class EventImp.
     */
    @Test
    public void testNormalize() {
        System.out.println("normalize");
        EventImp instance = null;
        instance.normalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toLongString method, of class EventImp.
     */
    @Test
    public void testToLongString() {
        System.out.println("toLongString");
        EventImp instance = null;
        String expResult = "";
        String result = instance.toLongString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMarkedForComplexEval method, of class EventImp.
     */
    @Test
    public void testIsMarkedForComplexEval() {
        System.out.println("isMarkedForComplexEval");
        EventImp instance = null;
        boolean expResult = false;
        boolean result = instance.isMarkedForComplexEval();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpForComplexEval method, of class EventImp.
     */
    @Test
    public void testSetUpForComplexEval() {
        System.out.println("setUpForComplexEval");
        EventImp instance = null;
        instance.setUpForComplexEval();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class EventImp.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        EventImp instance = null;
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTo method, of class EventImp.
     */
    @Test
    public void testSetTo() {
        System.out.println("setTo");
        Collection<Placement> inPlacements = null;
        EventImp instance = null;
        instance.setTo(inPlacements);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class EventImpImpl extends EventImp {

        public EventImpImpl() {
            super("");
        }

        public boolean isValid() {
            return false;
        }

        public void setTo(Collection<? extends  Placement> inPlacements) {
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
        public List<? extends Placement> getPlacements() {
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
