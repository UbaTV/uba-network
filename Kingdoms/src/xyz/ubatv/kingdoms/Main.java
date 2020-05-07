package xyz.ubatv.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.kingdoms.commands.*;
import xyz.ubatv.kingdoms.commands.econ.BalanceCommand;
import xyz.ubatv.kingdoms.commands.econ.EconCommand;
import xyz.ubatv.kingdoms.commands.econ.PayCommand;
import xyz.ubatv.kingdoms.commands.kingdoms.KingdomUtils;
import xyz.ubatv.kingdoms.commands.kingdoms.KingdomsManager;
import xyz.ubatv.kingdoms.commands.shop.*;
import xyz.ubatv.kingdoms.commands.staff.*;
import xyz.ubatv.kingdoms.configs.CooldownYML;
import xyz.ubatv.kingdoms.configs.KingdomClaimYML;
import xyz.ubatv.kingdoms.configs.KingdomsYML;
import xyz.ubatv.kingdoms.configs.LocationYML;
import xyz.ubatv.kingdoms.events.*;
import xyz.ubatv.kingdoms.guis.KingdomsShopGUI;
import xyz.ubatv.kingdoms.guis.VoteGUI;
import xyz.ubatv.kingdoms.lunchbox.LunchboxCommand;
import xyz.ubatv.kingdoms.lunchbox.LunchboxGUI;
import xyz.ubatv.kingdoms.lunchbox.PlaceLunchboxCommand;
import xyz.ubatv.kingdoms.mysql.BankTable;
import xyz.ubatv.kingdoms.mysql.KingdomsTable;
import xyz.ubatv.kingdoms.mysql.MySQLConnections;
import xyz.ubatv.kingdoms.mysql.UserDataTable;
import xyz.ubatv.kingdoms.rankSystem.RankCommand;
import xyz.ubatv.kingdoms.rankSystem.RankManager;
import xyz.ubatv.kingdoms.silkspawner.BreakPlaceSpawner;
import xyz.ubatv.kingdoms.utils.*;

import java.util.ArrayList;
import java.util.Map;

public class Main extends JavaPlugin {

    public static Main instance;

    public TextUtils textUtils;
    public PriceUtils priceUtils;
    public ItemAPI itemAPI;
    public MySQLConnections mySQLConnections;
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

        mySQLConnections.setCredentials();
        mySQLConnections.connectMainDatabase();
        mySQLConnections.connectKingdomsDatabase();

        for(String kingdomName : kingdomClaimYML.getConfig().getConfigurationSection("").getKeys(false)) {
            kingdomClaimYML.loadKingdomClaims(kingdomName);
        }

        registerEvents();
        registerCommands();

        locationYML.setupSpawn();
        updateScoreboards();

        cooldownYML.loadConfig();
        cooldownYML.cooldownStarterKit();
        cooldownYML.cooldownVipKit();
        cooldownYML.cooldownMvpKit();
        cooldownYML.cooldownWild();

        saveUserData();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(target -> {
            userDataTable.saveUserData(target);
            target.kickPlayer("§5Server is restarting. Please reconnect.");
        });

        cooldownYML.saveShutdown();

        for(Map.Entry<String, ArrayList<Chunk>> entry : KingdomUtils.kingdomsChunks.entrySet()){
            String kingdomName = entry.getKey();
            kingdomClaimYML.saveKingdomClaims(kingdomName);
        }
    }

    private void registerCommands(){
        getCommand("test").setExecutor(new TestCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("vote").setExecutor(new VoteCommand());
        getCommand("staff").setExecutor(new StaffCommand());
        getCommand("privatechest").setExecutor(new PrivateChestCommand());
        getCommand("ranks").setExecutor(new RanksCommand());
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
        getCommand("rankup").setExecutor(new RankupCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("pm").setExecutor(new PrivateMessageCommand());
        getCommand("links").setExecutor(new LinksCommand());
        getCommand("placelunchbox").setExecutor(new PlaceLunchboxCommand());
        getCommand("lunchbox").setExecutor(new LunchboxCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
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
        pluginManager.registerEvents(new VoteGUI(), this);
        pluginManager.registerEvents(new LunchboxGUI(), this);
        pluginManager.registerEvents(new ObsidianDestroy(), this);
        pluginManager.registerEvents(new TreeCapitator(), this);
        pluginManager.registerEvents(new KingdomsShopGUI(), this);
        pluginManager.registerEvents(new MoveEvent(), this);
        pluginManager.registerEvents(new BreakPlaceSpawner(), this);
    }

    private void instanceClasses(){
        textUtils = new TextUtils();
        priceUtils = new PriceUtils();
        mySQLConnections = new MySQLConnections();
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

    private void saveUserData(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    userDataTable.saveUserData(player);
                }
            }
        }.runTaskTimer(this, 20L * 60 * 5,20L * 60 * 20);
    }

    private void updateScoreboards(){
        new BukkitRunnable(){
            @Override
            public void run(){
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(ScoreboardUtils.hasScoreboard(player)){
                        // Tab Header and Footer
                        int online = Bukkit.getServer().getOnlinePlayers().size();
                        int max = Bukkit.getServer().getMaxPlayers();
                        player.setPlayerListHeaderFooter(
                                textUtils.serverName + " §6§o§lBETA MODE\n" +
                                        "§aOnline: §5" + online + "§7/§5" + max,
                                "§7Website: §5" + textUtils.website);

                        UserData userData = userDataTable.online.get(player.getUniqueId());
                        ScoreboardUtils scoreboardUtils = ScoreboardUtils.getScoreboard(player);

                        // Below Name
                        scoreboardUtils.updateBelowName(player);

                        // Sidebar Scoreboard
                        scoreboardUtils.setSlot(6, "§6| §7Coins: §5" + userData.getCoins() + textUtils.coinsSymbol);
                        scoreboardUtils.setSlot(5, "§a| §7Rank: " + rankManager.getRankName(userData.getRank(), true));
                        scoreboardUtils.setSlot(4, "§d| §7Kills: §5" + userData.getKills());
                        scoreboardUtils.setSlot(3, "§c| §7Deaths: §5" + userData.getDeaths());
                    }
                }
            }
        }.runTaskTimer(this, 0, 20L*15);
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
