package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.commons.common.data.DataManager;
import eu.builderscoffee.commons.common.data.tables.BuildbattleEntity;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.competitor.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.competitor.CompetitorLeaveEvent;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.expressos.ExpressoManager;
import eu.builderscoffee.expresso.buildbattle.expressos.types.IlClassicoExpresso;
import eu.builderscoffee.expresso.buildbattle.notation.NotationManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.teams.TeamManager;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Data
@Accessors(chain = true)
public class BuildBattle {

    // Liste des compétiteurs
    private List<Player> competitor = new ArrayList<>();
    // Liste des jurys
    private List<Player> jury = new ArrayList<>();
    // Type d'instance ( Expresso , BB , tournois )
    private BuildBattleInstanceType bbGameTypes = BuildBattleInstanceType.NONE;
    // Etat de la partie
    public BuildBattleManager.BBState bbState = BuildBattleManager.BBState.WAITING;
    // Phase de la partie
    private Deque<BBPhase> instancePhases;
    // Type d'expresso
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
     */

    public BuildBattle() {
        // Définir l'instance du BuildBattleManager
        setBbGameManager(new BuildBattleManager(this));

        // Setup les managers
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
