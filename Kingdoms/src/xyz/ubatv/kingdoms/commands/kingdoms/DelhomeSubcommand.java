package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.utils.UserData;

public class DelhomeSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "delhome";
    }

    @Override
    public String getDescription() {
        return "Remove your kingdom's home location";
    }

    @Override
    public String getSyntax() {
        return "/kingdoms delhome";
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
            player.sendMessage(main.textUtils.error + "You must be king to sethome.");
            return;
        }

        if(args.length == 1){
            if(!main.kingdomsYML.getConfig().contains(userKingdom.toLowerCase() + ".home")){
                player.sendMessage(main.textUtils.warning + "Your kingdom does not have a home set.");
                return;
            }

            if(main.kingdomsYML.getConfig().get(userKingdom.toLowerCase() + ".home") == null){
                player.sendMessage(main.textUtils.warning + "Your kingdom does not have a home set.");
                return;
            }

            main.kingdomsYML.removeKingdomHome(userKingdom);
            main.kingdomUtils.broadcastKingdom(userKingdom, "Kingdom home has been removed.");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
