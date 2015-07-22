/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.Placement;
import java.util.List;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class FieldChangeConstraintTest {
    private static final Logger LOG = Logger.getLogger(FieldChangeConstraintTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public FieldChangeConstraintTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class FieldChangeConstraint.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        FieldChangeConstraint.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lowerFields method, of class FieldChangeConstraint.
     */
    @Test
    public void testLowerFields() {
        System.out.println("lowerFields");
        DateTimeFieldType inDateTimeFieldType = null;
        List<DateTimeFieldType> expResult = null;
        List<DateTimeFieldType> result = FieldChangeConstraint.lowerFields(inDateTimeFieldType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class FieldChangeConstraint.
     */
    @Test
    public void testConsistentWithConstraint_DateTime_DateTime() {
        System.out.println("consistentWithConstraint");
        DateTime inFirstDay = null;
        DateTime inSecondDay = null;
        FieldChangeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseWhat method, of class FieldChangeConstraint.
     */
    @Test
    public void testIncreaseWhat_DateTime_DateTime() {
        System.out.println("increaseWhat");
        DateTime inFirstDay = null;
        DateTime inSecondDay = null;
        FieldChangeConstraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inFirstDay, inSecondDay);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class FieldChangeConstraint.
     */
    @Test
    public void testConsistentWithConstraint_PlacementArr() {
        System.out.println("consistentWithConstraint");
        Placement[] inValues = null;
        FieldChangeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseWhat method, of class FieldChangeConstraint.
     */
    @Test
    public void testIncreaseWhat_PlacementArr() {
        System.out.println("increaseWhat");
        Placement[] inValues = null;
        FieldChangeConstraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstEvent method, of class FieldChangeConstraint.
     */
    @Test
    public void testGetFirstEvent() {
        System.out.println("getFirstEvent");
        FieldChangeConstraint instance = null;
        Event expResult = null;
        Event result = instance.getFirstEvent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSecondEvent method, of class FieldChangeConstraint.
     */
    @Test
    public void testGetSecondEvent() {
        System.out.println("getSecondEvent");
        FieldChangeConstraint instance = null;
        Event expResult = null;
        Event result = instance.getSecondEvent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStrictlyBefore method, of class FieldChangeConstraint.
     */
    @Test
    public void testIsStrictlyBefore() {
        System.out.println("isStrictlyBefore");
        FieldChangeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstrainedEvents method, of class FieldChangeConstraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        FieldChangeConstraint instance = null;
        Event[] expResult = null;
        Event[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraint method, of class FieldChangeConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        FieldChangeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.applyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complexApplyConstraint method, of class FieldChangeConstraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        FieldChangeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstraintSatisfied method, of class FieldChangeConstraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        FieldChangeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isStrict method, of class FieldChangeConstraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        FieldChangeConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDateTimeFieldType method, of class FieldChangeConstraint.
     */
    @Test
    public void testGetDateTimeFieldType() {
        System.out.println("getDateTimeFieldType");
        FieldChangeConstraint instance = null;
        DateTimeFieldType expResult = null;
        DateTimeFieldType result = instance.getDateTimeFieldType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAmount method, of class FieldChangeConstraint.
     */
    @Test
    public void testGetAmount() {
        System.out.println("getAmount");
        FieldChangeConstraint instance = null;
        int expResult = 0;
        int result = instance.getAmount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class FieldChangeConstraint.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        FieldChangeConstraint instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
