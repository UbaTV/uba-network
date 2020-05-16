package xyz.ubatv.pve.mysql;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.pve.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnections {

    private Main main = Main.getInstance();

    private String host, username, password;
    private int port;

    private Connection mainDatabase;

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
                try{
                    if(mainDatabase != null && !mainDatabase.isClosed()){
                        return;
                    }

                    Class.forName("com.mysql.jdbc.Driver");
                    mainDatabase = DriverManager.getConnection("jdbc:mysql://" +
                                    host + ":" + port + "/" +
                                    "ubanetwork-main" + "?autoReconnect=true&useUnicode=yes&useSSL=false",
                                    username, password);
                }catch (SQLException | ClassNotFoundException e){
                    main.getLogger().severe("MySQL connection failed. Reconecting...");
                    connectMainDatabase();
                }
            }
        };

        r.runTaskAsynchronously(main);
    }

    public Connection getMainDatabase() {
        try {
            if(mainDatabase.isClosed() || mainDatabase == null){
                connectMainDatabase();
            }
        } catch (SQLException e) {
            connectMainDatabase();
        }
        return mainDatabase;
    }
}
