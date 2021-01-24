package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorLeaveEvent;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.expressos.Expressos;
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
    public static Expressos expressoType;
    public static Expresso expressoInstance;
    @Getter
    public String display = "BuildBattle ";
    @Getter
    @Setter
    public BBGameManager.BBState bbState;
    @Getter
    @Setter
    public BBGameManager bbGameManager;
    @Getter
    private final List<Player> competitor; // La liste des compétitors
    private final List<Player> jury;
    @Getter
    @Setter
    private boolean isReady;

    /***
     * Créer une instance d'une BBGame
     * @param type
     */
    public BBGame(Expressos type) {
        competitor = new ArrayList<>();
        jury = new ArrayList<>();
        expressoType = type;
        isReady = false;
        bbState = BBGameManager.BBState.WAITING;
        bbGameManager = new BBGameManager(this);
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

    public void broadcast(final String message) {
        Bukkit.broadcastMessage(message);
    }

}
