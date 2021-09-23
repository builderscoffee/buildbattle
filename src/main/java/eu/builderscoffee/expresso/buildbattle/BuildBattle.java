package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorLeaveEvent;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.expressos.ExpressoManager;
import eu.builderscoffee.expresso.buildbattle.notation.NotationManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.teams.TeamManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BuildBattle {

    @Getter
    private final List<Player> competitor = new ArrayList<>(); // La liste des compétitors
    private final List<Player> jury = new ArrayList<>();
    @Getter
    @Setter
    public BuildBattleManager.BBState bbState = BuildBattleManager.BBState.WAITING;
    @Getter
    @Setter
    public TeamManager teamManager;
    @Getter
    @Setter
    private Main main;
    /***
     * Le type d'expresso en cours !
     */
    @Getter
    private NotationManager notationManager;
    @Getter
    @Setter
    private Expresso expressoType;
    @Getter
    @Setter
    private BuildBattleInstanceType bbGameTypes;
    @Getter
    @Setter
    private Deque<BBPhase> expressoPhases;
    @Getter
    @Setter
    private BuildBattleManager bbGameManager;
    @Getter
    @Setter
    private ExpressoManager expressoManager;
    @Getter
    @Setter
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

    /***
     * Définir ou redéfinir l'expresso de la partie en cours
     *
     * @param expresso
     */
    public final void defineExpresso(Expresso expresso) {
        setExpressoType(expresso);
        setExpressoPhases(expresso.getPhases());

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
