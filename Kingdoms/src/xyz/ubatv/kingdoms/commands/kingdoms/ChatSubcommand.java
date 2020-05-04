package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.utils.UserData;

public class ChatSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public String getDescription() {
        return "Send private kingdoms messages.";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms chat §5<msg>";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();

        if(userKingdom.equalsIgnoreCase("none")){
            player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
            return;
        }

        if(main.kingdomUtils.getOnlineMembers(userKingdom) == 1){
            player.sendMessage(main.textUtils.warning + "The rest of your kingdoms members are offline.");
            return;
        }

        StringBuilder message = new StringBuilder();
        for(int i = 1; i < args.length; i++){
            message.append(args[i]).append(" ");
        }

        main.kingdomUtils.broadcastKingdom(userKingdom, "§8[§5Kingdom §7Chat§8] §5" + player.getName() + " §8→ §7" + message);
    }
}
