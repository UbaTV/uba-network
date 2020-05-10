package xyz.ubatv.kingdoms.commands.econ;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.userData.UserDataManager;

public class BalanceCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                int coins = UserDataManager.usersData.get(player.getUniqueId()).getCoins();
                player.sendMessage("§7Coins §8⟿ §5" + coins + " " + main.textUtils.coinsSymbol);
                return false;
            }

            if(args.length == 1){
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(main.textUtils.error + "Invalid player.");
                    return false;
                }

                int coins = UserDataManager.usersData.get(target.getUniqueId()).getCoins();
                player.sendMessage("§5" + target.getName() + "§7's coins §8⟿ §5" + coins + main.textUtils.coinsSymbol);
                return false;
            }

            player.sendMessage(main.textUtils.error + "Wrong syntax.");
            player.sendMessage(main.textUtils.warning + "/balance §5[player]");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
