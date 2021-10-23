package eu.builderscoffee.expresso.commands;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MainUtil;
import com.intellectualcrafters.plot.util.UUIDHandler;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.notation.Notation;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import eu.builderscoffee.expresso.inventory.jury.JuryNotationInventory;
import eu.builderscoffee.expresso.inventory.jury.JuryTeleportation;
import eu.builderscoffee.expresso.utils.PlotUtils;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlotCommand implements CommandExecutor {

    MessageConfiguration messages = Main.getMessages();
    static SettingsConfiguration settings = Main.getSettings();

    public static boolean argLength0(Player player) {
        List<String> commandList = new ArrayList<>();
        commandList.add("§a/eplot §b: Aide du plugin Expresso");
        commandList.add("§a/eplot info§b: Voir les infoormations du plot");
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
                    if (new PlotAPI().getPlot(player.getLocation()).canClaim(UUIDHandler.getPlayer(player.getUniqueId()))) {
                        val plot = MainUtil.getPlotFromString(PlotPlayer.get(player.getName()), null, false);
                        String name = MainUtil.getName(plot.owner);
                        List<String> membersList = new ArrayList<>();
                        plot.getMembers().forEach(uuid -> membersList.add(UUIDHandler.getName(uuid)));
                        player.sendMessage("§0§7§m--- §fPlot §0§7§m---");
                        player.sendMessage("§aId: §7" + PlotUtils.getPlotsPos(plot));
                        player.sendMessage("§aOwner : §7" + name);
                        player.sendMessage("§aMembers : §7" + membersList.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(" ,")));
                        player.sendMessage("§0§7§m------");
                    } else {
                        player.sendMessage("§cCe plot n'est pas claim");
                    }
                } else {
                    player.sendMessage("§cTu n'est pas sur un plot, espèce de café moulu");
                }
                break;
            case "paste":
                /*
                Location loc = PlotUtils.convertBukkitLoc(player.getTargetBlock(null, 100).getLocation());
                final Plot plot = loc.getPlotAbs();
                PlotUtils.pasteSchematic(Main.getSettings().getSchematicToPaste(), plot);
                player.sendMessage("§a Paste Plot Test");
                */
                break;
            case "invleo":
                //checker
                if (new PlotAPI().isInPlot(player)) {
                    Plot plotinv = (PlotUtils.convertBukkitLoc(player.getLocation()).getPlotAbs());
                    if (!Main.getBbGame().getNotationManager().playerHasNote(plotinv, player)) {
                        JuryNotationInventory.INVENTORY.open(player);
                    } else {
                        player.sendMessage("§cTu as déjà noté ce plot, espèce de café moulu");
                    }
                } else {
                    player.sendMessage("§cTu n'est pas sur un plot, espèce de café moulu");
                }
                break;

            case "seenote":
                Plot plote = (PlotUtils.convertBukkitLoc(player.getLocation()).getPlotAbs());
                Set<Notation> a = Main.getBbGame().getNotationManager().getNotationsByPlot(plote);
                if (a == null || a.isEmpty()) {
                    player.sendMessage("Ce plot a 0 notation");
                    break;
                }
                player.sendMessage("Ce plot a " + a.size() + "notation(s)");
                for (Notation note : a) {
                   // player.sendMessage("Juge: " + Bukkit.getOfflinePlayer(note.getUUID()).getName() + " Fun: " + note.getFun());
                }
                break;

            case "invlist":
                JuryTeleportation.INVENTORY.open(player);
                break;

            case "tpnext":
                // Get plot
                Plot plotinvtp = (PlotUtils.convertBukkitLoc(player.getLocation()).getPlotAbs());
                int b = PlotUtils.getPlotsPos(plotinvtp) + 1;
                Plot current = PlotUtils.getPlotsByPos(b);

                // tp player
                PlotUtils.convertPlotCenterLoc(current.getCenter());
                player.teleport(PlotUtils.convertPlotCenterLoc(current.getCenter()));
                break;
            case "schem":
                new PlotUtils().exportAllSchematics(settings.getPath_for_backup(), () -> {
                    System.out.println("Tout les plots on été schématisés");
                });
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
            if (player.hasPermission(settings.getExpresso_eplot_permission()) || player.hasPermission(settings.getExpresso_all_permission())) {
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
                player.sendMessage(messages.getGlobal_prefix() + messages.getCommand_bad_syntaxe());
            }

            return ret;
        }

        sender.sendMessage(messages.getGlobal_prefix() + messages.getCommand_must_be_player());
        return true;
    }
}
