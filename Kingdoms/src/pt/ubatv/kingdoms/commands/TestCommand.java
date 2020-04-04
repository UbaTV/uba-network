package pt.ubatv.kingdoms.commands;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;

public class TestCommand implements CommandExecutor, Listener {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.TESTER)
            || player.getName().equalsIgnoreCase("andreubita")){
                /*UserData userData = main.userDataTable.online.get(player.getUniqueId());
                userData.setCoins(userData.getCoins() + 100);*/

                //freezeEntity(shop);
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    public void freezeEntity(Entity en) {
        net.minecraft.server.v1_15_R1.Entity nmsEn = ((CraftEntity) en).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte) 1);
        nmsEn.f(compound);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Location loc = event.getBlock().getLocation();
        // PLACE Mystery Lunchbox Hologram
        /*if(event.getBlock().getType().equals(Material.PURPLE_SHULKER_BOX)){
            ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
            as.setGravity(true);
            as.setCanPickupItems(false);
            as.setCustomName(ChatColor.translateAlternateColorCodes('&', "&7Mystery &5&lLunchbox"));
            as.setCustomNameVisible(true);
            as.setVisible(false);
            as.setSmall(true);
            as.setCollidable(false);
            as.setSwimming(true);
        }*/
    }
}
