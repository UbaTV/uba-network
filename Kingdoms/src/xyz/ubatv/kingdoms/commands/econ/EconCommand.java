package xyz.ubatv.kingdoms.commands.econ;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.rankSystem.Permissions;
import xyz.ubatv.kingdoms.userData.UserData;

public class EconCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.ECON_MANAGEMENT)){
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("balance")){
                        player.performCommand("balance " + args[1]);
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("reset")
                    || args[0].equalsIgnoreCase("r")){
                        Player target = Bukkit.getServer().getPlayer(args[1]);
                        if(target == null){
                            player.sendMessage(main.textUtils.error + "Invalid player.");
                            return false;
                        }
                        main.mainUserData.online.get(target.getUniqueId()).setCoins(0);

                        if(target.getName().equalsIgnoreCase(player.getName())){
                            player.sendMessage(main.textUtils.right + "Your balance suffered a reset.");
                        }else{
                            player.sendMessage(main.textUtils.right + "§5" + target.getName() + "§7's balance has been reset.");
                            target.sendMessage(main.textUtils.warning + "Your balance suffered a reset.");
                        }

                        return false;
                    }
                }

                if(args.length == 3){
                    Player target = Bukkit.getServer().getPlayer(args[1]);
                    if(target == null){
                        player.sendMessage(main.textUtils.error + "Invalid player.");
                        return false;
                    }

                    try{
                        UserData userData = main.mainUserData.online.get(target.getUniqueId());
                        int coins = Integer.parseInt(args[2]);
                        int balance = userData.getCoins();

                        if(args[0].equalsIgnoreCase("set")){
                            userData.setCoins(coins);

                            if(target.getName().equalsIgnoreCase(player.getName())){
                                player.sendMessage(main.textUtils.right + "Your balance was set to §5" + coins + main.textUtils.coinsSymbol);
                            }else{
                                player.sendMessage(main.textUtils.right + "You set §5" + target.getName() + "§7's balance to §5" + coins + main.textUtils.coinsSymbol);
                                target.sendMessage(main.textUtils.right + "§5" + coins + main.textUtils.coinsSymbol + " §7is your new balance.");
                            }
                        }

                        if(args[0].equalsIgnoreCase("add")){
                            userData.setCoins(balance + coins);

                            if(target.getName().equalsIgnoreCase(player.getName())){
                                player.sendMessage(main.textUtils.right + "You added §5" + coins + main.textUtils.coinsSymbol + " §7to your balance.");
                            }else{
                                player.sendMessage(main.textUtils.right + "You added §5" + coins + main.textUtils.coinsSymbol + " §7to §5" + target.getName() + " §7balance.");
                                target.sendMessage(main.textUtils.right + "§5" + coins + main.textUtils.coinsSymbol + " §7were added to your balance.");
                            }
                        }

                        if(args[0].equalsIgnoreCase("remove")){
                            userData.setCoins(balance < coins ? 0 : balance - coins);

                            if(target.getName().equalsIgnoreCase(player.getName())){
                                player.sendMessage(main.textUtils.right + "You removed §5" + coins + main.textUtils.coinsSymbol + " §7from your balance.");
                            }else{
                                player.sendMessage(main.textUtils.right + "You removed §5" + coins + main.textUtils.coinsSymbol + " §7from §5" + target.getName() + " §7balance.");
                                target.sendMessage(main.textUtils.warning + "§5" + coins + main.textUtils.coinsSymbol + " §7were removed from your balance.");
                            }
                        }
                    }catch (NumberFormatException e){
                        player.sendMessage(main.textUtils.error + "Invalid number.");
                    }
                    return false;
                }

                player.sendMessage("§7/economy balance §5<player>");
                player.sendMessage("§7/economy reset §5<player>");
                player.sendMessage("§7/economy set §5<player> <amount>");
                player.sendMessage("§7/economy add §5<player> <amount>");
                player.sendMessage("§7/economy remove §5<player> <amount>");
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
