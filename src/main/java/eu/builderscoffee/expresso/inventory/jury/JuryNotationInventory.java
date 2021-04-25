package eu.builderscoffee.expresso.inventory.jury;

import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import eu.builderscoffee.api.bukkit.gui.ClickableItem;
import eu.builderscoffee.api.bukkit.gui.SmartInventory;
import eu.builderscoffee.api.bukkit.gui.content.InventoryContents;
import eu.builderscoffee.api.bukkit.gui.content.InventoryProvider;
import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.notation.Notation;
import eu.builderscoffee.expresso.utils.PlotUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JuryNotationInventory implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("Jury_Notation")
            .provider(new JuryNotationInventory())
            .size(4, 9)
            .title(ChatColor.WHITE + "Menu Notation")
            .manager(Main.getInventoryManager())
            .build();

    private int beaute, crea, ame, folkore, fun = 0;

    @Override
    public void init(Player player, InventoryContents contents) {

        Location loc = PlotUtils.convertBukkitLoc(player.getLocation());
        Plot plot = loc.getPlotAbs(); // On est sur qu'il y a un plot

        ClickableItem blackGlasses = ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15));
        contents.fillRow(0, blackGlasses);
        contents.fillRow(3, blackGlasses);


        contents.set(3, 4, ClickableItem.of(new ItemBuilder(Material.RAW_FISH).setName("§bValider mon verdict").build(),
                e -> {
                    Notation note = new Notation(player.getUniqueId(), beaute, crea, ame, folkore, fun);
                    Main.getBbGame().getNotationManager().addNotationInPlot(plot, note);
                    INVENTORY.close(player);
                }));
        contents.set(1, 2, ClickableItem.of(new ItemBuilder(Material.YELLOW_FLOWER).setName("§bBeauté/Technicité").build(),
                e -> INVENTORY.close(player)));
        contents.set(1, 3, ClickableItem.of(new ItemBuilder(Material.PAINTING).setName("§bCrétivité/Originalité").build(),
                e -> INVENTORY.close(player)));
        contents.set(1, 4, ClickableItem.of(new ItemBuilder(Material.SIGN).setName("§bAménagement/Finalité").build(),
                e -> INVENTORY.close(player)));
        contents.set(1, 5, ClickableItem.of(new ItemBuilder(Material.WRITTEN_BOOK).setName("§bFolklore " + plot.getId()).build(),
                e -> INVENTORY.close(player)));
        contents.set(1, 6, ClickableItem.of(new ItemBuilder(Material.RAW_FISH).setName("§bFun").build(),
                e -> INVENTORY.close(player)));

        // Les PAPER avec les points en fonctions des clicks

        contents.set(2, 2, ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§6" + beaute + " Points").build(),
                e -> {
                    switch (e.getClick()) {
                        case LEFT:
                            beaute = addCap30(beaute, 1);
                            break;
                        case RIGHT:
                            beaute = sub(beaute, 1);
                            break;
                        case SHIFT_LEFT:
                            beaute = addCap30(beaute, 5);
                            break;
                        case SHIFT_RIGHT:
                            beaute = sub(beaute, 5);
                            break;
                    }
                    INVENTORY.open(player);
                }));
        contents.set(2, 3, ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§6" + crea + " Points").build(),
                e -> {
                    switch (e.getClick()) {
                        case LEFT:
                            crea = addCap22(crea, 1);
                            break;
                        case RIGHT:
                            crea = sub(crea, 1);
                            break;
                        case SHIFT_LEFT:
                            crea = addCap22(crea, 5);
                            break;
                        case SHIFT_RIGHT:
                            crea = sub(crea, 5);
                            break;
                    }
                    INVENTORY.open(player);
                }));
        contents.set(2, 4, ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§6" + ame + " Points").build(),
                e -> {
                    switch (e.getClick()) {
                        case LEFT:
                            ame = addCap22(ame, 1);
                            break;
                        case RIGHT:
                            ame = sub(ame, 1);
                            break;
                        case SHIFT_LEFT:
                            ame = addCap22(ame, 5);
                            break;
                        case SHIFT_RIGHT:
                            ame = sub(ame, 5);
                            break;
                    }
                    player.sendMessage(String.valueOf(e.getClick()));
                    INVENTORY.open(player);
                }));
        contents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§6" + folkore + " Points").build(),
                e -> {
                    switch (e.getClick()) {
                        case LEFT:
                            folkore = addCap22(folkore, 1);
                            break;
                        case RIGHT:
                            folkore = sub(folkore, 1);
                            break;
                        case SHIFT_LEFT:
                            folkore = addCap22(folkore, 5);
                            break;
                        case SHIFT_RIGHT:
                            folkore = sub(folkore, 5);
                            break;
                    }
                    INVENTORY.open(player);
                }));


        contents.set(2, 6, ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§6" + fun + " Points").build(),
                e -> {
                    switch (e.getClick()) {
                        case LEFT:
                            fun = addCap4(fun, 1);
                            break;
                        case RIGHT:
                            fun = sub(fun, 1);
                            break;
                        case SHIFT_LEFT:
                            fun = addCap4(fun, 5);
                            break;
                        case SHIFT_RIGHT:
                            fun = sub(fun, 5);
                            break;
                    }
                    INVENTORY.open(player);
                }));


    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    // Fonction de check si les somme sont au maxium de leurs capasiters.

    public int addCap22(int a, int b) {
        if ((a + b) > 22) {
            return a;
        } else {
            return a + b;
        }
    }

    public int addCap30(int a, int b) {
        if ((a + b) > 30) {
            return a;
        } else {
            return a + b;
        }
    }

    public int addCap4(int a, int b) {
        if ((a + b) > 4) {
            return a;
        } else {
            return a + b;
        }
    }

    public int sub(int a, int b) {
        if ((a - b) < 0) {
            return a;
        } else {
            return a - b;
        }
    }
}
