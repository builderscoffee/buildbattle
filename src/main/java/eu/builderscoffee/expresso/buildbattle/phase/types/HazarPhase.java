package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.IGameEngine;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.types.HazarEngine;
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
        return super.runnable();
    }

    @Override
    public IGameEngine engine() {
        return hazarEngine;
    }
}
