package eu.builderscoffee.expresso.configuration;

import eu.builderscoffee.api.configuration.annotation.Configuration;
import lombok.Data;

@Data
@Configuration("settings")
public class SettingsConfiguration {

    // Global settings
    String spawnLocation = "plotevent:0:65:0:90.0:0.0";

    String expressoAllPermission = "expresso.*";
    String expressoJuryPermission = "expresso.jury";
    String expressoEplotPermission = "expresso.eplot";

    // Expresso settings
    String buildTheme = "Futuriste";
}
