/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints;

import TVShowTimelineMaker.timeConstraints.core.AbstractTwoEventTimeConstraint;
import TVShowTimelineMaker.timeConstraints.core.TimeConstraintImp;
import TVShowTimelineMaker.timeConstraints.interfaces.TwoOnceDayEventTimeConstraint;
import TVShowTimelineMaker.timeline.DayEvent;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.EventImp;
import TVShowTimelineMaker.timeline.OnceDayEvent;
import TVShowTimelineMaker.timeline.OnceEvent;
import TVShowTimelineMaker.timeline.OncePeriodEvent;
import TVShowTimelineMaker.timeline.PeriodEvent;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import TVShowTimelineMaker.util.MyLittePonyMaps;
import TVShowTimelineMaker.util.XML.XMLWriter;
import TVShowTimelineMaker.util.XML.XMLWriterImp;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.ReadablePeriod;

public class Relation<T extends Placement<DateTime>, S extends Placement<DateTime>> extends AbstractTwoEventTimeConstraint<T, S> {

    private static final long serialVersionUID = 9L;
    private static final Logger LOG = Logger.getLogger(Relation.class.getName());

    @com.civprod.dynamicClassLoading.ClassInitalizer
    public static final void init() {
        MyLittePonyMaps.putConstraint("Relation", Relation.class);
    }

    private static RelationKind readKindElement(Element kindElement) {
        RelationKind kind;
        String kindText = kindElement.getTextNormalize();
        if (kindText.contains(RelationKind.SAME_DAY_OF_YEAR.name())) {
            kind = RelationKind.SAME_DAY_OF_YEAR;
        } else {
            kind = RelationKind.valueOf(kindElement.getTextNormalize());
        }
        return kind;
    }

    private static ReadablePeriod readTimeElement(Element TimeElement) {
        ReadablePeriod time;
        if (TimeElement != null) {
            XMLWriter<ReadablePeriod> ReadablePeriodWriter = XMLWriterImp.getXMLWriter(ReadablePeriod.class);
            time = ReadablePeriodWriter.readElements(TimeElement.getChildren().get(0));
        } else {
            time = null;
        }
        return time;
    }

    private final OnceEvent<T> firstEpisode;
    private final OnceEvent<S> secondEpisode;
    private final Event rConstrainedEvents[];
    private final RelationKind kind;
    private final org.joda.time.ReadablePeriod time;

    protected final SameTimeOfYearConstraint mSameTimeOfYearConstraint;

    public Relation(OnceEvent<T> inFirstEpisode, OnceEvent<S> inSecondEpisode, RelationKind inKind) {
        this(inFirstEpisode, inSecondEpisode, inKind, null);
    }

    public Relation(OnceEvent<T> inFirstEpisode, OnceEvent<S> inSecondEpisode, RelationKind inKind, ReadablePeriod inPeriod) {
        this(false, inFirstEpisode, inSecondEpisode, inKind, inPeriod);
    }

    public Relation(boolean isSynthetic, OnceEvent<T> inFirstEpisode, OnceEvent<S> inSecondEpisode, RelationKind inKind) {
        this(isSynthetic, inFirstEpisode, inSecondEpisode, inKind, null);
    }

    public Relation(boolean isSynthetic, OnceEvent<T> inFirstEpisode, OnceEvent<S> inSecondEpisode, RelationKind inKind, ReadablePeriod inPeriod) {
        super(isSynthetic);
        this.firstEpisode = inFirstEpisode;
        this.secondEpisode = inSecondEpisode;
        this.kind = inKind;
        this.time = inPeriod;
        this.mSameTimeOfYearConstraint = (this.kind == RelationKind.SAME_DAY_OF_YEAR) ? new SameTimeOfYearConstraint((DayEvent<DateTime, ? extends DayEvent.DayPlacement<DateTime>>) this.firstEpisode, (DayEvent<DateTime, ? extends DayEvent.DayPlacement<DateTime>>) this.secondEpisode) : null;
        this.rConstrainedEvents = new OnceEvent[2];
        this.rConstrainedEvents[0] = this.firstEpisode;
        this.rConstrainedEvents[1] = this.secondEpisode;
    }

    protected Relation(Element root) {
        this(root,
                (OnceEvent<T>) EventImp.EventIDXMLWriter.instance.readElements(root.getChild("firstEventByID")),
                (OnceEvent<S>) EventImp.EventIDXMLWriter.instance.readElements(root.getChild("secondEventByID")),
                readKindElement(root.getChild("kind")),
                readTimeElement(root.getChild("time")));
    }

    protected Relation(Element root, OnceEvent<T> inFirstEpisode, OnceEvent<S> inSecondEpisode, RelationKind inKind, ReadablePeriod inPeriod) {
        super(root);
        this.firstEpisode = inFirstEpisode;
        this.secondEpisode = inSecondEpisode;
        this.kind = inKind;
        this.time = inPeriod;
        this.mSameTimeOfYearConstraint = (this.kind == RelationKind.SAME_DAY_OF_YEAR) ? new SameTimeOfYearConstraint((DayEvent<DateTime, ? extends DayEvent.DayPlacement<DateTime>>) this.firstEpisode, (DayEvent<DateTime, ? extends DayEvent.DayPlacement<DateTime>>) this.secondEpisode) : null;
        this.rConstrainedEvents = new OnceEvent[2];
        this.rConstrainedEvents[0] = this.firstEpisode;
        this.rConstrainedEvents[1] = this.secondEpisode;
    }

    public boolean isValid() {
        if ((this.kind == RelationKind.LESS_THEN_BEFORE) || (this.kind == RelationKind.MORE_THEN_BEFORE)) {
            return this.time != null;
        }
        return true;
    }

    @Override
    public boolean inBeta() {
        return false;
    }

    @Override
    public boolean isStrictlyBefore() {
        return !(this.kind.equals(RelationKind.SAME_TIME_AS) || this.kind.equals(RelationKind.SAME_DAY_OF_YEAR));
    }

    /**
     * @return the firstEpisode
     */
    @Override
    public Event getFirstEvent() {
        return this.firstEpisode;
    }

    /**
     * @return the secondEpisode
     */
    @Override
    public Event getSecondEvent() {
        return this.secondEpisode;
    }

    /**
     * @return the kind
     */
    public RelationKind getKind() {
        return this.kind;
    }

    /**
     * @return the time
     */
    public org.joda.time.ReadablePeriod getTime() {
        return this.time;
    }

    @Override
    public boolean isStrict() {
        if ((this.kind == RelationKind.IMMEDIATELY_BEFORE) || (this.kind == RelationKind.BEFORE)) {
            return false;
        } else if (this.kind == RelationKind.FIXED_TIME_BEFORE) {
            return true;
        } else if (this.kind == RelationKind.LESS_THEN_BEFORE) {
            return true;
        } else if (this.kind == RelationKind.SAME_DAY_OF_YEAR) {
            return true;
        } else if ((this.kind == RelationKind.SAME_TIME_AS) || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
            return this.mSameTimeOfYearConstraint.isStrict();
        } else if (this.kind == RelationKind.MORE_THEN_BEFORE) {
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean complexApplyConstraint() {
        boolean changed = this.applyConstraint();
        if ((!this.firstEpisode.isMarkedForComplexEval()) && this.secondEpisode.isMarkedForComplexEval()) {
            if ((this.kind == RelationKind.FIXED_TIME_BEFORE)
                    || (this.kind == RelationKind.SAME_DAY_OF_YEAR)
                    || (this.kind == RelationKind.LESS_THEN_BEFORE)
                    || (this.kind == RelationKind.SAME_TIME_AS)
                    || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
                this.firstEpisode.setUpForComplexEval();
            }
        }
        if (this.firstEpisode.isMarkedForComplexEval() && (!this.secondEpisode.isMarkedForComplexEval())) {
            if ((this.kind == RelationKind.FIXED_TIME_BEFORE)
                    || (this.kind == RelationKind.SAME_DAY_OF_YEAR)
                    || (this.kind == RelationKind.LESS_THEN_BEFORE)
                    || (this.kind == RelationKind.SAME_TIME_AS)
                    || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
                this.secondEpisode.setUpForComplexEval();
            }
        }
        if (this.firstEpisode.isMarkedForComplexEval() && this.secondEpisode.isMarkedForComplexEval()) {
            NavigableSet<DateTime> firstEpisodeStartDates;
            NavigableSet<DateTime> firstEpisodeEndDates;
            NavigableSet<DateTime> secondEpisodeStartDates;
            NavigableSet<DateTime> secondEpisodeEndDates;
            if (this.firstEpisode instanceof OnceDayEvent) {
                OnceDayEvent firstEpisodeDayEvent = (OnceDayEvent) this.firstEpisode;
                firstEpisodeStartDates = firstEpisodeDayEvent.getPossibleDays();
                firstEpisodeEndDates = firstEpisodeDayEvent.getPossibleDays();
            } else if (this.firstEpisode instanceof OncePeriodEvent) {
                OncePeriodEvent firstEpisodePeriodEvent = (OncePeriodEvent) this.firstEpisode;
                firstEpisodeStartDates = firstEpisodePeriodEvent.getStartPossibleDays();
                firstEpisodeEndDates = firstEpisodePeriodEvent.getEndPossibleDays();
            } else {
                throw new UnsupportedOperationException(this.firstEpisode.getClass().getCanonicalName() + "Not supported yet");
            }
            if (this.secondEpisode instanceof OnceDayEvent) {
                OnceDayEvent secondEpisodeDayEvent = (OnceDayEvent) this.secondEpisode;
                secondEpisodeStartDates = secondEpisodeDayEvent.getPossibleDays();
                secondEpisodeEndDates = secondEpisodeDayEvent.getPossibleDays();
            } else if (this.secondEpisode instanceof OncePeriodEvent) {
                OncePeriodEvent secondEpisodePeriodEvent = (OncePeriodEvent) this.secondEpisode;
                secondEpisodeStartDates = secondEpisodePeriodEvent.getStartPossibleDays();
                secondEpisodeEndDates = secondEpisodePeriodEvent.getEndPossibleDays();
            } else {
                throw new UnsupportedOperationException(this.firstEpisode.getClass().getCanonicalName() + "Not supported yet");
            }
            Iterator<DateTime> firstEpisodeStartDatesIterator = firstEpisodeStartDates.iterator();
            Iterator<DateTime> secondEpisodeStartDatesIterator = secondEpisodeStartDates.iterator();
            DateTime curFirstEpisodeStartDate = firstEpisodeStartDatesIterator.next();
            DateTime curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
            Iterator<DateTime> firstEpisodeEndDatesIterator = firstEpisodeEndDates.iterator();
            Iterator<DateTime> secondEpisodeEndDatesIterator = secondEpisodeEndDates.iterator();
            DateTime curFirstEpisodeEndDate = firstEpisodeEndDatesIterator.next();
            DateTime curSecondEpisodeEndDate = secondEpisodeEndDatesIterator.next();
            boolean cont = true;
            if (this.kind == RelationKind.IMMEDIATELY_BEFORE) {
                DateTime startOfLastDay = secondEpisodeStartDates.last().withHourOfDay(1);
                /*while (indexInToFirstEpisodeDatesDates < firstEpisodeDates.size()) {
                 if (firstEpisodeDates.get(indexInToFirstEpisodeDatesDates).isAfter(startOfLastDay)) {
                 firstEpisodeDates.remove(indexInToFirstEpisodeDatesDates);
                 changed = true;
                 } else {
                 indexInToFirstEpisodeDatesDates++;
                 }
                 }*/
                while (!firstEpisodeEndDates.isEmpty()) {
                    if (firstEpisodeEndDates.last().isAfter(startOfLastDay)) {
                        firstEpisodeEndDates.pollLast();
                        changed = true;
                    } else {
                        break;
                    }
                }
                DateTime endOfFirstPossibleDay = firstEpisodeEndDates.first().withHourOfDay(23);
                while (cont) {
                    if (endOfFirstPossibleDay.isAfter(curSecondEpisodeStartDate)) {
                        secondEpisodeStartDatesIterator.remove();
                        if (secondEpisodeStartDatesIterator.hasNext()) {
                            curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    } else {
                        //indexInToSecondEpisodeDatesDates++;
                        break;
                    }
                }
            } else if (this.kind == RelationKind.BEFORE) {
                DateTime startOfLastDay = secondEpisodeStartDates.last().withHourOfDay(1);
                /*while (indexInToFirstEpisodeDatesDates < firstEpisodeDates.size()) {
                 if (firstEpisodeDates.get(indexInToFirstEpisodeDatesDates).isAfter(startOfLastDay)) {
                 firstEpisodeDates.remove(indexInToFirstEpisodeDatesDates);
                 changed = true;
                 } else {
                 indexInToFirstEpisodeDatesDates++;
                 }
                 }*/
                while (!firstEpisodeEndDates.isEmpty()) {
                    if (firstEpisodeEndDates.last().isAfter(startOfLastDay)) {
                        firstEpisodeEndDates.pollLast();
                        changed = true;
                    } else {
                        break;
                    }
                }
                DateTime endOfFirstPossibleDay = firstEpisodeEndDates.first().withHourOfDay(23);
                while (cont) {
                    if (endOfFirstPossibleDay.isAfter(curSecondEpisodeStartDate)) {
                        secondEpisodeStartDatesIterator.remove();
                        if (secondEpisodeStartDatesIterator.hasNext()) {
                            curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    } else {
                        //indexInToSecondEpisodeDatesDates++;
                        break;
                    }
                }
            } else if (this.kind == RelationKind.FIXED_TIME_BEFORE) {
                //while (indexInToFirstEpisodeDatesDates < firstEpisodeEndDates.size()) {
                while (cont) {
                    //DateTime later = firstEpisodeEndDates.get(indexInToFirstEpisodeDatesDates).plus(time);
                    DateTime later = curFirstEpisodeEndDate.plus(this.time);
                    if (secondEpisodeStartDates.contains(later)) {
                        if (firstEpisodeEndDatesIterator.hasNext()) {
                            curFirstEpisodeEndDate = firstEpisodeEndDatesIterator.next();
                        } else {
                            break;
                        }
                    } else {
                        firstEpisodeEndDatesIterator.remove();
                        if (firstEpisodeEndDatesIterator.hasNext()) {
                            curFirstEpisodeEndDate = firstEpisodeEndDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    }
                }
                while (cont) {
                    DateTime earlier = curSecondEpisodeStartDate.minus(this.time);
                    if (firstEpisodeEndDates.contains(earlier)) {
                        if (secondEpisodeStartDatesIterator.hasNext()) {
                            curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                        } else {
                            break;
                        }
                    } else {
                        secondEpisodeStartDatesIterator.remove();
                        if (secondEpisodeStartDatesIterator.hasNext()) {
                            curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    }
                }
            } else if (this.kind == RelationKind.LESS_THEN_BEFORE) {
                DateTime startOfDay = curSecondEpisodeStartDate.withHourOfDay(1);
                DateTime tempEarliestPossibleDate = curSecondEpisodeStartDate.minus(this.time).withHourOfDay(1);
                while (cont) {
                    if (curFirstEpisodeEndDate.isBefore(tempEarliestPossibleDate)) {
                        firstEpisodeEndDatesIterator.remove();
                        if (firstEpisodeEndDatesIterator.hasNext()) {
                            curFirstEpisodeEndDate = firstEpisodeEndDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    } else if (curFirstEpisodeEndDate.isAfter(startOfDay)) {
                        //if (indexInToSecondEpisodeDatesDates < secondEpisodeStartDates.size()) {
                        if (secondEpisodeStartDatesIterator.hasNext()) {
                            curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                            startOfDay = curSecondEpisodeStartDate.withHourOfDay(1);
                            tempEarliestPossibleDate = curSecondEpisodeStartDate.minus(this.time).withHourOfDay(1);
                        } else {
                            firstEpisodeEndDatesIterator.remove();
                            if (firstEpisodeEndDatesIterator.hasNext()) {
                                curFirstEpisodeEndDate = firstEpisodeEndDatesIterator.next();
                            } else {
                                cont = false;
                            }
                            changed = true;
                        }
                    } else {
                        if (firstEpisodeEndDatesIterator.hasNext()) {
                            curFirstEpisodeEndDate = firstEpisodeEndDatesIterator.next();
                        } else {
                            cont = false;
                        }
                    }
                }
                firstEpisodeStartDatesIterator = firstEpisodeStartDates.iterator();
                secondEpisodeStartDatesIterator = secondEpisodeStartDates.iterator();
                curFirstEpisodeStartDate = firstEpisodeStartDatesIterator.next();
                curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                firstEpisodeEndDatesIterator = firstEpisodeEndDates.iterator();
                secondEpisodeEndDatesIterator = secondEpisodeEndDates.iterator();
                curFirstEpisodeEndDate = firstEpisodeEndDatesIterator.next();
                curSecondEpisodeEndDate = secondEpisodeEndDatesIterator.next();
                cont = true;
                DateTime endOfDay = curFirstEpisodeEndDate.withHourOfDay(23);
                DateTime tempLatestPossibleDate = curFirstEpisodeEndDate.plus(this.time).withHourOfDay(23);
                while (cont) {
                    if (curSecondEpisodeStartDate.isBefore(endOfDay)) {
                        secondEpisodeStartDatesIterator.remove();
                        if (secondEpisodeStartDatesIterator.hasNext()) {
                            curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                        } else {
                            cont = false;
                        }
                        changed = true;
                    } else if (curSecondEpisodeStartDate.isAfter(tempLatestPossibleDate)) {
                        //indexInToFirstEpisodeDatesDates++;
                        //if (indexInToFirstEpisodeDatesDates < firstEpisodeEndDates.size()) {
                        if (firstEpisodeEndDatesIterator.hasNext()) {
                            curFirstEpisodeEndDate = firstEpisodeEndDatesIterator.next();
                            endOfDay = curFirstEpisodeEndDate.withHourOfDay(23);
                            tempLatestPossibleDate = curFirstEpisodeEndDate.plus(this.time).withHourOfDay(23);
                        } else {
                            secondEpisodeStartDatesIterator.remove();
                            if (secondEpisodeStartDatesIterator.hasNext()) {
                                curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                            } else {
                                cont = false;
                            }
                            changed = true;
                        }
                    } else {
                        if (secondEpisodeStartDatesIterator.hasNext()) {
                            curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                        } else {
                            cont = false;
                        }
                    }
                }
            } else if (this.kind == RelationKind.SAME_DAY_OF_YEAR) {
                changed |= this.mSameTimeOfYearConstraint.complexApplyConstraint();
            } else if ((this.kind == RelationKind.SAME_TIME_AS) || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
                Iterator<DateTime> iterator = firstEpisodeStartDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!secondEpisodeStartDates.contains(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
                iterator = secondEpisodeStartDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!firstEpisodeStartDates.contains(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
                iterator = firstEpisodeEndDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!secondEpisodeEndDates.contains(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
                iterator = secondEpisodeEndDates.iterator();
                while (iterator.hasNext()) {
                    DateTime next = iterator.next();
                    if (!firstEpisodeEndDates.contains(next)) {
                        iterator.remove();
                        changed = true;
                    }
                }
            } else if (this.kind == RelationKind.MORE_THEN_BEFORE) {
                DateTime tempEarliestPossibleDateForSecondEpisode = firstEpisodeEndDates.first().plus(this.time).withHourOfDay(1);
                DateTime tempLatestPossibleDateForFirstEpisode = secondEpisodeStartDates.last().minus(this.time).withHourOfDay(23);
                /*while (indexInToFirstEpisodeDatesDates < firstEpisodeDates.size()) {
                 if (firstEpisodeDates.get(indexInToFirstEpisodeDatesDates).isAfter(tempLatestPossibleDateForFirstEpisode)) {
                 firstEpisodeDates.remove(indexInToFirstEpisodeDatesDates);
                 changed = true;
                 } else {
                 indexInToFirstEpisodeDatesDates++;
                 }
                 }*/
                while (!firstEpisodeEndDates.isEmpty()) {
                    if (firstEpisodeEndDates.last().isAfter(tempLatestPossibleDateForFirstEpisode)) {
                        firstEpisodeEndDates.pollLast();
                        changed = true;
                    } else {
                        break;
                    }
                }
                while (cont) {
                    if (tempEarliestPossibleDateForSecondEpisode.isAfter(curSecondEpisodeStartDate)) {
                        secondEpisodeStartDatesIterator.remove();
                        if (secondEpisodeStartDatesIterator.hasNext()) {
                            curSecondEpisodeStartDate = secondEpisodeStartDatesIterator.next();
                        } else {
                            cont = true;
                        }
                        changed = true;
                    } else {
                        //indexInToSecondEpisodeDatesDates++;
                        break;
                    }
                }
            }
        }
        return changed;
    }

    @Override
    public boolean applyConstraint() {
        boolean changed = false;
        DateTime dayAfterFirstEpisode = this.firstEpisode.getEarliestPossibleStartTime().plusDays(1).withHourOfDay(1);
        DateTime dayBeforeSecondEpisode = this.secondEpisode.getLatestPossibleEndTime().minusDays(1).withHourOfDay(23);
        if (this.kind == RelationKind.IMMEDIATELY_BEFORE) {
            changed = this.secondEpisode.setAfter(dayAfterFirstEpisode) || changed;
            changed = this.firstEpisode.setBefore(dayBeforeSecondEpisode) || changed;
        } else if (this.kind == RelationKind.BEFORE) {
            changed = this.secondEpisode.setAfter(dayAfterFirstEpisode) || changed;
            changed = this.firstEpisode.setBefore(dayBeforeSecondEpisode) || changed;
        } else if (this.kind == RelationKind.FIXED_TIME_BEFORE) {
            DateTime tempEarliestPossibleDateForFirstEpisode = this.secondEpisode.getEarliestPossibleStartTime().minus(this.time).withHourOfDay(1);
            DateTime tempEarliestPossibleDateForSecondEpisode = this.firstEpisode.getEarliestPossibleStartTime().plus(this.time).withHourOfDay(1);
            DateTime tempLatestPossibleDateForFirstEpisode = this.secondEpisode.getLatestPossibleEndTime().minus(this.time).withHourOfDay(23);
            DateTime tempLatestPossibleDateForSecondEpisode = this.firstEpisode.getLatestPossibleEndTime().plus(this.time).withHourOfDay(23);
            changed = this.secondEpisode.setAfter(tempEarliestPossibleDateForSecondEpisode) || changed;
            changed = this.firstEpisode.setAfter(tempEarliestPossibleDateForFirstEpisode) || changed;
            changed = this.firstEpisode.setBefore(tempLatestPossibleDateForFirstEpisode) || changed;
            changed = this.secondEpisode.setBefore(tempLatestPossibleDateForSecondEpisode) || changed;
        } else if (this.kind == RelationKind.LESS_THEN_BEFORE) {
            DateTime tempEarliestPossibleDateForFirstEpisode = this.secondEpisode.getEarliestPossibleStartTime().minus(this.time).withHourOfDay(1);
            DateTime tempLatestPossibleDateForSecondEpisode = this.firstEpisode.getLatestPossibleEndTime().plus(this.time).withHourOfDay(23);
            changed = this.secondEpisode.setAfter(dayAfterFirstEpisode) || changed;
            changed = this.firstEpisode.setBefore(dayBeforeSecondEpisode) || changed;
            changed = this.firstEpisode.setAfter(tempEarliestPossibleDateForFirstEpisode) || changed;
            changed = this.secondEpisode.setBefore(tempLatestPossibleDateForSecondEpisode) || changed;
        } else if (this.kind == RelationKind.SAME_DAY_OF_YEAR) {
            changed |= this.mSameTimeOfYearConstraint.applyConstraint();
        } else if ((this.kind == RelationKind.SAME_TIME_AS) || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
            if (this.firstEpisode.getEarliestPossibleStartTime().withHourOfDay(1).isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                changed = this.secondEpisode.setAfter(this.firstEpisode.getEarliestPossibleStartTime()) || changed;
            }
            if (this.secondEpisode.getEarliestPossibleStartTime().withHourOfDay(1).isAfter(this.firstEpisode.getEarliestPossibleStartTime())) {
                changed = this.firstEpisode.setAfter(this.secondEpisode.getEarliestPossibleStartTime()) || changed;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(this.secondEpisode.getLatestPossibleEndTime().withHourOfDay(23))) {
                changed = this.firstEpisode.setBefore(this.secondEpisode.getLatestPossibleEndTime()) || changed;
            }
            if (this.secondEpisode.getLatestPossibleEndTime().isAfter(this.firstEpisode.getLatestPossibleEndTime().withHourOfDay(23))) {
                changed = this.secondEpisode.setBefore(this.firstEpisode.getLatestPossibleEndTime()) || changed;
            }
        } else if (this.kind == RelationKind.MORE_THEN_BEFORE) {
            DateTime tempEarliestPossibleDateForSecondEpisode = this.firstEpisode.getEarliestPossibleStartTime().plus(this.time).withHourOfDay(1);
            DateTime tempLatestPossibleDateForFirstEpisode = this.secondEpisode.getLatestPossibleEndTime().minus(this.time).withHourOfDay(23);
            if (dayAfterFirstEpisode.isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                changed = this.secondEpisode.setAfter(dayAfterFirstEpisode) || changed;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(dayBeforeSecondEpisode)) {
                changed = this.firstEpisode.setBefore(dayBeforeSecondEpisode) || changed;
            }
            if (tempEarliestPossibleDateForSecondEpisode.isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                changed = this.secondEpisode.setAfter(tempEarliestPossibleDateForSecondEpisode) || changed;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(tempLatestPossibleDateForFirstEpisode)) {
                changed = this.firstEpisode.setBefore(tempLatestPossibleDateForFirstEpisode) || changed;
            }
        }
        return changed;
    }

    @Override
    public Event[] getConstrainedEvents() {
        return this.rConstrainedEvents;
    }

    @Override
    public boolean ConstraintSatisfied() {
        boolean satisfied = true;
        DateTime dayAfterFirstEpisode = this.firstEpisode.getEarliestPossibleStartTime().plusDays(1).withHourOfDay(1);
        DateTime dayBeforeSecondEpisode = this.secondEpisode.getLatestPossibleEndTime().minusDays(1).withHourOfDay(23);
        if (this.kind == RelationKind.IMMEDIATELY_BEFORE) {
            if (dayAfterFirstEpisode.isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(dayBeforeSecondEpisode)) {
                satisfied = false;
            }
        } else if (this.kind == RelationKind.BEFORE) {
            if (dayAfterFirstEpisode.isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(dayBeforeSecondEpisode)) {
                satisfied = false;
            }
        } else if (this.kind == RelationKind.FIXED_TIME_BEFORE) {
            DateTime tempEarliestPossibleDateForFirstEpisode = this.secondEpisode.getEarliestPossibleStartTime().minus(this.time).withHourOfDay(1);
            DateTime tempEarliestPossibleDateForSecondEpisode = this.firstEpisode.getEarliestPossibleStartTime().plus(this.time).withHourOfDay(1);
            DateTime tempLatestPossibleDateForFirstEpisode = this.secondEpisode.getLatestPossibleEndTime().minus(this.time).withHourOfDay(23);
            DateTime tempLatestPossibleDateForSecondEpisode = this.firstEpisode.getLatestPossibleEndTime().plus(this.time).withHourOfDay(23);
            if (tempEarliestPossibleDateForSecondEpisode.isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (tempEarliestPossibleDateForFirstEpisode.isAfter(this.firstEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(tempLatestPossibleDateForFirstEpisode)) {
                satisfied = false;
            }
            if (this.secondEpisode.getLatestPossibleEndTime().isAfter(tempLatestPossibleDateForSecondEpisode)) {
                satisfied = false;
            }
        } else if (this.kind == RelationKind.LESS_THEN_BEFORE) {
            DateTime tempEarliestPossibleDateForFirstEpisode = this.secondEpisode.getEarliestPossibleStartTime().minus(this.time).withHourOfDay(1);
            DateTime tempLatestPossibleDateForSecondEpisode = this.firstEpisode.getLatestPossibleEndTime().plus(this.time).withHourOfDay(23);
            if (dayAfterFirstEpisode.isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(dayBeforeSecondEpisode)) {
                satisfied = false;
            }
            if (tempEarliestPossibleDateForFirstEpisode.isAfter(this.firstEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.secondEpisode.getLatestPossibleEndTime().isAfter(tempLatestPossibleDateForSecondEpisode)) {
                satisfied = false;
            }
        } else if (this.kind == RelationKind.SAME_DAY_OF_YEAR) {
            satisfied &= this.mSameTimeOfYearConstraint.ConstraintSatisfied();
        } else if ((this.kind == RelationKind.SAME_TIME_AS) || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
            if (this.firstEpisode.getEarliestPossibleStartTime().withHourOfDay(1).isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.secondEpisode.getEarliestPossibleStartTime().withHourOfDay(1).isAfter(this.firstEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(this.secondEpisode.getLatestPossibleEndTime().withHourOfDay(23))) {
                satisfied = false;
            }
            if (this.secondEpisode.getLatestPossibleEndTime().isAfter(this.firstEpisode.getLatestPossibleEndTime().withHourOfDay(23))) {
                satisfied = false;
            }
        } else if (this.kind == RelationKind.MORE_THEN_BEFORE) {
            DateTime tempEarliestPossibleDateForSecondEpisode = this.firstEpisode.getEarliestPossibleStartTime().plus(this.time).withHourOfDay(1);
            DateTime tempLatestPossibleDateForFirstEpisode = this.secondEpisode.getLatestPossibleEndTime().minus(this.time).withHourOfDay(23);
            if (dayAfterFirstEpisode.isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(dayBeforeSecondEpisode)) {
                satisfied = false;
            }
            if (tempEarliestPossibleDateForSecondEpisode.isAfter(this.secondEpisode.getEarliestPossibleStartTime())) {
                satisfied = false;
            }
            if (this.firstEpisode.getLatestPossibleEndTime().isAfter(tempLatestPossibleDateForFirstEpisode)) {
                satisfied = false;
            }
        }
        return satisfied;
    }

    @Override
    public String toString() {
        String rString;
        if (this.kind == RelationKind.SAME_DAY_OF_YEAR) {
            rString = this.mSameTimeOfYearConstraint.toString();
        } else {
            rString = this.firstEpisode.toString();
            if (this.kind == RelationKind.FIXED_TIME_BEFORE) {
                rString = rString + " is exactly " + this.time.toString() + " before ";
            } else if (this.kind == RelationKind.LESS_THEN_BEFORE) {
                rString = rString + " within " + this.time.toString() + " of ";
            } else if (this.kind == RelationKind.MORE_THEN_BEFORE) {
                rString = rString + " more than " + this.time.toString() + " before ";
            } else {
                rString = rString + " " + this.kind.toString() + " ";
            }
            rString += this.secondEpisode.toString();
        }
        return rString;
    }

    public boolean consistentWithConstraint(DateTime inFirstDay, DateTime inSecondDay) {
        if ((this.kind == RelationKind.IMMEDIATELY_BEFORE) || (this.kind == RelationKind.BEFORE)) {
            return inFirstDay.isBefore(inSecondDay.withHourOfDay(1));
        } else if (this.kind == RelationKind.FIXED_TIME_BEFORE) {
            return inFirstDay.plus(this.time).equals(inSecondDay);
        } else if (this.kind == RelationKind.LESS_THEN_BEFORE) {
            DateTime tempEarliestPossibleDate = inSecondDay.minus(this.time).withHourOfDay(1);
            return inFirstDay.isAfter(tempEarliestPossibleDate) && inFirstDay.isBefore(inSecondDay.withHourOfDay(1));
        } else if (this.kind == RelationKind.SAME_DAY_OF_YEAR) {
            return (inFirstDay.getMonthOfYear() == inSecondDay.getMonthOfYear())
                    && (inFirstDay.getDayOfMonth() == inSecondDay.getDayOfMonth());
        } else if ((this.kind == RelationKind.SAME_TIME_AS) || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
            return this.mSameTimeOfYearConstraint.consistentWithConstraint(inFirstDay, inSecondDay);
        } else if (this.kind == RelationKind.MORE_THEN_BEFORE) {
            DateTime earyer = inSecondDay.minus(this.time).withHourOfDay(1);
            return inFirstDay.isBefore(earyer);
        } else {
            return true;
        }
    }

    @Override
    public boolean consistentWithConstraint(T inFirstPlacement, S inSecondPlacement) {
        DateTime inFirstStartTime;
        DateTime inFirstEndTime;
        DateTime inSecondStartTime;
        DateTime inSecondEndTime;
        if (this.firstEpisode instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inFirstPlacement;
            inFirstStartTime = curOnceDayEventPlacement.day;
            inFirstEndTime = curOnceDayEventPlacement.day;
        } else {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inFirstPlacement;
            inFirstStartTime = curOncePeriodEventPlacement.startDay;
            inFirstEndTime = curOncePeriodEventPlacement.endDay;
        }
        if (this.secondEpisode instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inSecondPlacement;
            inSecondStartTime = curOnceDayEventPlacement.day;
            inSecondEndTime = curOnceDayEventPlacement.day;
        } else {
            OncePeriodEvent.OncePeriodEventPlacement curOncePeriodEventPlacement = (OncePeriodEvent.OncePeriodEventPlacement) inSecondPlacement;
            inSecondStartTime = curOncePeriodEventPlacement.startDay;
            inSecondEndTime = curOncePeriodEventPlacement.endDay;
        }
        if ((this.kind == RelationKind.IMMEDIATELY_BEFORE) || (this.kind == RelationKind.BEFORE)) {
            return inFirstEndTime.isBefore(inSecondStartTime.withHourOfDay(1));
        } else if (this.kind == RelationKind.FIXED_TIME_BEFORE) {
            return inFirstEndTime.plus(this.time).equals(inSecondStartTime);
        } else if (this.kind == RelationKind.LESS_THEN_BEFORE) {
            DateTime tempEarliestPossibleDate = inSecondStartTime.minus(this.time).withHourOfDay(1);
            return inFirstEndTime.isAfter(tempEarliestPossibleDate) && inFirstEndTime.isBefore(inSecondStartTime.withHourOfDay(1));
        } else if (this.kind == RelationKind.SAME_DAY_OF_YEAR) {
            return this.mSameTimeOfYearConstraint.consistentWithConstraint(inFirstPlacement, inSecondPlacement);
        } else if ((this.kind == RelationKind.SAME_TIME_AS) || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
            return inFirstStartTime.equals(inSecondStartTime) && inFirstEndTime.equals(inSecondEndTime);
        } else if (this.kind == RelationKind.MORE_THEN_BEFORE) {
            DateTime earyer = inSecondStartTime.minus(this.time).withHourOfDay(1);
            return inFirstEndTime.isBefore(earyer);
        } else {
            return true;
        }
    }

    @Override
    public Event[] increaseWhat(Placement<?> inValues[]) {
        DateTime inFirstStartTime;
        DateTime inFirstEndTime;
        DateTime inSecondStartTime;
        DateTime inSecondEndTime;
        if (this.firstEpisode instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[0];
            inFirstStartTime = curOnceDayEventPlacement.day;
            inFirstEndTime = curOnceDayEventPlacement.day;
        } else {
            return new Event[]{this.firstEpisode, this.secondEpisode};
        }
        if (this.secondEpisode instanceof OnceDayEvent) {
            OnceDayEvent.OnceDayEventPlacement curOnceDayEventPlacement = (OnceDayEvent.OnceDayEventPlacement) inValues[1];
            inSecondStartTime = curOnceDayEventPlacement.day;
            inSecondEndTime = curOnceDayEventPlacement.day;
        } else {
            return new Event[]{this.firstEpisode, this.secondEpisode};
        }
        if ((this.kind == RelationKind.IMMEDIATELY_BEFORE) || (this.kind == RelationKind.BEFORE)) {
            if (!inFirstEndTime.isBefore(inSecondStartTime.withHourOfDay(1))) {
                return new Event[]{this.secondEpisode};
            }
        } else if (this.kind == RelationKind.FIXED_TIME_BEFORE) {
            //inFirstEndTime.plus(this.time).equals(inSecondStartTime);
            DateTime later = inFirstEndTime.plus(this.time);
            if (later.isBefore(inSecondStartTime)) {
                return new Event[]{this.firstEpisode};
            } else if (later.isAfter(inSecondStartTime)) {
                return new Event[]{this.secondEpisode};
            }
        } else if (this.kind == RelationKind.LESS_THEN_BEFORE) {
            DateTime tempEarliestPossibleDate = inSecondStartTime.minus(this.time).withHourOfDay(1);
            if (!inFirstEndTime.isAfter(tempEarliestPossibleDate)) {
                return new Event[]{this.firstEpisode};
            }
            if (!inFirstEndTime.isBefore(inSecondStartTime.withHourOfDay(1))) {
                return new Event[]{this.secondEpisode};
            }
        } else if (this.kind == RelationKind.SAME_DAY_OF_YEAR) {
            return this.mSameTimeOfYearConstraint.increaseWhat(inValues);
        } else if ((this.kind == RelationKind.SAME_TIME_AS) || (this.kind == RelationKind.BEFORE_SAME_DAY)) {
            //inFirstStartTime.equals(inSecondStartTime) && inFirstEndTime.equals(inSecondEndTime);
            if (inFirstStartTime.isBefore(inSecondStartTime) || inFirstEndTime.isBefore(inSecondEndTime)) {
                return new Event[]{this.firstEpisode};
            } else if (inFirstStartTime.isAfter(inSecondStartTime) || inFirstEndTime.isAfter(inSecondEndTime)) {
                return new Event[]{this.secondEpisode};
            }
        } else if (this.kind == RelationKind.MORE_THEN_BEFORE) {
            DateTime earyer = inSecondStartTime.minus(this.time).withHourOfDay(1);
            if (!inFirstEndTime.isBefore(earyer)) {
                return new Event[]{this.secondEpisode};
            }
        }
        return new Event[]{};
    }

    public static enum RelationKind {

        IMMEDIATELY_BEFORE, BEFORE, FIXED_TIME_BEFORE, LESS_THEN_BEFORE, SAME_DAY_OF_YEAR, SAME_TIME_AS, MORE_THEN_BEFORE, BEFORE_SAME_DAY
    }

    public static class RelationXMLWriter extends IDedObjectXMLWriter<Relation> {

        @com.civprod.dynamicClassLoading.ClassInitalizer
        public static final void init() {
            XMLWriterImp.addXMLWriter(Relation.class, new RelationXMLWriter());
        }

        @Override
        public Element writeElements(Relation ObjectToWrite) {
            Element newElement = new Element("Relation");
            this.writeElements(ObjectToWrite, newElement);
            Element firstEpisodeElement = new Element("firstEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.firstEpisode, firstEpisodeElement);
            newElement.addContent(firstEpisodeElement);
            Element secondEpisodeElement = new Element("secondEventByID");
            EventImp.EventIDXMLWriter.instance.writeElements(ObjectToWrite.secondEpisode, secondEpisodeElement);
            newElement.addContent(secondEpisodeElement);
            Element kindElement = new Element("kind");
            kindElement.setText(ObjectToWrite.kind.name());
            newElement.addContent(kindElement);
            if (ObjectToWrite.time != null) {
                Element TimeElement = new Element("time");
                XMLWriter<ReadablePeriod> ReadablePeriodWriter = XMLWriterImp.getXMLWriter(ReadablePeriod.class);
                TimeElement.addContent(ReadablePeriodWriter.writeElements(ObjectToWrite.time));
                newElement.addContent(TimeElement);
            }
            return newElement;
        }

        @Override
        public Relation<?,?> readElements(org.jdom2.Element root) {
            return new Relation<>(root);
        }
    }
}
