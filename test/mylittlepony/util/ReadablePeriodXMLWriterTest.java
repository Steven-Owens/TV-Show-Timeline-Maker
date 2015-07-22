/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mylittlepony.util;

import TVShowTimelineMaker.util.XML.MutablePeriodXMLWriter;
import TVShowTimelineMaker.util.XML.ReadablePeriodXMLWriter;
import java.util.Random;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.MutablePeriod;
import org.joda.time.ReadablePeriod;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ReadablePeriodXMLWriterTest {
    private static final Logger LOG = Logger.getLogger(ReadablePeriodXMLWriterTest.class.getName());
    
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
        Random rnd = new Random();
        ReadablePeriod ObjectToWrite = new MutablePeriod(1+rnd.nextInt(12),rnd.nextInt(60),rnd.nextInt(60),rnd.nextInt(1000));
        ReadablePeriodXMLWriter instance = new ReadablePeriodXMLWriter();
        Element tempElement = instance.writeElements(ObjectToWrite);
        ReadablePeriod result = instance.readElements(tempElement);
        assertEquals(ObjectToWrite, result);
    }
    
}
