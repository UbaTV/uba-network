package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;

public class ClearInventoryCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.CLEAR_INVENTORY)){
                if(args.length == 0){
                    player.getInventory().clear();
                    player.sendMessage(main.textUtils.right + "Your inventory has been cleared.");
                    return false;
                }

                if(args.length == 1){
                    if(main.rankManager.hasPermission(player, Permissions.CLEAR_INVENTORY_OTHERS)){
                        Player target = Bukkit.getServer().getPlayer(args[0]);
                        if(target == null){
                            player.sendMessage(main.textUtils.error + "Invalid player.");
                            return false;
                        }

                        target.getInventory().clear();
                        target.sendMessage(main.textUtils.warning + "Your inventory has been cleared.");
                        player.sendMessage(main.textUtils.right + "§5" + target.getName() + " §7inventory has been cleared.");
                    }else{
                        player.sendMessage(main.textUtils.noPerms);
                    }
                    return false;
                }

                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "§7/clearinventory §5[player]");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
