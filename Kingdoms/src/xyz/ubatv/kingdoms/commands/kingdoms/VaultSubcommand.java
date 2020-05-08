package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.userData.UserData;

public class VaultSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "vault";
    }

    @Override
    public String getDescription() {
        return "Manages kingdom vault";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms vault";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = main.mainUserData.online.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();
        if(userKingdom.equalsIgnoreCase("none")){
            player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
            return;
        }

        if(args.length == 2){
            if(args[1].equalsIgnoreCase("balance")
            || args[1].equalsIgnoreCase("money")
            || args[1].equalsIgnoreCase("bal")){
                int balance = main.kingdomsTable.getCoins(userKingdom);
                String kingdomName = main.kingdomsTable.getDisplayName(userKingdom);
                player.sendMessage("§5" + kingdomName + "§7's Vault: " + balance + main.textUtils.coinsSymbol);
            }
            return;
        }

        if(args.length == 3){
            try{
                int coins = Integer.parseInt(args[2]);
                if(args[1].equalsIgnoreCase("deposit")
                        || args[1].equalsIgnoreCase("d")){
                    int balance = userData.getCoins();
                    if(balance < coins){
                        player.sendMessage(main.textUtils.error + "You don't have enough coins.");
                    }else{
                        int vault = main.kingdomsTable.getCoins(userKingdom);
                        userData.setCoins(balance - coins);
                        main.kingdomsTable.updateVault(userKingdom, vault + coins);
                        player.sendMessage(main.textUtils.right + "§5" + coins + main.textUtils.coinsSymbol + " §7deposited to kingdom's vault");
                    }
                }

                if(args[1].equalsIgnoreCase("withdraw")
                        || args[1].equalsIgnoreCase("w")){
                    if(main.kingdomsTable.getOwner(userKingdom).equalsIgnoreCase(player.getName())){
                        int balance = main.kingdomsTable.getCoins(userKingdom);
                        if(balance < coins){
                            player.sendMessage(main.textUtils.error + "Not enough coins in kingdom.");
                        }else{
                            userData.setCoins(userData.getCoins() + coins);
                            main.kingdomsTable.updateVault(userKingdom, balance - coins);
                            player.sendMessage(main.textUtils.right + "§5" + coins + main.textUtils.coinsSymbol + " §7withdrew from kingdom's vault");
                        }
                    }else{
                        player.sendMessage(main.textUtils.warning + "You must be kingdom owner to withdraw.");
                    }
                }
            }catch (NumberFormatException e){
                player.sendMessage(main.textUtils.error + "Invalid amount.");
            }
            return;
        }

        player.sendMessage("§7/kingdoms vault balance");
        player.sendMessage("§7/kingdoms vault deposit §5<amount>");
        player.sendMessage("§7/kingdoms vault withdraw §5<amount>");
    }
}
