package eu.builderscoffee.expresso;


import eu.builderscoffee.api.bukkit.gui.InventoryManager;
import eu.builderscoffee.api.bukkit.utils.Plugins;
import eu.builderscoffee.expresso.board.BBBoard;
import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import eu.builderscoffee.expresso.buildbattle.expressos.types.IlClassicoExpresso;
import eu.builderscoffee.expresso.listeners.PlotListener;
import eu.builderscoffee.expresso.listeners.TeamListeners;
import eu.builderscoffee.expresso.commands.GameCommand;
import eu.builderscoffee.expresso.commands.JuryCommand;
import eu.builderscoffee.expresso.commands.PlotCommand;
import eu.builderscoffee.expresso.commands.TeamCommand;
import eu.builderscoffee.expresso.configuration.CacheConfiguration;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import eu.builderscoffee.expresso.listeners.CompetitorListener;
import eu.builderscoffee.expresso.listeners.PlayerListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import static eu.builderscoffee.api.bukkit.configuration.Configurations.readOrCreateConfiguration;

public class Main extends JavaPlugin {

    @Getter
    public static MessageConfiguration messages;
    @Getter
    public static SettingsConfiguration settings;
    @Getter
    public static CacheConfiguration cache;
    @Getter
    public static InventoryManager inventoryManager;
    @Getter
    private static Main instance;
    @Getter
    private static BuildBattle bbGame;

    @Override
    public void onEnable() {
        instance = this;

        // Read or create configurations
        messages = readOrCreateConfiguration(this, MessageConfiguration.class);
        settings = readOrCreateConfiguration(this, SettingsConfiguration.class);
        cache = readOrCreateConfiguration(this, CacheConfiguration.class);

        // Register Listeners
        Plugins.registerListeners(this, new PlayerListener(), new CompetitorListener(), new TeamListeners(), new PlotListener());

        // Register Command Executors
        this.getCommand("game").setExecutor(new GameCommand());
        this.getCommand("jury").setExecutor(new JuryCommand());
        this.getCommand("group").setExecutor(new TeamCommand());
        this.getCommand("eplot").setExecutor(new PlotCommand());

        // Init invt
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        // Update scoreboard
        this.getServer().getScheduler().runTaskTimer(this, () -> {
            BBBoard.boards.values().forEach(BBBoard::updateBoard);
        }, 0, 20);

        // Set game type
        bbGame = new BuildBattle(this, new IlClassicoExpresso());

        // Check Start
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (getBbGame() != null) {
                getBbGame().getBbGameManager().checkStart();
            }
        }, 0L, 20L);


    }

    @Override
    public void onDisable() {

    }

}
