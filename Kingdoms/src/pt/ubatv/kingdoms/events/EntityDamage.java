package pt.ubatv.kingdoms.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.utils.UserData;

public class EntityDamage implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntityType().equals(EntityType.PLAYER)
                && event.getDamager().getType().equals(EntityType.PLAYER)){
            Player player = (Player) event.getEntity();
            UserData playerData = main.userDataTable.online.get(player.getUniqueId());
            String playerKingdom = playerData.getKingdom();

            if(playerKingdom.equalsIgnoreCase("none")){
                return;
            }

            Player damager = (Player) event.getDamager();
            UserData damagerData = main.userDataTable.online.get(damager.getUniqueId());
            String damagerKingdom = damagerData.getKingdom();

            if(damagerKingdom.equalsIgnoreCase("none")){
                return;
            }

            String[] playerAllies = main.kingdomUtils.getAllies(playerKingdom);
            for(String ally : playerAllies){
                if(ally.equalsIgnoreCase(damagerKingdom)){
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
