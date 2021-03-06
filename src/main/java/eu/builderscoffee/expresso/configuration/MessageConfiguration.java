package eu.builderscoffee.expresso.configuration;

import eu.builderscoffee.api.configuration.annotation.Configuration;
import lombok.Data;

@Data
@Configuration("messages")
public final class MessageConfiguration {

    // Global
    String global_prefix = "§6§lBuilders Coffee §8>> ";

    // Command
    String command_must_be_player = "§cVous devez être un joueur pour executer cette commande";
    String command_bad_syntaxe = "§cMauvaise syntaxe de la commande";

    // Game Command
    String game_cant_edit_type = "§cVous ne pouvez changer le type d'une partie en cours";
    String game_is_to_stop = "§cVous venez de stopper la partie";
    String game_not_going_to_start = "§cVous ne pouvez pas stopper une partie qui n'est pas démarrée";

    // Expresso
    String expresso_competitor_join = "§f%player% §aa rejoins la compétition";
    String expresoo_competitor_leave = "§f%player% §ca quitté la compétition";

    // Game

}
