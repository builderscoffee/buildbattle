package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import lombok.Getter;

import java.util.List;

public abstract class Expresso {

    public static List<BBPhase> phases;
    public static BBPhase currentPhase;
    @Getter
    protected int count;
    private final Main main;

    public Expresso(Main main) {
        this.main = main;

    }

    public abstract void onExpressoStart();

    /**
     * Retournes les phases d'un type d'expresso
     *
     * @return
     */
    public List<BBPhase> getPhases() {
        return phases;
    }

    /**
     * Avance le phases d'un cran
     */
    public void nextPhases() {
        if (currentPhase != null) {
            int current = phases.indexOf(currentPhase);
            if (current < phases.size()) {
                currentPhase = phases.get(current + 1);
            }
        } else {
            currentPhase = phases.get(0);
        }
    }

    public void resetCount() {
        count = 0;
    }


}
