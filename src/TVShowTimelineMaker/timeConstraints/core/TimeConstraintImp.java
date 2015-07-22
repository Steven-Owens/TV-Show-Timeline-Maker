/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TVShowTimelineMaker.timeConstraints.core;

import TVShowTimelineMaker.Main;
import TVShowTimelineMaker.timeConstraints.interfaces.TimeConstraint;
import TVShowTimelineMaker.util.IDedObjectImp;
import org.jdom2.Element;

/**
 *
 * @author Steven Owens
 */
public abstract class TimeConstraintImp extends IDedObjectImp implements TimeConstraint {
    private static final long serialVersionUID = 25634L;
    private final boolean mSynthetic;
    
    private static int count = 0;
    
    private static int setIDForTimeConstraint(boolean tempEvent) {
        int rid = -1;
        if (!tempEvent) {
            rid = count;
            count++;
        }
        return rid;
    }
    
    public TimeConstraintImp(boolean isSynthetic){
        this(isSynthetic, Main.myShow.getNameSpace(), setIDForTimeConstraint(isSynthetic));
    }
    
    protected TimeConstraintImp(boolean isSynthetic, String inRootNamespace, int inMyID){
        super(inRootNamespace, "TimeConstraint", inMyID);
        mSynthetic = isSynthetic;
    }
    
    protected TimeConstraintImp(Element root){
        super(root, "TimeConstraint");
        mSynthetic = false;
        if (this.getLeastSignificantIDPart() >= count) {
            count = getLeastSignificantIDPart() + 1;
        }
    }
    
    public boolean isSynthetic(){
        return mSynthetic;
    }
}
