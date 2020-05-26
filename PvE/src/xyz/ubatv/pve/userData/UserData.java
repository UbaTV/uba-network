package xyz.ubatv.pve.userData;

import xyz.ubatv.pve.rankSystem.Rank;

public class UserData {

    private Rank rank;
    private PlayerStatus playerStatus;
    private int kills;
    private int gameCoins;

    public UserData(Rank rank, PlayerStatus playerStatus){
        this.setRank(rank);
        this.setPlayerStatus(playerStatus);
        this.setKills(0);
        this.setGameCoins(0);
    }

    public int getGameCoins() {
        return gameCoins;
    }

    public void setGameCoins(int gameCoins) {
        this.gameCoins = gameCoins;
    }

    public void addGameCoins(int gameCoins){
        this.gameCoins += gameCoins;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKills(int kills){
        this.kills += kills;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

}
