package eu.builderscoffee.expresso.buildbattle.phase.bases;

import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import org.bukkit.scheduler.BukkitRunnable;

public class EndPhase implements BBPhase {

    public EndPhase() {
        // Mettre fin à la game
    }

    @Override
    public String name() {
        return "Fin de la partie";
    }

    @Override
    public String description() {
        return "Mettre fin à la partie";
    }

    @Override
    public int time() {
        return 0;
    }

    @Override
    public void setTime(int time) {
        // Nothing to do
    }

    @Override
    public int defaultTime() {
        return 0;
    }

    @Override
    public BuildBattleManager.GameState state() {
        return BuildBattleManager.GameState.ENDING;
    }

    @Override
    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                // Finir la partie
                ExpressoBukkit.getBbGame().getBbGameManager().endGame();
                //TODO Autre chose à faire avant de fermer le serveur ?
            }
        };
    }

    @Override
    public BuildBattleEngine engine() {
        return null;
    }
}
