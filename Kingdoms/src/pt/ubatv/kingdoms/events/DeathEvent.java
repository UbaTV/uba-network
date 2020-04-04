package pt.ubatv.kingdoms.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.utils.UserData;

public class DeathEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player death = event.getEntity();
        UserData deathData = main.userDataTable.online.get(death.getUniqueId());
        deathData.setDeaths(deathData.getDeaths() + 1);

        if(death.getKiller() instanceof Player){
            Player killer = death.getKiller();
            UserData killerData = main.userDataTable.online.get(killer.getUniqueId());
            killerData.setKills(killerData.getKills() + 1);
        }

        event.setDeathMessage("§4☠ §7" + death.getName());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        event.setRespawnLocation(main.locationYML.spawn);
    }
}