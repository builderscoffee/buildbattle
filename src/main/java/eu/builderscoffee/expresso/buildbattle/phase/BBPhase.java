package eu.builderscoffee.expresso.buildbattle.phase;

import eu.builderscoffee.expresso.buildbattle.BBGame;

public abstract class BBPhase {

    protected final BBGame bbGame;


    public BBPhase(BBGame bbGame) {
        this.bbGame = bbGame;
    }

    /**
     * Method call when phase start
     */
    public void onStart() {
    }

    /**
     * Method call when phase update
     */
    public void onUpdate() {
    }

    /**
     * Methode call when phase end
     */
    public void onEnd() {
    }
}
