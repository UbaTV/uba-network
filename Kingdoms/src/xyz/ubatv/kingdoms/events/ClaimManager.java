package xyz.ubatv.kingdoms.events;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.userData.UserData;

import java.util.Objects;

public class ClaimManager implements Listener {

    private Main main = Main.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        UserData userData = main.mainUserData.online.get(player.getUniqueId());
        String playerKingdom = userData.getKingdom();
        Chunk chunk = event.getBlock().getChunk();
        String chunkClaim = main.kingdomUtils.getChunkClaim(chunk);
        if(chunkClaim.equalsIgnoreCase("none")
        || chunkClaim.equalsIgnoreCase(playerKingdom)) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        UserData userData = main.mainUserData.online.get(player.getUniqueId());
        String playerKingdom = userData.getKingdom();
        Chunk chunk = event.getBlock().getChunk();
        String chunkClaim = main.kingdomUtils.getChunkClaim(chunk);
        if(chunkClaim.equalsIgnoreCase("none")
        || chunkClaim.equalsIgnoreCase(playerKingdom)) return;
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        UserData userData = main.mainUserData.online.get(player.getUniqueId());

        String playerKingdom = userData.getKingdom();
        if(event.getAction().equals(Action.LEFT_CLICK_AIR)
        || event.getAction().equals(Action.RIGHT_CLICK_AIR)
        || event.getAction().equals(Action.PHYSICAL)) return;

        Chunk chunk = Objects.requireNonNull(event.getClickedBlock()).getLocation().getChunk();
        String chunkClaim = main.kingdomUtils.getChunkClaim(chunk);
        if(chunkClaim.equalsIgnoreCase("none")
        || chunkClaim.equalsIgnoreCase(playerKingdom)) return;

        Material mat = event.getClickedBlock().getType();
        if(mat.equals(Material.CHEST)
        || mat.equals(Material.ENDER_CHEST)
        || mat.equals(Material.BARREL)
        || mat.equals(Material.FURNACE)
        || mat.equals(Material.HOPPER)
        || mat.equals(Material.TRAPPED_CHEST)
        || mat.equals(Material.BLAST_FURNACE)
        || mat.equals(Material.CRAFTING_TABLE)
        || String.valueOf(mat).contains("MINECART")
        || String.valueOf(mat).contains("SHULKER_BOX")) return;
        event.setCancelled(true);
    }
}
