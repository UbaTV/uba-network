package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;

import java.util.ArrayList;

public class KingdomsManager implements CommandExecutor {

    private Main main = Main.getInstance();

    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public KingdomsManager(){
        subCommands.add(new HelpSubcommand());
        subCommands.add(new CreateSubcommand());
        subCommands.add(new OwnershipSubcommand());
        subCommands.add(new QuitSubcommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length > 0){
                for(int i = 0; i < getSubCommands().size(); i++){
                    if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        getSubCommands().get(i).perform(player, args);
                    }
                }
            }else{
                player.performCommand("kingdoms help");
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
