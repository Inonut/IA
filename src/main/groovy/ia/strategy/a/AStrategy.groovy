package ia.strategy.a

import com.sun.javafx.application.PlatformImpl
import groovyx.gpars.GParsPool
import ia.domain.SlidingState
import ia.domain.State
import ia.strategy.Strategy
import javafx.application.Platform
import javafx.scene.control.Alert

/**
 * Created by Dragos on 24.03.2016.
 */
class AStrategy extends Strategy {

    private State currentState;
    private Closure closure;
    private int count = 0

    private print(State state){
        if(state != null){
            print(state.parent)

            if(closure != null){
                closure(state)
            }

            println "\n${++count}"
            state.currentSolution.each {
                it.each {print "$it "}
                println ""
            }
        }
    }


    @Override
    protected resolve() {

        GParsPool.withPool {

            def openList = [game.currentState]
            def closedList = []

            while(true){
                println openList.size() + "    " + closedList.size()

                if(openList.size() >= 500){

                    def msg = "Nu s-a putut gasi o solutie exacta, dar o solutie apoximativa este"
                    println msg
                    if(this.closure != null){
                        this.closure(msg)
                    }

                    currentState = game.currentState
                    game.currentState = closedList[0]

                    count = 0;
                    print(currentState)

                    return;
                }

                if(openList.size() == 0){

                    def msg = "Nu s-a putut gasi o solutie"
                    println msg
                    if(this.closure != null){
                        this.closure(msg)
                    }

                    return;
                }
                game.currentState = openList.minParallel {a,b -> game.calculateScore(a,game.finalState) - game.calculateScore(b,game.finalState)}
                openList.remove(game.currentState)
                closedList.add(game.currentState)

                if(game.isOver()){
                    currentState = game.currentState
                    game.currentState = closedList[0]

                    count = 0;
                    print(currentState)

                    return;
                }


                openList += actions.collectParallel {action -> return action.clone().execute() }
                        .findAllParallel {it != null && !it.equals(game.currentState.parent)}
                        .collectParallel {it.parent = game.currentState; return it}
                        .findAllParallel { elem -> openList.count {elem.equals(it)} + closedList.count {elem.equals(it)} < 1 }
                        .collectParallel {it}
            }
        }

    }

    @Override
    def prepareResult(Closure closure) {
        this.closure = closure
    }
}
