/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.character;

import TVShowTimelineMaker.timeline.OnceDayEvent;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class NamedCharacterTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public NamedCharacterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class NamedCharacter.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "test";
        NamedCharacter instance = new NamedCharacter(expResult);
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class NamedCharacter.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "test";
        NamedCharacter instance = new NamedCharacter(expResult);
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBirthday method, of class NamedCharacter.
     */
    @Test
    public void testGetBirthday() {
        System.out.println("getBirthday");
        NamedCharacter instance = new NamedCharacter();
        OnceDayEvent result = instance.getBirthday();
        assertNotNull(result);
    }

    /**
     * Test of setName method, of class NamedCharacter.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "test";
        NamedCharacter instance = new NamedCharacter();
        instance.setName(name);
        String result = instance.getName();
        assertEquals(name, result);
    }
}
