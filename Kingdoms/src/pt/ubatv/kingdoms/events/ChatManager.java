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
            String userKingdom = userData.getKingdom();
            String kingdomTag;
            if(!userKingdom.equalsIgnoreCase("none")){
                String tag = main.kingdomsTable.getTag(userKingdom);
                if(!tag.equalsIgnoreCase("none")){
                    kingdomTag = "§7[§5" + main.kingdomsTable.getDisplayTag(userKingdom) + "§7] ";
                }else{
                    kingdomTag = "";
                }
            }else{
                kingdomTag = "";
            }
            for(Player target : Bukkit.getOnlinePlayers()){
                target.sendMessage(
                        kingdomTag +
                        "§7[" + main.rankManager.getRankName(userData.getRank(), true) + "§7] §7"
                        + player.getName() + "§8§l: §r§7" + msg);
            }
        }else{
            player.sendMessage(main.textUtils.error + "You are muted.");
        }
    }
}
