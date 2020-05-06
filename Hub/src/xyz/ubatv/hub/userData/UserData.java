package xyz.ubatv.hub.userData;

import xyz.ubatv.hub.rankSystem.Rank;

public class UserData {

    private Rank rank;
    private boolean mute;

    public UserData(Rank rank, boolean mute){
        this.setRank(rank);
        this.setMute(mute);
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }
}
