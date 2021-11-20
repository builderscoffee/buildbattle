package eu.builderscoffee.expresso.board.type;

import eu.builderscoffee.expresso.board.IScoreboard;
import org.bukkit.entity.Player;

public class ExpressoScoreboard extends IScoreboard {

    public ExpressoScoreboard(Player player) {
        super(player);
    }

    public void buildWaitingBoard(Player player) {
        // message = MessageUtils.getMessageConfig(this.get);
        this.updateLines("§0§8§m----------§8§m------",
                //getTheme(),
                //InlineString("§aSaison : ", message.getBoard().getSeasonName()),
                //InlineString("§aExpresso : §f", ExpressoBukkit.getBbGame().getBuildBattleGameType().getName()),
                "§aThème : " + "§f§kLait",
                "§aTimer : §fEn Attente",
                "",
                //message.getBoard().getServerIp(),
                "§0§8§m----------§8§m------");
    }

    @Override
    public void update(IScoreboard iScoreboard) {

    }
}
