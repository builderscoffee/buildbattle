package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.expresso.utils.Log;
import org.bukkit.scheduler.BukkitRunnable;

public class testPhase extends GamePhase {

    public testPhase(int maxTime) {
        super(maxTime);
    }

    @Override
    public BukkitRunnable runnable() {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                testPhase.super.runnable();
                Log.get().info("TEST SUPER GAME PHASE");
            }
        };
        return runnable;
    }
}
