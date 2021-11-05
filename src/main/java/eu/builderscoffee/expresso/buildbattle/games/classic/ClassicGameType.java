package eu.builderscoffee.expresso.buildbattle.games.classic;

import eu.builderscoffee.expresso.buildbattle.BuildBattleGameType;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.EndPhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.GamePhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.LaunchingPhase;
import lombok.val;

import java.util.Deque;
import java.util.LinkedList;

import static eu.builderscoffee.expresso.utils.TimeUtils.HOUR;

public class ClassicGameType extends BuildBattleGameType {

    @Override
    public String getName() {
        return "BuildBattle Classic";
    }

    @Override
    public Deque<BBPhase> getPhases() {
        val phases = new LinkedList();
        phases.add(new LaunchingPhase(30));
        phases.add(new GamePhase(2 * HOUR));
        phases.add(new EndPhase());
        return phases;
    }
}