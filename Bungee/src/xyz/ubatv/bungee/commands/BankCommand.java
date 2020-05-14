package xyz.ubatv.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.ubatv.bungee.Main;

public class BankCommand extends Command {

    private Main main = Main.getInstance();

    public BankCommand() {
        super("bank");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            ProxiedPlayer target = player;
            if(args.length == 1){
                target = main.getProxy().getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(new TextComponent(main.textUtils.error + "Invalid player"));
                    return;
                }
            }

            int kingdomsCoins = main.mainBank.getKingdomCoins(target.getUniqueId());
            int pveCoins = main.mainBank.getPvECoins(target.getUniqueId());

            if(player.getName().equals(target.getName())){
                player.sendMessage(new TextComponent("§5Your §7Account"));
            }else{
                player.sendMessage(new TextComponent("§5" + target.getName() + "§7's Account"));
            }
            player.sendMessage(new TextComponent("§7Kingdoms: §5" + kingdomsCoins + main.textUtils.coinsSymbol));
            player.sendMessage(new TextComponent("§7PvE: §5" + pveCoins + main.textUtils.coinsSymbol));
        }else{
            main.getLogger().warning(main.textUtils.playerOnly);
        }
    }
}
