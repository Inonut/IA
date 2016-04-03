package ia.domain

import groovy.transform.Sortable
import ia.game.Action
import ia.util.Util
import jdk.nashorn.internal.objects.annotations.Getter

/**
 * Created by Dragos on 23.03.2016.
 */
class SlidingState extends State{

    def int[][] currentSolution
    def int x
    def int y
    def Action actionFromParent

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
