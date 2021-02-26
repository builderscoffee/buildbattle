package eu.builderscoffee.expresso.buildbattle.expressos.types;

import eu.builderscoffee.api.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class HazarExpresso extends Expresso {
    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.INK_SACK, 1, (short) 2)
                .setName(getName())
                //.addLoreLine(getDescription())
                .build();
    }

    @Override
    public String getName() {
        return "Hazar";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("Mélange les blocs du même type entre eux");
    }
}
