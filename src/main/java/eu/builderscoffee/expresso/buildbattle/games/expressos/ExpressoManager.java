package eu.builderscoffee.expresso.buildbattle.games.expressos;

import eu.builderscoffee.expresso.buildbattle.BuildBattle;
import lombok.Data;
import lombok.val;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpressoManager {

    public List<ExpressoGameType> expressoGameTypes;
    private final BuildBattle bbGame;
    private ExpressoGameType currentExpressoGameType;

    public ExpressoManager(BuildBattle bbGame) {
        this.bbGame = bbGame;
        this.expressoGameTypes = new ArrayList<>();
        setCurrentExpressoGameType(bbGame.getExpressoGameTypeType());
        // Récuperer tout les expresso
        getAllExpresso();
        // Définir un expresso par défault
        setCurrentExpressoGameType(bbGame.getExpressoGameTypeType());
    }

    /**
     * Retournes tout les expresso crées
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
