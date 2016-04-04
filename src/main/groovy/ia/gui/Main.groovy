package ia.gui

import com.sun.javafx.application.PlatformImpl
import groovyx.javafx.GroovyFX
import ia.domain.State
import ia.game.Game
import ia.game.sliding.MenhattenScore
import ia.game.sliding.SlidingGame
import ia.strategy.Strategy
import ia.strategy.a.AStrategy
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.layout.Priority

/**
 * Created by Dragos on 28.03.2016.
 */

def nrStep = new SimpleStringProperty()

Thread.start {
    GroovyFX.start {
        stage(title: 'Sliding puzzle', visible: true, resizable: false, onCloseRequest: {Platform.exit(); System.exit(0);}) {
            scene(fill: "BLACK", width: 250, height: 300) {
                vbox(padding: 10) {
                    gridPane(alignment: "center", vgrow: "ALWAYS", id: "panel"){
                        columnConstraints(halignment: "center", hgrow: "ALWAYS")
                        columnConstraints(halignment: "center", hgrow: "ALWAYS")
                        columnConstraints(halignment: "center", hgrow: "ALWAYS")
                        rowConstraints(valignment: "center", vgrow: "ALWAYS")
                        rowConstraints(valignment: "center", vgrow: "ALWAYS")
                        rowConstraints(valignment: "center", vgrow: "ALWAYS")

                        int n = 3
                        int k = 0
                        for(int i=0;i<n;i++){
                            for(int j=0;j<n;j++){
                                button("${(++k)%9}",
                                        row: i,
                                        column: j,
                                        style: "-fx-background-color: #BC8F8F; -fx-padding: 10 20 10 20; -fx-font-size: 40;",
                                        onAction: {e->

                                            List coordThis = owner.panel.children.findIndexOf{it.text == e.target.text}.collect{[it/3 as int, it%3 as int, it]}.flatten()
                                            List coord0 = owner.panel.children.findIndexOf{it.text == "0"}.collect{[it/3 as int, it%3 as int, it]}.flatten()

                                            if((coordThis[0]+1 == coord0[0] || coordThis[0]-1 == coord0[0]) && coordThis[1] == coord0[1] ||
                                                    (coordThis[1]+1 == coord0[1] || coordThis[1]-1 == coord0[1])  && coordThis[0] == coord0[0]){

                                                def aux = owner.panel.children[coordThis[2]].text
                                                owner.panel.children[coordThis[2]].text = owner.panel.children[coord0[2]].text
                                                owner.panel.children[coord0[2]].text = aux
                                            }

                                            //e.target.text = ((e.target.text as int) + 1) %9
                                        }
                                )

                            }
                        }

                    }
                    hbox(spacing: 20){
                        button("Amesteca",
                                onAction: {e ->
                                    List list = (0..8).collect()
                                    Collections.shuffle(list)
                                    [list, owner.panel.children].transpose().collect { a, b ->
                                        b.text = a
                                    }
                                }
                        )
                        button("Rezolva",
                                onAction: { e ->
                                    List list = owner.panel.children*.text.collect {it as int}
                                    Game game = new SlidingGame([list.take(3),list.drop(3).take(3),list.drop(3).drop(3).take(3)],[[1, 2, 3], [4, 5, 6], [7, 8, 0]])
                                    game.score = new MenhattenScore()

                                    game.addListener {state ->
                                        PlatformImpl.runAndWait{
                                            [state.currentSolution.collect{it.collect()}.flatten(), owner.panel.children].transpose().collect{a,b ->
                                                b.text = a
                                            }
                                        }
                                        Thread.sleep(300)
                                    }

                                    Strategy strategy = new AStrategy()
                                    strategy.prepareResult { msg ->

                                        switch (msg){
                                            case String :
                                                PlatformImpl.runAndWait{
                                                    new Alert(Alert.AlertType.INFORMATION).with {
                                                        it.setContentText(msg)
                                                        it.showAndWait()
                                                    }
                                                }
                                                break
                                            case List :
                                                PlatformImpl.runAndWait {
                                                    nrStep.set(msg[1].toString())
                                                }
                                                msg[0].actionFromParent == null?: game.next(msg[0].actionFromParent)
                                                break
                                        }
                                    }

                                    Thread.start {
                                        e.target.disable = true
                                        strategy.resolve(game)
                                        e.target.disable = false
                                    }

                                }
                        )
                        label("Nr. pasi")
                        label( text: bind(nrStep) )
                    }
                }
            }
        }
    }
}

