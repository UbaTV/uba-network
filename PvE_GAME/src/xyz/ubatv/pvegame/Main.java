package xyz.ubatv.pvegame;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.ubatv.pvegame.mysql.Main_UserData;
import xyz.ubatv.pvegame.mysql.MySQLConnections;
import xyz.ubatv.pvegame.rankSystem.RankManager;
import xyz.ubatv.pvegame.userData.UserDataManager;
import xyz.ubatv.pvegame.utils.TextUtils;

public class Main extends JavaPlugin {

    public static Main instance;

    public Main_UserData mainUserData;
    public MySQLConnections mySQLConnections;
    public TextUtils textUtils;
    public UserDataManager userDataManager;
    public RankManager rankManager;

    @Override
    public void onEnable() {
        setInstance(this);
        setInstances();

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
        mainUserData = new Main_UserData();
        mySQLConnections = new MySQLConnections();
        textUtils = new TextUtils();
        userDataManager = new UserDataManager();
        rankManager = new RankManager();
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
