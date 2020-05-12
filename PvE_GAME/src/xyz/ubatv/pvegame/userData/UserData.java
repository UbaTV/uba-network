package xyz.ubatv.pvegame.userData;

import xyz.ubatv.pvegame.rankSystem.Rank;

public class UserData {

    private Rank rank;

    public UserData(Rank rank){
        this.setRank(rank);
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

}
