package eu.builderscoffee.expresso.buildbattle.events;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.api.common.redisson.Redis;
import eu.builderscoffee.api.common.redisson.listeners.PacketListener;
import eu.builderscoffee.api.common.redisson.listeners.ProcessPacket;
import eu.builderscoffee.commons.common.data.DataManager;
import eu.builderscoffee.commons.common.data.tables.BuildbattleThemeEntity;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.commons.common.redisson.topics.CommonTopics;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import eu.builderscoffee.expresso.buildbattle.BuildBattleInstanceType;
import eu.builderscoffee.expresso.utils.WorldBuilder;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.Objects;

public class ConfigListener implements PacketListener {

    /***
     * Recevoir l'action de configuration de la partie
     * @param request
     */
    @ProcessPacket
    public void onRequestConfig(ServerManagerRequest request) {
        if (request.getType().equals("request_config")) {
            if (Objects.isNull(Main.getBbGame())) {
                sendBuildBattleInstanceType(request);
                return;
            } else if (Main.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.NONE)) {
                sendGameType(request);
                return;
            } else if (Main.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.EXPRESSO) && Objects.isNull(Main.getBbGame().getExpressoGameType()) && !Main.getBbGame().isReady()) {
                sendGameType(request);
                return;
            } else if (Main.getBbGame().isReady()) {
                sendGameConfigCategories(request);
                return;
            } else if (!Main.getBbGame().isReady()) {
                sendStartConfig(request);
                return;
            }
        }
    }

    /***
     * Recevoir l'action du choix de type de partie
     * @param request
     */
    @ProcessPacket
    public void onRequestType(ServerManagerRequest request) {
        if (request.getType().equals("type")) {
            val type = BuildBattleInstanceType.valueOf(request.getData());
            Main.setBbGame(new BuildBattle().setBbGameTypes(type));
            sendGameType(request);
        }
    }

    /***
     * Recevoir l'action du type d'expresso choisi
     * @param request
     */
    @ProcessPacket
    public void onRequestExpresso(ServerManagerRequest request) {
        if (request.getType().equals("expresso")) {
            val expressoString = request.getData();
            Main.getBbGame().setExpressoGameType(Main.getBbGame().getExpressoManager().fetchExpressoByName(expressoString));
            Main.getBbGame().configureGameType(BuildBattleInstanceType.EXPRESSO);
            sendStartConfig(request);
        }
    }

    @ProcessPacket
    public void onRequestTheme(ServerManagerRequest request) {
        if (request.getType().equals("theme")) {
            //Main.getBbGame().getBbGameManager().setThemes(request.getType());
        }
    }

    /***
     * Recevoir l'action lié à la partie
     * @param request
     */
    @ProcessPacket
    public void onRequestGame(ServerManagerRequest request) {
        if(!request.getType().equals("game")) return;

        switch (request.getData()) {
            case "settings":
                sendGameConfig(request);
                break;
            case "utils":
                sendGameUtils(request);
                break;
            case "start":
                Main.getBbGame().setReady(true);
                Main.getBbGame().getBbGameManager().startGame();
                break;
            case "stop":
                if (Main.getBbGame().isReady()) {
                    Main.getBbGame().getBbGameManager().cancelGame();
                    //TODO Send message to server
                }
                break;
            case "pause":
                //TODO Mettre la partie en pause
                break;
        }
    }

    /***
     * Répondre à la requète avec le type de partie voulue
     * @param request
     */
    public void sendBuildBattleInstanceType(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();

        itemsAction.setType("type");
        for (BuildBattleInstanceType bbit : BuildBattleInstanceType.values()) {
            if (bbit.equals(BuildBattleInstanceType.NONE)) continue;
            val item = new ItemBuilder(Material.PAPER).setName(bbit.name()).build();
            itemsAction.addItem(-1, -1, item, bbit.name());

        }
        response.getActions().add(itemsAction);
        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
        System.out.println(response);
    }

    /***
     * Répondre à la requète avec les types de sous partie pour chaques instances
     * @param request
     */
    public void sendGameType(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);

        switch (Main.getBbGame().getBbGameTypes()) {
            case CLASSIC:
                //TODO Ajouter le type de partie classic
                //Main.getBbGame().setClassicGameType(null);
                //Main.getBbGame().configureGameType(BuildBattleInstanceType.CLASSIC);
                break;
            case EXPRESSO:
                val expressoList = Main.getBbGame().getExpressoManager().getExpressoGameTypes();
                val itemsAction = new ServerManagerResponse.Items();

                itemsAction.setType("expresso");
                expressoList
                        .forEach(expresso -> {
                            itemsAction.addItem(-1, -1, new ItemBuilder(expresso.getIcon().getType(), 1, expresso.getIcon().getDurability()).setName(expresso.getName()).addLoreLine(expresso.getDescription()).build(), expresso.getName());
                        });
                response.getActions().add(itemsAction);
                break;
            case TOURNAMENT:
                //TODO Ajouter le type de partie tournois
                //Main.getBbGame().setTournamentGameType(null);
                //Main.getBbGame().configureGameType(BuildBattleInstanceType.TOURNAMENT);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Main.getBbGame().getBbGameTypes());
        }

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    /***
     * Selectioner un thème pour la partie
     * @param request
     */
    public void sendThemesSelection(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();
        itemsAction.setType("theme");
        val data = DataManager.getBuildbattleThemeStore().select(BuildbattleThemeEntity.class).get();
        data.stream().forEach(theme -> itemsAction.addItem(-1, -1, new ItemBuilder(Material.MAP).setName(theme.getName()).build(), theme.getName()));

        response.getActions().add(itemsAction);

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    public void sendMapGeneration(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();
        itemsAction.setType("mapgen");
        //itemsAction.addItem(-1,-1, new ItemBuilder(Mater));

        new WorldBuilder.DefaultWorldBuilder()
                .setBedrock(true)
                .setPlotFilling(new ItemStack(Material.DIRT))
                .setPlotFloor(new ItemStack(Material.GRASS, 1))
                .setPlotHeight(64)
                .setPlotSize(42)
                .setRoadBlock(new ItemStack(Material.QUARTZ_BLOCK))
                .setRoadHeight(64)
                .setRoadWidth(7)
                .setWall(new ItemStack(Material.STONE_SLAB2))
                .setWallClaimed(new ItemStack(Material.STONE_SLAB2,1,(short)2))
                .setWallFilling(new ItemStack(Material.STONE))
                .setWallHeight(64)
                .generate("worldName");
    }

    /***
     * Répondre à la requète avec une confirmation pour démarrer la partie
     * @param request
     */
    public void sendStartConfig(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();

        itemsAction.setType("game");
        if (!Main.getBbGame().getBbGameManager().isRunning()) {
            itemsAction.addItem(-1, -1, new ItemBuilder(Material.WOOL, 1, (short) 13).setName("Démarer").build(), "start");
            response.getActions().add(itemsAction);
        } else
            response.setFinished(true);

        // Publish the reponse
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

        itemsAction.addItem(-1, -1, new ItemBuilder(Material.WATCH).setName("Gestion de la partie").build(), "settings");
        itemsAction.addItem(-1, -1, new ItemBuilder(Material.WORKBENCH).setName("Utilitaire de la partie").build(), "utils");

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
        val itemsAction = new ServerManagerResponse.Items();
        itemsAction.setType("game");

        itemsAction.addItem(-1, -1, new ItemBuilder(Material.WOOL, 1, (short) 1).setName("Stop").build(), "stop");
        itemsAction.addItem(-1, -1, new ItemBuilder(Material.WOOL, 1, (short) 6).setName("Pause").build(), "pause");
        itemsAction.addItem(-1, -1, new ItemBuilder(Material.DROPPER, 1).setName("Reset").build(), "reset");

        response.getActions().add(itemsAction);

        // Publish the response
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }

    public void sendGameUtils(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);
        val itemsAction = new ServerManagerResponse.Items();
        itemsAction.setType("game");

        itemsAction.addItem(-1, -1, new ItemBuilder(Material.MAP).setName("Backup World").build(), "worldbackup");

        response.getActions().add(itemsAction);

        // Publish the response
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
    }
}
