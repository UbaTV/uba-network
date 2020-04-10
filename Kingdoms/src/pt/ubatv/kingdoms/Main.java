package pt.ubatv.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pt.ubatv.kingdoms.commands.*;
import pt.ubatv.kingdoms.commands.econ.BalanceCommand;
import pt.ubatv.kingdoms.commands.econ.EconCommand;
import pt.ubatv.kingdoms.commands.econ.PayCommand;
import pt.ubatv.kingdoms.commands.kingdoms.KingdomUtils;
import pt.ubatv.kingdoms.commands.kingdoms.KingdomsManager;
import pt.ubatv.kingdoms.commands.shop.*;
import pt.ubatv.kingdoms.commands.staff.*;
import pt.ubatv.kingdoms.configs.CooldownYML;
import pt.ubatv.kingdoms.configs.KingdomClaimYML;
import pt.ubatv.kingdoms.configs.KingdomsYML;
import pt.ubatv.kingdoms.configs.LocationYML;
import pt.ubatv.kingdoms.events.*;
import pt.ubatv.kingdoms.mysql.BankTable;
import pt.ubatv.kingdoms.mysql.KingdomsTable;
import pt.ubatv.kingdoms.mysql.MySQLConnection;
import pt.ubatv.kingdoms.mysql.UserDataTable;
import pt.ubatv.kingdoms.rankSystem.RankCommand;
import pt.ubatv.kingdoms.rankSystem.RankManager;
import pt.ubatv.kingdoms.utils.*;

import java.util.ArrayList;
import java.util.Map;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;
    public PriceUtils priceUtils;
    public ItemAPI itemAPI;
    public MySQLConnection mySQLConnection;
    public UserDataTable userDataTable;
    public BankTable bankTable;
    public LocationYML locationYML;
    public KingdomClaimYML kingdomClaimYML;
    public KingdomsYML kingdomsYML;
    public CooldownYML cooldownYML;
    public RankManager rankManager;
    public ShopUtils shopUtils;
    public KingdomsTable kingdomsTable;
    public KingdomUtils kingdomUtils;

    @Override
    public void onEnable() {
        setInstance(this);
        instanceClasses();

        loadConfig();
        locationYML.createConfig();
        kingdomClaimYML.createConfig();
        kingdomsYML.createConfig();
        cooldownYML.createConfig();

        mySQLConnection.runMySQLAsync();

        for(String kingdomName : kingdomClaimYML.getConfig().getConfigurationSection("").getKeys(false)) {
            kingdomClaimYML.loadKingdomClaims(kingdomName);
        }
        cooldownYML.loadConfig();

        registerEvents();
        registerCommands();

        locationYML.setupSpawn();
        updateScoreboards();
        cooldownYML.cooldownManager();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(target -> {
            userDataTable.saveUserData(target);
            target.kickPlayer("Server is restarting. Please reconnect.");
        });

        cooldownYML.saveShutdown();

        for(Map.Entry<String, ArrayList<Chunk>> entry : KingdomUtils.kingdomsChunks.entrySet()){
            String kingdomName = entry.getKey();
            kingdomClaimYML.saveKingdomClaims(kingdomName);
        }
    }

    private void registerCommands(){
        getCommand("test").setExecutor(new TestCommand());
        getCommand("setlocation").setExecutor(new SetLocationCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("economy").setExecutor(new EconCommand());
        getCommand("kingdoms").setExecutor(new KingdomsManager());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("hologram").setExecutor(new HologramCommand());
        getCommand("shopnpc").setExecutor(new ShopNPCCommand());
        getCommand("clearinventory").setExecutor(new ClearInventoryCommand());
        getCommand("wild").setExecutor(new WildCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("sethome").setExecutor(new SethomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("delhome").setExecutor(new DelhomeCommand());
        getCommand("rankup").setExecutor(new RankCommand());
        getCommand("stats").setExecutor(new StatsCommand());
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        //pluginManager.registerEvents(new DeveloperMode(), this);
        pluginManager.registerEvents(new JoinQuitEvent(), this);
        //pluginManager.registerEvents(new KillRewards(), this);
        pluginManager.registerEvents(new ChatManager(), this);
        pluginManager.registerEvents(new ShopGUI(), this);
        pluginManager.registerEvents(new BlockGUI(), this);
        pluginManager.registerEvents(new FarmingGUI(), this);
        pluginManager.registerEvents(new MiscGUI(), this);
        pluginManager.registerEvents(new OresGUI(), this);
        pluginManager.registerEvents(new MobDropsGUI(), this);
        pluginManager.registerEvents(new FoodGUI(), this);
        pluginManager.registerEvents(new RaidGUI(), this);
        pluginManager.registerEvents(new DeathEvent(), this);
        pluginManager.registerEvents(new TestCommand(), this);
        pluginManager.registerEvents(new ShopNPCCommand(), this);
        pluginManager.registerEvents(new EntityDamage(), this);
        pluginManager.registerEvents(new ClaimManager(), this);
        pluginManager.registerEvents(new VoteEvent(), this);
    }

    private void instanceClasses(){
        textUtils = new TextUtils();
        priceUtils = new PriceUtils();
        mySQLConnection = new MySQLConnection();
        itemAPI = new ItemAPI();
        userDataTable = new UserDataTable();
        bankTable = new BankTable();
        locationYML = new LocationYML();
        rankManager = new RankManager();
        shopUtils = new ShopUtils();
        kingdomsTable = new KingdomsTable();
        kingdomUtils = new KingdomUtils();
        kingdomClaimYML = new KingdomClaimYML();
        kingdomsYML = new KingdomsYML();
        cooldownYML = new CooldownYML();
    }

    private void loadConfig(){
        getConfig().options().copyDefaults();
        saveConfig();
    }

    private void updateScoreboards(){
        new BukkitRunnable(){
            @Override
            public void run(){
                for(Player player : Bukkit.getOnlinePlayers()){
                    //userDataTable.saveUserData(player);
                    if(ScoreboardUtils.hasScoreboard(player)){
                        int online = Bukkit.getServer().getOnlinePlayers().size();
                        int max = Bukkit.getServer().getMaxPlayers();
                        player.setPlayerListHeaderFooter(
                                textUtils.serverName + " §6§o§lBETA MODE\n" +
                                        "§aOnline: §5" + online + "§7/§5" + max,
                                "§7Website: §5" + textUtils.website);

                        UserData userData = userDataTable.online.get(player.getUniqueId());
                        ScoreboardUtils scoreboardUtils = ScoreboardUtils.getScoreboard(player);
                        scoreboardUtils.setSlot(6, "§6| §7Coins: §5" + userData.getCoins() + textUtils.coinsSymbol);
                        scoreboardUtils.setSlot(5, "§a| §7Rank: " + rankManager.getRankName(userData.getRank(), true));
                        scoreboardUtils.setSlot(4, "§d| §7Kills: §5" + userData.getKills());
                        scoreboardUtils.setSlot(3, "§c| §7Deaths: §5" + userData.getDeaths());
                    }
                }
            }
        }.runTaskTimer(this, 20L * 5, 20L);
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
