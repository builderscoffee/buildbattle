package eu.builderscoffee.expresso.buildbattle.expressos.listeners;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HazarListener implements Listener {

    public HazarListener(Main main) {
        Bukkit.getServer().getPluginManager().registerEvents(this,main);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!(Main.getBbGame().getBbState() == BBGameManager.BBState.IN_GAME)) {
            return;
        }
        //TODO SOME STUFF
    }
}
