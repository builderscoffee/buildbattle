package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.IGameEngine;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.types.HazarEngine;
import eu.builderscoffee.expresso.buildbattle.expressos.listeners.HazarListener;
import eu.builderscoffee.expresso.utils.Log;
import lombok.val;
import org.bukkit.scheduler.BukkitRunnable;

public class HazarPhase extends GamePhase {

    public static HazarEngine hazarEngine;

    public HazarPhase(int maxTime) {
        super(maxTime);
        // Enregistrer l'engine de la partie
        hazarEngine = new HazarEngine(Main.getInstance());
    }

    @Override
    public String name() {
        return "Game hazard";
    }

    @Override
    public BukkitRunnable runnable() {
        // Shuffle une liste de block
        /*
        val runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Log.get().info("Test super phase runnable");
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
        */
        return super.runnable();
    }

    @Override
    public IGameEngine engine() {
        return hazarEngine;
    }
}
