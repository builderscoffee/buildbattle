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
import static org.bukkit.GameMode.CREATIVE;
import static org.bukkit.GameMode.SPECTATOR;


public class GameTask extends BukkitRunnable {
    @Getter
    private final BBGame game;
    private final int maxTime;
    @Getter
    @Setter
    private int time;

    public GameTask(final BBGame game, final int maxTime) {
        this.game = game;
        this.maxTime = maxTime;
    }

    @Override
    public void run() {
        if (time == 0) {
            getOnlinePlayers().forEach(p -> {
                new Title("Thème", Main.getSettings().getBuildTheme(), 20, 20, 20).send(p);
                p.setGameMode(CREATIVE);
            });
            this.getGame().broadcast(Main.getMessages().getPrefix() + "§a/plot auto pour participer");
        }
        if (time % 60 == 0) {
            Log.get().info(" " + time / 60 + " minutes de jeux");
        }
        if (time >= maxTime) {
            for (Player player : getOnlinePlayers()) player.setGameMode(SPECTATOR);
            game.getBbGameManager().endGame();
        }
        switch (time) {
            case 1800:
                this.getGame().broadcast(Main.getMessages().getPrefix() + " §a1h30 §fde jeu restante !");
                break;
            case 3600:
                this.getGame().broadcast(Main.getMessages().getPrefix() + " §a1h §fde jeu restante !");
                break;
            case 5400:
                this.getGame().broadcast(Main.getMessages().getPrefix() + " §a30min §fde jeu restantes !");
                break;
            case 6600:
                this.getGame().broadcast(Main.getMessages().getPrefix() + " §a10min §fde jeu restantes !");
                break;
            case 7140:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§71 minutes", 20, 5, 20).send(p);
                });
                break;
            case 7170:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§730 secondes", 20, 5, 20).send(p);
                });
            case 7180:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§720 secondes", 20, 5, 20).send(p);
                });
            case 7190:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§710 secondes", 20, 5, 20).send(p);
                });
                break;
            case 7195:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§75 secondes", 20, 5, 20).send(p);
                });
                break;
            case 7196:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§74 secondes", 20, 5, 20).send(p);
                });
                break;
            case 7197:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§73 secondes", 20, 5, 20).send(p);
                });
                break;
            case 7198:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§72 secondes", 20, 5, 20).send(p);
                });
                break;
            case 7199:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps restant", "§71 secondes", 20, 5, 20).send(p);
                });
                break;
            case 7200:
                getOnlinePlayers().forEach(p -> {
                    new Title("§aTemps écoulé", "§7merci d'avoir participé", 20, 20, 20).send(p);
                });
                break;
        }
        ++this.time;
    }
}
