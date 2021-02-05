package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Queue;

/***
 * Représente un expresso avec les élements suivant :
 * - Une icon
 * - Un nom
 * - Une description
 * - Une suite de phases
 */
public abstract class Expresso {

    @Getter
    public static Queue<BBPhase> phases;
    @Getter @Setter
    public static BBPhase currentPhase;

    /**
     * Retourne l'icone de l'expresso
     *
     * @return
     */
    public abstract ItemStack getIcon();

    /**
     * Retourne le nom de l'expresso
     *
     * @return
     */
    public abstract String getName();

    /**
     * Retourne la description de l'expresso
     *
     * @return
     */
    public abstract List<String> getDescription();

    /**
     * Retournes les phases de l'expresso
     *
     * @return
     */
    public Queue<BBPhase> getPhases() {
        return phases;
    }


}
