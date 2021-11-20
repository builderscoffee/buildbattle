package eu.builderscoffee.expresso.board;

import eu.builderscoffee.api.bukkit.board.FastBoard;
import eu.builderscoffee.expresso.ExpressoBukkit;
import eu.builderscoffee.expresso.buildbattle.phase.BBPhase;
import eu.builderscoffee.expresso.utils.MessageUtils;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;

public class BaseBoard {

    private static Map<UUID, FastBoard> playerBoards = new HashMap<>();
    @Getter
    private Map<Class<? extends BBPhase>, Function<Player, List<String>>> boards = new HashMap<>();

    public void update(Player player) {
        System.out.println("Update start");
        FastBoard fb;
        if (!playerBoards.containsKey(player.getUniqueId())) {
            System.out.println("Update CheckPlayer");
            fb = new FastBoard(player);
            fb.updateTitle(MessageUtils.getMessageConfig(player).getBoard().getTitle());
            playerBoards.put(player.getUniqueId(), fb);
        }
        fb = playerBoards.get(player.getUniqueId());
        System.out.println("Update getFastBoard");

        if (Objects.nonNull(getBoards().containsKey(ExpressoBukkit.getBbGame()))
                && Objects.nonNull(getBoards().containsKey(ExpressoBukkit.getBbGame().getBuildBattleGameType())
                && Objects.nonNull(getBoards().containsKey(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase()))
                && getBoards().containsKey(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getClass()))) {
            System.out.println("Update UpdateLines");
            fb.updateLines(getBoards().get(ExpressoBukkit.getBbGame().getBuildBattleGameType().getCurrentPhase().getClass()).apply(player));
        } else {
            System.out.println("Soucis bb");
            fb.updateLines("§cY'a un soucis chef");
        }
    }

    public void remove(Player player) {
        if (playerBoards.containsKey(player.getUniqueId())) {
            playerBoards.get(player.getUniqueId()).delete();
            playerBoards.remove(player.getUniqueId());
        }
    }

    public void removeAll() {
        playerBoards.values().forEach(board -> board.delete());
        playerBoards.clear();
    }
}