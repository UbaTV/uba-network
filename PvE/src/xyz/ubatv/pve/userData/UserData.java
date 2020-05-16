package xyz.ubatv.pve.userData;

import xyz.ubatv.pve.rankSystem.Rank;

public class UserData {

    private Rank rank;
    private PlayerStatus playerStatus;
    private int gameCoins;

    public UserData(Rank rank, PlayerStatus playerStatus){
        this.setRank(rank);
        this.setPlayerStatus(playerStatus);
        this.gameCoins = 0;
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
