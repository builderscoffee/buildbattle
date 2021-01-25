package eu.builderscoffee.expresso.board;

import eu.builderscoffee.api.board.FastBoard;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGame;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BBBoard {

    public static Map<UUID, FastBoard> boards = new HashMap<>();
    @Getter
    public static boolean hiddenTheme = true;

    public static String getTimeStringed(int time) {
        String timeStringed = null;
        if (time > 60) {
            if (time % 60 == 0) {
                timeStringed = "§b" + time / 60 + " §emin";
            }
        } else {
            final String plur = (time > 1) ? "s" : "";
            timeStringed = "§b" + time + " §esec" + plur;
        }
        return timeStringed;
    }

    public static String getDurationString(int seconds) {
        String duration = null;
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        if (hours >= 1 && minutes >= 0) {
            duration = twoDigitString(hours) + "h" + twoDigitString(minutes);
        } else if (hours < 1 && minutes >= 1) {
            duration = twoDigitString(minutes) + (minutes > 1 ? " mins" : " min");
        } else {
            duration = seconds + (seconds > 1 ? " secs" : " sec");
        }
        return duration;
    }

    public static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }
        if (number < 10) {
            return "" + number;
        } else if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static void updateBoard(FastBoard board) {

        Player player = board.getPlayer();
        if (Main.getBbGame().getBbState().equals(BBGameManager.BBState.WAITING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : §6Torréfaction ",
                    "§aExpresso : §f" + BBGame.getExpressoType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §fEn Attente",
                    "",
                    " §6play.builderscoffee.eu",
                    "§0§8§m----------§8§m------"
            );
        } else if (Main.getBbGame().getBbState().equals(BBGameManager.BBState.LAUNCHING)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : §6Torréfaction ",
                    "§aExpresso : §f" + BBGame.getExpressoType().getName(),
                    "§aThème : " + "§f§kLait",
                    "§aTimer : §f" + getTimeStringed(Main.getBbGame().bbGameManager.getStartTask().getTime()),
                    "",
                    " §6play.builderscoffee.eu",
                    "§0§8§m----------§8§m------"
            );
        } else if (Main.getBbGame().getBbState().equals(BBGameManager.BBState.IN_GAME)) {
            board.updateLines(
                    "§0§8§m----------§8§m------",
                    "§aSaison : §6Torréfaction ",
                    "§aExpresso : §f" + BBGame.getExpressoType().getName(),
                    "§aThème : §f" + Main.getSettings().getBuildTheme(),
                    "§aTimer : §f" + getDurationString(Main.getBbGame().bbGameManager.getGameTask().getTime()),
                    "",
                    " §6play.builderscoffee.eu",
                    "§0§8§m----------§8§m------"
            );
        }
    }
}
