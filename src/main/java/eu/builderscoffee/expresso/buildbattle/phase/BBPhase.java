package eu.builderscoffee.expresso.buildbattle.phase;

import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import org.bukkit.inventory.ItemStack;
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
     * Description d'une phase
     *
     * @return
     */
    String description();

    /**
     * Icon de la phase
     *
     * @return
     */
    ItemStack icon();

    /**
     * Temps à display de la phase
     *
     * @return
     */
    int time();

    /**
     * Temps en cours de la partie
     * @return
     */
    int currentTime();

    /**
     * Définir un nouveau temps
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
     * Unité du temps de la phase
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
