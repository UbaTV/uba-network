package xyz.ubatv.pve.mysql;

import xyz.ubatv.pve.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Main_Bank {

    private Main main = Main.getInstance();

    public void updateCoins(UUID uuid, int coins){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("UPDATE bank SET pve=? WHERE uuid=?");
            statement.setInt(1, coins);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCoins(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("SELECT * FROM bank WHERE uuid=?");
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
