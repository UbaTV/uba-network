package xyz.ubatv.kingdoms.lunchbox;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Permissions;

public class PlaceLunchboxCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.PLACE_LUNCHBOX)){
                Location loc = player.getLocation().add(1, -2, 0);
                ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
                as.setGravity(true);
                as.setCanPickupItems(false);
                as.setCustomName(ChatColor.translateAlternateColorCodes('&', "&7Mystery &5&lLunchbox"));
                as.setCustomNameVisible(true);
                as.setVisible(false);
                as.setSmall(true);
                as.setCollidable(false);
                as.setSwimming(true);
                loc.getBlock().setType(Material.PURPLE_SHULKER_BOX);
                main.getConfig().set("lunchbox.location", loc);
                player.sendMessage(main.textUtils.right + "&7Mystery &5&lLunchbox §7placed.");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
