package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.board.BBBoard;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.utils.Log;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BuildBattleManager {

    // Instances
    @Getter
    private final ExpressoBukkit expressoBukkit;
    @Getter
    private final BuildBattle game;
    // Managers
    @Getter
    @Setter
    private ExpressoManager expressoManager;
    // Engines
    @Getter
    @Setter
    private BuildBattleEngine gameEngine;
    // Tasks
    @Getter
    @Setter
    private BukkitTask currentTask;
    // Others
    @Getter
    @Setter
    private AtomicInteger phases;
    @Getter
    @Setter
    private BBPhase bbPhase;
    @Getter
    @Setter
    private String themes;

    public BuildBattleManager(final BuildBattle game) {
        // Instances
        this.expressoBukkit = ExpressoBukkit.getInstance();
        this.game = game;
        // Managers
        setExpressoManager(game.getExpressoManager());
        // Définir la phase par défault
        this.getGame().setGameState(GameState.WAITING);
    }

    // GAME MANAGEMENT

    /***
     * Lancer
     */
    public void startGame() {
        // La partie est prête à démarrer
        ExpressoBukkit.getBbGame().setReady(true);

        // Lancer le check de démarrage
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ExpressoBukkit.getInstance(), () -> {
            if (game != null) {
                this.checkStart();
            }
        }, 0L, 20L);

        // Mettre à jour le scoreboard
        ExpressoBukkit.getInstance().getServer().getScheduler().runTaskTimer(ExpressoBukkit.getInstance(), () -> {
            BBBoard.boards.values().forEach(BBBoard::updateBoard);
        }, 0, 20);
    }


    /***
     * Checker si la partie peux démarrer
     */
    @SneakyThrows
    public void checkStart() {
        if (this.shouldStart()) {
            // Lancer la prochaine phase
            this.nextPhase();
        }
    }

    /***
     * Retourne si la partie est prète à démarrer
     * @return
     */
    public boolean shouldStart() {
        return this.getGame().getGameState() == GameState.WAITING
                && this.getGame().isReady();
    }

    /***
     * Check l'état de la partie
     * @return
     */
    public boolean isRunning() {
        return this.getGame().getGameState() != GameState.WAITING;
    }

    /***
     * Annuler la partie en cours et
     * reset le système
     */
    public void cancelGame() {
        // On stopper la phase en cours si ce n'est déja pas fait
        cancelPhase();
        // On redéfinis l'état
        this.game.setGameState(GameState.WAITING);
        this.game.setReady(false);
    }

    /***
     * Stopper la partie en cours
     */
    public void endGame() {
        if (this.game.getBuildBattleGameType().getCurrentPhase().state() == GameState.ENDING) {
            Log.get().info("Une erreur est survenue lors de la fin de la partie !");
        } else {
            // Définir l'état de fin de la partie
            this.getGame().setGameState(this.game.getExpressoGameType().getCurrentPhase().state());
            // Couper la phase en cours
            this.cancelPhase();
            // Désactiver les plugin de build
            this.disablePlugins();
        }
    }

    // PHASE SYSTEM

    /***
     * Démarrer une nouvelle phase
     * @param runnable - La task bukkit
     */
    public void startPhase(BukkitRunnable runnable) {
        if (getCurrentTask() != null) {
            getCurrentTask().cancel();
        }
        setCurrentTask(runnable.runTaskTimerAsynchronously(expressoBukkit, 0, 20));
    }

    /***
     * Stopper la phase en cours
     */
    public void cancelPhase() {
        Log.get().info("Phase cancel : " + this.game.getExpressoGameType().getCurrentPhase().name());
        if (!this.getCurrentTask().isCancelled()) {
            getCurrentTask().cancel();
        }
    }

    /***
     * Démarrer la prochaine phase
     */
    @SneakyThrows
    public void nextPhase() {
        // Get & Poll la prochaine phase
        this.game.getBuildBattleGameType().setCurrentPhase(this.game.getInstancePhases().poll());
        Log.get().info("Phase en cours : " + this.game.getExpressoGameType().getCurrentPhase().name());
        // Définir le status de la prochaine phase
        this.getGame().setGameState(this.game.getBuildBattleGameType().getCurrentPhase().state());
        // Lancer la Task de la prochaine phase
        this.startPhase(this.game.getBuildBattleGameType().getCurrentPhase().runnable());
        // Lancer le moteur de la partie si il en existe un pour la phase en cours
        if (this.game.getBuildBattleGameType().getCurrentPhase().engine() != null) {
            // Lancer le moteur de la partie
            this.game.getBuildBattleGameType().getCurrentPhase().engine().load();
            // Enregister les evenements propre aux moteur de la partie
            this.game.getBuildBattleGameType().getCurrentPhase().engine().registerListener();
        }
    }

    // OTHER STUFF

    /***
     * Désactiver les plugin non nécessaire après la phase IN-GAME
     */
    public void disablePlugins() {
        PluginManager pm = ExpressoBukkit.getInstance().getServer().getPluginManager();
        List<String> pluginToDisable = ExpressoBukkit.getSettings().getGame_plugin_end_disable();
        pluginToDisable.forEach(s -> {
            if (pm.getPlugin(s) != null) {
                pm.disablePlugin(pm.getPlugin(s));
            }
        });
    }

    // STATE

    /***
     * État d'une partie en cours
     */
    public enum GameState {
        NONE,
        WAITING,
        LAUNCHING,
        IN_GAME,
        ENDING
    }
}

