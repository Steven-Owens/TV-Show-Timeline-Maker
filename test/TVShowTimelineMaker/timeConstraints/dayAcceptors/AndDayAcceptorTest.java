/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import TVShowTimelineMaker.testUtil.DateTimeMaker;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.Season;
import java.util.Collection;
import org.joda.time.DateTime;
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
public class AndDayAcceptorTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    public AndDayAcceptorTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDayAcceptorOfKind method, of class AndDayAcceptor.
     */
    @Test
    public void testGetDayAcceptorOfKind() {
        System.out.println("getDayAcceptorOfKind");
        OncePeriodEvent tempOncePeriodEvent = new OncePeriodEvent();
        ContainsDayAcceptor mContainsDayAcceptor = tempOncePeriodEvent.getContainsDayAcceptor();
        NonoverlapingDayAcceptor mNonoverlapingDayAcceptor = tempOncePeriodEvent.getNonoverlapingDayAcceptor();
        NotDayAcceptor mNotDayAcceptor = new NotDayAcceptor(mContainsDayAcceptor);
        PartialDayAcceptor mPartialDayAcceptor = new PartialDayAcceptor(new Partial());
        SameTimeAsDayAcceptor mSameTimeAsDayAcceptor = new SameTimeAsDayAcceptor();
        SeasonDayAcceptor mSeasonDayAcceptor = new SeasonDayAcceptor(Season.SPRING);
        AndDayAcceptor mAndDayAcceptor = new AndDayAcceptor();
        AndDayAcceptor instance = new AndDayAcceptor();
        instance.add(mContainsDayAcceptor);
        instance.add(mNonoverlapingDayAcceptor);
        instance.add(mNotDayAcceptor);
        instance.add(mPartialDayAcceptor);
        instance.add(mSameTimeAsDayAcceptor);
        instance.add(mSeasonDayAcceptor);
        Object expResult = null;
        Object result = instance.getDayAcceptorOfKind(AndDayAcceptor.class);
        assertEquals(expResult, result);
        instance.add(mAndDayAcceptor);
        expResult = mContainsDayAcceptor;
        result = instance.getDayAcceptorOfKind(ContainsDayAcceptor.class);
        assertEquals(expResult, result);
        expResult = mNonoverlapingDayAcceptor;
        result = instance.getDayAcceptorOfKind(NonoverlapingDayAcceptor.class);
        assertEquals(expResult, result);
        expResult = mNotDayAcceptor;
        result = instance.getDayAcceptorOfKind(NotDayAcceptor.class);
        assertEquals(expResult, result);
        expResult = mPartialDayAcceptor;
        result = instance.getDayAcceptorOfKind(PartialDayAcceptor.class);
        assertEquals(expResult, result);
        expResult = mSameTimeAsDayAcceptor;
        result = instance.getDayAcceptorOfKind(SameTimeAsDayAcceptor.class);
        assertEquals(expResult, result);
        expResult = mSeasonDayAcceptor;
        result = instance.getDayAcceptorOfKind(SeasonDayAcceptor.class);
        assertEquals(expResult, result);
        expResult = mAndDayAcceptor;
        result = instance.getDayAcceptorOfKind(AndDayAcceptor.class);
        assertEquals(expResult, result);
    }

    /**
     * Test of accept method, of class AndDayAcceptor.
     */
    @Test
    public void testAccept_DateTime() {
        System.out.println("accept");
        DateTime inDateTime = new DateTime(0,1,1,1,0,0);
        DayAcceptor part1 = new DayAcceptor(){

            @Override
            public boolean accept(DateTime inDateTime) {
                return inDateTime.getDayOfMonth() == 1;
            }

            @Override
            public boolean accept(DayOfYear inDateTime) {
                return inDateTime.getDay() == 1;
            }

            @Override
            public double divFactor() {
                return 1/30;
            }
            
        };
        DayAcceptor part2 = new DayAcceptor(){
            @Override
            public boolean accept(DateTime inDateTime) {
                return inDateTime.getMonthOfYear() == 1;
            }

            @Override
            public boolean accept(DayOfYear inDateTime) {
                return inDateTime.getMonth() == 1;
            }

            @Override
            public double divFactor() {
                return 1/12;
            }
        };
        AndDayAcceptor instance = new AndDayAcceptor();
        instance.add(part1);
        instance.add(part2);
        boolean expResult = true;
        boolean result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        inDateTime = new DateTime(0,1,2,1,0,0);
        expResult = false;
        result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        inDateTime = new DateTime(0,2,1,1,0,0);
        expResult = false;
        result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        for (int i = 0; i < 1000; i++){
            inDateTime = DateTimeMaker.randomDateTime();
        expResult = part1.accept(inDateTime) && part2.accept(inDateTime);
        result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of accept method, of class AndDayAcceptor.
     */
    @Test
    public void testAccept_DayOfYear() {
        System.out.println("accept");
        DayOfYear inDateTime = new DayOfYear(1,1);
        DayAcceptor part1 = new DayAcceptor(){

            @Override
            public boolean accept(DateTime inDateTime) {
                return inDateTime.getDayOfMonth() == 1;
            }

            @Override
            public boolean accept(DayOfYear inDateTime) {
                return inDateTime.getDay() == 1;
            }

            @Override
            public double divFactor() {
                return 1/30;
            }
            
        };
        DayAcceptor part2 = new DayAcceptor(){
            @Override
            public boolean accept(DateTime inDateTime) {
                return inDateTime.getMonthOfYear() == 1;
            }

            @Override
            public boolean accept(DayOfYear inDateTime) {
                return inDateTime.getMonth() == 1;
            }

            @Override
            public double divFactor() {
                return 1/12;
            }
        };
        AndDayAcceptor instance = new AndDayAcceptor();
        instance.add(part1);
        instance.add(part2);
        boolean expResult = true;
        boolean result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        inDateTime = new DayOfYear(1,2);
        expResult = false;
        result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        inDateTime = new DayOfYear(2,1);
        expResult = false;
        result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        for (int i = 0; i < 1000; i++){
            inDateTime = DayOfYear.fromDateTime(DateTimeMaker.randomDateTime());
        expResult = part1.accept(inDateTime) && part2.accept(inDateTime);
        result = instance.accept(inDateTime);
        assertEquals(expResult, result);
        }
    }

    /**
     * Test of divFactor method, of class AndDayAcceptor.
     */
    @Test
    public void testDivFactor() {
        System.out.println("divFactor");
        DayAcceptor part1 = new DayAcceptor(){

            @Override
            public boolean accept(DateTime inDateTime) {
                return inDateTime.getDayOfMonth() == 1;
            }

            @Override
            public boolean accept(DayOfYear inDateTime) {
                return inDateTime.getDay() == 1;
            }

            @Override
            public double divFactor() {
                return 1/30;
            }
            
        };
        DayAcceptor part2 = new DayAcceptor(){
            @Override
            public boolean accept(DateTime inDateTime) {
                return inDateTime.getMonthOfYear() == 1;
            }

            @Override
            public boolean accept(DayOfYear inDateTime) {
                return inDateTime.getMonth() == 1;
            }

            @Override
            public double divFactor() {
                return 1/12;
            }
        };
        AndDayAcceptor instance = new AndDayAcceptor();
        instance.add(part1);
        instance.add(part2);
        double expResult = part1.divFactor() * part2.divFactor();
        double result = instance.divFactor();
        assertEquals(expResult, result, 0.0001);
    }

    /**
     * Test of size method, of class AndDayAcceptor.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        OncePeriodEvent tempOncePeriodEvent = new OncePeriodEvent();
        ContainsDayAcceptor mContainsDayAcceptor = tempOncePeriodEvent.getContainsDayAcceptor();
        NonoverlapingDayAcceptor mNonoverlapingDayAcceptor = tempOncePeriodEvent.getNonoverlapingDayAcceptor();
        NotDayAcceptor mNotDayAcceptor = new NotDayAcceptor(mContainsDayAcceptor);
        PartialDayAcceptor mPartialDayAcceptor = new PartialDayAcceptor(new Partial());
        SameTimeAsDayAcceptor mSameTimeAsDayAcceptor = new SameTimeAsDayAcceptor();
        SeasonDayAcceptor mSeasonDayAcceptor = new SeasonDayAcceptor(Season.SPRING);
        AndDayAcceptor mAndDayAcceptor = new AndDayAcceptor();
        AndDayAcceptor instance = new AndDayAcceptor();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        instance.add(mContainsDayAcceptor);
        expResult++;
        result = instance.size();
        assertEquals(expResult, result);
        instance.add(mNonoverlapingDayAcceptor);
        expResult++;
        result = instance.size();
        assertEquals(expResult, result);
        instance.add(mNotDayAcceptor);
        expResult++;
        result = instance.size();
        assertEquals(expResult, result);
        instance.add(mPartialDayAcceptor);
        expResult++;
        result = instance.size();
        assertEquals(expResult, result);
        instance.add(mSameTimeAsDayAcceptor);
        expResult++;
        result = instance.size();
        assertEquals(expResult, result);
        instance.add(mSeasonDayAcceptor);
        expResult++;
        result = instance.size();
        assertEquals(expResult, result);
        instance.add(mAndDayAcceptor);
        expResult++;
        result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class AndDayAcceptor.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        AndDayAcceptor instance = new AndDayAcceptor();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        AndDayAcceptor mAndDayAcceptor = new AndDayAcceptor();
        instance.add(mAndDayAcceptor);
        expResult = false;
        result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class AndDayAcceptor.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        OncePeriodEvent tempOncePeriodEvent = new OncePeriodEvent();
        ContainsDayAcceptor mContainsDayAcceptor = tempOncePeriodEvent.getContainsDayAcceptor();
        NonoverlapingDayAcceptor mNonoverlapingDayAcceptor = tempOncePeriodEvent.getNonoverlapingDayAcceptor();
        AndDayAcceptor instance = new AndDayAcceptor();
        instance.add(mContainsDayAcceptor);
        boolean expResult = false;
        boolean result = instance.contains(mNonoverlapingDayAcceptor);
        assertEquals(expResult, result);
        expResult = true;
        result = instance.contains(mContainsDayAcceptor);
        assertEquals(expResult, result);
    }

    /**
     * Test of iterator method, of class AndDayAcceptor.
     */
    @Test
    public void testIterator() {
        System.out.println("iterator");
        /*AndDayAcceptor instance = new AndDayAcceptor();
        Iterator<DayAcceptor> expResult = null;
        Iterator<DayAcceptor> result = instance.iterator();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of toArray method, of class AndDayAcceptor.
     */
    @Test
    public void testToArray_0args() {
        System.out.println("toArray");
        /*AndDayAcceptor instance = new AndDayAcceptor();
        Object[] expResult = null;
        Object[] result = instance.toArray();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of toArray method, of class AndDayAcceptor.
     */
    @Test
    public void testToArray_GenericType() {
        System.out.println("toArray");
        /*Object[] a = null;
        AndDayAcceptor instance = new AndDayAcceptor();
        Object[] expResult = null;
        Object[] result = instance.toArray(a);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of add method, of class AndDayAcceptor.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        DayAcceptor e = new AndDayAcceptor();
        AndDayAcceptor instance = new AndDayAcceptor();
        boolean expResult = false;
        boolean result = instance.contains(e);
        assertEquals(expResult, result);
        expResult = true;
        result = instance.add(e);
        assertEquals(expResult, result);
        expResult = true;
        result = instance.contains(e);
        assertEquals(expResult, result);
    }

    /**
     * Test of remove method, of class AndDayAcceptor.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        DayAcceptor e = new AndDayAcceptor();
        AndDayAcceptor instance = new AndDayAcceptor();
        instance.add(e);
        boolean expResult = true;
        boolean result = instance.contains(e);
        assertEquals(expResult, result);
        expResult = true;
        result = instance.remove(e);
        assertEquals(expResult, result);
        expResult = false;
        result = instance.contains(e);
        assertEquals(expResult, result);
        expResult = false;
        result = instance.remove(e);
        assertEquals(expResult, result);
    }

    /**
     * Test of containsAll method, of class AndDayAcceptor.
     */
    @Test
    public void testContainsAll() {
        System.out.println("containsAll");
        Collection c = null;
        AndDayAcceptor instance = new AndDayAcceptor();
        boolean expResult = false;
        boolean result = instance.containsAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAll method, of class AndDayAcceptor.
     */
    @Test
    public void testAddAll() {
        System.out.println("addAll");
        Collection<? extends DayAcceptor> c = null;
        AndDayAcceptor instance = new AndDayAcceptor();
        boolean expResult = false;
        boolean result = instance.addAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAll method, of class AndDayAcceptor.
     */
    @Test
    public void testRemoveAll() {
        System.out.println("removeAll");
        Collection c = null;
        AndDayAcceptor instance = new AndDayAcceptor();
        boolean expResult = false;
        boolean result = instance.removeAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retainAll method, of class AndDayAcceptor.
     */
    @Test
    public void testRetainAll() {
        System.out.println("retainAll");
        Collection c = null;
        AndDayAcceptor instance = new AndDayAcceptor();
        boolean expResult = false;
        boolean result = instance.retainAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class AndDayAcceptor.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        AndDayAcceptor instance = new AndDayAcceptor();
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
