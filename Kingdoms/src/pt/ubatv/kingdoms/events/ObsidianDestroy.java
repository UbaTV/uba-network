package pt.ubatv.kingdoms.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import pt.ubatv.kingdoms.Main;

import java.util.HashMap;

public class ObsidianDestroy implements Listener {

    private Main main = Main.getInstance();

    private int explosionRadius = 1;
    private int maxHealth = 10;
    private int explosionDamage = 1;
    private HashMap<Location,Integer> obsidianHealth = new HashMap<>();

    @EventHandler
    public void onExplode(EntityExplodeEvent event){
        if(event.getLocation().getBlock().getType() == Material.WATER) return;
        if(event.getEntityType() == EntityType.ENDER_DRAGON) return;

        Location loc = event.getLocation();

        for(int x = explosionRadius * -1; x <= explosionRadius; x++){
            for(int y = explosionRadius * -1; y <= explosionRadius; y++){
                for(int z = explosionRadius * -1; z <= explosionRadius; z++){
                    Block block = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    if(block.getType().equals(Material.OBSIDIAN)){
                        if(obsidianHealth.containsKey(block.getLocation())){
                            int damage = obsidianHealth.get(block.getLocation());
                            if(damage <= explosionDamage){
                                obsidianHealth.remove(block.getLocation());
                                block.setType(Material.AIR);
                            }else{
                                obsidianHealth.put(block.getLocation(), damage - explosionDamage);
                            }
                        }else{
                            obsidianHealth.put(block.getLocation(), maxHealth - explosionDamage);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void checkDamage(PlayerInteractEvent event){
        if(event.getHand() != EquipmentSlot.HAND) return;
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemOnCursor().getType().equals(Material.STICK)){
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if(obsidianHealth.containsKey(block.getLocation())){
                int damage = obsidianHealth.get(block.getLocation());
                player.sendMessage(main.textUtils.right + "§7Obsidian Health§8: §5" + damage + "§8/§510");
            }else{
                player.sendMessage(main.textUtils.right + "§7Obsidian Health§8: §510§8/§510");
            }
        }
    }
}
