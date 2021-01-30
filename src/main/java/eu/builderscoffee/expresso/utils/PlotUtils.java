package eu.builderscoffee.expresso.utils;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlotUtils {

    public static Set<Plot> allPlots = new PlotAPI().getAllPlots();

    /***
     * Convertir une Location Plot en Location Bukkit
     * @param location
     * @return
     */
    public static Location convertPlotCenterLoc(com.intellectualcrafters.plot.object.Location location) {
        return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
    }

    /***
     * Retourne la position d'un plot
     * @param plot
     * @return
     */
    public static int getPlotsPos(Plot plot) {
        return new ArrayList<>(allPlots).indexOf(plot);
    }
}
