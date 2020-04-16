package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

public class PrivateChestCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            player.sendMessage(main.textUtils.error + "UNDER DEVELOPMENT");
            return false;

            /*if(args.length == 1){
                try{
                    int id = Integer.parseInt(args[0]);

                }catch (NumberFormatException e){
                    player.sendMessage(main.textUtils.error + "Invalid chest id.");
                }
                return false;
            }

            player.sendMessage(main.textUtils.error + "Wrong syntax.");
            player.sendMessage(main.textUtils.warning + "/privatechest §5<id>");*/
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
