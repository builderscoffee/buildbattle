package eu.builderscoffee.expresso.buildbattle.expressos.listeners;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.types.HazarEngine;
import eu.builderscoffee.expresso.utils.Log;
import eu.builderscoffee.expresso.utils.blocks.BlockData;
import eu.builderscoffee.expresso.utils.blocks.BoundingBox;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Stairs;
import org.bukkit.util.Vector;

public class HazarListener implements Listener {

    private final HazarEngine engine;

    public HazarListener(Main main, HazarEngine engine) {
        this.engine = engine;
        Bukkit.getServer().getPluginManager().registerEvents(this, main);
    }

    /*
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // On check si on est en partie sinon on retourne
        if (Main.getBbGame().getBbState() == BBGameManager.BBState.IN_GAME) {
            // On recupère le bloc actuelle
            Block block = event.getBlock();
            int blockId = event.getBlock().getType().getId();
            BlockFace blockFace = event.getBlock().getFace(block);
            BlockState blockState = block.getState();
            Log.get().info("Current block " + blockId);
            // On check via l'engine le block à convertir
            //Log.get().info("BlockData " + BlockData.getBlockDataById(blockId).getId());
            if (engine.convertBlockdata.containsKey(BlockData.getBlockDataById(blockId))) {
                // Le block à convertir
                final BlockData blockData = (BlockData) engine.convertBlockdata.get(BlockData.getBlockDataById(blockId));
                Log.get().info("Block convert " + blockData.id);
                // On récupère le material du block converti
                Material material = Material.getMaterial(blockData.id);
                blockState.setType(material);
                MaterialData blockMaterialData = new MaterialData(blockData.id, (byte) blockData.shortId);

                if(blockState.getData() instanceof Directional) {
                    ((Directional) blockMaterialData).setFacingDirection(blockFace);
                }

                blockState.setData(blockMaterialData);
                // On n'oublie pas de l'update sinon ça ne marchera pas
                blockState.update(true);
            }
        }

    }
    */

    @EventHandler
    public void onBlock(BlockPlaceEvent event) {
        // Check si la partie est commencer !
        if (Main.getBbGame().getBbState() == BBGameManager.BBState.IN_GAME) {
            // Get joueur et face du block
            val player = event.getPlayer();
            val face = event.getBlockAgainst().getFace(event.getBlock());
            val block = event.getBlock();
            val blockId = event.getBlock().getType().getId();

            Log.get().info("Current block " + blockId);

            // On check via l'engine le block à convertir
            if (engine.convertBlockdata.containsKey(BlockData.getBlockDataById(blockId))) {

                // Le block à convertir
                val blockData = (BlockData) engine.convertBlockdata.get(BlockData.getBlockDataById(blockId));
                Log.get().info("Block convert " + blockData.id);

                // On récupère le material du block converti
                Material material = Material.getMaterial(blockData.id);

                // Calcul pour savoir ou le joueur regarde
                val box = new BoundingBox(event.getBlockAgainst().getLocation().toVector().add(new Vector(0.5D, 0.5D, 0.5D)), -0.5D, 0.5D, -0.5D, 0.5D, -0.5D, 0.5D);
                val intersection = box.getIntersection(player.getEyeLocation().toVector(), player.getEyeLocation()
                        .add(player.getEyeLocation().getDirection().multiply(100)).toVector());

                // Calcul pour savoir la face du joueur
                double yaw = (player.getLocation().getYaw() - 180) % 360;
                if (yaw < 0) {
                    yaw += 360.0;
                }
                BlockFace stairsFace = null;
                if (yaw > 315 || yaw <= 45) {
                    stairsFace = BlockFace.NORTH;
                } else if (yaw > 45 && yaw <= 135) {
                    stairsFace = BlockFace.EAST;
                } else if (yaw > 135 && yaw <= 225) {
                    stairsFace = BlockFace.SOUTH;
                } else if (yaw > 225 && yaw <= 315) {
                    stairsFace = BlockFace.WEST;
                }

                // Calcul du y du block ou le joueur regarde
                double y = (int) ((intersection.getY() - (int) (intersection.getY())) * 100) / 100.0;


                // Récupère le block
                val blockPlaced = event.getBlockPlaced();

                // Met l'escalier
                blockPlaced.setType(material);

                // Récupère le blockstate
                val state = blockPlaced.getState();

                if (blockData.blockCategory.equals(BlockData.BlockCategory.STAIRS)) {
                    // Récupère le data du blockstate
                    val stairs = (Stairs) state.getData();
                    // Met la dirrection di l'escalier
                    stairs.setFacingDirection(stairsFace);
                    // Met l'escalier en haut ou en bas selon le cacul de la ou le joueur regarde
                    stairs.setInverted(y > 0.5 || face == BlockFace.DOWN);
                    // Met la data dans le state
                    state.setData(stairs);
                }

                // Update le state
                state.update();
            }
        }
    }

}
