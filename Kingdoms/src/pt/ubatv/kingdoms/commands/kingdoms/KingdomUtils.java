package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

import java.util.ArrayList;
import java.util.HashMap;

public class KingdomUtils {

    private Main main = Main.getInstance();

    public static HashMap<Player,String> invites = new HashMap<>();
    public static HashMap<String,String[]> allyInvite = new HashMap<>();

    public String[] getMembers(String kingdomName){
        return main.textUtils.stringToList(main.kingdomsTable.getMembers(kingdomName));
    }

    public String[] getAllies(String kingdomName){
        return main.textUtils.stringToList(main.kingdomsTable.getAllies(kingdomName));
    }

    public String[] getEnemies(String kingdomName){
        return main.textUtils.stringToList(main.kingdomsTable.getEnemies(kingdomName));
    }

    public void broadcastKingdom(String kingdomName, String msg){
        for(String playerName : getMembers(kingdomName)){
            Player target = Bukkit.getServer().getPlayer(playerName);
            if(target != null){
                target.sendMessage(main.textUtils.right + "" + msg);
            }
        }
    }

    public ArrayList<String> bannedNames(){
        ArrayList<String> names = new ArrayList<>();
        names.add("wood");
        names.add("stone");
        names.add("iron");
        names.add("gold");
        names.add("vip");
        names.add("mvp");
        names.add("admin");
        names.add("ceo");
        names.add("nigga");
        names.add("negro");
        names.add("faggs");
        names.add("fag");
        names.add("fags");
        names.add("nigas");
        names.add("gay");
        names.add("gai");
        names.add("accept");
        names.add("deny");
        return names;
    }
}
