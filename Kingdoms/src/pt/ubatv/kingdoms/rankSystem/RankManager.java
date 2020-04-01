package pt.ubatv.kingdoms.rankSystem;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

import java.util.UUID;

public class RankManager {

    public Main main = Main.getInstance();

    public boolean hasPermission(Player player, Permissions permission){
        Rank rank = getRank(player);
        int rankTier = getRankTier(rank);
        switch (permission){
            case CLEAR_INVENTORY:
            case PRIVATE_CHEST:
                return rankTier > 1;
            case CLEAR_CHAT:
                return rankTier > 7;
            case SET_LOCATION:
            case TESTER:
            case RANK_MANAGEMENT:
            case ECON_MANAGEMENT:
                return rankTier > 8;
            default:
                return true;
        }
    }

    public int getRankTier(Rank rank){
        if(rank.equals(Rank.WOOD)) return 1;
        if(rank.equals(Rank.STONE)) return 2;
        if(rank.equals(Rank.IRON)) return 3;
        if(rank.equals(Rank.GOLD)) return 4;
        if(rank.equals(Rank.DIAMOND)) return 5;
        if(rank.equals(Rank.VIP)) return 6;
        if(rank.equals(Rank.MVP)) return 7;
        if(rank.equals(Rank.ADMIN)) return 8;
        if(rank.equals(Rank.CEO)) return 9;
        return 1;
    }

    public boolean isStaff(Player player){
        Rank rank = getRank(player);
        switch (rank){
            case CEO:
            case ADMIN:
                return true;
            default:
                return false;
        }
    }

    public Rank getRank(Player player){
        return main.userDataTable.online.get(player.getUniqueId()).getRank();
    }

    public Rank getRank(UUID uuid){
        return main.userDataTable.online.get(uuid).getRank();
    }
}
