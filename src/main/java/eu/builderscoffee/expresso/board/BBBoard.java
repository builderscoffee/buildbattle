package eu.builderscoffee.expresso.board;


import eu.builderscoffee.api.bukkit.board.FastBoard;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.utils.TimeUtils;
import lombok.val;

import java.util.*;

public class BBBoard {

    public static Map<UUID, FastBoard> boards = new HashMap<>();

    public static List<String> getTheme() {
        String littletheme = "§aThème : " + Main.getSettings().getBoard_build_theme();
        if (littletheme.length() < 30) {
            return Arrays.asList(littletheme);
        } else {
            return Arrays.asList("§aThème :", Main.getSettings().getBoard_build_theme());
        }
    }

    /***
     * Mettre à jours le scoreboard tout les ticks
     * @param board
     */
    public static void updateBoard(FastBoard board) {
        if (Main.getBbGame().getBbState().equals(BuildBattleManager.BBState.WAITING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : " + Main.getSettings().getBoard_season_name(),
                    "§aExpresso : §f" + Main.getBbGame().getExpressoGameTypeType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §fEn Attente",
                    "",
                    Main.getSettings().getBoard_server_ip(),
                    "§0§8§m----------§8§m------"
            );
        } else if (Main.getBbGame().getBbState().equals(BuildBattleManager.BBState.LAUNCHING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : " + Main.getSettings().getBoard_season_name(),
                    "§aExpresso : §f" + Main.getBbGame().getExpressoGameTypeType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §f" + TimeUtils.getDurationString(Main.getBbGame().getExpressoGameTypeType().getCurrentPhase().time()),
                    "",
                    Main.getSettings().getBoard_server_ip(),
                    "§0§8§m----------§8§m------"
            );
        } else if (Main.getBbGame().getBbState().equals(BuildBattleManager.BBState.IN_GAME)) {
            val part1 = Arrays.asList("§0§8§m----------§8§m------", "§aSaison :  " + Main.getSettings().getBoard_season_name(), "§aExpresso : §f" + Main.getBbGame().getExpressoGameTypeType().getName());
            val _board = new ArrayList<>(part1);
            //_board.addAll(getTheme());
            val part2 = Arrays.asList("§aTimer : §f" + TimeUtils.getDurationString(Main.getBbGame().getExpressoGameTypeType().getCurrentPhase().time()), "", Main.getSettings().getBoard_server_ip(), "§0§8§m----------§8§m------");
            _board.addAll(part2);
            board.updateLines(_board);
        }
    }
}
