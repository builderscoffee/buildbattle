package eu.builderscoffee.expresso.buildbattle.phase.types;

import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class JuryPhase extends BukkitRunnable implements BBPhase {

    @Override
    public void run() {
        // Kick tous les joueurs
        Bukkit.getOnlinePlayers().forEach(s -> s.kickPlayer("Les plots sont en cours de notation"));

        // Permettre la notation des plots
        // TODO some stuff here

    }

    @Override
    public String name() {
        return "Jury";
    }

    @Override
    public String description() {
        return "Notation des plots";
    }

    @Override
    public int time() {
        return -1;
    }
}
