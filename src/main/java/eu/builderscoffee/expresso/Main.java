package eu.builderscoffee.expresso;


import eu.builderscoffee.api.bukkit.gui.InventoryManager;
import eu.builderscoffee.api.bukkit.utils.Plugins;
import eu.builderscoffee.api.common.events.EventHandler;
import eu.builderscoffee.api.common.redisson.Redis;
import eu.builderscoffee.api.common.redisson.infos.Server;
import eu.builderscoffee.commons.common.redisson.topics.CommonTopics;
import eu.builderscoffee.expresso.board.BBBoard;
import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import eu.builderscoffee.expresso.buildbattle.events.ConfigListener;
import eu.builderscoffee.expresso.buildbattle.expressos.types.IlClassicoExpresso;
import eu.builderscoffee.expresso.commands.GameCommand;
import eu.builderscoffee.expresso.commands.JuryCommand;
import eu.builderscoffee.expresso.commands.PlotCommand;
import eu.builderscoffee.expresso.commands.TeamCommand;
import eu.builderscoffee.expresso.configuration.CacheConfiguration;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import eu.builderscoffee.expresso.listeners.CompetitorListener;
import eu.builderscoffee.expresso.listeners.PlayerListener;
import eu.builderscoffee.expresso.listeners.PlotListener;
import eu.builderscoffee.expresso.listeners.TeamListeners;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RSortedSet;

import static eu.builderscoffee.api.common.configuration.Configuration.readOrCreateConfiguration;

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
    @Getter @Setter
    private static BuildBattle bbGame;

    @Override
    public void onEnable() {
        instance = this;

        // Read or create configurations
        messages = readOrCreateConfiguration(this.getName(), MessageConfiguration.class);
        settings = readOrCreateConfiguration(this.getName(), SettingsConfiguration.class);
        cache = readOrCreateConfiguration(this.getName(), CacheConfiguration.class);

        // Register Bukkit Listeners
        Plugins.registerListeners(this, new PlayerListener(), new CompetitorListener(), new TeamListeners(), new PlotListener());

        // Register Redis Listeners
        Redis.subscribe(CommonTopics.SERVER_MANAGER,new ConfigListener());

        // Register BuildCoffee EventListeners
        EventHandler.getInstance().addListener(new HeartBeatListener());

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

        //RSortedSet<Server> servers = Redis.getRedissonClient().getSortedSet("servers");

    }

    @Override
    public void onDisable() {

    }

}
