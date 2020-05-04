package xyz.ubatv.hub.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location loc = player.getLocation();
    }
}
