package ia.game

import java.util.function.Consumer

/**
 * Created by Dragos on 21.03.2016.
 */
trait Action<T,R>{

    def abstract void setSource(T elem)

    def abstract boolean verify()

    def abstract R execute()

    def abstract getTarget()

    def abstract R rollback()
}