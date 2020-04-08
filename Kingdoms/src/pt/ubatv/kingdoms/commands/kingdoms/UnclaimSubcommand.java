package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

public class UnclaimSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getDescription() {
        return "Unclaim a chunk for your kingdom";
    }

    @Override
    public String getSyntax() {
        return "/kingdoms unclaim";
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
            player.sendMessage(main.textUtils.error + "You must be king to unclaim land.");
            return;
        }

        if(args.length == 1){
            Chunk chunk = player.getLocation().getChunk();
            String claimedBy = main.kingdomUtils.getChunkClaim(chunk);
            if(!claimedBy.equalsIgnoreCase(userKingdom)){
                player.sendMessage(main.textUtils.warning + "You can only unclaim your kingdoms land.");
                return;
            }

            if(main.locationYML.getConfig().contains(userKingdom.toLowerCase())){
                Location homeLocation = main.locationYML.getLocation(userKingdom.toLowerCase());
                if(homeLocation.getChunk().equals(chunk)){
                    player.sendMessage(main.textUtils.warning + "Change your kingdom's home location before unclaiming land.");
                    return;
                }
            }

            main.kingdomUtils.removeClaim(userKingdom, chunk);
            player.sendMessage(main.textUtils.right + "Chunk unclaimed successfully");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax!");
        player.sendMessage(main.textUtils.warning + getSyntax());
        return;
    }
}
