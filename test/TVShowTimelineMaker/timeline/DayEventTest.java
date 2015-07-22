/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import java.util.Collection;
import java.util.List;
import java.util.NavigableSet;
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
public class DayEventTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public DayEventTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPossibleDays method, of class DayEvent.
     */
    @Test
    public void testGetPossibleDays() {
        System.out.println("getPossibleDays");
        DayEvent instance = new DayEventImpl();
        NavigableSet expResult = null;
        NavigableSet result = instance.getPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDayAcceptor method, of class DayEvent.
     */
    @Test
    public void testGetDayAcceptor() {
        System.out.println("getDayAcceptor");
        DayEvent instance = new DayEventImpl();
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSuggestedDay method, of class DayEvent.
     */
    @Test
    public void testAddSuggestedDay() {
        System.out.println("addSuggestedDay");
        DateTime inDate = null;
        DayEvent instance = new DayEventImpl();
        boolean expResult = false;
        boolean result = instance.addSuggestedDay(inDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeSuggestedDay method, of class DayEvent.
     */
    @Test
    public void testRemoveSuggestedDay() {
        System.out.println("removeSuggestedDay");
        Object inDate = null;
        DayEvent instance = new DayEventImpl();
        boolean expResult = false;
        boolean result = instance.removeSuggestedDay(inDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuggestedDays method, of class DayEvent.
     */
    @Test
    public void testGetSuggestedDays() {
        System.out.println("getSuggestedDays");
        DayEvent instance = new DayEventImpl();
        Collection expResult = null;
        Collection result = instance.getSuggestedDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class DayEventImpl<T> implements DayEvent<T> {

        public NavigableSet<T> getPossibleDays() {
            return null;
        }

        public AndDayAcceptor getDayAcceptor() {
            return null;
        }

        public boolean addSuggestedDay(DateTime inDate) {
            return false;
        }

        public boolean removeSuggestedDay(T inDate) {
            return false;
        }

        public Collection<T> getSuggestedDays() {
            return null;
        }

        @Override
        public void reset() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isValid() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getLeastSignificantIDPart() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setName(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public long getLastmodifyed() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void normalize() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String toLongString() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double sizeAdj() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String toTimeFrameString() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isMarkedForComplexEval() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setUpForComplexEval() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<? extends Placement> getPlacements() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setTo(Collection<? extends  Placement> inPlacements) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int compareTo(Event o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setTo(Placement inPlacement) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public List<? extends Placement> getSuggestedPlacements() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
