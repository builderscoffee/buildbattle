package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.malus.Malus;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;

import java.util.List;

public abstract class Expresso {

    private Main main;
    public List<BBPhase> phases;
    public List<Malus> malus;

    public Expresso(Main main) {
        this.main = main;
        //DO SOME STUFF
    }

    public final void startExpresso() {
        // Start WaitingPhase
    }

    /**
     * Retournes les phases d'un type d'expresso
     * @return
     */
    public List<BBPhase> getPhases() {
        return phases;
    }

    /**
     * Retour les malus d'un type d'expresso
     * @return
     */
    public List<Malus> getMalus() {
        return malus;
    }
}
