package xyz.ubatv.pve.game;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.pve.Main;
import xyz.ubatv.pve.userData.PlayerStatus;
import xyz.ubatv.pve.userData.UserData;
import xyz.ubatv.pve.userData.UserDataManager;

import java.util.UUID;

public class GameManager {

    private Main main = Main.getInstance();

    public final int roundDayTime = 60*5; // 5min
    public final int roundNightTime = 60*5; // 5min
    public final int maxRounds = 5;
    public final int minPlayers = 2;
    public final int maxPlayers = 6;

    public int round;
    public GameState gameState;
    public boolean mobSpawn;

    public int lobbyTimer;
    public int gameTimer;
    public int roundTime;

    public void initGame(){
        gameState = GameState.LOBBY;
        mobSpawn = false;
        lobbyTimer = 10;
        gameTimer = 0;
        round = 1;
        roundTime = 0;
        main.locationYML.game.getWorld().setTime(0);
        startLobby();
        Bukkit.broadcastMessage("game init");
    }

    public void startLobby(){
        /*
        TODO
        Change inventory
         */
        gameState = GameState.LOBBY;
        if(Bukkit.getServer().getOnlinePlayers().size() != 0){
            for(Player player : Bukkit.getServer().getOnlinePlayers()){
                UserData playerData = UserDataManager.userData.get(player.getUniqueId());
                playerData.setPlayerStatus(PlayerStatus.LOBBY);
                player.teleport(main.locationYML.lobby);
            }
        }

        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                int online = Bukkit.getServer().getOnlinePlayers().size();
                if(minPlayers <= (online - main.userDataManager.getNumberSpectating())){
                    if(lobbyTimer > 0){
                        if(lobbyTimer == 3) Bukkit.getServer().getOnlinePlayers().forEach(
                                player -> player.sendTitle("§7Game Starting in", "§c3", 0, 20, 0));
                        if(lobbyTimer == 2) Bukkit.getServer().getOnlinePlayers().forEach(
                                player -> player.sendTitle("§7Game Starting in", "§e2", 0, 20, 0));
                        if(lobbyTimer == 1) Bukkit.getServer().getOnlinePlayers().forEach(
                                player -> player.sendTitle("§7Game Starting in", "§a1", 0, 20, 0));
                        lobbyTimer--;
                    }else{
                        Bukkit.getServer().getOnlinePlayers().forEach(
                                player -> {
                                    player.sendTitle("§5Game Started", "", 0, 20*2, 10);
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                                });
                        this.cancel();
                        startGame();
                    }
                }else{
                    if(Bukkit.getOnlinePlayers().size() != 0){
                        for(Player target : Bukkit.getOnlinePlayers()){
                            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                                    main.textUtils.warning + "§7Game needs §5" + minPlayers + " §7players to start."));
                        }
                    }
                    lobbyTimer = 10;
                }
            }
        };

        r.runTaskTimerAsynchronously(main, 0, 20);
    }

    public void startGame(){
        gameState = GameState.ROUND_DAY;
        gameTimer = 0;
        lobbyTimer = 10;
        round = 1;
        roundTime = 0;
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            player.teleport(main.locationYML.game);
            player.setGameMode(GameMode.SURVIVAL);
        }
        for(UUID uuid : main.userDataManager.getSpectatingPlayer()){
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;
            player.setGameMode(GameMode.SPECTATOR);
        }

        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                gameTimer++;
                roundTime++;
                main.dayNightCicle.setWorldTime(roundTime);
                if(0 <= roundTime && roundTime < roundDayTime){
                    // Round Day Time
                    if(roundTime == 0) Bukkit.getServer().getOnlinePlayers().forEach(
                            player -> player.sendTitle("§5Day §7has started", "§cGather weapons to fight.", 0, 20, 0));
                    Bukkit.broadcastMessage("");
                    gameState = GameState.ROUND_DAY;
                }else if(roundDayTime <= roundTime && roundTime <= (roundDayTime + roundNightTime)){
                    // Round Night Time
                    if(roundTime == roundDayTime) Bukkit.getServer().getOnlinePlayers().forEach(
                            player -> player.sendTitle("§5Night §7has started", "§cKill all mobs and survive", 0, 20, 0));
                    gameState = GameState.ROUND_NIGHT;
                }else{
                    // Round End
                    roundTime = 0;
                    if(round >= maxRounds){
                        // End Game
                        endGame();
                        this.cancel();
                    }else{
                        // Change round
                        round++;
                        Bukkit.broadcastMessage(main.textUtils.warning + "Round: §5" + round);
                    }
                }

                long worldTime = main.locationYML.game.getWorld().getTime();
                if(13000 <= worldTime && worldTime <= 23000){
                    if(gameTimer == 13000) Bukkit.getServer().getOnlinePlayers().forEach(
                            player -> player.sendTitle("§5Night §7has started", "§cKill all mobs and survive", 0, 20, 0));
                    mobSpawn = true;
                }else{
                    mobSpawn = false;
                }
            }
        };

        r.runTaskTimerAsynchronously(main, 0, 20);
    }

    public void endGame(){
        lobbyTimer = 10;
        gameTimer = 0;
        round = 1;
    }
}
