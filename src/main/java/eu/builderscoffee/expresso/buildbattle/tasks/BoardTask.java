package eu.builderscoffee.expresso.buildbattle.tasks;

import eu.builderscoffee.expresso.board.BBBoard;
import org.bukkit.scheduler.BukkitRunnable;

public class BoardTask extends BukkitRunnable {

    /***
     * Tache permettant de mettre à jour le scoreboards des joueurs tout les ticks
     */

    @Override
    public void run() {
        BBBoard.boards.values().forEach(BBBoard::updateBoard);
    }
}
