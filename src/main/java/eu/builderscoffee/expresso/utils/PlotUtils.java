package eu.builderscoffee.expresso.utils;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.RunnableVal;
import com.intellectualcrafters.plot.util.MainUtil;
import com.intellectualcrafters.plot.util.SchematicHandler;
import com.intellectualcrafters.plot.util.TaskManager;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Set;

@UtilityClass
public class PlotUtils {

    public static Set<Plot> allPlots = new PlotAPI().getAllPlots();

    /***
     * Exporter toutes les schématics dans le dossier
     */
    public void exportAllSchematics() {
        SchematicHandler handler = new PlotAPI().getSchematicHandler();
        /*
        handler.exportAll(allPlots, output, "", new BukkitRunnable() {
            @Override
            public void run() {

            }
        })
        */
    }

    /***
     * Coller une schématique sur un plot
     * @param schematicLocation
     * @param plot
     */
    public void pasteSchematic(String schematicLocation,Plot plot) {
        TaskManager.runTaskAsync(() -> {
            SchematicHandler.Schematic schematic;
                schematic = SchematicHandler.manager.getSchematic(schematicLocation);
            if (schematic == null) {
                Log.get().info("Schematic null");
                return;
            }
            SchematicHandler.manager.paste(schematic, plot, 0, plot.getArea().MIN_BUILD_HEIGHT, 0, false, new RunnableVal<Boolean>() {
                @Override
                public void run(Boolean value) {
                    if (value) {
                        Log.get().info("Schematic " + schematicLocation + " paste to " + plot.getId());

                    } else {
                        Log.get().severe("Schematic " + schematicLocation + " error paste from " + plot.getId());
                    }
                }
            });
        });
    }

    /***
     * Retour si un plot a été merge avec d'autres
     * @param plot
     * @return
     */
    public boolean isMerged(Plot plot) {
        return plot.getConnectedPlots().size() < 1;
    }

    /***
     * Convertir une Location Plot en Location Bukkit
     * @param location
     * @return
     */
    public Location convertPlotCenterLoc(com.intellectualcrafters.plot.object.Location location) {
        return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
    }

    /***
     * Convertir une Location Bukkit en Plot
     * @param location
     * @return
     */
    public com.intellectualcrafters.plot.object.Location convertBukkitLoc(Location location) {
        return new com.intellectualcrafters.plot.object.Location(location.getWorld().getName(),location.getBlockX(),location.getBlockY(),location.getBlockZ());
    }

    /***
     * Retourne la position d'un plot
     * @param plot
     * @return
     */
    public int getPlotsPos(Plot plot) {
        return new ArrayList<>(allPlots).indexOf(plot);
    }
}
