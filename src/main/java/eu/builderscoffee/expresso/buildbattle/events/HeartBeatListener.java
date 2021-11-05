package eu.builderscoffee.expresso.buildbattle.events;

import eu.builderscoffee.api.common.events.EventListener;
import eu.builderscoffee.api.common.events.ProcessEvent;
import eu.builderscoffee.api.common.events.events.HeartBeatEvent;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleInstanceType;
import eu.builderscoffee.expresso.utils.TimeUtils;

import java.util.Objects;

public class HeartBeatListener implements EventListener {

    /**
    - Exemple d'affichage -
    GameType : Expresso
    GameSubType : Allongé
    State :  in_game
    Timer : 1h
     */

    @ProcessEvent
    public void onHeartBeat(HeartBeatEvent event) {
        if (Objects.nonNull(ExpressoBukkit.getBbGame())) {
            // Type de partie
            if (!ExpressoBukkit.getBbGame().getBbGameTypes().equals(BuildBattleInstanceType.NONE)) {
                event.getServer().getProperties().put("GameType", ExpressoBukkit.getBbGame().getBbGameTypes().getBuildBattleGameTypeName());
            }
            // Type de sous partie
            if (Objects.nonNull(ExpressoBukkit.getBbGame().getBuildBattleGameType())) {
                event.getServer().getProperties().put("GameSubType", ExpressoBukkit.getBbGame().getBuildBattleGameType().getName());
            }
            // Etat de la partie
            if (ExpressoBukkit.getBbGame().isReady()) {
                event.getServer().getProperties().put("State", ExpressoBukkit.getBbGame().getGameState().toString());
            }
            // Timer de la partie
            if (Objects.nonNull(ExpressoBukkit.getBbGame().getBuildBattleGameType()) && Objects.nonNull(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase())) {
                event.getServer().getProperties().put("Timer", TimeUtils.getDurationString(ExpressoBukkit.getBbGame().getExpressoGameType().getCurrentPhase().time()));
            }
        }
    }
}
