package xyz.ubatv.kingdoms.silkspawner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;

public class BreakPlaceSpawner implements Listener {

    private Main main = Main.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        if(event.isCancelled()) return;

        if(player.getInventory().getItemInMainHand() != null){
            if(player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)){
                if(event.getBlock().getType() == Material.SPAWNER){
                    Block block = event.getBlock();
                    CreatureSpawner cs = (CreatureSpawner) block.getState();
                    EntityType entityType = cs.getSpawnedType();
                    ItemStack spawner = main.itemAPI.item(Material.SPAWNER, "Spawner", "§7Type: §5" + entityType.getEntityClass().getSimpleName());

                    player.getWorld().dropItem(block.getLocation().add(0.5, 0, 0.5), spawner);
                    event.setExpToDrop(0);

                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();

        if(event.isCancelled()) return;

        if(player.getInventory().getItemInMainHand() == null) return;

        if(player.getInventory().getItemInMainHand().getType() == Material.SPAWNER){
            if(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName() != null){
                if(event.getBlock().getType() == Material.SPAWNER){
                    String type = player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).substring(10);

                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        EntityType entityType = EntityType.fromName(type);
                        Block block = event.getBlockPlaced();
                        BlockState blockState = block.getState();
                        CreatureSpawner spawner = (CreatureSpawner) blockState;
                        spawner.setSpawnedType(entityType == null ? EntityType.PIG : entityType);
                        blockState.update();
                    }, 5);
                }
            }
        }
    }
}
