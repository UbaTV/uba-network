package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

public class NeutralSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "neutral";
    }

    @Override
    public String getDescription() {
        return "Remove kingdom from ally";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms neutral §5<kingdom>";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();
        if(args.length == 2){
            if(!main.kingdomsTable.kingdomExists(args[1].toLowerCase())){
                player.sendMessage(main.textUtils.error + "Kingdom does not exist.");
                return;
            }

            String targetKingdom = args[1].toLowerCase();
            if(targetKingdom.equalsIgnoreCase(userKingdom)){
                player.sendMessage(main.textUtils.warning + "You can't neutral your own kingdom.");
                return;
            }

            String[] allies = main.kingdomUtils.getAllies(userKingdom);
            for(String ally : allies){
                if(ally.equalsIgnoreCase(targetKingdom)){
                    String[] targetAllies = main.kingdomUtils.getAllies(targetKingdom);

                    StringBuilder newUserAllies = new StringBuilder();
                    StringBuilder newTargetAllies = new StringBuilder();

                    for(String alli : allies){
                        if(!alli.equalsIgnoreCase(targetKingdom)){
                            newUserAllies.append(alli).append("#");
                        }
                    }

                    for(String alli : targetAllies){
                        if(!alli.equalsIgnoreCase(userKingdom)){
                            newTargetAllies.append(alli).append("#");
                        }
                    }

                    main.kingdomsTable.updateAllies(targetKingdom, newTargetAllies.toString());
                    main.kingdomsTable.updateAllies(userKingdom, newUserAllies.toString());
                    main.kingdomUtils.broadcastKingdom(userKingdom, "Your kingdom is now neutral with §5" + main.kingdomsTable.getDisplayName(targetKingdom));
                    main.kingdomUtils.broadcastKingdom(targetKingdom, "Your kingdom is now neutral with §5" + main.kingdomsTable.getDisplayName(userKingdom));
                    return;
                }
            }

            player.sendMessage(main.textUtils.warning + "You must be allied with the kingdom to turn neutral.");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
