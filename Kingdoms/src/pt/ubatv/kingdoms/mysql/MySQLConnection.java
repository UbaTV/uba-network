package pt.ubatv.kingdoms.mysql;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pt.ubatv.kingdoms.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private Main main = Main.getInstance();

    private Connection connection;
    private String host, database, username, password;
    private int port;

    private void connectMySQL(){
        host = main.getConfig().getString("host");
        port = main.getConfig().getInt("port");
        database = main.getConfig().getString("database");
        password = main.getConfig().getString("password");
        username = main.getConfig().getString("username");

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
            Bukkit.getServer().getConsoleSender().sendMessage("MYSQL CONNECTION FAILED. RECONECTING...");
            connectMySQL();
        }
    }

    public void runMySQLAsync(){
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                connectMySQL();
            }
        };

        r.runTaskAsynchronously(main);
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
