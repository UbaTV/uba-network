package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

public class OwnershipSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "ownership";
    }

    @Override
    public String getDescription() {
        return "Transfer kingdom ownership";
    }

    @Override
    public String getSyntax() {
        return "/kingdoms ownership <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();
        if(userKingdom.equalsIgnoreCase("none")){
            player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
            return;
        }

        if(!main.kingdomsTable.getOwner(userKingdom).equalsIgnoreCase(player.getName())){
            player.sendMessage(main.textUtils.error + "You must be the kingdoms king to transfer ownership.");
            return;
        }

        Player target = Bukkit.getServer().getPlayer(args[1]);
        if(target == null){
            player.sendMessage(main.textUtils.error + "Invalid player");
            return;
        }

        UserData targetData = main.userDataTable.online.get(target.getUniqueId());
        if(!targetData.getKingdom().equalsIgnoreCase(userKingdom)){
            player.sendMessage("§5" + target.getName() + " §7is not in your kingdom.");
            return;
        }

        main.kingdomsTable.updateOwner(userKingdom, target);
        main.kingdomUtils.broadcastKingdom(userKingdom, "§5" + target.getName() + " §7is the new king of §5"
                + main.kingdomsTable.getDisplayName(userKingdom));
    }
}
