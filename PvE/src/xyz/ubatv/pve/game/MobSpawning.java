package xyz.ubatv.pve.game;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.userData.UserData;
import xyz.ubatv.pve.userData.UserDataManager;

public class MobSpawning implements Listener {

    private Main main = Main.getInstance();

    public boolean mobSpawn = false;
    public int mobsKilled = 0;
    public int killReward = 20;

    public void onMobSpawn(CreatureSpawnEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof Monster){
            if(mobSpawn){
                event.setCancelled(false);
            }else{
                event.setCancelled(true);
            }
        }
    }

    public void onMobDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof Monster){
            mobsKilled++;
            Entity killer = ((Monster) entity).getKiller();
            if(killer != null){
                if(UserDataManager.userData.containsKey(killer.getUniqueId())){
                    UserData userData = UserDataManager.userData.get(killer.getUniqueId());
                    userData.addKills(1);
                    userData.addGameCoins(killReward);
                }
            }
        }
    }

    public void spawnMobs(){
        for(Location loc : main.locationYML.mobSpawn){
            loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        }
    }

    public int getRoundSpawnDelay(int round){
        if(round == 1) return 5;
        else if(round == 2) return 4;
        else if(round == 3) return 3;
        else if(round == 4) return 2;
        else if(round == 5) return 1;
        return 1;
    }
}
