package eu.builderscoffee.expresso.inventory;

import eu.builderscoffee.api.gui.ClickableItem;
import eu.builderscoffee.api.gui.SmartInventory;
import eu.builderscoffee.api.gui.content.*;
import eu.builderscoffee.api.utils.ItemBuilder;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.types.HazarPhase;
import eu.builderscoffee.expresso.inventory.game.GameExpressoInventory;
import eu.builderscoffee.expresso.utils.BlockData;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HazardExpressoInventory implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("hazard_expresso")
            .provider(new GameExpressoInventory())
            .size(3,9)
            .title(ChatColor.WHITE + "§fListe des blocks")
            .manager(Main.getInventoryManager())
            .build();

    ClickableItem whiteGlasses = ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0));

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        val blockDataList = new ArrayList<>(HazarPhase.hazarEngine.convertBlockdata.values());
        ClickableItem[] expressoItem = new ClickableItem[blockDataList.size()];

        // Fill expresso
        for (int i = 0; i < expressoItem.length; i++) {
            int expressoIndex = i;
            expressoItem[i] = ClickableItem.of(new ItemBuilder(Material.getMaterial(blockDataList.get(i).getId())).addLoreLine("Convert to " + BlockData.getBlockDataById(i).id).build(),
                    e -> {
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
