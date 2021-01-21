package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.malus.Malus;

import java.util.List;

public abstract class Expresso {

    private Main main;

    public Expresso(Main main) {
        this.main = main;
        //DO SOME STUFF
    }

    public abstract void run();
    public abstract void end();

    public List<Malus> malusList;
}
