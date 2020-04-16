package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Rank;

public class RanksCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                main.textUtils.sendCenteredMessage(player, "§7§m========[§5Ranks§7§m]========");
                player.sendMessage(" ");
                main.textUtils.sendCenteredMessage(player, main.rankManager.getRankName(Rank.DIAMOND, true) + " §7- §5" + main.rankManager.rankupPrice(Rank.DIAMOND) + main.textUtils.coinsSymbol);
                main.textUtils.sendCenteredMessage(player, main.rankManager.getRankName(Rank.GOLD, true) + " §7- §5" + main.rankManager.rankupPrice(Rank.GOLD) + main.textUtils.coinsSymbol);
                main.textUtils.sendCenteredMessage(player, main.rankManager.getRankName(Rank.IRON, true) + " §7- §5" + main.rankManager.rankupPrice(Rank.IRON) + main.textUtils.coinsSymbol);
                main.textUtils.sendCenteredMessage(player, main.rankManager.getRankName(Rank.STONE, true) + " §7- §5" + main.rankManager.rankupPrice(Rank.STONE) + main.textUtils.coinsSymbol);
                main.textUtils.sendCenteredMessage(player, main.rankManager.getRankName(Rank.WOOD, true) + " §7- §5" + main.rankManager.rankupPrice(Rank.WOOD) + main.textUtils.coinsSymbol);
                player.sendMessage(" ");
                return false;
            }

            if(args.length == 1){
                Rank rank = Rank.valueOf(args[0].toUpperCase());
                try{
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

    public void sendRankPerks(Player player, Rank rank){
        if(rank.equals(Rank.WOOD)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "/enderchest");
            player.sendMessage(" ");
        }else if(rank.equals(Rank.STONE)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "/clearinventory");
            player.sendMessage(" ");
        }else if(rank.equals(Rank.IRON)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "/privatechest");
            player.sendMessage(" ");
        }else if(rank.equals(Rank.GOLD)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "");
            player.sendMessage(" ");
        }else if(rank.equals(Rank.DIAMOND)){
            main.textUtils.sendCenteredMessage(player, "§7§m========[" + main.rankManager.getRankName(rank, true) + "§7's Perks§7§m]========");
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "");
            player.sendMessage(" ");
        }else{
            player.sendMessage(main.textUtils.error + "Invalid rank.");
        }
    }
}
