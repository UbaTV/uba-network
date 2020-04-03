package pt.ubatv.kingdoms.events;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Rank;
import pt.ubatv.kingdoms.utils.ScoreboardUtils;
import pt.ubatv.kingdoms.utils.UserData;

import java.util.UUID;

public class JoinQuitEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        main.userDataTable.createUser(uuid);
        main.bankTable.createUser(uuid);
        main.userDataTable.loadUserData(player);

        createScoreboard(player);

        event.setJoinMessage("§7[§a+§7] " + player.getName());

        player.setPlayerListName("§7[" + main.rankManager.getRankName(main.userDataTable.online.get(player.getUniqueId()).getRank(), true) + "§7] §7" + player.getName());

        // 1.8 PVP - Anticooldown
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);

        if(!player.hasPlayedBefore()){
            player.teleport(main.locationYML.spawn);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        main.userDataTable.saveUserData(player);
        main.userDataTable.online.remove(uuid);

        event.setQuitMessage("§7[§c-§7] " + player.getName());

        if(ScoreboardUtils.hasScoreboard(player)){
            ScoreboardUtils.removeScoreboard(player);
        }
    }

    public void createScoreboard(Player player){
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        ScoreboardUtils scoreboardUtils = ScoreboardUtils.createScoreboard(player);
        scoreboardUtils.setTitle(main.textUtils.serverName + " §6§lBETA");
        scoreboardUtils.setSlot(7, "  ");
        scoreboardUtils.setSlot(6, "§6| §7Coins: §5" + userData.getCoins() + main.textUtils.coinsSymbol);
        scoreboardUtils.setSlot(5, "§a| §7Rank: " + main.rankManager.getRankName(userData.getRank(), true));
        scoreboardUtils.setSlot(4, "§d| §7Kills: §5" + userData.getKills());
        scoreboardUtils.setSlot(3, "§c| §7Deaths: §5" + userData.getDeaths());
        scoreboardUtils.setSlot(2, " ");
        scoreboardUtils.setSlot(1, "§7" + main.textUtils.serverIP);
    }

    public void updateScoreboard(Player player){
        if(ScoreboardUtils.hasScoreboard(player)){
            UserData userData = main.userDataTable.online.get(player.getUniqueId());
            ScoreboardUtils scoreboardUtils = ScoreboardUtils.getScoreboard(player);
            scoreboardUtils.setSlot(6, "§6| §7Coins: §5" + userData.getCoins() + main.textUtils.coinsSymbol);
            scoreboardUtils.setSlot(5, "§a| §7Rank: " + main.rankManager.getRankName(userData.getRank(), true));
            scoreboardUtils.setSlot(4, "§d| §7Kills: §5" + userData.getKills());
            scoreboardUtils.setSlot(3, "§c| §7Deaths: §5" + userData.getDeaths());
        }
    }
}
