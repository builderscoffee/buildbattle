package eu.builderscoffee.expresso.buildbattle.notation;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class Notation {
    private UUID UUID;
    private Map<NotationType, Integer> notes = new HashMap<>();

    public Notation(UUID uuid) {
        this.UUID = uuid;
        Arrays.stream(NotationType.values()).forEach(notationType -> notes.put(notationType, 0));
    }

    public enum NotationType {

        Beauty(30),
        Creative(22),
        Amenagement(22),
        Folklore(22),
        Fun(4);

        @Getter
        private final int maxValue; // Valeur maximun d'une note

        NotationType(int maxValue) {
            this.maxValue = maxValue;
        }
    }

}
