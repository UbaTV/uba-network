package xyz.ubatv.bungee.mysql;

import xyz.ubatv.bungee.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnections {

    private Main main = Main.getInstance();

    private String host, username, password;
    private int port;

    private Connection mainDatabase;

    public void setCredentials(){
        host = main.configYML.getConfiguration().getString("host");
        port = main.configYML.getConfiguration().getInt("port");
        password = main.configYML.getConfiguration().getString("password");
        username = main.configYML.getConfiguration().getString("username");
        main.getLogger().info(host + " " + port + " " + password + " " + username);
    }

    public void connectMainDatabase(){
        main.getProxy().getScheduler().runAsync(main, new Runnable() {
            @Override
            public void run() {
                try{
                    if(mainDatabase != null && !mainDatabase.isClosed()){
                        return;
                    }
                    Class.forName("com.mysql.jdbc.Driver");
                    mainDatabase = DriverManager.getConnection("jdbc:mysql://"
                                    + host + ":" + port + "/"
                                    + "ubanetwork-main" + "?autoReconnect=true&useUnicode=yes&useSSL=false"
                            , username, password);
                }catch (SQLException | ClassNotFoundException e){
                    main.getLogger().severe("MySQL connection failed. Reconecting...");
                    connectMainDatabase();
                }
            }
        });
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
