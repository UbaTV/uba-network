package xyz.ubatv.kingdoms.events;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Rank;
import xyz.ubatv.kingdoms.rankSystem.ServerRank;
import xyz.ubatv.kingdoms.userData.UserDataManager;
import xyz.ubatv.kingdoms.utils.ScoreboardUtils;
import xyz.ubatv.kingdoms.userData.UserData;

import java.util.Objects;
import java.util.UUID;

public class JoinQuitEvent implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        main.kingdomsUserData.createUser(player);

        main.userDataManager.loadUserData(player);

        createScoreboard(player);

        event.setJoinMessage("§7[§a+§7] " + player.getName());

        UserData userData = UserDataManager.usersData.get(player.getUniqueId());
        Rank rank = userData.getRank();
        String rankName = main.rankManager.getRankName(rank, true);
        ServerRank serverRank = userData.getServerRank();
        String serverRankName = main.rankManager.getServerRankName(serverRank, true);

        if(rank.equals(Rank.MEMBER)){
            player.setPlayerListName("§7[" + serverRankName + "§7] §7" + player.getName());
        }else{
            player.setPlayerListName("§7[" + serverRankName + "§7] §7" +"§7[" + rankName + "§7] §7" + player.getName());
        }

        String userKingdom = userData.getKingdom();
        /*if(!userKingdom.equalsIgnoreCase("none")){
            if(main.kingdomUtils.getOnlineMembers(userKingdom) <= 1){
                main.kingdomClaimYML.loadKingdomClaims(userKingdom);
            }
        }*/

        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);

        // 1.8 PVP - Anticooldown
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)).setBaseValue(100);

        main.textUtils.sendCenteredMessage(player, "§7Welcome to the §5UbaTV Kingdoms§7!");
        player.sendMessage(" ");
        main.textUtils.sendCenteredMessage(player, "§7Website: §5ubatv.xyz");
        main.textUtils.sendCenteredMessage(player, "§7Discord: §5discord.gg/AJxFu2C");
        player.sendMessage(" ");

        if(!player.hasPlayedBefore()){
            player.teleport(main.locationYML.spawn);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        UserData userData = UserDataManager.usersData.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();
        /*if(!userKingdom.equalsIgnoreCase("none")){
            if(main.kingdomUtils.getOnlineMembers(userKingdom) <= 1){
                main.kingdomClaimYML.saveKingdomClaims(userKingdom);
            }
        }*/

        main.userDataManager.saveUserData(player);
        UserDataManager.usersData.remove(uuid);

        event.setQuitMessage("§7[§c-§7] " + player.getName());
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);

        if(ScoreboardUtils.hasScoreboard(player)){
            ScoreboardUtils.removeScoreboard(player);
        }
    }

    public void createScoreboard(Player player){
        UserData userData = UserDataManager.usersData.get(player.getUniqueId());
        ScoreboardUtils scoreboardUtils = ScoreboardUtils.createScoreboard(player);
        scoreboardUtils.setTitle(main.textUtils.serverName + " §6§lBETA");
        scoreboardUtils.setSlot(7, "  ");
        scoreboardUtils.setSlot(6, "§6| §7Coins: §5" + userData.getCoins() + main.textUtils.coinsSymbol);
        scoreboardUtils.setSlot(5, "§a| §7Rank: " + main.rankManager.getServerRankName(userData.getServerRank(), true));
        scoreboardUtils.setSlot(4, "§d| §7Kills: §5" + userData.getKills());
        scoreboardUtils.setSlot(3, "§c| §7Deaths: §5" + userData.getDeaths());
        scoreboardUtils.setSlot(2, " ");
        scoreboardUtils.setSlot(1, "§7" + main.textUtils.serverIP);
    }

    public void updateScoreboard(Player player){
        if(ScoreboardUtils.hasScoreboard(player)){
            UserData userData = UserDataManager.usersData.get(player.getUniqueId());
            ScoreboardUtils scoreboardUtils = ScoreboardUtils.getScoreboard(player);
            scoreboardUtils.setSlot(6, "§6| §7Coins: §5" + userData.getCoins() + main.textUtils.coinsSymbol);
            scoreboardUtils.setSlot(5, "§a| §7Rank: " + main.rankManager.getServerRankName(userData.getServerRank(), true));
            scoreboardUtils.setSlot(4, "§d| §7Kills: §5" + userData.getKills());
            scoreboardUtils.setSlot(3, "§c| §7Deaths: §5" + userData.getDeaths());
        }
    }
}
