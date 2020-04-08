package pt.ubatv.kingdoms.commands.kingdoms;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KingdomUtils {

    private Main main = Main.getInstance();

    public static HashMap<String,ArrayList<Chunk>> kingdomsChunks = new HashMap<>();
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

    public ArrayList<Chunk> getKingdomClaims(String kingdomName){
        if(kingdomsChunks.containsKey(kingdomName)){
            return kingdomsChunks.get(kingdomName);
        }
        return new ArrayList<>();
    }

    public int getNumberClaims(String kingdomName){
        ArrayList<Chunk> kingdomClaims = getKingdomClaims(kingdomName);
        if(kingdomClaims.size() != 0) return kingdomClaims.size();
        return 0;
    }

    public int getKingdomMaxClaims(String kingdomName){
        return getSize(kingdomName) * 5;
    }

    public int getOnlineMembers(String kingdomName){
        int i = 0;
        for(String member : getMembers(kingdomName)) {
            if(Bukkit.getServer().getPlayer(member) != null) i++;
        }
        return i;
    }

    public String getChunkClaim(Chunk chunk){
        for(Map.Entry<String, ArrayList<Chunk>> entry : kingdomsChunks.entrySet()){
            String kingdomName = entry.getKey();
            ArrayList<Chunk> claimedChunks = entry.getValue();
            for(Chunk chunk1 : claimedChunks){
                if(chunk.equals(chunk1)){
                    return kingdomName;
                }
            }
        }
        return "none";
    }

    public void addClaim(String kingdomName, Chunk chunk){
        ArrayList<Chunk> kingdomChunks = getKingdomClaims(kingdomName);
        kingdomChunks.add(chunk);
        kingdomsChunks.put(kingdomName, kingdomChunks);
    }

    public void removeClaim(String kingdomName, Chunk chunk){
        ArrayList<Chunk> kingdomChunks = getKingdomClaims(kingdomName);
        kingdomChunks.remove(chunk);
        kingdomsChunks.put(kingdomName, kingdomChunks);
    }

    public void broadcastKingdom(String kingdomName, String msg){
        for(String playerName : getMembers(kingdomName)){
            Player target = Bukkit.getServer().getPlayer(playerName);
            if(target != null){
                target.sendMessage(main.textUtils.right + "" + msg);
            }
        }
    }

    public int getSize(String kingdomName){
        String[] members = getMembers(kingdomName);
        int i = 0;
        for(String member : members){
            i++;
        }
        return i;
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
        names.add("spawn");
        names.add("lobby");
        names.add("hub");
        names.add("hud");
        return names;
    }
}
