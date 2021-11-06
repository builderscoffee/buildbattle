package eu.builderscoffee.expresso.buildbattle.toolbars.tools;


import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.toolbars.ToolbarItem;
import eu.builderscoffee.expresso.inventory.jury.JuryInventory;
import eu.builderscoffee.expresso.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public class PlotItem extends ToolbarItem {

    public PlotItem(int slot) {
        super(new ItemBuilder(Material.GRASS), slot);
        builder.setName(MessageUtils.getMessageConfig(player).getToolbar().getPlotItems());

    }

    @Override
    public void interact(Player player, Action action) {
        JuryInventory.INVENTORY.open(player);
    }
}
