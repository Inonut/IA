package ia.game.sliding

import ia.domain.SlidingState
import ia.domain.State
import ia.game.Score

/**
 * Created by Dragos on 24.03.2016.
 */
class MenhattenScore implements Score<SlidingState>{

    @Override
    int getScore(SlidingState oldState, SlidingState newState) {
        int score = 0

        oldState.currentSolution.eachWithIndex { line, i ->
            line.eachWithIndex { value, j ->
                int x,y
                newState.currentSolution.eachWithIndex{ int[] entry, int ii ->
                    entry.eachWithIndex{ int entryj, int jj ->
                        if(entryj == value){
                            (x,y)=[ii,jj]
                        }
                    }
                }

                score += Math.abs(x-i)+Math.abs(y-j)
            }
        }

        return score
    }
}
