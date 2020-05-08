package xyz.ubatv.kingdoms.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.userData.UserData;

import java.util.UUID;

public class KillRewards implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onKill(EntityDeathEvent event){
        LivingEntity entity = event.getEntity();
        if(entity.getKiller() instanceof Player){
            Player killer = entity.getKiller();
            if(entity.getType().equals(EntityType.PLAYER)){
                giveReward(killer, 100);
            }else{
                giveReward(killer, (int) entity.getMaxHealth());
            }
        }
    }

    public void giveReward(Player player, int coins){
        UUID uuid = player.getUniqueId();
        UserData userData = main.mainUserData.online.get(uuid);
        int balance = userData.getCoins();
        main.mainUserData.online.get(uuid).setCoins(balance + coins);
        player.sendMessage("§6+ " + coins + main.textUtils.coinsSymbol);
    }
}
