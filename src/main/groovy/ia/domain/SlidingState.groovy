package ia.domain

import groovy.transform.Sortable
import ia.util.Util

/**
 * Created by Dragos on 23.03.2016.
 */
class SlidingState extends State{

    private int[][] currentSolution
    private int x
    private int y

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


    @Override
    public String toString() {
        return "x=$x y=$y ${if(parent!= null && parent instanceof SlidingState) return "parentx=$parent.x parenty=$parent.y"}"
    }

    @Override
    boolean equals(Object o) {
        if(o == null ) {
            return false
        }
        int n = 0;
        Object obj = o
        if(o instanceof List){
            n=o[1]
            obj=o[0]
        }

        if(obj == null ) {
            return false
        }
        if(!obj instanceof SlidingState){
            return false
        }
        SlidingState slidingState = obj as SlidingState

        if(n>=Util.deep){
            return slidingState.x == x && slidingState.y == y
        } else{
            return slidingState.x == x && slidingState.y == y &&
                    (this.parent != null ? this.parent.equals([slidingState.parent,n+1]) : slidingState.parent == null)
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new SlidingState(x: x, y: y, currentSolution: currentSolution.collect {it.collect {it}})
    }
}
