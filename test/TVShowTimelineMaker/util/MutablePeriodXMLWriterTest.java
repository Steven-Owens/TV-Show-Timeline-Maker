/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import TVShowTimelineMaker.util.XML.MutablePeriodXMLWriter;
import org.jdom2.Element;
import org.joda.time.MutablePeriod;
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
public class MutablePeriodXMLWriterTest {
    
    @BeforeClass
    public static void setUpClass() {
        MutablePeriodXMLWriter.init();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public MutablePeriodXMLWriterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of writeElements method, of class MutablePeriodXMLWriter.
     */
    @Test
    public void testWriteElements() {
        System.out.println("writeElements");
        MutablePeriod ObjectToWrite = null;
        MutablePeriodXMLWriter instance = new MutablePeriodXMLWriter();
        Element expResult = null;
        Element result = instance.writeElements(ObjectToWrite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readElements method, of class MutablePeriodXMLWriter.
     */
    @Test
    public void testReadElements() {
        System.out.println("readElements");
        Element root = null;
        MutablePeriodXMLWriter instance = new MutablePeriodXMLWriter();
        MutablePeriod expResult = null;
        MutablePeriod result = instance.readElements(root);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
