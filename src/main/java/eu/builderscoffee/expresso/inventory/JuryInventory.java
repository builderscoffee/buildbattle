package eu.builderscoffee.expresso.inventory;

import com.plotsquared.core.IPlotMain;
import com.plotsquared.core.api.PlotAPI;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotManager;
import eu.builderscoffee.api.gui.ClickableItem;
import eu.builderscoffee.api.gui.SmartInventory;
import eu.builderscoffee.api.gui.content.*;
import eu.builderscoffee.api.utils.ItemBuilder;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JuryInventory implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("network")
            .provider(new JuryInventory())
            .size(3, 9)
            .title(ChatColor.WHITE + "Menu Jury")
            .manager(Main.getInventoryManager())
            .build();
    private final Main main = Main.getInstance();
    private final MessageConfiguration messages = Main.getMessages();
    ClickableItem blackGlasses = ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        Set<Plot> plots = new PlotAPI().getAllPlots();
        int plotSize = plots.size();
        ClickableItem[] plotsItem = new ClickableItem[plotSize];

        for(int i = 0; i < plotsItem.length; i++) {
            int tempPlot = i;
            plotsItem[i] = ClickableItem.of(new ItemBuilder(Material.GRASS).setName("Plot #" + i).build(),
                    e -> {
                       Plot currentPlot = (Plot) Array.get(plots,tempPlot);
                       player.teleport((Entity) currentPlot.getCenterSynchronous());
                    });
        }

        pagination.setItems(plotsItem);
        pagination.setItemsPerPage(7);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));

        contents.set(2, 3, ClickableItem.of(new ItemStack(Material.ARROW),
                e -> INVENTORY.open(player, pagination.previous().getPage())));
        contents.set(2, 4, ClickableItem.of(new ItemStack(Material.BARRIER),
                e -> INVENTORY.close(player)));
        contents.set(2, 5, ClickableItem.of(new ItemStack(Material.ARROW),
                e -> INVENTORY.open(player, pagination.next().getPage())));
        contents.set(2, 8, ClickableItem.of(new ItemStack(Material.PAPER),
                e -> INVENTORY.open(player, pagination.next().getPage())));

        //Fill Black borders
        contents.fillColumn(0,blackGlasses);
        contents.fillColumn(8,blackGlasses);
        contents.fillRow(2,blackGlasses);

    }

    @Override
    public void update(Player player, InventoryContents contents) {
        // Nothing to do here
    }
}
