package eu.builderscoffee.expresso.inventory.jury.notation;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import eu.builderscoffee.api.gui.ClickableItem;
import eu.builderscoffee.api.gui.SmartInventory;
import eu.builderscoffee.api.gui.content.*;
import eu.builderscoffee.api.utils.ItemBuilder;
import eu.builderscoffee.expresso.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JuryNotationInventory implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("network")
            .provider(new JuryNotationInventory())
            .size(4, 9)
            .title(ChatColor.WHITE + "§fMenu Notation")
            .manager(Main.getInventoryManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

        // Plot à noter
        //Main.getBbGame().getNotationManager()
        Set<Plot> plots = new PlotAPI().getAllPlots();
        List<Plot> list = new ArrayList<>(plots);
        Plot plot = list.get(0);


        ClickableItem blackGlasses = ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
        contents.fillRow(0, blackGlasses);
        contents.fillRow(3, blackGlasses);

        contents.set(2, 4, ClickableItem.of(new ItemBuilder(Material.BARRIER).setName("§cFermer").build(),
                e -> INVENTORY.close(player)));


    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
