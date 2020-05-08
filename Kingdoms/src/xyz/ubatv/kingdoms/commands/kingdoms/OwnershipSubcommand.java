package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.userData.UserData;

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
        return "§7/kingdoms ownership §5<player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){
            UserData userData = main.mainUserData.online.get(player.getUniqueId());
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

            UserData targetData = main.mainUserData.online.get(target.getUniqueId());
            if(!targetData.getKingdom().equalsIgnoreCase(userKingdom)){
                player.sendMessage(main.textUtils.error + "§5" + target.getName() + " §7is not in your kingdom.");
                return;
            }

            main.kingdomsTable.updateOwner(userKingdom, target);
            main.kingdomUtils.broadcastKingdom(userKingdom, "§5" + target.getName() + " §7is the new king of §5"
                    + main.kingdomsTable.getDisplayName(userKingdom));
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
