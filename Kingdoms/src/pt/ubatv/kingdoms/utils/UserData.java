package pt.ubatv.kingdoms.utils;

import pt.ubatv.kingdoms.rankSystem.Rank;

public class UserData {

    private Rank rank;
    private int coins;
    private boolean mute;

    public UserData(Rank rank, int coins, boolean mute){
        this.setRank(rank);
        this.setCoins(coins);
        this.setMute(mute);
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
