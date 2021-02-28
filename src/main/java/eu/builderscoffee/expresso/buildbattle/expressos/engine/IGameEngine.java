package eu.builderscoffee.expresso.buildbattle.expressos.engine;

public interface IGameEngine {

    /***
     * Précharger l'engine de la game
     */
    public void preLoad();

    /***
     * Démarrer l'engine de la game
     */
    public void run();

    /***
     * Clean l'engine de la game
     */
    public void clear();
}
