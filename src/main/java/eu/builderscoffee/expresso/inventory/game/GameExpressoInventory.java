package eu.builderscoffee.expresso.inventory.game;

import eu.builderscoffee.api.gui.ClickableItem;
import eu.builderscoffee.api.gui.SmartInventory;
import eu.builderscoffee.api.gui.content.InventoryContents;
import eu.builderscoffee.api.gui.content.InventoryProvider;
import eu.builderscoffee.api.utils.ItemBuilder;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameExpressoInventory implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("game_expresso")
            .provider(new GameExpressoInventory())
            .size(3,9)
            .title(ChatColor.WHITE + "§fChoix de l'expresso")
            .manager(Main.getInventoryManager())
            .build();

    ClickableItem whiteGlasses = ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0));

    @Override
    public void init(Player player, InventoryContents contents) {
        // Fill expresso
        int expressoSize = Expresso.expressos.size();
        ClickableItem[] expressoItem = new ClickableItem[expressoSize];
        for (int i = 0; i < expressoItem.length; i++) {
            int expressoIndex = i;
            expressoItem[i] = ClickableItem.of(Expresso.expressos.get(i).getIcon(),
                    e -> {
                Expresso expresso = Expresso.getExpressos().get(expressoIndex);
                Main.getBbGame().setExpressoType(expresso);
                player.sendMessage("§aVous avez selectionné :§f" + expresso.getName());
                    });
        }
        // Fill row
        contents.fillRow(2, whiteGlasses);
        // Set validate item
        contents.set(2,4,ClickableItem.empty(new ItemBuilder(Material.CARPET, 1, (short) 3).setName("§aValider").build()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
