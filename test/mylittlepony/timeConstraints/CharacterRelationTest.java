/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylittlepony.timeConstraints;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeConstraints.CharacterRelation;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Steven Owens
 */
public class CharacterRelationTest {

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
    public void testCullDays() {
        System.out.println("cullDays");
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

}
