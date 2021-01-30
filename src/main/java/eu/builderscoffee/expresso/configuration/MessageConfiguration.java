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

    // Expresso
    String competitorJoin = "§f%player% §aa rejoins la compétition";
    String competitorLeave = "§f%player% §ca quitté la compétition";
}
