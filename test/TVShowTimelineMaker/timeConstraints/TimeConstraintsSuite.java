/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.timeConstraints;

import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TVShowTimelineMaker.timeConstraints.RelationTest.class, TVShowTimelineMaker.timeConstraints.IntervalConstraintTest.class, TVShowTimelineMaker.timeConstraints.AgeGroupConstraintTest.class, TVShowTimelineMaker.timeConstraints.ContainsConstraintTest.class, TVShowTimelineMaker.timeConstraints.SeasonTimeConstraintTest.class, TVShowTimelineMaker.timeConstraints.AgeConstraintTest.class, TVShowTimelineMaker.timeConstraints.IntervalRelationTest.class, TVShowTimelineMaker.timeConstraints.TwoEventTimeConstraintTest.class, TVShowTimelineMaker.timeConstraints.PartialTimeConstraintTest.class, TVShowTimelineMaker.timeConstraints.TwoOnceDayEventTimeConstraintTest.class, TVShowTimelineMaker.timeConstraints.dayAcceptors.DayAcceptorsSuite.class, TVShowTimelineMaker.timeConstraints.PeriodToDayConstraintTest.class, TVShowTimelineMaker.timeConstraints.TimeConstraintTest.class, TVShowTimelineMaker.timeConstraints.AbutsContraintTest.class, TVShowTimelineMaker.timeConstraints.CharacterRelationTest.class})
public class TimeConstraintsSuite {
    private static final Logger LOG = Logger.getLogger(TimeConstraintsSuite.class.getName());

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
