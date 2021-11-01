package eu.builderscoffee.expresso.buildbattle.tasks;

import eu.builderscoffee.expresso.board.BBBoard;
import org.bukkit.scheduler.BukkitRunnable;

public class BoardTask extends BukkitRunnable {

    @Override
    public void run() {
        BBBoard.boards.values().forEach(BBBoard::updateBoard);
    }
}
