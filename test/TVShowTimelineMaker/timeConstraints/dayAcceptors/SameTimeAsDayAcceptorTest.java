/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.util.DayOfYear;
import java.util.Collection;
import java.util.Iterator;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class SameTimeAsDayAcceptorTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public SameTimeAsDayAcceptorTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of divFactor method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testDivFactor() {
        System.out.println("divFactor");
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        double expResult = 0.0;
        double result = instance.divFactor();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accept method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testAccept_DateTime() {
        System.out.println("accept");
        DateTime inDateTime = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accept method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testAccept_DayOfYear() {
        System.out.println("accept");
        DayOfYear inDateTime = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accept2 method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testAccept2_DateTime() {
        System.out.println("accept2");
        DateTime inDateTime = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.accept2(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accept2 method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testAccept2_DayOfYear() {
        System.out.println("accept2");
        DayOfYear inDateTime = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.accept2(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of build method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        instance.build();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        Object o = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.contains(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iterator method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testIterator() {
        System.out.println("iterator");
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        Iterator<DayEvent<? extends Object>> expResult = null;
        Iterator<DayEvent<? extends Object>> result = instance.iterator();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toArray method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testToArray_0args() {
        System.out.println("toArray");
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        Object[] expResult = null;
        Object[] result = instance.toArray();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toArray method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testToArray_GenericType() {
        System.out.println("toArray");
        Object[] a = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        Object[] expResult = null;
        Object[] result = instance.toArray(a);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        DayEvent e = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.add(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Object o = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.remove(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsAll method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testContainsAll() {
        System.out.println("containsAll");
        Collection c = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.containsAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAll method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testAddAll() {
        System.out.println("addAll");
        Collection<? extends DayEvent<? extends Object>> c = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.addAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retainAll method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testRetainAll() {
        System.out.println("retainAll");
        Collection c = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.retainAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAll method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testRemoveAll() {
        System.out.println("removeAll");
        Collection c = null;
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        boolean expResult = false;
        boolean result = instance.removeAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class SameTimeAsDayAcceptor.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        SameTimeAsDayAcceptor instance = new SameTimeAsDayAcceptor();
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
