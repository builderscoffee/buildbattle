package eu.builderscoffee.expresso.buildbattle.expressos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Expressos {
    Classic("Classic"),
    Alamano("A la mano"),
    Amer("Amer"),
    Toutnoir("Ca va faire tout noir"),
    SwitchBuild("Switch'nd Build"),
    SouleveCup("Soulève ta cup"),
    BoucheTrou("Bouche trou"),
    DeuxClassico("II Classico"),
    Hazar("Hazar"),
    Bingo("Bingo");


    String name;
    List<String> description;

    Expressos(String name) {
        this.name = name;
    }
}
