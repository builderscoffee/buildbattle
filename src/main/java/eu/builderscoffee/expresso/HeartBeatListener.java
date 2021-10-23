package eu.builderscoffee.expresso;

import eu.builderscoffee.api.common.events.EventListener;
import eu.builderscoffee.api.common.events.ProcessEvent;
import eu.builderscoffee.api.common.events.events.HeartBeatEvent;

public class HeartBeatListener implements EventListener {

    @ProcessEvent
    public void onHeartBeat(HeartBeatEvent event){
        event.getServer().getProperties().put("State", Main.getBbGame().getBbState());
    }
}
