package eu.builderscoffee.expresso.inventory.game;


import eu.builderscoffee.api.bukkit.gui.ClickableItem;
import eu.builderscoffee.api.bukkit.gui.SmartInventory;
import eu.builderscoffee.api.bukkit.gui.content.*;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoGameType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GameExpressoInventory implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("game_expresso")
            .provider(new GameExpressoInventory())
            .size(3, 9)
            .title(ChatColor.WHITE + "§fChoix de l'expresso")
            .manager(Main.getInventoryManager())
            .build();

    ClickableItem whiteGlasses = ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0));

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<ExpressoGameType> expressoGameTypeList = Main.getBbGame().getExpressoManager().getExpressoGameTypes();
        ClickableItem[] expressoItem = new ClickableItem[expressoGameTypeList.size()];

        // Fill expresso
        for (int i = 0; i < expressoItem.length; i++) {
            int expressoIndex = i;
            expressoItem[i] = ClickableItem.of(expressoGameTypeList.get(i).getIcon(),
                    e -> {
                        // do action on click
                        ExpressoGameType expressoGameType = expressoGameTypeList.get(expressoIndex);
                        Main.getBbGame().configureExpresso(expressoGameType);
                        player.sendMessage("§aVous avez selectionné :§f " + expressoGameType.getName());
                    });
        }

        // Fill row
        contents.fillRow(2, whiteGlasses);

        // Set pages
        pagination.setItems(expressoItem);
        pagination.setItemsPerPage(18);

        // Iterate pages
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, SlotPos.of(0, 0)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
