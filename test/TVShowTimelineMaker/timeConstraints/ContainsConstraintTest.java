/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.testUtil.DateTimeMaker;
import TVShowTimelineMaker.testUtil.EventPair;
import TVShowTimelineMaker.testUtil.RandomEvents;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent;
import TVShowTimelineMaker.timeline.Placement;
import java.util.Map;
import java.util.logging.Logger;
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
public class ContainsConstraintTest {

    private static final java.util.Random rnd = new java.util.Random();
    private static final Map<String, EventPair<OncePeriodEvent,OncePeriodEvent>> mOncePeriodEventTestCases = new java.util.HashMap<>();
    private static final Logger LOG = Logger.getLogger(ContainsConstraintTest.class.getName());
            

    @BeforeClass
    public static void setUpClass() {
        ContainsConstraint.init();
        
        //create cases
        OncePeriodEvent ContainsEvent = new OncePeriodEvent();
        OncePeriodEvent inContainedOncePeriodEvent = new OncePeriodEvent();
        PeriodEvent ContainedEvent = inContainedOncePeriodEvent;
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        mOncePeriodEventTestCases.put("inside1", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        mOncePeriodEventTestCases.put("overlap1", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        mOncePeriodEventTestCases.put("overlap2", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        mOncePeriodEventTestCases.put("overlap3", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,4,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        mOncePeriodEventTestCases.put("insideStart1", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        mOncePeriodEventTestCases.put("insideBoth1", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        mOncePeriodEventTestCases.put("insideEnd1", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        mOncePeriodEventTestCases.put("insideStart2", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        mOncePeriodEventTestCases.put("insideEnd2", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        mOncePeriodEventTestCases.put("outside1", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,7,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,7,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,8,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,8,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        mOncePeriodEventTestCases.put("outside2", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,4,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        mOncePeriodEventTestCases.put("noMiddleInside1", new EventPair<>(ContainsEvent, inContainedOncePeriodEvent));
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public ContainsConstraintTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getFirstEvent method, of class ContainsConstraint.
     */
    @Test
    public void testGetFirstEvent() {
        System.out.println("getFirstEvent");
        PeriodEvent expResult = RandomEvents.randomOncePeriodEvent();
        ContainsConstraint instance = new ContainsConstraint(expResult, RandomEvents.randomOncePeriodEvent(), ContainsConstraint.ContainsType.CONTAINS);
        Event result = instance.getFirstEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSecondEvent method, of class ContainsConstraint.
     */
    @Test
    public void testGetSecondEvent() {
        System.out.println("getSecondEvent");
        PeriodEvent expResult = RandomEvents.randomOncePeriodEvent();
        ContainsConstraint instance = new ContainsConstraint(RandomEvents.randomOncePeriodEvent(), expResult, ContainsConstraint.ContainsType.CONTAINS);
        Event result = instance.getSecondEvent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getConstrainedEvents method, of class ContainsConstraint.
     */
    @Test
    public void testGetConstrainedEvents() {
        System.out.println("getConstrainedEvents");
        Event[] expResult = new Event[2];
        expResult[0] = RandomEvents.randomOncePeriodEvent();
        expResult[1] = RandomEvents.randomOncePeriodEvent();
        ContainsConstraint instance = new ContainsConstraint((PeriodEvent) expResult[0], expResult[1], ContainsConstraint.ContainsType.CONTAINS);
        Event[] result = instance.getConstrainedEvents();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of ConstraintSatisfied method, of class ContainsConstraint.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        OncePeriodEvent ContainsEvent = new OncePeriodEvent();
        OncePeriodEvent inContainedOncePeriodEvent = new OncePeriodEvent();
        PeriodEvent ContainedEvent = inContainedOncePeriodEvent;
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsConstraint.ContainsType tContainsType = ContainsConstraint.ContainsType.CONTAINS;
        boolean expResult = true;
        ContainsConstraint instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        boolean result = instance.ConstraintSatisfied();
        if (!result){
            instance.ConstraintSatisfied();
        }
        assertEquals(expResult, result);
        
        tContainsType = ContainsConstraint.ContainsType.NONOVERLAPING;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        tContainsType = ContainsConstraint.ContainsType.CONTAINS;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        tContainsType = ContainsConstraint.ContainsType.NONOVERLAPING;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        tContainsType = ContainsConstraint.ContainsType.CONTAINS;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        tContainsType = ContainsConstraint.ContainsType.NONOVERLAPING;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        tContainsType = ContainsConstraint.ContainsType.CONTAINS;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        tContainsType = ContainsConstraint.ContainsType.NONOVERLAPING;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        tContainsType = ContainsConstraint.ContainsType.CONTAINS;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        tContainsType = ContainsConstraint.ContainsType.NONOVERLAPING;
        expResult = true;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,7,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,7,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,8,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,8,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        tContainsType = ContainsConstraint.ContainsType.CONTAINS;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        tContainsType = ContainsConstraint.ContainsType.NONOVERLAPING;
        expResult = true;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        ContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        ContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,4,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        ContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        ContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        tContainsType = ContainsConstraint.ContainsType.CONTAINS;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        
        tContainsType = ContainsConstraint.ContainsType.NONOVERLAPING;
        expResult = false;
        instance = new ContainsConstraint(ContainsEvent, ContainedEvent, tContainsType);
        result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class ContainsConstraint.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        OncePeriodEvent inContainsEvent = new OncePeriodEvent();
        OnceDayEvent inContainedOnceDayEvent = new OnceDayEvent();
        Event inContainedEvent = inContainedOnceDayEvent;
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOnceDayEvent.setEarliestPossibleDate(new DateTime(0,1,2,0,0));
        inContainedOnceDayEvent.setLatestPossibleDate(new DateTime(0,1,2,0,0));
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        boolean expResult = true;
        boolean result = ContainsConstraint.contains(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOnceDayEvent.setEarliestPossibleDate(new DateTime(0,1,1,0,0));
        inContainedOnceDayEvent.setLatestPossibleDate(new DateTime(0,1,3,0,0));
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = false;
        result = ContainsConstraint.contains(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOnceDayEvent.setEarliestPossibleDate(new DateTime(0,1,3,0,0));
        inContainedOnceDayEvent.setLatestPossibleDate(new DateTime(0,1,5,0,0));
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = false;
        result = ContainsConstraint.contains(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOnceDayEvent.setEarliestPossibleDate(new DateTime(0,1,2,0,0));
        inContainedOnceDayEvent.setLatestPossibleDate(new DateTime(0,1,4,0,0));
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        expResult = false;
        result = ContainsConstraint.contains(inContainsEvent, inContainedEvent);
        assertEquals(inContainsEvent.toTimeFrameString() + " contains " + inContainedEvent.toTimeFrameString(),expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOnceDayEvent.setEarliestPossibleDate(new DateTime(0,1,3,0,0));
        inContainedOnceDayEvent.setLatestPossibleDate(new DateTime(0,1,6,0,0));
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,7,0,0));
        expResult = false;
        result = ContainsConstraint.contains(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
    }

    /**
     * Test of overlaps method, of class ContainsConstraint.
     */
    @Test
    public void testOverlaps() {
        System.out.println("overlaps");
        OncePeriodEvent inContainsEvent = new OncePeriodEvent();
        OncePeriodEvent inContainedOncePeriodEvent = new OncePeriodEvent();
        PeriodEvent inContainedEvent = inContainedOncePeriodEvent;
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        boolean expResult = true;
        boolean result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = true;
        result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = true;
        result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = true;
        result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,4,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        expResult = true;
        result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = true;
        result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = true;
        result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
        assertEquals(inContainsEvent.toTimeFrameString() + " doesn't overlap " + inContainedEvent.toTimeFrameString(),expResult, result);
        
        for (int i = 0; i < 100; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            inContainedEvent = RandomEvents.randomOncePeriodEvent();
            expResult = ContainsConstraint.overlaps(inContainedEvent, inContainsEvent);
            result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
            assertEquals(expResult, result);
        }
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            try {
                inContainedEvent = RandomEvents.randomContainsEvent(inContainsEvent, OncePeriodEvent.class);
                expResult = true;
                result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
                if (!result) {
                    System.out.println(inContainsEvent.toTimeFrameString() + " ; " + inContainedEvent.toTimeFrameString());
                    ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
                }
                assertEquals(expResult, result);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            try {
                inContainedEvent = RandomEvents.randomOutsideEvent(inContainsEvent, OncePeriodEvent.class);
                expResult = false;
                result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
                assertEquals(expResult, result);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            inContainedEvent = RandomEvents.randomOverlapsEvent(inContainsEvent);
            expResult = true;
            result = ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
            if (!result) {
                System.out.println(inContainsEvent.toTimeFrameString() + " ; " + inContainedEvent.toTimeFrameString());
                ContainsConstraint.overlaps(inContainsEvent, inContainedEvent);
            }
            assertEquals(expResult, result);
        }*/
    }

    /**
     * Test of couldOverlap method, of class ContainsConstraint.
     */
    @Test
    public void testCouldOverlap() {
        System.out.println("couldOverlap");
        OncePeriodEvent inContainsEvent = new OncePeriodEvent();
        OncePeriodEvent inContainedOncePeriodEvent = new OncePeriodEvent();
        PeriodEvent inContainedEvent = inContainedOncePeriodEvent;
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        boolean expResult = true;
        boolean result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = true;
        result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = true;
        result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = true;
        result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,4,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        expResult = true;
        result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = true;
        result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = true;
        result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = true;
        result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = true;
        result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        for (int i = 0; i < 100; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            inContainedEvent = RandomEvents.randomOncePeriodEvent();
            expResult = ContainsConstraint.couldOverlap(inContainedEvent, inContainsEvent);
            result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
            assertEquals(expResult, result);
        }
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            try {
                inContainedEvent = RandomEvents.randomContainsEvent(inContainsEvent, OncePeriodEvent.class);
                expResult = true;
                result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
                assertEquals(expResult, result);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            try {
                inContainedEvent = RandomEvents.randomOutsideEvent(inContainsEvent, OncePeriodEvent.class);
                expResult = false;
                result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
                if (result) {
                    System.out.println(inContainsEvent.toTimeFrameString() + " ; " + inContainedEvent.toTimeFrameString());
                    ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
                }
                assertEquals(expResult, result);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            inContainedEvent = RandomEvents.randomOverlapsEvent(inContainsEvent);
            expResult = true;
            result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
            if (!result) {
                System.out.println(inContainsEvent.toTimeFrameString() + " ; " + inContainedEvent.toTimeFrameString());
                ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
            }
            assertEquals(expResult, result);
        }*/
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            inContainedEvent = RandomEvents.randomCouldOverlapsEvent(inContainsEvent, OncePeriodEvent.class);
            expResult = true;
            result = ContainsConstraint.couldOverlap(inContainsEvent, inContainedEvent);
            assertEquals(expResult, result);
        }*/
    }

    /**
     * Test of outside method, of class ContainsConstraint.
     */
    @Test
    public void testOutside() {
        System.out.println("outside");
        OncePeriodEvent inContainsEvent = new OncePeriodEvent();
        OncePeriodEvent inContainedOncePeriodEvent = new OncePeriodEvent();
        PeriodEvent inContainedEvent = inContainedOncePeriodEvent;
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        boolean expResult = false;
        boolean result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = false;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = false;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        expResult = false;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,4,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,3,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        expResult = false;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = false;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = false;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,2,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = false;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,1,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,1,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,5,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,2,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = false;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,5,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,3,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,4,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = true;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        if (!result){
            ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        }
        assertEquals(expResult, result);
        
        inContainsEvent.setEarliestPossibleDateForStart(new DateTime(0,1,5,0,0));
        inContainsEvent.setLatestPossibleDateForStart(new DateTime(0,1,5,0,0));
        
        inContainedOncePeriodEvent.setEarliestPossibleDateForStart(new DateTime(0,1,7,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForStart(new DateTime(0,1,7,0,0));
        inContainedOncePeriodEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,8,0,0));
        inContainedOncePeriodEvent.setLatestPossibleDateForEnd(new DateTime(0,1,8,0,0));
        
        inContainsEvent.setEarliestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        inContainsEvent.setLatestPossibleDateForEnd(new DateTime(0,1,6,0,0));
        expResult = true;
        result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
        assertEquals(expResult, result);
        
        for (int i = 0; i < 100; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            inContainedEvent = RandomEvents.randomOncePeriodEvent();
            expResult = ContainsConstraint.outside(inContainedEvent, inContainsEvent);
            result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
            if (result != expResult) {
                System.out.println(inContainsEvent.toTimeFrameString() + " ; " + inContainedEvent.toTimeFrameString());
                ContainsConstraint.outside(inContainedEvent, inContainsEvent);
                ContainsConstraint.outside(inContainsEvent, inContainedEvent);
            }
            assertEquals(expResult, result);
        }
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            Event inContainedEvent = RandomEvents.randomContainsEvent(inContainsEvent);
            boolean expResult = false;
            boolean result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
            if (result) {
                System.out.println(inContainsEvent.toTimeFrameString() + " ; " + inContainedEvent.toTimeFrameString());
                ContainsConstraint.outside(inContainsEvent, inContainedEvent);
            }
            assertEquals(expResult, result);
        }*/
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            Event inContainedEvent = RandomEvents.randomOutsideEvent(inContainsEvent);
            boolean expResult = true;
            boolean result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
            if (result != expResult){
                System.out.println(inContainsEvent.toTimeFrameString() + " ; " + inContainedEvent.toTimeFrameString());
                ContainsConstraint.outside(inContainsEvent, inContainedEvent);
            }
            assertEquals(expResult, result);
        }*/
        /*for (int i = 0; i < 10; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            Event inContainedEvent = RandomEvents.randomOverlapsEvent(inContainsEvent);
            boolean expResult = false;
            boolean result = ContainsConstraint.outside(inContainsEvent, inContainedEvent);
            assertEquals(expResult, result);
        }*/
    }

    /**
     * Test of applyConstraint method, of class ContainsConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        OncePeriodEvent inContainsEvent;
        for (int i = 0; i < 1000; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            Event inContainedEvent = RandomEvents.randomEvent();
            ContainsConstraint instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.CONTAINS);
            instance.applyConstraint();
            org.junit.Assert.assertTrue(inContainsEvent.toTimeFrameString() + " does not contains " + inContainedEvent.toTimeFrameString(), ContainsConstraint.contains(inContainsEvent, inContainedEvent));
            org.junit.Assert.assertFalse(inContainsEvent.toTimeFrameString() + " is outside " + inContainedEvent.toTimeFrameString(), ContainsConstraint.outside(inContainsEvent, inContainedEvent));
        }
        for (int i = 0; i < 1000; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            Event inContainedEvent = RandomEvents.randomEvent();
            ContainsConstraint instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.NONOVERLAPING);
            instance.applyConstraint();
            org.junit.Assert.assertFalse(inContainsEvent.toTimeFrameString() + " contains " + inContainedEvent.toTimeFrameString(), ContainsConstraint.contains(inContainsEvent, inContainedEvent));
            org.junit.Assert.assertTrue(inContainedEvent.toTimeFrameString() + " is not outside " + inContainsEvent.toTimeFrameString(), ContainsConstraint.outside(inContainsEvent, inContainedEvent));
        }
    }

    /**
     * Test of complexApplyConstraint method, of class ContainsConstraint.
     */
    @Test
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        ContainsConstraint instance = null;
        boolean expResult = false;
        boolean result = instance.complexApplyConstraint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class ContainsConstraint.
     */
    @Test
    public void testConsistentWithConstraint() {
        System.out.println("consistentWithConstraint");
        Placement[] inValues = new Placement[2];
        ContainsConstraint instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.CONTAINS);
        boolean expResult = true;
        for (int i = 0; i < 100; i++) {
            DateTime middle = DateTimeMaker.randomDateTime();
            inValues[1] = new OnceDayEvent.OnceDayEventPlacement(middle);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(DateTimeMaker.randomDateTimeBeforeTime(middle), DateTimeMaker.randomDateTimeAfterTime(middle));
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        expResult = false;
        for (int i = 0; i < 100; i++) {
            DateTime middle = DateTimeMaker.randomDateTime();
            inValues[1] = new OnceDayEvent.OnceDayEventPlacement(DateTimeMaker.randomDateTimeBeforeTimeSure(middle));
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(middle, DateTimeMaker.randomDateTimeAfterTime(middle));
            boolean result = instance.consistentWithConstraint(inValues);
            if (result) {
                System.out.println(instance.getFirstEvent().toTimeFrameString() + " ; " + instance.getSecondEvent().toTimeFrameString());
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            DateTime middle = DateTimeMaker.randomDateTime();
            inValues[1] = new OnceDayEvent.OnceDayEventPlacement(DateTimeMaker.randomDateTimeAfterTime(middle));
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(DateTimeMaker.randomDateTimeBeforeTime(middle), middle);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result) {
                System.out.println(instance.getFirstEvent().toTimeFrameString() + " ; " + instance.getSecondEvent().toTimeFrameString());
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.NONOVERLAPING);
        expResult = false;
        for (int i = 0; i < 100; i++) {
            DateTime middle = DateTimeMaker.randomDateTime();
            inValues[1] = new OnceDayEvent.OnceDayEventPlacement(middle);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(DateTimeMaker.randomDateTimeBeforeTimeSure(middle), DateTimeMaker.randomDateTimeAfterTimeSure(middle));
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                System.out.println(instance.getFirstEvent().toTimeFrameString() + " ; " + instance.getSecondEvent().toTimeFrameString());
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        expResult = true;
        for (int i = 0; i < 100; i++) {
            DateTime middle = DateTimeMaker.randomDateTime();
            inValues[1] = new OnceDayEvent.OnceDayEventPlacement(DateTimeMaker.randomDateTimeBeforeTime(middle));
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(middle, DateTimeMaker.randomDateTimeAfterTime(middle));
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            DateTime middle = DateTimeMaker.randomDateTime();
            inValues[1] = new OnceDayEvent.OnceDayEventPlacement(DateTimeMaker.randomDateTimeAfterTime(middle));
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(DateTimeMaker.randomDateTimeBeforeTime(middle), middle);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }

        instance = new ContainsConstraint(new OncePeriodEvent(), new OncePeriodEvent(), ContainsConstraint.ContainsType.CONTAINS);
        expResult = true;
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(start, end);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(middle1, middle2);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        expResult = false;
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(end);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(start, middle1);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(middle2, end);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(start, middle1);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(middle2, end);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        expResult = false;
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTimeSure(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(start, middle2);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(middle1, end);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(start, middle2);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(middle1, end);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        instance = new ContainsConstraint(new OncePeriodEvent(), new OncePeriodEvent(), ContainsConstraint.ContainsType.NONOVERLAPING);
        expResult = false;
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(start, end);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(middle1, middle2);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        expResult = true;
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(start, middle1);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(middle2, end);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(start, middle1);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(middle2, end);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        expResult = false;
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(start, middle2);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(middle1, end);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            DateTime middle1 = DateTimeMaker.randomDateTime();
            DateTime end = DateTimeMaker.randomDateTimeAfterTime(middle1);
            DateTime start = DateTimeMaker.randomDateTimeBeforeTime(middle1);
            DateTime middle2 = DateTimeMaker.randomDateTimeBetweenTimes(middle1, end);
            inValues[1] = new OncePeriodEvent.OncePeriodEventPlacement(start, middle2);
            inValues[0] = new OncePeriodEvent.OncePeriodEventPlacement(middle1, end);
            boolean result = instance.consistentWithConstraint(inValues);
            if (result != expResult){
                instance.consistentWithConstraint(inValues);
            }
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of setNonoverlaping method, of class ContainsConstraint.
     */
    @Test
    public void testSetNonoverlaping() {
        System.out.println("setNonoverlaping");
        OncePeriodEvent inContainsEvent;
        for (int i = 0; i < 1000; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            Event inContainedEvent = RandomEvents.randomEvent();
            while (ContainsConstraint.contains(inContainsEvent, inContainedEvent)){
                inContainsEvent = RandomEvents.randomOncePeriodEvent();
                inContainedEvent = RandomEvents.randomEvent();
            }
            ContainsConstraint.setNonoverlaping(inContainsEvent, inContainedEvent);
            if (!ContainsConstraint.outside(inContainsEvent, inContainedEvent)){
                ContainsConstraint.outside(inContainsEvent, inContainedEvent);
            }
            org.junit.Assert.assertFalse(inContainsEvent.toTimeFrameString() + " contains " + inContainedEvent.toTimeFrameString(), ContainsConstraint.contains(inContainsEvent, inContainedEvent));
            org.junit.Assert.assertTrue(inContainedEvent.toTimeFrameString() + " is not outside " + inContainsEvent.toTimeFrameString(), ContainsConstraint.outside(inContainsEvent, inContainedEvent));
        }
    }

    /**
     * Test of setContains method, of class ContainsConstraint.
     */
    @Test
    public void testSetContains() {
        System.out.println("setContains");
        OncePeriodEvent inContainsEvent;
        for (int i = 0; i < 1000; i++) {
            inContainsEvent = RandomEvents.randomOncePeriodEvent();
            Event inContainedEvent = RandomEvents.randomEvent();
            while (!ContainsConstraint.couldContain(inContainsEvent, inContainedEvent)){
                inContainsEvent = RandomEvents.randomOncePeriodEvent();
                inContainedEvent = RandomEvents.randomEvent();
            }
            ContainsConstraint.setContains(inContainsEvent, inContainedEvent);
            org.junit.Assert.assertTrue(inContainsEvent.toTimeFrameString() + " does not contains " + inContainedEvent.toTimeFrameString(), ContainsConstraint.contains(inContainsEvent, inContainedEvent));
            org.junit.Assert.assertFalse(inContainsEvent.toTimeFrameString() + " is outside " + inContainedEvent.toTimeFrameString(), ContainsConstraint.outside(inContainsEvent, inContainedEvent));
        }
    }

    /**
     * Test of isStrict method, of class ContainsConstraint.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        ContainsConstraint instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.CONTAINS);
        boolean expResult = true;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrictlyBefore method, of class ContainsConstraint.
     */
    @Test
    public void testIsStrictlyBefore() {
        System.out.println("isStrictlyBefore");
        ContainsConstraint instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.CONTAINS);
        boolean expResult = false;
        boolean result = instance.isStrictlyBefore();
        assertEquals(expResult, result);
    }

    /**
     * Test of increaseWhat method, of class ContainsConstraint.
     */
    @Test
    public void testIncreaseWhat() {
        System.out.println("increaseWhat");
        Placement[] inValues = null;
        ContainsConstraint instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.CONTAINS);
        Event[] expResult = instance.getConstrainedEvents();
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getContainsType method, of class ContainsConstraint.
     */
    @Test
    public void testGetContainsType() {
        System.out.println("getContainsType");
        ContainsConstraint instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.CONTAINS);
        ContainsConstraint.ContainsType expResult = ContainsConstraint.ContainsType.CONTAINS;
        ContainsConstraint.ContainsType result = instance.getContainsType();
        assertEquals(expResult, result);
        instance = new ContainsConstraint(new OncePeriodEvent(), new OnceDayEvent(), ContainsConstraint.ContainsType.NONOVERLAPING);
        expResult = ContainsConstraint.ContainsType.NONOVERLAPING;
        result = instance.getContainsType();
        assertEquals(expResult, result);
    }

}
