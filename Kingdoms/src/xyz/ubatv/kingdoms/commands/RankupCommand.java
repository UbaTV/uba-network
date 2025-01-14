package xyz.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Rank;
import xyz.ubatv.kingdoms.rankSystem.ServerRank;
import xyz.ubatv.kingdoms.userData.UserData;
import xyz.ubatv.kingdoms.userData.UserDataManager;

public class RankupCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                UserData userData = UserDataManager.usersData.get(player.getUniqueId());
                ServerRank nextRank = main.rankManager.getNextRank(player);
                if(nextRank == null){
                    player.sendMessage(main.textUtils.warning + "You are on the highest rank.");
                    return false;
                }

                int balance = userData.getCoins();
                int rankupPrice = main.rankManager.rankupPrice(nextRank);
                if(balance < rankupPrice){
                    player.sendMessage(main.textUtils.error + "§7You need §5" + rankupPrice + main.textUtils.coinsSymbol + " §7to rankup.");
                    return false;
                }

                userData.setServerRank(nextRank);
                userData.setCoins(balance - rankupPrice);
                Bukkit.getServer().broadcastMessage(main.textUtils.right + "§5" + player.getName() + " §7just ranked up to " + main.rankManager.getServerRankName(nextRank, true));
                return false;
            }

            player.sendMessage(main.textUtils.error + "Wrong syntax.");
            player.sendMessage(main.textUtils.warning + "/rankup");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
