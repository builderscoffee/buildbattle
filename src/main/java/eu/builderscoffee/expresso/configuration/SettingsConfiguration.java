package eu.builderscoffee.expresso.configuration;


import eu.builderscoffee.api.common.configuration.annotation.Configuration;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Configuration("settings")
public class SettingsConfiguration {

    // Global settings
    String global_spawn_location = "plotevent:0:65:0:90.0:0.0";
    String pluginMode = "DEV";

    // Permission settings
    String expresso_all_permission = "expresso.*";
    String expresso_jury_permission = "expresso.jury";
    String expresso_eplot_permission = "expresso.eplot";

    // Board settings
    String board_title = "§6§l- Builders Coffee -";
    String board_season_name = "§6Torréfaction";
    String board_build_theme = "§7Les rouages de l'avenir";
    String board_server_ip = " §6play.builderscoffee.eu";

    // Game settings
    List<String> game_plugin_end_disable = Arrays.asList("worldedit", "fastasyncworldedit", "fastasyncvoxelsniper", "voxelsniper", "betterbrushes", "gobrush", "schematicbrush", "schematicsbrowser", "arceon", "gopaint");
    String game_schematic_to_paste = "cobble";

}
