package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Shows a kingdoms information";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms info §5[kingdom]";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        if(args.length == 1){
            if(userData.getKingdom().equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
                return;
            }
            String userKingdom = userData.getKingdom();
            showKingdomInfo(player, userKingdom);
            return;
        }

        if(args.length == 2){
            String targetKingdom = args[1].toLowerCase();
            if(main.kingdomsTable.kingdomExists(targetKingdom)){
                showKingdomInfo(player, targetKingdom);
                return;
            }else{
                try{
                    PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("" +
                            "SELECT * FROM kingdoms WHERE tag=?");
                    statement.setString(1, targetKingdom.toLowerCase());
                    ResultSet rs = statement.executeQuery();

                    if(!rs.next()){
                        showKingdomInfo(player, main.kingdomsTable.getDisplayNameByTag(targetKingdom).toLowerCase());
                        return;
                    }else{
                        player.sendMessage(main.textUtils.error + "Kingdom does not exist.");
                        player.sendMessage(main.textUtils.error + "A kingdom with this tag already exists");
                    }
                }catch (SQLException | NullPointerException e){
                    player.sendMessage(main.textUtils.error + "An error occurred please contact a staff member.");
                    e.printStackTrace();
                }
            }
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }

    public void showKingdomInfo(Player player, String kingdomName){
        String[] members = main.kingdomUtils.getMembers(kingdomName);
        String[] allies = main.kingdomUtils.getAllies(kingdomName);

        StringBuilder membersString = new StringBuilder();
        StringBuilder alliesString = new StringBuilder();

        for(String member : members){
            if(Bukkit.getPlayer(member) != null){
                membersString.append("§a").append(member).append(" ");
            }else{
                membersString.append("§c").append(member).append(" ");
            }
        }

        for(String ally : allies){
            if(!ally.equalsIgnoreCase("none")){
                alliesString.append("§5").append(ally).append(" ");
            }
        }

        String tag = main.kingdomsTable.getDisplayTag(kingdomName);
        String king = main.kingdomsTable.getOwner(kingdomName);
        int claims = main.kingdomUtils.getNumberClaims(kingdomName), maxClaims = main.kingdomUtils.getKingdomMaxClaims(kingdomName);
        int vault = main.kingdomsTable.getCoins(kingdomName);
        int level = main.kingdomsTable.getLevel(kingdomName);

        player.sendMessage(" ");
        main.textUtils.sendCenteredMessage(player, "§7§m========[§5" + main.kingdomsTable.getDisplayName(kingdomName) + "§7's Info§7§m]========");
        player.sendMessage(" ");
        player.sendMessage("§7Tag: §5" + tag);
        player.sendMessage("§7King: §5" + king);
        player.sendMessage("§7Claims: §5" + claims + "§7/§5" + maxClaims);
        player.sendMessage("§7Vault: §5" + vault);
        player.sendMessage("§7Level: §5" + level);
        player.sendMessage("§7Members: " + membersString.toString());
        player.sendMessage("§7Allies: " + alliesString.toString());
    }
}
