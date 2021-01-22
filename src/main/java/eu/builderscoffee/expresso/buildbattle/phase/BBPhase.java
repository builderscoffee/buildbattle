package eu.builderscoffee.expresso.buildbattle.phase;

import eu.builderscoffee.expresso.buildbattle.BBGame;
import lombok.Getter;
import lombok.Setter;

public abstract class BBPhase {

    protected final BBGame bbGame;

    /**
     * The state of a phase
     */
    @Getter @Setter
    private final boolean isActive;

    public BBPhase(BBGame bbGame, boolean isActive) {
        this.bbGame = bbGame;
        this.isActive = isActive;
    }

    /**
     * Method call when phase start
     */
    public void onStart(){}

    /**
     * Method call when phase update
     */
    public void onUpdate(){}

    /**
     * Methode call when phase end
     */
    public void onEnd(){}
}
