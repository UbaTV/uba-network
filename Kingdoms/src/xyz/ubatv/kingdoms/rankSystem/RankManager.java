package xyz.ubatv.kingdoms.rankSystem;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.userData.UserDataManager;

import java.util.UUID;

public class RankManager {

    public Main main = Main.getInstance();

    public boolean hasPermission(Player player, Permissions permission){
        ServerRank serverRank = getServerRank(player);
        Rank rank = getRank(player);
        switch (permission){
            case ENDERCHEST:
                return !rank.equals(Rank.MEMBER)
                        || !serverRank.equals(ServerRank.WOOD);
            case CLEAR_INVENTORY:
                return !rank.equals(Rank.MEMBER)
                        || !(serverRank.equals(ServerRank.WOOD) || serverRank.equals(ServerRank.STONE));
            case PRIVATE_CHEST:
                return !rank.equals(Rank.MEMBER)
                        || !(serverRank.equals(ServerRank.WOOD) || serverRank.equals(ServerRank.STONE) || serverRank.equals(ServerRank.IRON));
            case KIT_VIP:
                return isStaff(player) || rank.equals(Rank.VIP);
            case KIT_MVP:
                return isStaff(player) || rank.equals(Rank.VIP) || rank.equals(Rank.MVP);
            case TELEPORT:
                return isStaff(player);
            case MUTE:
            case CLEAR_CHAT:
            case CLEAR_INVENTORY_OTHERS:
            case ENDERCHEST_OTHERS:
            case INVSEE:
            case GAMEMODE:
                return rank.equals(Rank.ADMIN) || rank.equals(Rank.CEO);
            // CEO PERMS
            case PLACE_LUNCHBOX:
            case SET_LOCATION:
            case TESTER:
            case RANK_MANAGEMENT:
            case ECON_MANAGEMENT:
            case HOLOGRAMS:
            case SHOP_NPC:
                return rank.equals(Rank.CEO);
            default:
                return true;
        }
    }

    public int getMaxHomes(Player player){
        ServerRank serverRank = getServerRank(player);
        if(serverRank.equals(ServerRank.DIAMOND)) return 5;
        if(serverRank.equals(ServerRank.GOLD)) return 4;
        if(serverRank.equals(ServerRank.IRON)) return 3;
        if(serverRank.equals(ServerRank.STONE)) return 2;
        return 1;
    }

    public int rankupPrice(ServerRank rank){
        if(rank.equals(ServerRank.WOOD)) return 0;
        else if(rank.equals(ServerRank.STONE)) return 50000;
        else if(rank.equals(ServerRank.IRON)) return 500000;
        else if(rank.equals(ServerRank.GOLD)) return 1500000;
        else if(rank.equals(ServerRank.DIAMOND)) return 5000000;
        return 999999999;
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

    public String getServerRankName(ServerRank serverRank, boolean color){
        switch (serverRank){
            case DIAMOND:
                return color ? "§bDiamond" : "Diamond";
            case GOLD:
                return color ? "§eGold" : "Gold";
            case IRON:
                return color ? "§fIron" : "Iron";
            case STONE:
                return color ? "§7Stone" : "Stone";
            default:
                return color ? "§6Wood" : "Wood";
        }
    }

    public ServerRank getNextRank(Player player){
        ServerRank rank = getServerRank(player);
        if(rank.equals(ServerRank.WOOD)) return ServerRank.STONE;
        else if(rank.equals(ServerRank.STONE)) return ServerRank.IRON;
        else if(rank.equals(ServerRank.IRON)) return ServerRank.GOLD;
        else if(rank.equals(ServerRank.GOLD)) return ServerRank.DIAMOND;
        return null;
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

    public ServerRank getServerRank(Player player){
        return UserDataManager.usersData.get(player.getUniqueId()).getServerRank();
    }

    public Rank getRank(Player player){
        return UserDataManager.usersData.get(player.getUniqueId()).getRank();
    }
}
