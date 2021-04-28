package eu.builderscoffee.expresso.commands;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.teams.TeamManager;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamCommand implements CommandExecutor {

    //Configurations
    MessageConfiguration messages = Main.getMessages();
    SettingsConfiguration settings = Main.getSettings();
    // Instances of
    @Getter
    private final BBGame bbGame = Main.getBbGame();

    public static boolean argLength0(Player player) {
        List<String> commandList = new ArrayList<>();
        commandList.add("§a/group §b: Aide du système de group");
        commandList.add("§a/group add <joueur> §b: Ajouter un joueur dans votre group");
        commandList.add("§a/group remove <joueur> §b: Retirer un joueur de votre group");
        commandList.add("§a/group leave §b: Quitter le groupe d'un joueur");
        commandList.add("§a/group disband §b: Supprimer votre groupe");
        commandList.add("§a/group invite <player> accept/deny §b: Accepter ou refuser l'invite d'un joueur");
        for (String s : commandList) {
            player.sendMessage(s);
        }
        return true;
    }

    public boolean argLength1(Player player, String args1) {
        args1 = args1.toLowerCase();
        switch (args1) {
            case "help":
                // Afficher l'aides aux commandes
                argLength0(player);
                break;
            case "leave":
                // Quitter le groupe d'un joueur
                Main.getBbGame().getTeamManager().removePlayerFromTeam(player);
                break;
            case "disband":
                // Supprimer votre groupe si vous êtes leader
                Main.getBbGame().getTeamManager().unregisterTeam(player);
            default:
                return false;
        }
        return true;
    }

    public boolean argLength2(Player player, String args1, String args2) {
        args1 = args1.toLowerCase();
        Player targetLenght2 = Bukkit.getPlayer(args2);
        //System.out.println("TeamCommand : ARGS1: " + args1 + "ARGS2: " + args2 + targetLenght2.getName());
        switch (args1) {
            case "add":
                // Ajouter un joueur aux groupe
                    Main.getBbGame().getTeamManager().SendInvitation(player, targetLenght2.getPlayer());
                    break;
            case "remove":
                // Retirer un joueur aux groupe
                Main.getBbGame().getTeamManager().removePlayerFromTeam(targetLenght2.getPlayer());
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean argLength3(Player player, String args1, String args2, String args3) {
        args1 = args1.toLowerCase();
        switch (args1) {
            case "invite":
                // Gèrer les invitation
                Player target = Bukkit.getPlayerExact(args2);
                switch (args3) {
                    case "accept":
                        // Accepter l'invite du joueur
                        Main.getBbGame().getTeamManager().AcceptInvitation(player,Bukkit.getPlayerExact(args2));
                        break;
                    case "deny":
                        // Refuser l'invite du joueur
                        Main.getBbGame().getTeamManager().DenyInvitation(player,Bukkit.getPlayerExact(args2));
                        break;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean ret = false;
            //if (player.hasPermission(settings.getExpresso_all_permission())) {
                switch (args.length) {
                    case 0:
                        ret = argLength0(player);
                        break;
                    case 1:
                        ret = argLength1(player, args[0]);
                        break;
                    case 2:
                        ret = argLength2(player, args[0], args[1]);
                        break;
                    case 3:
                        ret = argLength3(player, args[0], args[1], args[2]);
                        break;
                    default:
                        //ret = argLength4(player, args);
                        break;
                //}
            }

            if (!ret) {
                player.sendMessage(messages.getGlobal_prefix() + messages.getCommand_bad_syntaxe());
            }

            return ret;
        }

        sender.sendMessage(messages.getGlobal_prefix() + messages.getCommand_must_be_player());
        return true;
    }
}
