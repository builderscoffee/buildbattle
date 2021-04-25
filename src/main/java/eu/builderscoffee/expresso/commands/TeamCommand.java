package eu.builderscoffee.expresso.commands;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamCommand implements CommandExecutor {
    MessageConfiguration messages = Main.getMessages();
    SettingsConfiguration settings = Main.getSettings();

    public static boolean argLength0(Player player) {
        List<String> commandList = new ArrayList<>();
        commandList.add("§a/groupe §b: Aide du système de groupe");
        commandList.add("§a/groupe add <joueur> §b: Ajouter un joueur dans votre groupe");
        commandList.add("§a/groupe remove <joueur> §b: Retirer un joeur de votre groupe");
        commandList.add("§a/groupe invite <player> accept/deny §b: Accepter ou refuser l'invite d'un joueur");
        for (String s : commandList) {
            player.sendMessage(s);
        }
        return true;
    }

    public boolean argLength2(Player player, String args1, String args2) {
        args1 = args1.toLowerCase();
        switch (args1) {
            case "add":
                // Ajouter un joueur dans le groupe

            case "remove":
                // Retirer un joueur du groupe
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
                        break;
                    case "deny":
                        // Refuser l'invite du joueur
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
            if (player.hasPermission(settings.getExpresso_all_permission())) {
                switch (args.length) {
                    case 0:
                        ret = argLength0(player);
                        break;
                    case 1:
                        //ret = argLength1(player, args[0]);
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
                }
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
