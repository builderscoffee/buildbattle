package eu.builderscoffee.expresso.buildbattle.expressos.types;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.LaunchingPhase;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class IlClassicoExpresso extends Expresso {

    private final BBGame gameInstance = Main.getBbGame();
    private List<BBPhase> phases;

    public IlClassicoExpresso(Main main) {
        super(main);
    }

    @Override
    public String getName() {
        return "IlClassico";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("La compétition classique");
    }

    /***
     * Retourne les phases d'un expresso classic
     * @return
     */
    @Override
    public Queue<BBPhase> getPhases() {
        phases.add(new LaunchingPhase(7200));
        return super.getPhases();
    }
}
