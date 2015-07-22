/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.util.DayOfYear;
import org.joda.time.DateTime;
import org.joda.time.Partial;
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
public class PartialDayAcceptorTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public PartialDayAcceptorTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class PartialDayAcceptor.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        PartialDayAcceptor instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class PartialDayAcceptor.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        PartialDayAcceptor instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accept method, of class PartialDayAcceptor.
     */
    @Test
    public void testAccept_DateTime() {
        System.out.println("accept");
        DateTime inDateTime = null;
        PartialDayAcceptor instance = null;
        boolean expResult = false;
        boolean result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accept method, of class PartialDayAcceptor.
     */
    @Test
    public void testAccept_DayOfYear() {
        System.out.println("accept");
        DayOfYear inDateTime = null;
        PartialDayAcceptor instance = null;
        boolean expResult = false;
        boolean result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of divFactor method, of class PartialDayAcceptor.
     */
    @Test
    public void testDivFactor() {
        System.out.println("divFactor");
        PartialDayAcceptor instance = null;
        double expResult = 0.0;
        double result = instance.divFactor();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIncompleteDate method, of class PartialDayAcceptor.
     */
    @Test
    public void testGetIncompleteDate() {
        System.out.println("getIncompleteDate");
        PartialDayAcceptor instance = null;
        Partial expResult = null;
        Partial result = instance.getIncompleteDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
