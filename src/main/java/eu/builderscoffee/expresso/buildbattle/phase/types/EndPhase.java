package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.IGameEngine;
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
    public BBGameManager.BBState state() {
        return BBGameManager.BBState.ENDING;
    }

    @Override
    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                Main.getBbGame().getBbGameManager().endGame();
            }
        };
    }

    @Override
    public IGameEngine engine() {
        return null;
    }
}
