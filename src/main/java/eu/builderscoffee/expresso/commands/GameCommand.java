package eu.builderscoffee.expresso.commands;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import eu.builderscoffee.expresso.inventory.game.GameExpressoInventory;
import eu.builderscoffee.expresso.inventory.game.GameTypeInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameCommand implements CommandExecutor {

    MessageConfiguration messages = Main.getMessages();
    SettingsConfiguration settings = Main.getSettings();

    public static boolean argLength0(Player player) {
        List<String> commandList = new ArrayList<>();
        commandList.add("§a/game §b: Aide du plugin Expresso");
        commandList.add("§a/game type §b: Choisir le build battle");
        commandList.add("§a/game start §b: Démarrer le build battle");
        commandList.add("§a/game stop §b: Stopper le build battle");
        commandList.add("§a/game sql <on/off> §b: Activer/Désactiver la sql pour la partie");
        for (String s : commandList) {
            player.sendMessage(s);
        }
        return true;
    }

    public boolean argLength1(Player player, String cmd) {
        cmd = cmd.toLowerCase();
        switch (cmd) {
            case "type":
                if (Objects.nonNull(Main.getBbGame()) && !Main.getBbGame().isReady()) {
                    GameExpressoInventory.INVENTORY.open(player);
                } else {
                    player.sendMessage(messages.getGlobal_prefix() + messages.getGame_cant_edit_type());
                }
                break;
            case "start":
                // Démarrer la game
                Main.getBbGame().setReady(true);
                break;
            case "stop":
                if (Main.getBbGame().isReady()) {
                    Main.getBbGame().getBbGameManager().cancelGame();
                    player.sendMessage(messages.getGlobal_prefix() + messages.getGame_is_to_stop());
                } else {
                    player.sendMessage(messages.getGlobal_prefix() + messages.getGame_not_going_to_start());
                }
                case "sql":

            default:
                return false;
        }
        return true;
    }

    private boolean argLength2(Player player, String cmd, String arg1) {
        cmd = cmd.toLowerCase();
        if (cmd.equals("sql")) {
            if(arg1.equals("on") && !Main.getSettings().getSqlMode()) {
                Main.getSettings().setSqlMode(true);
                player.sendMessage("§a SQL MODE ON");
            } else if(arg1.equals("off") && Main.getSettings().getSqlMode()){
                Main.getSettings().setSqlMode(false);
                player.sendMessage("§c SQL MODE OFF");
            }
        } else {
            throw new IllegalStateException("Unexpected value: " + cmd);
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
                        ret = argLength1(player, args[0]);
                        break;
                    case 2:
                        ret = argLength2(player, args[0], args[1]);
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
