/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.AbstractTwoEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.TimeConstraintImp;
import TVShowTimelineMaker.timeConstraints.interfaces.TwoOnceDayEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.AutomaticMetaData;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.Primary;
import TVShowTimelineMaker.util.XML.XMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

//TODO:change to new xml reader model
public class FieldChangeConstraint extends AbstractTwoEventTimeConstraint<OnceDayEvent.DayPlacement<DateTime>,OnceDayEvent.DayPlacement<DateTime>> {

    private static final long serialVersionUID = 1207L;
    private static final Logger LOG = Logger.getLogger(FieldChangeConstraint.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("FieldChangeConstraint", FieldChangeConstraint.class);
        MyLittePonyMaps.addTimeConstraintMetaData(FieldChangeConstraint.class,AutomaticMetaData.constuctFromClassSingleVerb(FieldChangeConstraint.class, "field change"));
    }

    public static List<DateTimeFieldType> lowerFields(DateTimeFieldType inDateTimeFieldType) {
        List<DateTimeFieldType> rValues = new java.util.ArrayList<>();
        //temporyly add the input to the collection so all are treated the same
        rValues.add(inDateTimeFieldType);
        if (rValues.contains(DateTimeFieldType.era())) {
            rValues.add(DateTimeFieldType.centuryOfEra());
            //rValues.add(DateTimeFieldType.yearOfEra());
        }
        if (rValues.contains(DateTimeFieldType.centuryOfEra())) {
            //rValues.add(DateTimeFieldType.weekyearOfCentury());
            rValues.add(DateTimeFieldType.yearOfCentury());
        }
        if ((rValues.contains(DateTimeFieldType.year())) || (rValues.contains(DateTimeFieldType.yearOfCentury())) || (rValues.contains(DateTimeFieldType.yearOfEra()))) {
            rValues.add(DateTimeFieldType.dayOfYear());
            //rValues.add(DateTimeFieldType.monthOfYear());
        }
        if ((rValues.contains(DateTimeFieldType.weekyear())) || (rValues.contains(DateTimeFieldType.weekyearOfCentury()))) {
            rValues.add(DateTimeFieldType.weekOfWeekyear());
        }
        if (rValues.contains(DateTimeFieldType.monthOfYear())) {
            rValues.add(DateTimeFieldType.dayOfMonth());
        }
        if (rValues.contains(DateTimeFieldType.weekOfWeekyear())) {
            rValues.add(DateTimeFieldType.dayOfWeek());
        }
        //currently we only have a day worth of accturecy
        /*if ((rValues.contains(DateTimeFieldType.dayOfMonth())) || (rValues.contains(DateTimeFieldType.dayOfWeek())) || (rValues.contains(DateTimeFieldType.dayOfYear()))){
        //rValues.add(DateTimeFieldType.clockhourOfDay());
        //rValues.add(DateTimeFieldType.halfdayOfDay());
        rValues.add(DateTimeFieldType.hourOfDay());
        //rValues.add(DateTimeFieldType.millisOfDay());
        //rValues.add(DateTimeFieldType.minuteOfDay());
        //rValues.add(DateTimeFieldType.secondOfDay());
        }
        if (rValues.contains(DateTimeFieldType.halfdayOfDay())){
        //rValues.add(DateTimeFieldType.clockhourOfHalfday());
        rValues.add(DateTimeFieldType.hourOfHalfday());
        }
        if ((rValues.contains(DateTimeFieldType.hourOfDay())) || (rValues.contains(DateTimeFieldType.hourOfHalfday()))){
        rValues.add(DateTimeFieldType.minuteOfHour());
        }
        if ((rValues.contains(DateTimeFieldType.minuteOfDay())) || (rValues.contains(DateTimeFieldType.minuteOfHour()))){
        rValues.add(DateTimeFieldType.secondOfMinute());
        }
        if ((rValues.contains(DateTimeFieldType.secondOfDay())) || (rValues.contains(DateTimeFieldType.secondOfMinute()))){
        rValues.add(DateTimeFieldType.millisOfSecond());
        }*/
        
        //remove input from the collection
        rValues.remove(inDateTimeFieldType);
        return rValues;
    }

    private final OnceDayEvent firstEpisode;
    private final OnceDayEvent secondEpisode;
    private final Event rConstrainedEvents[];
    private final DateTimeFieldType mDateTimeFieldType;
    private final int mAmount;
    //late init members
    private List<DateTimeFieldType> lowerFields;
    org.joda.time.MutablePeriod toPeriod;
    
    @Primary
    public FieldChangeConstraint(OnceDayEvent inFirstEpisode, OnceDayEvent inSecondEpisode, DateTimeFieldType inDateTimeFieldType, int inAmount) {
        this(false, inFirstEpisode, inSecondEpisode, inDateTimeFieldType, inAmount);
    }

    public FieldChangeConstraint(boolean isSynthetic, OnceDayEvent inFirstEpisode, OnceDayEvent inSecondEpisode, DateTimeFieldType inDateTimeFieldType, int inAmount) {
        super(isSynthetic);
        this.firstEpisode = inFirstEpisode;
        this.secondEpisode = inSecondEpisode;
        this.rConstrainedEvents = new Event[]{this.firstEpisode, this.secondEpisode};
        this.mDateTimeFieldType = inDateTimeFieldType;
        this.mAmount = inAmount;
        this.lowerFields = null;
        this.toPeriod = null;
    }
    
    @Override
    public boolean inBeta() {
        return false;
    }
    
    private void makeToPeriodIfNull(){
        if (this.toPeriod == null) {
            this.toPeriod = new org.joda.time.MutablePeriod();
            this.toPeriod.add(this.mDateTimeFieldType.getDurationType(), this.mAmount);
        }
    }

    public Event[] increaseWhat(DateTime inFirstDay, DateTime inSecondDay) {
        this.makeToPeriodIfNull();
        DateTime minForFirstEvent = inSecondDay.minus(this.toPeriod);
        DateTime minForSecondEvent = inFirstDay.plus(this.toPeriod);
        if (this.lowerFields == null) {
            this.lowerFields = lowerFields(this.mDateTimeFieldType);
        }
        for (DateTimeFieldType curDateTimeFieldType : this.lowerFields) {
            minForFirstEvent = minForFirstEvent.property(curDateTimeFieldType).withMinimumValue();
            minForSecondEvent = minForSecondEvent.property(curDateTimeFieldType).withMinimumValue();
        }
        if (inFirstDay.isBefore(minForFirstEvent)) {
            return new Event[]{this.firstEpisode};
        } else if (inSecondDay.isBefore(minForSecondEvent)) {
            return new Event[]{this.secondEpisode};
        } else {
            return new Event[]{};
        }
    }
    
    public boolean consistentWithConstraint(DateTime inFirstDay, DateTime inSecondDay) {
        this.makeToPeriodIfNull();
        int firstValue = inFirstDay.plus(this.toPeriod).get(this.mDateTimeFieldType);
        int secondValue = inSecondDay.get(this.mDateTimeFieldType);
        return (firstValue == secondValue);
    }
    
    @Override
    public boolean consistentWithConstraint(DayEvent.DayPlacement<DateTime> inFirstPlacement, DayEvent.DayPlacement<DateTime> inSecondPlacement) {
        return consistentWithConstraint(inFirstPlacement.getDay(),inSecondPlacement.getDay());
    }

    @Override
    public boolean consistentWithConstraint(Placement[] inValues) {
        return this.consistentWithConstraint(((OnceDayEvent.OnceDayEventPlacement) inValues[0]).day, ((OnceDayEvent.OnceDayEventPlacement) inValues[1]).day);
    }
    
    

    @Override
    public Event[] increaseWhat(Placement[] inValues) {
        return this.increaseWhat(((OnceDayEvent.OnceDayEventPlacement) inValues[0]).day, ((OnceDayEvent.OnceDayEventPlacement) inValues[1]).day);
    }

    @Override
    public Event getFirstEvent() {
        return this.firstEpisode;
    }

    @Override
    public Event getSecondEvent() {
        return this.secondEpisode;
    }

    @Override
    public boolean isStrictlyBefore() {
        return true;
    }

    @Override
    public Event[] getConstrainedEvents() {
        return this.rConstrainedEvents;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        this.makeToPeriodIfNull();
        DateTime minForFirstEvent = this.secondEpisode.getEarliestPossibleStartTime().minus(this.toPeriod);
        DateTime minForSecondEvent = this.firstEpisode.getEarliestPossibleStartTime().plus(this.toPeriod);
        DateTime maxForFirstEvent = this.secondEpisode.getLatestPossibleEndTime().minus(this.toPeriod);
        DateTime maxForSecondEvent = this.firstEpisode.getLatestPossibleEndTime().plus(this.toPeriod);
        if (this.lowerFields == null) {
            this.lowerFields = lowerFields(this.mDateTimeFieldType);
        }
        for (DateTimeFieldType curDateTimeFieldType : this.lowerFields) {
            minForFirstEvent = minForFirstEvent.property(curDateTimeFieldType).withMinimumValue();
            minForSecondEvent = minForSecondEvent.property(curDateTimeFieldType).withMinimumValue();
            maxForFirstEvent = maxForFirstEvent.property(curDateTimeFieldType).withMaximumValue();
            maxForSecondEvent = maxForSecondEvent.property(curDateTimeFieldType).withMaximumValue();
        }
        if (!this.firstEpisode.isAfter(minForFirstEvent)) {
            changed = this.firstEpisode.setAfter(minForFirstEvent) || changed;
        }
        if (!this.secondEpisode.isAfter(minForSecondEvent)) {
            changed = this.secondEpisode.setAfter(minForSecondEvent) || changed;
        }
        if (!this.firstEpisode.isBefore(maxForFirstEvent)) {
            changed = this.firstEpisode.setBefore(maxForFirstEvent) || changed;
        }
        if (!this.secondEpisode.isBefore(maxForSecondEvent)) {
            changed = this.secondEpisode.setBefore(maxForSecondEvent) || changed;
        }
        return changed;
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = false;
        if (this.firstEpisode.isMarkedForComplexEval() && (!this.secondEpisode.isMarkedForComplexEval())) {
            this.secondEpisode.setUpForComplexEval();
        }
        if ((!this.firstEpisode.isMarkedForComplexEval()) && this.secondEpisode.isMarkedForComplexEval()) {
            this.secondEpisode.setUpForComplexEval();
        }
        if (this.firstEpisode.isMarkedForComplexEval() && this.secondEpisode.isMarkedForComplexEval()) {
            Collection<DateTime> safeFirst = new java.util.HashSet<>(0);
            Collection<DateTime> safeSecond = new java.util.HashSet<>(0);
            //todo:make this mutiThreaded
            for (DateTime curFirstDateTime : this.firstEpisode.getPossibleDays()) {
                for (DateTime curSecondDateTime : this.secondEpisode.getPossibleDays()) {
                    if (this.consistentWithConstraint(curFirstDateTime, curSecondDateTime)) {
                        safeFirst.add(curFirstDateTime);
                        safeSecond.add(curSecondDateTime);
                    }
                }
            }
            if (!safeFirst.containsAll(this.firstEpisode.getPossibleDays())) {
                changed = true;
            }
            if (!safeSecond.containsAll(this.secondEpisode.getPossibleDays())) {
                changed = true;
            }
            this.firstEpisode.getPossibleDays().retainAll(safeFirst);
            this.secondEpisode.getPossibleDays().retainAll(safeSecond);
        }
        return changed;
    }

    @Override
    public boolean ConstraintSatisfied() {
        boolean consistent = true;
        consistent = consistent && this.consistentWithConstraint(this.firstEpisode.getEarliestPossibleStartTime(), this.secondEpisode.getEarliestPossibleStartTime());
        consistent = consistent && this.consistentWithConstraint(this.firstEpisode.getLatestPossibleEndTime(), this.secondEpisode.getLatestPossibleEndTime());
        return consistent;
    }

    @Override
    public boolean isStrict() {
        return true;
    }

    /**
     * @return the mDateTimeFieldType
     */
    public DateTimeFieldType getDateTimeFieldType() {
        return this.mDateTimeFieldType;
    }

    /**
     * @return the mAmount
     */
    public int getAmount() {
        return this.mAmount;
    }

    @Override
    public String toString() {
        return this.firstEpisode.getName() + " is " + java.lang.Integer.toString(this.mAmount) + " " + this.mDateTimeFieldType.getName() + " before " + this.secondEpisode.getName();
    }

    public static class FieldChangeConstraintXMLWriter extends XMLWriterImp<FieldChangeConstraint> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(FieldChangeConstraint.class, new FieldChangeConstraintXMLWriter());
        }

        @Override
        public Element writeElements(FieldChangeConstraint ObjectToWrite) {
            Element newElement = new Element(MyLittePonyMaps.getFriendlyStringForConstraintClass(FieldChangeConstraint.class));
            //TODO: refactor reading and writing Events
            Element firstEpisodeElement = new Element("firstEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.firstEpisode, firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            Element secondEpisodeElement = new Element("secondEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.secondEpisode,secondEpisodeElement);
            newElement.addContent(secondEpisodeElement);
            XMLWriter<DateTimeFieldType> DateTimeFieldTypeWriter = XMLWriterImp.getXMLWriter(DateTimeFieldType.class);
            Element DateTimeFieldTypeElement = new Element("DateTimeFieldType");
            DateTimeFieldTypeElement.addContent(DateTimeFieldTypeWriter.writeElements(ObjectToWrite.mDateTimeFieldType));
            newElement.addContent(DateTimeFieldTypeElement);
            Element AmountElement = new Element("Amount");
            AmountElement.setText(java.lang.Integer.toString(ObjectToWrite.mAmount));
            newElement.addContent(AmountElement);
            return newElement;
        }

        @Override
        public FieldChangeConstraint readElements(Element root) {
            Element firstEpisodeElement = root.getChild("firstEventByID");
            OnceDayEvent firstEpisode = (OnceDayEvent) EventImp.EventIDXMLWriter.instance.readElements(firstEpisodeElement);
            Element secondEpisodeElement = root.getChild("secondEventByID");
            OnceDayEvent secondEpisode = (OnceDayEvent) EventImp.EventIDXMLWriter.instance.readElements(secondEpisodeElement);
            XMLWriter<DateTimeFieldType> DateTimeFieldTypeWriter = XMLWriterImp.getXMLWriter(DateTimeFieldType.class);
            Element DateTimeFieldTypeElement = root.getChild("DateTimeFieldType");
            DateTimeFieldType tempDateTimeFieldType = DateTimeFieldTypeWriter.readElements(DateTimeFieldTypeElement.getChildren().get(0));
            Element AmountElement = root.getChild("Amount");
            int tempAmount = Integer.parseInt(AmountElement.getTextNormalize());
            return new FieldChangeConstraint(firstEpisode, secondEpisode, tempDateTimeFieldType, tempAmount);
        }
    }

}
