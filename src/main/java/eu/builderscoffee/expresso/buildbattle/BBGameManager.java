package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.expressos.ExpressoManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.GamePhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.JuryPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.LaunchingPhase;
import eu.builderscoffee.expresso.task.GameTask;
import eu.builderscoffee.expresso.task.StartTask;
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
    @Getter
    private ExpressoManager expressoManager;
    // Tasks
    @Getter
    @Setter
    private StartTask startTask;
    @Getter
    @Setter
    private GameTask gameTask;
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
    private Expresso expresso;

    public BBGameManager(final BBGame game) {
        // Instances
        this.main = Main.getInstance();
        this.game = game;
        // Managers
        this.expressoManager = Main.getBbGame().getExpressoManager();
        // Tasks
        this.startTask = new StartTask(getGame(), 30);
        this.gameTask = new GameTask(getGame(), 7200);
        // Others
        this.phases.incrementAndGet();
        this.expresso = expressoManager.getCurrentExpresso();
    }

    // GAME MANAGEMENT

    /***
     * Checker si la partie peux démarrer
     */
    @SneakyThrows
    public void checkStart() {
        if (this.shouldStart()) {
            //this.nextPhase();
            //this.startPhase(LaunchingPhase.class);
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
     * Décompte avant le lancement d'une partie
     */
    public void startLaunchCountdown() {
        this.getGame().setBbState(BBState.LAUNCHING);
        this.getStartTask().runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    /***
     * Annuler le décompte avant le lancement
     */
    public void cancelLaunchCountdown() {
        this.getGame().setBbState(BBState.WAITING);
        this.getStartTask().cancel();
        this.getStartTask().setTime(30);
    }

    /***
     * Lancer une nouvelle partie
     */
    public void startGame() {
        this.getStartTask().cancel();
        this.getGame().setBbState(BBState.IN_GAME);
        this.getGameTask().runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    /***
     * Stopper la partie en cours
     */
    public void endGame() {
        if (!this.getGameTask().isCancelled()) {
            this.getGameTask().cancel();
            this.getGame().setBbState(BBState.ENDING);
            this.disablePlugins();

        }
    }

    // PHASE SYSTEM

    /***
     * Démarrer une nouvelle phase
     * @param runnable - La task bukkit
     * @throws ReflectiveOperationException - Exception d'une réflection
     */
    public void startPhase(Class<? extends BukkitRunnable> runnable) throws ReflectiveOperationException {
        setCurrentTask(((BukkitRunnable)  runnable.getDeclaredConstructors()[0].newInstance(main)).runTaskTimerAsynchronously(main, 0, 20));
    }

    /***
     * Stopper la phase en cours
     */
    public void cancelPhase() {
        getCurrentTask().cancel();
    }

    /***
     * Retourne la phase en cours
     */
    public BBPhase currentPhase() { return expresso.getCurrentPhase();}

    /***
     * Démarrer la prochaine phase
     */
    public void nextPhase() {
        this.expressoManager.getCurrentExpresso().getPhases().poll();
    }

    /***
     * Check la phase en cours
     * et retourne un nouvelle état
     * de partie
     */
    public void checkPhase() {
        if(expresso.getPhases().equals(LaunchingPhase.class)) this.getGame().setBbState(BBState.LAUNCHING);
        else if(expresso.getPhases().equals(GamePhase.class)) this.getGame().setBbState(BBState.IN_GAME);
        else if (expresso.getPhases().equals(JuryPhase.class)) this.getGame().setBbState(BBState.ENDING);
    }

    // Other Stuff

    /***
     * Désactiver les plugin non nécessaire après la phase IN-GAME
     */
    public void disablePlugins() {
        PluginManager pm = Main.getInstance().getServer().getPluginManager();
        List<String> pluginToDisable = Main.getSettings().getPluginEndDisable();
        pluginToDisable.forEach(s -> {
            if (pm.getPlugin(s) != null) {
                pm.disablePlugin(pm.getPlugin(s));
            }
        });
    }

    /***
     * État d'une partie en cours
     */
    public enum BBState {
        WAITING,
        LAUNCHING,
        IN_GAME,
        ENDING
    }

}

