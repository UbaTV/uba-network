package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

public class LevelupSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "levelup";
    }

    @Override
    public String getDescription() {
        return "Level up your kingdom.";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms levelup";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();
        if(userKingdom.equalsIgnoreCase("none")){
            player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
            return;
        }

        if(!main.kingdomsTable.getOwner(userKingdom).equalsIgnoreCase(player.getName())){
            player.sendMessage(main.textUtils.error + "You must be kingdom king to levelup.");
            return;
        }

        int levelupPrice = main.kingdomUtils.getLevelupPrice(userKingdom);
        int vault = main.kingdomsTable.getCoins(userKingdom);
        if(vault < levelupPrice){
            player.sendMessage(main.textUtils.error + "Your kingdom's vault does not have enough coins.");
            return;
        }

        int level = main.kingdomsTable.getLevel(userKingdom);
        main.kingdomsTable.updateVault(userKingdom, vault - levelupPrice);
        main.kingdomsTable.updateLevel(userKingdom, level + 1);
        main.kingdomUtils.broadcastKingdom(userKingdom, "§7Your kingdom just leveled up to level §5" + (level + 1));
    }
}
