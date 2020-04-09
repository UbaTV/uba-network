package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

public class SethomeSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "sethome";
    }

    @Override
    public String getDescription() {
        return "Sets a home for your kingdom";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms sethome";
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
            Location loc = player.getLocation();
            String chunkClaim = main.kingdomUtils.getChunkClaim(loc.getChunk());
            if(!chunkClaim.equalsIgnoreCase(userKingdom)){
                player.sendMessage(main.textUtils.error + "You can only sethome in your factions claims");
                return;
            }

            main.kingdomsYML.setKingdomHome(userKingdom.toLowerCase(), loc);
            main.kingdomUtils.broadcastKingdom(userKingdom, "Kingdom home location has been defined.");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
