package ia.strategy

import ia.game.Action
import ia.game.Game

/**
 * Created by Dragos on 21.03.2016.
 */
abstract class Strategy {

    protected Game game
    protected List<Action> actions

    def resolve(Game game){
        game.start()

        this.game = game

        actions = game.actions.collect {it[0].newInstance(it[1] as Object[]).with{it.source = this.game; it}}

        resolve()


    }

    protected def abstract resolve()


    def abstract prepareResult(Closure closure)

}