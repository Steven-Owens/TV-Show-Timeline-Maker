/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.testUtil;

import TVShowTimelineMaker.timeConstraints.ContainsConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.YearlyDayEvent;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RandomEventsTest {
    private static final Logger LOG = Logger.getLogger(RandomEventsTest.class.getName());

    @BeforeClass
    public static void setUpClass() {
        ContainsConstraint.init();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRandomOncePeriodEvent() {
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent();
            org.junit.Assert.assertTrue("output not vaild", rOncePeriodEvent.isValid());
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(i);
            org.junit.Assert.assertTrue("output not vaild", rOncePeriodEvent.isValid());
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-i);
            org.junit.Assert.assertTrue("output not vaild", rOncePeriodEvent.isValid());
        }
        //todo: test randomness
    }

    /**
     * Test of containsCould method, of class OncePeriodEvent.
     */
    @Test
    public void testContainsCould_DateTime() {
        System.out.println("containsCould");
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent();
            DateTime inDateTime = DateTimeMaker.randomDateTimeBetweenTimes(instance.getEarliestPossibleStartTime(), instance.getLatestPossibleEndTime());
            boolean expResult = true;
            boolean result = instance.containsCould(inDateTime);
            assertEquals(inDateTime.toString() + " is not contained in " + instance.toTimeFrameString(), expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent(-2);
            DateTime inDateTime = DateTimeMaker.randomDateTimeBeforeTime(instance.getEarliestPossibleStartTime().minusDays(1));
            boolean expResult = false;
            boolean result = instance.containsCould(inDateTime);
            assertEquals(inDateTime.toString() + " is contained in " + instance.toTimeFrameString(), expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent(-2);
            DateTime inDateTime = DateTimeMaker.randomDateTimeAfterTime(instance.getLatestPossibleEndTime().plusDays(1));
            boolean expResult = false;
            boolean result = instance.containsCould(inDateTime);
            assertEquals(inDateTime.toString() + " is contained in " + instance.toTimeFrameString(), expResult, result);
        }
    }

    /**
     * Test of containsSure method, of class OncePeriodEvent.
     */
    @Test
    public void testContainsSure_DateTime() {
        System.out.println("containsSure");
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent();
            if (instance.getLatestPossibleStartTime().isBefore(instance.getEarliestPossibleEndTime().minusDays(2))) {
                DateTime inDateTime = DateTimeMaker.randomDateTimeBetweenTimes(instance.getLatestPossibleStartTime().plusDays(1), instance.getEarliestPossibleEndTime().minusDays(1));
                boolean expResult = true;
                boolean result = instance.containsSure(inDateTime);
                assertEquals(inDateTime.toString() + " is not contained in " + instance.toTimeFrameString(), expResult, result);
            } else {
                System.out.println(i);
            }
        }
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent(-2);
            DateTime inDateTime = DateTimeMaker.randomDateTimeBeforeTime(instance.getLatestPossibleStartTime().minusDays(1));
            boolean expResult = false;
            boolean result = instance.containsSure(inDateTime);
            assertEquals(inDateTime.toString() + " is contained in " + instance.toTimeFrameString(), expResult, result);
        }
        for (int i = 0; i < 100; i++) {
            OncePeriodEvent instance = RandomEvents.randomOncePeriodEvent(-2);
            DateTime inDateTime = DateTimeMaker.randomDateTimeAfterTime(instance.getEarliestPossibleEndTime().plusDays(1));
            boolean expResult = false;
            boolean result = instance.containsSure(inDateTime);
            assertEquals(inDateTime.toString() + " is contained in " + instance.toTimeFrameString(), expResult, result);
        }
    }

    @Test
    public void testRandomContainsEventMakeSamples() {
        EventPairTestCaseDocument.init();
        Collection<EventPair> OncePeriodEventNotValid = new java.util.ArrayList<>();
        Collection<EventPair> OncePeriodEventContains = new java.util.ArrayList<>();
        Collection<EventPair> OncePeriodEventCouldOverlap = new java.util.ArrayList<>();
        Collection<EventPair> OncePeriodEventOverlap = new java.util.ArrayList<>();
        Collection<EventPair> OncePeriodEventOutside = new java.util.ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                OncePeriodEvent testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent, OncePeriodEvent.class);
                if (testEvent.isValid()) {
                    if (!ContainsConstraint.contains(rOncePeriodEvent, testEvent)) {
                        OncePeriodEventContains.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                    if (!ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent)) {
                        OncePeriodEventCouldOverlap.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                    if (!ContainsConstraint.overlaps(testEvent, rOncePeriodEvent)) {
                        OncePeriodEventOverlap.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                    if (ContainsConstraint.outside(rOncePeriodEvent, testEvent)) {
                        OncePeriodEventOutside.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                } else {
                    OncePeriodEventNotValid.add(new EventPair(rOncePeriodEvent, testEvent));
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collection<EventPair> OnceDayEventNotValid = new java.util.ArrayList<>();
        Collection<EventPair> OnceDayEventContains = new java.util.ArrayList<>();
        Collection<EventPair> OnceDayEventOutside = new java.util.ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                OnceDayEvent testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent, OnceDayEvent.class);
                if (testEvent.isValid()) {
                    if (!ContainsConstraint.contains(rOncePeriodEvent, testEvent)) {
                        OnceDayEventContains.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                    if (ContainsConstraint.outside(rOncePeriodEvent, testEvent)) {
                        OnceDayEventOutside.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                } else {
                    OnceDayEventNotValid.add(new EventPair(rOncePeriodEvent, testEvent));
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collection<EventPair> YearlyPeriodEventNotValid = new java.util.ArrayList<>();
        Collection<EventPair> YearlyPeriodEventContains = new java.util.ArrayList<>();
        Collection<EventPair> YearlyPeriodEventCouldOverlap = new java.util.ArrayList<>();
        Collection<EventPair> YearlyPeriodEventOverlap = new java.util.ArrayList<>();
        Collection<EventPair> YearlyPeriodEventOutside = new java.util.ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                YearlyPeriodEvent testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent, YearlyPeriodEvent.class);
                if (testEvent.isValid()) {
                    if (!ContainsConstraint.contains(rOncePeriodEvent, testEvent)) {
                        YearlyPeriodEventContains.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                    if (!ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent)) {
                        YearlyPeriodEventCouldOverlap.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                    if (!ContainsConstraint.overlaps(testEvent, rOncePeriodEvent)) {
                        YearlyPeriodEventOverlap.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                    if (ContainsConstraint.outside(rOncePeriodEvent, testEvent)) {
                        YearlyPeriodEventOutside.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                } else {
                    YearlyPeriodEventNotValid.add(new EventPair(rOncePeriodEvent, testEvent));
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        Collection<EventPair> YearlyDayEventNotValid = new java.util.ArrayList<>();
        Collection<EventPair> YearlyDayEventContains = new java.util.ArrayList<>();
        Collection<EventPair> YearlyDayEventOutside = new java.util.ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                YearlyDayEvent testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent, YearlyDayEvent.class);
                if (testEvent.isValid()) {
                    if (!ContainsConstraint.contains(rOncePeriodEvent, testEvent)) {
                        YearlyDayEventContains.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                    if (ContainsConstraint.outside(rOncePeriodEvent, testEvent)) {
                        YearlyDayEventOutside.add(new EventPair(rOncePeriodEvent, testEvent));
                    }
                } else {
                    YearlyDayEventNotValid.add(new EventPair(rOncePeriodEvent, testEvent));
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //write xml file
        if (!OncePeriodEventNotValid.isEmpty()){
            EventPairTestCaseDocument OncePeriodEventNotValidTestCaseDocument = new EventPairTestCaseDocument("OncePeriodEventNotValid.xml");
            try {
                OncePeriodEventNotValidTestCaseDocument.writeXMLFile(OncePeriodEventNotValid);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!OncePeriodEventContains.isEmpty()){
            EventPairTestCaseDocument OncePeriodEventContainsTestCaseDocument = new EventPairTestCaseDocument("OncePeriodEventContains.xml");
            try {
                OncePeriodEventContainsTestCaseDocument.writeXMLFile(OncePeriodEventContains);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!OncePeriodEventCouldOverlap.isEmpty()){
            EventPairTestCaseDocument OncePeriodEventCouldOverlapTestCaseDocument = new EventPairTestCaseDocument("OncePeriodEventCouldOverlap.xml");
            try {
                OncePeriodEventCouldOverlapTestCaseDocument.writeXMLFile(OncePeriodEventCouldOverlap);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!OncePeriodEventOverlap.isEmpty()){
            EventPairTestCaseDocument OncePeriodEventOverlapTestCaseDocument = new EventPairTestCaseDocument("OncePeriodEventOverlap.xml");
            try {
                OncePeriodEventOverlapTestCaseDocument.writeXMLFile(OncePeriodEventOverlap);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!OncePeriodEventOutside.isEmpty()){
            EventPairTestCaseDocument OncePeriodEventOutsideTestCaseDocument = new EventPairTestCaseDocument("OncePeriodEventOutside.xml");
            try {
                OncePeriodEventOutsideTestCaseDocument.writeXMLFile(OncePeriodEventOutside);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!OnceDayEventNotValid.isEmpty()){
            EventPairTestCaseDocument OnceDayEventNotValidTestCaseDocument = new EventPairTestCaseDocument("OnceDayEventNotValid.xml");
            try {
                OnceDayEventNotValidTestCaseDocument.writeXMLFile(OnceDayEventNotValid);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!OnceDayEventContains.isEmpty()){
            EventPairTestCaseDocument OnceDayEventContainsTestCaseDocument = new EventPairTestCaseDocument("OnceDayEventContains.xml");
            try {
                OnceDayEventContainsTestCaseDocument.writeXMLFile(OnceDayEventContains);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!OnceDayEventOutside.isEmpty()){
            EventPairTestCaseDocument OnceDayEventOutsideTestCaseDocument = new EventPairTestCaseDocument("OnceDayEventOutside.xml");
            try {
                OnceDayEventOutsideTestCaseDocument.writeXMLFile(OnceDayEventOutside);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!YearlyPeriodEventNotValid.isEmpty()){
            EventPairTestCaseDocument YearlyPeriodEventNotValidTestCaseDocument = new EventPairTestCaseDocument("YearlyPeriodEventNotValid.xml");
            try {
                YearlyPeriodEventNotValidTestCaseDocument.writeXMLFile(YearlyPeriodEventNotValid);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!YearlyPeriodEventContains.isEmpty()){
            EventPairTestCaseDocument YearlyPeriodEventContainsTestCaseDocument = new EventPairTestCaseDocument("YearlyPeriodEventContains.xml");
            try {
                YearlyPeriodEventContainsTestCaseDocument.writeXMLFile(YearlyPeriodEventContains);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!YearlyPeriodEventCouldOverlap.isEmpty()){
            EventPairTestCaseDocument YearlyPeriodEventCouldOverlapTestCaseDocument = new EventPairTestCaseDocument("YearlyPeriodEventCouldOverlap.xml");
            try {
                YearlyPeriodEventCouldOverlapTestCaseDocument.writeXMLFile(YearlyPeriodEventCouldOverlap);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!YearlyPeriodEventOverlap.isEmpty()){
            EventPairTestCaseDocument YearlyPeriodEventOverlapTestCaseDocument = new EventPairTestCaseDocument("YearlyPeriodEventOverlap.xml");
            try {
                YearlyPeriodEventOverlapTestCaseDocument.writeXMLFile(YearlyPeriodEventOverlap);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!YearlyPeriodEventOutside.isEmpty()){
            EventPairTestCaseDocument YearlyPeriodEventOutsideTestCaseDocument = new EventPairTestCaseDocument("YearlyPeriodEventOutside.xml");
            try {
                YearlyPeriodEventOutsideTestCaseDocument.writeXMLFile(YearlyPeriodEventOutside);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!YearlyDayEventNotValid.isEmpty()){
            EventPairTestCaseDocument YearlyDayEventNotValidTestCaseDocument = new EventPairTestCaseDocument("YearlyDayEventNotValid.xml");
            try {
                YearlyDayEventNotValidTestCaseDocument.writeXMLFile(YearlyDayEventNotValid);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!YearlyDayEventContains.isEmpty()){
            EventPairTestCaseDocument YearlyDayEventContainsTestCaseDocument = new EventPairTestCaseDocument("YearlyDayEventContains.xml");
            try {
                YearlyDayEventContainsTestCaseDocument.writeXMLFile(YearlyDayEventContains);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!YearlyDayEventOutside.isEmpty()){
            EventPairTestCaseDocument YearlyDayEventOutsideTestCaseDocument = new EventPairTestCaseDocument("YearlyDayEventOutside.xml");
            try {
                YearlyDayEventOutsideTestCaseDocument.writeXMLFile(YearlyDayEventOutside);
            } catch (IOException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //fail
        if (!OncePeriodEventNotValid.isEmpty()){
            fail("fail");
        }
        if (!OncePeriodEventContains.isEmpty()){
            fail("fail");
        }
        if (!OncePeriodEventCouldOverlap.isEmpty()){
            fail("fail");
        }
        if (!OncePeriodEventOverlap.isEmpty()){
            fail("fail");
        }
        if (!OncePeriodEventOutside.isEmpty()){
            fail("fail");
        }
        if (!OnceDayEventNotValid.isEmpty()){
            fail("fail");
        }
        if (!OnceDayEventContains.isEmpty()){
            fail("fail");
        }
        if (!OnceDayEventOutside.isEmpty()){
            fail("fail");
        }
        if (!YearlyPeriodEventNotValid.isEmpty()){
            fail("fail");
        }
        if (!YearlyPeriodEventContains.isEmpty()){
            fail("fail");
        }
        if (!YearlyPeriodEventCouldOverlap.isEmpty()){
            fail("fail");
        }
        if (!YearlyPeriodEventOverlap.isEmpty()){
            fail("fail");
        }
        if (!YearlyPeriodEventOutside.isEmpty()){
            fail("fail");
        }
        if (!YearlyDayEventNotValid.isEmpty()){
            fail("fail");
        }
        if (!YearlyDayEventContains.isEmpty()){
            fail("fail");
        }
        if (!YearlyDayEventOutside.isEmpty()){
            fail("fail");
        }
    }

    @Test
    public void testRandomContainsEvent() {
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                OncePeriodEvent testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent, OncePeriodEvent.class);
                org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
                if (!ContainsConstraint.contains(rOncePeriodEvent, testEvent)) {
                    ContainsConstraint.contains(rOncePeriodEvent, testEvent);
                }
                if (!ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent)) {
                    ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent);
                }
                if (!ContainsConstraint.overlaps(testEvent, rOncePeriodEvent)) {
                    ContainsConstraint.overlaps(testEvent, rOncePeriodEvent);
                }
                if (ContainsConstraint.outside(rOncePeriodEvent, testEvent)) {
                    ContainsConstraint.outside(rOncePeriodEvent, testEvent);
                }
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " does not contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " doesn't overlap " + testEvent.toTimeFrameString(), ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent));
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " doesn't overlap " + testEvent.toTimeFrameString(), ContainsConstraint.overlaps(testEvent, rOncePeriodEvent));
                org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " is outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                OnceDayEvent testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent, OnceDayEvent.class);
                org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " does not contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
                org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " is outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                YearlyPeriodEvent testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent, YearlyPeriodEvent.class);
                org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " does not contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " doesn't overlap " + testEvent.toTimeFrameString(), ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent));
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " doesn't overlap " + testEvent.toTimeFrameString(), ContainsConstraint.overlaps(testEvent, rOncePeriodEvent));
                org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " is outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                YearlyDayEvent testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent, YearlyDayEvent.class);
                org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " does not contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
                org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " is outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            Event testEvent = RandomEvents.randomContainsEvent(rOncePeriodEvent);
            org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
            org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " does not contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
            org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " is outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
        }
        //todo: test randomness
    }

    @Test
    public void testRandomOutsideEvent() {
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                OncePeriodEvent testEvent = RandomEvents.randomOutsideEvent(rOncePeriodEvent, OncePeriodEvent.class);
                org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
                org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
                org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " overlaps " + testEvent.toTimeFrameString(), ContainsConstraint.overlaps(testEvent, rOncePeriodEvent));
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " is not outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                OnceDayEvent testEvent = RandomEvents.randomOutsideEvent(rOncePeriodEvent, OnceDayEvent.class);
                org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
                org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
                org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " is not outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                YearlyPeriodEvent testEvent = RandomEvents.randomOutsideEvent(rOncePeriodEvent, YearlyPeriodEvent.class);
                if ((!testEvent.getStartPossibleDays().isEmpty()) && (!testEvent.getEndPossibleDays().isEmpty())) {
                    if (!testEvent.isValid()) {
                        testEvent.isValid();
                    }
                    org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
                    if (ContainsConstraint.outside(rOncePeriodEvent, testEvent)) {
                        ContainsConstraint.outside(rOncePeriodEvent, testEvent);
                    }
                    org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
                    org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " overlaps " + testEvent.toTimeFrameString(), ContainsConstraint.overlaps(testEvent, rOncePeriodEvent));
                    org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " is not outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            try {
                YearlyDayEvent testEvent = RandomEvents.randomOutsideEvent(rOncePeriodEvent, YearlyDayEvent.class);
                if (!testEvent.getPossibleDays().isEmpty()) {
                    org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
                    org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
                    org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " is not outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(RandomEventsTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            Event testEvent = RandomEvents.randomOutsideEvent(rOncePeriodEvent);
            org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
            org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
            org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " is not outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
        }
        //todo: test randomness
    }

    @Test
    public void testRandomOverlapsEvent() {
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            OncePeriodEvent testEvent = RandomEvents.randomOverlapsEvent(rOncePeriodEvent);
            org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
            if (!ContainsConstraint.overlaps(rOncePeriodEvent, testEvent)) {
                ContainsConstraint.overlaps(rOncePeriodEvent, testEvent);
            }
            if (!ContainsConstraint.overlaps(testEvent, rOncePeriodEvent)) {
                ContainsConstraint.overlaps(testEvent, rOncePeriodEvent);
            }
            org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " contains " + testEvent.toTimeFrameString(), ContainsConstraint.contains(rOncePeriodEvent, testEvent));
            org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " doesn't overlap " + testEvent.toTimeFrameString(), ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent));
            org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " doesn't overlap " + testEvent.toTimeFrameString(), ContainsConstraint.overlaps(testEvent, rOncePeriodEvent));
            org.junit.Assert.assertFalse(rOncePeriodEvent.toTimeFrameString() + " is outside " + testEvent.toTimeFrameString(), ContainsConstraint.outside(rOncePeriodEvent, testEvent));
        }
        //todo: test randomness
    }

    @Test
    public void testRandomCouldOverlapsEvent() {
        /*for (int i = 0; i < 1000; i++) {
         OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent();
         Event testEvent = RandomEvents.randomCouldOverlapsEvent(rOncePeriodEvent);
         }*/
        for (int i = 0; i < 1000; i++) {
            OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent(-365);
            OncePeriodEvent testEvent = RandomEvents.randomCouldOverlapsEvent(rOncePeriodEvent, OncePeriodEvent.class);
            org.junit.Assert.assertTrue("output not vaild", testEvent.isValid());
            if (!ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent)) {
                ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent);
            }
            org.junit.Assert.assertTrue(rOncePeriodEvent.toTimeFrameString() + " doesn't overlap " + testEvent.toTimeFrameString(), ContainsConstraint.couldOverlap(testEvent, rOncePeriodEvent));
        }
        /*for (int i = 0; i < 1000; i++) {
         OncePeriodEvent rOncePeriodEvent = RandomEvents.randomOncePeriodEvent();
         OnceDayEvent testEvent = RandomEvents.randomCouldOverlapsEvent(rOncePeriodEvent, OnceDayEvent.class);
         }*/
        //todo: test randomness
    }
}
