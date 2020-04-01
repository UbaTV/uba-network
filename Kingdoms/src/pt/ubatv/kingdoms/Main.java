package pt.ubatv.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pt.ubatv.kingdoms.commands.SetLocationCommand;
import pt.ubatv.kingdoms.commands.SpawnCommand;
import pt.ubatv.kingdoms.commands.econ.BalanceCommand;
import pt.ubatv.kingdoms.commands.econ.PayCommand;
import pt.ubatv.kingdoms.configs.LocationYML;
import pt.ubatv.kingdoms.events.DeveloperMode;
import pt.ubatv.kingdoms.events.JoinQuitEvent;
import pt.ubatv.kingdoms.mysql.BankTable;
import pt.ubatv.kingdoms.mysql.MySQLConnection;
import pt.ubatv.kingdoms.mysql.UserDataTable;
import pt.ubatv.kingdoms.rankSystem.RankManager;
import pt.ubatv.kingdoms.utils.ItemAPI;
import pt.ubatv.kingdoms.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;
    public MySQLConnection mySQLConnection;
    public ItemAPI itemAPI;
    public UserDataTable userDataTable;
    public BankTable bankTable;
    public LocationYML locationYML;
    public RankManager rankManager;

    @Override
    public void onEnable() {
        setInstance(this);
        instanceClasses();

        loadConfig();
        locationYML.createConfig();
        mySQLConnection.runMySQLAsync();

        registerEvents();
        registerCommands();

        locationYML.setupSpawn();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(target -> target.kickPlayer("Server is restarting. Please reconnect."));
    }

    private void registerCommands(){
        getCommand("setlocation").setExecutor(new SetLocationCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("pay").setExecutor(new PayCommand());
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
        userDataTable = new UserDataTable();
        bankTable = new BankTable();
        locationYML = new LocationYML();
        rankManager = new RankManager();
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
