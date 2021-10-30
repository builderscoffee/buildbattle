package eu.builderscoffee.expresso.board;


import eu.builderscoffee.api.bukkit.board.FastBoard;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.utils.MessageUtils;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.val;
import org.bukkit.entity.Player;

import java.util.*;

public class BBBoard {

    public static Map<UUID, FastBoard> boards = new HashMap<>();


    public static List<String> getTheme(MessageConfiguration messages) {
        String littletheme = "§aThème : " + messages.getBoard().getBuildTheme();
        if (littletheme.length() < 30) {
            return Arrays.asList(littletheme);
        } else {
            return Arrays.asList("§aThème :", messages.getBoard().getBuildTheme());
        }
    }

    /***
     * Mettre à jours le scoreboard tout les ticks
     * @param board
     */
    public static void updateBoard(FastBoard board) {
        val message = MessageUtils.getMessageConfig(board.getPlayer());
        if (Main.getBbGame().getGameState().equals(BuildBattleManager.GameState.WAITING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : " + message.getBoard().getSeasonName(),
                    "§aExpresso : §f" + Main.getBbGame().getExpressoGameType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §fEn Attente",
                    "",
                    message.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------"
            );
        } else if (Main.getBbGame().getGameState().equals(BuildBattleManager.GameState.LAUNCHING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : " + message.getBoard().getSeasonName(),
                    "§aExpresso : §f" + Main.getBbGame().getExpressoGameType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §f" + TimeUtils.getDurationString(Main.getBbGame().getExpressoGameType().getCurrentPhase().time()),
                    "",
                    message.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------"
            );
        } else if (Main.getBbGame().getGameState().equals(BuildBattleManager.GameState.IN_GAME)) {
            val part1 = Arrays.asList("§0§8§m----------§8§m------", "§aSaison :  " + message.getBoard().getSeasonName(), "§aExpresso : §f" + Main.getBbGame().getExpressoGameType().getName());
            val _board = new ArrayList<>(part1);
            _board.addAll(getTheme(message));
            val part2 = Arrays.asList("§aTimer : §f" + TimeUtils.getDurationString(Main.getBbGame().getExpressoGameType().getCurrentPhase().time()), "", message.getBoard().getServerIp(), "§0§8§m----------§8§m------");
            _board.addAll(part2);
            board.updateLines(_board);
        }
    }
}
