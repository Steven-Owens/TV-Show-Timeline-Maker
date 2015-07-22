/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class SeasonTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

        public SeasonTest() {
        }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of inSeason method, of class Season.
     */
    @Test
    public void testInSeason_DateTime() {
        System.out.println("inSeason");
        for (Season curSeason : Season.values()) {
            System.out.println(curSeason.toString());
            DateTime inDateTime = curSeason.getStartDay().toDateTime();
            boolean result = curSeason.inSeason(inDateTime);
            assertTrue(result);
            inDateTime = inDateTime.minusDays(1);
            result = curSeason.inSeason(inDateTime);
            assertFalse(result);
            inDateTime = curSeason.getEndDay().toDateTime();
            result = curSeason.inSeason(inDateTime);
            assertTrue(result);
            inDateTime = inDateTime.plusDays(1);
            result = curSeason.inSeason(inDateTime);
            assertFalse(result);
        }
    }

    /**
     * Test of inSeason method, of class Season.
     */
    @Test
    public void testInSeason_DayOfYear() {
        System.out.println("inSeason");
        for (Season curSeason : Season.values()) {
            System.out.println(curSeason.toString());
            DayOfYear inDateTime = curSeason.getStartDay();
            boolean result = curSeason.inSeason(inDateTime);
            assertTrue(result);
            inDateTime = inDateTime.plusDays(-1);
            result = curSeason.inSeason(inDateTime);
            assertFalse(result);
            inDateTime = curSeason.getEndDay();
            result = curSeason.inSeason(inDateTime);
            assertTrue(result);
            inDateTime = inDateTime.plusDays(1);
            result = curSeason.inSeason(inDateTime);
            assertFalse(result);
        }
    }

    /**
     * Test of values method, of class Season.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        Season[] expResult = null;
        Season[] result = Season.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueOf method, of class Season.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "";
        Season expResult = null;
        Season result = Season.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartDay method, of class Season.
     */
    @Test
    public void testGetStartDay() {
        System.out.println("getStartDay");
        Season instance = null;
        DayOfYear expResult = null;
        DayOfYear result = instance.getStartDay();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndDay method, of class Season.
     */
    @Test
    public void testGetEndDay() {
        System.out.println("getEndDay");
        Season instance = null;
        DayOfYear expResult = null;
        DayOfYear result = instance.getEndDay();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
