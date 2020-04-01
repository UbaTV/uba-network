package pt.ubatv.kingdoms.mysql;

import pt.ubatv.kingdoms.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BankTable {

    private Main main = Main.getInstance();

    public boolean userExists(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM bank WHERE uuid=?");
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
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM bank WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if(!userExists(uuid)){
                PreparedStatement insert = main.mySQLConnection.getConnection().prepareStatement("INSERT INTO bank (uuid,kingdoms) VALUES (?,?)");
                insert.setString(1, uuid.toString());
                insert.setInt(2, 0);
                insert.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateCoins(UUID uuid, int coins){
        if(coins < 0) coins = 0;
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("UPDATE bank SET kingdoms=? WHERE uuid=?");
            statement.setInt(1, coins);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCoins(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnection.getConnection().prepareStatement("SELECT * FROM bank WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("kingdoms");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
