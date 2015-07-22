/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import java.util.Collection;
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
public class SubSetTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public SubSetTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class SubSet.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        SubSet instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class SubSet.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        SubSet instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of RandomSubSet method, of class SubSet.
     */
    @Test
    public void testRandomSubSet() {
        System.out.println("RandomSubSet");
        int size = 0;
        SubSet instance = null;
        SubSet expResult = null;
        SubSet result = instance.RandomSubSet(size);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstructNewSubSet method, of class SubSet.
     */
    @Test
    public void testConstructNewSubSet_EventArr() {
        System.out.println("ConstructNewSubSet");
        Event[] seen = null;
        SubSet instance = null;
        SubSet expResult = null;
        SubSet result = instance.ConstructNewSubSet(seen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstructNewSubSet method, of class SubSet.
     */
    @Test
    public void testConstructNewSubSet_Collection() {
        System.out.println("ConstructNewSubSet");
        Collection<Event> seen = null;
        SubSet instance = null;
        SubSet expResult = null;
        SubSet result = instance.ConstructNewSubSet(seen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isConnected method, of class SubSet.
     */
    @Test
    public void testIsConnected() {
        System.out.println("isConnected");
        SubSet newSubSet = null;
        SubSet instance = null;
        boolean expResult = false;
        boolean result = instance.isConnected(newSubSet);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLabel method, of class SubSet.
     */
    @Test
    public void testSetLabel() {
        System.out.println("setLabel");
        String label = "";
        SubSet instance = null;
        instance.setLabel(label);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
