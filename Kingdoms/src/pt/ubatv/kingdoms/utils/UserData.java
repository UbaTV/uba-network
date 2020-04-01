package pt.ubatv.kingdoms.utils;

import pt.ubatv.kingdoms.rankSystem.Rank;

public class UserData {

    private Rank rank;
    private int coins;

    public UserData(Rank rank, int coins){
        this.setRank(rank);
        this.setCoins(coins);
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
