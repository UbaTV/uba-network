package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

import java.util.HashMap;

public class KingdomUtils {

    private Main main = Main.getInstance();

    public static HashMap<Player,String> invites = new HashMap<>();

    public String[] getMembers(String kingdomName){
        return main.textUtils.stringToList(main.kingdomsTable.getMembers(kingdomName));
    }

    public void broadcastKingdom(String kingdomName, String msg){
        for(String playerName : getMembers(kingdomName)){
            Player target = Bukkit.getServer().getPlayer(playerName);
            if(target != null){
                target.sendMessage(main.textUtils.right + "" + msg);
            }
        }
    }
}
