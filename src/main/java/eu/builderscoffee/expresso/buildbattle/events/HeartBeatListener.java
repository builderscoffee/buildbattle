package eu.builderscoffee.expresso.buildbattle.events;

import eu.builderscoffee.api.common.events.EventListener;
import eu.builderscoffee.api.common.events.ProcessEvent;
import eu.builderscoffee.api.common.events.events.HeartBeatEvent;
import eu.builderscoffee.expresso.ExpressoBukkit;
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
        if (Objects.nonNull(ExpressoBukkit.getBbGame())) {
            if (!ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.NONE)) {
                event.getServer().getProperties().put("GameType", ExpressoBukkit.getBbGame().getBbGameTypes().getBuildBattleGameTypeName());
            }
            if (Objects.nonNull(ExpressoBukkit.getBbGame().getBuildBattleGameType())) {
                event.getServer().getProperties().put("GameSubType", ExpressoBukkit.getBbGame().getBuildBattleGameType().getName());
            }
            if (ExpressoBukkit.getBbGame().isReady()) {
                event.getServer().getProperties().put("State", ExpressoBukkit.getBbGame().getGameState().toString());
            }
            if (Objects.nonNull(ExpressoBukkit.getBbGame().getBuildBattleGameType()) && Objects.nonNull(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase())) {
                event.getServer().getProperties().put("Timer", String.valueOf(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().time()));
            }
        }
    }
}
