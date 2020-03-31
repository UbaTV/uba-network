package pt.ubatv.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pt.ubatv.kingdoms.events.DeveloperMode;
import pt.ubatv.kingdoms.events.JoinQuitEvent;
import pt.ubatv.kingdoms.mysql.MySQLConnection;
import pt.ubatv.kingdoms.utils.ItemAPI;
import pt.ubatv.kingdoms.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;
    public MySQLConnection mySQLConnection;
    public ItemAPI itemAPI;

    @Override
    public void onEnable() {
        setInstance(this);
        instanceClasses();

        mySQLConnection.runMySQLAsync();
        loadConfig();

        registerEvents();
        registerCommands();

    }

    @Override
    public void onDisable() {
    }

    private void registerCommands(){

    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new DeveloperMode(), this);
        pluginManager.registerEvents(new JoinQuitEvent(), this);
    }

    private void instanceClasses(){
        textUtils = new TextUtils();
        mySQLConnection = new MySQLConnection();
        itemAPI = new ItemAPI();
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
