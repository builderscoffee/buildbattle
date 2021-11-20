package eu.builderscoffee.expresso.board.type;

import eu.builderscoffee.expresso.board.IScoreboard;
import eu.builderscoffee.expresso.configuration.MessageConfiguration;
import eu.builderscoffee.expresso.utils.MessageUtils;
import org.bukkit.entity.Player;

public class BaseScoreboard extends IScoreboard {

    MessageConfiguration messages;

    public BaseScoreboard(Player player) {
        super(player);
        messages = MessageUtils.getMessageConfig(player);
        updateTitle(messages.getBoard().getTitle());
    }

    public void buildWaitingBoard(Player player) {
        this.updateLines("§0§8§m----------§8§m------",
                "",
                "§aEn attente d'une buildbattle",
                "",
                messages.getBoard().getServerIp(),
                "§0§8§m----------§8§m------");
    }

    @Override
    public void update(IScoreboard iScoreboard) {
        this.buildWaitingBoard(this.getPlayer());
    }
}
