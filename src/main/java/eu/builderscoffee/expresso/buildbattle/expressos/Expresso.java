package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.buildbattle.BuildBattleInstanceType;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/***
 * Représente un expresso avec les élements suivant :
 * - Une icon
 * - Un nom
 * - Une description
 * - Une suite de phases
 */
public abstract class Expresso {

    @Getter
    public static Deque<BBPhase> phases = new LinkedBlockingDeque<>();
    @Getter
    @Setter
    public BukkitRunnable currentRunnable;
    @Getter
    @Setter
    public BBPhase currentPhase;

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
     * Retourne le thèmes de l'expresso
     *
     * @return
     */
    public abstract String getThemes();

    /**
     * Retournes les phases de l'expresso
     *
     * @return
     */
    public Deque<BBPhase> getPhases() {
        return phases;
    }

}
