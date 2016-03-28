package ia.strategy.ida

import ia.domain.State
import ia.strategy.Strategy

/**
 * Created by Dragos on 22.03.2016.
 */
class IDAStrategy extends Strategy{


    def IDAStrategy(){
    }

    @Override
    def resolve() {
        def bound = game.calculateScore(game.currentState,game.finalState)
        while(true){
            int t = search(game.currentState,0,bound)
            if(t==0) break
            if(t==Integer.MAX_VALUE) throw new Exception("Nu reusesc!")
            bound = t
        }

    }

    @Override
    def prepareResult(Closure closure) {
        return null
    }

    private def search(State node, int g, int bound) {
        int f = g + game.calculateScore(game.currentState,game.finalState)
        if(f > bound) return f
        if(game.isOver()) return 0
        int min = Integer.MAX_VALUE

        List succesores = actions.collect {it.execute()}
                .findAll()
                .collect {it.parent = node; return it}
                .collect { elem ->
                    if(!elem.equals(node.parent)) {
                        return elem
                    }
                }
                .findAll()
                .sort {a,b -> game.calculateScore(a,game.finalState) - game.calculateScore(b,game.finalState)}

        succesores.each {
            println ""
            it.currentSolution.each {
                it.each {print "$it "}
                println ""
            }
        }

        succesores.each {succ ->
            def localState = game.currentState
            game.currentState = succ
            int t = search(succ, g + game.calculateScore(node,succ), bound)
            game.currentState = localState
            if(t==0) return 0
            if(t < min) min = t
        }
        return min
    }


}
