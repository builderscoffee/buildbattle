package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.task.GameTask;
import eu.builderscoffee.expresso.task.StartTask;
import eu.builderscoffee.expresso.utils.Log;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.List;

public class BBGameManager {

    @Getter
    private final BBGame game;
    @Getter
    @Setter
    private StartTask startTask;
    @Getter
    @Setter
    private GameTask gameTask;

    public BBGameManager(final BBGame game) {
        this.game = game;
        this.startTask = new StartTask(getGame(), 30);
        this.gameTask = new GameTask(getGame(), 7200);
    }

    /***
     * Cacher si la partie peux démarrer
     */
    public void checkStart() {
        if (this.shouldStart()) {
            this.startLaunchCountdown();
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

    /***
     * Désactiver les plugin non nécessaire après la phase IN-GAME
     */
    public void disablePlugins() {
        PluginManager pm = Main.getInstance().getServer().getPluginManager();
        List<String> pluginToDisable = Main.getSettings().getPluginEndDisable();
        pluginToDisable.forEach(s -> {
           if(pm.getPlugin(s) != null) {
               pm.disablePlugin(pm.getPlugin(s));
           }
        });
    }

    /***
     * Phases / états d'une partie en cours
     */
    public enum BBState {
        WAITING,
        LAUNCHING,
        IN_GAME,
        ENDING
    }

}

