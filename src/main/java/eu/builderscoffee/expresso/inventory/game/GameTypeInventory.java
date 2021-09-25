package eu.builderscoffee.expresso.inventory.game;

import eu.builderscoffee.api.bukkit.gui.ClickableItem;
import eu.builderscoffee.api.bukkit.gui.SmartInventory;
import eu.builderscoffee.api.bukkit.gui.content.*;
import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameTypeInventory implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("game_type")
            .provider(new GameExpressoInventory())
            .size(3, 9)
            .title(ChatColor.WHITE + "§fChoix de la partie")
            .manager(Main.getInventoryManager())
            .build();

    ClickableItem whiteGlasses = ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0));

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        contents.set(2, 2, ClickableItem.of(new ItemBuilder(Material.INK_SACK).setName("§6Expresso").build(),
                e -> {
                    GameExpressoInventory.INVENTORY.open(player);
                }));

        contents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.INK_SACK).setName("§6BuildBattle Classic").build(),
                e -> {
                    //INVENTORY.open(player, pagination.previous().getPage());
                }));

        contents.set(2, 8, ClickableItem.of(new ItemBuilder(Material.INK_SACK).setName("§6Tournois").build(),
                e -> {
                    //INVENTORY.open(player, pagination.previous().getPage());
                }));

        // Fill row
        contents.fillRow(0, whiteGlasses);
        contents.fillRow(2, whiteGlasses);

        // Set pages
        pagination.setItemsPerPage(18);

        // Iterate pages
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, SlotPos.of(0, 0)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}

