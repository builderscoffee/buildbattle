package eu.builderscoffee.expresso.commands;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameCommand implements CommandExecutor {

    MessageConfiguration messages = Main.getMessages();
    SettingsConfiguration settings = Main.getSettings();

    public static boolean argLength0(Player player) {
        List<String> commandList = new ArrayList<>();
        commandList.add("§a/game §b: Aide du plugin Expresso");
        commandList.add("§a/game start §b: Démarrer le build battle");
        commandList.add("§a/game stop §b: Stopper le build battle");
        commandList.add("§a/game cancel §b: Cancel le build battle");
        for (String s : commandList) {
            player.sendMessage(s);
        }
        return true;
    }

    public static boolean argLength1(Player player, String cmd) {
        cmd = cmd.toLowerCase();
        switch (cmd) {
            case "start":
                // Démarrer la game
                Main.getBbGame().setReady(true);
                break;
            case "stop":
                Main.getBbGame().bbGameManager.endGame();
            case "cancel":
                Main.getBbGame().bbGameManager.cancelLaunchCountdown("");
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
            if (player.hasPermission(settings.getExpressoAllPermission())) {
                switch (args.length) {
                    case 0:
                        ret = argLength0(player);
                        break;
                    case 1:
                        ret = argLength1(player, args[0]);
                        break;
                    case 2:
                        //ret = argLength2(player, args[0], args[1]);
                        break;
                    case 3:
                        //ret = argLength3(player, args[0], args[1], args[2]);
                        break;
                    default:
                        //ret = argLength4(player, args);
                        break;
                }
            }

            if (!ret) {
                player.sendMessage(messages.getPrefix() + messages.getCommandBadSyntaxe());
            }

            return ret;
        }

        sender.sendMessage(messages.getPrefix() + messages.getCommandMustBePlayer());
        return true;
    }
}
