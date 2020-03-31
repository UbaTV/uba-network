package pt.ubatv.kingdoms.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pt.ubatv.kingdoms.Main;

import java.util.UUID;

public class JoinQuitEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        main.userDataTable.createUser(uuid);
        main.userDataTable.loadUserData(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        main.userDataTable.online.remove(uuid);
    }
}
