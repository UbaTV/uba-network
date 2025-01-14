package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.userData.UserData;
import xyz.ubatv.kingdoms.userData.UserDataManager;

public class ClaimSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getDescription() {
        return "Claim a chunk for your kingdom";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms claim";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = UserDataManager.usersData.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();
        if(userKingdom.equalsIgnoreCase("none")){
            player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
            return;
        }

        if(!main.kingdomsTable.getKing(userKingdom).equalsIgnoreCase(player.getName())){
            player.sendMessage(main.textUtils.error + "You must be king to claim land.");
            return;
        }

        if(args.length == 1){
            Chunk chunk = player.getLocation().getChunk();
            String claimedBy = main.kingdomUtils.getChunkClaim(chunk);
            if(!claimedBy.equals("none")){
                if(claimedBy.equalsIgnoreCase(userKingdom)){
                    player.sendMessage(main.textUtils.error + "This chunk is claimed by your kingdom");
                }else{
                    player.sendMessage(main.textUtils.error + "This chunk is claimed by §5" + main.kingdomsTable.getDisplayName(claimedBy));
                }
                return;
            }

            if(!userKingdom.equalsIgnoreCase("UbaTV")){
                if(main.kingdomUtils.getNumberClaims(userKingdom) >= main.kingdomUtils.getKingdomMaxClaims(userKingdom)){
                    player.sendMessage(main.textUtils.error + "You can't claim any more land.");
                    return;
                }
            }

            main.kingdomUtils.addClaim(userKingdom, chunk);
            player.sendMessage(main.textUtils.right + "Chunk claimed successfully");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax!");
        player.sendMessage(main.textUtils.warning + getSyntax());
        return;
    }
}
