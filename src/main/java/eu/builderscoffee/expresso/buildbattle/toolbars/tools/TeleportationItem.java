package eu.builderscoffee.expresso.buildbattle.toolbars.tools;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.toolbars.ToolbarItem;
import eu.builderscoffee.expresso.inventory.jury.JuryTeleportation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class TeleportationItem extends ToolbarItem {

    public TeleportationItem(int slot) {
        super(new ItemBuilder(Material.EYE_OF_ENDER).setName("§aTéléportation"),slot);
    }

    @Override
    public void interact(Player player, Action action) {
        JuryTeleportation.INVENTORY.open(player);
    }
}
