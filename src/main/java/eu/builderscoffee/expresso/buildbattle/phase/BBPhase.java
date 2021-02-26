package eu.builderscoffee.expresso.buildbattle.phase;

/***
 * Représente une phase/étapes d'une partie
 */
public interface BBPhase {

    /**
     * Représente le nom d'une phase
     * @return
     */
    String name();

    /**
     * Bref description d'une phase
     * @return
     */
    String description();

    /**
     * Temps maximun de la phase
     * @return
     */
    int time();


}
