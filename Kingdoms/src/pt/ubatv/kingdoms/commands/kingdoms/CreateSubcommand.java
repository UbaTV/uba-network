package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            if(!main.userDataTable.online.get(player.getUniqueId()).getKingdom().equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "You are already in a kingdom.");
                return;
            }

            String kingdomName = args[1];
            if(kingdomName.length() > 16){
                player.sendMessage(main.textUtils.error + "Name too long. Kingdom name must be 16 characters or lower.");
                return;
            }

            try{
                PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("" +
                        "SELECT * FROM kingdoms WHERE name=?");
                statement.setString(1, kingdomName.toLowerCase());
                ResultSet rs = statement.executeQuery();

                if(!rs.next()){
                    PreparedStatement insert = main.mySQLConnection.getConnection()
                            .prepareStatement
                                    ("INSERT INTO kingdoms " +
                                            "(name,display_name,owner,vault,members,tag,ally,enemy) " +
                                            "VALUES (?,?,?,?,?,?,?,?)");
                    insert.setString(1, kingdomName.toLowerCase());
                    insert.setString(2, kingdomName);
                    insert.setString(3, player.getName());
                    insert.setInt(4, 0);
                    insert.setString(5, player.getName() + "#");
                    insert.setString(6, "none");
                    insert.setString(7, "none");
                    insert.setString(8, "none");
                    insert.executeUpdate();
                    main.userDataTable.online.get(player.getUniqueId()).setKingdom(kingdomName.toLowerCase());
                    player.sendMessage(main.textUtils.right + "Your kingdom has been created successfully.");
                    Bukkit.getServer().getOnlinePlayers().forEach(
                            (online) -> online
                                    .sendMessage(main.textUtils.right + "§5" + player.getName()
                                            + " §7just created §5" + kingdomName + "§7's Kingdom.")
                    );
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
