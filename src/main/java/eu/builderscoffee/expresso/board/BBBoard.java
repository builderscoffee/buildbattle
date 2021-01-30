package eu.builderscoffee.expresso.board;

import eu.builderscoffee.api.board.FastBoard;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.Getter;
import lombok.val;

import java.util.*;

public class BBBoard {

    public static Map<UUID, FastBoard> boards = new HashMap<>();

    public static BBGame bbGame = Main.getBbGame();

    public static List<String> getTheme() {
        String littletheme = "§aThème : " + Main.getSettings().getBuildTheme();
        if (littletheme.length() < 30) {
            return Arrays.asList(littletheme);
        } else {
            return Arrays.asList("§aThème :", Main.getSettings().getBuildTheme());
        }
    }

    /***
     * Mettre à jours le scoreboard tout les ticks
     * @param board
     */
    public static void updateBoard(FastBoard board) {
        if (Main.getBbGame().getBbState().equals(BBGameManager.BBState.WAITING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : " + Main.getSettings().getSeasonName(),
                    "§aExpresso : §f" + bbGame.getExpressoType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §fEn Attente",
                    "",
                    Main.getSettings().getServerIp(),
                    "§0§8§m----------§8§m------"
            );
        } else if (Main.getBbGame().getBbState().equals(BBGameManager.BBState.LAUNCHING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : " + Main.getSettings().getSeasonName(),
                    "§aExpresso : §f" + bbGame.getExpressoType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §f" + TimeUtils.getDurationString(Main.getBbGame().bbGameManager.getStartTask().getTime()),
                    "",
                    Main.getSettings().getServerIp(),
                    "§0§8§m----------§8§m------"
            );
        } else if (Main.getBbGame().getBbState().equals(BBGameManager.BBState.IN_GAME)) {
            val _board = new ArrayList<String>();
            val part1 = Arrays.asList("§0§8§m----------§8§m------", "§aSaison :  " + Main.getSettings().getSeasonName(), "§aExpresso : §f" + bbGame.getExpressoType().getName());
            _board.addAll(part1);
            _board.addAll(getTheme());
            val part2 = Arrays.asList("§aTimer : §f" + TimeUtils.getDurationString(Main.getBbGame().bbGameManager.getGameTask().getTime()), "", Main.getSettings().getServerIp(), "§0§8§m----------§8§m------");
            _board.addAll(part2);
            board.updateLines(_board);
        }
    }
}
