/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.ReadablePeriod;


public class CharacterRelation extends Relation<OnceDayEvent.OnceDayEventPlacement,OnceDayEvent.OnceDayEventPlacement> {

    private static final long serialVersionUID = 6L;

    public static int START_OF_PUBERTY = 13;
    public static int START_OF_MENOPAUSE = 61;
    private static final Logger LOG = Logger.getLogger(CharacterRelation.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init2() {
        MyLittePonyMaps.putConstraint("CharacterRelation", CharacterRelation.class);
    }

    private final NamedCharacter mFirstNamedCharacter;
    private final NamedCharacter mSecondNamedCharacter;
    private final CharacterRelationKind mCharacterRelationKind;

    public CharacterRelation(NamedCharacter inFirstNamedCharacter, NamedCharacter inSecondNamedCharacter, CharacterRelationKind inCharacterRelationKind) {
        super(inFirstNamedCharacter.getBirthday(),
                inSecondNamedCharacter.getBirthday(), inCharacterRelationKind.getCharacterRelationKind(),
                inCharacterRelationKind.getPeriod());
        this.mFirstNamedCharacter = inFirstNamedCharacter;
        this.mSecondNamedCharacter = inSecondNamedCharacter;
        this.mCharacterRelationKind = inCharacterRelationKind;
    }
    
    public CharacterRelation(Element root){
        super(root,
                NamedCharacter.NamedCharacterIDXMLWriter.instance.readElements(root.getChild("firstCharacterByID")).getBirthday(),
                NamedCharacter.NamedCharacterIDXMLWriter.instance.readElements(root.getChild("secondCharacterByID")).getBirthday(),
                CharacterRelationKind.valueOf(root.getChild("kind").getTextNormalize()).getCharacterRelationKind(),
                CharacterRelationKind.valueOf(root.getChild("kind").getTextNormalize()).getPeriod());
        Element firstCharacterElement = root.getChild("firstCharacterByID");
            this.mFirstNamedCharacter = NamedCharacter.NamedCharacterIDXMLWriter.instance.readElements(firstCharacterElement);
            Element secondCharacterElement = root.getChild("secondCharacterByID");
            this.mSecondNamedCharacter = NamedCharacter.NamedCharacterIDXMLWriter.instance.readElements(secondCharacterElement);
            Element kindElement = root.getChild("kind");
            this.mCharacterRelationKind = CharacterRelationKind.valueOf(kindElement.getTextNormalize());
    }

    /**
     * @return the mFirstNamedCharacter
     */
    public NamedCharacter getFirstNamedCharacter() {
        return this.mFirstNamedCharacter;
    }

    /**
     * @return the mSecondNamedCharacter
     */
    public NamedCharacter getSecondNamedCharacter() {
        return this.mSecondNamedCharacter;
    }

    /**
     * @return the mCharacterRelationKind
     */
    public CharacterRelationKind getCharacterRelationKind() {
        return this.mCharacterRelationKind;
    }

    @Override
    public boolean isStrict() {
        boolean rvalue = super.isStrict();
        if (this.mCharacterRelationKind.equals(CharacterRelationKind.MOTHER)) {
            rvalue = true;
        }
        return rvalue;
    }

    @Override
    public boolean consistentWithConstraint(DateTime inFirstDay, DateTime inSecondDay) {
        boolean con = super.consistentWithConstraint(inFirstDay, inSecondDay);
        if (con) {
            if (this.mCharacterRelationKind.equals(CharacterRelationKind.MOTHER)) {
                DateTime lowerBoundOnBirthDay1 = inSecondDay.minusYears(START_OF_MENOPAUSE).withHourOfDay(1);
                DateTime upperBoundOnBirthDay1 = inSecondDay.minus(this.mCharacterRelationKind.mPeriod).withHourOfDay(23);
                con = inFirstDay.isAfter(lowerBoundOnBirthDay1) && inFirstDay.isBefore(upperBoundOnBirthDay1);
            }
        }
        return con;
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = super.complexApplyConstraint();
        if (this.mCharacterRelationKind.equals(CharacterRelationKind.MOTHER)) {
            if ((!this.mFirstNamedCharacter.getBirthday().isMarkedForComplexEval()) && this.mSecondNamedCharacter.getBirthday().isMarkedForComplexEval()) {
                this.mFirstNamedCharacter.getBirthday().setUpForComplexEval();
            }
            if (this.mFirstNamedCharacter.getBirthday().isMarkedForComplexEval() && (!this.mSecondNamedCharacter.getBirthday().isMarkedForComplexEval())) {
                this.mSecondNamedCharacter.getBirthday().setUpForComplexEval();
            }
            if (this.mFirstNamedCharacter.getBirthday().isMarkedForComplexEval() && this.mSecondNamedCharacter.getBirthday().isMarkedForComplexEval()) {
                NavigableSet<DateTime> firstEpisodeDates = this.mFirstNamedCharacter.getBirthday().getPossibleDays();
                NavigableSet<DateTime> secondEpisodeDates = this.mSecondNamedCharacter.getBirthday().getPossibleDays();
                DateTime lowerBoundOnBirthDay1 = secondEpisodeDates.first().minusYears(START_OF_MENOPAUSE).withHourOfDay(1);
                DateTime upperBoundOnBirthDay1 = secondEpisodeDates.first().minus(this.mCharacterRelationKind.mPeriod).withHourOfDay(23);
                Iterator<DateTime> firstEpisodeDatesIterator = firstEpisodeDates.iterator();
                Iterator<DateTime> secondEpisodeDatesIterator = secondEpisodeDates.iterator();
                DateTime curFirstEpisodeDate = firstEpisodeDatesIterator.next();
                DateTime curSecondEpisodeDate = secondEpisodeDatesIterator.next();
                boolean cont = true;
                //while (indexInToFirstEpisodeDatesDates < firstEpisodeDates.size()) {
                while (cont) {
                    if (curFirstEpisodeDate.isBefore(lowerBoundOnBirthDay1)) {
                        firstEpisodeDatesIterator.remove();
                        if (firstEpisodeDatesIterator.hasNext()) {
                            curFirstEpisodeDate = firstEpisodeDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    } else if (curFirstEpisodeDate.isAfter(upperBoundOnBirthDay1)) {
                        if (secondEpisodeDatesIterator.hasNext()) {
                            curSecondEpisodeDate = secondEpisodeDatesIterator.next();
                            lowerBoundOnBirthDay1 = curSecondEpisodeDate.minusYears(START_OF_MENOPAUSE).withHourOfDay(1);
                            upperBoundOnBirthDay1 = curSecondEpisodeDate.minus(this.mCharacterRelationKind.mPeriod).withHourOfDay(23);
                        } else {
                            firstEpisodeDatesIterator.remove();
                            if (firstEpisodeDatesIterator.hasNext()) {
                                curFirstEpisodeDate = firstEpisodeDatesIterator.next();
                            } else {
                                cont = false;
                            }
                            changed = true;
                        }
                    } else {
                        if (firstEpisodeDatesIterator.hasNext()) {
                            curFirstEpisodeDate = firstEpisodeDatesIterator.next();
                        } else {
                            cont = false;
                        }
                    }
                }
                firstEpisodeDatesIterator = firstEpisodeDates.iterator();
                secondEpisodeDatesIterator = secondEpisodeDates.iterator();
                curFirstEpisodeDate = firstEpisodeDatesIterator.next();
                curSecondEpisodeDate = secondEpisodeDatesIterator.next();
                cont = true;
                DateTime lowerBoundOnBirthDay2 = firstEpisodeDates.first().plus(this.mCharacterRelationKind.mPeriod).withHourOfDay(1);
                DateTime upperBoundOnBirthDay2 = firstEpisodeDates.first().plusYears(START_OF_MENOPAUSE).withHourOfDay(23);
                while (cont) {
                    if (curSecondEpisodeDate.isBefore(lowerBoundOnBirthDay2)) {
                        secondEpisodeDatesIterator.remove();
                        if (secondEpisodeDatesIterator.hasNext()) {
                            curSecondEpisodeDate = secondEpisodeDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    } else if (curSecondEpisodeDate.isAfter(upperBoundOnBirthDay2)) {
                        if (firstEpisodeDatesIterator.hasNext()) {
                            curFirstEpisodeDate = firstEpisodeDatesIterator.next();
                            lowerBoundOnBirthDay2 = curFirstEpisodeDate.plus(this.mCharacterRelationKind.mPeriod).withHourOfDay(1);
                            upperBoundOnBirthDay2 = curFirstEpisodeDate.plusYears(START_OF_MENOPAUSE).withHourOfDay(23);
                        } else {
                            secondEpisodeDatesIterator.remove();
                            if (secondEpisodeDatesIterator.hasNext()) {
                                curSecondEpisodeDate = secondEpisodeDatesIterator.next();
                            } else {
                                cont = false;
                            }
                            changed = true;
                        }
                    } else {
                        if (secondEpisodeDatesIterator.hasNext()) {
                            curSecondEpisodeDate = secondEpisodeDatesIterator.next();
                        } else {
                            cont = false;
                        }
                    }
                }
            }
        }
        return changed;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = super.applyConstraint();
        if (this.mCharacterRelationKind.equals(CharacterRelationKind.MOTHER)) {
            DateTime tempEarliestPossibleDateForFirstEpisode = this.mSecondNamedCharacter.getBirthday().getEarliestPossibleDate().minusYears(START_OF_MENOPAUSE).withHourOfDay(1);
            DateTime tempLatestPossibleDateForSecondEpisode = this.mFirstNamedCharacter.getBirthday().getLatestPossibleDate().plusYears(START_OF_MENOPAUSE).withHourOfDay(23);
            if (tempEarliestPossibleDateForFirstEpisode.isAfter(this.mFirstNamedCharacter.getBirthday().getEarliestPossibleDate())) {
                this.mFirstNamedCharacter.getBirthday().setEarliestPossibleDate(tempEarliestPossibleDateForFirstEpisode);
                changed = true;
            }
            if (this.mSecondNamedCharacter.getBirthday().getLatestPossibleDate().isAfter(tempLatestPossibleDateForSecondEpisode)) {
                this.mSecondNamedCharacter.getBirthday().setLatestPossibleDate(tempLatestPossibleDateForSecondEpisode);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean ConstraintSatisfied() {
        boolean rValue = super.ConstraintSatisfied();
        if (this.mCharacterRelationKind.equals(CharacterRelationKind.MOTHER)) {
            DateTime tempEarliestPossibleDateForFirstEpisode = this.mSecondNamedCharacter.getBirthday().getEarliestPossibleDate().minusYears(START_OF_MENOPAUSE).withHourOfDay(1);
            DateTime tempLatestPossibleDateForSecondEpisode = this.mFirstNamedCharacter.getBirthday().getLatestPossibleDate().plusYears(START_OF_MENOPAUSE).withHourOfDay(23);
            if (tempEarliestPossibleDateForFirstEpisode.isAfter(this.mFirstNamedCharacter.getBirthday().getEarliestPossibleDate())) {
                rValue = false;
            }
            if (this.mSecondNamedCharacter.getBirthday().getLatestPossibleDate().isAfter(tempLatestPossibleDateForSecondEpisode)) {
                rValue = false;
            }
        }
        return rValue;
    }

    @Override
    public String toString() {
        String rString = this.mFirstNamedCharacter.getName() + " is " + this.mSecondNamedCharacter.getName() + "'s ";
        if (this.mCharacterRelationKind.equals(CharacterRelationKind.OLDER_SIBLING)) {
            rString += "older sibling";
        } else if (this.mCharacterRelationKind.equals(CharacterRelationKind.PARENT)) {
            rString += "parent";
        } else if (this.mCharacterRelationKind.equals(CharacterRelationKind.TWIN)) {
            rString += "twin";
        } else {
            rString += this.mCharacterRelationKind.toString();
        }
        return rString;
    }


    @Override
    public boolean consistentWithConstraint(Placement[] inValues) {
        boolean consistent = super.consistentWithConstraint(inValues);
        DateTime inFirstStartTime;
        DateTime inFirstEndTime;
        DateTime inSecondStartTime;
        DateTime inSecondEndTime;
        if (this.mFirstNamedCharacter.getBirthday() instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[0];
            inFirstStartTime = curOnceDayEventPlacement.day;
            inFirstEndTime = curOnceDayEventPlacement.day;
        } else {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inValues[0];
            inFirstStartTime = curOncePeriodEventPlacement.startDay;
            inFirstEndTime = curOncePeriodEventPlacement.endDay;
        }
        if (this.mSecondNamedCharacter.getBirthday() instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[1];
            inSecondStartTime = curOnceDayEventPlacement.day;
            inSecondEndTime = curOnceDayEventPlacement.day;
        } else {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inValues[1];
            inSecondStartTime = curOncePeriodEventPlacement.startDay;
            inSecondEndTime = curOncePeriodEventPlacement.endDay;
        }
        if (consistent) {
            if (this.mCharacterRelationKind.equals(CharacterRelationKind.MOTHER)) {
                DateTime lowerBoundOnBirthDay1 = inSecondStartTime.minusYears(START_OF_MENOPAUSE).withHourOfDay(1);
                DateTime upperBoundOnBirthDay1 = inSecondStartTime.minus(this.mCharacterRelationKind.mPeriod).withHourOfDay(23);
                consistent = inFirstEndTime.isAfter(lowerBoundOnBirthDay1) && inFirstEndTime.isBefore(upperBoundOnBirthDay1);
            }
        }
        return consistent;
    }

    @Override
    public Event[] increaseWhat(Placement inValues[]) {
        Event[] rValue = super.increaseWhat(inValues);
        DateTime inFirstStartTime;
        DateTime inFirstEndTime;
        DateTime inSecondStartTime;
        DateTime inSecondEndTime;
        if (this.mFirstNamedCharacter.getBirthday() instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[0];
            inFirstStartTime = curOnceDayEventPlacement.day;
            inFirstEndTime = curOnceDayEventPlacement.day;
        } else {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inValues[0];
            inFirstStartTime = curOncePeriodEventPlacement.startDay;
            inFirstEndTime = curOncePeriodEventPlacement.endDay;
        }
        if (this.mSecondNamedCharacter.getBirthday() instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[1];
            inSecondStartTime = curOnceDayEventPlacement.day;
            inSecondEndTime = curOnceDayEventPlacement.day;
        } else {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inValues[1];
            inSecondStartTime = curOncePeriodEventPlacement.startDay;
            inSecondEndTime = curOncePeriodEventPlacement.endDay;
        }
        if (rValue.length == 0) {
            if (this.mCharacterRelationKind.equals(CharacterRelationKind.MOTHER)) {
                DateTime lowerBoundOnBirthDay1 = inSecondStartTime.minusYears(START_OF_MENOPAUSE).withHourOfDay(1);
                DateTime upperBoundOnBirthDay1 = inSecondStartTime.minus(this.mCharacterRelationKind.mPeriod).withHourOfDay(23);
                if (!inFirstEndTime.isAfter(lowerBoundOnBirthDay1)) {
                    rValue = new Event[]{this.getFirstEvent()};
                }
                if (!inFirstEndTime.isBefore(upperBoundOnBirthDay1)) {
                    rValue = new Event[]{this.getSecondEvent()};
                }
            }
        }
        return rValue;
    }

    public static enum CharacterRelationKind {

        OLDER_SIBLING(RelationKind.BEFORE, null), PARENT(RelationKind.MORE_THEN_BEFORE, org.joda.time.Years.years(13)), TWIN(RelationKind.SAME_TIME_AS, null), GRANDPARENT(RelationKind.MORE_THEN_BEFORE, org.joda.time.Years.years(26)), GREAT_GRANDPARENT(RelationKind.MORE_THEN_BEFORE, org.joda.time.Years.years(39)), MOTHER(RelationKind.MORE_THEN_BEFORE, org.joda.time.Years.years(13));
        private final RelationKind mCharacterRelationKind;
        private final ReadablePeriod mPeriod;

        CharacterRelationKind(RelationKind inCharacterRelationKind, ReadablePeriod inPeriod) {
            this.mCharacterRelationKind = inCharacterRelationKind;
            this.mPeriod = inPeriod;
        }

        /**
         * @return the mCharacterRelationKind
         */
        public RelationKind getCharacterRelationKind() {
            return this.mCharacterRelationKind;
        }

        /**
         * @return the mPeriod
         */
        public ReadablePeriod getPeriod() {
            return this.mPeriod;
        }
    }

    public static class CharacterRelationXMLWriter extends IDedObjectXMLWriter<CharacterRelation> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(CharacterRelation.class, new CharacterRelationXMLWriter());
        }

        @Override
        public Element writeElements(CharacterRelation ObjectToWrite) {
            Element newElement = new Element("CharacterRelation");
            this.writeElements(ObjectToWrite, newElement);
            Element firstCharacterElement = new Element("firstCharacterByID");
            NamedCharacter.NamedCharacterIDXMLWriter.instance.writeElements(ObjectToWrite.mFirstNamedCharacter,firstCharacterElement);
            newElement.addContent(firstCharacterElement);
            Element secondCharacterElement = new Element("secondCharacterByID");
            NamedCharacter.NamedCharacterIDXMLWriter.instance.writeElements(ObjectToWrite.mSecondNamedCharacter,secondCharacterElement);
            newElement.addContent(secondCharacterElement);
            Element kindElement = new Element("kind");
            kindElement.setText(ObjectToWrite.mCharacterRelationKind.name());
            newElement.addContent(kindElement);
            return newElement;
        }

        @Override
        public CharacterRelation readElements(Element root) {
            return new CharacterRelation(root);
        }

    }
}
