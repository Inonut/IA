package ia

import ia.domain.SlidingState
import ia.game.Game
import ia.game.Score
import ia.game.sliding.MapingScore
import ia.game.sliding.MenhattenScore
import ia.game.sliding.SlidingGame
import ia.game.sliding.WhiteSqure
import ia.strategy.Strategy
import ia.strategy.a.AStrategy
import ia.strategy.ida.IDAStrategy

import static ia.game.sliding.WhiteSqure.Target.DOWN
import static ia.game.sliding.WhiteSqure.Target.RIGHT
import static ia.game.sliding.WhiteSqure.Target.UP

/**
 * Created by Dragos on 21.03.2016.
 */
class Main {

    def static void main(String ...args){


        //Game game = new SlidingGame([[1,2,3],[4,5,6],[7,8,0]],[[1,2,3],[4,5,6],[7,8,0]])
        //Game game = new SlidingGame([[1,2,0],[4,5,3],[7,8,6]],[[1,2,3],[4,5,6],[7,8,0]])
        //Game game = new SlidingGame([[3,2,1],[4,5,6],[0,8,7]],[[1,2,3],[4,5,6],[7,8,0]])
        Game game = new SlidingGame([[1,2,3],[4,0,6],[7,5,8]],[[1,2,3],[4,5,6],[7,8,0]])
        //Game game = new SlidingGame([[2,8,3],[1,6,4],[7,0,5]],[[1,2,3],[4,5,6],[7,8,0]])
        //Game game = new SlidingGame([[1,2,3],[7,5,6],[4,8,0]],[[1,2,3],[4,5,6],[7,8,0]])
        game.score = new MenhattenScore()

        Strategy strategy = new AStrategy()
        strategy.resolve(game)
        strategy.prepareResult()


        /*game.start()

        game.next(new WhiteSqure(WhiteSqure.Target.UP))
        game.printCurrentState()
        game.next(new WhiteSqure(WhiteSqure.Target.UP))
        game.printCurrentState()
        game.next(new WhiteSqure(WhiteSqure.Target.LEFT))
        game.printCurrentState()

        println game.calculateScore(score)*/


    }
}
