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

import java.util.Objects;

public class ConfigListener implements PacketListener {

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
            } else {
                sendStartConfig(request);
            }
        }
    }

    @ProcessPacket
    public void onRequestType(ServerManagerRequest request) {
        //Define BuildBattleInstanceType
        if (request.getAction().startsWith("type.")) {
            val type = BuildBattleInstanceType.valueOf(request.getAction().replaceFirst("type.", ""));
            Main.setBbGame(new BuildBattle().setBbGameTypes(type));
            sendGameType(request);
        }
    }

    @ProcessPacket
    public void onRequestStart(ServerManagerRequest request) {
        if (request.getAction().equals("start")) {
            Main.getBbGame().getBbGameManager().startTimer();
            Main.getBbGame().getBbGameManager().startBoard();
        }
    }

    /***
     * Envoyer la réponses avec le type de partie
     * @param request
     */
    public void sendBuildBattleInstanceType(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);

        for (BuildBattleInstanceType bbit : BuildBattleInstanceType.values()) {
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
     * Envoyer la réponses avec le type de sous partie
     * @param request
     */
    public void sendGameType(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);

        switch (Main.getBbGame().getBbGameTypes()) {
            case CLASSIC:
                break;
            case EXPRESSO:
                val expressoList = Main.getBbGame().getExpressoManager().getExpressoGameTypes();
                expressoList.stream()
                        .filter(expresso -> request.getAction().equals("type." + expresso.getName()))
                        .forEach(expresso -> {
                            response.addItem(-1, -1, expresso.getIcon(), "expresso." + expresso.getName());
                            Main.getBbGame().configureExpresso(expresso);
                        });
                break;
            case TOURNAMENT:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Main.getBbGame().getBbGameTypes());
        }

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
        System.out.println(response.serialize());
    }

    /***
     * Envoyer la réponse pour démarrer la partie
     * @param request
     */
    public void sendStartConfig(ServerManagerRequest request) {
        // Create from the request
        val response = new ServerManagerResponse(request);

        if (!Main.getBbGame().getBbGameManager().isRunning())
            response.addItem(-1, -1, new ItemBuilder(Material.WOOL, 1, (short) 13).setName("Démarer").build(), "start");
        else
            response.setTitle("finish");

        // Publish the reponse
        Redis.publish(CommonTopics.SERVER_MANAGER, response);
        System.out.println(request.serialize());
    }
}
