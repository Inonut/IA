package ia.gui

import com.sun.javafx.application.PlatformImpl
import groovyx.javafx.GroovyFX
import ia.game.Game
import ia.game.sliding.MenhattenScore
import ia.game.sliding.SlidingGame
import ia.strategy.Strategy
import ia.strategy.a.AStrategy
import javafx.application.Platform
import javafx.scene.layout.Priority

/**
 * Created by Dragos on 28.03.2016.
 */

GroovyFX.start {
    stage(title: 'Sliding puzzle', visible: true, resizable: false) {
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
                            button("${k++}",
                                    row: i,
                                    column: j,
                                    style: "-fx-background-color: #2f4f4f; -fx-padding: 10 20 10 20; -fx-font-size: 40;",
                                    onAction: {e->
                                        e.target.text = ((e.target.text as int) + 1) %9
                                    }
                            )

                        }
                    }

                }
                hbox(){
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

                            Strategy strategy = new AStrategy()
                            strategy.resolve(game)
                            strategy.prepareResult { mat ->
                                PlatformImpl.runAndWait{
                                    [mat.collect{it.collect()}.flatten(), owner.panel.children].transpose().collect{ a, b ->
                                        b.text = a
                                    }
                                }

                                Thread.sleep(500)
                            }

                        }
                    )
                }
            }
        }
    }
}
