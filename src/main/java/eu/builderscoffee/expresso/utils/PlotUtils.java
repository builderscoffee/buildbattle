package eu.builderscoffee.expresso.utils;

import com.fasterxml.uuid.Generators;
import com.intellectualcrafters.jnbt.CompoundTag;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.RunnableVal;
import com.intellectualcrafters.plot.util.SchematicHandler;
import com.intellectualcrafters.plot.util.TaskManager;
import eu.builderscoffee.commons.common.data.DataManager;
import eu.builderscoffee.commons.common.data.tables.BuildbattleEntity;
import eu.builderscoffee.commons.common.data.tables.ProfilEntity;
import eu.builderscoffee.commons.common.data.tables.SchematicsEntity;
import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.configuration.SettingsConfiguration;
import lombok.NonNull;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;


public class PlotUtils {

    public static Set<Plot> allPlots = new PlotAPI().getAllPlots();
    SettingsConfiguration settings = Main.getSettings();

    /***
     * Convertir une Location Plot en Location Bukkit
     * @param location
     * @return
     */
    public static Location convertPlotCenterLoc(com.intellectualcrafters.plot.object.Location location) {
        return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ());
    }

    /***
     * Convertir une Location Bukkit en Plot
     * @param location
     * @return
     */
    public static com.intellectualcrafters.plot.object.Location convertBukkitLoc(Location location) {
        return new com.intellectualcrafters.plot.object.Location(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /***
     * Retourne la position d'un plot
     * @param plot
     * @return
     */
    public static int getPlotsPos(Plot plot) {
        return new ArrayList<>(allPlots).indexOf(plot);
    }

    /***
     * Retourne le plot par rapport a la position dans la list
     * @param
     * @return
     */
    public static Plot getPlotsByPos(int in) {
        return new ArrayList<>(allPlots).get(in);
    }

    /***
     * Exporter toutes les schématics dans le dossier
     */
    public boolean exportAllSchematics(@NonNull String outputDir, final Runnable ifSuccess) {
        TaskManager.runTask(new Runnable() {
            @Override
            public void run() {
                // Si il n'y a plus de plot à sauvegarder , exécuter la tache IfSuccess
                if (allPlots.isEmpty()) {
                    TaskManager.runTask(ifSuccess);
                    return;
                }
                // Itérer le prochain plot
                Iterator<Plot> i = allPlots.iterator();
                final Plot plot = i.next();
                i.remove();

                // Générer un UUID basé sur le temps
                UUID uuid = Generators.timeBasedGenerator().generate();
                String name = uuid.toString();

                final Runnable THIS = this;
                SchematicHandler.manager.getCompoundTag(plot, new RunnableVal<CompoundTag>() {
                    @Override
                    public void run(final CompoundTag value) {
                        if (value == null) {
                            Main.getBbGame().broadcast("§7 - Plot suivant §c" + plot.getId());
                        } else {
                            TaskManager.runTaskAsync(() -> {
                                Main.getBbGame().broadcast("§6ID: §f" + plot.getId() + "§6UUID: §7" + name);
                                boolean result = SchematicHandler.manager
                                        .save(value, outputDir + File.separator + name + ".schematic");
                                if (!result)
                                    Main.getBbGame().broadcast("§7 - Impossible à sauvegarder §c" + plot.getId());
                                else {
                                    Main.getBbGame().broadcast("§7 - §a  sauvegarder: " + plot.getId());
                                    if (settings.getSqlMode()) {
                                        HashSet<UUID> plotsMembers = plot.getMembers();
                                        List<Player> name = new ArrayList<>();
                                        val pl = DataManager.getProfilStore().select(ProfilEntity.class).get();
                                        val bb = DataManager.getBuildbattlesStore().select(BuildbattleEntity.class).get().first();

                                        System.out.println("Player1: " + pl.toList().get(0).getName());
                                        System.out.println("Player2: " + pl.toList().get(1).getName());
                                        System.out.println("BB: " + bb.getNum());

                                        val schem = new SchematicsEntity();
                                        schem.setToken(uuid);
                                        schem.setBuildbattle(bb);
                                        schem.getProfils().add(pl.toList().get(0));
                                        schem.getProfils().add(pl.toList().get(1));

                                        DataManager.getSchematicsStore().insert(schem);
                                    }
                                }
                                TaskManager.runTask(() -> THIS.run());
                            });
                        }
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
    public void pasteSchematic(String schematicLocation, Plot plot) {
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
}
