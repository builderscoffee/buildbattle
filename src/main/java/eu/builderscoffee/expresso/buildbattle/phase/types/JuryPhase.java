package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class JuryPhase implements BBPhase {

    @Override
    public String name() {
        return "Jury";
    }

    @Override
    public String description() {
        return "Notation des plots";
    }

    @Override
    public int time() {
        return -1;
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
                // Kick tous les joueurs
                Bukkit.getOnlinePlayers().forEach(s -> s.kickPlayer("Les plots sont en cours de notation"));

                // Permettre la notation des plots
                // TODO some stuff here

            }
        };
    }
}