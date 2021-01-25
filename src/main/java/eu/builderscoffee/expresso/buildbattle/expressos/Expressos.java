package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.buildbattle.expressos.types.IlClassicoExpresso;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Expressos {
    Classico("Il Classico", IlClassicoExpresso.class),
    Alamano("A la mano", IlClassicoExpresso.class),
    Amer("Amer", IlClassicoExpresso.class),
    Toutnoir("Ca va faire tout noir", IlClassicoExpresso.class),
    SwitchBuild("Switch'nd Build", IlClassicoExpresso.class),
    SouleveCup("Soulève ta cup", IlClassicoExpresso.class),
    BoucheTrou("Bouche trou", IlClassicoExpresso.class),
    DeuxClassico("II Classico", IlClassicoExpresso.class),
    Hazar("Hazar", IlClassicoExpresso.class),
    Bingo("Bingo", IlClassicoExpresso.class);


    @Getter
    private final String name;
    @Getter
    private final Class<? extends Expresso> expressoClass;
    @Getter
    private List<String> description;

    Expressos(String name, Class<? extends Expresso> expressoClass) {
        this.name = name;
        this.expressoClass = expressoClass;
    }

}
