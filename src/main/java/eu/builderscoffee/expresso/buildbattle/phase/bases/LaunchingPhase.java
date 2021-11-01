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

public class LaunchingPhase implements BBPhase {

    @Getter
    @Setter
    private int startTime;

    public LaunchingPhase(int startTime) {
        setStartTime(startTime);
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
    public int time() {
        return startTime;
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
                    player.setLevel(getStartTime());
                    if (getStartTime() == 30 || getStartTime() == 20 || getStartTime() == 10 || getStartTime() == 5)
                        new Title(MessageUtils.getMessageConfig(player).getGame().getStartInTitle(), MessageUtils.getMessageConfig(player).getGame().getStartInSubTitle(), 20, 10, 20).send(player);
                }
                // Décompte du temps dans le chat
                if (getStartTime() % 10 == 0 || getStartTime() == 10 || getStartTime() == 5 || getStartTime() == 4 || getStartTime() == 3 || getStartTime() == 2 || getStartTime() == 1) {
                    ExpressoBukkit.getInstance().getServer().getOnlinePlayers().forEach(player -> player.sendMessage(MessageUtils.getMessageConfig(player).getGame().getCompetitionBeginningIn().replace("%prefix%",MessageUtils.getDefaultMessageConfig().getPrefix()).replace("%time%",TimeUtils.getDurationString(getStartTime()))));
                    Bukkit.getOnlinePlayers().forEach(player2 -> player2.playSound(player2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20.0f, 20.0f));
                }
                // Lancer la compétition
                if (getStartTime() < 1) {
                    ExpressoBukkit.getInstance().getServer().getOnlinePlayers().forEach(player -> player.sendMessage(MessageUtils.getMessageConfig(player).getGame().getCompetitionStarting().replace("%prefix%",MessageUtils.getDefaultMessageConfig().getPrefix())));
                    Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20.0f, 20.0f));
                    ExpressoBukkit.getBbGame().getBbGameManager().nextPhase();
                    return;
                }
                --startTime;
            }
        };
    }

    @Override
    public BuildBattleEngine engine() {
        return null;
    }
}
