package pt.ubatv.kingdoms.utils;

import pt.ubatv.kingdoms.rankSystem.Rank;

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
