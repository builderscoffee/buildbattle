package eu.builderscoffee.expresso.buildbattle.expressos.engine;

import org.bukkit.event.Listener;

import java.util.List;

public interface IGameEngine {

    /***
     * Démarrer l'engine de la game
     */
    public void load();

    /***
     * Enregistrer les evenements custom du moteur
     * @return
     */
    public List<Listener> registerListener();
}
