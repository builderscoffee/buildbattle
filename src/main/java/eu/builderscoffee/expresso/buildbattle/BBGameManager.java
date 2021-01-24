package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.task.GameTask;
import eu.builderscoffee.expresso.task.StartTask;
import lombok.Getter;
import lombok.Setter;

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

    public void checkStart() {
        if (this.shouldStart()) {
            this.startLaunchCountdown();
        }
    }

    public boolean shouldStart() {
        return this.getGame().getBbState() == BBState.WAITING
                && this.getGame().isReady();
    }

    public void startLaunchCountdown() {
        this.getGame().setBbState(BBState.LAUNCHING);
        this.getStartTask().runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    public void cancelLaunchCountdown(final String reason) {
        this.getGame().setBbState(BBState.WAITING);
        this.getStartTask().cancel();
        this.getStartTask().setTime(30);
        //this.getGame().broadcast(reason);
    }

    public void startGame() {
        this.getStartTask().cancel();
        this.getGame().setBbState(BBState.IN_GAME);
        this.getGameTask().runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    public void endGame() {
        this.getGameTask().cancel();
        this.getGame().setBbState(BBState.ENDING);
    }

    public enum BBState {
        WAITING,
        LAUNCHING,
        IN_GAME,
        ENDING
    }

}

