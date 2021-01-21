package eu.builderscoffee.expresso.board;

import eu.builderscoffee.api.board.FastBoard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BBBoard {

    public static Map<UUID, FastBoard> boards = new HashMap<>();

    public static void updateBoard(FastBoard board) {
        final FastBoard fastBoard = board;

        Player player = board.getPlayer();
        fastBoard.updateLines(
                "§0§8§m----------§8§m------",
                "§aSaison : §6Torréfaction ",
                "§aBB : §fEn Attente",
                "§aExpresso : §eClassic",
                "§0§8§m----------§8§m------"
                );
    }
}
