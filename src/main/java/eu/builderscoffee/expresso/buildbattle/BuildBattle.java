package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.competitor.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.competitor.CompetitorLeaveEvent;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.expressos.ExpressoManager;
import eu.builderscoffee.expresso.buildbattle.notation.NotationManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.teams.TeamManager;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Data
public class BuildBattle {

    private final List<Player> competitor = new ArrayList<>(); // La liste des compétitors
    private final List<Player> jury = new ArrayList<>(); // La liste des jurys

    private Main main;
    private BuildBattleInstanceType bbGameTypes;
    public BuildBattleManager.BBState bbState = BuildBattleManager.BBState.WAITING;
    private Deque<BBPhase> instancePhases;
    private Expresso expressoType;
    // Manager
    private BuildBattleManager bbGameManager;
    private ExpressoManager expressoManager;
    public TeamManager teamManager;
    private NotationManager notationManager;
    // Instance Check
    private boolean isReady = false;


    /***
     * Créer une instance d'une BBGame
     * @param type
     */

    public BuildBattle(Main main, Expresso type) {
        setMain(main);
        setBbGameManager(new BuildBattleManager(this));
        if(bbGameTypes.equals(BuildBattleInstanceType.EXPRESSO)) {
            defineExpresso(type);
            setExpressoManager(new ExpressoManager(this));
        } else if (bbGameTypes.equals(BuildBattleInstanceType.CLASSIC)) {

        } else if (bbGameTypes.equals(BuildBattleInstanceType.TOURNAMENT)) {

        }
        teamManager = new TeamManager();
        notationManager = new NotationManager();
    }

    // EXPRESSO INSTANCE

    /***
     * Définir ou redéfinir l'expresso de la partie en cours
     *
     * @param expresso
     */
    public final void defineExpresso(Expresso expresso) {
        setExpressoType(expresso);
        setInstancePhases(expresso.getPhases());

    }

    public final void startExpresso() {
        bbGameManager.startGame();
    }

    // COMPETITOR

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

    // JURY

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

    // OTHER STUFF

    /***
     * Broadcast un message dans la partie
     * @param message
     */
    public void broadcast(final String message) {
        Bukkit.broadcastMessage(message);
    }

}
