package eu.builderscoffee.expresso.buildbattle.events.configs;

import eu.builderscoffee.api.common.redisson.Redis;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.commons.common.redisson.topics.CommonTopics;
import eu.builderscoffee.expresso.buildbattle.events.ConfigListener;
import lombok.Data;

import java.util.Map;

@Data
public abstract class ConfigTemplate {

    public String type;

    public ConfigTemplate(String type) {
        this.type = type;
    }

    public abstract ServerManagerRequest request(ServerManagerRequest request);

    public final void onRequest(ServerManagerRequest request){
        sendResponse(request(request));
    }
    public abstract ServerManagerResponse response(ServerManagerResponse response);

    public final void sendResponse(ServerManagerRequest request){
        Redis.publish(CommonTopics.SERVER_MANAGER, response(new ServerManagerResponse(request)));
    }

    public final ServerManagerResponse redirect(Class<? extends ConfigTemplate> clazz, ServerManagerResponse response){
        return ConfigListener.getConfigs().get(BuildbattleInstanceType.class).response(response);
    }
}
