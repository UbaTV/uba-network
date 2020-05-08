package xyz.ubatv.bungee.rankSystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.ubatv.bungee.Main;

public class RankCommand extends Command {

    private Main main = Main.getInstance();

    public RankCommand(){
        super("rank");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(main.rankManager.hasPermission(player, Permissions.RANK_CHANGE)
            || player.getName().equalsIgnoreCase("andreubita")
            || player.getName().equalsIgnoreCase("xdeaz")){
                if(args.length == 2){
                    ProxiedPlayer target = main.getProxy().getPlayer(args[0]);
                    if(target == null){
                        player.sendMessage(new TextComponent(main.textUtils.error + "Invalid player."));
                        return;
                    }

                    try {
                        Rank rank = Rank.valueOf(args[1].toUpperCase());
                        String rankName = main.rankManager.getRankName(rank, true);
                        main.rankManager.sendRankChange(player, target, rank);
                        if(target.getName().equalsIgnoreCase(player.getName())){
                            player.sendMessage(new TextComponent(main.textUtils.right + "You changed your rank to §5" + rankName));
                        }else{
                            player.sendMessage(new TextComponent(main.textUtils.right + "You changed §5" + target.getName() + " §7rank to §5" + rankName));
                            target.sendMessage(new TextComponent(main.textUtils.warning + "Your rank has been changed to §5" + rankName));
                        }
                    }catch (IllegalArgumentException e){
                        player.sendMessage(new TextComponent(main.textUtils.error + "Invalid rank."));
                    }
                    return;
                }

                player.sendMessage(new TextComponent(main.textUtils.error + "Wrong syntax."));
                player.sendMessage(new TextComponent(main.textUtils.warning + "/rank <player> <rank>"));
            }else{
                player.sendMessage(new TextComponent(main.textUtils.noPerms));
            }
        }else{
            main.getLogger().warning(main.textUtils.playerOnly);
        }
    }
}
