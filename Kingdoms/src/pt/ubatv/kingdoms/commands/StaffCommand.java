package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Rank;

import java.util.ArrayList;

public class StaffCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            ArrayList<Player> staffOnline = getOnlineStaff();

            StringBuilder ceo = new StringBuilder();
            StringBuilder admin = new StringBuilder();

            for(Player staff : staffOnline){
                Rank rank = main.rankManager.getRank(staff);
                if(rank.equals(Rank.CEO)) ceo.append("§5" + staff.getName() + " ");
                else if(rank.equals(Rank.ADMIN)) admin.append("§5" + staff.getName() + " ");
            }

            if(ceo.length() == 0){
                player.sendMessage(main.textUtils.error + main.rankManager.getRankName(Rank.CEO, true) + "§7: No staff members online");
            }else{
                player.sendMessage(main.textUtils.right + main.rankManager.getRankName(Rank.CEO, true) + "§7: " + ceo.toString());
            }

            if(admin.length() == 0){
                player.sendMessage(main.textUtils.error + main.rankManager.getRankName(Rank.ADMIN, true) + "§7: No staff members online");
            }else{
                player.sendMessage(main.textUtils.right + main.rankManager.getRankName(Rank.ADMIN, true) + "§7: " + admin.toString());
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    private ArrayList<Player> getOnlineStaff(){
        ArrayList<Player> onlineStaff = new ArrayList<>();
        for(Player target : Bukkit.getOnlinePlayers()){
            if(main.rankManager.isStaff(target)){
                onlineStaff.add(target);
            }
        }
        return onlineStaff;
    }
}
