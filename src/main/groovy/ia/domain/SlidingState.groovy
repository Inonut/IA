package ia.domain

import groovy.transform.Sortable
import ia.game.Action
import ia.util.Util
import jdk.nashorn.internal.objects.annotations.Getter

/**
 * Created by Dragos on 23.03.2016.
 */
class SlidingState extends State{

    private int[][] currentSolution
    private int x
    private int y
    private Action actionFromParent

    int[][] getCurrentSolution() {
        return currentSolution
    }

    void setCurrentSolution(int[][] currentSolution) {
        this.currentSolution = currentSolution
    }

    int getX() {
        return x
    }

    void setX(int x) {
        this.x = x
    }

    int getY() {
        return y
    }

    void setY(int y) {
        this.y = y
    }

    Action getActionFromParent() {
        return actionFromParent
    }

    void setActionFromParent(Action actionFromParent) {
        this.actionFromParent = actionFromParent
    }

    @Override
    boolean equals(Object obj) {
        if(obj == null){
            return false
        }
        if(!obj instanceof SlidingState){
            return false
        }
        SlidingState slidingState = obj as SlidingState
        return Util.sumDiff(slidingState.currentSolution, currentSolution) == 0
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new SlidingState(x: x, y: y, currentSolution: currentSolution.collect {it.collect {it}})
    }
}
