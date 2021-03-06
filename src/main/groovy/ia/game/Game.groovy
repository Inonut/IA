package ia.game

import ia.strategy.State

/**
 * Created by Dragos on 21.03.2016.
 */
trait Game<S> {

    private Score score

    def abstract void start()

    def abstract boolean isOver()

    def abstract List getActions()

    def abstract S getCurrentState()

    def abstract S getInitState()

    def abstract S getFinalState()

    def abstract void printCurrentState()

    def abstract setCurrentState(S currentState)

    def setScore(Score score){
        this.score=score
    }


    def next(Action<Game,S> action){
        action.setSource(this)
        if(action.verify()){
            setCurrntState(action.execute())
            return true
        }

        return false
    }

    def prev(Action<Game,S> action){
        action.setSource(this)
        action.rollback()
    }

    int calculateScore(S oldState, S newState){
        if(this.score == null){
            this.score = score
        }

        return this.score.getScore(oldState, newState)
    }
}