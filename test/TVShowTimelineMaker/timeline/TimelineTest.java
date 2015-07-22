/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
public class TimelineTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
        public TimelineTest() {
        }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConstraintsOnEvent method, of class Timeline.
     */
    @Test
    public void testGetConstraintsOnEvent() {
        System.out.println("getConstraintsOnEvent");
        Event inEvent = null;
        Timeline instance = new Timeline();
        Collection<TimeConstraint> expResult = null;
        Collection<TimeConstraint> result = instance.getConstraintsOnEvent(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of afterEventAre method, of class Timeline.
     */
    @Test
    public void testAfterEventAre() {
        System.out.println("afterEventAre");
        OnceEvent inEvent = null;
        Timeline instance = new Timeline();
        Collection<OnceEvent> expResult = null;
        Collection<OnceEvent> result = instance.afterEventAre(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beforeEventAre method, of class Timeline.
     */
    @Test
    public void testBeforeEventAre() {
        System.out.println("beforeEventAre");
        OnceEvent inEvent = null;
        Timeline instance = new Timeline();
        Collection<OnceEvent> expResult = null;
        Collection<OnceEvent> result = instance.beforeEventAre(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of betweenEventsAre method, of class Timeline.
     */
    @Test
    public void testBetweenEventsAre() {
        System.out.println("betweenEventsAre");
        OnceEvent inEvent1 = null;
        OnceEvent inEvent2 = null;
        Timeline instance = new Timeline();
        Collection<OnceEvent> expResult = null;
        Collection<OnceEvent> result = instance.betweenEventsAre(inEvent1, inEvent2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of afterEventCouldBe method, of class Timeline.
     */
    @Test
    public void testAfterEventCouldBe() {
        System.out.println("afterEventCouldBe");
        OnceEvent inEvent = null;
        Timeline instance = new Timeline();
        Collection<OnceEvent> expResult = null;
        Collection<OnceEvent> result = instance.afterEventCouldBe(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beforeEventCouldBe method, of class Timeline.
     */
    @Test
    public void testBeforeEventCouldBe() {
        System.out.println("beforeEventCouldBe");
        OnceEvent inEvent = null;
        Timeline instance = new Timeline();
        Collection<OnceEvent> expResult = null;
        Collection<OnceEvent> result = instance.beforeEventCouldBe(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of betweenEventsCouldBe method, of class Timeline.
     */
    @Test
    public void testBetweenEventsCouldBe() {
        System.out.println("betweenEventsCouldBe");
        OnceEvent inEvent1 = null;
        OnceEvent inEvent2 = null;
        Timeline instance = new Timeline();
        Collection<OnceEvent> expResult = null;
        Collection<OnceEvent> result = instance.betweenEventsCouldBe(inEvent1, inEvent2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraints method, of class Timeline.
     */
    @Test
    public void testApplyConstraints() {
        System.out.println("applyConstraints");
        Timeline instance = new Timeline();
        boolean expResult = false;
        boolean result = instance.applyConstraints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstraintsSatisfied method, of class Timeline.
     */
    @Test
    public void testConstraintsSatisfied() {
        System.out.println("ConstraintsSatisfied");
        Timeline instance = new Timeline();
        boolean expResult = false;
        boolean result = instance.ConstraintsSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of trimEvents method, of class Timeline.
     */
    @Test
    public void testTrimEvents() {
        System.out.println("trimEvents");
        Timeline instance = new Timeline();
        instance.normalizeEvents();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetEvents method, of class Timeline.
     */
    @Test
    public void testResetEvents() {
        System.out.println("resetEvents");
        Timeline instance = new Timeline();
        instance.resetEvents();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeEventsOld method, of class Timeline.
     */
    @Test
    public void testPlaceEventsOld() {
        System.out.println("placeEventsOld");
        Timeline instance = new Timeline();
        int expResult = 0;
        int result = instance.placeEventsOld();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeEventsNew method, of class Timeline.
     */
    @Test
    public void testPlaceEventsNew() {
        System.out.println("placeEventsNew");
        Timeline instance = new Timeline();
        int expResult = 0;
        int result = instance.placeEvents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addEvent method, of class Timeline.
     */
    @Test
    public void testAddEvent() {
        System.out.println("addEvent");
        Event inEvent = null;
        Timeline instance = new Timeline();
        boolean expResult = false;
        boolean result = instance.addEvent(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTimeConstraint method, of class Timeline.
     */
    @Test
    public void testAddTimeConstraint() {
        System.out.println("addTimeConstraint");
        TimeConstraint inTimeConstraint = null;
        Timeline instance = new Timeline();
        boolean expResult = false;
        boolean result = instance.addTimeConstraint(inTimeConstraint);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEvent method, of class Timeline.
     */
    @Test
    public void testRemoveEvent() {
        System.out.println("removeEvent");
        Event inEvent = null;
        Timeline instance = new Timeline();
        boolean expResult = false;
        boolean result = instance.removeEvent(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeTimeConstraint method, of class Timeline.
     */
    @Test
    public void testRemoveTimeConstraint() {
        System.out.println("removeTimeConstraint");
        TimeConstraint inTimeConstraint = null;
        Timeline instance = new Timeline();
        boolean expResult = false;
        boolean result = instance.removeTimeConstraint(inTimeConstraint);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEvents method, of class Timeline.
     */
    @Test
    public void testGetEvents() {
        System.out.println("getEvents");
        Timeline instance = new Timeline();
        Collection<Event> expResult = null;
        Collection<Event> result = instance.getEvents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConstraints method, of class Timeline.
     */
    @Test
    public void testGetConstraints() {
        System.out.println("getConstraints");
        Timeline instance = new Timeline();
        Collection<TimeConstraint> expResult = null;
        Collection<TimeConstraint> result = instance.getConstraints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of firstEventOfType method, of class Timeline.
     */
    @Test
    public void testFirstEventOfType() {
        System.out.println("firstEventOfType");
        Timeline instance = new Timeline();
        Object expResult = null;
        Object result = instance.anyEventOfType(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeEvents method, of class Timeline.
     */
    @Test
    public void testPlaceEvents() {
        System.out.println("placeEvents");
        Timeline instance = new Timeline();
        int expResult = 0;
        int result = instance.placeEvents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of RandomSubSet method, of class Timeline.
     */
    @Test
    public void testRandomSubSet() {
        System.out.println("RandomSubSet");
        int size = 0;
        Timeline instance = new Timeline();
        SubSet expResult = null;
        SubSet result = instance.RandomSubSet(size);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findSubSets method, of class Timeline.
     */
    @Test
    public void testFindSubSets() {
        System.out.println("findSubSets");
        int Size = 0;
        Timeline instance = new Timeline();
        Collection<SubSet> expResult = null;
        Collection<SubSet> result = instance.findSubSets(Size);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEventFromSameTimeOfYearPairs method, of class Timeline.
     */
    @Test
    public void testCreateEventFromSameTimeOfYearPairs_0args() {
        System.out.println("createEventFromSameTimeOfYearPairs");
        Timeline instance = new Timeline();
        List<SubSet> expResult = null;
        List<SubSet> result = instance.createEventFromSameTimeOfYearPairs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEventFromSameTimeOfYearPairs method, of class Timeline.
     */
    @Test
    public void testCreateEventFromSameTimeOfYearPairs_Collection() {
        System.out.println("createEventFromSameTimeOfYearPairs");
        Collection<SubSet> inCollection = null;
        Timeline instance = new Timeline();
        instance.createEventFromSameTimeOfYearPairs(inCollection);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findConstraintsBetweenEventPair method, of class Timeline.
     */
    @Test
    public void testFindConstraintsBetweenEventPair() {
        System.out.println("findConstraintsBetweenEventPair");
        Event EventOne = null;
        Event EventTwo = null;
        Timeline instance = new Timeline();
        Set<TimeConstraint> expResult = null;
        Set<TimeConstraint> result = instance.findConstraintsBetweenEventPair(EventOne, EventTwo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEventFromEventPair method, of class Timeline.
     */
    @Test
    public void testCreateEventFromEventPair_4args() {
        System.out.println("createEventFromEventPair");
        Event EventOne = null;
        Event EventTwo = null;
        boolean strict = false;
        boolean ordered = false;
        Timeline instance = new Timeline();
        Set<Event> expResult = null;
        Set<Event> result = instance.createEventFromEventPair(EventOne, EventTwo, strict, ordered);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEventFromEventPair method, of class Timeline.
     */
    @Test
    public void testCreateEventFromEventPair_5args_1() {
        System.out.println("createEventFromEventPair");
        Event EventOne = null;
        Event EventTwo = null;
        TimeConstraint exculdeEvent = null;
        boolean strict = false;
        boolean ordered = false;
        Timeline instance = new Timeline();
        Set<Event> expResult = null;
        Set<Event> result = instance.createEventFromEventPair(EventOne, EventTwo, exculdeEvent, strict, ordered);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEventFromEventPair method, of class Timeline.
     */
    @Test
    public void testCreateEventFromEventPair_5args_2() {
        System.out.println("createEventFromEventPair");
        Event EventOne = null;
        Event EventTwo = null;
        Collection<TimeConstraint> exculdeEvents = null;
        boolean strict = false;
        boolean ordered = false;
        Timeline instance = new Timeline();
        Set<Event> expResult = null;
        Set<Event> result = instance.createEventFromEventPair(EventOne, EventTwo, exculdeEvents, strict, ordered);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEventsFromSeedEvents method, of class Timeline.
     */
    @Test
    public void testCreateEventsFromSeedEvents_0args() {
        System.out.println("createEventsFromSeedEvents");
        Timeline instance = new Timeline();
        List<SubSet> expResult = null;
        List<SubSet> result = instance.createEventsFromSeedEvents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createEventsFromSeedEvents method, of class Timeline.
     */
    @Test
    public void testCreateEventsFromSeedEvents_Collection() {
        System.out.println("createEventsFromSeedEvents");
        Collection<SubSet> inCollection = null;
        Timeline instance = new Timeline();
        instance.createEventsFromSeedEvents(inCollection);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createSubSetFromSeedEvent method, of class Timeline.
     */
    @Test
    public void testCreateSubSetFromSeedEvent() {
        System.out.println("createSubSetFromSeedEvent");
        Event inEvent = null;
        Timeline instance = new Timeline();
        SubSet expResult = null;
        SubSet result = instance.createSubSetFromSeedEvent(inEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstructNewSubSet method, of class Timeline.
     */
    @Test
    public void testConstructNewSubSet_EventArr() {
        System.out.println("ConstructNewSubSet");
        Event[] seen = null;
        Timeline instance = new Timeline();
        SubSet expResult = null;
        SubSet result = instance.ConstructNewSubSet(seen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ConstructNewSubSet method, of class Timeline.
     */
    @Test
    public void testConstructNewSubSet_Collection() {
        System.out.println("ConstructNewSubSet");
        Collection<Event> seen = null;
        Timeline instance = new Timeline();
        SubSet expResult = null;
        SubSet result = instance.ConstructNewSubSet(seen);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isConnected method, of class Timeline.
     */
    @Test
    public void testIsConnected() {
        System.out.println("isConnected");
        SubSet newSubSet = null;
        Timeline instance = new Timeline();
        boolean expResult = false;
        boolean result = instance.isConnected(newSubSet);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyConstraintsUptoLevel3Sure method, of class Timeline.
     */
    @Test
    public void testApplyConstraintsUptoLevel3Sure() {
        System.out.println("applyConstraintsUptoLevel3Sure");
        Logger inLogger = null;
        Timeline instance = new Timeline();
        boolean expResult = false;
        boolean result = instance.applyConstraintsUptoLevel3Sure(inLogger);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
