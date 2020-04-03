package pt.ubatv.kingdoms.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;

import java.util.concurrent.atomic.AtomicInteger;

public class HologramCommand implements CommandExecutor, Listener {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.HOLOGRAMS)){
                if(args.length == 1 &&
                        (args[0].equalsIgnoreCase("delete")
                        || args[0].equalsIgnoreCase("d"))){
                    Location loc = player.getLocation();
                    AtomicInteger i = new AtomicInteger();
                    loc.getWorld().getNearbyEntities(loc, 2, 2, 2).forEach(
                            entity -> {
                                if(entity.getType().equals(EntityType.ARMOR_STAND)){
                                    entity.remove();
                                    i.getAndIncrement();
                                }
                            }
                    );
                    player.sendMessage(main.textUtils.right + "Removed §5" + i.toString() + " §7holograms.");
                    return false;
                }

                if(args.length > 1 &&
                        (args[0].equalsIgnoreCase("create")
                        || args[0].equalsIgnoreCase("c"))){
                    Location loc = player.getLocation();
                    ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

                    StringBuilder text = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        text.append(args[i] + " ");
                    }
                    text.deleteCharAt(text.length()-1);

                    as.setGravity(false);
                    as.setCanPickupItems(false);
                    as.setCustomName(ChatColor.translateAlternateColorCodes('&', text.toString()));
                    as.setCustomNameVisible(true);
                    as.setVisible(false);
                    return false;
                }

                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/hologram create §5<text>");
                player.sendMessage(main.textUtils.warning + "/hologram delete");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
