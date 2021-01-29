package eu.builderscoffee.expresso.commands;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import eu.builderscoffee.expresso.inventory.JuryInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JuryCommand implements CommandExecutor {

    MessageConfiguration messages = Main.getMessages();
    SettingsConfiguration settings = Main.getSettings();

    public static boolean argLength0(Player player) {
        JuryInventory.INVENTORY.open(player);
        return true;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean ret = false;
            if (player.hasPermission(settings.getExpressoJuryPermission()) || player.hasPermission(settings.getExpressoAllPermission())) {
                if (args.length == 0) {
                    ret = argLength0(player);
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
