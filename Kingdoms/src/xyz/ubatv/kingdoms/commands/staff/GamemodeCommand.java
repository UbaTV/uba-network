package xyz.ubatv.kingdoms.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Permissions;

public class GamemodeCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.GAMEMODE)){
                if(!(args.length == 1 || args.length == 2)){
                    player.sendMessage(main.textUtils.error + "Wrong syntax.");
                    player.sendMessage(main.textUtils.warning + "/gamemode <gamemode> [player]");
                    return false;
                }

                GameMode gamemode;

                if(args[0].equalsIgnoreCase("creative")
                || args[0].equalsIgnoreCase("c")
                || args[0].equalsIgnoreCase("1")){
                    gamemode = GameMode.CREATIVE;
                }else if(args[0].equalsIgnoreCase("adventure")
                || args[0].equalsIgnoreCase("a")
                || args[0].equalsIgnoreCase("2")){
                    gamemode = GameMode.ADVENTURE;
                }else if(args[0].equalsIgnoreCase("survival")
                || args[0].equalsIgnoreCase("s")
                || args[0].equalsIgnoreCase("0")){
                    gamemode = GameMode.SURVIVAL;
                }else if(args[0].equalsIgnoreCase("spectator")
                || args[0].equalsIgnoreCase("spec")
                || args[0].equalsIgnoreCase("3")){
                    gamemode = GameMode.SPECTATOR;
                }else{
                    player.sendMessage(main.textUtils.error + "Invalid gamemode.");
                    return false;
                }

                String gamemodeName;
                if(gamemode.equals(GameMode.ADVENTURE)) gamemodeName = "Adventure";
                else if(gamemode.equals(GameMode.CREATIVE)) gamemodeName = "Creative";
                else if(gamemode.equals(GameMode.SPECTATOR)) gamemodeName = "Spectator";
                else gamemodeName = "Survival";

                if(args.length == 1){
                    player.setGameMode(gamemode);
                    player.sendMessage(main.textUtils.right + "Your gamemode has been updated to §5" + gamemodeName);
                    return false;
                }

                Player target = Bukkit.getServer().getPlayer(args[1]);
                if(target == null){
                    player.sendMessage(main.textUtils.error + "Invalid player.");
                    return false;
                }

                target.setGameMode(gamemode);
                target.sendMessage(main.textUtils.right + "Your gamemode has been updated to §5" + gamemodeName);
                player.sendMessage(main.textUtils.right + "§5" + target.getName() + " §7gamemode has been updated to §5" + gamemodeName);
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
