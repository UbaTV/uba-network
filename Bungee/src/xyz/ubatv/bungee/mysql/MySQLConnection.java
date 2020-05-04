package xyz.ubatv.bungee.mysql;

import xyz.ubatv.bungee.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private Main main = Main.getInstance();

    private Connection connection;
    private String host, database, username, password;
    private int port;

    private void connectMySQL(){
        host = main.configYML.getConfiguration().getString("host");
        port = main.configYML.getConfiguration().getInt("port");
        database = main.configYML.getConfiguration().getString("database");
        password = main.configYML.getConfiguration().getString("password");
        username = main.configYML.getConfiguration().getString("username");

        try {
            if(connection != null && !connection.isClosed()){
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://"
                            + this.host + ":" + this.port + "/"
                            + this.database + "?autoReconnect=true&useUnicode=yes&useSSL=false"
                    , this.username, this.password);
        } catch (SQLException | ClassNotFoundException e) {
            main.getLogger().severe("MYSQL CONNECTION FAILED. RECONECTING...");
            connectMySQL();
        }
    }

    public void runMySQLAsync(){
        main.getProxy().getScheduler().runAsync(main, new Runnable() {
            @Override
            public void run() {
                connectMySQL();
            }
        });
    }

    public Connection getConnection() {
        try {
            if(connection.isClosed()){
                runMySQLAsync();
            }
        } catch (SQLException e) {
            runMySQLAsync();
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
