/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.timeConstraints.core.AbstractTwoEventTimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
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


public class AgeGroupConstraint extends AbstractTwoEventTimeConstraint<OnceDayEvent.OnceDayEventPlacement,OnceDayEvent.OnceDayEventPlacement> {

    private static final long serialVersionUID = 5L;
    private static final Logger LOG = Logger.getLogger(AgeGroupConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init2() {
        MyLittePonyMaps.putConstraint("AgeGroupConstraint", AgeGroupConstraint.class);
    }
    private final NamedCharacter mCharacter;
    private final AgeGroup mAgeGroup;
    private final OnceDayEvent mAtEvent;
    private final OnceDayEvent rConstrainedEvents[];
    
    public AgeGroupConstraint(NamedCharacter inCharacter, AgeGroup inAgeGroup, OnceDayEvent inAtEvent) {
        this(false, inCharacter, inAgeGroup, inAtEvent);
    }

    public AgeGroupConstraint(boolean isSynthetic, NamedCharacter inCharacter, AgeGroup inAgeGroup, OnceDayEvent inAtEvent) {
        super(isSynthetic);
        this.mCharacter = inCharacter;
        this.mAgeGroup = inAgeGroup;
        this.mAtEvent = inAtEvent;
        this.rConstrainedEvents = new OnceDayEvent[2];
        this.rConstrainedEvents[0] = this.mCharacter.getBirthday();
        this.rConstrainedEvents[1] = this.mAtEvent;
    }
    
    public AgeGroupConstraint(Element root){
        super(root);
         Element characterElement = root.getChild("CharacterByID");
            this.mCharacter = NamedCharacter.NamedCharacterIDXMLWriter.instance.readElements(characterElement);
            Element EventElement = root.getChild("EventByID");
            this.mAtEvent = (OnceDayEvent) EventImp.EventIDXMLWriter.instance.readElements(EventElement);
            Element AgeGroupElement = root.getChild("AgeGroup");
            this.mAgeGroup = AgeGroup.valueOf(AgeGroupElement.getTextNormalize());
            this.rConstrainedEvents = new OnceDayEvent[2];
        this.rConstrainedEvents[0] = this.mCharacter.getBirthday();
        this.rConstrainedEvents[1] = this.mAtEvent;
    }
    
    @Override
    public boolean inBeta(){
        return false;
    }
    
    @Override
    public boolean isStrict() {
        return true;
    }

    @Override
    public EventImp getFirstEvent() {
        return this.mCharacter.getBirthday();
    }

    @Override
    public EventImp getSecondEvent() {
        return this.mAtEvent;
    }

    @Override
    public boolean isStrictlyBefore() {
        return true;
    }

    /**
     * @return the mCharacter
     */
        public NamedCharacter getmCharacter() {
            return this.mCharacter;
        }

    /**
     * @return the mAgeGroup
     */
        public AgeGroup getmAgeGroup() {
            return this.mAgeGroup;
        }

    /**
     * @return the mAtEvent
     */
        public OnceDayEvent getmAtEvent() {
            return this.mAtEvent;
        }

    @Override
    public OnceDayEvent[] getConstrainedEvents() {
        return this.rConstrainedEvents;
    }
    
    public boolean consistentWithConstraint(DateTime inFirstDay, DateTime inSecondDay) {
        DateTime lowerBoundOnBirthDay = inSecondDay.minusYears(this.mAgeGroup.upperBound).withHourOfDay(1);
        DateTime upperBoundOnBirthDay = inSecondDay.minusYears(this.mAgeGroup.lowerBound).withHourOfDay(23);
        DateTime lowerBoundOnEvent = inFirstDay.plusYears(this.mAgeGroup.lowerBound).withHourOfDay(1);
        DateTime upperBoundOnEvent = inFirstDay.plusYears(this.mAgeGroup.upperBound).withHourOfDay(23);
        return !inFirstDay.isBefore(lowerBoundOnBirthDay) && !inFirstDay.isAfter(upperBoundOnBirthDay) && !inSecondDay.isBefore(lowerBoundOnEvent) && !inSecondDay.isAfter(upperBoundOnEvent);
    }
    
    @Override
    public boolean consistentWithConstraint(OnceDayEvent.OnceDayEventPlacement inFirstPlacement, OnceDayEvent.OnceDayEventPlacement inSecondPlacement){
        return consistentWithConstraint(inFirstPlacement.day, inSecondPlacement.day);
    }
    
    @Override
    public Event[] increaseWhat(Placement<?> inValues[]){
         DateTime inFirstStartTime;
        DateTime inFirstEndTime;
        DateTime inSecondStartTime;
        DateTime inSecondEndTime;
        if (this.mCharacter.getBirthday() instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[0];
            inFirstStartTime = curOnceDayEventPlacement.day;
            inFirstEndTime = curOnceDayEventPlacement.day;
        } else {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inValues[0];
            inFirstStartTime = curOncePeriodEventPlacement.startDay;
            inFirstEndTime = curOncePeriodEventPlacement.endDay;
        }
        if (this.mAtEvent instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[1];
            inSecondStartTime = curOnceDayEventPlacement.day;
            inSecondEndTime = curOnceDayEventPlacement.day;
        } else {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inValues[1];
            inSecondStartTime = curOncePeriodEventPlacement.startDay;
            inSecondEndTime = curOncePeriodEventPlacement.endDay;
        }
        DateTime lowerBoundOnBirthDay = inSecondStartTime.minusYears(this.mAgeGroup.upperBound).withHourOfDay(1);
        DateTime upperBoundOnBirthDay = inSecondStartTime.minusYears(this.mAgeGroup.lowerBound).withHourOfDay(23);
        DateTime lowerBoundOnEvent = inFirstEndTime.plusYears(this.mAgeGroup.lowerBound).withHourOfDay(1);
        DateTime upperBoundOnEvent = inFirstEndTime.plusYears(this.mAgeGroup.upperBound).withHourOfDay(23);
        if (inFirstEndTime.isBefore(lowerBoundOnBirthDay) || inSecondStartTime.isAfter(upperBoundOnEvent)){
            return new Event[] {this.mCharacter.getBirthday()};
        }
        if (inFirstEndTime.isAfter(upperBoundOnBirthDay) || inSecondStartTime.isBefore(lowerBoundOnEvent)){
            return new Event[] {this.mAtEvent};
        }
        return new Event[] {};
     }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = this.applyConstraint();
        OnceDayEvent birthDay = this.mCharacter.getBirthday();
        if ((!birthDay.isMarkedForComplexEval()) && this.mAtEvent.isMarkedForComplexEval()) {
            birthDay.setUpForComplexEval();
        }
        if (birthDay.isMarkedForComplexEval() && (!this.mAtEvent.isMarkedForComplexEval())) {
            this.mAtEvent.setUpForComplexEval();
        }
        if (birthDay.isMarkedForComplexEval() && this.mAtEvent.isMarkedForComplexEval()) {
            NavigableSet<DateTime> birthDayDates = birthDay.getPossibleDays();
            NavigableSet<DateTime> EventAtDates = this.mAtEvent.getPossibleDays();
            DateTime lowerBoundOnBirthDay = EventAtDates.first().minusYears(this.mAgeGroup.upperBound).withHourOfDay(1);
            DateTime upperBoundOnBirthDay = EventAtDates.first().minusYears(this.mAgeGroup.lowerBound).withHourOfDay(23);
            Iterator<DateTime> birthDayIterator = birthDayDates.iterator();
            Iterator<DateTime> EventAtDatesIterator = EventAtDates.iterator();
            DateTime curBirthDay = birthDayIterator.next();
            DateTime curEventAtDate = EventAtDatesIterator.next();
            boolean cont = true;
            //while (birthDayIterator.hasNext()) {
            while (cont) {
                if (curBirthDay.isBefore(lowerBoundOnBirthDay)) {
                    birthDayIterator.remove();
                    if (birthDayIterator.hasNext()) {
                        curBirthDay = birthDayIterator.next();
                    } else {
                        cont = false;
                    }
                    changed = true;
                } else if (curBirthDay.isAfter(upperBoundOnBirthDay)) {
                    if (EventAtDatesIterator.hasNext()) {
                        curEventAtDate = EventAtDatesIterator.next();
                        lowerBoundOnBirthDay = curEventAtDate.minusYears(this.mAgeGroup.upperBound).withHourOfDay(1);
                        upperBoundOnBirthDay = curEventAtDate.minusYears(this.mAgeGroup.lowerBound).withHourOfDay(23);
                    } else {
                        birthDayIterator.remove();
                        if (birthDayIterator.hasNext()) {
                            curBirthDay = birthDayIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    }
                } else {
                    if (birthDayIterator.hasNext()) {
                        curBirthDay = birthDayIterator.next();
                    } else {
                        cont = false;
                    }
                }
            }
            birthDayIterator = birthDayDates.iterator();
            EventAtDatesIterator = EventAtDates.iterator();
            curBirthDay = birthDayIterator.next();
            curEventAtDate = EventAtDatesIterator.next();
            cont = true;
            DateTime lowerBoundOnEvent = birthDayDates.first().plusYears(this.mAgeGroup.lowerBound).withHourOfDay(1);
            DateTime upperBoundOnEvent = birthDayDates.first().plusYears(this.mAgeGroup.upperBound).withHourOfDay(23);
            while (cont) {
                if (curEventAtDate.isBefore(lowerBoundOnEvent)) {
                    EventAtDatesIterator.remove();
                    if (EventAtDatesIterator.hasNext()) {
                        curEventAtDate = EventAtDatesIterator.next();
                    } else {
                        cont = false;
                    }
                    changed = true;
                } else if (curEventAtDate.isAfter(upperBoundOnEvent)) {
                    if (birthDayIterator.hasNext()) {
                        curBirthDay = birthDayIterator.next();
                        lowerBoundOnEvent = curBirthDay.plusYears(this.mAgeGroup.lowerBound).withHourOfDay(1);
                        upperBoundOnEvent = curBirthDay.plusYears(this.mAgeGroup.upperBound).withHourOfDay(23);
                    } else {
                        EventAtDatesIterator.remove();
                        if (EventAtDatesIterator.hasNext()) {
                            curEventAtDate = EventAtDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    }
                } else {
                    if (EventAtDatesIterator.hasNext()) {
                        curEventAtDate = EventAtDatesIterator.next();
                    } else {
                        cont = false;
                    }
                }
            }
        }
        return changed;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        OnceDayEvent birthDay = this.mCharacter.getBirthday();
        DateTime lowerBoundOnBirthDay = this.mAtEvent.getEarliestPossibleDate().minusYears(this.mAgeGroup.upperBound).withHourOfDay(1);
        DateTime upperBoundOnBirthDay = this.mAtEvent.getLatestPossibleDate().minusYears(this.mAgeGroup.lowerBound).withHourOfDay(23);
        DateTime lowerBoundOnEvent = birthDay.getEarliestPossibleDate().plusYears(this.mAgeGroup.lowerBound).withHourOfDay(1);
        DateTime upperBoundOnEvent = birthDay.getLatestPossibleDate().plusYears(this.mAgeGroup.upperBound).withHourOfDay(23);
        if (lowerBoundOnEvent.isAfter(this.mAtEvent.getEarliestPossibleDate())) {
            this.mAtEvent.setEarliestPossibleDate(lowerBoundOnEvent);
            changed = true;
        }
        if (lowerBoundOnBirthDay.isAfter(birthDay.getEarliestPossibleDate())) {
            birthDay.setEarliestPossibleDate(lowerBoundOnBirthDay);
            changed = true;
        }
        if (birthDay.getLatestPossibleDate().isAfter(upperBoundOnBirthDay)) {
            birthDay.setLatestPossibleDate(upperBoundOnBirthDay);
            changed = true;
        }
        if (this.mAtEvent.getLatestPossibleDate().isAfter(upperBoundOnEvent)) {
            this.mAtEvent.setLatestPossibleDate(upperBoundOnEvent);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean ConstraintSatisfied() {
        boolean satisfied = true;
        OnceDayEvent birthDay = this.mCharacter.getBirthday();
        DateTime lowerBoundOnBirthDay = this.mAtEvent.getEarliestPossibleDate().minusYears(this.mAgeGroup.upperBound).withHourOfDay(1);
        DateTime upperBoundOnBirthDay = this.mAtEvent.getLatestPossibleDate().minusYears(this.mAgeGroup.lowerBound).withHourOfDay(23);
        DateTime lowerBoundOnEvent = birthDay.getEarliestPossibleDate().plusYears(this.mAgeGroup.lowerBound).withHourOfDay(1);
        DateTime upperBoundOnEvent = birthDay.getLatestPossibleDate().plusYears(this.mAgeGroup.upperBound).withHourOfDay(23);
        if (lowerBoundOnEvent.isAfter(this.mAtEvent.getEarliestPossibleDate())) {
            satisfied = false;
        }
        if (lowerBoundOnBirthDay.isAfter(birthDay.getEarliestPossibleDate())) {
            satisfied = false;
        }
        if (birthDay.getLatestPossibleDate().isAfter(upperBoundOnBirthDay)) {
            satisfied = false;
        }
        if (this.mAtEvent.getLatestPossibleDate().isAfter(upperBoundOnEvent)) {
            satisfied = false;
        }
        return satisfied;
    }

    @Override
    public String toString() {
        String rString = this.mCharacter.getName() + " is ";
        if (this.mAgeGroup.equals(AgeGroup.BABY)) {
            rString += "a baby";
        } else if (this.mAgeGroup.equals(AgeGroup.ADULT)) {
            rString += "an adult";
        } else if (this.mAgeGroup.equals(AgeGroup.CHILD)) {
            rString += "an child";
        } else if (this.mAgeGroup.equals(AgeGroup.MIDDLE_AGE)) {
            rString += "middle age";
        } else if (this.mAgeGroup.equals(AgeGroup.OLD)) {
            rString += "old";
        } else if (this.mAgeGroup.equals(AgeGroup.TEENAGER)) {
            rString += "a teenager";
        } else if (this.mAgeGroup.equals(AgeGroup.YOUNG_ADULT)) {
            rString += "an young adult";
        } else {
            rString += this.mAgeGroup.toString();
        }
        rString = rString + " during '" + this.mAtEvent.getName() + "'";
        return rString;
    }

    public static enum AgeGroup {

        BABY(0, 2), YOUNG_CHILD(1,7), CHILD(5, 13), TEENAGER(8, 19), YOUNG_ADULT(15, 30), ADULT(20, 35), MIDDLE_AGE(30, 50), OLD(50, 200);

        private final int lowerBound;
        private final int upperBound;

        AgeGroup(int inLowerBound, int inUpperBound) {
            this.lowerBound = inLowerBound;
            this.upperBound = inUpperBound;
        }

        /**
         * @return the lowerBound
         */
        public int getLowerBound() {
            return this.lowerBound;
        }

        /**
         * @return the upperBound
         */
        public int getUpperBound() {
            return this.upperBound;
        }

    }

    public static class AgeGroupConstraintXMLWriter extends IDedObjectXMLWriter<AgeGroupConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(AgeGroupConstraint.class, new AgeGroupConstraint.AgeGroupConstraintXMLWriter());
        }

        @Override
        public Element writeElements(AgeGroupConstraint ObjectToWrite) {
            Element newElement = new Element(MyLittePonyMaps.getFriendlyStringForConstraintClass(AgeGroupConstraint.class));
            this.writeElements(ObjectToWrite, newElement);
            Element firstCharacterElement = new Element("CharacterByID");
            NamedCharacter.NamedCharacterIDXMLWriter.instance.writeElements(ObjectToWrite.mCharacter,firstCharacterElement);
            newElement.addContent(firstCharacterElement);
            Element firstEpisodeElement = new Element("EventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.mAtEvent, firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            Element kindElement = new Element("AgeGroup");
            kindElement.setText(ObjectToWrite.mAgeGroup.name());
            newElement.addContent(kindElement);
            return newElement;
        }

        @Override
        public AgeGroupConstraint readElements(Element root) {
            return new AgeGroupConstraint(root);
        }
    }
}
