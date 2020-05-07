package xyz.ubatv.hub.userData;

import org.bukkit.entity.Player;
import xyz.ubatv.hub.Main;

import java.util.HashMap;
import java.util.UUID;

public class UserDataManager {

    private Main main = Main.getInstance();

    public HashMap<UUID, UserData> userData = new HashMap<>();

    public void loadUserData(Player player){
        UUID uuid = player.getUniqueId();
        userData.put(uuid,
                new UserData(main.mainUserData.getRank(uuid)));
    }

    public void saveUserData(Player player){
        UUID uuid = player.getUniqueId();
        UserData userData = this.userData.get(uuid);

        main.mainUserData.updateRank(uuid, userData.getRank());
    }
}
