package pt.ubatv.kingdoms.rankSystem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

public class RankCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.RANK_MANAGEMENT)
            || player.getName().equalsIgnoreCase("andreubita")){
                if(args.length == 2){
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if(target == null){
                        player.sendMessage(main.textUtils.error + "Invalid player.");
                        return false;
                    }

                    try{
                        Rank rank = Rank.valueOf(args[1].toUpperCase());
                        main.userDataTable.online.get(target.getUniqueId()).setRank(rank);
                        target.sendMessage(main.textUtils.right + "Your rank has been updated to " + main.rankManager.getRankName(rank , true) + "§7.");
                        if(!target.getName().equalsIgnoreCase(player.getName())){
                            player.sendMessage(main.textUtils.right + "§5" + target.getName() + "§7's rank has been updated to " + main.rankManager.getRankName(rank, true) + "§7.");
                        }
                    }catch (IllegalArgumentException e){
                        player.sendMessage(main.textUtils.error + "Invalid rank.");
                    }
                    return false;
                }

                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/rank <player> <rank>");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
