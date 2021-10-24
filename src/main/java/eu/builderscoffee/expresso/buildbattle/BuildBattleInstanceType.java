package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.expresso.buildbattle.games.classic.ClassicGameType;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoGameType;
import eu.builderscoffee.expresso.buildbattle.games.tournament.TournamentGameType;
import lombok.Getter;

public enum BuildBattleInstanceType {

    EXPRESSO(ExpressoGameType.class, "Expresso"),
    CLASSIC(ClassicGameType.class, "Classic"),
    TOURNAMENT(TournamentGameType.class, "Tournois");

    @Getter
    private final Class<? extends BuildBattleGameType> buildbattleGameTypeClass;
    @Getter
    private final String buildBattleGameTypeName;

    BuildBattleInstanceType(Class<? extends BuildBattleGameType> buildbattleGameTypeClass, String buildBattleGameTypeName) {
        this.buildbattleGameTypeClass = buildbattleGameTypeClass;
        this.buildBattleGameTypeName = buildBattleGameTypeName;
    }
}
