package pt.ubatv.kingdoms.rankSystem;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import pt.ubatv.kingdoms.Main;

import java.util.UUID;

public class RankManager {

    public Main main = Main.getInstance();

    public boolean hasPermission(Player player, Permissions permission){
        Rank rank = getRank(player);
        int rankTier = getRankTier(rank);
        switch (permission){
            // STONE PERMS
            case ENDERCHEST:
                return rankTier > 1;
            // IRON PERMS
            case CLEAR_INVENTORY:
                return rankTier > 2;
            // GOLD PERMS
            case PRIVATE_CHEST:
                return rankTier > 3;
            // MVP PERMS
            case KIT_VIP:
                return rankTier > 5;
            // MVP PERMS
            case KIT_MVP:
                return rankTier > 6;
            // ADMIN PERMS
            case MUTE:
            case CLEAR_CHAT:
            case CLEAR_INVENTORY_OTHERS:
            case ENDERCHEST_OTHERS:
            case INVSEE:
            case GAMEMODE:
                return rankTier > 7;
            // CEO PERMS
            case SET_LOCATION:
            case TESTER:
            case RANK_MANAGEMENT:
            case ECON_MANAGEMENT:
            case HOLOGRAMS:
            case SHOP_NPC:
                return rankTier > 8;
            default:
                return true;
        }
    }

    public int getMaxHomes(Player player){
        Rank rank = getRank(player);
        int rankTier = getRankTier(rank);
        if(rankTier > 7) return 20;
        if(rankTier > 3) return 3;
        if(rankTier > 1) return 2;
        return 1;
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

    public int rankupPrice(Rank rank){
        if(rank.equals(Rank.WOOD)) return 0;
        else if(rank.equals(Rank.STONE)) return 50000;
        else if(rank.equals(Rank.IRON)) return 500000;
        else if(rank.equals(Rank.GOLD)) return 1500000;
        else if(rank.equals(Rank.DIAMOND)) return 5000000;
        return 999999999;
    }

    public String getRankName(Rank rank, boolean color){
        switch (rank){
            case CEO:
                return color ? "§5§lCEO" : "CEO";
            case ADMIN:
                return color ? "§4Admin" : "Admin";
            case MVP:
                return color ? "§bMVP" : "MVP";
            case VIP:
                return color ? "§aVIP" : "VIP";
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

    public Rank getNextRank(Player player){
        Rank rank = getRank(player);
        if(rank.equals(Rank.WOOD)) return Rank.STONE;
        else if(rank.equals(Rank.STONE)) return Rank.IRON;
        else if(rank.equals(Rank.IRON)) return Rank.GOLD;
        else if(rank.equals(Rank.GOLD)) return Rank.DIAMOND;
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

    public Rank getRank(Player player){
        return main.userDataTable.online.get(player.getUniqueId()).getRank();
    }

    public Rank getRank(UUID uuid){
        return main.userDataTable.online.get(uuid).getRank();
    }
}
