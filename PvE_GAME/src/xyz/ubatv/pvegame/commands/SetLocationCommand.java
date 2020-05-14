package xyz.ubatv.pvegame.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.pvegame.Main;
import xyz.ubatv.pvegame.rankSystem.Permissions;

public class SetLocationCommand implements CommandExecutor{

    public Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.SET_LOCATION)){
                Location location = player.getLocation();
                String locationName = args[0].toLowerCase();
                main.locationYML.setLocation(locationName, location);

                if(locationName.equalsIgnoreCase("lobby")){
                    main.locationYML.lobby = location;
                }

                player.sendMessage(main.textUtils.right + "§5" + locationName + " §7location saved successfully.");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
