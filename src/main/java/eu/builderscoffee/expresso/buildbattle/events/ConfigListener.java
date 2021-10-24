package eu.builderscoffee.expresso.buildbattle.events;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.api.common.redisson.Redis;
import eu.builderscoffee.api.common.redisson.listeners.PacketListener;
import eu.builderscoffee.api.common.redisson.listeners.ProcessPacket;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.commons.common.redisson.topics.CommonTopics;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import eu.builderscoffee.expresso.buildbattle.BuildBattleInstanceType;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ConfigListener implements PacketListener {

    /***
     * Recevoir l'action de configuration de la partie
     * @param request
     */
    @ProcessPacket
    public void onRequestConfig(ServerManagerRequest request) {
        System.out.println(request.serialize());
        // Request BuildBattleInstanceType
        if (request.getAction().equals("request_config")) {
            if (Objects.isNull(Main.getBbGame())) {
                sendBuildBattleInstanceType(request);
                return;
            } else if (Main.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.NONE)) {
                sendGameType(request);
                return;
            } else if (Main.getBbGame().isReady()) {
                sendGameConfig(request);
            } else {
                sendStartConfig(request);
            }
        }
    }

    /***
     * Recevoir l'action du choix de type de partie
     * @param request
     */
    @ProcessPacket
    public void onRequestType(ServerManagerRequest request) {
        if (request.getAction().startsWith("type.")) {
            val type = BuildBattleInstanceType.valueOf(request.getAction().replaceFirst("type.", ""));
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
        if(request.getAction().startsWith("expresso.")) {
            val expressoString = request.getAction().replace("expresso.","");
            Main.getBbGame().setExpressoGameType(Main.getBbGame().getExpressoManager().fetchExpressoByName(expressoString));
            Main.getBbGame().configureGameType(BuildBattleInstanceType.EXPRESSO);
            sendStartConfig(request);
        }
    }

    /***
     * Recevoir l'action de démarrer une partie
     * @param request
     */
    @ProcessPacket
    public void onRequestStart(ServerManagerRequest request) {
        if (request.getAction().equals("game_start")) {
            Main.getBbGame().setReady(true);
            Main.getBbGame().getBbGameManager().startGame();
        }
    }

    /***
     * Répondre à la requète avec le type de partie voulue
     * @param request
     */
    public void sendBuildBattleInstanceType(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);

        for (BuildBattleInstanceType bbit : BuildBattleInstanceType.values()) {
            if(bbit.equals(BuildBattleInstanceType.NONE)) continue;
            val item = new ItemBuilder(Material.PAPER).setName(bbit.name()).build();
            response.setTitle(Bukkit.getName() + " Configuration");
            response.addItem(-1, -1, item, "type." + bbit.name());
            System.out.println(bbit.name());
        }

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
        System.out.println(request.serialize());
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
                expressoList
                        .forEach(expresso -> {
                            response.addItem(-1, -1, new ItemBuilder(expresso.getIcon().getType(),1, expresso.getIcon().getDurability()).setName(expresso.getName()).addLoreLine(expresso.getDescription()).build(), "expresso." + expresso.getName());
                        });
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
        System.out.println(response.serialize());
    }

    /***
     * Répondre à la requète avec une confirmation pour démarrer la partie
     * @param request
     */
    public void sendStartConfig(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);

        if (!Main.getBbGame().getBbGameManager().isRunning())
            response.addItem(-1, -1, new ItemBuilder(Material.WOOL, 1, (short) 13).setName("Démarer").build(), "game_start");
        else
            response.setFinished(true);
        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
        System.out.println(request.serialize());
    }

    /***
     * Répondre à la requète avec les paramètre d'une partie
     * @param request
     */
    public void sendGameConfig(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);

        response.addItem(-1,-1,new ItemBuilder(Material.WOOL,1,(short) 1).setName("Stop").build(), "game_stop");
        response.addItem(-1,-1,new ItemBuilder(Material.WOOL,1,(short) 6).setName("Pause").build(), "game_pause");
        response.addItem(-1,-1,new ItemBuilder(Material.DROPPER,1).setName("Reset").build(), "game_reset");

        Redis.publish(CommonTopics.SERVER_MANAGER, response);
        System.out.println(request.serialize());
    }
}
