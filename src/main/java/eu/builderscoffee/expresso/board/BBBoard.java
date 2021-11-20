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
     * Si le string est supérieur à 30 caractères
     * alors split le string en plusieurs lignes
     * pour l'afficher dans le scoreboard
     * @param line - String à vérifier
     * @return
     */
    public static String InlineString(String line) {
        val strings = new ArrayList<String>();
        if (line.length() < 30) {
            String[] arraySplit = line.split("\\s");
            strings.addAll(Arrays.asList(arraySplit));
        }
        return strings.size() < 1 ? String.valueOf(strings) : line;
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
                    InlineString("§aSaison : " + message.getBoard().getSeasonName()),
                    InlineString("§aExpresso : §f" + ExpressoBukkit.getBbGame().getBuildBattleGameType().getName()),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §fEn Attente",
                    "",
                    message.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------"
            );
        } else if (ExpressoBukkit.getBbGame().getGameState().equals(BuildBattleManager.GameState.LAUNCHING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    InlineString("§aSaison : " + message.getBoard().getSeasonName()),
                    InlineString("§aExpresso : §f" + ExpressoBukkit.getBbGame().getBuildBattleGameType().getName()),
                    "§aThème : " + "§f§kLait",
                    InlineString("§aTimer : §f" + TimeUtils.getDurationString(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getTime() - ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getCurrentTime())),
                    "",
                    message.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------"
            );
        } else if (ExpressoBukkit.getBbGame().getGameState().equals(BuildBattleManager.GameState.IN_GAME)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    InlineString("§aSaison : " + message.getBoard().getSeasonName()),
                    InlineString("§aExpresso : §f" + ExpressoBukkit.getBbGame().getBuildBattleGameType().getName()),
                    InlineString(ExpressoBukkit.getBbGame().getBbGameManager().getThemes()),
                    InlineString("§aTimer : §f" + TimeUtils.getDurationString(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getTime() - ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getCurrentTime())),
                    "",
                    message.getBoard().getServerIp(),
                    "§0§8§m----------§8§m------");
            ;
        }
    }
}
