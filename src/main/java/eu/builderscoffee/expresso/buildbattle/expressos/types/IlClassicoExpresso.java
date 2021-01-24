package eu.builderscoffee.expresso.buildbattle.expressos.types;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.PrePlotPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.WaitingPhase;

import java.util.List;

public class IlClassicoExpresso extends Expresso {

    private final BBGame gameInstance = Main.getBbGame();
    private List<BBPhase> phases;

    public IlClassicoExpresso(Main main) {
        super(main);
    }

    @Override
    public void onExpressoStart() {
    }

    /***
     * Retourne les phases d'un expresso classic
     * @return
     */
    @Override
    public List<BBPhase> getPhases() {
        phases.add(new WaitingPhase(gameInstance));
        phases.add(new PrePlotPhase(gameInstance));
        return super.getPhases();
    }
}
