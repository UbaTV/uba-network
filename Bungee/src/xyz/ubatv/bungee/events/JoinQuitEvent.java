package xyz.ubatv.bungee.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.ubatv.bungee.Main;

public class JoinQuitEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PostLoginEvent event){
        ProxiedPlayer player = event.getPlayer();
        main.mainUserData.createUser(player);
        main.userDataManager.loadUserData(player);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event){
        ProxiedPlayer player = event.getPlayer();
        main.userDataManager.saveUserData(player);
    }
}
