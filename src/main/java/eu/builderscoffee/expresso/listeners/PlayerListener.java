package eu.builderscoffee.expresso.listeners;

import eu.builderscoffee.api.board.FastBoard;
import eu.builderscoffee.api.utils.LocationsUtil;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.board.BBBoard;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        // Scoreboard Updater
        FastBoard board = new FastBoard(player);
        board.updateTitle("§6§l- Builders Coffee -"); // Même titre pour tout
        BBBoard.boards.put(player.getUniqueId(), board);

        // Player Inventory
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setAllowFlight(true);

        player.getInventory().clear();
        // If player as plot
        player.teleport(LocationsUtil.getLocationFromString(Main.getSettings().getSpawnLocation()));

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDisconnect(PlayerQuitEvent event) {
        // Scoreboard clean
        FastBoard board = BBBoard.boards.remove(event.getPlayer().getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("/plot auto")) {
            Main.getBbGame().addCompetitor(event.getPlayer());
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!Main.getBbGame().getBbState().equals(BBGameManager.BBState.IN_GAME)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!Main.getBbGame().getBbState().equals(BBGameManager.BBState.IN_GAME)) {
            event.setCancelled(true);
        }
    }
}
