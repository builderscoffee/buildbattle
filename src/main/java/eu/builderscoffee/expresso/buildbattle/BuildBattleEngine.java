package eu.builderscoffee.expresso.buildbattle;

import org.bukkit.event.Listener;

import java.util.List;

public interface BuildBattleEngine {

    /***
     * Démarrer le moteur de la game
     */
    public void load();

    /***
     * Enregistrer les évenements custom du moteur de la partie
     * @return
     */
    public List<Listener> registerListener();

}
