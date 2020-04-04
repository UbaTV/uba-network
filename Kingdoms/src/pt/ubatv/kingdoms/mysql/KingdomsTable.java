package pt.ubatv.kingdoms.mysql;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KingdomsTable {

    private Main main = Main.getInstance();

    public boolean kingdomExists(String kingdomName){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM kingdoms WHERE name=?");
            statement.setString(1, kingdomName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateVault(String kingdomName, int coins){
        if(coins < 0) coins = 0;
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("UPDATE kingdoms SET vault=? WHERE name=?");
            statement.setInt(1, coins);
            statement.setString(2, kingdomName.toLowerCase());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCoins(String kingdomName){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM kingdoms WHERE name=?");
            statement.setString(1, kingdomName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("vault");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void updateOwner(String kingdomName, Player owner){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("UPDATE kingdoms SET owner=? WHERE name=?");
            statement.setString(1, owner.getName());
            statement.setString(2, kingdomName.toLowerCase());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getOwner(String kingdomName){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM kingdoms WHERE name=?");
            statement.setString(1, kingdomName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("owner");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateMembers(String kingdomName, String members){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("UPDATE kingdoms SET members=? WHERE name=?");
            statement.setString(1, members);
            statement.setString(2, kingdomName.toLowerCase());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getMembers(String kingdomName){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM kingdoms WHERE name=?");
            statement.setString(1, kingdomName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("members");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateDisplayName(String kingdomName, String displayName){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("UPDATE kingdoms SET display_name=? WHERE name=?");
            statement.setString(1, displayName);
            statement.setString(2, kingdomName.toLowerCase());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDisplayName(String kingdomName){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM kingdoms WHERE name=?");
            statement.setString(1, kingdomName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("display_name");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteKingdom(String kingdomName){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("DELETE FROM kingdoms WHERE name=?");
            statement.setString(1, kingdomName.toLowerCase());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}