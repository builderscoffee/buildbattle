package eu.builderscoffee.expresso.configuration;

import eu.builderscoffee.api.configuration.annotation.Configuration;
import lombok.Data;

@Data
@Configuration("messages")
public final class MessageConfiguration {

    // Global
    String prefix = "§6§lBuilders Coffee §8>> ";

    // Command
    String commandMustBePlayer = "§cVous devez être un joueur pour executer cette commande";
    String commandBadSyntaxe = "§cMauvaise syntaxe de la commande";

    // Game Command
    String cantedittype = "§cVous ne pouvez changer le type d'une partie en cours";
    String game_is_to_stop = "§cVous venez de stopper la partie";
    String game_not_going_to_start = "§cVous ne pouvez pas stopper une partie qui n'est pas démarrée";

    // Expresso
    String competitorJoin = "§f%player% §aa rejoins la compétition";
    String competitorLeave = "§f%player% §ca quitté la compétition";

    // Game
}
