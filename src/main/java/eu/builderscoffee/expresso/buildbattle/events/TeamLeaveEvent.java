package eu.builderscoffee.expresso.buildbattle.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public final class TeamLeaveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final Player player;
    @Getter
    private final List<Player> memberList;

    public TeamLeaveEvent(Player player, List<Player> memberList) {
        this.player = player;
        this.memberList = memberList;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}