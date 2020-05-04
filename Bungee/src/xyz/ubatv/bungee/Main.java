package xyz.ubatv.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import xyz.ubatv.bungee.configs.ConfigYML;
import xyz.ubatv.bungee.mysql.MySQLConnection;

public class Main extends Plugin {

    public static Main instance;
    public ConfigYML configYML;
    public MySQLConnection mySQLConnection;

    @Override
    public void onEnable() {
        setInstance(this);
        setInstances();

        configYML.loadConfig();
        mySQLConnection.runMySQLAsync();

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands(){
    }

    private void registerEvents(){
    }

    private void setInstances(){
        configYML = new ConfigYML();
        mySQLConnection = new MySQLConnection();
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
