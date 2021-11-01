package eu.builderscoffee.expresso.buildbattle;

import eu.builderscoffee.api.bukkit.utils.ItemBuilder;
import eu.builderscoffee.expresso.buildbattle.games.classic.ClassicGameType;
import eu.builderscoffee.expresso.buildbattle.games.expressos.ExpressoGameType;
import eu.builderscoffee.expresso.buildbattle.games.tournament.TournamentGameType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum BuildBattleInstanceType {

    EXPRESSO(ExpressoGameType.class, "Expresso",new ItemBuilder(Material.INK_SACK,1,(short)3).setName("§cExpresso").addLoreLine("§7Mélange savoureux d'arôme").build()),
    CLASSIC(ClassicGameType.class, "Classic", new ItemBuilder(Material.WORKBENCH, 1).setName("§cClassic").addLoreLine("§7Décaféiné").build()),
    TOURNAMENT(TournamentGameType.class, "Tournois", new ItemBuilder(Material.BANNER,1).setName("§cTournois").addLoreLine("§7Mélange maison").build()),
    NONE(null, "Rien", null);

    @Getter
    private final Class<? extends BuildBattleGameType> buildbattleGameTypeClass;
    @Getter
    private final String buildBattleGameTypeName;
    @Getter
    private final ItemStack icon;

    BuildBattleInstanceType(Class<? extends BuildBattleGameType> buildbattleGameTypeClass, String buildBattleGameTypeName, ItemStack icon) {
        this.buildbattleGameTypeClass = buildbattleGameTypeClass;
        this.buildBattleGameTypeName = buildBattleGameTypeName;
        this.icon = icon;
    }
}
