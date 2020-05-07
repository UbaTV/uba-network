package xyz.ubatv.hub.mysql;

import xyz.ubatv.hub.Main;
import xyz.ubatv.hub.rankSystem.Rank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Main_UserData {

    private Main main = Main.getInstance();

    public void updateRank(UUID uuid, Rank rank){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("UPDATE user_data SET rank=? WHERE uuid=?");
            statement.setString(1, rank.toString().toUpperCase());
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Rank getRank(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return Rank.valueOf(resultSet.getString("rank").toUpperCase());
        } catch (SQLException e) {
            e.printStackTrace();
            return Rank.MEMBER;
        }
    }
}
