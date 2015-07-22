/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.util.DayOfYear;
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
public class DayAcceptorTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public DayAcceptorTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of accept method, of class DayAcceptor.
     */
    @Test
    public void testAccept_DateTime() {
        System.out.println("accept");
        DateTime inDateTime = null;
        DayAcceptor instance = new DayAcceptorImpl();
        boolean expResult = false;
        boolean result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accept method, of class DayAcceptor.
     */
    @Test
    public void testAccept_DayOfYear() {
        System.out.println("accept");
        DayOfYear inDateTime = null;
        DayAcceptor instance = new DayAcceptorImpl();
        boolean expResult = false;
        boolean result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of divFactor method, of class DayAcceptor.
     */
    @Test
    public void testDivFactor() {
        System.out.println("divFactor");
        DayAcceptor instance = new DayAcceptorImpl();
        double expResult = 0.0;
        double result = instance.divFactor();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class DayAcceptorImpl implements DayAcceptor {

        public boolean accept(DateTime inDateTime) {
            return false;
        }

        public boolean accept(DayOfYear inDateTime) {
            return false;
        }

        public double divFactor() {
            return 0.0;
        }
    }
    
}
