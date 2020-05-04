package xyz.ubatv.kingdoms.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Permissions;

public class InvseeCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.INVSEE)){
                if(args.length == 1){
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if(target == null) {
                        player.sendMessage(main.textUtils.error + "Invalid player.");
                        return false;
                    }

                    player.openInventory(target.getInventory());
                    player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_OPEN, 1f ,1f);
                    return false;
                }

                player.sendMessage(main.textUtils.error + "Wrong syntax");
                player.sendMessage(main.textUtils.warning + "/invsee §5<player>");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}

