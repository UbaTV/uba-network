package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.userData.UserData;
import xyz.ubatv.kingdoms.userData.UserDataManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TagSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "tag";
    }

    @Override
    public String getDescription() {
        return "Change the kingdoms tag";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms tag §5<tag>";
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

            if(!main.kingdomsTable.getKing(userKingdom).equalsIgnoreCase(player.getName())){
                player.sendMessage(main.textUtils.error + "You must be king to change the tag.");
                return;
            }

            if(!main.kingdomsTable.getTag(userKingdom).equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "Kingdom tag can only be changed once.");
                return;
            }

            String tag = args[1];
            if(!(2 <= tag.length() && tag.length() <= 6)){
                player.sendMessage(main.textUtils.error + "Tag length must be between 2 and 6 characters");
                return;
            }

            ArrayList<String> bannedTags = main.kingdomUtils.bannedNames();
            if(bannedTags.contains(tag.toLowerCase())){
                player.sendMessage(main.textUtils.error + "This kingdom tag has been banned from being used.");
                return;
            }

            try{
                PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("" +
                        "SELECT * FROM kingdoms WHERE tag=?");
                statement.setString(1, tag.toLowerCase());
                ResultSet rs = statement.executeQuery();

                if(!rs.next()){
                    main.kingdomsTable.updateTag(userKingdom, tag.toLowerCase());
                    main.kingdomsTable.updateDisplayTag(userKingdom, tag);
                    main.kingdomUtils.broadcastKingdom(userKingdom, "Your kingdom's tag has been updated to §5" + tag);
                }else{
                    player.sendMessage(main.textUtils.error + "A kingdom with this tag already exists");
                }
            }catch (SQLException | NullPointerException e){
                player.sendMessage(main.textUtils.error + "An error occurred please contact a staff member.");
                e.printStackTrace();
            }
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
