package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.userData.UserData;
import xyz.ubatv.kingdoms.userData.UserDataManager;

public class InviteSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invite someone to your kingdom";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms invite §5<player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){
            UserData userData = UserDataManager.usersData.get(player.getUniqueId());
            String userKingdom = userData.getKingdom();
            if(userKingdom.equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
                return;
            }

            if(main.kingdomUtils.getSize(userKingdom) >= 25){
                player.sendMessage(main.textUtils.warning + "Kingdom reached max capacity.");
                return;
            }

            if(!main.kingdomsTable.getKing(userKingdom).equalsIgnoreCase(player.getName())){
                player.sendMessage(main.textUtils.error + "You must be king to invite players.");
                return;
            }

            Player target = Bukkit.getServer().getPlayer(args[1]);
            if(target == null){
                player.sendMessage(main.textUtils.error + "Invalid player.");
                return;
            }

            UserData targetData = UserDataManager.usersData.get(target.getUniqueId());
            String targetKingdom = targetData.getKingdom();
            if(!targetKingdom.equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "§5" + target.getName() + " §7is already in a kingdom.");
                return;
            }

            KingdomUtils.invites.put(target, userKingdom.toLowerCase());
            player.sendMessage(main.textUtils.right + "§5" + target.getName() + " §7has been invited to your kingdom.");
            target.sendMessage(main.textUtils.right + "§5" + player.getName() + " §7invited you to his kingdom.");
            target.sendMessage(main.textUtils.right + "To join use /kingdoms accept");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
