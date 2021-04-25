package eu.builderscoffee.expresso.buildbattle.phase.types;


import eu.builderscoffee.api.bukkit.utils.Title;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.IGameEngine;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.utils.Log;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.GameMode.CREATIVE;

public class GamePhase implements BBPhase {

    protected static final int HOUR = 3600;
    protected static final int MIN = 3600;

    private final int maxTime;
    private final int[] bcTime;
    private final int[] titleTime;
    @Getter
    @Setter
    private BBGame game;
    @Getter
    @Setter
    private int time;

    public GamePhase(int maxTime) {
        this.maxTime = maxTime;
        this.bcTime = new int[]{timeLeft(10*MIN), timeLeft(30*MIN), maxTime / 2};
        this.titleTime = new int[]{timeLeft(1), timeLeft(2), timeLeft(3), timeLeft(4), timeLeft(5), timeLeft(10), timeLeft(20), timeLeft(30), timeLeft(1*MIN)};
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
    public BBGameManager.BBState state() {
        return BBGameManager.BBState.IN_GAME;
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
                                new Title("Thème", Main.getSettings().getBoard_build_theme(), 20, 20, 20).send(p);
                                p.setGameMode(CREATIVE);
                            });
                        }
                    }.runTask(Main.getInstance());
                    Main.getBbGame().broadcast(Main.getMessages().getGlobal_prefix() + "§a/plot auto pour participer");
                }
                // Log les minutes du jeux en console
                if (time % 60 == 0) {
                    Log.get().info(" " + time / 60 + " minutes de jeux");
                }

                // Tout les X temps envoyé un broadcast pour le temps de jeux restant
                for (int i : bcTime) {
                    if (i == time) {
                        Main.getBbGame().broadcast(Main.getMessages().getGlobal_prefix() + "§a" + TimeUtils.getDurationString(timeLeft(time)) + " §fde jeux restantes !");
                    }
                }

                // Tout les X temps envoyé un title pour la dernière minutes restante
                for (int i : titleTime) {
                    if (i == time) {
                        getOnlinePlayers().forEach(p -> {
                            new Title("§aTemps restant", TimeUtils.getDurationString(timeLeft(time)), 20, 5, 20).send(p);
                        });
                    }
                }

                // Passer à l'étape suivante si le temps est écoulé
                if (time >= maxTime) {
                    //for (Player player : getOnlinePlayers()) player.setGameMode(SPECTATOR);
                    Main.getBbGame().getBbGameManager().nextPhase();
                }

                ++time;
            }
        };
    }

    @Override
    public IGameEngine engine() {
        return null;
    }

    protected int timeLeft(int s){
        return maxTime - s;
    }
}
