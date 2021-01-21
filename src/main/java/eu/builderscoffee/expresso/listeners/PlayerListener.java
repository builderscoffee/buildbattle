package eu.builderscoffee.expresso.listeners;

import eu.builderscoffee.api.board.FastBoard;
import eu.builderscoffee.expresso.board.BBBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {
        // Scoreboard Updater
        FastBoard board = new FastBoard(event.getPlayer());
        board.updateTitle("§6§l- Builders Coffee -"); // Même titre pour tout
        BBBoard.boards.put(event.getPlayer().getUniqueId(), board);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDisconnect(PlayerQuitEvent event) {
        // Scoreboard clean
        FastBoard board = BBBoard.boards.remove(event.getPlayer().getUniqueId());

        if (board != null) {
            board.delete();
        }
    }
}
