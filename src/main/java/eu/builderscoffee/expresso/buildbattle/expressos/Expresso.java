package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
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
     * Retourne le nom de l'expresso
     * @return
     */
    public abstract String getName();

    /**
     * Retourne la description de l'expresso
     * @return
     */
    public abstract List<String> getDescription();

    /**
     * Retournes les phases d'un type d'expresso
     *
     * @return
     */
    public Queue<BBPhase> getPhases() {
        return phases;
    }

    /**
     * Peek la prochaine phases de la liste
     */
    public void nextPhases() {
        phases.remove(currentPhase); // Retirer la phase finie
        setCurrentPhase(phases.peek());
    }
}
