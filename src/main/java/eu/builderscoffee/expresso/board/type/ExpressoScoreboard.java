package eu.builderscoffee.expresso.board.type;

import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.board.BaseBoard;
import eu.builderscoffee.expresso.buildbattle.phase.bases.EndPhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.GamePhase;
import eu.builderscoffee.expresso.buildbattle.phase.bases.LaunchingPhase;
import eu.builderscoffee.expresso.utils.MessageUtils;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.val;

import java.util.Arrays;

public class ExpressoScoreboard extends BaseBoard {


    public ExpressoScoreboard() {
        this.getBoards().put(LaunchingPhase.class, player -> {
            val messages = MessageUtils.getMessageConfig(player);
            return Arrays.asList("§0§8§m----------§8§m------",
                    "§aSaison : ", messages.getBoard().getSeasonName(),
                    "§aExpresso : §f", ExpressoBukkit.getBbGame().getBuildBattleGameType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §f", TimeUtils.getDurationString(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getTime() - ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getCurrentTime()),
                    "",
                    messages.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------");
        });

        this.getBoards().put(GamePhase.class, player -> {
            val messages = MessageUtils.getMessageConfig(player);
            return Arrays.asList("§0§8§m----------§8§m------",
                    "§aSaison : ", messages.getBoard().getSeasonName(),
                    "§aExpresso : §f", ExpressoBukkit.getBbGame().getBuildBattleGameType().getName(),
                    "§aThème : ", ExpressoBukkit.getBbGame().getBbGameManager().getThemes(),
                    "§aTimer : §f", TimeUtils.getDurationString(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getTime() - ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getCurrentTime()),
                    "",
                    messages.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------");
        });

        this.getBoards().put(EndPhase.class, player -> {
            val messages = MessageUtils.getMessageConfig(player);
            return Arrays.asList("§0§8§m----------§8§m------",
                    "",
                    messages.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------");
        });
    }
}
