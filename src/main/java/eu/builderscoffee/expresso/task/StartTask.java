package eu.builderscoffee.expresso.task;

import eu.builderscoffee.api.utils.Title;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Data
@AllArgsConstructor
public class StartTask extends BukkitRunnable {

    private BBGame game;
    private int time;

    @Override
    @SuppressWarnings("deprecation")
    public void run() {
        if (!this.getGame().isReady()) {
            return;
        }
        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.setLevel(this.time);
            switch (time) {
                case 30:
                case 20:
                case 10:
                case 5:
                    new Title("§eDébut dans", "§6" + this.time + " §esecondes", 20, 10, 20).send(player);
                    break;
            }
        }
        if (this.time < 1) {
            Bukkit.getServer().broadcastMessage(Main.getMessages().getPrefix() + "§eLa compétition commence ! Bonne chance !");
            Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20.0f, 20.0f));
            this.getGame().startExpresso();
            return;
        }
        String message = null;
        if (this.time > 60) {
            if (this.time % 60 == 0) {
                message = "§b" + this.time / 60 + " §emins.";
            }
        } else {
            final String plur = (this.time > 1) ? "s" : "";
            if (this.time % 10 == 0 || this.time == 10 || this.time == 5 || this.time == 3 || this.time == 2 || this.time == 1) {
                message = "§b" + this.time + " §esec" + plur + "";
            }
        }
        if (message != null) {
            Bukkit.getServer().broadcastMessage(Main.getMessages().getPrefix() + "§eLa compétition commence dans " + message);
            for (final Player player2 : Bukkit.getOnlinePlayers()) {
                player2.playSound(player2.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20.0f, 20.0f);
            }
        }
        --this.time;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(final int time) {
        this.time = time;
    }


}
