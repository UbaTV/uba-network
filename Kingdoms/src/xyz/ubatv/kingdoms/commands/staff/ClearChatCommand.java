package xyz.ubatv.kingdoms.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Permissions;

public class ClearChatCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.CLEAR_CHAT)){
                if(args.length == 0){
                    Bukkit.getServer().getOnlinePlayers().forEach(this::clearChat);
                    return false;
                }

                if(args.length == 1){
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if(target == null){
                        player.sendMessage(main.textUtils.error + "Invalid player.");
                        return false;
                    }

                    player.sendMessage(main.textUtils.right + "You cleared §5" + target.getName() + " §7chat.");
                    clearChat(target);
                    return false;
                }

                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/clearchat §5[player]");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    private void clearChat(Player player){
        for(int i = 0; i < 150; i++){
            player.sendMessage(" ");
        }
        player.sendMessage(main.textUtils.right + "Your chat has been cleared.");
        player.sendMessage(" ");
    }
}
