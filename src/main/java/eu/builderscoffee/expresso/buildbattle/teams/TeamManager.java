package eu.builderscoffee.expresso.buildbattle.teams;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.UUIDHandler;
import com.plotsquared.bukkit.chat.FancyMessage;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.events.TeamCreateEvent;
import eu.builderscoffee.expresso.buildbattle.events.TeamDisbandEvent;
import eu.builderscoffee.expresso.buildbattle.events.TeamJoinEvent;
import eu.builderscoffee.expresso.buildbattle.events.TeamLeaveEvent;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class TeamManager {

    public List<Team> teams;
    public List<Invitation> invitations;
    // Configuration
    public MessageConfiguration messages = Main.getMessages();
    public SettingsConfiguration settings = Main.getSettings();
    // Instance
    private PlotAPI plotAPI = new PlotAPI();

    public TeamManager() {
        // Init les variables
        teams = new ArrayList<>();
        invitations = new ArrayList<>();
    }

    /***
     * Retourne la team du joueur
     * @param player
     * @return
     */
    public Team getPlayerTeam(Player player) {
        return teams.stream()
                .filter(team -> team.getMembers().contains(player))
                .findFirst().orElse(null);
    }

    /***
     * Retourne si la team à atteint sont maximun de membres
     * @param player
     * @return
     */
    public boolean IsMembersReachLimit(Player player) {
        Team team = getPlayerTeam(player);
        return team.members.size() <= team.maxPlayers;
    }

    /***
     * Retourne si la target à la même team que le player
     * @param player
     * @param target
     * @return
     */
    public boolean IsSameTeam(Player player, Player target) {
        Team team = getPlayerTeam(player);
        return team.members.contains(target);
    }

    /***
     * Retourne si player est le leader de la team
     * @param player
     * @return
     */
    public boolean IsTeamLeader(Player player) {
        Team team = getPlayerTeam(player);
        return team.getLeader().equals(player);
    }

    /***
     * Retourne si le joueur à une team
     * @param player
     * @return
     */
    public boolean AsNoTeam(Player player) {
        return teams.stream().flatMap(team -> team.getMembers().stream()).anyMatch(member -> member.getName().equals(player.getName()));
    }

    /***
     * Voir la team du sender
     * @param player
     */
    public void viewTeam(Player player) {
        viewTargetTeam(player, player);
    }

    /***
     * Voir la team de la target
     * @param player
     */
    public void viewTargetTeam(Player player, Player target) {
        Team team = getPlayerTeam(target);
        if (team != null) {
            player.sendMessage(messages.getTeam_info_header());
            player.sendMessage(messages.getTeam_info_leader() + team.leader.getName());
            StringJoiner joiner = new StringJoiner(", ");
            for (Player m : team.getMembers()) {
                if (team.getLeader() != null || !m.getName().equals(team.getLeader().getName())) {
                    String displayName = m.getName();
                    joiner.add(displayName);
                }
            }
            player.sendMessage(messages.getTeam_info_members() + joiner.toString());
        } else if(!Objects.deepEquals(player,target)) {
            player.sendMessage(messages.getTeam_no_team());
        } else {
            player.sendMessage(messages.getTeam_info_no_team().replace("%target%",target.getName()));
        }
    }

    /***
     * Ajouter un joueur à une team
     * @param player
     * @param target
     */
    public void addPlayerToTeam(Player player, Player target) {
        Team team = getPlayerTeam(player);
        if (!AsNoTeam(target)) {
            team.members.add(target);
            TeamJoinEvent joinEvent = new TeamJoinEvent(target, team.members); // Fire TeamJoin Event
            Bukkit.getPluginManager().callEvent(joinEvent); // Call event
        } else if (IsSameTeam(player, target)) {
            player.sendMessage(messages.getTeam_already_in_a_team().replace("%target%", target.getName()));
        } else if (IsMembersReachLimit(player)) {
            player.sendMessage(messages.getTeam_limit_reached().replace("%limit%", String.valueOf(settings.getTeam_maxplayer())));
        }
    }

    /***
     * Retirer un joueur d'une team
     * @param player - Le joueur à retirer
     */
    public void removePlayerFromTeam(Player player) {
        if (getPlayerTeam(player) != null) {
            if (!IsTeamLeader(player)) {
                Team team = getPlayerTeam(player);
                team.members.remove(player);
                TeamLeaveEvent leaveEvent = new TeamLeaveEvent(player, team.members); // Fire TeamLeave Event
                Bukkit.getPluginManager().callEvent(leaveEvent);
            } else {
                player.sendMessage(messages.getTeam_leader_cannot_leave());
            }
        }
    }

    /***
     * Enregistrer une team
     * @param player - Membre du groupe
     */
    public void registerTeam(Player player) {
        if (!AsNoTeam(player)) {
            Team _team = new Team(player.getName(), player.getName(), settings.getTeam_maxplayer(), player , new ArrayList<>());
            _team.members.add(player); // Ajouter le leader à la liste des membres
            teams.add(_team);
            player.sendMessage(messages.getTeam_create_team());
            TeamCreateEvent createEvent = new TeamCreateEvent(player); // Fire TeamCreate Event
            Bukkit.getPluginManager().callEvent(createEvent); // Call event
        } else {
            player.sendMessage(messages.getTeam_already_created()); }
    }

    /***
     * Supprimer une team
     * @param player - Leader du groupe
     */
    public void unregisterTeam(Player player) {
        if(getPlayerTeam(player) != null && IsTeamLeader(player)) {
            Team team = getPlayerTeam(player);
            TeamDisbandEvent disbandEvent = new TeamDisbandEvent(player,team.getMembers()); // Fire TeamDisband Event
            Bukkit.getPluginManager().callEvent(disbandEvent); // Call event
            teams.remove(team);
            player.sendMessage(messages.getTeam_disband());
        }
    }

    // Invitation Part

    /***
     * Envoyer une invitation à un joueur
     * @param player
     * @param target
     */
    public void SendInvitation(Player player, Player target) {
        // Check si le sender et la target ne sont pas les mêmes joueurs
        if(!Objects.deepEquals(player,target)) {
            Invitation invitation = new Invitation(player, target);
            // Check si l'invitation à déja été créer
            invitations.add(invitation);
            player.sendMessage(messages.getInvitation_send().replace("%target%", target.getName()));
            new FancyMessage(messages.getInvitation_receive_target().replace("%sender%", player.getName())).then(messages.getInvitation_receive_acceptance()).command("/group invite " + player.getName() + " accept").then(" ou ").then(messages.getInvitation_receive_denyance()).command("/group invite " + player.getName() + " deny").send(target);
        } else {player.sendMessage(messages.getInvitation_not_invite_yourself()); }
    }

    /***
     * Accepter l'invitation d'un joueur
     * @param receiver - Joueur qui reçois l'invitation
     * @param sender - Joueur qui envois l'invitation
     */
    public void AcceptInvitation(Player receiver, Player sender) {
        Invitation invitation = getInvitation(sender, receiver);
        if (invitation.getTarget() != null) {
            if(!AsNoTeam(sender)) {
                registerTeam(sender);
            }
            addPlayerToTeam(sender, receiver);
            invitations.remove(invitation); // Clean l'invitation accepter
        }
    }

    /***
     * Refuser l'invitation d'un joueur
     * @param receiver - Joueur qui reçois l'invitation
     * @param sender - Joueur qui envois l'invitation
     */
    public void DenyInvitation(Player receiver, Player sender) {
        Invitation invitation = getInvitation(sender, receiver);
        if (invitation != null) {
            if (invitation.getTarget() != null)
                receiver.sendMessage(messages.getInvitation_deny_target().replace("%sender%", sender.getName()));
            sender.sendMessage(messages.getInvitation_deny_sender().replace("%target%", receiver.getName()));
            invitations.remove(invitation); // Clean l'invitation accepter
        } else {
            receiver.sendMessage(messages.getInvitation_not_avaliable().replace("%sender%", sender.getName()));
        }
    }

    /***
     * Retourne l'invitation envoyer à un joueur
     * @param sender
     * @param receiver
     * @return
     */
    public Invitation getInvitation(Player sender, Player receiver) {
        return invitations.stream()
                .filter(invitation -> invitation.getSender() == sender && invitation.getTarget() == receiver)
                .findFirst()
                .get();
    }

    /***
     * Nettoyer la liste des invitations en cours
     */
    public void CleanInvitations() {
        invitations.clear();
    }


    // Plot part

    /***
     * Ajouté tout les membres du group aux plot du leader
     * @param team - L'object Team
     */
    public void addAllMembersToPlot(Team team,Plot plot) {
        team.getMembers().forEach(member -> {
            if(member != team.getLeader()) {
                plot.addMember(UUIDHandler.getPlayer(member.getName()).getUUID());
                plot.addTrusted(UUIDHandler.getPlayer(member.getName()).getUUID());
            }
        });
    }

    /***
     * Ajouter un joueur aux plots de la team
     * @param player - Joueur à ajouter
     */
    public void addMemberToAllPlot(Player player) {
        Team team = Main.getBbGame().getTeamManager().getPlayerTeam(player);
        Set<Plot> plots = new PlotAPI().getPlayerPlots(team.getLeader());
        plots.forEach(plot -> {
            plot.addMember(UUIDHandler.getPlayer(player.getName()).getUUID());
            plot.addTrusted(UUIDHandler.getPlayer(player.getName()).getUUID());
        });
    }

    /***
     * Retiré un joueur de tout les plots de la team
     * @param player - Joueur à retirer
     * @param team - Team du leader
     */
    public void removeMemberFromAllPlot(Player player) {
        Team team = Main.getBbGame().getTeamManager().getPlayerTeam(player);
        Set<Plot> plots = new PlotAPI().getPlayerPlots(team.getLeader());
        plots.forEach(plot -> {
            plot.removeMember(UUIDHandler.getPlayer(player.getName()).getUUID());
            plot.removeTrusted(UUIDHandler.getPlayer(player.getName()).getUUID());
        });
    }

}
