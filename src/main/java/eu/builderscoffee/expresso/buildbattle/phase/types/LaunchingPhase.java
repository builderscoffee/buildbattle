package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.api.utils.Title;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
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
    public BBGameManager.BBState state() {
        return BBGameManager.BBState.LAUNCHING;
    }

    @Override
    public BukkitRunnable runnable() {
        return new BukkitRunnable() {
            @Override
                public void run() {
                    // Checker si la partie est prète à démarrer ?
                    if (!Main.getBbGame().isReady()) {
                        return;
                    }
                    // Lancer le chrono ( Title + Level )
                    for (final Player player : Bukkit.getOnlinePlayers()) {
                        player.setLevel(getStartTime());
                        if (getStartTime() == 30 || getStartTime() == 20 || getStartTime() == 10 || getStartTime() == 5) {
                            new Title("§eDébut dans", "§6" + getStartTime() + " §esecondes", 20, 10, 20).send(player);
                        }
                    }
                    // Décompte du temps dans le chat
                    if (getStartTime() % 10 == 0 || getStartTime() == 10 || getStartTime() == 5 || getStartTime() == 4 ||getStartTime() == 3 || getStartTime() == 2 || getStartTime() == 1) {
                        Bukkit.getServer().broadcastMessage(Main.getMessages().getPrefix() + "§eLa compétition commence dans " + TimeUtils.getDurationString(getStartTime()));
                        for (final Player player2 : Bukkit.getOnlinePlayers()) {
                            player2.playSound(player2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20.0f, 20.0f);
                        }
                    }
                    // Lancer la compétition
                    if (getStartTime() < 1) {
                        Bukkit.getServer().broadcastMessage(Main.getMessages().getPrefix() + "§eLa compétition commence ! Bonne chance !");
                        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20.0f, 20.0f));
                        //this.getGame().startExpresso();
                        Main.getBbGame().getBbGameManager().nextPhase();
                        return;
                    }
                    --startTime;
                }
            };
        }
}
