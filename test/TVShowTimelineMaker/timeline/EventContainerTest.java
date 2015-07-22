/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import java.util.Collection;
import java.util.logging.Logger;
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
public class EventContainerTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public EventContainerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of applyConstraints method, of class EventContainer.
     */
    @Test
    public void testApplyConstraints() {
        System.out.println("applyConstraints");
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.applyConstraints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstraintsSatisfied method, of class EventContainer.
     */
    @Test
    public void testConstraintsSatisfied() {
        System.out.println("ConstraintsSatisfied");
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.ConstraintsSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraintsLevel1Sure method, of class EventContainer.
     */
    @Test
    public void testApplyConstraintsLevel1Sure_0args() {
        System.out.println("applyConstraintsLevel1Sure");
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.applyConstraintsLevel1Sure();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraintsLevel1Sure method, of class EventContainer.
     */
    @Test
    public void testApplyConstraintsLevel1Sure_Logger() {
        System.out.println("applyConstraintsLevel1Sure");
        Logger inLogger = null;
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.applyConstraintsLevel1Sure(inLogger);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraintsLevel2Sure method, of class EventContainer.
     */
    @Test
    public void testApplyConstraintsLevel2Sure_0args() {
        System.out.println("applyConstraintsLevel2Sure");
        EventContainer instance = new EventContainerImpl();
        int expResult = 0;
        int result = instance.applyConstraintsLevel2Sure();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraintsLevel2Sure method, of class EventContainer.
     */
    @Test
    public void testApplyConstraintsLevel2Sure_Logger_boolean() {
        System.out.println("applyConstraintsLevel2Sure");
        Logger inLogger = null;
        boolean onlyChanged = false;
        EventContainer instance = new EventContainerImpl();
        int expResult = 0;
        int result = instance.applyConstraintsLevel2Sure(inLogger, onlyChanged);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraintsLevel3Sure method, of class EventContainer.
     */
    @Test
    public void testApplyConstraintsLevel3Sure_0args() {
        System.out.println("applyConstraintsLevel3Sure");
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.applyConstraintsLevel3Sure();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraintsUptoLevel3Sure method, of class EventContainer.
     */
    @Test
    public void testApplyConstraintsUptoLevel3Sure() {
        System.out.println("applyConstraintsUptoLevel3Sure");
        Logger inLogger = null;
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.applyConstraintsUptoLevel3Sure(inLogger);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraintsLevel3Sure method, of class EventContainer.
     */
    @Test
    public void testApplyConstraintsLevel3Sure_Logger_Collection() {
        System.out.println("applyConstraintsLevel3Sure");
        Logger inLogger = null;
        Collection<Event> inEvents = null;
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.applyConstraintsLevel3Sure(inLogger, inEvents);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normalizeEvents method, of class EventContainer.
     */
    @Test
    public void testNormalizeEvents() {
        System.out.println("normalizeEvents");
        EventContainer instance = new EventContainerImpl();
        instance.normalizeEvents();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetEvents method, of class EventContainer.
     */
    @Test
    public void testResetEvents() {
        System.out.println("resetEvents");
        EventContainer instance = new EventContainerImpl();
        instance.resetEvents();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpForComplexEval method, of class EventContainer.
     */
    @Test
    public void testSetUpForComplexEval() {
        System.out.println("setUpForComplexEval");
        EventContainer instance = new EventContainerImpl();
        instance.setUpForComplexEval();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addEvent method, of class EventContainer.
     */
    @Test
    public void testAddEvent() {
        System.out.println("addEvent");
        Event inEvent = null;
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.addEvent(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTimeConstraint method, of class EventContainer.
     */
    @Test
    public void testAddTimeConstraint() {
        System.out.println("addTimeConstraint");
        TimeConstraint inTimeConstraint = null;
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.addTimeConstraint(inTimeConstraint);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEvent method, of class EventContainer.
     */
    @Test
    public void testRemoveEvent() {
        System.out.println("removeEvent");
        Event inEvent = null;
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.removeEvent(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeTimeConstraint method, of class EventContainer.
     */
    @Test
    public void testRemoveTimeConstraint() {
        System.out.println("removeTimeConstraint");
        TimeConstraint inTimeConstraint = null;
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.removeTimeConstraint(inTimeConstraint);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEvents method, of class EventContainer.
     */
    @Test
    public void testGetEvents() {
        System.out.println("getEvents");
        EventContainer instance = new EventContainerImpl();
        Collection<Event> expResult = null;
        Collection<Event> result = instance.getEvents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstraints method, of class EventContainer.
     */
    @Test
    public void testGetConstraints() {
        System.out.println("getConstraints");
        EventContainer instance = new EventContainerImpl();
        Collection<TimeConstraint> expResult = null;
        Collection<TimeConstraint> result = instance.getConstraints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of RandomSubSet method, of class EventContainer.
     */
    @Test
    public void testRandomSubSet() {
        System.out.println("RandomSubSet");
        int size = 0;
        EventContainer instance = new EventContainerImpl();
        SubSet expResult = null;
        SubSet result = instance.RandomSubSet(size);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstructNewSubSet method, of class EventContainer.
     */
    @Test
    public void testConstructNewSubSet_EventArr() {
        System.out.println("ConstructNewSubSet");
        Event[] seen = null;
        EventContainer instance = new EventContainerImpl();
        SubSet expResult = null;
        SubSet result = instance.ConstructNewSubSet(seen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstructNewSubSet method, of class EventContainer.
     */
    @Test
    public void testConstructNewSubSet_Collection() {
        System.out.println("ConstructNewSubSet");
        Collection<Event> seen = null;
        EventContainer instance = new EventContainerImpl();
        SubSet expResult = null;
        SubSet result = instance.ConstructNewSubSet(seen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isConnected method, of class EventContainer.
     */
    @Test
    public void testIsConnected() {
        System.out.println("isConnected");
        SubSet newSubSet = null;
        EventContainer instance = new EventContainerImpl();
        boolean expResult = false;
        boolean result = instance.isConnected(newSubSet);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLabel method, of class EventContainer.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel");
        EventContainer instance = new EventContainerImpl();
        String expResult = "";
        String result = instance.getLabel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class EventContainerImpl extends EventContainer {

        public SubSet RandomSubSet(int size) {
            return null;
        }

        public SubSet ConstructNewSubSet(Event[] seen) {
            return null;
        }

        public SubSet ConstructNewSubSet(Collection<Event> seen) {
            return null;
        }

        public boolean isConnected(SubSet newSubSet) {
            return false;
        }

        @Override
        public Collection<TimeConstraint> getConstraintsOnEvent(Event inEvent) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
