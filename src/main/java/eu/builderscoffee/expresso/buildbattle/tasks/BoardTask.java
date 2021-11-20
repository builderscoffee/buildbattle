package eu.builderscoffee.expresso.buildbattle.tasks;

import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.board.BaseBoard;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BoardTask extends BukkitRunnable {

    /***
     * Tache permettant de mettre à jour le scoreboards des joueurs tout les ticks
     */

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> ExpressoBukkit.getBbGame().getBbGameTypes().getBaseBoard().update(player));
    }
}
