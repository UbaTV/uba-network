package xyz.ubatv.kingdoms.utils;

import xyz.ubatv.kingdoms.rankSystem.Rank;

public class UserData {

    private Rank rank;
    private int coins;
    private boolean mute;
    private int kills;
    private int deaths;
    private String kingdom;

    public UserData(Rank rank, int coins, boolean mute, int kills, int deaths, String kingdom){
        this.setRank(rank);
        this.setCoins(coins);
        this.setMute(mute);
        this.setKills(kills);
        this.setDeaths(deaths);
        this.setKingdom(kingdom);
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
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
