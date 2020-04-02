package pt.ubatv.kingdoms.mysql;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Rank;
import pt.ubatv.kingdoms.utils.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class UserDataTable {

    private Main main = Main.getInstance();

    public HashMap<UUID, UserData> online = new HashMap<>();

    public void loadUserData(Player player){
        UUID uuid = player.getUniqueId();
        online.put(uuid,
                new UserData(getRank(uuid),
                        main.bankTable.getCoins(uuid),
                        getMute(uuid)));
    }

    public void saveUserData(Player player){
        UUID uuid = player.getUniqueId();
        UserData userData = online.get(uuid);

        updateRank(uuid, userData.getRank());
        main.bankTable.updateCoins(uuid, userData.getCoins());
        updateMute(uuid, userData.isMute());
    }

    public boolean userExists(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(UUID uuid) {
        try{
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if(!userExists(uuid)){
                PreparedStatement insert = main.mySQLConnection.getConnection().prepareStatement("INSERT INTO user_data (uuid,rank,mute) VALUES (?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, "WOOD");
                insert.setBoolean(3, false);
                insert.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateRank(UUID uuid, Rank rank){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("UPDATE user_data SET rank=? WHERE uuid=?");
            statement.setString(1, rank.toString().toUpperCase());
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Rank getRank(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Rank.valueOf(resultSet.getString("rank").toUpperCase());
        } catch (SQLException e) {
            e.printStackTrace();
            return Rank.WOOD;
        }
    }

    public void updateMute(UUID uuid, boolean muted){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("UPDATE user_data SET mute=? WHERE uuid=?");
            statement.setBoolean(1, muted);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getMute(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("mute");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
