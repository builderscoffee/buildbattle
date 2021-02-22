package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.api.utils.Title;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.utils.Log;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.GameMode.CREATIVE;
import static org.bukkit.GameMode.SPECTATOR;

public class GamePhase implements BBPhase {

    private final int maxTime;
    @Getter
    @Setter
    private BBGame game;
    @Getter
    @Setter
    private int time;
    private final int[] bcTime;
    private final int[] titleTime;

    public GamePhase(int maxTime) {
        this.maxTime = maxTime;
        this.bcTime = new int[]{maxTime - 600, maxTime - 1800, maxTime - maxTime % 2};
        this.titleTime = new int[]{maxTime - 1, maxTime - 2, maxTime - 3, maxTime - 4, maxTime - 5, maxTime - 10, maxTime - 20, maxTime - 30, maxTime - 60};
    }

    @Override
    public String name() {
        return "En jeux";
    }

    @Override
    public String description() {
        return "Représente une partie en cours";
    }

    @Override
    public int time() {
        return maxTime;
    }

    @Override
    public BBGameManager.BBState state() {
        return BBGameManager.BBState.IN_GAME;
    }

    @Override
    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                // Démarrer la game en dévoilant le thème
                if (time == 0) {
                    getOnlinePlayers().forEach(p -> {
                        new Title("Thème", Main.getSettings().getBuildTheme(), 20, 20, 20).send(p);
                        p.setGameMode(CREATIVE);
                    });
                    getGame().broadcast(Main.getMessages().getPrefix() + "§a/plot auto pour participer");
                }
                // Log les minutes du jeux en console
                if (time % 60 == 0) {
                    Log.get().info(" " + time / 60 + " minutes de jeux");
                }
                // Passer à l'étape suivante si le temps est écoulé
                if (time >= maxTime) {
                    for (Player player : getOnlinePlayers()) player.setGameMode(SPECTATOR);
                    //game.getBbGameManager().endGame();
                    getGame().getBbGameManager().nextPhase();
                }
                // Tout les X temps envoyé un broadcast pour le temps de jeux restant
                for (int i : bcTime) {
                    if (i == time) {
                        getGame().broadcast(Main.getMessages().getPrefix() + "§a" + TimeUtils.getDurationString(time) + "§fde jeux restantes !");
                    }
                }
                // Tout les X temps envoyé un title pour la dernière minutes restante
                for (int i : titleTime) {
                    if( i == time) {
                        getOnlinePlayers().forEach(p -> {
                            new Title("§aTemps restant", TimeUtils.getDurationString(time), 20, 5, 20).send(p);
                        });
                    }
                }
                ++time;
            }
        };
    }
}
