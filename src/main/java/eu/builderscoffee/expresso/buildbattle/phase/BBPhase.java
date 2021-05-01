package eu.builderscoffee.expresso.buildbattle.phase;

import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.IGameEngine;
import org.bukkit.scheduler.BukkitRunnable;

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
     * Temps maximun de la phase
     *
     * @return
     */
    int time();

    /**
     * Etat de la phase
     *
     * @return
     */
    BBGameManager.BBState state();

    /**
     * Bukkit runnable de la phase
     *
     * @return
     */
    BukkitRunnable runnable();

    /**
     * Charger le moteur de la partie si il a lieu d'être
     */
    IGameEngine engine();

}
