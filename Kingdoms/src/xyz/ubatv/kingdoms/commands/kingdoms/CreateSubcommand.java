package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.userData.UserDataManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a kingdom.";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdom create §5<name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){
            if(!UserDataManager.usersData.get(player.getUniqueId()).getKingdom().equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "You are already in a kingdom.");
                return;
            }

            String kingdomName = args[1];
            if(kingdomName.length() > 16){
                player.sendMessage(main.textUtils.error + "Name too long. Kingdom name must be 16 characters or lower.");
                return;
            }

            if(!main.kingdomUtils.validChars(kingdomName)){
                player.sendMessage(main.textUtils.error + "You kingdom name contains invalid characters.");
                return;
            }

            ArrayList<String> bannedTags = main.kingdomUtils.bannedNames();
            if(bannedTags.contains(kingdomName.toLowerCase())){
                player.sendMessage(main.textUtils.error + "This kingdom name has been banned from being used.");
                return;
            }

            try{
                PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("" +
                        "SELECT * FROM kingdoms WHERE name=?");
                statement.setString(1, kingdomName.toLowerCase());
                ResultSet rs = statement.executeQuery();

                if(!rs.next()){
                    PreparedStatement insert = main.mySQLConnections.getKingdomsDatabase()
                            .prepareStatement
                                    ("INSERT INTO kingdoms " +
                                            "(name,display_name,king,vault,members,tag,display_tag,ally,enemy) " +
                                            "VALUES (?,?,?,?,?,?,?,?,?)");
                    insert.setString(1, kingdomName.toLowerCase());
                    insert.setString(2, kingdomName);
                    insert.setString(3, player.getName());
                    insert.setInt(4, 0);
                    insert.setString(5, player.getName() + "#");
                    insert.setString(6, "none");
                    insert.setString(7, "none");
                    insert.setString(8, "none");
                    insert.setString(9, "none");
                    insert.executeUpdate();
                    UserDataManager.usersData.get(player.getUniqueId()).setKingdom(kingdomName.toLowerCase());
                    Bukkit.getServer().getOnlinePlayers().forEach(
                            (online) -> online
                                    .sendMessage(main.textUtils.right + "§5" + player.getName()
                                            + " §7just created §5" + kingdomName + "§7's Kingdom.")
                    );

                    main.kingdomsYML.getConfig().set(kingdomName.toLowerCase() + ".fly", false);
                    main.kingdomsYML.getConfig().set(kingdomName.toLowerCase() + ".mining_speed", false);
                    main.kingdomsYML.getConfig().set(kingdomName.toLowerCase() + ".obby_speed", false);
                    main.kingdomsYML.getConfig().set(kingdomName.toLowerCase() + ".home", null);

                    player.sendMessage(main.textUtils.right + "Your kingdom has been created successfully.");
                    player.sendMessage(main.textUtils.right + "Do §5/kingdom tag §7to add a prefix for kingdom members.");
                }else{
                    player.sendMessage(main.textUtils.error + "A kingdom with this name already exists");
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
