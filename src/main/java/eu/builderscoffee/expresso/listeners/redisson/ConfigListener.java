package eu.builderscoffee.expresso.listeners.redisson;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.api.common.redisson.Redis;
import eu.builderscoffee.api.common.redisson.listeners.PacketListener;
import eu.builderscoffee.api.common.redisson.listeners.ProcessPacket;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.commons.common.redisson.topics.CommonTopics;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.config.ConfigRequestable;
import eu.builderscoffee.expresso.buildbattle.config.ConfigResponsible;
import eu.builderscoffee.expresso.buildbattle.phase.bases.EndPhase;
import eu.builderscoffee.expresso.utils.ReflectionUtils;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfigListener implements PacketListener {

    @Getter
    private static Map<Class<? extends ConfigRequestable>, ConfigRequestable> requestables = new HashMap<>();

    /*
    public static class ConfigStage {
        public static final ConfigStage TEST = new ConfigStage();
        public static final ConfigStage TEST2 = new ConfigStage(TEST);

        final ConfigStage previous;
        public ConfigStage() {
            previous = null;
        }

        public ConfigStage(ConfigStage previous) {
            this.previous = previous;
        }
    }*/
    @Getter
    private static Map<Class<? extends ConfigResponsible>, ConfigResponsible> responsibles = new HashMap<>();
    @Getter
    @Setter
    private int plotSize = 0;
    private ConfigStage configStage = ConfigStage.BBINSTANCETYPE;

    public ConfigListener() {
        ReflectionUtils.reflectInstances(ConfigRequestable.class.getPackage(), ConfigRequestable.class)
                .forEach(requestable -> requestables.put(requestable.getClass(), requestable));
        ReflectionUtils.reflectInstances(ConfigResponsible.class.getPackage(), ConfigResponsible.class)
                .forEach(responsable -> responsibles.put(responsable.getClass(), responsable));
    }

    @ProcessPacket
    public void onConfigRequest(ServerManagerRequest request) {
        System.out.println("Request: " + request.getType());
        getRequestables().values().stream()
                .filter(config -> request.getType().equals(config.getType()))
                .forEach(config -> {
                    Redis.publish(CommonTopics.SERVER_MANAGER, config.request(request, new ServerManagerResponse(request)));
                });
    }

    /***
     * Recevoir l'action de configuration de la partie
     * Sinon renvoyer l'étape de configuration en cours
     * @param request
     */
    @ProcessPacket
    public void onRequestConfig(ServerManagerRequest request) {
        /*if (request.getType().equals("request_config")) {
            // On check si l'instance de la partie est null ?
            if (Objects.isNull(ExpressoBukkit.getBbGame())) {
                sendBuildBattleInstanceType(request);
                return;
                // Le type d'instance à déja été définis ?
            } else if (ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.NONE)) {
                sendGameType(request);
                return;
                // L'instance définie est expresso mais le type d'expresso n'est pas défini lui
            } else if (ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.EXPRESSO) && Objects.isNull(ExpressoBukkit.getBbGame().getExpressoGameType())) {
                sendGameType(request);
                return;
                // L'instance définie est classic mais le type n'est pas défini lui
            } else if (ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.CLASSIC) && Objects.isNull(ExpressoBukkit.getBbGame().getClassicGameType())) {
                sendGameConfig(request);
                return;
                // L'instance définie est tournois mais le type n'est pas défini lui
            } else if (ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.TOURNAMENT) && Objects.isNull(ExpressoBukkit.getBbGame().getTournamentGameType())) {
                sendGameConfig(request);
                return;
            } else if (Objects.isNull(ExpressoBukkit.getBbGame().getInstancePhases())) {
                sendPlayTime(request);
                // Le thème de la partie n'est pas définie
            } else if (Objects.isNull(ExpressoBukkit.getBbGame().getBbGameManager().getThemes())) {
                sendThemesSelection(request);
                return;
                // La map n'est pas générée
            } else if (Objects.isNull(Bukkit.getWorld(ExpressoBukkit.getSettings().getPlotWorldName()))) {
                sendMapGeneration(request);
                return;
                // La partie n'est pas démarrer ou est en pause
            } else if (!ExpressoBukkit.getBbGame().isReady() || ExpressoBukkit.getBbGame().isPaused()) {
                sendStartConfig(request);
                return;
                // La partie est démarrer
            } else if (ExpressoBukkit.getBbGame().isReady() && ExpressoBukkit.getBbGame().getBbGameManager().isRunning()) {
                sendGameConfigCategories(request);
                return;
            }
        }*/
    }

    /***
     * Sélectioner le temps de jeux d'une partie
     * @param request
     */
    public void sendPlayTime(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();
        itemsAction.setType("playtime");

        val gameType = ExpressoBukkit.getBbGame().getBuildBattleGameType();

        AtomicInteger i = new AtomicInteger(0);
        gameType.getPhases().stream()
                .filter(phase -> !(phase instanceof EndPhase))
                .forEach(phase -> itemsAction.addItem(2, 2 + i.incrementAndGet(), new ItemBuilder(phase.getIcon().getType()).setName("§a" + phase.getName()).addLoreLine(Arrays.asList("§bTemps:", "§bPar défault: §f" + TimeUtils.getDurationString(phase.getDefaultTime()), "§bCustom :§f" + TimeUtils.getDurationString(phase.getTime()))).build(), phase.getClass().getSimpleName()));

        itemsAction.addItem(3, 4, new ItemBuilder(Material.WOOL, 1, (short) 13).setName("§aValider les phases").build(), "setplaytime");

        // Add Action to response
        response.getActions().add(itemsAction);

        // Set

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    /***
     * Recevoir l'action du choix de type de partie
     * @param request
     */
    /*@ProcessPacket
    public void onRequestType(ServerManagerRequest request) {
        if (request.getType().equals("type")) {
            val type = BuildBattleInstanceType.valueOf(request.getData());
            ExpressoBukkit.setBbGame(new BuildBattle().setBbGameTypes(type));
            sendGameType(request);
        }
    }*/

    /***
     * Recevoir l'action du type d'expresso choisi
     * @param request
     */
    /*@ProcessPacket
    public void onRequestExpresso(ServerManagerRequest request) {
        if (request.getType().equals("expresso")) {
            val expressoString = request.getData();
            ExpressoBukkit.getBbGame().setExpressoGameType(ExpressoBukkit.getBbGame().getExpressoManager().fetchExpressoByName(expressoString));
            ExpressoBukkit.getBbGame().configureGameType(BuildBattleInstanceType.EXPRESSO);
            sendPlayTime(request);
        }
    }*/

    /***
     * Recevoir l'action de changer le temps des phases
     * @param request
     */
    /*@SneakyThrows
    @ProcessPacket
    public void onRequestPlayTime(ServerManagerRequest request) {
        if (request.getType().equals("playtime")) {
            val gameType = ExpressoBukkit.getBbGame().getBuildBattleGameType();

            if (request.getData().equals("setplaytime")) {
                ExpressoBukkit.getBbGame().setInstancePhases(ExpressoBukkit.getBbGame().getBuildBattleGameType().getPhases());
                sendThemesSelection(request);
                return;
            }

            for (BBPhase phase : gameType.getPhases()) {
                if (request.getData().equals(phase.getClass().getSimpleName())) {
                    switch (request.getItemAction()) {
                        case LEFT_CLICK:
                            phase.setTime(phase.getTime() + (int) phase.getUnit().toSeconds(1));
                            break;
                        case SHIFT_LEFT_CLICK:
                            phase.setTime(phase.getTime() + (int) phase.getUnit().toSeconds(60));
                            break;
                        case RIGHT_CLICK:
                            phase.setTime(phase.getTime() <= (int) phase.getUnit().toSeconds(1) ? 0 : phase.getTime() - (int) phase.getUnit().toSeconds(1));
                            break;
                        case SHIFT_RIGHT_CLICK:
                            phase.setTime(phase.getTime() <= (int) phase.getUnit().toSeconds(60) ? 0 : phase.getTime() - (int) phase.getUnit().toSeconds(60));
                            break;
                        case DROP:
                            phase.setTime(phase.getDefaultTime());
                            break;
                    }
                    sendPlayTime(request);
                }
            }
        }
    }*/

    /***
     * Recevoir l'action du thème choisi
     * @param request
     */
    /*@ProcessPacket
    public void onRequestTheme(ServerManagerRequest request) {
        if (request.getType().equals("theme")) {
            ExpressoBukkit.getBbGame().getBbGameManager().setThemes(request.getData());
            sendMapGeneration(request);
        }
    }*/

    /***
     * Recevoir l'action lié à la partie
     * @param request
     */
    /*@ProcessPacket
    public void onRequestGame(ServerManagerRequest request) {
        if (!request.getType().equals("game")) return;

        switch (request.getData()) {
            case "settings":
                sendGameConfig(request);
                break;
            case "utils":
                sendGameUtils(request);
                break;
            case "start":
                // Dire que la confiruation est finie
                sendEndConfig(request);
                // Checker si le monde exist
                if (Objects.nonNull(ExpressoBukkit.getInstance().getServer().getWorld(ExpressoBukkit.getSettings().getPlotWorldName()))) {
                    // Si oui le supprimer
                    Bukkit.getWorld(ExpressoBukkit.getSettings().getPlotWorldName()).getWorldFolder().delete();
                }
                if (Objects.isNull(ExpressoBukkit.getInstance().getServer().getWorld(ExpressoBukkit.getSettings().getPlotWorldName()))) {
                    // Générer la map
                    new WorldBuilder.DefaultWorldBuilder()
                            .setBedrock(true)
                            .setPlotFilling(new ItemStack(Material.DIRT))
                            .setPlotFloor(new ItemStack(Material.GRASS, 1))
                            .setPlotHeight(64)
                            .setPlotSize(plotSize)
                            .setRoadBlock(new ItemStack(Material.QUARTZ_BLOCK))
                            .setRoadHeight(64)
                            .setRoadWidth(7)
                            .setWall(new ItemStack(Material.STONE_SLAB2))
                            .setWallClaimed(new ItemStack(Material.STONE_SLAB2, 1, (short) 2))
                            .setWallFilling(new ItemStack(Material.STONE))
                            .setWallHeight(64)
                            .generate(ExpressoBukkit.getSettings().getPlotWorldName());
                }
                // Lancer la partie
                ExpressoBukkit.getBbGame().setReady(true);
                ExpressoBukkit.getBbGame().getBbGameManager().startGame();
                break;
            case "stop":
                // Stopper la partie
                if (ExpressoBukkit.getBbGame().isReady()) {
                    ExpressoBukkit.getBbGame().getBbGameManager().cancelGame();
                    //TODO Send message to server , fix console error
                }
                break;
            case "pause":
                Log.get().info("Receive Pause Action");
                // Mettre en pause la partie
                ExpressoBukkit.getBbGame().getBbGameManager().PauseGame();
                // Envoyer le menu start
                sendStartConfig(request);
                break;
            case "worldbackup":
                if (!BackupUtils.backupOfWorldExist(ExpressoBukkit.getSettings().getPlotWorldName(), ExpressoBukkit.getInstance().getServer().getServerName())) {
                    BackupUtils.backupWorld(ExpressoBukkit.getSettings().getPlotWorldName(), ExpressoBukkit.getInstance().getServer().getServerName());
                    Log.get().info("Le monde à été backup");
                }
                break;
        }
    }*/

    /***
     * Recevoir l'action lié à la génération de la map
     * @param request
     */
    /*@ProcessPacket
    public void onRequestMapGen(ServerManagerRequest request) {
        if (!request.getType().startsWith("plot")) return;
        val maxPlotSize = ExpressoBukkit.getSettings().getPlotMaxSize();
        val minPlotSize = ExpressoBukkit.getSettings().getPlotMinSize();
        switch (request.getData()) {
            case "size":
                switch (request.getItemAction()) {
                    case LEFT_CLICK:
                        plotSize = plotSize >= maxPlotSize ? maxPlotSize : plotSize + 1;
                        break;
                    case SHIFT_LEFT_CLICK:
                        plotSize = plotSize >= maxPlotSize ? maxPlotSize : plotSize + 10;
                        break;
                    case RIGHT_CLICK:
                        plotSize = plotSize <= minPlotSize ? 0 : plotSize - 1;
                        break;
                    case SHIFT_RIGHT_CLICK:
                        plotSize = plotSize <= minPlotSize ? 0 : plotSize - 10;
                        break;
                }
                sendMapGeneration(request);
                break;
            case "mapgen":
                sendStartConfig(request);
                break;
        }
    }*/

    /***
     * Répondre à la requète avec le type de partie voulue
     * @param request
     */
    /*public void sendBuildBattleInstanceType(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();

        itemsAction.setType("type");
        int column = 2;
        for (BuildBattleInstanceType bbit : BuildBattleInstanceType.values()) {
            if (bbit.equals(BuildBattleInstanceType.NONE)) continue;
            itemsAction.addItem(2, column, bbit.getIcon(), bbit.name());
            column += 2;
        }

        // Add Action to response
        response.getActions().add(itemsAction);

        //Set Stage
        configStage = ConfigStage.BBINSTANCETYPE;

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }*/

    /***
     * Répondre à la requète avec les types de sous partie pour chaques instances
     * @param request
     */
    /*public void sendGameType(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);

        switch (ExpressoBukkit.getBbGame().getBbGameTypes()) {
            case CLASSIC:
            case TOURNAMENT:
                sendGameType(request);
                break;
            case EXPRESSO:
                val expressoList = ExpressoBukkit.getBbGame().getExpressoManager().getExpressoGameTypes();
                val pageItemsAction = new ServerManagerResponse.PageItems();
                pageItemsAction.setType("expresso");
                expressoList
                        .forEach(expresso -> {
                            pageItemsAction.addItem(new ItemBuilder(expresso.getIcon().getType(), 1, expresso.getIcon().getDurability()).setName("§a" + expresso.getName()).addLoreLine(expresso.getDescription()).build(), expresso.getName());
                        });
                // Add Action to response
                response.getActions().add(pageItemsAction);

                //Set Stage
                configStage = ConfigStage.BBINSTANCETYPE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ExpressoBukkit.getBbGame().getBbGameTypes());
        }

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }*/

    /***
     * Répondre à la requète avec une confirmation pour démarrer la partie
     * @param request
     */
    public void sendStartConfig(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();

        itemsAction.setType("game");
        if (!ExpressoBukkit.getBbGame().getBbGameManager().isRunning() || ExpressoBukkit.getBbGame().isPaused()) {
            itemsAction.addItem(2, 4, new ItemBuilder(Material.WOOL, 1, (short) 13).setName("§aDémarer").build(), "start");

            // Add Action to response
            response.getActions().add(itemsAction);
        }

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    /***
     * Selectioner un thème pour la partie
     * @param request
     */
    /*public void sendThemesSelection(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val pageItemsAction = new ServerManagerResponse.PageItems();
        pageItemsAction.setType("theme");
        // Get Themes form database
        val data = DataManager.getBuildbattleThemeStore().select(BuildbattleThemeEntity.class).get();

        // Paginate the themes
        data.forEach(theme -> pageItemsAction.addItem(new ItemBuilder(Material.MAP).setName("§a" + theme.getName()).build(), theme.getName()));

        // Add Action to response
        response.getActions().add(pageItemsAction);

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }*/

    /***
     * Répondre à la requète de génération d'une map
     * - Il faut la valider la laine verte :p
     * @param request
     */
    /*public void sendMapGeneration(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();
        itemsAction.setType("plot");

        itemsAction.addItem(2, 2, new ItemBuilder(Material.NAME_TAG).setName("§aTaille").addLoreLine(Arrays.asList("§7Taille du plot " + plotSize, "", "§aClick gauche +1 ", "§aClick droit -1", "§aShift click gauche +10", "§aShift click droit -10")).build(), "size");
        itemsAction.addItem(2, 6, new ItemBuilder(Material.BARRIER).setName("§cBientot").build(), "commingsoon");
        itemsAction.addItem(3, 4, new ItemBuilder(Material.WOOL, 1, (short) 13).setName("§aValider la génération").build(), "mapgen");

        // Add Action to response
        response.getActions().add(itemsAction);

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }*/

    /***
     * Répondre à la requète en disant que la configuration est terminée
     * @param request
     */
    public void sendEndConfig(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        response.setFinished(true);

        // Public the response
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    /***
     * Répondre à la requète des paramètres de la partie
     * @param request
     */
    public void sendGameConfigCategories(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();
        itemsAction.setType("game");

        itemsAction.addItem(2, 2, new ItemBuilder(Material.WATCH).setName("§7Gestion de la partie").build(), "settings");
        itemsAction.addItem(2, 6, new ItemBuilder(Material.WORKBENCH).setName("§7Utilitaire de la partie").build(), "utils");

        // Add Action to response
        response.getActions().add(itemsAction);

        // Publish the response
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    /***
     * Répondre à la requète avec les paramètre d'une partie
     * @param request
     */
    public void sendGameConfig(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);


        // Publish the response
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    /***
     * Répondre à la requète sur les utilitaires de la partie
     * @param request
     */
    public void sendGameUtils(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();
        itemsAction.setType("game");

        itemsAction.addItem(2, 4, new ItemBuilder(Material.MAP).setName("§aBackup World").build(), "worldbackup");

        // Add Action to response
        response.getActions().add(itemsAction);

        // Publish the response
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    public enum ConfigStage {
        BBINSTANCETYPE(null),
        GAMETYPE_EXPRESSO(ConfigStage.BBINSTANCETYPE),
        GAMETYPE_BUILDBATTLE(ConfigStage.BBINSTANCETYPE),
        GAMETYPE_TOURNAMENT(ConfigStage.BBINSTANCETYPE),
        EXPRESSO_TYPE(ConfigStage.GAMETYPE_EXPRESSO);

        final ConfigStage previous;

        ConfigStage(ConfigStage previous) {
            this.previous = previous;
        }
    }
}
