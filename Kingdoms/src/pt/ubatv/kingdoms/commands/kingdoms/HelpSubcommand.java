package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;

public class HelpSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Shows all the kingdoms commands.";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms help §5[page]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){
            try{
                int page = Integer.parseInt(args[1]);
                kingdomsHelp(player, page);
                return;
            }catch (NumberFormatException e){
                player.sendMessage(main.textUtils.error + "Invalid page number");
                return;
            }
        }

        kingdomsHelp(player, 1);
    }

    public void kingdomsHelp(Player player, int page){
        player.sendMessage(" ");
        main.textUtils.sendCenteredMessage(player, "§7§m========[§5Kingdoms§7§m]========");
        player.sendMessage(" ");
        if(page == 2){
            player.sendMessage("§7/kingdoms accept");
            player.sendMessage("§7/kingdoms ownership §5<player>");
            player.sendMessage("§7/kingdoms ally");
            player.sendMessage("§7/kingdoms neutral");
            player.sendMessage("§7/kingdoms vault");
            player.sendMessage(" ");
            player.sendMessage("§7Page §52§7/§53");
            player.sendMessage("§7do §5/kingdoms help <page> §7to see next page");
            return;
        }

        if(page == 3){
            player.sendMessage("§7/kingdoms claim");
            player.sendMessage("§7/kingdoms unclaim");
            player.sendMessage("§7/kingdoms unclaimall");
            player.sendMessage("§7/kingdoms map");
            player.sendMessage("§7/kingdoms sethome");
            player.sendMessage(" ");
            player.sendMessage("§7Page §53§7/§53");
            player.sendMessage("§7do /kingdoms help <page> §7to see next page");
            return;
        }

        if(page == 4){
            player.sendMessage("§7/kingdoms home");
            player.sendMessage("§7/kingdoms delhome");
            player.sendMessage("§7/kingdoms chat");
            player.sendMessage("§7/kingdoms levelup");
            player.sendMessage("§7/kingdoms shop");
            player.sendMessage(" ");
            player.sendMessage("§7Page §53§7/§53");
            player.sendMessage("§7do /kingdoms help <page> §7to see next page");
            return;
        }

        player.sendMessage("§7/kingdoms create §5<name>");
        player.sendMessage("§7/kingdoms info §5[kingdom]");
        player.sendMessage("§7/kingdoms invite §5<player>");
        player.sendMessage("§7/kingdoms tag §5<tag>");
        player.sendMessage("§7/kingdoms quit");
        player.sendMessage(" ");
        player.sendMessage("§7Page §51§7/§53");
        player.sendMessage("§7do §5/kingdoms help <page> §7to see next page");
    }
}
