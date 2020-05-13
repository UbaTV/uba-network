package xyz.ubatv.pvegame.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.ubatv.pvegame.Main;
import xyz.ubatv.pvegame.rankSystem.Rank;
import xyz.ubatv.pvegame.userData.UserData;
import xyz.ubatv.pvegame.userData.UserDataManager;

import java.util.UUID;

public class ChatManager implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        UserData userData = UserDataManager.userData.get(uuid);

        String msg = event.getMessage();

        event.setCancelled(true);

        Rank rank = main.rankManager.getRank(player);
        String rankName = main.rankManager.getRankName(rank, true);
        Bukkit.broadcastMessage("§7[" + rankName + "§7] §7" + player.getName() + "§8§l: §r§7" + msg);
    }
}
