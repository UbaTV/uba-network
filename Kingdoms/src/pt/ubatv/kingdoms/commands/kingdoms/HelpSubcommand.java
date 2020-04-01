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
        kingdomsHelp(player);
    }

    // TODO Format pages
    public void kingdomsHelp(Player player){
        player.sendMessage(getSyntax());
    }
}
