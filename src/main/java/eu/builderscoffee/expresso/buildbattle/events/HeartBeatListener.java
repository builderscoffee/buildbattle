package eu.builderscoffee.expresso.buildbattle.events;

import eu.builderscoffee.api.common.events.EventListener;
import eu.builderscoffee.api.common.events.ProcessEvent;
import eu.builderscoffee.api.common.events.events.HeartBeatEvent;
import eu.builderscoffee.expresso.Main;

import java.util.Objects;

public class HeartBeatListener implements EventListener {

    @ProcessEvent
    public void onHeartBeat(HeartBeatEvent event) {
        if (Objects.nonNull(Main.getBbGame())) {
            event.getServer().getProperties().put("State", Main.getBbGame().getBbState());
        }
    }
}
