package eu.builderscoffee.expresso.configuration;

import eu.builderscoffee.api.configuration.annotation.Configuration;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Configuration("settings")
public class SettingsConfiguration {

    // Global settings
    String spawnLocation = "plotevent:0:65:0:90.0:0.0";

    String expressoAllPermission = "expresso.*";
    String expressoJuryPermission = "expresso.jury";
    String expressoEplotPermission = "expresso.eplot";

    // Board settings
    String seasonName = "§6Torréfaction";
    String buildTheme = "§7Les rouages de l'avenir";
    String serverIp = " §6play.builderscoffee.eu";

    // Game settings
    List<String> pluginEndDisable = Arrays.asList("worldedit", "fastasyncworldedit", "fastasyncvoxelsniper", "voxelsniper", "betterbrushes", "gobrush", "schematicbrush", "schematicsbrowser", "arceon", "gopaint");

}
