package xyz.ubatv.pve.rankSystem;

import org.bukkit.entity.Player;
import xyz.ubatv.pve.Main;

import java.util.UUID;

public class RankManager {

    private Main main = Main.getInstance();

    public boolean hasPermission(Player player, Permissions permission){
        Rank rank = getRank(player);
        int rankTier = getRankTier(rank);
        switch (permission){
            case SET_LOCATION:
                return rank.equals(Rank.CEO);
            default:
                return false;
        }
    }

    public int getRankTier(Rank rank){
        if(rank.equals(Rank.MEMBER)) return 1;
        else if(rank.equals(Rank.VIP)) return 2;
        else if(rank.equals(Rank.MVP)) return 3;
        else if(rank.equals(Rank.BUILDER)) return 4;
        else if(rank.equals(Rank.ADMIN)) return 5;
        else if(rank.equals(Rank.CEO)) return 6;
        else return 1;
    }

    public String getRankName(Rank rank, boolean color){
        switch (rank){
            case CEO:
                return color ? "§5§lCEO" : "CEO";
            case ADMIN:
                return color ? "§4Admin" : "Admin";
            case BUILDER:
                return color ? "§2Builder" : "Builder";
            case MVP:
                return color ? "§bMVP" : "MVP";
            case VIP:
                return color ? "§aVIP" : "VIP";
            case MEMBER:
            default:
                return color ? "§fMember" : "Member";
        }
    }

    public boolean isStaff(Player player){
        Rank rank = getRank(player);
        return rank.equals(Rank.CEO) || rank.equals(Rank.ADMIN);
    }

    public Rank getRank(Player player){
        return main.userDataManager.userData.get(player.getUniqueId()).getRank();
    }

    public Rank getRank(UUID uuid){
        return main.userDataManager.userData.get(uuid).getRank();
    }
}
