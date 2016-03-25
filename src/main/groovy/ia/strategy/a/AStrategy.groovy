package ia.strategy.a

import ia.strategy.Strategy

/**
 * Created by Dragos on 24.03.2016.
 */
class AStrategy extends Strategy {


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
                return;
            }

            List succesores = actions.collect {it.execute()}
                    .findAll()
                    .collect {it.parent = game.currentState; return it}
                    //.sort {a,b -> game.calculateScore(a,game.finalState) - game.calculateScore(b,game.finalState)}

            succesores = succesores.findAll { !openList.contains(it) && !closedList.contains(it)}.collect()

            openList += succesores
            //openList = openList.sort {a,b -> game.calculateScore(a,game.finalState) - game.calculateScore(b,game.finalState)}.collect()

        }

    }

    @Override
    def prepareResult() {
        return null
    }
}
