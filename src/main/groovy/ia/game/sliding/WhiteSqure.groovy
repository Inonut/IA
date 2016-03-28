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

        result.actionFromParent = this
        return result
    }

    @Override
    def getTarget() {
        return this.target
    }

    @Override
    def Action rollback() {
        switch (target){
            case UP : return new WhiteSqure(DOWN)
            case DOWN : return new WhiteSqure(UP)
            case LEFT : return new WhiteSqure(RIGHT)
            case RIGHT : return new WhiteSqure(LEFT)
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
