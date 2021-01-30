package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.api.utils.Title;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LaunchingPhase implements BBPhase {

    @Getter
    @Setter
    private BBGame game;

    @Getter
    @Setter
    private int startTime;

    public LaunchingPhase(int startTime) { ;
        setGame(Main.getBbGame());
        setStartTime(startTime);
    }

    @Override
    public void run() {
        // Checker si la partie est prète à démarrer ?
        if (!this.getGame().isReady()) {
            return;
        }
        // Lancer le chrono ( Title + Level )
        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.setLevel(getStartTime());
            if (getStartTime() == 30 || getStartTime() == 20 || getStartTime() == 10 || getStartTime() == 5) {
                new Title("§eDébut dans", "§6" + getStartTime() + " §esecondes", 20, 10, 20).send(player);
            }
        }
        // Lancer la compétition
        if (getStartTime() < 1) {
            Bukkit.getServer().broadcastMessage(Main.getMessages().getPrefix() + "§eLa compétition commence ! Bonne chance !");
            Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20.0f, 20.0f));
            this.getGame().startExpresso();
            return;
        }

        String message = null;
        if (getStartTime() > 60) {
            if (getStartTime() % 60 == 0) {
                message = "§b" + getStartTime() / 60 + " §emins.";
            }
        } else {
            final String plur = (getStartTime() > 1) ? "s" : "";
            if (getStartTime() % 10 == 0 || getStartTime() == 10 || getStartTime() == 5 || getStartTime() == 3 || getStartTime() == 2 || getStartTime() == 1) {
                message = "§b" + getStartTime() + " §esec" + plur + "";
            }
        }
        if (message != null) {
            Bukkit.getServer().broadcastMessage(Main.getMessages().getPrefix() + "§eLa compétition commence dans " + message);
            for (final Player player2 : Bukkit.getOnlinePlayers()) {
                player2.playSound(player2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20.0f, 20.0f);
            }
        }
        --startTime;
    }
}
