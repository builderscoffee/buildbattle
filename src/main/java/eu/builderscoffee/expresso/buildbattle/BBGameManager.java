package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.expressos.ExpressoManager;
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

public class BBGameManager {

    // Instances
    @Getter
    private final Main main;
    @Getter
    private final BBGame game;
    // Managers
    @Getter @Setter
    private ExpressoManager expressoManager;
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

    public BBGameManager(final BBGame game) {
        // Instances
        this.main = game.getMain().getInstance();
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
     * Stopper la partie en cours
     */
    public void endGame() {
        if (!(this.game.getExpressoType().getCurrentPhase().state() != BBState.ENDING)) {
            Log.get().info("Une erreur est survenue lors de la fin de la partie !");
        } else {
            // Définir l'état de fin de la partie
            this.getGame().setBbState(this.game.getExpressoType().getCurrentPhase().state());
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
        if(getCurrentTask() != null) {
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
        // Poll la prochaine phase
        Log.get().info(this.game.getExpressoType().getName());
        Log.get().info(this.game.getExpressoType().getDescription().toString());
        //this.game.getExpressoType().getPhases().this.game.getExpressoType().getPhases().removeFirst();
        Log.get().info("Phase size " + this.game.getExpressoPhases().size());
        this.game.getExpressoType().setCurrentPhase(this.game.getExpressoPhases().poll());
        Log.get().info("Phase en cours " + this.game.getExpressoType().getCurrentPhase().name());
        // Définir le status de la prochaine phase
        this.getGame().setBbState(this.game.getExpressoType().getCurrentPhase().state());
        // Lancer la prochaine phase
        this.startPhase(this.game.getExpressoType().getCurrentPhase().runnable());
    }

    // OTHER STUFF

    /***
     * Désactiver les plugin non nécessaire après la phase IN-GAME
     */
    public void disablePlugins() {
        PluginManager pm = main.getInstance().getServer().getPluginManager();
        List<String> pluginToDisable = Main.getSettings().getPluginEndDisable();
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

}

