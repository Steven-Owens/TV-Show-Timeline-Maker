/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.testUtil;

import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class DateTimeMakerTest {
    private static final Logger LOG = Logger.getLogger(DateTimeMakerTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
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
    public void testRandomDateTime() {
        DateTime randomDateTime = DateTimeMaker.randomDateTime();
        //TODO: test randomness
        DateTimeMaker.randomDateTimeAfterTime(randomDateTime);
        DateTimeMaker.randomDateTimeBeforeTime(randomDateTime);
        DateTimeMaker.randomDateTimeBetweenTimes(randomDateTime, randomDateTime);
    }
    
    @Test
    public void testRandomDateTimeAfterTime() {
        for (int i = 0; i <1000; i++){
            DateTime randomDateTime = DateTimeMaker.randomDateTime();
            DateTime secondDate = DateTimeMaker.randomDateTimeAfterTime(randomDateTime);
            org.junit.Assert.assertTrue(secondDate.toString() + " is not after " + randomDateTime.toString(),secondDate.isAfter(randomDateTime) || secondDate.equals(randomDateTime));
        }
    }
    
    @Test
    public void testRandomDateTimeBeforeTime() {
        for (int i = 0; i <1000; i++){
            DateTime randomDateTime = DateTimeMaker.randomDateTime();
            DateTime secondDate = DateTimeMaker.randomDateTimeBeforeTime(randomDateTime);
            org.junit.Assert.assertTrue(secondDate.toString() + " is not before " + randomDateTime.toString(),secondDate.isBefore(randomDateTime) || secondDate.equals(randomDateTime));
        }
    }
    
    @Test
    public void testRandomDateTimeBetweenTimes() {
        for (int i = 0; i <1000; i++){
            DateTime startDateTime = DateTimeMaker.randomDateTime();
            DateTime endDateTime = DateTimeMaker.randomDateTimeAfterTime(startDateTime);
            DateTime testDateTime = DateTimeMaker.randomDateTimeBetweenTimes(startDateTime, endDateTime);
            org.junit.Assert.assertTrue(testDateTime.toString() + " is not after " + startDateTime.toString(),testDateTime.isAfter(startDateTime) || testDateTime.equals(startDateTime));
            org.junit.Assert.assertTrue(testDateTime.toString() + " is not before " + endDateTime.toString(),testDateTime.isBefore(endDateTime) || testDateTime.equals(endDateTime));
        }
    }
}
