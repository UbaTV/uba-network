package xyz.ubatv.kingdoms.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.utils.UserData;

public class EntityDamage implements Listener {

    // TODO Kingdom members can kill each other with arrows

    private Main main = Main.getInstance();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() == null) return;
        if(event.getEntityType() == null) return;
        if(event.getDamager() == null) return;
        if(event.getDamager().getType() == null) return;
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

            if(damagerKingdom.equalsIgnoreCase(playerKingdom)){
                damager.sendMessage(main.textUtils.error + "This player is a member of your kingdom.");
                event.setCancelled(true);
                return;
            }

            String[] playerAllies = main.kingdomUtils.getAllies(playerKingdom);
            for(String ally : playerAllies){
                if(ally.equalsIgnoreCase(damagerKingdom)){
                    player.sendMessage(main.textUtils.warning + "You are allied with this players kingdom.");
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
