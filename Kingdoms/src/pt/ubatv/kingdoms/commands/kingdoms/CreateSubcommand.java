package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;

public class CreateSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a kingdom.";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {

    }
}
