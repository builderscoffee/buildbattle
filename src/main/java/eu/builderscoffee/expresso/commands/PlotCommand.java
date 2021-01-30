package eu.builderscoffee.expresso.commands;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MainUtil;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import eu.builderscoffee.expresso.utils.PlotUtils;
import lombok.val;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlotCommand implements CommandExecutor {

    MessageConfiguration messages = Main.getMessages();
    SettingsConfiguration settings = Main.getSettings();

    public static boolean argLength0(Player player) {
        List<String> commandList = new ArrayList<>();
        commandList.add("§a/eplot §b: Aide du plugin Expresso");
        commandList.add("§a/eplot info§b: Voir les info du plot");
        for (String s : commandList) {
            player.sendMessage(s);
        }
        return true;
    }

    public static boolean argLength1(Player player, String cmd) {
        cmd = cmd.toLowerCase();
        switch (cmd) {
            case "info":
                // Informations sur le plot
                if (new PlotAPI().isInPlot(player)) {
                val plot = MainUtil.getPlotFromString(PlotPlayer.get(player.getName()), null, false);
                    String name = MainUtil.getName(plot.owner);
                    player.sendMessage("§0§7§m--- §fPlot §0§7§m---");
                    player.sendMessage("§aId : §7" + PlotUtils.getPlotsPos(plot));
                    player.sendMessage("§aOwner : §7" + name);
                    player.sendMessage("§0§7§m------");
                } else {
                    player.sendMessage("§cTu n'est pas sur un plot, espèce de café moulu");
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
            if (player.hasPermission(settings.getExpressoEplotPermission()) || player.hasPermission(settings.getExpressoAllPermission())) {
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
