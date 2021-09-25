package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.competitor.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.competitor.CompetitorLeaveEvent;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoGameType;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoManager;
import eu.builderscoffee.expresso.buildbattle.games.expressos.types.IlClassicoExpressoGameType;
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
    private BuildBattleInstanceType bbType;
    public BuildBattleManager.BBState bbState = BuildBattleManager.BBState.WAITING;
    private Deque<BBPhase> instancePhases;
    private ExpressoGameType expressoGameTypeType = new IlClassicoExpressoGameType();
    // Manager
    private BuildBattleManager bbGameManager;
    private ExpressoManager expressoManager;
    public TeamManager teamManager;
    private NotationManager notationManager;
    // Instance Check
    private boolean isReady = false;


    /***
     * Créer une instance d'une BBGame
     */

    public BuildBattle(Main main) {
        setMain(main);
        setBbGameManager(new BuildBattleManager(this));
        /*
        if(bbGameTypes.equals(BuildBattleInstanceType.EXPRESSO)) {
            selectExpresso(battleType);
            setExpressoManager(new ExpressoManager(this));
        } else if (bbGameTypes.equals(BuildBattleInstanceType.CLASSIC)) {

        } else if (bbGameTypes.equals(BuildBattleInstanceType.TOURNAMENT)) {

        }
        */
        teamManager = new TeamManager();
        notationManager = new NotationManager();
    }

    // EXPRESSO INSTANCE

    /***
     * Définir ou redéfinir l'expresso de la partie en cours
     *
     * @param expressoGameType
     */
    public final void selectExpresso(ExpressoGameType expressoGameType) {
        setExpressoGameTypeType(expressoGameType);
        setInstancePhases(expressoGameType.getPhases());

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
