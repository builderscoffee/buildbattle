package eu.builderscoffee.expresso.listeners;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.competitor.CompetitorJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.competitor.CompetitorLeaveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CompetitorListener implements Listener {

    @EventHandler
    public void onCompetitorJoin(CompetitorJoinEvent event) {
        final Player player = event.getCompetitor();
        Main.getBbGame().getCompetitor().forEach(competitor -> competitor.sendMessage(Main.getMessages().getExpresso_competitor_join().replace("%player%", player.getName()).replace("&", "§")));
    }

    @EventHandler
    public void onCompetitorLeave(CompetitorLeaveEvent event) {
        final Player player = event.getCompetitor();
        Main.getBbGame().getCompetitor().forEach(competitor -> competitor.sendMessage(Main.getMessages().getExpresoo_competitor_leave().replace("%player%", player.getName()).replace("&", "§")));
    }
}
