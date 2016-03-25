package ia.game.sliding

import ia.domain.SlidingState
import ia.game.Game

/**
 * Created by Dragos on 21.03.2016.
 */
class SlidingGame implements Game<SlidingState>{

    private int dim
    private SlidingState initState
    private SlidingState finalState
    private SlidingState currentState

    def SlidingGame(def initState, def finalState) {
        this.initState = new SlidingState(currentSolution: initState as int[][])
        this.finalState = new SlidingState(currentSolution: finalState as int[][])

    }

    @Override
    def void start() {
        this.currentState = this.initState.clone() as SlidingState
        this.dim = this.initState.currentSolution.length

        getWhiteSquere(this.initState)
        getWhiteSquere(this.finalState)
        getWhiteSquere(this.currentState)
    }

    private static def getWhiteSquere(SlidingState state){
        int x,y
        state.currentSolution.eachWithIndex{ int[] entry, int i ->
            entry.eachWithIndex{ int entryj, int j ->
                if(entryj == 0){
                    (x,y)=[i,j]
                }
            }
        }

        state.x = x
        state.y = y
    }

    @Override
    def boolean isOver() {

        return [this.currentState.currentSolution, this.finalState.currentSolution].transpose().stream().allMatch{a,b ->
            [a,b].transpose().stream().allMatch {q,w -> q==w }
        }
    }


    @Override
    def void printCurrentState(){
        println ""
        currentState.currentSolution.each {
            it.each {print "$it "}
            println ""
        }
    }

    @Override
    def List getActions() {
        return [[WhiteSqure, [WhiteSqure.Target.UP]],
                [WhiteSqure, [WhiteSqure.Target.DOWN]],
                [WhiteSqure, [WhiteSqure.Target.LEFT]],
                [WhiteSqure, [WhiteSqure.Target.RIGHT]]]
    }

    @Override
    def SlidingState getCurrentState() {
        return this.currentState
    }


    @Override
    public String toString() {
        return "SlidingGame{" +
                "ia_game_Game__score=" + ia_game_Game__score +
                ", dim=" + dim +
                ", initState=" + initState +
                ", finalState=" + finalState +
                ", currentState=" + currentState +
                '}';
    }

    int getDim() {
        return dim
    }

    void setDim(int dim) {
        this.dim = dim
    }

    SlidingState getInitState() {
        return initState
    }

    void setInitState(SlidingState initState) {
        this.initState = initState
    }

    SlidingState getFinalState() {
        return finalState
    }

    void setFinalState(SlidingState finalState) {
        this.finalState = finalState
    }

    @Override
    def setCurrentState(SlidingState currentState) {
        this.currentState = currentState
    }
}
