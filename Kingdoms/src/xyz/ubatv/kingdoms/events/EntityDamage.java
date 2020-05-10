package xyz.ubatv.kingdoms.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.userData.UserData;
import xyz.ubatv.kingdoms.userData.UserDataManager;

public class EntityDamage implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntityType().equals(EntityType.PLAYER)
                && event.getDamager().getType().equals(EntityType.PLAYER)){
            Player player = (Player) event.getEntity();
            UserData playerData = UserDataManager.usersData.get(player.getUniqueId());
            String playerKingdom = playerData.getKingdom();

            if(playerKingdom.equalsIgnoreCase("none")) return;

            if(event.getDamager().getType() == EntityType.ARROW){
                Arrow arrow = (Arrow) event.getDamager();
                if(arrow.getShooter() instanceof Player){
                    Player shooter = (Player) arrow.getShooter();
                    UserData shooterData = UserDataManager.usersData.get(shooter.getUniqueId());
                    String shooterKingdom = shooterData.getKingdom();
                    if(shooterKingdom.equalsIgnoreCase(playerKingdom)){
                        shooter.sendMessage(main.textUtils.error + "This player is a member of your kingdom.");
                        event.setCancelled(true);
                        return;
                    }
                }
            }

            if(!event.getDamager().getType().equals(EntityType.PLAYER)) return;

            Player damager = (Player) event.getDamager();
            UserData damagerData = UserDataManager.usersData.get(damager.getUniqueId());
            String damagerKingdom = damagerData.getKingdom();

            if(damagerKingdom.equalsIgnoreCase("none")) return;

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
