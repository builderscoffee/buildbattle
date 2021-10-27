package eu.builderscoffee.expresso.buildbattle.events;

import eu.builderscoffee.api.common.events.EventListener;
import eu.builderscoffee.api.common.events.ProcessEvent;
import eu.builderscoffee.api.common.events.events.HeartBeatEvent;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BuildBattleInstanceType;

import java.util.Objects;

public class HeartBeatListener implements EventListener {

    /*
    - Exemple -
    GameType : Expresso
    GameSubType : Allongé
    State :  in_game
    Timer : 1h
     */

    @ProcessEvent
    public void onHeartBeat(HeartBeatEvent event) {
        if (Objects.nonNull(Main.getBbGame())) {
            if(!Main.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.NONE)) {
                event.getServer().getProperties().put("GameType", Main.getBbGame().getBbGameTypes().getBuildBattleGameTypeName());
            }
            if(Objects.nonNull(Main.getBbGame().getBuildBattleGameType())) {
                event.getServer().getProperties().put("GameSubType", Main.getBbGame().getBuildBattleGameType().getName());
            }
            if (Main.getBbGame().isReady()) { event.getServer().getProperties().put("State", Main.getBbGame().getGameState().toString()); }
            event.getServer().getProperties().put("Timer", String.valueOf(Main.getBbGame().getBuildBattleGameType().getCurrentPhase().time()));

        }
    }
}
