package eu.builderscoffee.expresso.buildbattle.toolbars;

import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.toolbars.tools.JoinItem;
import eu.builderscoffee.expresso.buildbattle.toolbars.tools.NotationItem;
import eu.builderscoffee.expresso.buildbattle.toolbars.tools.PlotItem;
import eu.builderscoffee.expresso.buildbattle.toolbars.tools.TeleportationItem;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class ToolbarManager implements Listener {

    public Map<Toolbars, List<ToolbarItem>> toolbarItems = new HashMap<>();
    public static Map<Player, Toolbars> playerToolbars = new HashMap<>();

    public ToolbarManager() {
        // Enregistrer les évenements de la class
        Bukkit.getPluginManager().registerEvents(this, ExpressoBukkit.getInstance());

        // Ajouter toutes les bar et leurs items à la liste
        Arrays.stream(Toolbars.values()).forEach(toolbars -> toolbarItems.put(toolbars, toolbars.getToolbarItems()));
    }

    /***
     * Ajouter une ToolBar à un joueur
     * @param player
     * @param toolbars
     */
    public void addToolBar(Player player, Toolbars toolbars) {
        // Ajouter le joueur dans la liste active
        playerToolbars.put(player, toolbars);
        // Nettoyer la HotBar du joueur
        cleanHotBar(player);
        // Set les items suivants la bar choisie en fonction de leur slot
        toolbars.getToolbarItems().forEach(toolbarItem -> {
            toolbarItem.setPlayer(player);
            player.getInventory().setItem(toolbarItem.getSlot(), toolbarItem.builder.build());
        });
    }

    /***
     * Retirer la ToolBar du joueur
     * @param player
     */
    public void removeToolBar(Player player) {
        if (playerToolbars.containsKey(player)) {
            // Retirer le joueur de la liste active
            playerToolbars.remove(player);
            // Nettoyer la HotBar du joueur
            cleanHotBar(player);
        }
    }

    /***
     * Nettoyer la hotbar
     * @param player
     */
    public void cleanHotBar(Player player) {
        for (int slot = 0; slot < 8; slot++) {
            player.getInventory().clear(slot);
        }
    }

    /***
     * Intéraction avec les items de la toolbar
     * @param event
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        // Checker si le joueur est dans la liste
        if (!playerToolbars.containsKey(event.getPlayer())) return;
        // Check si le joueur à un item en main
        if (Objects.nonNull(event.getPlayer().getInventory().getItemInHand())) {
            if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
                event.setCancelled(true);
                // Check si l'item correspond à la liste de sa toolbar
                val item = playerToolbars.get(event.getPlayer()).getToolbarItems().stream().filter(toolbarItem ->
                        toolbarItem.getSlot() == event.getPlayer().getInventory().getHeldItemSlot()).findFirst().orElse(null);
                if (Objects.nonNull(item)) item.interact(event.getPlayer(), event.getAction());
            }
        }
    }

    public enum Toolbars {

        JURORS("Jury", Arrays.asList(new PlotItem(0), new TeleportationItem(1), new NotationItem(2))),
        SPECTATOR("Spectateur", Arrays.asList(new PlotItem(0), new JoinItem(1)));

        @Getter
        private final String barName;
        @Getter
        private final List<ToolbarItem> toolbarItems;

        Toolbars(String barName, List<ToolbarItem> toolbarItems) {
            this.barName = barName;
            this.toolbarItems = toolbarItems;
        }
    }
}
