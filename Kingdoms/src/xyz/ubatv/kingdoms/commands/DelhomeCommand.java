package xyz.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;

import java.util.HashMap;

public class DelhomeCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 1){
                HashMap<String, Location> playerHomes = main.locationYML.getPlayerHomes(player.getUniqueId());
                String homeName = args[0].toLowerCase();
                if(playerHomes.containsKey(homeName)){
                    main.locationYML.getConfig().set(player.getUniqueId().toString() + "." + homeName, null);
                    main.locationYML.saveConfig();
                    player.sendMessage(main.textUtils.right + "Home §5" + homeName + " §7deleted.");
                }else{
                    player.sendMessage(main.textUtils.error + "Invalid home name.");
                }
                return false;
            }

            player.sendMessage(main.textUtils.error + "Wrong syntax.");
            player.sendMessage(main.textUtils.warning + "/delhome §5<name>");
        }else{
            Bukkit.getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
