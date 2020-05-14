package xyz.ubatv.kingdoms.userData;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Rank;

import java.util.HashMap;
import java.util.UUID;

public class UserDataManager implements PluginMessageListener {

    private Main main = Main.getInstance();

    public static HashMap<UUID, UserData> usersData = new HashMap<>();

    public void loadUserData(Player player){
        UUID uuid = player.getUniqueId();
        usersData.put(uuid,
                new UserData(main.mainUserData.getRank(uuid),
                        main.kingdomsUserData.getServerRank(uuid),
                        main.mainBank.getCoins(uuid),
                        main.kingdomsUserData.getKills(uuid),
                        main.kingdomsUserData.getDeaths(uuid),
                        main.kingdomsUserData.getKingdom(uuid)));
    }

    public void saveUserData(Player player){
        UUID uuid = player.getUniqueId();
        UserData userData = usersData.get(uuid);

        main.mainUserData.updateRank(uuid, userData.getRank());
        main.mainBank.updateCoins(uuid, userData.getCoins());
        main.kingdomsUserData.updateKills(uuid, userData.getKills());
        main.kingdomsUserData.updateDeaths(uuid, userData.getDeaths());
        main.kingdomsUserData.updateKingdom(uuid, userData.getKingdom());
        main.kingdomsUserData.updateServerRank(uuid, userData.getServerRank());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(!channel.equalsIgnoreCase("ubanetwork:userdata")) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
        String subChannel = input.readUTF();
        if(subChannel.equalsIgnoreCase("RANK_CHANGE")){
            Player target = Bukkit.getServer().getPlayer(input.readUTF());
            Rank rank = Rank.valueOf(input.readUTF().toUpperCase());
            UserData userData = usersData.get(target.getUniqueId());
            String rankName = main.rankManager.getRankName(rank, true);
            String serverRankName = main.rankManager.getServerRankName(userData.getServerRank(), true);
            userData.setRank(rank);
            if(rank.equals(Rank.MEMBER)){
                target.setPlayerListName("§7[" + serverRankName + "§7] §7" + target.getName());
            }else{
                target.setPlayerListName("§7[" + serverRankName + "§7] §7" +"§7[" + rankName + "§7] §7" + target.getName());
            }
        }
    }
}
