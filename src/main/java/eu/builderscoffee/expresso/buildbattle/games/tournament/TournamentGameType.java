package eu.builderscoffee.expresso.buildbattle.games.tournament;

import eu.builderscoffee.expresso.buildbattle.BuildBattleGameType;

public class TournamentGameType extends BuildBattleGameType {

    @Override
    public String getName() {
        return "Manche X du tournois X";
    }

    @Override
    public String getThemes() {
        return "Pull";
    }
}
