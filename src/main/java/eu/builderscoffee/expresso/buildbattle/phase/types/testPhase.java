package eu.builderscoffee.expresso.buildbattle.phase.types;

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
                //TODO DO SOME STUFF HERE
            }
        };
        return runnable;
    }
}
