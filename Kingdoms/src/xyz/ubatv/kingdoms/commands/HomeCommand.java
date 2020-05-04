package xyz.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;

import java.util.HashMap;
import java.util.Map;

public class HomeCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length >= 2){
                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/home §5[name]");
            }

            HashMap<String, Location> playerHomes = main.locationYML.getPlayerHomes(player.getUniqueId());
            if(playerHomes.size() == 0){
                player.sendMessage(main.textUtils.error + "You haven't set any homes.");
                return false;
            }

            if(args.length == 0){
                if(playerHomes.size() == 1){
                    Map.Entry<String,Location> entry = playerHomes.entrySet().iterator().next();
                    player.teleport(entry.getValue());
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                }else{
                    player.sendMessage(main.textUtils.error + "You need to specify a home.");
                }
                return false;
            }

            if(args.length == 1){
                String homeName = args[0].toLowerCase();
                if(playerHomes.containsKey(homeName)){
                    player.teleport(playerHomes.get(homeName));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                }else{
                    player.sendMessage(main.textUtils.error + "Invalid home name.");
                }
                return false;
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
