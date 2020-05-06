package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.guis.KingdomsShopGUI;
import xyz.ubatv.kingdoms.utils.UserData;

public class ShopSubcommand extends SubCommand {

    private Main main = Main.getInstance();
    private KingdomsShopGUI kingdomsShopGUI = new KingdomsShopGUI();

    @Override
    public String getName() {
        return "shop";
    }

    @Override
    public String getDescription() {
        return "Opens the kingdom shop gui";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms shop";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();
        if(userKingdom.equalsIgnoreCase("none")){
            player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
            return;
        }
        kingdomsShopGUI.openGUI(player);
    }
}