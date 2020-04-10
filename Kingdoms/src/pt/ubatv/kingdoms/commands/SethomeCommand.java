package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

import java.util.HashMap;
import java.util.UUID;

public class SethomeCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length >= 2){
                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/sethome §5[name]");
                return false;
            }

            if(args.length == 1){
                String homeName = args[0].toLowerCase();
                if(homeName.length() > 16){
                    player.sendMessage(main.textUtils.error + "Home name can't be that long.");
                    return false;
                }

                UUID uuid = player.getUniqueId();
                HashMap<String, Location> playerHomes = main.locationYML.getPlayerHomes(uuid);
                if(playerHomes.size() >= main.rankManager.getMaxHomes(player)){
                    player.sendMessage(main.textUtils.error + "You have reached the limit of homes.");
                    player.sendMessage(main.textUtils.warning + "Delete one using /delhome §5<name> §7before adding a new one.");
                    return false;
                }

                main.locationYML.setLocation(uuid.toString() + "." + homeName, player.getLocation());
                player.sendMessage(main.textUtils.right + "§7Home §5" + homeName + " §7has been set.");
            }

            if(args.length == 0){
                UUID uuid = player.getUniqueId();
                HashMap<String, Location> playerHomes = main.locationYML.getPlayerHomes(uuid);
                if(playerHomes.size() >= main.rankManager.getMaxHomes(player)){
                    player.sendMessage(main.textUtils.error + "You have reached the limit of homes.");
                    player.sendMessage(main.textUtils.warning + "Delete one using /delhome §5<name> §7before adding a new one.");
                    return false;
                }

                main.locationYML.setLocation(uuid.toString() + ".home", player.getLocation());
                player.sendMessage(main.textUtils.right + "§7Home §5home §7has been set.");
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
