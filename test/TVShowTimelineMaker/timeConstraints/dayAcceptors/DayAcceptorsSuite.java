/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints.dayAcceptors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Steven Owens
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TVShowTimelineMaker.timeConstraints.dayAcceptors.SameTimeAsDayAcceptorTest.class, TVShowTimelineMaker.timeConstraints.dayAcceptors.DayAcceptorTest.class, TVShowTimelineMaker.timeConstraints.dayAcceptors.SeasonDayAcceptorTest.class, TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptorTest.class, TVShowTimelineMaker.timeConstraints.dayAcceptors.NonoverlapingDayAcceptorTest.class, TVShowTimelineMaker.timeConstraints.dayAcceptors.NotDayAcceptorTest.class, TVShowTimelineMaker.timeConstraints.dayAcceptors.ContainsDayAcceptorTest.class, TVShowTimelineMaker.timeConstraints.dayAcceptors.PartialDayAcceptorTest.class})
public class DayAcceptorsSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
