package ia.strategy.a

import ia.domain.SlidingState
import ia.strategy.Strategy

/**
 * Created by Dragos on 24.03.2016.
 */
class AStrategy extends Strategy {

    private SlidingState currentState;

    private print(SlidingState state){
        if(state != null){
            print(state.parent)

            println ""
            state.currentSolution.each {
                it.each {print "$it "}
                println ""
            }
        }
    }


    @Override
    protected resolve() {

        def openList = [game.currentState]
        def closedList = []


        while(true){
            if(openList.size() == 0){
                return;
            }
            game.currentState = openList.min {a,b -> game.calculateScore(a,game.finalState) - game.calculateScore(b,game.finalState)}
            openList.remove(game.currentState)
            closedList.add(game.currentState)

            if(game.isOver()){
                currentState = game.currentState
                return;
            }

            List succesores = actions.collect {it.execute()}
                    .findAll()
                    .collect {it.parent = game.currentState; return it}
                    .collect { elem ->
                        if(!elem.equals(game.currentState.parent)) {
                            return elem
                        }
                    }
                    .findAll()
                    //.sort {a,b -> game.calculateScore(a,game.finalState) - game.calculateScore(b,game.finalState)}

            //succesores = succesores.findAll { !openList.contains(it) && !closedList.contains(it)}.collect()
            succesores = succesores.findAll { elem ->
                openList.count {elem.equals(it)} + closedList.count {elem.equals(it)} < 1
            }.collect()

            openList += succesores
            //openList = openList.sort {a,b -> game.calculateScore(a,game.finalState) - game.calculateScore(b,game.finalState)}.collect()

        }

    }

    @Override
    def prepareResult() {
        print(currentState)
    }
}
