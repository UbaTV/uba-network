package xyz.ubatv.hub.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryManager implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)){
            if(item != null && item.getType() == Material.COMPASS){

            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onArrowPickup(PlayerPickupArrowEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event){
        event.setCancelled(true);
    }
}
