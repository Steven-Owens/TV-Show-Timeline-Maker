/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeline.YearlyDayEvent.YearlyDayEventPlacement;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Element;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 */
public final class YearlyDayEvent extends EventImp implements DayOfYearDayEvent<YearlyDayEventPlacement> {

    private static final long serialVersionUID = 511L;
    private static final Logger LOG = Logger.getLogger(YearlyDayEvent.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static void init2() {
        MyLittePonyMaps.putEvent("YearlyDayEvent", YearlyDayEvent.class);
    }

    private final AndDayAcceptor mDayAcceptor;
    private final Collection<DayOfYear> SuggestedDays;
    private final Collection<DayOfYear> readOnlySuggestedDays;
    private final NavigableSet<DayOfYear> PossibleDays;
    
    //initilizer
    {
        SuggestedDays = new java.util.ArrayList<>(1);
        readOnlySuggestedDays = java.util.Collections.unmodifiableCollection(this.SuggestedDays);
        PossibleDays = new java.util.TreeSet<>();
        this.addPossibleDays();
    }
    

    public YearlyDayEvent() {
        this("new Event");
    }

    public YearlyDayEvent(String inName) {
        this(inName, false);
    }

    protected YearlyDayEvent(String inName, boolean tempEvent) {
        this(inName, tempEvent, new AndDayAcceptor());
    }

    protected YearlyDayEvent(String inName, boolean tempEvent, AndDayAcceptor inAndDayAcceptor) {
        super(inName, tempEvent);
        this.mDayAcceptor = inAndDayAcceptor;
    }

    protected YearlyDayEvent(String inName, String inRootNamespace, int inID) {
        super(inName, inRootNamespace, inID);
        this.mDayAcceptor = new AndDayAcceptor();
        this.addPossibleDays();
    }
    
    private YearlyDayEvent(Element root) {
        super(root);
        XMLWriter<DayOfYear> DayOfYearWriter = XMLWriterImp.getXMLWriter(DayOfYear.class);
            XMLWriter<AndDayAcceptor> AndDayAcceptorWriter = XMLWriterImp.getXMLWriter(AndDayAcceptor.class);
            Element DayAcceptorElement = root.getChild("DayAcceptor");
            mDayAcceptor = AndDayAcceptorWriter.readElements(DayAcceptorElement);
            Element suggestedDaysElement = root.getChild("suggestedDays");
            if (suggestedDaysElement != null) {
               addSuggestedDays(suggestedDaysElement.getChildren().parallelStream().map(DayOfYearWriter::readElements).collect(Collectors.toList()));
            }
            Element possibleDaysElement = root.getChild("possibleDays");
            PossibleDays.clear();
            if (possibleDaysElement != null) {
                PossibleDays.addAll(possibleDaysElement.getChildren().parallelStream().map(DayOfYearWriter::readElements).collect(Collectors.toList()));
            }
    }

    @Override
    public void normalize() {
        Iterator<DayOfYear> iterator = this.PossibleDays.iterator();
        while (iterator.hasNext()) {
            DayOfYear curDayOfYear = iterator.next();
            if (!this.mDayAcceptor.accept(curDayOfYear)) {
                iterator.remove();
            }
        }
    }

    
    public boolean addSuggestedDay(DateTime inDate) {
        return this.SuggestedDays.add(new DayOfYear(inDate.getMonthOfYear(), inDate.getDayOfMonth()));
    }

    @Override
    public boolean addSuggestedDay(DayOfYear inDate) {
        return this.SuggestedDays.add(inDate);
    }
    
    private void addSuggestedDays(Collection<DayOfYear> collect) {
        this.SuggestedDays.addAll(collect);
    }

    @Override
    public boolean removeSuggestedDay(DayOfYear inDate) {
        return this.SuggestedDays.remove(inDate);
    }

    @Override
    public Collection<DayOfYear> getSuggestedDays() {
        return this.readOnlySuggestedDays;
    }

    @Override
    public void reset() {
        super.reset();
        if (this.mDayAcceptor != null) {
            this.mDayAcceptor.clear();
        }
        if (this.PossibleDays != null) {
            this.addPossibleDays();
        }
    }

    @Override
    public boolean isValid() {
        return !this.PossibleDays.isEmpty();
    }

    public void addPossibleDays() {
        this.PossibleDays.clear();
        for (DayOfYear curDate = new DayOfYear(1, 1); curDate != null; curDate = curDate.plusDaysNoWrap(1)) {
            if (this.getDayAcceptor().accept(curDate)) {
                this.PossibleDays.add(curDate);
            }
        }
    }

    @Override
    public OnceEvent getInstance(int year) {
        OnceDayEvent newOnceDayEvent = new OnceDayEvent(this.getName() + " in year " + year, true, this.mDayAcceptor);
        if (!this.PossibleDays.isEmpty()) {
            newOnceDayEvent.setEarliestPossibleDate(this.PossibleDays.first().toDateTime());
            newOnceDayEvent.setLatestPossibleDate(this.PossibleDays.last().toDateTime());
        } else {
            newOnceDayEvent.normalize();
        }
        return newOnceDayEvent;
    }

    @Override
    public int size() {
        if (this.isValid()) {
            return this.PossibleDays.size();
        } else {
            return -1;
        }
    }

    @Override
    public double sizeAdj() {
        return this.size();
    }

    @Override
    public String toTimeFrameString() {
        return "on " + this.PossibleDays.first().getMonth() + "/" + this.PossibleDays.first().getDay();
    }

    @Override
    public AndDayAcceptor getDayAcceptor() {
        return this.mDayAcceptor;
    }

    @Override
    public NavigableSet<DayOfYear> getPossibleDays() {
        return this.PossibleDays;
    }

    @Override
    public List<YearlyDayEventPlacement> getPlacements() {
        return this.PossibleDays.stream().map((DayOfYear curDateTime) -> new YearlyDayEventPlacement(curDateTime)).collect(Collectors.toList());
    }
    
    @Override
    public List<YearlyDayEventPlacement> getSuggestedPlacements() {
        return this.SuggestedDays.stream().map((DayOfYear curDateTime) -> new YearlyDayEventPlacement(curDateTime)).collect(Collectors.toList());
    }

    @Override
    public void setViaPlacement(YearlyDayEventPlacement inPlacement) {
        this.PossibleDays.clear();
        this.PossibleDays.add(inPlacement.day);
    }
    
    @Override
    public void setTo(Placement inPlacement){
        if (inPlacement instanceof YearlyDayEventPlacement){
            this.setViaPlacement((YearlyDayEventPlacement)inPlacement);
        }
    }

    @Override
    public void setTo(Collection<? extends  Placement<?>> inPlacements) {
        this.PossibleDays.retainAll(inPlacements.parallelStream()
                .filter((Placement curPlacement) -> curPlacement instanceof YearlyDayEventPlacement)
                .map((Placement curPlacement) -> (YearlyDayEventPlacement) curPlacement)
                .map((YearlyDayEventPlacement curPlacement) -> curPlacement.day)
                .collect(java.util.stream.Collectors.toList()));
    }

    public static class YearlyDayEventPlacement implements DayEvent.DayPlacement<DayOfYear> {

        public final DayOfYear day;

        public YearlyDayEventPlacement(DayOfYear inDay) {
            this.day = inDay;
        }

        @Override
        public DayOfYear getDay() {
            return day;
        }
    }

    public static class YearlyDayEventXMLWriter
            extends EventXMLWriter<YearlyDayEvent> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(YearlyDayEvent.class, new YearlyDayEventXMLWriter());
        }

        @Override
        public Element writeElements(YearlyDayEvent ObjectToWrite) {
            Element newElement = new Element(MyLittePonyMaps.getFriendlyStringForEventClass(YearlyDayEvent.class));
            this.writeElements(ObjectToWrite, newElement);
            return newElement;
        }
            
        @Override
        public void writeElements(YearlyDayEvent ObjectToWrite, Element newElement) {
            super.writeElements(ObjectToWrite, newElement);
            XMLWriter<DayOfYear> DayOfYearWriter = XMLWriterImp.getXMLWriter(DayOfYear.class);
            Element suggestedDaysElement = new Element("suggestedDays");
            suggestedDaysElement.addContent(ObjectToWrite.getSuggestedDays().parallelStream().map(DayOfYearWriter::writeElements).collect(Collectors.toList()));
            newElement.addContent(suggestedDaysElement);
            Element possibleDaysElement = new Element("possibleDays");
            possibleDaysElement.addContent(ObjectToWrite.PossibleDays.parallelStream().map(DayOfYearWriter::writeElements).collect(Collectors.toList()));
            newElement.addContent(possibleDaysElement);
            XMLWriter<AndDayAcceptor> AndDayAcceptorWriter = XMLWriterImp.getXMLWriter(AndDayAcceptor.class);
            Element DayAcceptorElement = new Element("DayAcceptor");
            DayAcceptorElement.setContent(AndDayAcceptorWriter.writeElements(ObjectToWrite.mDayAcceptor));
            newElement.addContent(DayAcceptorElement);
        }

        @Override
        public YearlyDayEvent readElements(Element root) {
           return new YearlyDayEvent(root);
        }

    }
}
