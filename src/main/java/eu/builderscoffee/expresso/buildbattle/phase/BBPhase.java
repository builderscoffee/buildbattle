package eu.builderscoffee.expresso.buildbattle.phase;

import eu.builderscoffee.expresso.buildbattle.BBGame;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class BBPhase extends BukkitRunnable {

    protected final BBGame bbGame;


    public BBPhase(BBGame bbGame) {
        this.bbGame = bbGame;
    }


}
