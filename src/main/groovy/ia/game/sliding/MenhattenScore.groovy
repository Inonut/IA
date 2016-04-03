package ia.game.sliding

import ia.domain.SlidingState
import ia.domain.State
import ia.game.Score

/**
 * Created by Dragos on 24.03.2016.
 */
class MenhattenScore implements Score<SlidingState>{

    @Override
    def int getScore(SlidingState oldState, SlidingState newState) {
        int score = 0

        oldState.currentSolution.eachWithIndex { line, x ->
            line.eachWithIndex { elem, y ->
                List coord = newState.currentSolution.collect {it.collect {it}}.flatten().findIndexOf {it==elem}.collect {[it/3 as int, it%3 as int]}.flatten()
                score += Math.abs(coord[0]-x) + Math.abs(coord[1]-y)
            }
        }

        return score
    }
}
