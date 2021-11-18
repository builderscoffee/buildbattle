package eu.builderscoffee.expresso.buildbattle.events.configs;

import eu.builderscoffee.commons.common.redisson.packets.ServerManagerRequest;
import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;

public class RequestConfig extends ConfigTemplate{
    public RequestConfig() {
        super("request_config");
    }

    @Override
    public ServerManagerRequest request(ServerManagerRequest request) {
        return request;
    }

    @Override
    public ServerManagerResponse response(ServerManagerResponse response) {
        return redirect(BuildbattleInstanceType.class, response);
    }
}
