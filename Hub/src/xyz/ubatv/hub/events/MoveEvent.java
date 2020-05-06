package xyz.ubatv.hub.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.ubatv.hub.Main;

public class MoveEvent implements Listener {

    private Main main = Main.getInstance();

    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if(loc.getBlockY() <= 10) player.teleport(main.locationYML.spawn);
    }
}
