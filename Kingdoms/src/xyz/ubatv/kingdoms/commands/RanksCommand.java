package xyz.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Rank;
import xyz.ubatv.kingdoms.rankSystem.ServerRank;

public class RanksCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                main.textUtils.sendCenteredMessage(player, "§7§m========[§5Ranks§7§m]========");
                player.sendMessage(" ");
                main.textUtils.sendCenteredMessage(player, main.rankManager.getServerRankName(ServerRank.DIAMOND, true) + " §7- §5" + main.rankManager.rankupPrice(ServerRank.DIAMOND) + main.textUtils.coinsSymbol);
                main.textUtils.sendCenteredMessage(player, main.rankManager.getServerRankName(ServerRank.GOLD, true) + " §7- §5" + main.rankManager.rankupPrice(ServerRank.GOLD) + main.textUtils.coinsSymbol);
                main.textUtils.sendCenteredMessage(player, main.rankManager.getServerRankName(ServerRank.IRON, true) + " §7- §5" + main.rankManager.rankupPrice(ServerRank.IRON) + main.textUtils.coinsSymbol);
                main.textUtils.sendCenteredMessage(player, main.rankManager.getServerRankName(ServerRank.STONE, true) + " §7- §5" + main.rankManager.rankupPrice(ServerRank.STONE) + main.textUtils.coinsSymbol);
                main.textUtils.sendCenteredMessage(player, main.rankManager.getServerRankName(ServerRank.WOOD, true) + " §7- §5" + main.rankManager.rankupPrice(ServerRank.WOOD) + main.textUtils.coinsSymbol);
                player.sendMessage(" ");
                return false;
            }

            if(args.length == 1){
                try{
                    ServerRank rank = ServerRank.valueOf(args[0].toUpperCase());
                    sendRankPerks(player, rank);
                }catch (IllegalArgumentException e){
                    player.sendMessage(main.textUtils.error + "Invalid rank.");
                }
                return false;
            }

            player.sendMessage(main.textUtils.error + "Wrong syntax.");
            player.sendMessage(main.textUtils.warning + "/ranks §5[rank]");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    public void sendRankPerks(Player player, ServerRank rank){
        if(rank.equals(ServerRank.WOOD)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getServerRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "/enderchest");
            player.sendMessage(" ");
        }else if(rank.equals(ServerRank.STONE)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getServerRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "/clearinventory");
            player.sendMessage(" ");
        }else if(rank.equals(ServerRank.IRON)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getServerRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "/privatechest");
            player.sendMessage(" ");
        }else if(rank.equals(ServerRank.GOLD)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getServerRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "§4Currently none");
            player.sendMessage(" ");
        }else if(rank.equals(ServerRank.DIAMOND)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getServerRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "§4Currently none");
            player.sendMessage(" ");
        }else{
            player.sendMessage(main.textUtils.error + "Invalid rank.");
        }
    }
}
