package eu.builderscoffee.expresso.buildbattle.expressos.engine;

public interface IGameEngine {

    /***
     * Démarrer l'engine de la game
     */
    public void load();

    /***
     * Clean l'engine de la game
     */
    public void clear();

    /***
     * Print dans un fichier
     */
    public void print();
}
