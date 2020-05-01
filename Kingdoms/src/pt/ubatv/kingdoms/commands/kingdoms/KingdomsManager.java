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
        subCommands.add(new InfoSubcommand());
        subCommands.add(new InviteSubcommand());
        subCommands.add(new AcceptSubcommand());
        subCommands.add(new VaultSubcommand());
        subCommands.add(new TagSubcommand());
        subCommands.add(new AllySubcommand());
        subCommands.add(new NeutralSubcommand());
        subCommands.add(new ClaimSubcommand());
        subCommands.add(new UnclaimSubcommand());
        subCommands.add(new MapSubcommand());
        subCommands.add(new SethomeSubcommand());
        subCommands.add(new HomeSubcommand());
        subCommands.add(new DelhomeSubcommand());
        subCommands.add(new ChatSubcommand());
        subCommands.add(new LevelupSubcommand());
        subCommands.add(new ShopSubcommand());
        subCommands.add(new UnclaimAllSubcommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length > 0){
                for(int i = 0; i < getSubCommands().size(); i++){
                    if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        getSubCommands().get(i).perform(player, args);
                        return false;
                    }
                }
            }
            player.performCommand("kingdoms help");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
