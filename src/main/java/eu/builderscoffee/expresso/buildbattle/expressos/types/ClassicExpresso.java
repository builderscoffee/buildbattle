package eu.builderscoffee.expresso.buildbattle.expressos.types;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.expressos.Expresso;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.PrePlotPhase;
import eu.builderscoffee.expresso.buildbattle.phase.types.WaitingPhase;

import java.util.ArrayList;
import java.util.List;

public class ClassicExpresso extends Expresso {

    public ClassicExpresso(Main main) {
        super(main);
    }

    @Override
    public List<BBPhase> getPhases() {
        List<BBPhase> phases = new ArrayList<>();
        phases.add(new WaitingPhase(Main.getBbGame(),false));
        phases.add(new PrePlotPhase(Main.getBbGame(),false));

        return super.getPhases();
    }
}
