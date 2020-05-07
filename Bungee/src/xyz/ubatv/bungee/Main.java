package xyz.ubatv.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import xyz.ubatv.bungee.configs.ConfigYML;
import xyz.ubatv.bungee.events.JoinQuitEvent;
import xyz.ubatv.bungee.mysql.Main_UserData;
import xyz.ubatv.bungee.mysql.MySQLConnections;
import xyz.ubatv.bungee.utils.TextUtils;

public class Main extends Plugin {

    public static Main instance;
    public ConfigYML configYML;
    public MySQLConnections mySQLConnections;
    public Main_UserData mainUserData;
    public TextUtils textUtils;

    @Override
    public void onEnable() {
        setInstance(this);
        setInstances();

        configYML.loadConfig();
        mySQLConnections.setCredentials();
        mySQLConnections.connectMainDatabase();

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands(){
    }

    private void registerEvents(){
        getProxy().getPluginManager().registerListener(this, new JoinQuitEvent());
    }

    private void setInstances(){
        configYML = new ConfigYML();
        mySQLConnections = new MySQLConnections();
        mainUserData = new Main_UserData();
        textUtils = new TextUtils();
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
