package xyz.ubatv.hub.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.hub.Main;

public class SpawnCommand implements CommandExecutor {

    public Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.locationYML.spawn != null){
                player.teleport(main.locationYML.spawn);
                player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f , 1f);
            }else{
                player.sendMessage(main.textUtils.error + "Spawn location not defined.");
                player.sendMessage(main.textUtils.warning + "Please contact staff.");
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
