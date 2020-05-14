package xyz.ubatv.bungee.mysql;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.ubatv.bungee.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Main_Bank {

    private Main main = Main.getInstance();

    public boolean userExists(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("SELECT * FROM bank WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(ProxiedPlayer player) {
        UUID uuid = player.getUniqueId();
        try{
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("SELECT * FROM bank WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if(!userExists(uuid)){
                PreparedStatement insert = main.mySQLConnections.getMainDatabase().prepareStatement("INSERT INTO bank (uuid,name,kingdoms,pve) VALUES (?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.setInt(4, 0);
                insert.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateKingdomsCoins(UUID uuid, int coins){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("UPDATE user_data SET kingdoms=? WHERE uuid=?");
            statement.setInt(1, coins);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getKingdomCoins(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("kingdoms");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void updatePvECoins(UUID uuid, int coins){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("UPDATE user_data SET pve=? WHERE uuid=?");
            statement.setInt(1, coins);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPvECoins(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("pve");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
