package eu.builderscoffee.expresso.utils;

import com.intellectualcrafters.jnbt.CompoundTag;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.RunnableVal;
import com.intellectualcrafters.plot.util.SchematicHandler;
import com.intellectualcrafters.plot.util.TaskManager;
import com.intellectualcrafters.plot.util.UUIDHandler;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class PlotUtils {

    public static Set<Plot> allPlots = new PlotAPI().getAllPlots();

    /***
     * Exporter toutes les schématics dans le dossier
     */
    public boolean exportAllSchematics(String namingScheme, @NonNull String outputDir, final Runnable ifSuccess) {
        TaskManager.runTask(new Runnable() {
            @Override
            public void run() {
                if (allPlots.isEmpty()) {
                    System.out.println("Aucun plot à schématiser");
                    TaskManager.runTask(ifSuccess);
                    return;
                }
                Iterator<Plot> i = allPlots.iterator();
                final Plot plot = i.next();
                i.remove();
                String o = UUIDHandler.getName(plot.owner);
                if (o == null) o = "unknown";
                final String name;
                if (namingScheme == null) name = plot.getId().x + ";" + plot.getId().y + ',' + plot.getArea() + ',' + o;
                else
                    name = namingScheme.replaceAll("%owner%", o).replaceAll("%id%", plot.getId().toString()).replaceAll("%idx%", plot.getId().x + "").replaceAll("%idy%", plot.getId().y + "")
                            .replaceAll("%world%", plot.getArea().toString());

                final Runnable THIS = this;
                SchematicHandler.manager.getCompoundTag(plot, new RunnableVal<CompoundTag>() {
                    @Override
                    public void run(final CompoundTag value) {
                        if (value == null) Main.getBbGame().broadcast("§7 - Skipped plot §c" + plot.getId());
                        else TaskManager.runTaskAsync(() -> {
                            Main.getBbGame().broadcast("§6ID: " + plot.getId());
                            boolean result = SchematicHandler.manager
                                    .save(value, outputDir + File.separator + name + ".schematic");
                            if (!result) Main.getBbGame().broadcast("§7 - Failed to save §c" + plot.getId());
                            else Main.getBbGame().broadcast("§7 - §a  success: " + plot.getId());
                            TaskManager.runTask(() -> THIS.run());
                        });
                    }
                });
            }
        });
        return true;
    }

        /***
         * Coller une schématique sur un plot
         * @param schematicLocation
         * @param plot
         */
        public void pasteSchematic (String schematicLocation, Plot plot){
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
        public boolean isMerged (Plot plot){
            return plot.getConnectedPlots().size() < 1;
        }

        /***
         * Convertir une Location Plot en Location Bukkit
         * @param location
         * @return
         */
        public static Location convertPlotCenterLoc (com.intellectualcrafters.plot.object.Location location){
            return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
        }

        /***
         * Convertir une Location Bukkit en Plot
         * @param location
         * @return
         */
        public static com.intellectualcrafters.plot.object.Location convertBukkitLoc (Location location){
            return new com.intellectualcrafters.plot.object.Location(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }

        /***
         * Retourne la position d'un plot
         * @param plot
         * @return
         */
        public static int getPlotsPos (Plot plot){
            return new ArrayList<>(allPlots).indexOf(plot);
        }

        /***
         * Retourne le plot par rapport a la position dans la list
         * @param
         * @return
         */
        public static Plot getPlotsByPos ( int in){
            return new ArrayList<>(allPlots).get(in);
        }
    }
