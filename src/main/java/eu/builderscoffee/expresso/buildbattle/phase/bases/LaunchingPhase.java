package eu.builderscoffee.expresso.buildbattle.phase.bases;


import eu.builderscoffee.api.bukkit.utils.Title;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleEngine;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.utils.MessageUtils;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class LaunchingPhase implements BBPhase {

    private int time;
    private final int defaultTime;
    private final TimeUnit timeUnit;

    public LaunchingPhase(int defaultTime) {
        this.defaultTime = defaultTime;
        this.time = defaultTime;
        this.timeUnit = TimeUnit.SECONDS;
    }

    @Override
    public String name() {
        return "Lancement";
    }

    @Override
    public String description() {
        return "Lancement de la partie";
    }

    @Override
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int time() {
        return time;
    }

    @Override
    public int currentTime() {
        return time;
    }

    @Override
    public int defaultTime() {
        return defaultTime;
    }

    @Override
    public TimeUnit timeUnit() {
        return this.timeUnit;
    }

    @Override
    public BuildBattleManager.GameState state() {
        return BuildBattleManager.GameState.LAUNCHING;
    }

    @Override
    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                // Checker si la partie est prète à démarrer ?
                if (!ExpressoBukkit.getBbGame().isReady()) {
                    return;
                }
                // Lancer le chrono ( Title + Level )
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    player.setLevel(time());
                    if (time() == 30 || time() == 20 || time() == 10 || time() == 5)
                        new Title(MessageUtils.getMessageConfig(player).getGame().getStartInTitle(), MessageUtils.getMessageConfig(player).getGame().getStartInSubTitle(), 20, 10, 20).send(player);
                }
                // Décompte du temps dans le chat
                if (time() % 10 == 0 || time() == 10 || time() == 5 || time() == 4 || time() == 3 || time() == 2 || time() == 1) {
                    ExpressoBukkit.getInstance().getServer().getOnlinePlayers().forEach(player -> player.sendMessage(MessageUtils.getMessageConfig(player).getGame().getCompetitionBeginningIn().replace("%prefix%", MessageUtils.getDefaultMessageConfig().getPrefix()).replace("%time%", TimeUtils.getDurationString(time()))));
                    Bukkit.getOnlinePlayers().forEach(player2 -> player2.playSound(player2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20.0f, 20.0f));
                }
                // Lancer la compétition
                if (time() < 1) {
                    ExpressoBukkit.getInstance().getServer().getOnlinePlayers().forEach(player -> player.sendMessage(MessageUtils.getMessageConfig(player).getGame().getCompetitionStarting().replace("%prefix%", MessageUtils.getDefaultMessageConfig().getPrefix())));
                    Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20.0f, 20.0f));
                    ExpressoBukkit.getBbGame().getBbGameManager().nextPhase();
                    return;
                }
                --time;
            }
        };
    }

    @Override
    public BuildBattleEngine engine() {
        return null;
    }
}
