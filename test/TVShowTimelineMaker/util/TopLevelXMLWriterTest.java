/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import TVShowTimelineMaker.util.XML.TopLevelXMLWriter;
import TVShowTimelineMaker.Show;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;

/**
 *
 * @author Steven Owens
 */
public class TopLevelXMLWriterTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public TopLevelXMLWriterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of writeXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testWriteXMLFile_String_Show() throws Exception {
        System.out.println("writeXMLFile");
        String Path = "";
        Show inShow = null;
        TopLevelXMLWriter.writeXMLFile(Path, inShow);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testWriteXMLFile_File_Show() throws Exception {
        System.out.println("writeXMLFile");
        File inFile = null;
        Show inShow = null;
        TopLevelXMLWriter.writeXMLFile(inFile, inShow);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testWriteXMLFile_OutputStream_Show() throws Exception {
        System.out.println("writeXMLFile");
        OutputStream inFile = null;
        Show inShow = null;
        TopLevelXMLWriter.writeXMLFile(inFile, inShow);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testWriteXMLFile_BufferedOutputStream_Show() throws Exception {
        System.out.println("writeXMLFile");
        BufferedOutputStream inFile = null;
        Show inShow = null;
        TopLevelXMLWriter.writeXMLFile(inFile, inShow);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testWriteXMLFile_Writer_Show() throws Exception {
        System.out.println("writeXMLFile");
        Writer inFile = null;
        Show inShow = null;
        TopLevelXMLWriter.writeXMLFile(inFile, inShow);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testWriteXMLFile_BufferedWriter_Show() throws Exception {
        System.out.println("writeXMLFile");
        BufferedWriter inFile = null;
        Show inShow = null;
        TopLevelXMLWriter.writeXMLFile(inFile, inShow);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testReadXMLFile_String() throws Exception {
        System.out.println("readXMLFile");
        String Path = "";
        Show expResult = null;
        Show result = TopLevelXMLWriter.readXMLFile(Path);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testReadXMLFile_File() throws Exception {
        System.out.println("readXMLFile");
        File inFile = null;
        Show expResult = null;
        Show result = TopLevelXMLWriter.readXMLFile(inFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testReadXMLFile_InputStream() throws Exception {
        System.out.println("readXMLFile");
        InputStream inFile = null;
        Show expResult = null;
        Show result = TopLevelXMLWriter.readXMLFile(inFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testReadXMLFile_BufferedInputStream() throws Exception {
        System.out.println("readXMLFile");
        BufferedInputStream inFile = null;
        Show expResult = null;
        Show result = TopLevelXMLWriter.readXMLFile(inFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testReadXMLFile_Reader() throws Exception {
        System.out.println("readXMLFile");
        Reader inFile = null;
        Show expResult = null;
        Show result = TopLevelXMLWriter.readXMLFile(inFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testReadXMLFile_BufferedReader() throws Exception {
        System.out.println("readXMLFile");
        BufferedReader inFile = null;
        Show expResult = null;
        Show result = TopLevelXMLWriter.readXMLFile(inFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testReadXMLFile_InputSource() throws Exception {
        System.out.println("readXMLFile");
        InputSource inFile = null;
        Show expResult = null;
        Show result = TopLevelXMLWriter.readXMLFile(inFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testWriteXMLFile_Show() throws Exception {
        System.out.println("writeXMLFile");
        Show inShow = null;
        TopLevelXMLWriter instance = null;
        instance.writeXMLFile(inShow);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readXMLFile method, of class TopLevelXMLWriter.
     */
    @Test
    public void testReadXMLFile() throws Exception {
        System.out.println("readXMLFile");
        TopLevelXMLWriter instance = null;
        Show expResult = null;
        Show result = instance.readXMLFile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
