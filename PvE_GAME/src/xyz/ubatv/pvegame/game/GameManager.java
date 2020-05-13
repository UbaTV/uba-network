package xyz.ubatv.pvegame.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.ubatv.pvegame.Main;
import xyz.ubatv.pvegame.userData.PlayerStatus;
import xyz.ubatv.pvegame.userData.UserData;
import xyz.ubatv.pvegame.userData.UserDataManager;

public class GameManager {

    private Main main = Main.getInstance();

    public int minPlayers = 2;
    public int maxPlayers = 6;

    public GameState gameState;
    public int lobbyTimer;
    public int gameTimer;

    public void initGame(){
        gameState = GameState.LOBBY;
        lobbyTimer = 10;
        gameTimer = -1;
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

    }

    public void stopGame(){

    }
}
