package eu.builderscoffee.expresso.buildbattle.toolbars;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public abstract class ToolbarItem {

    protected ItemBuilder builder;
    @Getter
    protected int slot;

    public ToolbarItem(ItemBuilder builder, int slot) {
        this.builder = builder;
        this.slot = slot;
    }

    protected void set(Player player) {
        player.getInventory().setItem(slot, builder.build());
    }

    public abstract void interact(Player player, Action action);

}
