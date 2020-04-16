package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.configs.CooldownYML;

import java.util.ArrayList;
import java.util.Random;

public class WildCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                if(CooldownYML.wildCooldowns.containsKey(player)){
                    player.sendMessage(main.textUtils.error + "§7You need to wait " + main.textUtils.secondsToText(CooldownYML.wildCooldowns.get(player)) + " §7before you can teleport to wild.");
                }else{
                    Location wild = generateSafeLocation(player);
                    CooldownYML.wildCooldowns.put(player, 30*60);
                    player.teleport(wild);
                }
                return false;
            }

            player.sendMessage(main.textUtils.error + "Wrong syntax.");
            player.sendMessage(main.textUtils.warning + "/wild");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    private Location generateSafeLocation(Player player){
        Location randomLocation = generateLocation(player);
        while(!isLocationSafe(randomLocation)){
            randomLocation = generateLocation(player);
        }
        return randomLocation;
    }

    private Location generateLocation(Player player){
        Random random = new Random();

        int x = random.nextInt(2500);
        int y = 200;
        int z = random.nextInt(2500);
        
        Location randomLocation = new Location(player.getWorld(), x, y, z);
        y = randomLocation.getWorld().getHighestBlockYAt(randomLocation);
        randomLocation.setY(y);
        return randomLocation;
    }

    private boolean isLocationSafe(Location location){
        ArrayList<Material> bad_blocks = new ArrayList<>();

        bad_blocks.add(Material.LAVA);
        bad_blocks.add(Material.FIRE);
        bad_blocks.add(Material.CACTUS);


        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block blockHead = location.getWorld().getBlockAt(x,y,z);
        Block blockFeat = location.getWorld().getBlockAt(x,y-1,z);
        Block below = location.getWorld().getBlockAt(x, y - 2, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        return !(bad_blocks.contains(below.getType()))
                && !(bad_blocks.contains(blockFeat.getType()))
                && (blockHead.getType().isSolid())
                && (blockFeat.getType().isSolid())
                && (above.getType().isSolid());
    }
}
