package xyz.ubatv.hub;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.hub.commands.SpawnCommand;
import xyz.ubatv.hub.commands.staff.SetLocationCommand;
import xyz.ubatv.hub.configs.LocationYML;
import xyz.ubatv.hub.events.JoinQuitEvent;
import xyz.ubatv.hub.events.MoveEvent;
import xyz.ubatv.hub.mysql.MySQLConnections;
import xyz.ubatv.hub.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;
    public TextUtils textUtils;
    public MySQLConnections mySQLConnections;
    public LocationYML locationYML;

    @Override
    public void onEnable() {
        setInstance(this);
        setInstances();

        // Load configs
        loadConfig();
        locationYML.createConfig();

        // Connect to databases
        mySQLConnections.setCredentials();
        mySQLConnections.connectMainDatabase();

        locationYML.setupSpawn();

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands(){
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setlocation").setExecutor(new SetLocationCommand());
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new JoinQuitEvent(), this);
        pluginManager.registerEvents(new MoveEvent(), this);
    }

    private void setInstances(){
        textUtils = new TextUtils();
        mySQLConnections = new MySQLConnections();
        locationYML = new LocationYML();
    }

    private void loadConfig(){
        getConfig().options().copyDefaults();
        saveConfig();
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }

    public static Main getInstance() {
        return instance;
    }
}
