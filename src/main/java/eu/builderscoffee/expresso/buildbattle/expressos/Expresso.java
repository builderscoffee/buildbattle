package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;

public abstract class Expresso {

    public static Queue<BBPhase> phases;
    @Getter
    @Setter
    public static BBPhase currentPhase;
    private final Main main;

    public Expresso(Main main) {
        this.main = main;

    }

    /**
     * Retournes les phases d'un type d'expresso
     *
     * @return
     */
    public Queue<BBPhase> getPhases() {
        return phases;
    }

    /**
     * Avance le phases d'un cran
     */
    public void nextPhases() {
        phases.remove(currentPhase); // Retirer la phase finie
        setCurrentPhase(phases.peek());
    }
}
