package eu.builderscoffee.expresso.buildbattle.expressos.types;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.PrePlotPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.WaitingPhase;

import java.util.List;
import java.util.Queue;

public class IlClassicoExpresso extends Expresso {

    private final BBGame gameInstance = Main.getBbGame();
    private List<BBPhase> phases;

    public IlClassicoExpresso(Main main) {
        super(main);
    }

    /***
     * Retourne les phases d'un expresso classic
     * @return
     */
    @Override
    public Queue<BBPhase> getPhases() {
        phases.add(new WaitingPhase(gameInstance));
        phases.add(new PrePlotPhase(gameInstance));
        return super.getPhases();
    }
}
