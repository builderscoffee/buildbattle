package eu.builderscoffee.expresso.buildbattle.phase;

import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

/***
 * Représente une phase/étapes d'une partie
 */
public interface BBPhase {

    /**
     * Représente le nom d'une phase
     *
     * @return
     */
    String name();

    /**
     * Bref description d'une phase
     *
     * @return
     */
    String description();

    /**
     * Temps
     *
     * @return
     */
    int time();

    /**
     * Metre le temps
     *
     * @return
     */
    void setTime(int time);

    /**
     * Temps par default
     *
     * @return
     */
    int defaultTime();

    /**
     * Temps totale
     *
     * @return
     */
    TimeUnit timeUnit();

    /**
     * Etat de la phase
     *
     * @return
     */
    BuildBattleManager.GameState state();

    /**
     * Bukkit runnable de la phase
     *
     * @return
     */
    BukkitRunnable runnable();

    /**
     * Charger le moteur de la partie si il a lieu d'être
     */
    BuildBattleEngine engine();
}
