package eu.builderscoffee.expresso.buildbattle.expressos.types;

import eu.builderscoffee.api.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.GamePhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.JuryPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.LaunchingPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.HazarPhase;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class IlClassicoExpresso extends Expresso {

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.INK_SACK, 1, (short) 0)
                .setName(getName())
                //.addLoreLine(getDescription())
                .build();
    }

    @Override
    public String getName() {
        return "IlClassico";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("L'expresso des plus classique");
    }

    @Override
    public String getThemes() {
        return null;
    }

    /***
     * Retourne les phases d'un expresso classic
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
