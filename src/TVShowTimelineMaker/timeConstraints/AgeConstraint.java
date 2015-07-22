/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.character.NamedCharacter;
import TVShowTimelineMaker.character.NamedCharacter.NamedCharacterIDXMLWriter;
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


public class AgeConstraint extends AbstractTwoEventTimeConstraint<OnceDayEvent.OnceDayEventPlacement,OnceDayEvent.OnceDayEventPlacement> {

    private static final long serialVersionUID = 5L;
    private static final Logger LOG = Logger.getLogger(AgeConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init2() {
        MyLittePonyMaps.putConstraint("AgeConstraint", AgeConstraint.class);
    }
    private final NamedCharacter mCharacter;
    private final int lowerBound;
    private final int upperBound;
    private final OnceDayEvent mAtEvent;
    private final OnceDayEvent rConstrainedEvents[];
    
    public AgeConstraint(NamedCharacter inCharacter, int inLowerBound, int inUpperBound, OnceDayEvent inAtEvent) {
        this(false, inCharacter, inLowerBound, inUpperBound, inAtEvent);
    }

    public AgeConstraint(boolean isSynthetic, NamedCharacter inCharacter, int inLowerBound, int inUpperBound, OnceDayEvent inAtEvent) {
        super(isSynthetic);
        this.mCharacter = inCharacter;
        this.lowerBound = inLowerBound;
        this.upperBound = inUpperBound;
        this.mAtEvent = inAtEvent;
        this.rConstrainedEvents = new OnceDayEvent[2];
        this.rConstrainedEvents[0] = this.mCharacter.getBirthday();
        this.rConstrainedEvents[1] = this.mAtEvent;
    }
    
    public AgeConstraint(Element root){
        super(root);
        EventImp.EventIDXMLWriter appEventIDXMLWriter = EventImp.EventIDXMLWriter.instance;
            Element characterElement = root.getChild("CharacterByID");
            this.mCharacter = NamedCharacterIDXMLWriter.instance.readElements(characterElement);
            Element EventElement = root.getChild("EventByID");
            this.mAtEvent = (OnceDayEvent) appEventIDXMLWriter.readElements(EventElement);
            Element lowerBoundElement = root.getChild("lowerBound");
            this.lowerBound = java.lang.Integer.parseInt(lowerBoundElement.getTextNormalize());
            Element upperBoundElement = root.getChild("upperBound");
            this.upperBound = java.lang.Integer.parseInt(upperBoundElement.getTextNormalize());
            this.rConstrainedEvents = new OnceDayEvent[2];
        this.rConstrainedEvents[0] = this.mCharacter.getBirthday();
        this.rConstrainedEvents[1] = this.mAtEvent;
    }
    
    @Override
    public boolean inBeta(){
        return false;
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
     * @return the mAtEvent
     */
    public OnceDayEvent getmAtEvent() {
        return this.mAtEvent;
    }

    @Override
    public OnceDayEvent[] getConstrainedEvents() {
        return this.rConstrainedEvents;
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
            DateTime lowerBoundOnBirthDay = EventAtDates.first().minusYears(this.upperBound).withHourOfDay(1);
            DateTime upperBoundOnBirthDay = EventAtDates.first().minusYears(this.lowerBound).withHourOfDay(23);
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
                        lowerBoundOnBirthDay = curEventAtDate.minusYears(this.upperBound).withHourOfDay(1);
                        upperBoundOnBirthDay = curEventAtDate.minusYears(this.lowerBound).withHourOfDay(23);
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
            DateTime lowerBoundOnEvent = birthDayDates.first().plusYears(this.lowerBound).withHourOfDay(1);
            DateTime upperBoundOnEvent = birthDayDates.first().plusYears(this.upperBound).withHourOfDay(23);
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
                        lowerBoundOnEvent = curBirthDay.plusYears(this.lowerBound).withHourOfDay(1);
                        upperBoundOnEvent = curBirthDay.plusYears(this.upperBound).withHourOfDay(23);
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
        DateTime lowerBoundOnBirthDay = this.mAtEvent.getEarliestPossibleDate().minusYears(this.upperBound).withHourOfDay(1);
        DateTime upperBoundOnBirthDay = this.mAtEvent.getLatestPossibleDate().minusYears(this.lowerBound).withHourOfDay(23);
        DateTime lowerBoundOnEvent = birthDay.getEarliestPossibleDate().plusYears(this.lowerBound).withHourOfDay(1);
        DateTime upperBoundOnEvent = birthDay.getLatestPossibleDate().plusYears(this.upperBound).withHourOfDay(23);
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
        DateTime lowerBoundOnBirthDay = this.mAtEvent.getEarliestPossibleDate().minusYears(this.upperBound).withHourOfDay(1);
        DateTime upperBoundOnBirthDay = this.mAtEvent.getLatestPossibleDate().minusYears(this.lowerBound).withHourOfDay(23);
        DateTime lowerBoundOnEvent = birthDay.getEarliestPossibleDate().plusYears(this.lowerBound).withHourOfDay(1);
        DateTime upperBoundOnEvent = birthDay.getLatestPossibleDate().plusYears(this.upperBound).withHourOfDay(23);
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
        String rString = this.mCharacter.getName() + " is between "
                + this.lowerBound + " and " + this.upperBound + " during '"
                + this.mAtEvent.getName() + "'";
        return rString;
    }
    
    public boolean consistentWithConstraint(DateTime inFirstDay, DateTime inSecondDay) {
        DateTime lowerBoundOnBirthDay = inSecondDay.minusYears(this.upperBound).withHourOfDay(1);
        DateTime upperBoundOnBirthDay = inSecondDay.minusYears(this.lowerBound).withHourOfDay(23);
        DateTime lowerBoundOnEvent = inFirstDay.plusYears(this.lowerBound).withHourOfDay(1);
        DateTime upperBoundOnEvent = inFirstDay.plusYears(this.upperBound).withHourOfDay(23);
        return !inFirstDay.isBefore(lowerBoundOnBirthDay) && !inFirstDay.isAfter(upperBoundOnBirthDay) && !inSecondDay.isBefore(lowerBoundOnEvent) && !inSecondDay.isAfter(upperBoundOnEvent);
    }
    
    @Override
    public boolean consistentWithConstraint(OnceDayEvent.OnceDayEventPlacement inFirstPlacement, OnceDayEvent.OnceDayEventPlacement inSecondPlacement){
        return consistentWithConstraint(inFirstPlacement.day, inSecondPlacement.day);
    }

    @Override
    public Event[] increaseWhat(Placement inValues[]){
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
        DateTime lowerBoundOnBirthDay = inSecondStartTime.minusYears(this.upperBound).withHourOfDay(1);
        DateTime upperBoundOnBirthDay = inSecondStartTime.minusYears(this.lowerBound).withHourOfDay(23);
        DateTime lowerBoundOnEvent = inFirstEndTime.plusYears(this.lowerBound).withHourOfDay(1);
        DateTime upperBoundOnEvent = inFirstEndTime.plusYears(this.upperBound).withHourOfDay(23);
        if (inFirstEndTime.isBefore(lowerBoundOnBirthDay) || inSecondStartTime.isAfter(upperBoundOnEvent)){
            return new Event[] {this.mCharacter.getBirthday()};
        }
        if (inFirstEndTime.isAfter(upperBoundOnBirthDay) || inSecondStartTime.isBefore(lowerBoundOnEvent)){
            return new Event[] {this.mAtEvent};
        }
        return new Event[] {};
     }
    
    @Override
    public boolean isStrict() {
        return true;
    }

    public static class AgeConstraintXMLWriter extends IDedObjectXMLWriter<AgeConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(AgeConstraint.class, new AgeConstraint.AgeConstraintXMLWriter());
        }

        @Override
        public Element writeElements(AgeConstraint ObjectToWrite) {
            EventImp.EventIDXMLWriter appEventIDXMLWriter = EventImp.EventIDXMLWriter.instance;
            Element newElement = new Element(MyLittePonyMaps.getFriendlyStringForConstraintClass(AgeConstraint.class));
            this.writeElements(ObjectToWrite, newElement);
            Element firstCharacterElement = new Element("CharacterByID");
            NamedCharacterIDXMLWriter.instance.writeElements(ObjectToWrite.mCharacter, firstCharacterElement);
            newElement.addContent(firstCharacterElement);
            Element firstEpisodeElement = new Element("EventByID");
            appEventIDXMLWriter.writeElements(ObjectToWrite.mAtEvent, firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            Element lowerBoundElement = new Element("lowerBound");
            lowerBoundElement.setText(java.lang.Integer.toString(ObjectToWrite.lowerBound));
            newElement.addContent(lowerBoundElement);
            Element upperBoundElement = new Element("upperBound");
            upperBoundElement.setText(java.lang.Integer.toString(ObjectToWrite.upperBound));
            newElement.addContent(upperBoundElement);
            return newElement;
        }

        @Override
        public AgeConstraint readElements(Element root) {
            return new AgeConstraint(root);
        }
    }

}
