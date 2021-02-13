package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorLeaveEvent;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.expressos.ExpressoManager;
import eu.builderscoffee.expresso.buildbattle.notation.NotationManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BBGame {

    /***
     * Le type d'expresso en cours !
     */
    @Getter
    private NotationManager notationManager;
    @Getter @Setter
    private Expresso expressoType;
    @Getter
    private final List<Player> competitor = new ArrayList<>(); // La liste des compétitors
    private final List<Player> jury = new ArrayList<>();
    @Getter
    @Setter
    public BBGameManager.BBState bbState = BBGameManager.BBState.WAITING;
    @Getter
    @Setter
    public BBGameManager bbGameManager;
    @Getter
    public ExpressoManager expressoManager;
    @Getter
    @Setter
    private boolean isReady = false;


    /***
     * Créer une instance d'une BBGame
     * @param type
     */
    public BBGame(Expresso type) {
        notationManager = new NotationManager();
        expressoType = type;
        bbGameManager = new BBGameManager(this);
        expressoManager = new ExpressoManager(this);
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
        Main.getInstance().getServer().getPluginManager().callEvent(new CompetitorJoinEvent(player));
    }

    /**
     * Retirer un joueur de la liste des participants
     *
     * @param player
     */
    public void removeCompetitor(Player player) {
        competitor.remove(player);
        Main.getInstance().getServer().getPluginManager().callEvent(new CompetitorLeaveEvent(player));
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
