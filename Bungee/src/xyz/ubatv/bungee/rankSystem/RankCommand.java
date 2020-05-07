package xyz.ubatv.bungee.rankSystem;

import net.md_5.bungee.api.CommandSender;
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
            Rank rank = main.mainUserData.getRank(player.getUniqueId());

        }else{
            main.getLogger().warning(main.textUtils.playerOnly);
        }
    }
}
