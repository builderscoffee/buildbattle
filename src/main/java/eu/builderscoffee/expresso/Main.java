package eu.builderscoffee.expresso;

import eu.builderscoffee.api.board.FastBoard;
import eu.builderscoffee.api.utils.Plugins;
import eu.builderscoffee.expresso.board.BBBoard;
import eu.builderscoffee.expresso.commands.ExpressoCommand;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import eu.builderscoffee.expresso.listeners.PlayerListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import static eu.builderscoffee.api.configuration.Configurations.readOrCreateConfiguration;
import static eu.builderscoffee.expresso.board.BBBoard.updateBoard;

public class Main extends JavaPlugin {

    @Getter
    public static Main instance;

    @Getter
    public static MessageConfiguration messages;

    @Getter
    public static SettingsConfiguration settings;

    @Override
    public void onEnable() {
        instance = this;

        // Read or create configurations
        messages = readOrCreateConfiguration(this, MessageConfiguration.class);
        settings = readOrCreateConfiguration(this, SettingsConfiguration.class);

        // Register Listeners
        Plugins.registerListeners(this, new PlayerListener());

        // Register Command Executors
        this.getCommand("expresso").setExecutor(new ExpressoCommand());

        // Update scoreboard
        this.getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : BBBoard.boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);
    }

    @Override
    public void onDisable() {

    }


}
