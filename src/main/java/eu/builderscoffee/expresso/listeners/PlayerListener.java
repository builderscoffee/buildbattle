package eu.builderscoffee.expresso.listeners;

import eu.builderscoffee.api.bukkit.board.FastBoard;
import eu.builderscoffee.api.bukkit.utils.LocationsUtil;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.board.BBBoard;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    SettingsConfiguration settings = Main.getSettings();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        // Scoreboard Updater
        FastBoard board = new FastBoard(player);
        board.updateTitle(settings.getBoard_title()); // Même titre pour tout
        BBBoard.boards.put(player.getUniqueId(), board);

        // Player Inventory
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setAllowFlight(true);
        player.getInventory().clear();

        // If player as plot
        player.teleport(LocationsUtil.getLocationFromString(Main.getSettings().getGlobal_spawn_location()));

        if (Main.getBbGame().getBbState().equals(BBGameManager.BBState.IN_GAME)) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(Main.getMessages().getGlobal_prefix() + "§a/plot auto pour participer");
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDisconnect(PlayerQuitEvent event) {
        // Scoreboard clean
        FastBoard board = BBBoard.boards.remove(event.getPlayer().getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("plot auto") || event.getMessage().equalsIgnoreCase("plot claim")) {
            if (Main.getBbGame().getBbState().equals(BBGameManager.BBState.IN_GAME)) {
                if(Main.getBbGame().getTeamManager().IsTeamLeader(event.getPlayer())) {
                    Main.getBbGame().getTeamManager().addAllMembersToPlot(Main.getBbGame().getTeamManager().getPlayerTeam(event.getPlayer()));
                }
                Main.getBbGame().addCompetitor(event.getPlayer());
            } else {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§cVous devez attendre le lancement de la partie avant de créer votre plot");
            }
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

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
