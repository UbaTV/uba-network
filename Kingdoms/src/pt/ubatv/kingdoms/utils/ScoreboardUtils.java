package pt.ubatv.kingdoms.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pt.ubatv.kingdoms.Main;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardUtils {

    private Main main = Main.getInstance();

    static HashMap<UUID, ScoreboardUtils> scoreboards = new HashMap<>();

    private Scoreboard scoreboard;
    private Objective sidebar;
    private Objective belowName;

    public ScoreboardUtils(Player player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        belowName = scoreboard.registerNewObjective("below_name", "dummy");

        belowName.setDisplaySlot(DisplaySlot.BELOW_NAME);
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        // CREATE TEAMS
        for(int i = 1; i < 16; i++){
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }

        belowName.setDisplayName("§7Health");
        updateBelowName(player);

        player.setScoreboard(scoreboard);
        scoreboards.put(player.getUniqueId(), this);
    }

    public void updateBelowName(Player player){
        Score score = belowName.getScore(player);
        score.setScore((int) player.getHealth());
    }

    public void setTitle(String title){
        title = ChatColor.translateAlternateColorCodes('&', title);
        sidebar.setDisplayName(title.length() > 32 ? title.substring(0, 32) : title);
    }

    public void setSlot(int slot, String text){
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if(!scoreboard.getEntries().contains(entry)){
            sidebar.getScore(entry).setScore(slot);
        }

        text = ChatColor.translateAlternateColorCodes('&', text);
        String prefix = main.textUtils.getFirstSplit(text);
        String suffix = main.textUtils.getFirstSplit(ChatColor.getLastColors(prefix) + main.textUtils.getSecondSplit(text));
        team.setPrefix(prefix);
        team.setSuffix(suffix);
    }

    public void removeSlot(int slot){
        String entry = genEntry(slot);
        if(scoreboard.getEntries().contains(entry)){
            scoreboard.resetScores(entry);
        }
    }

    private String genEntry(int slot){
        return ChatColor.values()[slot].toString();
    }

    public static boolean hasScoreboard(Player player){
        return scoreboards.containsKey(player.getUniqueId());
    }

    public static ScoreboardUtils createScoreboard(Player player){
        return new ScoreboardUtils(player);
    }

    public static ScoreboardUtils getScoreboard(Player player){
        return scoreboards.get(player.getUniqueId());
    }

    public static ScoreboardUtils removeScoreboard(Player player){
        return scoreboards.remove(player.getUniqueId());
    }
}
