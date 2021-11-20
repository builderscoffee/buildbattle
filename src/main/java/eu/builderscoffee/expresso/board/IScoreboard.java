package eu.builderscoffee.expresso.board;

import eu.builderscoffee.api.bukkit.board.FastBoard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class IScoreboard extends FastBoard {

    public static Map<UUID, IScoreboard> boards = new HashMap<>();

    public IScoreboard(Player player) {
        super(player);
    }

    public abstract void update(IScoreboard iScoreboard);

}