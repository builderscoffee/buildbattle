package eu.builderscoffee.expresso.buildbattle.expressos.types;

import eu.builderscoffee.api.utils.ItemBuilder;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.GamePhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.LaunchingPhase;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class IlClassicoExpresso extends Expresso {

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.INK_SACK,1,(short) 0)
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

    /***
     * Retourne les phases d'un expresso classic
     * @return
     */
    @Override
    public Deque<BBPhase> getPhases() {
        phases.add(new LaunchingPhase(30));
        phases.add(new GamePhase(7200));
        return getPhases();
    }
}
