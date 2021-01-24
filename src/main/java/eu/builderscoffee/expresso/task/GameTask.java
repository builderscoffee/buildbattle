package eu.builderscoffee.expresso.task;

import eu.builderscoffee.api.utils.Title;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.utils.Log;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.GameMode.SPECTATOR;


public class GameTask extends BukkitRunnable {
    @Getter
    private final BBGame game;
    @Getter
    @Setter
    private int time;
    private final int maxTime;

    public GameTask(final BBGame game, final int maxTime) {
        this.game = game;
        this.maxTime = maxTime;
    }

    @Override
    public void run() {
        if (time == 0) {
            getOnlinePlayers().forEach(p -> {
				new Title("Thèmes", Main.getSettings().getBuildTheme(), 20, 20, 20).send(p);
			});
        }
        if (time % 60 == 0) {
            Log.get().info(" " + time/60 + " minutes de jeux");
        }
        if (time >= maxTime) {
			for (Player player : getOnlinePlayers()) player.setGameMode(SPECTATOR);
            game.bbGameManager.endGame();
        }
        switch (time) {
            case 1800:
                this.getGame().broadcast("§b 1h30 §ede jeu restantes.");
                break;
            case 3600:
                this.getGame().broadcast("§b 1h §ede jeu restantes.");
                break;
            case 5400:
                this.getGame().broadcast("§b 30 minutes §ede jeu restantes.");
                break;
            case 6600:
                this.getGame().broadcast("§b 10 minutes §ede jeu restantes.");
                break;
        }
        ++this.time;
    }
}
