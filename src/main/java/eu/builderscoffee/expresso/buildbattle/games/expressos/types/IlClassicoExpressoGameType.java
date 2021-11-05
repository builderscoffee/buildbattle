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

public class IlClassicoExpressoGameType extends ExpressoGameType {

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.INK_SACK, 1, (short) 0)
                .setName(getName())
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

    /***
     * Retourne les phases d'un expresso classic
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
