package xyz.ubatv.kingdoms.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Rank;
import xyz.ubatv.kingdoms.rankSystem.ServerRank;
import xyz.ubatv.kingdoms.userData.UserData;
import xyz.ubatv.kingdoms.userData.UserDataManager;

import java.util.UUID;

public class ChatManager implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        UserData userData = UserDataManager.usersData.get(uuid);

        String msg = event.getMessage();

        event.setCancelled(true);

        String userKingdom = userData.getKingdom();
        String kingdomTag = "";
        if(!userKingdom.equalsIgnoreCase("none")){
            String tag = main.kingdomsTable.getTag(userKingdom);
            if(!tag.equalsIgnoreCase("none")){
                kingdomTag = "§7[§5" + main.kingdomsTable.getDisplayTag(userKingdom) + "§7] ";
            }
        }

        Rank rank = main.rankManager.getRank(player);
        ServerRank serverRank = main.rankManager.getServerRank(player);
        String rankName = main.rankManager.getRankName(rank, true);
        String serverRankName = main.rankManager.getServerRankName(serverRank, true);
        for(Player target : Bukkit.getOnlinePlayers()){
            if(rank.equals(Rank.MEMBER)){
                target.sendMessage(
                        kingdomTag + "§7[" + serverRankName + "§7] §7" + player.getName() + "§8§l: §r§7" + msg);

            }else{
                target.sendMessage(
                        kingdomTag + "§7[" + serverRankName + "§7] §7[" + rankName + "§7] §7" + player.getName() + "§8§l: §r§7" + msg);
            }
        }
    }
}
