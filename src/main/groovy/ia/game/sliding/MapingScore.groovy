package ia.game.sliding

import ia.domain.SlidingState
import ia.game.Score

/**
 * Created by Dragos on 22.03.2016.
 */
class MapingScore implements Score<SlidingState>{


    @Override
    int getScore(SlidingState oldState, SlidingState newState) {
        return [oldState.currentSolution, newState.currentSolution].transpose().collect{a,b ->
            [a,b].transpose().collect {q,w -> q==w?0:1 }
        }.flatten().sum() as int
    }
}
