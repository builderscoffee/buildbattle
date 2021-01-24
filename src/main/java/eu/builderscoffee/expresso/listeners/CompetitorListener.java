package eu.builderscoffee.expresso.listeners;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.CompetitorLeaveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CompetitorListener implements Listener {

    @EventHandler
    public void onCompetitorJoin(CompetitorJoinEvent event) {
        final Player player = event.getCompetitor();
        Main.getBbGame().getCompetitor().forEach(competitor -> competitor.sendMessage(Main.getMessages().getCompetitorJoin().replace("%player%", player.getName()).replace("&", "§")));
    }

    @EventHandler
    public void onCompetitorLeave(CompetitorLeaveEvent event) {
        final Player player = event.getCompetitor();
        Main.getBbGame().getCompetitor().forEach(competitor -> competitor.sendMessage(Main.getMessages().getCompetitorLeave().replace("%player%", player.getName()).replace("&", "§")));
    }
}
