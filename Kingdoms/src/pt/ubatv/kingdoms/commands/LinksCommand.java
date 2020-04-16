package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

public class LinksCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("§7Website: §5ubatv.xyz");
            player.sendMessage("§7Instangram: §5instagram.com/ubatvoficial");
            player.sendMessage("§7Twitter: §5twitter.com/ubatvoficial");
            player.sendMessage("§7Patreon: §5www.patreon.com/ubatvoficial");
            player.sendMessage("§7Github: §5github.com/UbaTV");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
