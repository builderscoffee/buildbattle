package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.buildbattle.BBGame;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

public class ExpressoManager {

    @Getter
    public List<Expresso> expressos;
    @Getter
    private BBGame bbGame;
    @Getter
    @Setter
    private Expresso currentExpresso;

    public ExpressoManager(BBGame bbGame) {
        this.bbGame = bbGame;
        this.expressos = new ArrayList<>();
        setCurrentExpresso(bbGame.getExpressoType());
        // Récuperer tout les expresso
        getAllExpresso();
        // Définir un expresso par défault
        setCurrentExpresso(bbGame.getExpressoType());
    }

    /**
     * Retournes tout les expresso crées
     */
    private void getAllExpresso() {
        val reflections = new Reflections(Expresso.class.getPackage().getName());
        val classes = reflections.getSubTypesOf(Expresso.class);
        classes.forEach(expressoClass -> {
            try {
                expressoClass.getDeclaredConstructor().setAccessible(true);
                val expresso = expressoClass.newInstance();
                expressos.add(expresso);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
