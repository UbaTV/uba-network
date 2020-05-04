package xyz.ubatv.kingdoms.events;

import org.bukkit.event.Listener;
import xyz.ubatv.kingdoms.Main;

public class VoteEvent implements Listener {

    private Main main = Main.getInstance();

    private int coinReward = 500;

    /*@EventHandler
    public void onVote(VotifierEvent event){
        Vote vote = event.getVote();
        Bukkit.getServer().broadcastMessage("§5" + vote.getUsername() + " §7voted for the server at §5" + vote.getServiceName() + "§7!");

        Player player = Bukkit.getServer().getPlayer(vote.getUsername());
        if(player == null) return;

        UserData userData = main.userDataTable.online.get(player.getUniqueId());

        userData.setCoins(userData.getCoins() + coinReward);
        player.sendMessage(main.textUtils.right + "§7You just received §5" + coinReward + main.textUtils.coinsSymbol + " §7coins for voting on the server.");
    }*/
}
