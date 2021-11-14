package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

@Data
public abstract class BuildBattleGameType {
    public static Deque<BBPhase> phases = new LinkedBlockingDeque<>();
    public BukkitRunnable currentRunnable;
    public BBPhase currentPhase;
    private int gamePlayTime;

    /**
     * Retourne le nom du buildbattle
     *
     * @return
     */
    public abstract String getName();


    /**
     * Retournes les phases du buildbattle
     *
     * @return
     */
    public Deque<BBPhase> getPhases() {
        return phases;
    }

}
