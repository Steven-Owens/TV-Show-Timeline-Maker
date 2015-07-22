/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.util;

import TVShowTimelineMaker.util.XML.MutablePeriodXMLWriter;
import TVShowTimelineMaker.util.XML.ReadablePeriodXMLWriter;
import java.util.Random;
import org.jdom2.Element;
import org.joda.time.MutablePeriod;
import org.joda.time.ReadablePeriod;
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
public class ReadablePeriodXMLWriterTest {

    @BeforeClass
    public static void setUpClass() {
        ReadablePeriodXMLWriter.init();
        MutablePeriodXMLWriter.init();
    }

    @AfterClass
    public static void tearDownClass() {
    }

        public ReadablePeriodXMLWriterTest() {
        }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of writeElements method, of class ReadablePeriodXMLWriter.
     */
    @Test
    public void testWriteElements() {
        System.out.println("writeElements");
        Random rnd = new Random();
        for (int i = 0; i < 365; i++) {
            ReadablePeriod ObjectToWrite = new MutablePeriod(1 + rnd.nextInt(12), rnd.nextInt(60), rnd.nextInt(60), rnd.nextInt(1000));
            ReadablePeriodXMLWriter instance = new ReadablePeriodXMLWriter();
            Element tempElement = instance.writeElements(ObjectToWrite);
            ReadablePeriod result = instance.readElements(tempElement);
            assertEquals(ObjectToWrite, result);
        }
    }

    /**
     * Test of init method, of class ReadablePeriodXMLWriter.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        ReadablePeriodXMLWriter.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readElements method, of class ReadablePeriodXMLWriter.
     */
    @Test
    public void testReadElements() {
        System.out.println("readElements");
        Element root = null;
        ReadablePeriodXMLWriter instance = new ReadablePeriodXMLWriter();
        ReadablePeriod expResult = null;
        ReadablePeriod result = instance.readElements(root);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
