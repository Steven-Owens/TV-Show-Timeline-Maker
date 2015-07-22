/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util;

import TVShowTimelineMaker.util.XML.DateTimeXMLWriter;
import TVShowTimelineMaker.util.XML.IntervalXMLWriter;
import java.util.Random;
import org.jdom2.Element;
import org.joda.time.Interval;
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
public class IntervalXMLWriterTest {

    @BeforeClass
    public static void setUpClass() {
        IntervalXMLWriter.init();
        DateTimeXMLWriter.init();
    }

    @AfterClass
    public static void tearDownClass() {
    }

        public IntervalXMLWriterTest() {
        }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of writeElements method, of class IntervalXMLWriter.
     */
    @Test
    public void testWriteElements() {
        System.out.println("writeElements");
        Random rnd = new Random();
        for (int i = 0; i < 365; i++) {
            long start = Math.abs(rnd.nextInt());
            long end = start + 1 +  Math.abs(rnd.nextInt());
            Interval ObjectToWrite = new Interval(start, end);
            IntervalXMLWriter instance = new IntervalXMLWriter();
            Element tempContent = instance.writeElements(ObjectToWrite);
            Interval result = instance.readElements(tempContent);
            assertEquals(ObjectToWrite, result);
        }
    }

    /**
     * Test of init method, of class IntervalXMLWriter.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        IntervalXMLWriter.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readElements method, of class IntervalXMLWriter.
     */
    @Test
    public void testReadElements() {
        System.out.println("readElements");
        Element root = null;
        IntervalXMLWriter instance = new IntervalXMLWriter();
        Interval expResult = null;
        Interval result = instance.readElements(root);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
