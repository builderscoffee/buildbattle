package eu.builderscoffee.expresso.buildbattle.teams.events;


import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.events.TeamCreateEvent;
import eu.builderscoffee.expresso.buildbattle.events.TeamDisbandEvent;
import eu.builderscoffee.expresso.buildbattle.events.TeamJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.TeamLeaveEvent;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;


public class TeamListeners implements Listener {

    MessageConfiguration messages = Main.getMessages();


    @EventHandler
    public void onTeamCreateEvent(TeamCreateEvent event) {
    }


    @EventHandler
    public void onTeamDisbandEvent(TeamDisbandEvent event) {
    }


    @EventHandler
    public void onTeamJoinEvent(TeamJoinEvent event) {
        List<Player> members = event.getMemberList();
        // Envoyer un message quand le joueur rejoins
        members.forEach(member -> member.sendMessage(messages.getTeam_player_join().replace("%target%",event.getPlayer().getName())));
        // Ajouter le joueur aux plot du leader du groupe
        Main.getBbGame().getTeamManager().addMemberToAllPlot(event.getPlayer());
    }


    @EventHandler
    public void onTeamLeaveEvent(TeamLeaveEvent event) {
        List<Player> members = event.getMemberList();
        //Envoyer un message quand le joueur quitte
        members.forEach(member -> member.sendMessage(messages.getTeam_player_quit()));
        // Retirer le joueur aux plot du leader du groupe
        Main.getBbGame().getTeamManager().removeMemberFromAllPlot(event.getPlayer());
    }

}