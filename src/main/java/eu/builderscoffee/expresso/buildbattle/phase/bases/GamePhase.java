package eu.builderscoffee.expresso.buildbattle.phase.bases;


import eu.builderscoffee.api.bukkit.utils.Title;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.utils.Log;
import eu.builderscoffee.expresso.utils.MessageUtils;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static eu.builderscoffee.expresso.utils.TimeUtils.HOUR;
import static eu.builderscoffee.expresso.utils.TimeUtils.MIN;
import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.GameMode.CREATIVE;

public class GamePhase implements BBPhase {

    private int[] titleTime;
    private int[] bcTime;
    @Getter
    @Setter
    private BuildBattle game;
    private int time;
    private int currentTime;
    private int defaultTime;
    private TimeUnit timeUnit;

    public GamePhase(int defaultTime) {
        this.defaultTime = defaultTime;
        this.time = defaultTime;
        this.timeUnit = TimeUnit.MINUTES;
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
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int defaultTime() {
        return defaultTime;
    }

    @Override
    public TimeUnit timeUnit() {
        return timeUnit;
    }

    @Override
    public BuildBattleManager.GameState state() {
        return BuildBattleManager.GameState.IN_GAME;
    }

    @Override
    public BukkitRunnable runnable() {
        if(Objects.isNull(this.bcTime)) this.bcTime = addTimeEach(new int[]{defaultTime - 10 * MIN, defaultTime - 30 * MIN, defaultTime / 2}, HOUR);
        if(Objects.isNull(this.titleTime)) this.titleTime = new int[]{defaultTime - 1, defaultTime - 2, defaultTime - 3, defaultTime - 4, defaultTime - 5, defaultTime - 10, defaultTime - 20, defaultTime - 30, defaultTime - MIN};
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (currentTime == 0) {
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
                    }.runTask(ExpressoBukkit.getInstance());

                    ExpressoBukkit.getInstance().getServer().getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(MessageUtils.getMessageConfig(onlinePlayer).getGame().getPlotAuto().replace("%prefix%", MessageUtils.getDefaultMessageConfig().getPrefix())));

                }
                // Log les minutes du jeux en console
                if (currentTime % 60 == 0) Log.get().info(" " + currentTime / 60 + " minutes de jeux");

                // Tout les X temps envoyé un broadcast pour le temps de jeux restant
                Arrays.stream(bcTime).filter(i -> i == currentTime).forEach(i -> ExpressoBukkit.getInstance().getServer().getOnlinePlayers().forEach(player -> player.sendMessage(MessageUtils.getMessageConfig(player).getGame().getRemainingGames().replace("%prefix%",MessageUtils.getDefaultMessageConfig().getPrefix()).replace("%time%" ,TimeUtils.getDurationString(time - currentTime)))));

                // Tout les X temps envoyé un title pour la dernière minutes restante
                Arrays.stream(titleTime).filter(i -> i == currentTime).forEach(i -> getOnlinePlayers().forEach(p -> {
                    new Title(MessageUtils.getMessageConfig(p).getGame().getRemainingTime(), TimeUtils.getDurationString(time - currentTime), 20, 5, 20).send(p);
                }));

                // Passer à l'étape suivante si le temps est écoulé
                //for (Player player : getOnlinePlayers()) player.setGameMode(SPECTATOR);
                if (currentTime >= time) ExpressoBukkit.getBbGame().getBbGameManager().nextPhase();

                ++currentTime;
            }
        };
    }

    @Override
    public BuildBattleEngine engine() {
        return null;
    }

    protected int[] addTimeEach(int[] array, int seconds) {
        int[] newArray = new int[(int) (array.length + Math.floor(time / seconds) - 1)];

        IntStream.range(0, array.length).forEach(i -> newArray[i] = array[i]);

        for (int i = 1; i < time / seconds; i++)
            newArray[array.length + i - 1] = seconds * i;

        return newArray;
    }
}
