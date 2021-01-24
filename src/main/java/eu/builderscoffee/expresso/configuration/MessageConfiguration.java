package eu.builderscoffee.expresso.configuration;

import eu.builderscoffee.api.configuration.annotation.Configuration;
import lombok.Data;

@Data
@Configuration("messages")
public final class MessageConfiguration {

    // Global
    String prefix = "&7[&aExpresso&7]&f";

    // Command
    String commandMustBePlayer = "Vous devez être un joueur";
    String commandBadSyntaxe = "bad syntaxe";

    // Expresso
    String competitorJoin = "%player% a rejoins la compétition";
    String competitorLeave = "%player% a quitté la compétition";
    String compassName = "BuildBattle";
}
