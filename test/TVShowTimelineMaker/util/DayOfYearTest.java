/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util;

import java.util.Random;
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
public class DayOfYearTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    Random rnd;

        public DayOfYearTest() {
            rnd = new Random();
        }

        @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMonth method, of class DayOfYear.
     */
    @Test
    public void testGetMonth() {
        System.out.println("getMonth");
        for (int i = 1; i <= 12; i++) {
            int expResult = i;
            DayOfYear instance = new DayOfYear(expResult, 1 + rnd.nextInt(28));
            int result = instance.getMonth();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getDay method, of class DayOfYear.
     */
    @Test
    public void testGetDay() {
        System.out.println("getDay");
        for (int i = 1; i <= 28; i++) {
            int expResult = i;
            DayOfYear instance = new DayOfYear(1 + rnd.nextInt(12), expResult);
            int result = instance.getDay();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of isBefore method, of class DayOfYear.
     */
    @Test
    public void testIsBefore() {
        System.out.println("isBefore");
        DayOfYear inDayOfYear = new DayOfYear(1, 2);
        DayOfYear instance = new DayOfYear(1, 1);
        boolean expResult = true;
        boolean result = instance.isBefore(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 1);
        expResult = false;
        result = instance.isBefore(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 30);
        expResult = true;
        result = instance.isBefore(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 31);
        expResult = false;
        result = instance.isBefore(inDayOfYear);
        assertEquals(expResult, result);
        expResult = true;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays(1 + rnd.nextInt(28));
            result = instance.isBefore(inDayOfYear);
            assertEquals(expResult, result);
        }
        expResult = false;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays(rnd.nextInt(28) * -1);
            result = instance.isBefore(inDayOfYear);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of isEqualOrBefore method, of class DayOfYear.
     */
    @Test
    public void testIsEqualOrBefore() {
        System.out.println("isEqualOrBefore");
        DayOfYear inDayOfYear = new DayOfYear(1, 2);
        DayOfYear instance = new DayOfYear(1, 1);
        boolean expResult = true;
        boolean result = instance.isEqualOrBefore(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 1);
        expResult = true;
        result = instance.isEqualOrBefore(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 2);
        expResult = false;
        result = instance.isEqualOrBefore(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 30);
        expResult = true;
        result = instance.isEqualOrBefore(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 31);
        expResult = true;
        result = instance.isEqualOrBefore(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 30);
        instance = new DayOfYear(12, 31);
        expResult = false;
        result = instance.isEqualOrBefore(inDayOfYear);
        assertEquals(expResult, result);
        expResult = true;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays(rnd.nextInt(28));
            result = instance.isEqualOrBefore(inDayOfYear);
            assertEquals(expResult, result);
        }
        expResult = false;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays((1 + rnd.nextInt(28)) * -1);
            result = instance.isEqualOrBefore(inDayOfYear);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of isAfter method, of class DayOfYear.
     */
    @Test
    public void testIsAfter() {
        System.out.println("isAfter");
        DayOfYear inDayOfYear = new DayOfYear(1, 2);
        DayOfYear instance = new DayOfYear(1, 1);
        boolean expResult = false;
        boolean result = instance.isAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 1);
        expResult = false;
        result = instance.isAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 2);
        expResult = true;
        result = instance.isAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 30);
        expResult = false;
        result = instance.isAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 31);
        expResult = false;
        result = instance.isAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 30);
        instance = new DayOfYear(12, 31);
        expResult = true;
        result = instance.isAfter(inDayOfYear);
        assertEquals(expResult, result);
        expResult = false;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays(rnd.nextInt(28));
            result = instance.isAfter(inDayOfYear);
            assertEquals(expResult, result);
        }
        expResult = true;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays((1 + rnd.nextInt(28)) * -1);
            result = instance.isAfter(inDayOfYear);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of isEqualOrAfter method, of class DayOfYear.
     */
    @Test
    public void testIsEqualOrAfter() {
        System.out.println("isEqualOrAfter");
        DayOfYear inDayOfYear = new DayOfYear(1, 2);
        DayOfYear instance = new DayOfYear(1, 1);
        boolean expResult = false;
        boolean result = instance.isEqualOrAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 1);
        expResult = true;
        result = instance.isEqualOrAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 2);
        expResult = true;
        result = instance.isEqualOrAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 30);
        expResult = false;
        result = instance.isEqualOrAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 31);
        expResult = true;
        result = instance.isEqualOrAfter(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 30);
        instance = new DayOfYear(12, 31);
        expResult = true;
        result = instance.isEqualOrAfter(inDayOfYear);
        assertEquals(expResult, result);
        expResult = false;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays(1 + rnd.nextInt(28));
            result = instance.isEqualOrAfter(inDayOfYear);
            assertEquals(expResult, result);
        }
        expResult = true;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays(rnd.nextInt(28) * -1);
            result = instance.isEqualOrAfter(inDayOfYear);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of equals method, of class DayOfYear.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object inDayOfYear = "testing";
        DayOfYear instance = new DayOfYear(1, 1);
        boolean expResult = false;
        boolean result = instance.equals(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 2);
        instance = new DayOfYear(1, 1);
        expResult = false;
        result = instance.equals(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 1);
        expResult = true;
        result = instance.equals(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(1, 1);
        instance = new DayOfYear(1, 2);
        expResult = false;
        result = instance.equals(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 30);
        expResult = false;
        result = instance.equals(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 31);
        instance = new DayOfYear(12, 31);
        expResult = true;
        result = instance.equals(inDayOfYear);
        assertEquals(expResult, result);
        inDayOfYear = new DayOfYear(12, 30);
        instance = new DayOfYear(12, 31);
        expResult = false;
        result = instance.equals(inDayOfYear);
        assertEquals(expResult, result);
        expResult = false;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays(1 + rnd.nextInt(28));
            result = instance.equals(inDayOfYear);
            assertEquals(expResult, result);
        }
        expResult = false;
        for (int i = 0; i < 365; i++) {
            instance = new DayOfYear(2 + rnd.nextInt(10), 1 + rnd.nextInt(28));
            inDayOfYear = instance.plusDays((1 + rnd.nextInt(28)) * -1);
            result = instance.equals(inDayOfYear);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of plusDays method, of class DayOfYear.
     */
    @Test
    public void testPlusDays() {
        System.out.println("plusDays");
        int i = 1;
        DayOfYear instance = new DayOfYear(1, 1);
        DayOfYear expResult = new DayOfYear(1, 2);
        DayOfYear result = instance.plusDays(i);
        assertEquals(expResult, result);
        instance = new DayOfYear(1, 31);
        expResult = new DayOfYear(2, 1);
        result = instance.plusDays(i);
        assertEquals(expResult, result);
        instance = new DayOfYear(12, 31);
        expResult = new DayOfYear(1, 1);
        result = instance.plusDays(i);
        assertEquals(expResult, result);
        for (int count = 0; count < 365; count++) {
            instance = new DayOfYear(1 + rnd.nextInt(12), 1 + rnd.nextInt(28));
            expResult = new DayOfYear(1 + rnd.nextInt(12), 1 + rnd.nextInt(28));
            i = instance.diff(expResult);
            result = instance.plusDays(i);
            if ((expResult != null) && (!expResult.equals(result))){
                System.out.println("instance: " + instance.toString());
            }
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of plusDaysNoWrap method, of class DayOfYear.
     */
    @Test
    public void testPlusDaysNoWrap() {
        System.out.println("plusDaysNoWrap");
        int i = 1;
        DayOfYear instance = new DayOfYear(1, 1);
        DayOfYear expResult = new DayOfYear(1, 2);
        DayOfYear result = instance.plusDaysNoWrap(i);
        assertEquals(expResult, result);
        instance = new DayOfYear(1, 31);
        expResult = new DayOfYear(2, 1);
        result = instance.plusDaysNoWrap(i);
        assertEquals(expResult, result);
        instance = new DayOfYear(12, 31);
        expResult = null;
        result = instance.plusDaysNoWrap(i);
        assertEquals(expResult, result);
        i = 0;
        instance = new DayOfYear(1, 1);
        expResult = new DayOfYear(1, 1);
        result = instance.plusDaysNoWrap(i);
        assertEquals(expResult, result);
        for (int count = 0; count < 365; count++) {
            instance = new DayOfYear(1 + rnd.nextInt(12), 1 + rnd.nextInt(28));
            expResult = new DayOfYear(1 + rnd.nextInt(12), 1 + rnd.nextInt(28));
            i = instance.diff(expResult);
            if (expResult.isBefore(instance)) {
                expResult = null;
            }
            result = instance.plusDaysNoWrap(i);
            if ((expResult != null) && (!expResult.equals(result))){
                System.out.println("instance: " + instance.toString());
            }
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of toDateTime method, of class DayOfYear.
     */
    @Test
    public void testToDateTime() {
        System.out.println("toDateTime");
        int month = 1;
        int day = 1;
        DayOfYear instance = new DayOfYear(month, day);
        DateTime expResult = new DateTime(0, month, day, 12, 0);
        DateTime result = instance.toDateTime();
        assertEquals(expResult, result);
        month = 1;
        day = 31;
        instance = new DayOfYear(month, day);
        expResult = new DateTime(0, month, day, 12, 0);
        result = instance.toDateTime();
        assertEquals(expResult, result);
        month = 12;
        day = 1;
        instance = new DayOfYear(month, day);
        expResult = new DateTime(0, month, day, 12, 0);
        result = instance.toDateTime();
        assertEquals(expResult, result);
        month = 12;
        day = 31;
        instance = new DayOfYear(month, day);
        expResult = new DateTime(0, month, day, 12, 0);
        result = instance.toDateTime();
        assertEquals(expResult, result);
        for (int i = 0; i < 365; i++) {
            month = 1 + rnd.nextInt(12);
            day = 1 + rnd.nextInt(28);
            instance = new DayOfYear(month, day);
            expResult = new DateTime(0, month, day, 12, 0);
            result = instance.toDateTime();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of fromDateTime method, of class DayOfYear.
     */
    @Test
    public void testFromDateTime() {
        System.out.println("fromDateTime");
        int year = 0;
        int month = 1;
        int day = 1;
        DateTime inDateTime = new DateTime(year, month, day, 12, 0);
        DayOfYear expResult = new DayOfYear(month, day);;
        DayOfYear result = DayOfYear.fromDateTime(inDateTime);
        assertEquals(expResult, result);
        year = 1;
        month = 1;
        day = 31;
        inDateTime = new DateTime(year, month, day, 12, 0);
        expResult = new DayOfYear(month, day);;
        result = DayOfYear.fromDateTime(inDateTime);
        assertEquals(expResult, result);
        year = -1;
        month = 12;
        day = 1;
        inDateTime = new DateTime(year, month, day, 12, 0);
        expResult = new DayOfYear(month, day);;
        result = DayOfYear.fromDateTime(inDateTime);
        assertEquals(expResult, result);
        year = 1000;
        month = 12;
        day = 31;
        inDateTime = new DateTime(year, month, day, 12, 0);
        expResult = new DayOfYear(month, day);;
        result = DayOfYear.fromDateTime(inDateTime);
        assertEquals(expResult, result);
        for (int i = 0; i < 365; i++) {
            year = rnd.nextInt(2000) - 1000;
            month = 1 + rnd.nextInt(12);
            day = 1 + rnd.nextInt(28);
            inDateTime = new DateTime(year, month, day, 12, 0);
            expResult = new DayOfYear(month, day);;
            result = DayOfYear.fromDateTime(inDateTime);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of compareTo method, of class DayOfYear.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        DayOfYear o = new DayOfYear(1,1);
        DayOfYear instance = new DayOfYear(1,1);
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        o = new DayOfYear(1,1);
        instance = new DayOfYear(2,1);
        expResult = 1;
        result = instance.compareTo(o);
        assertEquals(expResult, result);
        o = new DayOfYear(3,1);
        instance = new DayOfYear(2,1);
        expResult = -1;
        result = instance.compareTo(o);
        assertEquals(expResult, result);
        o = new DayOfYear(4,1);
        instance = new DayOfYear(4,2);
        expResult = 1;
        result = instance.compareTo(o);
        assertEquals(expResult, result);
        o = new DayOfYear(4,3);
        instance = new DayOfYear(4,2);
        expResult = -1;
        result = instance.compareTo(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of diff method, of class DayOfYear.
     */
    @Test
    public void testDiff() {
        System.out.println("diff");
        DayOfYear instance = new DayOfYear(1,1);
        DayOfYear inDayOfYear = new DayOfYear(1,1);
        int expResult = 0;
        int result = instance.diff(inDayOfYear);
        assertEquals(expResult, result);
        instance = new DayOfYear(1,1);
        inDayOfYear = new DayOfYear(1,31);
        expResult = 30;
        result = instance.diff(inDayOfYear);
        assertEquals(expResult, result);
        instance = new DayOfYear(1,31);
        inDayOfYear = new DayOfYear(2,1);
        expResult = 1;
        result = instance.diff(inDayOfYear);
        assertEquals(expResult, result);
        instance = new DayOfYear(12,31);
        inDayOfYear = new DayOfYear(1,1);
        expResult = 1;
        result = instance.diff(inDayOfYear);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class DayOfYear.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        DayOfYear instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class DayOfYear.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        DayOfYear instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
