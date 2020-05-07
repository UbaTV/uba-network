package xyz.ubatv.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import xyz.ubatv.bungee.commands.HubCommand;
import xyz.ubatv.bungee.configs.ConfigYML;
import xyz.ubatv.bungee.events.JoinQuitEvent;
import xyz.ubatv.bungee.mysql.Main_UserData;
import xyz.ubatv.bungee.mysql.MySQLConnections;
import xyz.ubatv.bungee.rankSystem.RankCommand;
import xyz.ubatv.bungee.rankSystem.RankManager;
import xyz.ubatv.bungee.utils.TextUtils;

public class Main extends Plugin {

    public static Main instance;
    public ConfigYML configYML;
    public MySQLConnections mySQLConnections;
    public Main_UserData mainUserData;
    public TextUtils textUtils;
    public RankManager rankManager;

    @Override
    public void onEnable() {
        setInstance(this);
        setInstances();

        configYML.loadConfig();

        registerChannels();
        registerCommands();
        registerEvents();

        mySQLConnections.setCredentials();
        mySQLConnections.connectMainDatabase();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands(){
        getProxy().getPluginManager().registerCommand(this, new RankCommand());
        getProxy().getPluginManager().registerCommand(this, new HubCommand());
    }

    private void registerEvents(){
        getProxy().getPluginManager().registerListener(this, new JoinQuitEvent());
    }

    private void registerChannels(){
        getProxy().registerChannel("ubanetwork:userdata");
    }

    private void setInstances(){
        configYML = new ConfigYML();
        mySQLConnections = new MySQLConnections();
        mainUserData = new Main_UserData();
        textUtils = new TextUtils();
        rankManager = new RankManager();
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
