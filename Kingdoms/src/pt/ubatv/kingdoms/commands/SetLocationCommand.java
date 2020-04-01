package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;

public class SetLocationCommand implements CommandExecutor{

    public Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.SET_LOCATION)){
                Location location = player.getLocation();
                String locationName = args[0].toLowerCase();
                String worldName = location.getWorld().getName();
                int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();
                float yaw = location.getYaw(), pitch = location.getPitch();

                main.locationYML.getConfig().set(locationName + ".world", worldName);
                main.locationYML.getConfig().set(locationName + ".x", x);
                main.locationYML.getConfig().set(locationName + ".y", y);
                main.locationYML.getConfig().set(locationName + ".z", z);
                main.locationYML.getConfig().set(locationName + ".yaw", yaw);
                main.locationYML.getConfig().set(locationName + ".pitch", pitch);
                main.locationYML.saveConfig();

                if(locationName.equalsIgnoreCase("spawn")){
                    main.locationYML.spawn = location;
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
