package ia.game

/**
 * Created by Dragos on 22.03.2016.
 */
trait Score<S> {

    def abstract int getScore(S oldState, S newState)
}
