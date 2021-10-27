package eu.builderscoffee.expresso.buildbattle.games.expressos.listeners;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BuildBattleManager;
import eu.builderscoffee.expresso.buildbattle.games.expressos.engine.HazarEngine;
import eu.builderscoffee.expresso.utils.Log;
import eu.builderscoffee.expresso.utils.blocks.BlockData;
import eu.builderscoffee.expresso.utils.blocks.BoundingBox;
import eu.builderscoffee.expresso.utils.blocks.LogConverter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;
import org.bukkit.util.Vector;

public class HazarListener implements Listener {

    private final HazarEngine engine;

    public HazarListener(Main main, HazarEngine engine) {
        this.engine = engine;
        Bukkit.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onBlock(BlockPlaceEvent event) {
        // Check si la partie est commencer !
        if (Main.getBbGame().getGameState() == BuildBattleManager.GameState.IN_GAME) {
            // Get joueur et face du block
            val player = event.getPlayer();
            val face = event.getBlockAgainst().getFace(event.getBlock());
            val block = event.getBlock();
            val blockId = event.getBlock().getType().getId();
            // Copy de la data en cas de BlockCatégorie Log
            val blockOldData = event.getBlock().getData();

            Log.get().info("Current block " + blockId + ":" + block.getData());

            /* Dans le cas ou le block est un matérial de type LOG on définis un byte de 0-3 suivant sont type
            pour tromper le retour du BlockData
            */
            if (blockId == 17 && block.getData() <= 4) {
                block.setData((byte) LogConverter.getLogTypeByShort(block.getData()).Id);
            }

            // Dans le cas ou c'est des STAIRS , ne pas chercher par le short

            // Réaliser une recherche par l'id puis si trouvé une correspondance checker via l'id et le short en
            // sachant qui si certaines catégorie bug si on cherche via le short
            // Catégorie STAIR , LOG

            // On check via l'engine le block à convertir
            System.out.println("BlockData Size " + engine.convertBlockdata.size());
            if (engine.convertBlockdata.containsKey(BlockData.getBlockDataByIdAndShort(blockId, block.getData()))) {

                // Le block à convertir
                val blockData = (BlockData) engine.convertBlockdata.get(BlockData.getBlockDataById(blockId));
                Log.get().info("Block convert " + blockData.id + ":" + blockData.shortId);

                // On récupère le material data du block converti
                MaterialData materialData = new MaterialData(blockData.id, (byte) blockData.shortId);

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
                blockPlaced.setType(materialData.getItemType());

                // Récupère le blockstate
                val state = blockPlaced.getState();

                // Si la catégorie est les escaliers on applique une méthode différente

                if (blockData.blockCategory.equals(BlockData.BlockCategory.STAIRS)) {
                    // Récupère le data du blockstate
                    val stairs = (Stairs) state.getData();
                    // Met la dirrection di l'escalier
                    stairs.setFacingDirection(stairsFace);
                    // Met l'escalier en haut ou en bas selon le cacul de la ou le joueur regarde
                    stairs.setInverted(y > 0.5 || face == BlockFace.DOWN);
                    // Met la data dans le state
                    state.setData(stairs);
                } else if (blockData.blockCategory.equals(BlockData.BlockCategory.LOG)) {
                    // On récupère avec la nouvelle data du block placé le type de block
                    val oldLogData = LogConverter.getLogTypeByShort(blockOldData);
                    // On met en cache la data du block
                    oldLogData.setDataId(blockOldData);
                    // On récupère le data du block à placer
                    val newLogData = LogConverter.getLogTypeByShort(blockData.shortId);
                    // On met en cache la data du block
                    newLogData.setDataId(blockData.shortId);
                    // On applique une nouvelle data sur le block a partir du converter
                    state.setData(LogConverter.ConvertLogType(oldLogData, newLogData));
                }
                // Sinon on applique simplement la data
                else {
                    state.setData(materialData);
                }

                // Update le state
                state.update();
            }
        }
    }
}
