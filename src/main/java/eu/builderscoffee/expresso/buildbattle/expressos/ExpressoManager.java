package eu.builderscoffee.expresso.buildbattle.expressos;

import eu.builderscoffee.expresso.buildbattle.BBGame;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.reflections.Reflections;

import java.util.List;
import java.util.Objects;

public class ExpressoManager {

    @Getter
    private BBGame bbGame;
    @Getter
    public static List<Expresso> expressos;

    @Getter @Setter
    private Expresso currentExpresso;

    public ExpressoManager(BBGame bbGame) {
        this.bbGame = bbGame;
        // Récuperer tout les expresso
        getAllExpresso();
    }

    /**
     * Retournes tout les expresso crées
     */
    public void getAllExpresso() {
        val reflections = new Reflections("eu.builderscoffee.expresso.buildbattle.expressos.types");
        val classes = reflections.getSubTypesOf(Expresso.class);
        for (Class<? extends Expresso> aClass : classes) {
            System.out.println(aClass.getName()); // debug
            if (aClass == Expresso.class) {
                Expresso expresso = null;
                try {
                    expresso = aClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                expressos.add(expresso);
                System.out.println(Objects.requireNonNull(expresso).getClass().getName()); // debug
            }
        }
    }
}
