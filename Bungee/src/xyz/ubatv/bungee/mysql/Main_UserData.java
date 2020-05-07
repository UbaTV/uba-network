package xyz.ubatv.bungee.mysql;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.ubatv.bungee.Main;
import xyz.ubatv.bungee.rankSystem.Rank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Main_UserData {

    private Main main = Main.getInstance();

    public boolean userExists(UUID uuid){
        try {
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
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
            PreparedStatement statement = main.mySQLConnections.getMainDatabase().prepareStatement("SELECT * FROM user_data WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if(!userExists(uuid)){
                PreparedStatement insert = main.mySQLConnections.getMainDatabase().prepareStatement("INSERT INTO user_data (uuid,name,rank) VALUES (?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setString(3, Rank.MEMBER.toString());
                insert.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
