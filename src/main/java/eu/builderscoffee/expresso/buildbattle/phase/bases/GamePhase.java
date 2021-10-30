package eu.builderscoffee.expresso.buildbattle.phase.bases;


import eu.builderscoffee.api.bukkit.utils.Title;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.utils.Log;
import eu.builderscoffee.expresso.utils.MessageUtils;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.stream.IntStream;

import static eu.builderscoffee.expresso.utils.TimeUtils.HOUR;
import static eu.builderscoffee.expresso.utils.TimeUtils.MIN;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.GameMode.CREATIVE;

public class GamePhase implements BBPhase {

    private final int maxTime;
    private final int[] titleTime;
    private int[] bcTime;
    @Getter
    @Setter
    private BuildBattle game;
    @Getter
    @Setter
    private int time;

    public GamePhase(int maxTime) {
        this.maxTime = maxTime;
        this.bcTime = addTimeEach(new int[]{maxTime - 10 * MIN, maxTime - 30 * MIN, maxTime / 2}, HOUR);
        this.titleTime = new int[]{maxTime - 1, maxTime - 2, maxTime - 3, maxTime - 4, maxTime - 5, maxTime - 10, maxTime - 20, maxTime - 30, maxTime - MIN};
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
        return time;
    }

    @Override
    public BuildBattleManager.GameState state() {
        return BuildBattleManager.GameState.IN_GAME;
    }

    @Override
    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (time == 0) {
                    // Démarrer la game en dévoilant le thème
                    // et définir la gamemode en créatif pour chaques
                    // joueurs

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            getOnlinePlayers().forEach(p -> {
                                new Title(MessageUtils.getMessageConfig(p).getGame().getThemesTitle(), MessageUtils.getMessageConfig(p).getBoard().getBuildTheme(), 20, 20, 20).send(p);
                                p.setGameMode(CREATIVE);
                            });
                        }
                    }.runTask(Main.getInstance());

                    Main.getInstance().getServer().getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(MessageUtils.getMessageConfig(onlinePlayer).getGame().getPlotAuto().replace("%prefix%", MessageUtils.getDefaultMessageConfig().getPrefix())));

                }
                // Log les minutes du jeux en console
                if (time % 60 == 0) Log.get().info(" " + time / 60 + " minutes de jeux");

                // Tout les X temps envoyé un broadcast pour le temps de jeux restant
                Arrays.stream(bcTime).filter(i -> i == time).forEach(i -> Main.getInstance().getServer().getOnlinePlayers().forEach(player -> player.sendMessage(MessageUtils.getMessageConfig(player).getGame().getRemainingGames().replace("%prefix%",MessageUtils.getDefaultMessageConfig().getPrefix()).replace("%time%" ,TimeUtils.getDurationString(maxTime - time)))));

                // Tout les X temps envoyé un title pour la dernière minutes restante
                Arrays.stream(titleTime).filter(i -> i == time).forEach(i -> getOnlinePlayers().forEach(p -> {
                    new Title(MessageUtils.getMessageConfig(p).getGame().getRemainingTime(), TimeUtils.getDurationString(maxTime - time), 20, 5, 20).send(p);
                }));

                // Passer à l'étape suivante si le temps est écoulé
                //for (Player player : getOnlinePlayers()) player.setGameMode(SPECTATOR);
                if (time >= maxTime) Main.getBbGame().getBbGameManager().nextPhase();

                ++time;
            }
        };
    }

    @Override
    public BuildBattleEngine engine() {
        return null;
    }

    protected int[] addTimeEach(int[] array, int seconds) {
        int[] newArray = new int[(int) (array.length + Math.floor(maxTime / seconds) - 1)];

        IntStream.range(0, array.length).forEach(i -> newArray[i] = array[i]);

        for (int i = 1; i < maxTime / seconds; i++)
            newArray[array.length + i - 1] = seconds * i;

        return newArray;
    }
}
