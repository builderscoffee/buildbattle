package eu.builderscoffee.expresso.buildbattle.notation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Notation {
    private UUID UUID;
    private int beauty, crea, amenagement, folklore, fun;

    /***
     * Retourne la valeur en cache par rapport au type de notations
     * @param notationType
     * @return
     */
    public int getCachedValue(NotationType notationType) {
        switch (notationType) {
            case Beauty:
               return beauty;
            case Creative:
                return crea;
            case Amenagement:
                return amenagement;
            case Folklore:
                return folklore;
            case Fun:
                return fun;
            default:
                throw new IllegalStateException("Unexpected value: " + notationType);
        }
    }

    public enum NotationType {

        Beauty(30),
        Creative(22),
        Amenagement(22),
        Folklore(22),
        Fun(4);

        @Getter
        private int maxValue; // Valeur maximun d'une note

        NotationType(int maxValue) {
            this.maxValue = maxValue;
        }
    }

}
