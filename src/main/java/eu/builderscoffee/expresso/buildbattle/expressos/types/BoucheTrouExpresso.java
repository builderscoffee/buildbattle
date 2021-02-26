package eu.builderscoffee.expresso.buildbattle.expressos.types;

import eu.builderscoffee.api.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.JuryPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.LaunchingPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.HazarPhase;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BoucheTrouExpresso extends Expresso {

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.INK_SACK,1,(short) 1)
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

    /***
     * Retourne les phases d'un expresso bouche trou
     * @return
     */
    @Override
    public Deque<BBPhase> getPhases() {
        val phases = new LinkedList();
        phases.add(new LaunchingPhase(30));
        phases.add(new HazarPhase(7200));
        phases.add(new JuryPhase());
        return phases;
    }
}
