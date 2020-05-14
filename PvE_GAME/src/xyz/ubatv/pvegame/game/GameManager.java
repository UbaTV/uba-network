package xyz.ubatv.pvegame.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.pvegame.Main;
import xyz.ubatv.pvegame.userData.PlayerStatus;
import xyz.ubatv.pvegame.userData.UserData;
import xyz.ubatv.pvegame.userData.UserDataManager;

import java.util.UUID;

public class GameManager {

    private Main main = Main.getInstance();

    public int minPlayers = 2;
    public int maxPlayers = 6;

    public GameState gameState;
    public boolean mobSpawn;
    public int lobbyTimer;
    public int gameTimer;

    public void initGame(){
        gameState = GameState.LOBBY;
        mobSpawn = false;
        lobbyTimer = 10;
        gameTimer = 0;
        main.locationYML.game.getWorld().setTime(0);
        startLobby();
    }

    public void startLobby(){
        /*
        TODO
        Move Player
        Change inventory
         */
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            UserData playerData = UserDataManager.userData.get(player.getUniqueId());
            playerData.setPlayerStatus(PlayerStatus.LOBBY);
            player.teleport(main.locationYML.lobby);
        }

        gameState = GameState.LOBBY;
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
                    lobbyTimer = 10;
                }
            }
        };

        r.runTaskTimerAsynchronously(main, 0, 20);
    }

    public void startGame(){
        gameTimer = 0;
        lobbyTimer = 10;
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
                long worldTime = main.locationYML.game.getWorld().getTime();
                if(13000 <= worldTime && worldTime <= 23000){
                    if(lobbyTimer == 13000) Bukkit.getServer().getOnlinePlayers().forEach(
                            player -> player.sendTitle("§5Night §7has started", "§cKill all mobs and survive", 0, 20, 0));
                    mobSpawn = true;
                }else{
                    mobSpawn = false;
                }
            }
        };

        r.runTaskTimerAsynchronously(main, 0, 20);
    }

    public void stopGame(){
        lobbyTimer = 10;
        gameTimer = 0;
    }
}
