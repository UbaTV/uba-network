package xyz.ubatv.hub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.hub.commands.SpawnCommand;
import xyz.ubatv.hub.commands.staff.SetLocationCommand;
import xyz.ubatv.hub.configs.LocationYML;
import xyz.ubatv.hub.events.InventoryManager;
import xyz.ubatv.hub.events.JoinQuitEvent;
import xyz.ubatv.hub.events.MoveEvent;
import xyz.ubatv.hub.guis.JoinServerGUI;
import xyz.ubatv.hub.mysql.Main_UserData;
import xyz.ubatv.hub.mysql.MySQLConnections;
import xyz.ubatv.hub.rankSystem.RankManager;
import xyz.ubatv.hub.userData.UserDataManager;
import xyz.ubatv.hub.utils.ItemAPI;
import xyz.ubatv.hub.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;
    public TextUtils textUtils;
    public MySQLConnections mySQLConnections;
    public LocationYML locationYML;
    public ItemAPI itemAPI;
    public Main_UserData mainUserData;
    public UserDataManager userDataManager;
    public RankManager rankManager;

    @Override
    public void onEnable() {
        setInstance(this);
        setInstances();

        // Load configs
        loadConfig();
        locationYML.createConfig();

        registerChannels();
        registerCommands();
        registerEvents();

        // Connect to databases
        mySQLConnections.setCredentials();
        mySQLConnections.connectMainDatabase();

        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            userDataManager.loadUserData(player);
        }

        locationYML.setupSpawn();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(target -> {
            userDataManager.saveUserData(target);
            target.kickPlayer("§5Server is restarting. Please reconnect.");
        });
    }

    private void registerCommands(){
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setlocation").setExecutor(new SetLocationCommand());
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new JoinQuitEvent(), this);
        pluginManager.registerEvents(new MoveEvent(), this);
        pluginManager.registerEvents(new InventoryManager(), this);
        pluginManager.registerEvents(new JoinServerGUI(), this);
    }

    private void registerChannels(){
        // BungeeCord Main Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // USER DATA CHANNEL
        getServer().getMessenger().registerIncomingPluginChannel(this, "ubanetwork:userdata", new UserDataManager());
    }

    private void setInstances(){
        textUtils = new TextUtils();
        mySQLConnections = new MySQLConnections();
        locationYML = new LocationYML();
        itemAPI = new ItemAPI();
        mainUserData = new Main_UserData();
        userDataManager = new UserDataManager();
        rankManager = new RankManager();
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
