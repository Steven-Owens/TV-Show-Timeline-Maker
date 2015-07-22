/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.character.NamedCharacter;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class CharacterRelationTest {
    private static final Logger LOG = Logger.getLogger(CharacterRelationTest.class.getName());

    @BeforeClass
    public static void setUpClass() {
        CharacterRelation.init();
        CharacterRelation.init2();
    }

    @AfterClass
    public static void tearDownClass() {
    }

        public CharacterRelationTest() {
        }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of cullDays method, of class AgeConstraint.
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testComplexApplyConstraint() {
        System.out.println("complexApplyConstraint");
        NamedCharacter testCharacter1 = new NamedCharacter("ann");
        NamedCharacter testCharacter2 = new NamedCharacter("bob");
        DateTime center = new DateTime(0, 1, 1, 1, 0, 0);
        center = center.withHourOfDay(12);
        for (CharacterRelation.CharacterRelationKind curCharacterRelationKind : CharacterRelation.CharacterRelationKind.values()) {
            System.out.println(curCharacterRelationKind.toString());
            testCharacter1.getBirthday().reset();
            testCharacter2.getBirthday().reset();
            int testLowerBound = 0;
            if (curCharacterRelationKind.getPeriod() != null) {
                testLowerBound = curCharacterRelationKind.getPeriod().get(org.joda.time.DurationFieldType.years());
            }
            int testUpperbound = java.lang.Integer.MAX_VALUE;
            if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.MOTHER) {
                testUpperbound = CharacterRelation.START_OF_MENOPAUSE;
            }
            CharacterRelation instance = new CharacterRelation(testCharacter1, testCharacter2, curCharacterRelationKind);
            testCharacter2.getBirthday().setEarliestPossibleDate(center.minusDays(2));
            testCharacter2.getBirthday().setLatestPossibleDate(center.plusDays(2));
            instance.applyConstraint();
            testCharacter2.getBirthday().setUpForComplexEval();
            testCharacter1.getBirthday().setUpForComplexEval();
            boolean expResult = false;
            boolean result = instance.complexApplyConstraint();
            assertEquals(expResult, result);
            if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.TWIN) {
                assertTrue(testCharacter1.getBirthday().getPossibleDays().containsAll(testCharacter2.getBirthday().getPossibleDays()));
                assertTrue(testCharacter2.getBirthday().getPossibleDays().containsAll(testCharacter1.getBirthday().getPossibleDays()));
                testCharacter1.getBirthday().getPossibleDays().add(center.minusDays(3).withHourOfDay(12));
                testCharacter1.getBirthday().getPossibleDays().add(center.plusDays(3).withHourOfDay(12));
            } else {
                if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.MOTHER) {
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().first().withHourOfDay(12), center.minusDays(2).minusYears(testUpperbound).withHourOfDay(12));
                    testCharacter1.getBirthday().getPossibleDays().add(center.minusDays(3).minusYears(testUpperbound).withHourOfDay(12));
                }
                if (curCharacterRelationKind.getPeriod() != null) {
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().last().withHourOfDay(12), center.plusDays(2).minusYears(testLowerBound).withHourOfDay(12));
                } else {
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().last().withHourOfDay(12), center.plusDays(1).withHourOfDay(12));
                }
                testCharacter1.getBirthday().getPossibleDays().add(center.plusDays(3).minusYears(testLowerBound).withHourOfDay(12));
            }
            expResult = true;
            result = instance.complexApplyConstraint();
            assertEquals(expResult, result);
            if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.TWIN) {
                assertTrue(testCharacter1.getBirthday().getPossibleDays().containsAll(testCharacter2.getBirthday().getPossibleDays()));
                assertTrue(testCharacter2.getBirthday().getPossibleDays().containsAll(testCharacter1.getBirthday().getPossibleDays()));
            } else {
                if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.MOTHER) {
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().first().withHourOfDay(12), center.minusDays(2).minusYears(testUpperbound).withHourOfDay(12));
                }
                if (curCharacterRelationKind.getPeriod() != null) {
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().last().withHourOfDay(12), center.plusDays(2).minusYears(testLowerBound).withHourOfDay(12));
                } else {
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().last().withHourOfDay(12), center.plusDays(1).withHourOfDay(12));
                }
            }
            DateTime newcenter = center.plusYears(2 * CharacterRelation.START_OF_MENOPAUSE);
            testCharacter1.getBirthday().setLatestPossibleDate(newcenter);
            testCharacter2.getBirthday().getPossibleDays().add(newcenter);
            testCharacter1.getBirthday().addPossibleDays();
            instance.complexApplyConstraint();
            if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.TWIN) {
                assertTrue(testCharacter1.getBirthday().getPossibleDays().containsAll(testCharacter2.getBirthday().getPossibleDays()));
                assertTrue(testCharacter2.getBirthday().getPossibleDays().containsAll(testCharacter1.getBirthday().getPossibleDays()));
            } else {
                if (curCharacterRelationKind.getPeriod() != null) {
                    assertTrue(testCharacter1.getBirthday().getPossibleDays().contains(center.plusDays(2).minusYears(testLowerBound).withHourOfDay(12)));
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().last().withHourOfDay(12), newcenter.minusYears(testLowerBound).withHourOfDay(12));
                } else {
                    assertTrue(testCharacter1.getBirthday().getPossibleDays().contains(center.plusDays(1).withHourOfDay(12)));
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().last().withHourOfDay(12), newcenter.minusDays(1).withHourOfDay(12));
                }
                if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.MOTHER) {
                    assertEquals(testCharacter1.getBirthday().getPossibleDays().first().withHourOfDay(12), center.minusDays(2).minusYears(testUpperbound).withHourOfDay(12));
                    assertTrue(testCharacter1.getBirthday().getPossibleDays().contains(newcenter.minusYears(testUpperbound).withHourOfDay(12)));
                    assertFalse(testCharacter1.getBirthday().getPossibleDays().contains(center.plusDays(3).minusYears(testLowerBound).withHourOfDay(12)));
                    assertFalse(testCharacter1.getBirthday().getPossibleDays().contains(newcenter.minusDays(1).minusYears(testUpperbound).withHourOfDay(12)));
                }
            }
        }
    }

    /**
     * Test of applyConstraint method, of class AgeConstraint.
     */
    @Test
    public void testApplyConstraint() {
        System.out.println("applyConstraint");
        NamedCharacter testCharacter1 = new NamedCharacter("ann");
        NamedCharacter testCharacter2 = new NamedCharacter("bob");
        DateTime center = new DateTime(0, 1, 1, 1, 0, 0);
        center = center.withHourOfDay(12);
        for (CharacterRelation.CharacterRelationKind curCharacterRelationKind : CharacterRelation.CharacterRelationKind.values()) {
            System.out.println(curCharacterRelationKind.toString());
            testCharacter1.getBirthday().reset();
            testCharacter2.getBirthday().reset();
            int testLowerBound = 0;
            if (curCharacterRelationKind.getPeriod() != null) {
                testLowerBound = curCharacterRelationKind.getPeriod().get(org.joda.time.DurationFieldType.years());
            }
            int testUpperbound = java.lang.Integer.MAX_VALUE;
            if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.MOTHER) {
                testUpperbound = CharacterRelation.START_OF_MENOPAUSE;
            }
            CharacterRelation instance = new CharacterRelation(testCharacter1, testCharacter2, curCharacterRelationKind);
            testCharacter2.getBirthday().setEarliestPossibleDate(center.minusDays(2));
            testCharacter2.getBirthday().setLatestPossibleDate(center.plusDays(2));
            instance.applyConstraint();
            if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.MOTHER) {
                assertEquals(testCharacter1.getBirthday().getEarliestPossibleDate(), center.minusDays(2).minusYears(testUpperbound).withHourOfDay(10));
            } else if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.TWIN) {
                assertEquals(testCharacter1.getBirthday().getEarliestPossibleDate(), testCharacter2.getBirthday().getEarliestPossibleDate());
            }
            if (curCharacterRelationKind.getPeriod() != null) {
                assertEquals(testCharacter1.getBirthday().getLatestPossibleDate(), center.plusDays(2).minusYears(testLowerBound).withHourOfDay(14));
            } else if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.TWIN) {
                assertEquals(testCharacter1.getBirthday().getLatestPossibleDate(), testCharacter2.getBirthday().getLatestPossibleDate());
            } else {
                assertEquals(testCharacter1.getBirthday().getLatestPossibleDate(), center.plusDays(1).withHourOfDay(14));
            }
            assertEquals(testCharacter2.getBirthday().getEarliestPossibleDate(), center.minusDays(2).withHourOfDay(10));
            assertEquals(testCharacter2.getBirthday().getLatestPossibleDate(), center.plusDays(2).withHourOfDay(14));
            testCharacter2.getBirthday().reset();
            testCharacter1.getBirthday().setEarliestPossibleDate(center);
            testCharacter1.getBirthday().setLatestPossibleDate(center);
            instance.applyConstraint();
            assertEquals(testCharacter1.getBirthday().getEarliestPossibleDate(), center.withHourOfDay(10));
            assertEquals(testCharacter1.getBirthday().getLatestPossibleDate(), center.withHourOfDay(14));
            if (curCharacterRelationKind.getPeriod() != null) {
                assertEquals(testCharacter2.getBirthday().getEarliestPossibleDate(), center.plusYears(testLowerBound).withHourOfDay(10));
            } else if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.TWIN) {
                assertEquals(testCharacter2.getBirthday().getEarliestPossibleDate(), testCharacter1.getBirthday().getEarliestPossibleDate());
            } else {
                assertEquals(testCharacter2.getBirthday().getEarliestPossibleDate(), center.plusDays(1).withHourOfDay(10));
            }
            if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.MOTHER) {
                assertEquals(testCharacter2.getBirthday().getLatestPossibleDate(), center.plusYears(testUpperbound).withHourOfDay(14));
            } else if (curCharacterRelationKind == CharacterRelation.CharacterRelationKind.TWIN) {
                assertEquals(testCharacter2.getBirthday().getLatestPossibleDate(), testCharacter1.getBirthday().getLatestPossibleDate());
            }
        }
    }

    /**
     * Test of getFirstNamedCharacter method, of class CharacterRelation.
     */
    @Test
    public void testGetFirstNamedCharacter() {
        System.out.println("getFirstNamedCharacter");
        NamedCharacter testCharacter1 = new NamedCharacter("ann");
        NamedCharacter testCharacter2 = new NamedCharacter("bob");
        CharacterRelation instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.OLDER_SIBLING);
        NamedCharacter expResult = testCharacter1;
        NamedCharacter result = instance.getFirstNamedCharacter();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSecondNamedCharacter method, of class CharacterRelation.
     */
    @Test
    public void testGetSecondNamedCharacter() {
        System.out.println("getSecondNamedCharacter");
        NamedCharacter testCharacter1 = new NamedCharacter("ann");
        NamedCharacter testCharacter2 = new NamedCharacter("bob");
        CharacterRelation instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.OLDER_SIBLING);
        NamedCharacter expResult = testCharacter2;
        NamedCharacter result = instance.getSecondNamedCharacter();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCharacterRelationKind method, of class CharacterRelation.
     */
    @Test
    public void testGetCharacterRelationKind() {
        System.out.println("getCharacterRelationKind");
        NamedCharacter testCharacter1 = new NamedCharacter("ann");
        NamedCharacter testCharacter2 = new NamedCharacter("bob");
        CharacterRelation instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.OLDER_SIBLING);
        CharacterRelation.CharacterRelationKind expResult = CharacterRelation.CharacterRelationKind.OLDER_SIBLING;
        CharacterRelation.CharacterRelationKind result = instance.getCharacterRelationKind();
        assertEquals(expResult, result);
    }

    /**
     * Test of isStrict method, of class CharacterRelation.
     */
    @Test
    public void testIsStrict() {
        System.out.println("isStrict");
        NamedCharacter testCharacter1 = new NamedCharacter("ann");
        NamedCharacter testCharacter2 = new NamedCharacter("bob");
        CharacterRelation instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.MOTHER);
        boolean expResult = true;
        boolean result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.GRANDPARENT);
        expResult = false;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.GREAT_GRANDPARENT);
        expResult = false;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.OLDER_SIBLING);
        expResult = false;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.PARENT);
        expResult = false;
        result = instance.isStrict();
        assertEquals(expResult, result);
        instance = new CharacterRelation(testCharacter1,testCharacter2,CharacterRelation.CharacterRelationKind.TWIN);
        expResult = true;
        result = instance.isStrict();
        assertEquals(expResult, result);
    }

    /**
     * Test of consistentWithConstraint method, of class CharacterRelation.
     */
    @Test
    public void testConsistentWithConstraint_DateTime_DateTime() {
        /*System.out.println("consistentWithConstraint");
        DateTime inFirstDay = null;
        DateTime inSecondDay = null;
        CharacterRelation instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inFirstDay, inSecondDay);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of ConstraintSatisfied method, of class CharacterRelation.
     */
    @Test
    public void testConstraintSatisfied() {
        System.out.println("ConstraintSatisfied");
        CharacterRelation instance = null;
        boolean expResult = false;
        boolean result = instance.ConstraintSatisfied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consistentWithConstraint method, of class CharacterRelation.
     */
    @Test
    public void testConsistentWithConstraint_PlacementArr() {
        /*System.out.println("consistentWithConstraint");
        Placement[] inValues = null;
        CharacterRelation instance = null;
        boolean expResult = false;
        boolean result = instance.consistentWithConstraint(inValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of increaseWhat method, of class CharacterRelation.
     */
    @Test
    public void testIncreaseWhat() {
        /*System.out.println("increaseWhat");
        Placement[] inValues = null;
        CharacterRelation instance = null;
        Event[] expResult = null;
        Event[] result = instance.increaseWhat(inValues);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

}
