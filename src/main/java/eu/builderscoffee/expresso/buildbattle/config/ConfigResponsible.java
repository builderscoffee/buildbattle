package eu.builderscoffee.expresso.buildbattle.config;

import eu.builderscoffee.commons.common.redisson.packets.ServerManagerResponse;
import eu.builderscoffee.expresso.listeners.redisson.ConfigListener;

public interface ConfigResponsible {
    ServerManagerResponse response(ServerManagerResponse response);

    default <T extends ConfigResponsible> T getResponsible(Class<T> clazz){
        return (T) ConfigListener.getResponsibles().get(clazz);
    }
}
