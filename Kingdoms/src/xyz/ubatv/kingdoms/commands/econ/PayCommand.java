package xyz.ubatv.kingdoms.commands.econ;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;

public class PayCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 2){
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(main.textUtils.error + "§4Invalid player");
                    return false;
                }

                try{
                    int coins = Integer.parseInt(args[1]);
                    int balance = main.userDataTable.online.get(player.getUniqueId()).getCoins();
                    if(balance < coins){
                        player.sendMessage(main.textUtils.warning + "You don't have enough money.");
                    }else{
                        int targetCoins = main.userDataTable.online.get(target.getUniqueId()).getCoins();
                        main.userDataTable.online.get(target.getUniqueId()).setCoins(targetCoins + coins);
                        main.userDataTable.online.get(player.getUniqueId()).setCoins(balance - coins);
                        player.sendMessage("§7You just sent §5" + coins + main.textUtils.coinsSymbol + " §7to §5" + target.getName());
                        target.sendMessage("§7You just received §5" + coins + main.textUtils.coinsSymbol+ " §7from §5" + player.getName());
                    }
                }catch (NumberFormatException e){
                    player.sendMessage(main.textUtils.error + "Invalid amount.");
                }
                return false;
            }
            player.sendMessage(main.textUtils.error + "Wrong syntax");
            player.sendMessage(main.textUtils.warning + "/pay <player> <amount>");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
