/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.ContainsDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.NonoverlapingDayAcceptor;
import TVShowTimelineMaker.util.DayOfYear;
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
public class PeriodEventTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public PeriodEventTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMinDuration method, of class PeriodEvent.
     */
    @Test
    public void testGetMinDuration() {
        System.out.println("getMinDuration");
        PeriodEvent instance = new PeriodEventImpl();
        int expResult = 0;
        int result = instance.getMinDuration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinDuration method, of class PeriodEvent.
     */
    @Test
    public void testSetMinDuration() {
        System.out.println("setMinDuration");
        int minDuration = 0;
        PeriodEvent instance = new PeriodEventImpl();
        instance.setMinDuration(minDuration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxDuration method, of class PeriodEvent.
     */
    @Test
    public void testGetMaxDuration() {
        System.out.println("getMaxDuration");
        PeriodEvent instance = new PeriodEventImpl();
        int expResult = 0;
        int result = instance.getMaxDuration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMaxDuration method, of class PeriodEvent.
     */
    @Test
    public void testSetMaxDuration() {
        System.out.println("setMaxDuration");
        int maxDuration = 0;
        PeriodEvent instance = new PeriodEventImpl();
        instance.setMaxDuration(maxDuration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStart method, of class PeriodEvent.
     */
    @Test
    public void testGetStart() {
        System.out.println("getStart");
        PeriodEvent instance = new PeriodEventImpl();
        DayEvent expResult = null;
        DayEvent result = instance.getStart();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnd method, of class PeriodEvent.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        PeriodEvent instance = new PeriodEventImpl();
        DayEvent expResult = null;
        DayEvent result = instance.getEnd();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartDayAcceptor method, of class PeriodEvent.
     */
    @Test
    public void testGetStartDayAcceptor() {
        System.out.println("getStartDayAcceptor");
        PeriodEvent instance = new PeriodEventImpl();
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getStartDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndDayAcceptor method, of class PeriodEvent.
     */
    @Test
    public void testGetEndDayAcceptor() {
        System.out.println("getEndDayAcceptor");
        PeriodEvent instance = new PeriodEventImpl();
        AndDayAcceptor expResult = null;
        AndDayAcceptor result = instance.getEndDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartPossibleDays method, of class PeriodEvent.
     */
    @Test
    public void testGetStartPossibleDays() {
        System.out.println("getStartPossibleDays");
        PeriodEvent instance = new PeriodEventImpl();
        NavigableSet expResult = null;
        NavigableSet result = instance.getStartPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndPossibleDays method, of class PeriodEvent.
     */
    @Test
    public void testGetEndPossibleDays() {
        System.out.println("getEndPossibleDays");
        PeriodEvent instance = new PeriodEventImpl();
        NavigableSet expResult = null;
        NavigableSet result = instance.getEndPossibleDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContainsDayAcceptor method, of class PeriodEvent.
     */
    @Test
    public void testGetContainsDayAcceptor() {
        System.out.println("getContainsDayAcceptor");
        PeriodEvent instance = new PeriodEventImpl();
        ContainsDayAcceptor expResult = null;
        ContainsDayAcceptor result = instance.getContainsDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNonoverlapingDayAcceptor method, of class PeriodEvent.
     */
    @Test
    public void testGetNonoverlapingDayAcceptor() {
        System.out.println("getNonoverlapingDayAcceptor");
        PeriodEvent instance = new PeriodEventImpl();
        NonoverlapingDayAcceptor expResult = null;
        NonoverlapingDayAcceptor result = instance.getNonoverlapingDayAcceptor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsCould method, of class PeriodEvent.
     */
    @Test
    public void testContainsCould_DateTime() {
        System.out.println("containsCould");
        DateTime inDateTime = null;
        PeriodEvent instance = new PeriodEventImpl();
        boolean expResult = false;
        boolean result = instance.containsCould(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsCould method, of class PeriodEvent.
     */
    @Test
    public void testContainsCould_DayOfYear() {
        System.out.println("containsCould");
        DayOfYear inDateTime = null;
        PeriodEvent instance = new PeriodEventImpl();
        boolean expResult = false;
        boolean result = instance.containsCould(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsSure method, of class PeriodEvent.
     */
    @Test
    public void testContainsSure_DateTime() {
        System.out.println("containsSure");
        DateTime inDateTime = null;
        PeriodEvent instance = new PeriodEventImpl();
        boolean expResult = false;
        boolean result = instance.containsSure(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsSure method, of class PeriodEvent.
     */
    @Test
    public void testContainsSure_DayOfYear() {
        System.out.println("containsSure");
        DayOfYear inDateTime = null;
        PeriodEvent instance = new PeriodEventImpl();
        boolean expResult = false;
        boolean result = instance.containsSure(inDateTime);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abutsBefore method, of class PeriodEvent.
     */
    @Test
    public void testAbutsBefore() {
        System.out.println("abutsBefore");
        PeriodEvent inPeriodEvent = null;
        PeriodEvent instance = new PeriodEventImpl();
        boolean expResult = false;
        boolean result = instance.abutsBefore(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abutsAfter method, of class PeriodEvent.
     */
    @Test
    public void testAbutsAfter() {
        System.out.println("abutsAfter");
        PeriodEvent inPeriodEvent = null;
        PeriodEvent instance = new PeriodEventImpl();
        boolean expResult = false;
        boolean result = instance.abutsAfter(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAbutsBefore method, of class PeriodEvent.
     */
    @Test
    public void testSetAbutsBefore() {
        System.out.println("setAbutsBefore");
        PeriodEvent inPeriodEvent = null;
        PeriodEvent instance = new PeriodEventImpl();
        boolean expResult = false;
        boolean result = instance.setAbutsBefore(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAbutsAfter method, of class PeriodEvent.
     */
    @Test
    public void testSetAbutsAfter() {
        System.out.println("setAbutsAfter");
        PeriodEvent inPeriodEvent = null;
        PeriodEvent instance = new PeriodEventImpl();
        boolean expResult = false;
        boolean result = instance.setAbutsAfter(inPeriodEvent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class PeriodEventImpl<T> implements PeriodEvent<T> {

        public int getMinDuration() {
            return 0;
        }

        public void setMinDuration(int minDuration) {
        }

        public int getMaxDuration() {
            return 0;
        }

        public void setMaxDuration(int maxDuration) {
        }

        public DayEvent getStart() {
            return null;
        }

        public DayEvent getEnd() {
            return null;
        }

        public AndDayAcceptor getStartDayAcceptor() {
            return null;
        }

        public AndDayAcceptor getEndDayAcceptor() {
            return null;
        }

        public NavigableSet<T> getStartPossibleDays() {
            return null;
        }

        public NavigableSet<T> getEndPossibleDays() {
            return null;
        }

        public ContainsDayAcceptor getContainsDayAcceptor() {
            return null;
        }

        public NonoverlapingDayAcceptor getNonoverlapingDayAcceptor() {
            return null;
        }

        public boolean containsCould(DateTime inDateTime) {
            return false;
        }

        public boolean containsCould(DayOfYear inDateTime) {
            return false;
        }

        public boolean containsSure(DateTime inDateTime) {
            return false;
        }

        public boolean containsSure(DayOfYear inDateTime) {
            return false;
        }

        public boolean abutsBefore(TVShowTimelineMaker.ui.Event.PeriodEventEditor inPeriodEvent) {
            return false;
        }

        public boolean abutsAfter(PeriodEvent inPeriodEvent) {
            return false;
        }

        public boolean setAbutsBefore(PeriodEvent inPeriodEvent) {
            return false;
        }

        public boolean setAbutsAfter(PeriodEvent inPeriodEvent) {
            return false;
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
