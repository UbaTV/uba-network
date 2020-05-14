package xyz.ubatv.pvegame;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.pvegame.config.LocationYML;
import xyz.ubatv.pvegame.events.ChatManager;
import xyz.ubatv.pvegame.events.JoinQuitEvent;
import xyz.ubatv.pvegame.game.GameManager;
import xyz.ubatv.pvegame.mysql.Main_Bank;
import xyz.ubatv.pvegame.mysql.Main_UserData;
import xyz.ubatv.pvegame.mysql.MySQLConnections;
import xyz.ubatv.pvegame.rankSystem.RankManager;
import xyz.ubatv.pvegame.userData.UserDataManager;
import xyz.ubatv.pvegame.utils.TextUtils;

import java.util.UUID;

public class Main extends JavaPlugin {

    public static Main instance;

    public Main_UserData mainUserData;
    public Main_Bank mainBank;
    public MySQLConnections mySQLConnections;
    public TextUtils textUtils;
    public UserDataManager userDataManager;
    public RankManager rankManager;
    public LocationYML locationYML;
    public GameManager gameManager;

    @Override
    public void onEnable() {
        setInstance(this);
        setInstances();

        loadConfig();
        locationYML.setupLocations();

        mySQLConnections.setCredentials();
        mySQLConnections.connectMainDatabase();

        registerChannels();
        registerCommands();
        registerEvents();

        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            userDataManager.loadUserData(player);
        }

        gameManager.initGame();
    }

    @Override
    public void onDisable() {
        if(!UserDataManager.userData.isEmpty()){
            for(UUID uuid : UserDataManager.userData.keySet()){
                userDataManager.saveUserData(uuid);
            }
        }
    }

    private void registerCommands(){
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new JoinQuitEvent(), this);
        pluginManager.registerEvents(new ChatManager(), this);
    }

    private void setInstances(){
        mainUserData = new Main_UserData();
        mySQLConnections = new MySQLConnections();
        textUtils = new TextUtils();
        userDataManager = new UserDataManager();
        rankManager = new RankManager();
        mainBank = new Main_Bank();
        locationYML = new LocationYML();
        gameManager = new GameManager();
    }

    private void registerChannels(){
        // BungeeCord Main Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // USER DATA CHANNEL
        getServer().getMessenger().registerIncomingPluginChannel(this, "ubanetwork:userdata", new UserDataManager());
    }

    private void loadConfig(){
        getConfig().options().copyDefaults();
        saveConfig();
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
