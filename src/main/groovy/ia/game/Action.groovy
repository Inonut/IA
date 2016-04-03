package ia.game

import java.util.function.Consumer

/**
 * Created by Dragos on 21.03.2016.
 */
trait Action<T,R> implements Cloneable{

    private List listener = new ArrayList()

    def abstract void setSource(T elem)

    def abstract boolean verify()

    def abstract R execute()

    def abstract getTarget()

    def abstract Action rollback()

    def void addListener(Closure closure){
        listener += closure
    }

    def void removeListener(Closure closure){
        listener -= closure
    }
}