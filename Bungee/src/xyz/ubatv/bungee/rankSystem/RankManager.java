package xyz.ubatv.bungee.rankSystem;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.ubatv.bungee.Main;

import java.util.UUID;

public class RankManager {

    private Main main = Main.getInstance();

    public boolean hasPermission(ProxiedPlayer player, Permissions permission){
        Rank rank = getRank(player);
        int rankTier = getRankTier(rank);
        switch (permission){
            case RANK_CHANGE:
                return rankTier >= getRankTier(Rank.CEO);
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

    public boolean isStaff(ProxiedPlayer player){
        Rank rank = getRank(player);
        return rank.equals(Rank.CEO) || rank.equals(Rank.ADMIN);
    }

    public Rank getRank(ProxiedPlayer player){
        return main.mainUserData.getRank(player.getUniqueId());
    }

    public Rank getRank(UUID uuid){
        return main.mainUserData.getRank(uuid);
    }

}
