package pt.ubatv.kingdoms.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.utils.UserData;

import java.util.UUID;

public class ChatManager implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        UserData userData = main.userDataTable.online.get(uuid);

        String msg = event.getMessage();

        event.setCancelled(true);

        if(!userData.isMute()){
            for(Player target : Bukkit.getOnlinePlayers()){
                target.sendMessage("§7[" + main.rankManager.getRankName(userData.getRank(), true) + "§7] §7"
                        + player.getName() + "§8§l: §r§7" + msg);
            }
        }else{
            player.sendMessage(main.textUtils.error + "You are muted.");
        }
    }
}
