package eu.builderscoffee.expresso.buildbattle.toolbars.tools;


import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.toolbars.ToolbarItem;
import eu.builderscoffee.expresso.inventory.jury.JuryInventory;
import eu.builderscoffee.expresso.inventory.jury.JuryNotationInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class NotationItem extends ToolbarItem {

    public NotationItem(int slot) {
        super(new ItemBuilder(Material.SIGN).setName("§aNotations"),slot);
    }

    @Override
    public void interact(Player player, Action action) {
        JuryNotationInventory.INVENTORY.open(player);
    }
}
