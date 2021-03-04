package eu.builderscoffee.expresso.buildbattle.expressos.listeners;

import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.buildbattle.BBGameManager;
import eu.builderscoffee.expresso.buildbattle.expressos.engine.types.HazarEngine;
import eu.builderscoffee.expresso.utils.BlockData;
import eu.builderscoffee.expresso.utils.Log;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.MaterialData;

public class HazarListener implements Listener {

    private final HazarEngine engine;

    public HazarListener(Main main, HazarEngine engine) {
        this.engine = engine;
        Bukkit.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // On check si on est en partie sinon on retourne
        if (Main.getBbGame().getBbState() == BBGameManager.BBState.IN_GAME) {// On init les variables avant de cancel
            final Player player = event.getPlayer();
            Block block = event.getBlock();
            Location location = event.getBlock().getLocation();
            int blockId = event.getBlock().getType().getId();
            Log.get().info("Current block " + blockId);
            BlockFace blockFace = event.getBlock().getFace(block); // On récupere la face du block
            BlockState blockState = block.getState(); // On récupère l'état du block
            // On charge l'engine et on check le block à convertir
            Log.get().info("BlockData " + BlockData.getBlockDataById(blockId).getId());
            if (engine.convertBlockdata.get(BlockData.getBlockDataById(blockId)) != null) {
                val blockData = engine.convertBlockdata.get(BlockData.getBlockDataById(blockId));

                Log.get().info("Block convert " + blockData.id);
                // On remplace le bloc à placer par celui converti
                blockState.setType(Material.getMaterial(blockData.id));
                blockState.setData(new MaterialData(Material.getMaterial(blockData.id), (byte) blockData.shortId));
                // On n'oublie pas de l'update sinon ça ne marchera pas
                blockState.update(true);
            }
        }

    }

}
