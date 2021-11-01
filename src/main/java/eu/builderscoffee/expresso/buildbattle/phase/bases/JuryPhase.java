package eu.builderscoffee.expresso.buildbattle.phase.bases;


import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
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
    public BuildBattleManager.GameState state() {
        return BuildBattleManager.GameState.ENDING;
    }

    @Override
    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                // Kick tous les joueurs
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getOnlinePlayers().forEach(s -> s.kickPlayer("Les plots sont en cours de notation"));
                    }
                }.runTask(ExpressoBukkit.getInstance());


                // Permettre la notation des plots
                // TODO some stuff here

            }
        };
    }

    @Override
    public BuildBattleEngine engine() {
        return null;
    }
}
