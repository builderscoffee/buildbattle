package eu.builderscoffee.expresso.buildbattle.phase.bases;


import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.toolbars.ToolbarManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

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
    public int currentTime() {
        return time();
    }

    @Override
    public void setTime(int time) {
        // Nothing to do here
    }

    @Override
    public int defaultTime() {
        return 0;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.SECONDS;
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
                        Bukkit.getOnlinePlayers().forEach(s -> {
                            if(Bukkit.getServer().getWhitelistedPlayers().contains(s)) {
                                return;
                            }
                            s.kickPlayer("Les plots sont en cours de notation");
                        });
                    }
                }.runTask(ExpressoBukkit.getInstance());

                // Whitelist le serveur pour permettre au jury de noter les plots
                Bukkit.getServer().setWhitelist(true);

                // Ajouter la toolbar au jury
                ExpressoBukkit.getBbGame().getJurors().forEach(jury -> ExpressoBukkit.getBbGame().getToolbarManager().addToolBar(jury, ToolbarManager.Toolbars.JURORS));
            }
        };
    }

    @Override
    public BuildBattleEngine engine() {
        return null;
    }
}
