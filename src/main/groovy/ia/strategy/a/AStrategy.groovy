package ia.strategy.a

import com.sun.javafx.application.PlatformImpl
import ia.domain.SlidingState
import ia.strategy.Strategy
import javafx.application.Platform
import javafx.scene.control.Alert

/**
 * Created by Dragos on 24.03.2016.
 */
class AStrategy extends Strategy {

    private SlidingState currentState;
    private Closure closure;

    private print(SlidingState state){
        if(state != null){
            print(state.parent)

            if(closure != null){
                closure(state)
            }

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
                game.currentState = closedList[0]
                return;
            }

            List succesores = actions.collect {action ->
                        def state = action.execute();
                        if(state != null){
                            state.actionFromParent = action.clone()
                            state.parent = game.currentState
                        }
                        return state
                    }
                    .findAll()
//                    .collect {it.parent = game.currentState; return it}
                    .collect { elem ->
                        if(!elem.equals(game.currentState.parent)) {
                            return elem
                        }
                    }
                    .findAll()
                    .findAll { elem ->
                        openList.count {elem.equals(it)} + closedList.count {elem.equals(it)} < 1
                    }.collect()

            openList += succesores

        }

    }

    @Override
    def prepareResult(Closure closure) {
        this.closure = closure
        if(currentState == null){
            PlatformImpl.runAndWait{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Nu s-a putut gasi o solutie");

                alert.showAndWait();
            }
        } else {
            print(currentState)
        }


    }
}
