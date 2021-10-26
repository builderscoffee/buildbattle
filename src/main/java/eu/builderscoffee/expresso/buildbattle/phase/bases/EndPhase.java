package eu.builderscoffee.expresso.buildbattle.phase.bases;

import eu.builderscoffee.expresso.Main;
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
    public BuildBattleManager.BBState state() {
        return BuildBattleManager.BBState.ENDING;
    }

    @Override
    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                // Finir la partie
                Main.getBbGame().getBbGameManager().endGame();
                // Cancel cette phase
                Main.getBbGame().getBbGameManager().cancelPhase();
                //TODO Autre chose à faire avant de fermer le serveur ?
            }
        };
    }

    @Override
    public BuildBattleEngine engine() {
        return null;
    }
}
