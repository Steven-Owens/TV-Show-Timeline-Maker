/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import TVShowTimelineMaker.util.XML.PartialXMLWriter;
import org.jdom2.Element;
import org.joda.time.Partial;
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
public class PartialXMLWriterTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public PartialXMLWriterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class PartialXMLWriter.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        PartialXMLWriter.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeElements method, of class PartialXMLWriter.
     */
    @Test
    public void testWriteElements() {
        System.out.println("writeElements");
        Partial ObjectToWrite = null;
        PartialXMLWriter instance = new PartialXMLWriter();
        Element expResult = null;
        Element result = instance.writeElements(ObjectToWrite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readElements method, of class PartialXMLWriter.
     */
    @Test
    public void testReadElements() {
        System.out.println("readElements");
        Element root = null;
        PartialXMLWriter instance = new PartialXMLWriter();
        Partial expResult = null;
        Partial result = instance.readElements(root);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
