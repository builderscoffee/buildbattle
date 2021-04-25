package eu.builderscoffee.expresso.buildbattle.expressos.types;


import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.GamePhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.LaunchingPhase;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BoucheTrouExpresso extends Expresso {

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.INK_SACK, 1, (short) 1)
                .setName(getName())
                //.addLoreLine("test")
                .build();
    }

    @Override
    public String getName() {
        return "Bouche Trou";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("Une schématique est coller sur le plot");
    }

    @Override
    public String getThemes() {
        return null;
    }

    /***
     * Retourne les phases d'un expresso bouche trou
     * @return
     */
    @Override
    public Deque<BBPhase> getPhases() {
        val phases = new LinkedList();
        phases.add(new LaunchingPhase(30));
        phases.add(new GamePhase(7200));
        return phases;
    }
}
