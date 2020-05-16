package xyz.ubatv.pve.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.pve.Main;

public class JoinQuitEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage("§7[§a+§7] " + player.getName());
        main.userDataManager.loadUserData(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage("§7[§c-§7] " + player.getName());
        main.userDataManager.saveUserData(player);
    }
}
