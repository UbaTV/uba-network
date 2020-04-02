package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;

public class EnderchestCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.ENDERCHEST)){
                if(args.length == 1){
                    if(main.rankManager.hasPermission(player, Permissions.ENDERCHEST_OTHERS)){
                        Player target = Bukkit.getServer().getPlayer(args[0]);
                        if(target != null){
                            player.sendMessage(main.textUtils.error + "Invalid player.");
                            return false;
                        }

                        player.openInventory(target.getEnderChest());
                        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 1f);
                    }else{
                        player.sendMessage(main.textUtils.noPerms);
                    }
                    return false;
                }

                player.openInventory(player.getEnderChest());
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 1f);
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
