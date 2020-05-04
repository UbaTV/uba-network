package xyz.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;

public class PrivateMessageCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length < 2){
                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/pm §5<player> <message>");
                return false;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null){
                player.sendMessage(main.textUtils.error + "Invalid player.");
                return false;
            }

            if(target.getName().equalsIgnoreCase(player.getName())){
                player.sendMessage(main.textUtils.warning + "Try sending the message to someone else.");
                return false;
            }

            StringBuilder message = new StringBuilder();
            for(int i = 1; i < args.length; i++){
                message.append(args[i]).append(" ");
            }

            player.sendMessage("§7[§5PM§7] §5" + player.getName() + " §8→ §5" + target.getName() + "§8: §7" + message.toString());
            target.sendMessage("§7[§5PM§7] §5" + player.getName() + " §8→ §5" + target.getName() + "§8: §7" + message.toString());
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
