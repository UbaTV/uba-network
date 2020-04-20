package pt.ubatv.kingdoms.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

public class TreeCapitator implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void blockBreak(BlockBreakEvent event){
        if(event.isCancelled()) return;

        if(!(event.getBlock().getType() == Material.ACACIA_LOG
        || event.getBlock().getType() == Material.BIRCH_LOG
        || event.getBlock().getType() == Material.DARK_OAK_LOG
        || event.getBlock().getType() == Material.JUNGLE_LOG
        || event.getBlock().getType() == Material.OAK_LOG
        || event.getBlock().getType() == Material.SPRUCE_LOG)) return;

        ItemStack hand = event.getPlayer().getEquipment().getItemInMainHand();
        if(!(hand.getType().equals(Material.WOODEN_AXE)
        || hand.getType().equals(Material.STONE_AXE)
        || hand.getType().equals(Material.IRON_AXE)
        || hand.getType().equals(Material.GOLDEN_AXE)
        || hand.getType().equals(Material.DIAMOND_AXE))) return;

        if(!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;

        Block below = event.getBlock().getLocation().subtract(0, 1, 0).getBlock();
        if(!(below.getType().equals(Material.DIRT)
        || below.getType().equals(Material.GRASS_BLOCK))) return;

        breakTree(event.getBlock(), event.getPlayer());
    }

    public void breakTree(Block block, Player player){
        LinkedList<Block> blocks = new LinkedList<>();
        Location location = block.getLocation();
        for (int i = location.getBlockY(); i < location.getWorld().getHighestBlockYAt(location.getBlockX(),
                location.getBlockZ()); ) {
            Location l = location.add(0.0D, 1.0D, 0.0D);
            Block block2 = l.getBlock();
            if(block2.getType() == Material.ACACIA_LOG
            || block2.getType() == Material.BIRCH_LOG
            || block2.getType() == Material.DARK_OAK_LOG
            || block2.getType() == Material.JUNGLE_LOG
            || block2.getType() == Material.OAK_LOG
            || block2.getType() == Material.SPRUCE_LOG){
                blocks.add(l.getBlock());
                i++;
                continue;
            }
            break;
        }

        ItemStack hand = player.getItemOnCursor();
        for (Block block2 : blocks) {
            if (block2.breakNaturally(hand)) {
                hand.setDurability((short)(hand.getDurability() + 1));
                if (hand.getType().getMaxDurability() == hand.getDurability()) {
                    hand.setType(Material.AIR);
                    return;
                }
            }
        }
    }
}