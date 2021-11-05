package eu.builderscoffee.expresso.buildbattle.games.expressos.types;


import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoGameType;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.EndPhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.GamePhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.LaunchingPhase;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static eu.builderscoffee.expresso.utils.TimeUtils.HOUR;

public class BoucheTrouExpressoGameType extends ExpressoGameType {

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.INK_SACK, 1, (short) 1)
                .setName(getName())
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

    /***
     * Retourne les phases d'un expresso bouche trou
     * @return
     */
    @Override
    public Deque<BBPhase> getPhases() {
        val phases = new LinkedList();
        phases.add(new LaunchingPhase(30));
        phases.add(new GamePhase(2 * HOUR));
        phases.add(new EndPhase());
        return phases;
    }
}
