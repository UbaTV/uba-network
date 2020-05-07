package xyz.ubatv.kingdoms.mysql;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.kingdoms.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnections {

    private Main main = Main.getInstance();

    private String host, username, password;
    private int port;

    private Connection mainDatabase;
    private Connection kingdomsDatabase;

    public void setCredentials(){
        host = main.getConfig().getString("host");
        port = main.getConfig().getInt("port");
        password = main.getConfig().getString("password");
        username = main.getConfig().getString("username");
    }

    public void connectMainDatabase(){
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if(mainDatabase != null && !mainDatabase.isClosed()){
                        return;
                    }
                    Class.forName("com.mysql.jdbc.Driver");
                    mainDatabase = DriverManager.getConnection("jdbc:mysql://"
                                    + host + ":" + port + "/"
                                    + "ubanetwork-main" + "?autoReconnect=true&useUnicode=yes&useSSL=false"
                            , username, password);
                } catch (SQLException | ClassNotFoundException e) {
                    Bukkit.getServer().getConsoleSender().sendMessage("MySQL Main connection failed. Reconnecting...");
                    connectMainDatabase();
                }
            }
        };

        r.runTaskAsynchronously(main);
    }

    public void connectKingdomsDatabase(){
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if(mainDatabase != null && !mainDatabase.isClosed()){
                        return;
                    }
                    Class.forName("com.mysql.jdbc.Driver");
                    mainDatabase = DriverManager.getConnection("jdbc:mysql://"
                                    + host + ":" + port + "/"
                                    + "ubanetwork-kingdoms" + "?autoReconnect=true&useUnicode=yes&useSSL=false"
                            , username, password);
                } catch (SQLException | ClassNotFoundException e) {
                    Bukkit.getServer().getConsoleSender().sendMessage("MySQL Kingdoms connection failed. Reconnecting...");
                    connectMainDatabase();
                }
            }
        };

        r.runTaskAsynchronously(main);
    }

    public Connection getMainDatabase() {
        try {
            if(mainDatabase.isClosed()){
                connectMainDatabase();
            }
        } catch (SQLException e) {
            connectMainDatabase();
        }
        return mainDatabase;
    }

    public Connection getKingdomsDatabase() {
        try {
            if(kingdomsDatabase.isClosed()){
                connectKingdomsDatabase();
            }
        } catch (SQLException e) {
            connectKingdomsDatabase();
        }
        return kingdomsDatabase;
    }
}
