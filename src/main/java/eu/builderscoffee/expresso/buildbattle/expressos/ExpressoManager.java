package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.utils.Log;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpressoManager {

    @Getter
    private BBGame bbGame;
    @Getter
    public List<Expresso> expressos;

    @Getter @Setter
    private Expresso currentExpresso;

    public ExpressoManager(BBGame bbGame) {
        this.bbGame = bbGame;
        this.expressos = new ArrayList<>();
        // Récuperer tout les expresso
        getAllExpresso();
    }

    /**
     * Retournes tout les expresso crées
     */
    private void getAllExpresso() {
        val reflections = new Reflections(Expresso.class.getPackage().getName());
        val classes = reflections.getSubTypesOf(Expresso.class);
        for (val expressoClass : classes) {
                try {
                    expressoClass.getDeclaredConstructor().setAccessible(true);
                    val expresso = expressoClass.newInstance();
                    expressos.add(expresso);
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
        }
    }
}
