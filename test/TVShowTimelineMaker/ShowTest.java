/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeline.Episode;
import TVShowTimelineMaker.timeline.Timeline;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ShowTest {
    private static final Logger LOG = Logger.getLogger(ShowTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public ShowTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addEpisode method, of class Show.
     */
    @Test
    public void testAddEpisode() {
        System.out.println("addEpisode");
        Episode inEpisode = new Episode();
        Show instance = new Show("");
        boolean expResult = true;
        boolean result = instance.addEpisode(inEpisode);
        assertEquals(expResult, result);
        assertTrue(instance.getEpisodes().contains(inEpisode));
    }

    /**
     * Test of removeEpisode method, of class Show.
     */
    @Test
    public void testRemoveEpisode() {
        System.out.println("removeEpisode");
        Episode inEpisode = new Episode();
        Show instance = new Show("");
        boolean expResult = false;
        boolean result = instance.removeEpisode(inEpisode);
        assertEquals(expResult, result);
        assertTrue(instance.addEpisode(inEpisode));
        expResult = true;
        result = instance.removeEpisode(inEpisode);
        assertEquals(expResult, result);
        assertFalse(instance.getEpisodes().contains(inEpisode));
    }

    /**
     * Test of addCharacter method, of class Show.
     */
    @Test
    public void testAddCharacter() {
        System.out.println("addCharacter");
        NamedCharacter inCharacter = new NamedCharacter();
        Show instance = new Show("");
        boolean expResult = true;
        boolean result = instance.addCharacter(inCharacter);
        assertEquals(expResult, result);
        assertTrue(instance.getCharacters().contains(inCharacter));
    }

    /**
     * Test of removeCharacter method, of class Show.
     */
    @Test
    public void testRemoveCharacter() {
        System.out.println("removeCharacter");
        NamedCharacter inCharacter = new NamedCharacter();
        Show instance = new Show("");
        boolean expResult = false;
        boolean result = instance.removeCharacter(inCharacter);
        assertEquals(expResult, result);
        assertTrue(instance.addCharacter(inCharacter));
        expResult = true;
        result = instance.removeCharacter(inCharacter);
        assertEquals(expResult, result);
        assertFalse(instance.getCharacters().contains(inCharacter));
    }

    /**
     * Test of getTimeLine method, of class Show.
     */
    @Test
    public void testGetTimeLine() {
        System.out.println("getTimeLine");
        Show instance = new Show("");
        Timeline result = instance.getTimeLine();
        assertNotNull(result);
    }
    
}
