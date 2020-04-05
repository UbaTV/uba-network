package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

public class AcceptSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "accept";
    }

    @Override
    public String getDescription() {
        return "Accept a kingdom invitation";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms accept";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1){
            UserData userData = main.userDataTable.online.get(player.getUniqueId());
            String userKingdom = userData.getKingdom();
            if(!userKingdom.equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "You are already in a kingdom.");
                return;
            }

            if(KingdomUtils.invites.containsKey(player)){
                String kingdomsName = KingdomUtils.invites.get(player);

                if(main.kingdomUtils.getSize(kingdomsName) >= 25){
                    player.sendMessage(main.textUtils.warning + "Kingdom reached max capacity.");
                    return;
                }

                userData.setKingdom(kingdomsName);
                main.kingdomsTable.updateMembers(kingdomsName, main.kingdomsTable.getMembers(kingdomsName) + player.getName() + "#");
                main.kingdomUtils.broadcastKingdom(kingdomsName,
                        "§5" + player.getName() + " §7just joined the kingdom.");
                KingdomUtils.invites.remove(player);
            }else{
                player.sendMessage(main.textUtils.error + "You don't have an invite.");
            }
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
