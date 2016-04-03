package ia.game.sliding

import ia.domain.SlidingState
import ia.game.Score
import ia.util.Util

/**
 * Created by Dragos on 22.03.2016.
 */
class MapingScore implements Score<SlidingState>{


    @Override
    def int getScore(SlidingState oldState, SlidingState newState) {
        return Util.sumDiff(oldState.currentSolution, newState.currentSolution)
    }
}
