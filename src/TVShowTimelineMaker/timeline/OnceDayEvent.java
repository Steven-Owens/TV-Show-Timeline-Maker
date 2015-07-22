/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.PartialDayAcceptor;
import TVShowTimelineMaker.timeline.OnceDayEvent.OnceDayEventPlacement;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Partial;
import org.joda.time.Period;

/**
 *
 * @author Steven Owens
 */
public final class OnceDayEvent extends EventImp implements DateTimeDayEvent<OnceDayEventPlacement> {

    private static final long serialVersionUID = 12L;
    private static final Logger LOG = Logger.getLogger(OnceDayEvent.class.getName());

    static {
        java.util.logging.Logger.getLogger(OnceDayEvent.class.getCanonicalName()).setParent(Main.AppGlobalLogger);
    }

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init2() {
        MyLittePonyMaps.putEvent("OnceDayEvent", OnceDayEvent.class);
    }

    private DateTime earliestPossibleDate;
    private DateTime latestPossibleDate;
    private final AndDayAcceptor mDayAcceptor;
    private final Collection<DateTime> SuggestedDays = new java.util.ArrayList<>(1);
    private final Collection<DateTime> readOnlySuggestedDays = java.util.Collections.unmodifiableCollection(this.SuggestedDays);
    private final NavigableSet<DateTime> PossibleDays = new java.util.TreeSet<>();

    public OnceDayEvent() {
        this("New Event");
    }

    public OnceDayEvent(String inName) {
        this(inName, false);

    }

    protected OnceDayEvent(String inName, boolean tempEvent) {
        this(inName, tempEvent, new AndDayAcceptor());
    }

    protected OnceDayEvent(String inName, boolean tempEvent, AndDayAcceptor inAndDayAcceptor) {
        super(inName, tempEvent);
        this.mDayAcceptor = inAndDayAcceptor;
    }

    private OnceDayEvent(String inName, String inRootNamespace, int inID) {
        super(inName, inRootNamespace, inID);
        this.mDayAcceptor = new AndDayAcceptor();
    }

    private OnceDayEvent(Element root) {
        super(root);
        XMLWriter<DateTime> DateTimeWriter = XMLWriterImp.getXMLWriter(DateTime.class);
        Element earliestPossibleDateElement = root.getChild("earliestPossibleDate");
        DateTime newEarliestPossibleDate = DateTimeWriter.readElements(earliestPossibleDateElement.getChildren().get(0));
        Element latestPossibleDateElement = root.getChild("latestPossibleDate");
        DateTime newLatestPossibleDateElement = DateTimeWriter.readElements(latestPossibleDateElement.getChildren().get(0));
        mDayAcceptor = new AndDayAcceptor();
        Element incompleteDateElement = root.getChild("incompleteDate");
        if (incompleteDateElement != null) {
            XMLWriter<Partial> PartialWriter = XMLWriterImp.getXMLWriter(Partial.class);
            Partial newIncompleteDate = PartialWriter.readElements(incompleteDateElement.getChildren().get(0));
            mDayAcceptor.add(new PartialDayAcceptor(newIncompleteDate));
        }
        setEarliestPossibleDate(newEarliestPossibleDate);
        setLatestPossibleDate(newLatestPossibleDateElement);
        Element suggestedDaysElement = root.getChild("suggestedDays");
        if (suggestedDaysElement != null) {
            addSuggestedDays(suggestedDaysElement.getChildren().parallelStream().map(DateTimeWriter::readElements).collect(Collectors.toList()));
        }
    }

    @Override
    public boolean addSuggestedDay(DateTime inDate) {
        final DateTime transformedDate = inDate.withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        return this.SuggestedDays.add(transformedDate);
    }

    @Override
    public boolean removeSuggestedDay(DateTime inDate) {
        return this.SuggestedDays.remove(inDate);
    }

    @Override
    public Collection<DateTime> getSuggestedDays() {
        return this.readOnlySuggestedDays;
    }

    @Override
    public final void reset() {
        super.reset();
        if (this.mDayAcceptor != null) {
            this.mDayAcceptor.clear();
            this.PossibleDays.clear();
        }
        this.setEarliestPossibleDate(new DateTime(-1 * Main.YearRange, 1, 1, 1, 0));
        this.setLatestPossibleDate(new DateTime(Main.YearRange, 12, 31, 23, 59));
    }

    /**
     * like String.trim() but removes impossible days from the beginning and end
     * of the interval specified by earliestPossibleDate to latestPossibleDate
     */
    @Override
    public void normalize() {
        DateTime endDate = this.latestPossibleDate.withHourOfDay(23);

        DateTime iTime = this.earliestPossibleDate.withHourOfDay(11);
        while ((!this.mDayAcceptor.accept(iTime)) && (iTime.isBefore(endDate))) {
            iTime = iTime.plusDays(1);
        }
        this.earliestPossibleDate = iTime;
        DateTime startDate = this.earliestPossibleDate.withHourOfDay(1);
        iTime = this.latestPossibleDate.withHourOfDay(13);
        while ((!this.mDayAcceptor.accept(iTime)) && (iTime.isAfter(startDate))) {
            iTime = iTime.minusDays(1);
        }
        this.latestPossibleDate = iTime;

        if (!this.PossibleDays.isEmpty()) {
            this.trimPossibleDays();
        }
        this.lastmodifyed = System.currentTimeMillis();
    }

    @Override
    public boolean isValid() {
        return this.earliestPossibleDate.isBefore(this.latestPossibleDate);
    }

    /**
     * @param earliestPossibleDate the earliestPossibleDate to set
     */
    public void setEarliestPossibleDate(DateTime earliestPossibleDate) {
        this.earliestPossibleDate = earliestPossibleDate.withHourOfDay(10);
        this.lastmodifyed = System.currentTimeMillis();
    }

    /**
     * @param latestPossibleDate the latestPossibleDate to set
     */
    public void setLatestPossibleDate(DateTime latestPossibleDate) {
        this.latestPossibleDate = latestPossibleDate.withHourOfDay(14);
        this.lastmodifyed = System.currentTimeMillis();
    }

    /**
     * @return the incompleteDate
     */
    @Override
    public AndDayAcceptor getDayAcceptor() {
        return this.mDayAcceptor;
    }

    @Override
    public String toLongString() {
        return this.getLeastSignificantIDPart() + ":" + this.getName() + ":" + this.earliestPossibleDate.toString() + "\\" + this.latestPossibleDate.toString();
    }

    /**
     * @return the PossibleDays
     */
    @Override
    public NavigableSet<DateTime> getPossibleDays() {
        return this.PossibleDays;
    }

    @Deprecated
    public void addPossibleDays() {
        this.PossibleDays.clear();
        DateTime endDate = this.getLatestPossibleDate().withHourOfDay(23);
        //BigO(endDate - eventToPlace.getEarliestPossibleDate())
        for (DateTime curDate = this.getEarliestPossibleDate().withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0); curDate.isBefore(endDate); curDate = curDate.plusDays(1)) {
            if (this.getDayAcceptor().accept(curDate)) {
                this.PossibleDays.add(curDate);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setUpForComplexEval() {
        if (!this.isMarkedForComplexEval()) {
            this.addPossibleDays();
        }
        super.setUpForComplexEval();
    }

    @Override
    public DateTime getEarliestPossibleStartTime() {
        return this.earliestPossibleDate;
    }

    @Override
    public DateTime getLatestPossibleEndTime() {
        return this.latestPossibleDate;
    }

    public DateTime getEarliestPossibleDate() {
        return this.earliestPossibleDate;
    }

    public DateTime getLatestPossibleDate() {
        return this.latestPossibleDate;
    }

    @Override
    public DateTime getLatestPossibleStartTime() {
        return this.latestPossibleDate;
    }

    @Override
    public DateTime getEarliestPossibleEndTime() {
        return this.earliestPossibleDate;
    }

    @Override
    public boolean isAfter(DateTime inTime) {
        return this.earliestPossibleDate.isAfter(inTime);
    }

    @Override
    public boolean isBefore(DateTime inTime) {
        return this.latestPossibleDate.isBefore(inTime);
    }

    @Override
    public boolean setAfter(DateTime inTime) {
        boolean changed = false;
        DateTime transformedTime = inTime.withHourOfDay(1);
        if (!this.earliestPossibleDate.isAfter(transformedTime)) {
            this.setEarliestPossibleDate(transformedTime);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean setBefore(DateTime inTime) {
        boolean changed = false;
        DateTime transformedTime = inTime.withHourOfDay(23);
        if (!this.latestPossibleDate.isBefore(transformedTime)) {
            this.setLatestPossibleDate(transformedTime);
            changed = true;
        }
        return changed;
    }

    private void trimPossibleDays() {
        Iterator<DateTime> iterator = this.PossibleDays.iterator();
        DateTime curDay = iterator.next();
        while (iterator.hasNext() && curDay.isBefore(this.earliestPossibleDate)) {
            iterator.remove();
            curDay = iterator.next();
        }
        if (this.PossibleDays.size() >= 1) {
            iterator = this.PossibleDays.descendingIterator();
            if (!iterator.hasNext()) {
                java.util.logging.Logger.getLogger(this.getClass().getCanonicalName()).log(Level.INFO, "PossibleDays size:{0} has next{1}", new Object[]{this.PossibleDays.size(), iterator.hasNext()});
            } else {
                curDay = iterator.next();
                while (iterator.hasNext() && curDay.isAfter(this.latestPossibleDate)) {
                    iterator.remove();
                    curDay = iterator.next();
                }
            }
        }
        if (this.earliestPossibleDate.isBefore(this.PossibleDays.first())) {
            this.setEarliestPossibleDate(this.PossibleDays.first());
        }
        if (this.latestPossibleDate.isAfter(this.PossibleDays.last())) {
            this.setLatestPossibleDate(this.PossibleDays.last());
        }
    }

    @Override
    public int size() {
        if (this.isMarkedForComplexEval()) {
            return this.PossibleDays.size();
        } else if (this.latestPossibleDate.isAfter(this.earliestPossibleDate)) {
            org.joda.time.Interval newInterval = new org.joda.time.Interval(this.earliestPossibleDate, this.latestPossibleDate);
            Period daysPeriod = newInterval.toPeriod(org.joda.time.PeriodType.days());
            return daysPeriod.getDays() + 1;
        } else {
            return -1;
        }
    }

    @Override
    public double sizeAdj() {
        double rvalue = this.size();
        if (!this.isMarkedForComplexEval()) {
            rvalue *= this.mDayAcceptor.divFactor();
        }
        if (!this.SuggestedDays.isEmpty()) {
            double suggest;
            if (this.isMarkedForComplexEval()) {
                List<DateTime> temp = new java.util.ArrayList<>(this.SuggestedDays);
                temp.retainAll(this.PossibleDays);
                suggest = temp.size();
            } else {
                suggest = this.SuggestedDays.size();
            }
            if (suggest > 0) {
                rvalue = Math.min(rvalue, suggest);
            }
        }
        return rvalue;
    }

    @Override
    public String toTimeFrameString() {
        return "on " + this.earliestPossibleDate.toString();
    }

    @Override
    public List<OnceDayEventPlacement> getPlacements() {
        return this.PossibleDays.stream().map((DateTime curDateTime) -> new OnceDayEventPlacement(curDateTime)).collect(Collectors.toList());
    }

    @Override
    public List<OnceDayEventPlacement> getSuggestedPlacements() {
        return this.SuggestedDays.stream().map((DateTime curDateTime) -> new OnceDayEventPlacement(curDateTime)).collect(Collectors.toList());
    }

    @Override
    public void setViaPlacement(OnceDayEventPlacement inPlacement) {
        this.setEarliestPossibleDate(inPlacement.day);
        this.setLatestPossibleDate(inPlacement.day);
        this.PossibleDays.clear();
        this.PossibleDays.add(inPlacement.day);
    }

    @Override
    public void setTo(Collection<? extends Placement<?>> inPlacements) {
        this.PossibleDays.clear();
        this.PossibleDays.addAll(inPlacements.parallelStream()
                .filter((Placement curPlacement) -> curPlacement instanceof OnceDayEventPlacement)
                .map((Placement curPlacement) -> (OnceDayEventPlacement)curPlacement)
                .map((OnceDayEventPlacement curPlacement) -> curPlacement.day)
                .collect(Collectors.toList()));
    }

    @Override
    public void setTo(Placement inPlacement) {
        if (inPlacement instanceof OnceDayEventPlacement) {
            this.setViaPlacement((OnceDayEventPlacement) inPlacement);
        }
    }

    private void addSuggestedDays(Collection<DateTime> collect) {
        this.SuggestedDays.addAll(collect);
    }

    public static class OnceDayEventPlacement implements DayEvent.DayPlacement<DateTime> {

        public final DateTime day;

        public OnceDayEventPlacement(DateTime inDay) {
            this.day = inDay;
        }

        @Override
        public DateTime getDay() {
            return day;
        }
    }

    public static class OnceDayEventXMLWriter
            extends EventXMLWriter<OnceDayEvent> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(OnceDayEvent.class, new OnceDayEventXMLWriter());
        }

        @Override
        public Element writeElements(OnceDayEvent ObjectToWrite) {
            Element newElement = new Element(MyLittePonyMaps.getFriendlyStringForEventClass(OnceDayEvent.class));
            this.writeElements(ObjectToWrite, newElement);
            return newElement;
        }
            
        @Override
        public void writeElements(OnceDayEvent ObjectToWrite, Element newElement) {
            super.writeElements(ObjectToWrite, newElement);
            XMLWriter<DateTime> DateTimeWriter = XMLWriterImp.getXMLWriter(DateTime.class);
            Element earliestPossibleDateElement = new Element("earliestPossibleDate");
            earliestPossibleDateElement.setContent(DateTimeWriter.writeElements(ObjectToWrite.getEarliestPossibleDate()));
            newElement.addContent(earliestPossibleDateElement);
            Element latestPossibleDateElement = new Element("latestPossibleDate");
            latestPossibleDateElement.setContent(DateTimeWriter.writeElements(ObjectToWrite.getLatestPossibleDate()));
            newElement.addContent(latestPossibleDateElement);
            Element suggestedDaysElement = new Element("suggestedDays");
            suggestedDaysElement.addContent(ObjectToWrite.getSuggestedDays().parallelStream().map(DateTimeWriter::writeElements).collect(Collectors.toList()));
            newElement.addContent(suggestedDaysElement);
            XMLWriter<AndDayAcceptor> AndDayAcceptorWriter = XMLWriterImp.getXMLWriter(AndDayAcceptor.class);
            Element DayAcceptorElement = new Element("DayAcceptor");
            DayAcceptorElement.setContent(AndDayAcceptorWriter.writeElements(ObjectToWrite.getDayAcceptor()));
            newElement.addContent(DayAcceptorElement);
        }

        @Override
        public OnceDayEvent readElements(org.jdom2.Element root) {
            return new OnceDayEvent(root);
        }

    }
}
