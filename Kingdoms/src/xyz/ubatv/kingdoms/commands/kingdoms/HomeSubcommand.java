package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.userData.UserData;
import xyz.ubatv.kingdoms.userData.UserDataManager;

public class HomeSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getDescription() {
        return "Teleport to your kingdoms home";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms home";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1){
            UserData userData = UserDataManager.usersData.get(player.getUniqueId());
            String userKingdom = userData.getKingdom();
            if(userKingdom.equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
                return;
            }

            if(!main.kingdomsYML.getConfig().contains(userKingdom.toLowerCase() + ".home")){
                player.sendMessage(main.textUtils.warning + "Your kingdom does not have a home set.");
                return;
            }

            if(main.kingdomsYML.getConfig().get(userKingdom.toLowerCase() + ".home") == null){
                player.sendMessage(main.textUtils.warning + "Your kingdom does not have a home set.");
                return;
            }

            Location kingdomHome = main.kingdomsYML.getKingdomHome(userKingdom.toLowerCase());
            player.teleport(kingdomHome);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
