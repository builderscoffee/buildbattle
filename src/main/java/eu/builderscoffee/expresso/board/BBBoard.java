package eu.builderscoffee.expresso.board;


import eu.builderscoffee.api.bukkit.board.FastBoard;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.utils.MessageUtils;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.val;

import java.util.*;

public class BBBoard {

    public static Map<UUID, FastBoard> boards = new HashMap<>();


    public static List<String> getTheme() {
        if (ExpressoBukkit.getBbGame().getBbGameManager().getThemes() != null) {
            String littletheme = "§aThème : " + ExpressoBukkit.getBbGame().getBbGameManager().getThemes();
            if (littletheme.length() < 30) {
                return Arrays.asList(littletheme);
            } else {
                return Arrays.asList("§aThème :", ExpressoBukkit.getBbGame().getBbGameManager().getThemes());
            }
        }
        return null;
    }

    /***
     * Mettre à jours le scoreboard tout les ticks
     * @param board
     */
    public static void updateBoard(FastBoard board) {
        val message = MessageUtils.getMessageConfig(board.getPlayer());
        if (ExpressoBukkit.getBbGame().getGameState().equals(BuildBattleManager.GameState.WAITING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : " + message.getBoard().getSeasonName(),
                    "§aExpresso : §f" + ExpressoBukkit.getBbGame().getExpressoGameType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §fEn Attente",
                    "",
                    message.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------"
            );
        } else if (ExpressoBukkit.getBbGame().getGameState().equals(BuildBattleManager.GameState.LAUNCHING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : " + message.getBoard().getSeasonName(),
                    "§aExpresso : §f" + ExpressoBukkit.getBbGame().getExpressoGameType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §f" + TimeUtils.getDurationString(ExpressoBukkit.getBbGame().getExpressoGameType().getCurrentPhase().getTime() - ExpressoBukkit.getBbGame().getExpressoGameType().getCurrentPhase().getCurrentTime()),
                    "",
                    message.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------"
            );
            val phase = ExpressoBukkit.getBbGame().getExpressoGameType().getCurrentPhase();
            System.out.println("Time" + phase.getTime());
            System.out.println("CurrentTime" + phase.getCurrentTime());
            System.out.println("Time - CurrentTime" + (phase.getTime() - phase.getCurrentTime()));
        } else if (ExpressoBukkit.getBbGame().getGameState().equals(BuildBattleManager.GameState.IN_GAME)) {
            val part1 = Arrays.asList("§0§8§m----------§8§m------", "§aSaison :  " + message.getBoard().getSeasonName(), "§aExpresso : §f" + ExpressoBukkit.getBbGame().getExpressoGameType().getName());
            val _board = new ArrayList<>(part1);
            _board.addAll(getTheme());
            val part2 = Arrays.asList("§aTimer : §f" + TimeUtils.getDurationString(ExpressoBukkit.getBbGame().getExpressoGameType().getCurrentPhase().getTime() - ExpressoBukkit.getBbGame().getExpressoGameType().getCurrentPhase().getCurrentTime()), "", message.getBoard().getServerIp(), "§0§8§m----------§8§m------");
            _board.addAll(part2);
            board.updateLines(_board);
        }
    }
}
