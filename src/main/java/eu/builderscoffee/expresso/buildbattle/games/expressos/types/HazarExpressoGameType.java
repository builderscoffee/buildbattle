package eu.builderscoffee.expresso.buildbattle.games.expressos.types;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoGameType;
import eu.builderscoffee.expresso.buildbattle.games.expressos.phases.HazarPhase;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.EndPhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.LaunchingPhase;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static eu.builderscoffee.expresso.utils.TimeUtils.HOUR;

public class HazarExpressoGameType extends ExpressoGameType {
    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.INK_SACK, 1, (short) 2)
                .setName(getName())
                .build();
    }

    @Override
    public String getName() {
        return "Hazar";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("§7Mélange les blocs du même type entre eux");
    }

    @Override
    public int getGamePlayTime() {
        return 2 * HOUR;
    }

    /***
     * Retourne les phases d'un expresso hazard
     * @return
     */
    @Override
    public Deque<BBPhase> getPhases() {
        val phases = new LinkedList();
        phases.add(new LaunchingPhase(30));
        phases.add(new HazarPhase(getGamePlayTime()));
        phases.add(new EndPhase());
        return phases;
    }
}
