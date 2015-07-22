/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.timeline.EventImp;
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
public class MyLittePonyMapsTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public MyLittePonyMapsTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of putConstraint method, of class MyLittePonyMaps.
     */
    @Test
    public void testPutConstraint() {
        System.out.println("putConstraint");
        String inFriendlyString = "";
        Class<? extends TimeConstraint> inConstraintClass = null;
        MyLittePonyMaps.putConstraint(inFriendlyString, inConstraintClass);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstraintClassForFriendlyString method, of class MyLittePonyMaps.
     */
    @Test
    public void testGetConstraintClassForFriendlyString() {
        System.out.println("getConstraintClassForFriendlyString");
        String inFriendlyString = "";
        Class<? extends TimeConstraint> expResult = null;
        Class<? extends TimeConstraint> result = MyLittePonyMaps.getConstraintClassForFriendlyString(inFriendlyString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFriendlyStringForConstraintClass method, of class MyLittePonyMaps.
     */
    @Test
    public void testGetFriendlyStringForConstraintClass() {
        System.out.println("getFriendlyStringForConstraintClass");
        Class<? extends TimeConstraint> inConstraintClass = null;
        String expResult = "";
        String result = MyLittePonyMaps.getFriendlyStringForConstraintClass(inConstraintClass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFriendlyStringsForConstraintClasses method, of class MyLittePonyMaps.
     */
    @Test
    public void testGetFriendlyStringsForConstraintClasses() {
        System.out.println("getFriendlyStringsForConstraintClasses");
        Collection<String> expResult = null;
        Collection<String> result = MyLittePonyMaps.getFriendlyStringsForConstraintClasses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstraintClasses method, of class MyLittePonyMaps.
     */
    @Test
    public void testGetConstraintClasses() {
        System.out.println("getConstraintClasses");
        Collection<Class<? extends TimeConstraint>> expResult = null;
        Collection<Class<? extends TimeConstraint>> result = MyLittePonyMaps.getConstraintClasses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putEvent method, of class MyLittePonyMaps.
     */
    @Test
    public void testPutEvent() {
        System.out.println("putEvent");
        String inFriendlyString = "";
        Class<? extends EventImp> inEventClass = null;
        MyLittePonyMaps.putEvent(inFriendlyString, inEventClass);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEventClassForFriendlyString method, of class MyLittePonyMaps.
     */
    @Test
    public void testGetEventClassForFriendlyString() {
        System.out.println("getEventClassForFriendlyString");
        String inFriendlyString = "";
        Class<? extends EventImp> expResult = null;
        Class<? extends EventImp> result = MyLittePonyMaps.getEventClassForFriendlyString(inFriendlyString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFriendlyStringForEventClass method, of class MyLittePonyMaps.
     */
    @Test
    public void testGetFriendlyStringForEventClass() {
        System.out.println("getFriendlyStringForEventClass");
        Class<? extends EventImp> inEventClass = null;
        String expResult = "";
        String result = MyLittePonyMaps.getFriendlyStringForEventClass(inEventClass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFriendlyStringsForEventClasses method, of class MyLittePonyMaps.
     */
    @Test
    public void testGetFriendlyStringsForEventClasses() {
        System.out.println("getFriendlyStringsForEventClasses");
        Collection<String> expResult = null;
        Collection<String> result = MyLittePonyMaps.getFriendlyStringsForEventClasses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEventClasses method, of class MyLittePonyMaps.
     */
    @Test
    public void testGetEventClasses() {
        System.out.println("getEventClasses");
        Collection<Class<? extends EventImp>> expResult = null;
        Collection<Class<? extends EventImp>> result = MyLittePonyMaps.getEventClasses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
