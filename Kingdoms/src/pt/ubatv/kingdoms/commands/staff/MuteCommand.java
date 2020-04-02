package pt.ubatv.kingdoms.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;
import pt.ubatv.kingdoms.utils.UserData;

public class MuteCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.MUTE)){
                if(args.length == 1){
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if(target == null){
                        player.sendMessage(main.textUtils.error + "Invalid player");
                        return false;
                    }

                    if(target.getName().equalsIgnoreCase(player.getName())){
                        player.sendMessage(main.textUtils.warning + "Don't mute yourself.");
                    }else{
                        UserData userDataTarget = main.userDataTable.online.get(target.getUniqueId());
                        userDataTarget.setMute(!userDataTarget.isMute());
                        player.sendMessage(userDataTarget.isMute() ?
                                main.textUtils.right + "§5" + target.getName() + " §7has been muted" :
                                main.textUtils.right + "§5" + target.getName() + " §7has been unmuted");
                    }
                }else{
                    player.sendMessage(main.textUtils.error + "Wrong syntax.");
                    player.sendMessage(main.textUtils.warning + "/mute §5<player>");
                }
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
