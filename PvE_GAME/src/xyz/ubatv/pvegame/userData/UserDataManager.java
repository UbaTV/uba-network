package xyz.ubatv.pvegame.userData;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.ubatv.pvegame.Main;
import xyz.ubatv.pvegame.rankSystem.Rank;

import java.util.*;

public class UserDataManager implements PluginMessageListener {

    private Main main = Main.getInstance();

    public static HashMap<UUID, UserData> userData = new HashMap<>();

    public void loadUserData(Player player){
        UUID uuid = player.getUniqueId();
        PlayerStatus playerStatus = PlayerStatus.LOBBY; // TODO Player Status change with Game Status
        userData.put(uuid,
                new UserData(main.mainUserData.getRank(uuid)
                        , playerStatus));
    }

    public void saveUserData(Player player){
        UUID uuid = player.getUniqueId();
        if(!userData.containsKey(uuid)) return;
        UserData userData = UserDataManager.userData.get(uuid);

        main.mainUserData.updateRank(uuid, userData.getRank());
        // Add coins to bank table
    }

    public ArrayList<UUID> getSpectatingPlayer(){
        ArrayList<UUID> spec = new ArrayList<>();
        for(Map.Entry<UUID, UserData> entry : UserDataManager.userData.entrySet()){
            UUID uuid = entry.getKey();
            UserData userData = entry.getValue();
            if(userData.getPlayerStatus().equals(PlayerStatus.SPECTATE)) spec.add(uuid);
        }
        return spec;
    }

    public int getNumberSpectating(){
        int spec = 0;
        for(Map.Entry<UUID, UserData> entry : UserDataManager.userData.entrySet()){
            UUID uuid = entry.getKey();
            UserData userData = entry.getValue();
            if(userData.getPlayerStatus().equals(PlayerStatus.SPECTATE)) spec++;
        }
        return spec;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if(!channel.equalsIgnoreCase("ubanetwork:userdata")) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
        String subChannel = input.readUTF();
        if(subChannel.equalsIgnoreCase("RANK_CHANGE")){
            Player target = Bukkit.getServer().getPlayer(input.readUTF());
            String rankName = input.readUTF().toUpperCase();
            Rank rank = Rank.valueOf(rankName);
            UserData userData = UserDataManager.userData.get(target.getUniqueId());
            userData.setRank(rank);
            target.setPlayerListName("§7[" + main.rankManager.getRankName(rank, true) + "§7] §7" + target.getName());
        }
    }
}
