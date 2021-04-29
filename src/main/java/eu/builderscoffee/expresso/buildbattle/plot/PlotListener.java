package eu.builderscoffee.expresso.buildbattle.plot;

import com.plotsquared.bukkit.events.PlayerClaimPlotEvent;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.teams.Team;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlotListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerClaim(PlayerClaimPlotEvent event) {
        Team team = Main.getBbGame().getTeamManager().getPlayerTeam(event.getPlayer());
        Main.getBbGame().getTeamManager().addAllMembersToPlot(team,event.getPlot());
    }
}
