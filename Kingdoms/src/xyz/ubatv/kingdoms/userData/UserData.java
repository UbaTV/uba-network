package xyz.ubatv.kingdoms.userData;

import xyz.ubatv.kingdoms.rankSystem.Rank;
import xyz.ubatv.kingdoms.rankSystem.ServerRank;

public class UserData {

    private Rank rank;
    private ServerRank serverRank;
    private int coins;
    private int kills;
    private int deaths;
    private String kingdom;

    public UserData(Rank rank, ServerRank serverRank, int coins, int kills, int deaths, String kingdom){
        this.setRank(rank);
        this.setServerRank(serverRank);
        this.setCoins(coins);
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

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public ServerRank getServerRank() {
        return serverRank;
    }

    public void setServerRank(ServerRank serverRank) {
        this.serverRank = serverRank;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
