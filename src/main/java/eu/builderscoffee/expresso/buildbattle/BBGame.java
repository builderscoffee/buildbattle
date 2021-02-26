package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorLeaveEvent;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.expressos.ExpressoManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BBGame {

    @Getter @Setter
    private Main main;
    /***
     * Le type d'expresso en cours !
     */
    @Getter @Setter
    private Expresso expressoType;
    @Getter
    private final List<Player> competitor = new ArrayList<>(); // La liste des compétitors
    private final List<Player> jury = new ArrayList<>();
    @Getter
    @Setter
    public BBGameManager.BBState bbState = BBGameManager.BBState.WAITING;
    @Getter @Setter
    private BBGameManager bbGameManager;
    @Getter @Setter
    private ExpressoManager expressoManager;
    @Getter
    @Setter
    private boolean isReady = false;


    /***
     * Créer une instance d'une BBGame
     * @param type
     */
    public BBGame(Main main, Expresso type) {
        setMain(main);
        setExpressoType(type);
        setBbGameManager(new BBGameManager(this));
        setExpressoManager(new ExpressoManager(this));
    }

    public final void startExpresso() {
        bbGameManager.startGame();
    }

    /**
     * Ajouter un joueur à la liste des participants
     *
     * @param player
     */
    public void addCompetitor(Player player) {
        competitor.add(player);
        main.getInstance().getServer().getPluginManager().callEvent(new CompetitorJoinEvent(player));
    }

    /**
     * Retirer un joueur de la liste des participants
     *
     * @param player
     */
    public void removeCompetitor(Player player) {
        competitor.remove(player);
        main.getInstance().getServer().getPluginManager().callEvent(new CompetitorLeaveEvent(player));
    }

    /**
     * Ajouter un joueur à la liste des jury
     *
     * @param player
     */
    public void addJury(Player player) {
        jury.add(player);
    }

    /**
     * Retirer un joueur de la liste des jury
     *
     * @param player
     */
    public void removeJury(Player player) {
        jury.remove(player);
    }

    /***
     * Broadcast un message dans la partie
     * @param message
     */
    public void broadcast(final String message) {
        Bukkit.broadcastMessage(message);
    }

}
