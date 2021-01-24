package eu.builderscoffee.expresso.buildbattle.events;

import lombok.Data;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
public class CompetitorJoinEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private Player competitor;

    public CompetitorJoinEvent(Player competitor) {
        super();
        this.competitor = competitor;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public String getEventName() {
        return "CompetitorEvent";
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
