package eu.builderscoffee.expresso.buildbattle.malus;

import eu.builderscoffee.expresso.Main;

public abstract class Malus {

    private Main main;

    public Malus(Main main) {
        this.main = main;
    }

    public abstract void inject();
    public abstract void clear();

    public abstract boolean IsActive();


}
