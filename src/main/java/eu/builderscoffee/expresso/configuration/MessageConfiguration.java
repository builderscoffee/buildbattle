package eu.builderscoffee.expresso.configuration;

import eu.builderscoffee.api.configuration.annotation.Configuration;
import lombok.Data;

@Data
@Configuration("messages")
public final class MessageConfiguration {

    //Global
    String prefix = "&7[&aExpresso&7]&f";

    // Command
    String commandMustBePlayer = "Vous devez être un joueur";
    String commandBadSyntaxe = "bad syntaxe";
}
