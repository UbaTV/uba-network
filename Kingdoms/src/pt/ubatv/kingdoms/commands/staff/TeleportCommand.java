package pt.ubatv.kingdoms.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;

public class TeleportCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.TELEPORT)){
                if(args.length == 1){
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if(target == null){
                        player.sendMessage(main.textUtils.error + "Invalid player.");
                        return false;
                    }
                    
                    player.teleport(target);
                    player.sendMessage(main.textUtils.right + "Teleported to §5" + target.getName());
                    return false;
                }

                if(args.length == 2){
                    Player target1 = Bukkit.getServer().getPlayer(args[0]);
                    if(target1 == null){
                        player.sendMessage(main.textUtils.error + "First player is invalid.");
                        return false;
                    }

                    Player target2 = Bukkit.getServer().getPlayer(args[1]);
                    if(target2 == null){
                        player.sendMessage(main.textUtils.error + "Second player is invalid.");
                        return false;
                    }

                    target1.teleport(target2);
                    player.sendMessage(main.textUtils.right + "Teleported §5" + target1.getName() +
                            " §7to §5" + target2.getName());
                    target1.sendMessage(main.textUtils.warning + "You were teleported to §5" + target2.getName());
                    return false;
                }

                if(args.length == 3){
                    try{
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        int z = Integer.parseInt(args[2]);

                        player.teleport(new Location(player.getWorld(), x, y, z));
                        player.sendMessage(main.textUtils.right + "You were teleported to " +
                                "§7x:§5 " + x +
                                "§7, y:§5 " + y +
                                "§7, z:§5 " + z);
                    }catch (NumberFormatException e){
                        player.sendMessage(main.textUtils.error + "Invalid coordinates.");
                    }
                }

                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/teleport <player> [player]");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
