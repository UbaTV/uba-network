package xyz.ubatv.hub.userData;

import org.bukkit.entity.Player;
import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Rank;

import java.util.HashMap;
import java.util.UUID;

public class UserDataManager {

    private Main main = Main.getInstance();

    public HashMap<UUID, UserData> userData = new HashMap<>();

    public void loadUserData(Player player){
        UUID uuid = player.getUniqueId();
        userData.put(uuid,
                new UserData(Rank.ADMIN, false)); // TODO
    }
}
