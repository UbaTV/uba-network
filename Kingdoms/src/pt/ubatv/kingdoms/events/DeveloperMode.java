package pt.ubatv.kingdoms.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class DeveloperMode implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void noHunger(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void alwaysFly(PlayerMoveEvent event){
        event.getPlayer().setAllowFlight(true);
    }
}
