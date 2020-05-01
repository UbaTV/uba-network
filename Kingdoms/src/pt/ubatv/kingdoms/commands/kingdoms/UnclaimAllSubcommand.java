package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

import java.util.ArrayList;

public class UnclaimAllSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "unclaimall";
    }

    @Override
    public String getDescription() {
        return "Unclaim all chunks for your kingdom";
    }

    @Override
    public String getSyntax() {
        return "/kingdoms unclaimall";
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
            player.sendMessage(main.textUtils.error + "You must be king to unclaim all land.");
            return;
        }

        if(args.length == 1){
            ArrayList<Chunk> kingdomClaims = main.kingdomUtils.getKingdomClaims(userKingdom);
            if(kingdomClaims.isEmpty()){
                player.sendMessage(main.textUtils.error + "You dont have any claims.");
                return;
            }

            for(Chunk chunk : kingdomClaims){
                main.kingdomUtils.removeClaim(userKingdom, chunk);
            }
            main.kingdomsYML.removeKingdomHome(userKingdom);
            player.sendMessage(main.textUtils.right + "Chunks unclaimed successfully");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax!");
        player.sendMessage(main.textUtils.warning + getSyntax());
        return;
    }
}
