/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.core;

import TVShowTimelineMaker.timeConstraints.interfaces.TwoEventTimeConstraint;
import TVShowTimelineMaker.timeline.Event;
import TVShowTimelineMaker.timeline.PlacementEvent.Placement;
import org.jdom2.Element;

/**
 *
 * @author Steven Owens
 */
public abstract class AbstractTwoEventTimeConstraint<T extends Placement<?>,S extends Placement<?>> extends AbstractTimeConstraint implements TwoEventTimeConstraint<T,S> {

    public AbstractTwoEventTimeConstraint(boolean isSynthetic) {
        super(isSynthetic);
    }
    
    protected AbstractTwoEventTimeConstraint(boolean isSynthetic, String inRootNamespace, int inMyID){
        super(isSynthetic, inRootNamespace, inMyID);
    }
    
    protected AbstractTwoEventTimeConstraint(Element root){
        super(root);
    }
    
    @Override
    public abstract Event[] getConstrainedEvents();

    @Override
    public boolean consistentWithConstraint(Placement<?>[] inValues) {
        return consistentWithConstraint((T)inValues[0],(S)inValues[1]);
    }
    
    @Override
    public abstract boolean consistentWithConstraint(T inFirstPlacement, S inSecondPlacement);

    @Override
    public abstract Event[] increaseWhat(Placement<?>[] inValues);

    @Override
    public abstract boolean inBeta();

    @Override
    public Event getFirstEvent() {
        return getConstrainedEvents()[0];
    }

    @Override
    public Event getSecondEvent() {
        return getConstrainedEvents()[1];
    }

    @Override
    public abstract boolean isStrictlyBefore();
    
}
