package eu.builderscoffee.expresso.buildbattle.games.expressos.phases;

import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.games.expressos.engine.HazarEngine;
import eu.builderscoffee.expresso.buildbattle.phase.bases.GamePhase;
import org.bukkit.scheduler.BukkitRunnable;

public class HazarPhase extends GamePhase {

    public static HazarEngine hazarEngine;

    public HazarPhase(int defaultTime) {
        super(defaultTime);
        // Enregistrer l'engine de la partie
        hazarEngine = new HazarEngine(ExpressoBukkit.getInstance());
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
    public BuildBattleEngine engine() {
        return hazarEngine;
    }
}
