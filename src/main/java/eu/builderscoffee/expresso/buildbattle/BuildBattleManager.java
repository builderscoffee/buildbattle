package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.utils.Log;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BuildBattleManager {

    // Instances
    @Getter
    private final Main main;
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

    public BuildBattleManager(final BuildBattle game) {
        // Instances
        this.main = Main.getInstance();
        this.game = game;
        // Managers
        setExpressoManager(game.getExpressoManager());
        // Définir la phase par défault
        this.getGame().setBbState(BBState.WAITING);
    }

    // GAME MANAGEMENT

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
        return this.getGame().getBbState() == BBState.WAITING
                && this.getGame().isReady();
    }

    /***
     * Lancer une nouvelle partie
     */
    public void startGame() {
        this.getGame().setBbState(BBState.IN_GAME);
    }

    /***
     * Annuler la partie en cours et
     * reset le système
     */
    public void cancelGame() {
        // On stopper la phase en cours si ce n'est déja pas fait
        cancelPhase();
        // On redéfinis l'état
        this.game.setBbState(BBState.WAITING);
        this.game.setReady(false);
    }


    /***
     * Stopper la partie en cours
     */
    public void endGame() {
        if (this.game.getExpressoGameType().getCurrentPhase().state() == BBState.ENDING) {
            Log.get().info("Une erreur est survenue lors de la fin de la partie !");
        } else {
            // Définir l'état de fin de la partie
            this.getGame().setBbState(this.game.getExpressoGameType().getCurrentPhase().state());
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
        setCurrentTask(runnable.runTaskTimerAsynchronously(main, 0, 20));


    }

    /***
     * Stopper la phase en cours
     */
    public void cancelPhase() {
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
        this.game.getExpressoGameType().setCurrentPhase(this.game.getInstancePhases().poll());
        Log.get().info("Phase en cours " + this.game.getExpressoGameType().getCurrentPhase().name());
        // Définir le status de la prochaine phase
        this.getGame().setBbState(this.game.getExpressoGameType().getCurrentPhase().state());
        // Lancer la Task de la prochaine phase
        this.startPhase(this.game.getExpressoGameType().getCurrentPhase().runnable());
        // Lancer le moteur de la partie si il en existe un pour la phase en cours
        if (this.game.getExpressoGameType().getCurrentPhase().engine() != null) {
            // Lancer le moteur de la partie
            this.game.getExpressoGameType().getCurrentPhase().engine().load();
            // Enregister les evenements propre aux moteur de la partie
            this.game.getExpressoGameType().getCurrentPhase().engine().registerListener();
        }
    }

    // OTHER STUFF

    /***
     * Désactiver les plugin non nécessaire après la phase IN-GAME
     */
    public void disablePlugins() {
        PluginManager pm = Main.getInstance().getServer().getPluginManager();
        List<String> pluginToDisable = Main.getSettings().getGame_plugin_end_disable();
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
    public enum BBState {
        NONE,
        WAITING,
        LAUNCHING,
        IN_GAME,
        ENDING
    }

    /***
     * État de la partie
     */
    public enum BBPartySize {
        SOLO,
        TEAM
    }
}

