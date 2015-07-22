/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeline;

import TVShowTimelineMaker.timeConstraints.ContainsConstraint;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.AndDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.ContainsDayAcceptor;
import TVShowTimelineMaker.timeConstraints.dayAcceptors.NonoverlapingDayAcceptor;
import TVShowTimelineMaker.timeline.YearlyPeriodEvent.YearlyPeriodEventPlacement;
import TVShowTimelineMaker.util.DayOfYear;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Element;
import org.joda.time.DateTime;

/**
 *
 * @author Steven Owens
 */
public final class YearlyPeriodEvent extends EventImp implements DayOfYearPeriodEvent<YearlyPeriodEventPlacement> {

    private static final long serialVersionUID = 509L;
    private static final Logger LOG = Logger.getLogger(YearlyPeriodEvent.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init2() {
        MyLittePonyMaps.putEvent("YearlyPeriodEvent", YearlyPeriodEvent.class);
    }
    private int maxDuration = java.lang.Integer.MAX_VALUE;
    private int minDuration = 0;
    private final AndDayAcceptor startAndDayAcceptor = new AndDayAcceptor();
    ;
    private final AndDayAcceptor endAndDayAcceptor = new AndDayAcceptor();
    ;
    private final NavigableSet<DayOfYear> startPossibleDays = new java.util.TreeSet<>();
    private final NavigableSet<DayOfYear> endPossibleDays = new java.util.TreeSet<>();

    private final ContainsDayAcceptor mContainsDayAcceptor = new ContainsDayAcceptor(this);
    private final NonoverlapingDayAcceptor mNonoverlapingDayAcceptor = new NonoverlapingDayAcceptor(this);

    private final Set<DayOfYear> containsSureDays = new java.util.HashSet<>(0);
    private long lastUpdatedContainsSureDays = java.lang.Long.MIN_VALUE;
    private final Set<DayOfYear> containsDays = new java.util.HashSet<>(0);
    private long lastUpdatedContainsDays = java.lang.Long.MIN_VALUE;
    private List<YearlyPeriodEventPlacement> mVaildPlacements = new java.util.ArrayList<>(1);
    private final Set<Event> mContainedEvents = new java.util.HashSet<>(0);
    private final Set<Event> mNonoverlapingEvents = new java.util.HashSet<>(0);

    public YearlyPeriodEvent() {
        this("new Event");
    }

    public YearlyPeriodEvent(String inName) {
        super(inName);
        this.addPossibleDays();
    }

    protected YearlyPeriodEvent(String inName, String inRootNamespace, int inID) {
        super(inName, inRootNamespace, inID);
        this.addPossibleDays();
    }

    private YearlyPeriodEvent(Element root) {
        super(root);
        XMLWriter<DayOfYear> DayOfYearWriter = XMLWriterImp.getXMLWriter(DayOfYear.class);
        //read in newEvent.startPossibleDays
        Element startDaysElement = root.getChild("startDays");
        if (startDaysElement != null) {
            startPossibleDays.clear();
            startPossibleDays.addAll(startDaysElement.getChildren().parallelStream().map(DayOfYearWriter::readElements).collect(Collectors.toList()));
        }
        //read in newEvent.endPossibleDays
        Element endDaysElement = root.getChild("endDays");
        if (endDaysElement != null) {
            endPossibleDays.clear();
            endPossibleDays.addAll(endDaysElement.getChildren().parallelStream().map(DayOfYearWriter::readElements).collect(Collectors.toList()));
        }
        Element maxDurationElement = root.getChild("maxDuration");
        if (maxDurationElement != null) {
            setMaxDuration(java.lang.Integer.parseInt(maxDurationElement.getTextNormalize()));
        }
        Element minDurationElement = root.getChild("minDuration");
        if (minDurationElement != null) {
            setMinDuration(java.lang.Integer.parseInt(minDurationElement.getTextNormalize()));
        }
    }

    @Override
    public final void reset() {
        super.reset();
        if (this.startPossibleDays != null) {
            this.addPossibleDays();
        }
        this.maxDuration = java.lang.Integer.MAX_VALUE;
        this.minDuration = 0;
        if (this.containsSureDays != null) {
            this.containsSureDays.clear();
        }
        if (this.containsDays != null) {
            this.containsDays.clear();
        }
        if (this.mVaildPlacements != null) {
            this.mVaildPlacements.clear();
        }
    }

    @Override
    public void normalize() {
        Iterator<DayOfYear> iterator = this.startPossibleDays.iterator();
        while (iterator.hasNext()) {
            DayOfYear curDayOfYear = iterator.next();
            if (!this.startAndDayAcceptor.accept(curDayOfYear)) {
                iterator.remove();
            }
        }
        iterator = this.endPossibleDays.iterator();
        while (iterator.hasNext()) {
            DayOfYear curDayOfYear = iterator.next();
            if (!this.endAndDayAcceptor.accept(curDayOfYear)) {
                iterator.remove();
            }
        }
        Iterator<DayOfYear> iteratorStart = this.startPossibleDays.iterator();
        while (iteratorStart.hasNext()) {
            DayOfYear curStartDay = iteratorStart.next();
            boolean canbe = false;
            for (DayOfYear curEndDay : this.endPossibleDays) {
                if (this.definesVaildPlacement(curStartDay, curEndDay)) {
                    canbe = true;
                }
            }
            if (!canbe) {
                iteratorStart.remove();
            }
        }
        Iterator<DayOfYear> iteratorEnd = this.endPossibleDays.iterator();
        while (iteratorEnd.hasNext()) {
            DayOfYear curEndDay = iteratorEnd.next();
            boolean canbe = false;
            for (DayOfYear curStartDay : this.startPossibleDays) {
                if (this.definesVaildPlacement(curStartDay, curEndDay)) {
                    canbe = true;
                }
            }
            if (!canbe) {
                iteratorEnd.remove();
            }
        }
        int min = java.lang.Integer.MAX_VALUE;
        int max = 0;
        for (DayOfYear curStartDay : this.startPossibleDays) {
            for (DayOfYear curEndDay : this.endPossibleDays) {
                int currdiff = curStartDay.diff(curEndDay);
                if (currdiff < min) {
                    min = currdiff;
                }
                if (currdiff > max) {
                    max = currdiff;
                }
            }
        }
        if (min > this.minDuration) {
            this.setMinDuration(min);
        }
        if (max < this.maxDuration) {
            this.setMaxDuration(max);
        }
        if (this.isMarkedForComplexEval()) {
            Iterator<YearlyPeriodEventPlacement> PlacementIterator = this.mVaildPlacements.iterator();
            Collection<DayOfYear> retainStartDays = new java.util.HashSet<>(1);
            Collection<DayOfYear> retainEndDays = new java.util.HashSet<>(1);
            while (PlacementIterator.hasNext()) {
                YearlyPeriodEventPlacement curPlacement = PlacementIterator.next();
                if (!this.VaildPlacement(curPlacement)) {
                    PlacementIterator.remove();
                } else {
                    retainStartDays.add(curPlacement.startDay);
                    retainEndDays.add(curPlacement.endDay);
                }
            }
            this.startPossibleDays.retainAll(retainStartDays);
            this.endPossibleDays.retainAll(retainEndDays);
        }
    }

    @Override
    public boolean isValid() {
        if (this.startPossibleDays.isEmpty()) {
            return false;
        }
        if (this.endPossibleDays.isEmpty()) {
            return false;
        }
        //todo: debug
        if (this.minDuration > this.maxDuration) {
            int a = 1 + 2;
        }
        return this.minDuration <= this.maxDuration;
    }

    /**
     * @return the minDuration
     */
    @Override
    public int getMinDuration() {
        return this.minDuration;
    }

    /**
     * @param minDuration the minDuration to set
     */
    @Override
    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    /**
     * @return the maxDuration
     */
    @Override
    public int getMaxDuration() {
        return this.maxDuration;
    }

    /**
     * @param maxDuration the maxDuration to set
     */
    @Override
    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    public YearlyDayEvent getStart() {
        return new YearlyDayEvent("start of" + this.getName(), true, this.startAndDayAcceptor);
    }

    @Override
    public YearlyDayEvent getEnd() {
        return new YearlyDayEvent("end of" + this.getName(), true, this.endAndDayAcceptor);
    }

    @Override
    public boolean containsCould(DateTime inDateTime) {
        return this.getContainsCouldDays().contains(DayOfYear.fromDateTime(inDateTime));
    }

    @Override
    public boolean containsCould(DayOfYear inDateTime) {
        return this.getContainsCouldDays().contains(inDateTime);
    }

    @Override
    public boolean containsSure(DateTime inDateTime) {
        return this.getContainsSureDays().contains(DayOfYear.fromDateTime(inDateTime));
    }

    @Override
    public boolean containsSure(DayOfYear inDateTime) {
        return this.getContainsSureDays().contains(inDateTime);
    }

    @Override
    public boolean abutsBefore(PeriodEvent<?,?> inPeriodEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean abutsAfter(PeriodEvent<?,?> inPeriodEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setAbutsBefore(PeriodEvent<?,?> inPeriodEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setAbutsAfter(PeriodEvent<?,?> inPeriodEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OncePeriodEvent getInstance(int year) {
        OncePeriodEvent newOncePeriodEvent = new OncePeriodEvent(this.getName() + " in year " + year, true, this.startAndDayAcceptor, this.endAndDayAcceptor);
        if (!this.startPossibleDays.isEmpty()) {
            newOncePeriodEvent.setEarliestPossibleDateForStart(this.startPossibleDays.first().toDateTime());
            newOncePeriodEvent.setLatestPossibleDateForStart(this.startPossibleDays.last().toDateTime());
        }
        if (!this.endPossibleDays.isEmpty()) {
            newOncePeriodEvent.setEarliestPossibleDateForEnd(this.endPossibleDays.first().toDateTime());
            newOncePeriodEvent.setLatestPossibleDateForEnd(this.endPossibleDays.last().toDateTime());
        }
        newOncePeriodEvent.setMinDuration(this.minDuration);
        newOncePeriodEvent.setMaxDuration(this.maxDuration);
        newOncePeriodEvent.normalize();
        return newOncePeriodEvent;
    }

    @Override
    public int size() {
        //TODO: refine
        return this.startPossibleDays.size() * this.endPossibleDays.size();
    }

    @Override
    public double sizeAdj() {
        return this.size();
    }

    @Override
    public String toTimeFrameString() {
        if ((!this.startPossibleDays.isEmpty()) && (!this.endPossibleDays.isEmpty())) {
            DayOfYear start = this.startPossibleDays.first();
            DayOfYear end = this.endPossibleDays.first();
            return "between " + start.getMonth() + "/" + start.getDay() + " and " + end.getMonth() + "/" + end.getDay();
        } else {
            return "invaild yearlyday event";
        }
    }

    @Override
    public AndDayAcceptor getStartDayAcceptor() {
        return this.startAndDayAcceptor;
    }

    @Override
    public AndDayAcceptor getEndDayAcceptor() {
        return this.endAndDayAcceptor;
    }

    /**
     * @return the startPossibleDays
     */
    @Override
    public NavigableSet<DayOfYear> getStartPossibleDays() {
        return this.startPossibleDays;
    }

    /**
     * @return the endPossibleDays
     */
    @Override
    public NavigableSet<DayOfYear> getEndPossibleDays() {
        return this.endPossibleDays;
    }

    @Override
    public ContainsDayAcceptor getContainsDayAcceptor() {
        return this.mContainsDayAcceptor;
    }

    @Override
    public NonoverlapingDayAcceptor getNonoverlapingDayAcceptor() {
        return this.mNonoverlapingDayAcceptor;
    }

    /**
     * @return the containsSureDays
     */
    public Set<DayOfYear> getContainsSureDays() {
        if (this.lastUpdatedContainsSureDays < this.lastmodifyed) {
            //update containsSureDays
            containsSureDays.clear();
            containsSureDays.addAll(DayOfYear.fullYear.parallelStream().filter((DayOfYear curDay) -> startPossibleDays.parallelStream()
                    .allMatch((DayOfYear curStartDay) -> endPossibleDays.parallelStream()
                            .filter((DayOfYear curEndDay) -> definesVaildPlacement(curStartDay, curEndDay))
                            .allMatch((DayOfYear curEndDay) -> {
                                boolean fine = true;
                                if (curStartDay.isBefore(curEndDay)) {
                                    if (curStartDay.isAfter(curDay)) {
                                        fine = false;
                                    } else if (curEndDay.isBefore(curDay)) {
                                        fine = false;
                                    }
                                } else if (curStartDay.isAfter(curEndDay)) {
                                    if (curStartDay.isAfter(curDay) && curEndDay.isBefore(curDay)) {
                                        fine = false;
                                    }
                                }
                                return fine;
                            })))
                    .collect(Collectors.toList()));
            this.lastUpdatedContainsSureDays = this.lastmodifyed;
        }
        return Collections.unmodifiableSet(containsSureDays);
    }

    /**
     * @return the containsDays
     */
    public Set<DayOfYear> getContainsCouldDays() {
        if (this.lastUpdatedContainsDays < this.lastmodifyed) {
            //update containsSureDays
            this.containsDays.clear();
            containsDays.addAll(DayOfYear.fullYear.parallelStream().filter((DayOfYear curDay) -> startPossibleDays.parallelStream()
                    .anyMatch((DayOfYear curStartDay) -> endPossibleDays.parallelStream()
                            .filter((DayOfYear curEndDay) -> definesVaildPlacement(curStartDay, curEndDay))
                            .anyMatch((DayOfYear curEndDay) -> {
                                boolean shouldAdd = false;
                                if (curStartDay.isBefore(curEndDay)) {
                                    if (curStartDay.isBefore(curDay) && curEndDay.isAfter(curDay)) {
                                        shouldAdd = true;
                                    }
                                } else if (curStartDay.isAfter(curEndDay)) {
                                    if ((curStartDay.isBefore(curDay)) || (curEndDay.isAfter(curDay))) {
                                        shouldAdd = true;
                                    }
                                }
                                return shouldAdd;
                            })))
                    .collect(Collectors.toList()));
            this.lastUpdatedContainsDays = this.lastmodifyed;
        }
        return Collections.unmodifiableSet(containsDays);
    }

    @Override
    public List<YearlyPeriodEventPlacement> getPlacements() {
        if (this.isMarkedForComplexEval()) {
            return this.mVaildPlacements;
        } else {
            List<YearlyPeriodEventPlacement> vaildPlacements = startPossibleDays.parallelStream().map((DayOfYear curStartDay) -> endPossibleDays.parallelStream()
                    .filter((DayOfYear curEndDay) -> definesVaildPlacement(curStartDay, curEndDay))
                    .map((DayOfYear curEndDay) -> new YearlyPeriodEventPlacement(curStartDay, curEndDay))
                    .collect(Collectors.toList()))
                    .collect(java.util.ArrayList<YearlyPeriodEventPlacement>::new, List<YearlyPeriodEventPlacement>::addAll, List<YearlyPeriodEventPlacement>::addAll);
            return vaildPlacements;
        }
    }

    @Override
    public List<YearlyPeriodEventPlacement> getSuggestedPlacements() {
        return java.util.Collections.EMPTY_LIST;
    }

    @Override
    public void setUpForComplexEval() {
        this.mVaildPlacements = this.getPlacements();
        super.setUpForComplexEval();
    }

    @Override
    public void setViaPlacement(YearlyPeriodEventPlacement inPlacement) {
        this.startPossibleDays.clear();
        this.startPossibleDays.add(inPlacement.startDay);
        this.endPossibleDays.clear();
        this.endPossibleDays.add(inPlacement.endDay);
        this.setMinDuration(inPlacement.startDay.diff(inPlacement.endDay));
        this.setMaxDuration(inPlacement.startDay.diff(inPlacement.endDay));
    }

    @Override
    public void setTo(Placement inPlacement) {
        if (inPlacement instanceof YearlyPeriodEventPlacement) {
            this.setViaPlacement((YearlyPeriodEventPlacement) inPlacement);
        }
    }

    @Override
    public void setTo(Collection<? extends Placement<?>> inPlacements) {
        this.mVaildPlacements.clear();
        this.mVaildPlacements.addAll(inPlacements.parallelStream()
                .filter((Placement curPlacement) -> curPlacement instanceof YearlyPeriodEventPlacement)
        .map((Placement curPlacement) -> (YearlyPeriodEventPlacement)curPlacement)
        .collect(Collectors.toSet()));
    }

    private boolean VaildPlacement(YearlyPeriodEventPlacement inSeasonPlacement) {
        return this.definesVaildPlacement(inSeasonPlacement.startDay, inSeasonPlacement.endDay);
    }

    private boolean definesVaildPlacement(DayOfYear startDay, DayOfYear endDay) {
        int diffdays = startDay.diff(endDay);
        if (!((diffdays >= this.minDuration) && (diffdays <= this.maxDuration))) {
            return false;
        }
        if ((!this.startPossibleDays.contains(startDay))
                || (!this.endPossibleDays.contains(endDay))) {
            return false;
        }
        //a vaild placement overlaps with all contained dayEvents and doesn't contain any Nonoverlaping Event
        for (Event curEvent : this.mNonoverlapingEvents) {
            if (ContainsConstraint.contains(this, curEvent)) {
                return false;
            }
        }
        for (Event curEvent : this.mContainedEvents) {
            if (ContainsConstraint.outside(this, curEvent)) {
                return false;
            }
            if (curEvent instanceof PeriodEvent) {
                PeriodEvent curPeriodEvent = (PeriodEvent) curEvent;
                if (!ContainsConstraint.couldOverlap(this, curPeriodEvent)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addPossibleDays() {
        this.startPossibleDays.addAll(DayOfYear.fullYear);
        this.endPossibleDays.addAll(DayOfYear.fullYear);
    }

    public void addContainedEvent(Event inEvent) {
        this.mContainedEvents.add(inEvent);
    }

    public void addNonoverlapingEvent(Event inEvent) {
        this.mNonoverlapingEvents.add(inEvent);
    }

    public static class YearlyPeriodEventPlacement implements PeriodEvent.PeriodPlacement<DayOfYear> {

        public final DayOfYear startDay;
        public final DayOfYear endDay;

        public YearlyPeriodEventPlacement(DayOfYear inStartDay, DayOfYear inEndDay) {
            this.startDay = inStartDay;
            this.endDay = inEndDay;
        }

        @Override
        public DayOfYear getStartDay() {
            return startDay;
        }

        @Override
        public DayOfYear getEndDay() {
            return endDay;
        }
    }

    public static class SeasonXMLWriter extends EventXMLWriter<YearlyPeriodEvent> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(YearlyPeriodEvent.class, new SeasonXMLWriter());
        }

        @Override
        public Element writeElements(YearlyPeriodEvent ObjectToWrite) {
            Element newElement = new Element(MyLittePonyMaps.getFriendlyStringForEventClass(YearlyPeriodEvent.class));
            this.writeElements(ObjectToWrite, newElement);
            return newElement;
        }

        @Override
        public void writeElements(YearlyPeriodEvent ObjectToWrite, Element newElement) {
            super.writeElements(ObjectToWrite, newElement);
            XMLWriter<DayOfYear> DayOfYearWriter = XMLWriterImp.getXMLWriter(DayOfYear.class);
            //write out ObjectToWrite.startPossibleDays
            Element startDaysElement = new Element("startDays");
            startDaysElement.addContent(ObjectToWrite.startPossibleDays.parallelStream().map(DayOfYearWriter::writeElements).collect(Collectors.toList()));
            newElement.addContent(startDaysElement);
            //write out ObjectToWrite.endPossibleDays
            Element endDaysElement = new Element("endDays");
            endDaysElement.addContent(ObjectToWrite.endPossibleDays.parallelStream().map(DayOfYearWriter::writeElements).collect(Collectors.toList()));
            newElement.addContent(endDaysElement);
            Element maxDurationElement = new Element("maxDuration");
            maxDurationElement.setText(java.lang.Integer.toString(ObjectToWrite.maxDuration));
            newElement.addContent(maxDurationElement);
            Element minDurationElement = new Element("minDuration");
            minDurationElement.setText(java.lang.Integer.toString(ObjectToWrite.minDuration));
            newElement.addContent(minDurationElement);
        }

        @Override
        public YearlyPeriodEvent readElements(Element root) {
            return new YearlyPeriodEvent(root);
        }
    }

}
