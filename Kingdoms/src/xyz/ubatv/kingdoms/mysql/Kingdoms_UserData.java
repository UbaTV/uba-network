package xyz.ubatv.kingdoms.mysql;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.ServerRank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Kingdoms_UserData {

    private Main main = Main.getInstance();

    public boolean userExists(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(Player player) {
        UUID uuid = player.getUniqueId();
        try{
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if(!userExists(uuid)){
                PreparedStatement insert = main.mySQLConnections.getKingdomsDatabase().prepareStatement("INSERT INTO user_data (uuid,name,kills,deaths,kingdom,serverRank) VALUES (?,?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.setInt(4, 0);
                insert.setString(5, "none");
                insert.setString(5, ServerRank.WOOD.toString().toUpperCase());
                insert.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateKills(UUID uuid, int kills){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("UPDATE user_data SET kills=? WHERE uuid=?");
            statement.setInt(1, kills);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getKills(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("kills");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void updateDeaths(UUID uuid, int deaths){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("UPDATE user_data SET deaths=? WHERE uuid=?");
            statement.setInt(1, deaths);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getDeaths(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("deaths");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void updateKingdom(UUID uuid, String kingdomName){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("UPDATE user_data SET kingdom=? WHERE uuid=?");
            statement.setString(1, kingdomName);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getKingdom(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("kingdom");
        } catch (SQLException e) {
            e.printStackTrace();
            return "none";
        }
    }

    public void updateServerRank(UUID uuid, ServerRank serverRank){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("UPDATE user_data SET serverRank=? WHERE uuid=?");
            statement.setString(1, serverRank.toString().toUpperCase());
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ServerRank getServerRank(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getKingdomsDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return ServerRank.valueOf(resultSet.getString("serverRank"));
        } catch (SQLException e) {
            e.printStackTrace();
            return ServerRank.WOOD;
        }
    }
}
