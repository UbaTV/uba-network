package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

import java.util.HashMap;

public class TpaCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    public static HashMap<Player,Player> tpaRequest = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 1){
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(main.textUtils.error + "Invalid player.");
                    return false;
                }

                if(tpaRequest.containsKey(player)){
                    if(tpaRequest.get(player).equals(target)){
                        player.sendMessage(main.textUtils.warning + "You already sent a request for this player.");
                        return false;
                    }
                }

                tpaRequest.put(player, target);
                player.sendMessage(main.textUtils.right + "Teleport sent to §5" + target.getName());
                target.sendMessage(main.textUtils.right + "Teleport request received from §5" + player.getName());
                target.sendMessage(main.textUtils.right + "Do §7/tpa accept §5" + player.getName() + " §7to accept the teleport.");
                return false;
            }

            if(args.length == 2){
                Player target = Bukkit.getServer().getPlayer(args[1]);
                if(target == null){
                    player.sendMessage(main.textUtils.error + "Invalid player.");
                    return false;
                }

                if(args[0].equalsIgnoreCase("accept")
                || args[0].equalsIgnoreCase("a")
                || args[0].equalsIgnoreCase("acc")
                || args[0].equalsIgnoreCase("acpt")){
                    if(tpaRequest.containsKey(target)){
                        if(tpaRequest.get(target).equals(player)){
                            player.sendMessage(main.textUtils.right + "Teleport accepted.");
                            target.sendMessage(main.textUtils.warning + "§5" + player.getName() + " §7accepted your teleport.");
                            tpaRequest.remove(target);
                            target.teleport(player.getLocation());
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                            return false;
                        }
                    }
                }

                if(args[0].equalsIgnoreCase("deny")
                || args[0].equalsIgnoreCase("d")){
                    if(tpaRequest.containsKey(target)){
                        if(tpaRequest.get(target).equals(player)){
                            player.sendMessage(main.textUtils.right + "Teleport denied.");
                            target.sendMessage(main.textUtils.warning + "§5" + player.getName() + " §7denied your teleport.");
                            tpaRequest.remove(target);
                            return false;
                        }
                    }
                }

                player.sendMessage(main.textUtils.error + "Wrong syntax.");
                player.sendMessage(main.textUtils.warning + "/tpa accept §5§5<player>");
                player.sendMessage(main.textUtils.warning + "/tpa deny §5§5<player>");
                return false;
            }

            player.sendMessage(main.textUtils.error + "Wrong syntax.");
            player.sendMessage(main.textUtils.warning + "/tpa §5<player>");
            player.sendMessage(main.textUtils.warning + "/tpa accept §5§5<player>");
            player.sendMessage(main.textUtils.warning + "/tpa deny §5§5<player>");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
