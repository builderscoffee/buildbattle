package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.buildbattle.expressos.types.IlClassicoExpresso;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Expressos {
    Classico("Il Classico", IlClassicoExpresso.class),
    Alamano("A la mano", null),
    Amer("Amer", null),
    Toutnoir("Ca va faire tout noir", null),
    SwitchBuild("Switch'nd Build", null),
    SouleveCup("Soulève ta cup", null),
    BoucheTrou("Bouche trou", null),
    DeuxClassico("II Classico", null),
    Hazar("Hazar", null),
    Bingo("Bingo", null);


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
