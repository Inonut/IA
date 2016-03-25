package ia.game.sliding

import ia.domain.SlidingState
import ia.game.Action

import static ia.game.sliding.WhiteSqure.Target.*

/**
 * Created by Dragos on 22.03.2016.
 */
public class WhiteSqure implements Action<SlidingGame, SlidingState>{


    static enum Target{
        UP, DOWN, LEFT, RIGHT
    }

    private Target target
    private SlidingGame game

    def WhiteSqure(Target target){
        this.target = target
    }

    @Override
    def void setSource(SlidingGame elem) {
        this.game = elem
    }

    @Override
    def boolean verify() {
        switch (target){
            case UP : return game.currentState.x-1>=0
            case DOWN : return game.currentState.x+1 < game.dim
            case LEFT : return game.currentState.y-1 >=0
            case RIGHT : return game.currentState.y+1 < game.dim
        }

    }

    @Override
    def SlidingState execute() {

        if(!verify()) return;

        SlidingState result = game.currentState.clone() as SlidingState

        switch (target){
            case UP :
                result.with {
                    currentSolution[x][y] =currentSolution[x-1][y]
                    currentSolution[x-1][y] = 0
                    x--
                }
                break
            case DOWN :
                result.with {
                    currentSolution[x][y] =currentSolution[x+1][y]
                    currentSolution[x+1][y] = 0
                    x++
                }
                break
            case LEFT :
                result.with {
                    currentSolution[x][y] =currentSolution[x][y-1]
                    currentSolution[x][y-1] = 0
                    y--
                }
                break
            case RIGHT :
                result.with {
                    currentSolution[x][y] =currentSolution[x][y+1]
                    currentSolution[x][y+1] = 0
                    y++
                }
                break
        }

        return result
    }

    @Override
    def getTarget() {
        return this.target
    }

    @Override
    def SlidingState rollback() {
        switch (target){
            case UP : return game.next(new WhiteSqure(DOWN)) as SlidingState
            case DOWN : return game.next(new WhiteSqure(UP)) as SlidingState
            case LEFT : return game.next(new WhiteSqure(RIGHT)) as SlidingState
            case RIGHT : return game.next(new WhiteSqure(LEFT)) as SlidingState
        }
    }


    @Override
    public String toString() {
        return "WhiteSqure{" +
                "target=" + target +
                ", game=" + game +
                '}';
    }

}
