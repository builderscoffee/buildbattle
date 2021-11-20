package eu.builderscoffee.expresso.buildbattle.games.expressos;

import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import lombok.Data;
import lombok.val;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpressoManager {

    private final BuildBattle bbGame;
    public List<ExpressoGameType> expressoGameTypes;
    private ExpressoGameType currentExpressoGameType;

    public ExpressoManager(BuildBattle bbGame) {
        System.out.println("EXP A");
        this.bbGame = bbGame;
        System.out.println("EXP B");
        this.expressoGameTypes = new ArrayList<>();
        System.out.println("EXP C");
        setCurrentExpressoGameType(bbGame.getExpressoGameType());
        System.out.println("EXP D");
        // Récuperer tout les expresso
        getAllExpresso();
        System.out.println("EXP E");
        // Définir un expresso par défault
        setCurrentExpressoGameType(bbGame.getExpressoGameType());
        System.out.println("EXP F");
    }

    /**
     * Retourne un expresso par sont nom
     *
     * @param name
     * @return
     */
    public ExpressoGameType fetchExpressoByName(String name) {
        return expressoGameTypes.stream().filter(expresso -> expresso.getName().equals(name)).findFirst().get();
    }

    /**
     * Retournes tout les class expressos
     */
    private void getAllExpresso() {
        val reflections = new Reflections(ExpressoGameType.class.getPackage().getName());
        val classes = reflections.getSubTypesOf(ExpressoGameType.class);
        classes.forEach(expressoClass -> {
            try {
                expressoClass.getDeclaredConstructor().setAccessible(true);
                val expresso = expressoClass.newInstance();
                expressoGameTypes.add(expresso);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
