package eu.builderscoffee.expresso.utils;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public class PlotUtils {

    public static Set<Plot> plots = new PlotAPI().getAllPlots();

    /***
     * Convertir une Location Plot en Location Bukkit
     * @param location
     * @return
     */
    public static Location convertPlotCenterLoc(com.intellectualcrafters.plot.object.Location location) {
        return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
    }

    /***
     * Retourne si un joueur possède un plot !
     * @param player
     * @return
     */
    public static boolean AsPlot(Player player) {
        return plots.stream().anyMatch(plot -> plot.isOwner(player.getUniqueId()));
    }

    /***
     * Teleporter un joueur à sont plot
     * @param player
     */
    public static void teleportToPlot(Player player) {
        if (AsPlot(player)) {
            val plot = plots.stream().filter(plota -> plota.isOwner(player.getUniqueId())).findAny().get();
            player.teleport(convertPlotCenterLoc(plot.getCenter()));
        }
    }
}
