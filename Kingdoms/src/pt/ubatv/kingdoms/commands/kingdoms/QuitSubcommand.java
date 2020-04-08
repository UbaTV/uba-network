package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

public class QuitSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "quit";
    }

    @Override
    public String getDescription() {
        return "Leave your current kingdom";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms quit";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1){
            UserData userData = main.userDataTable.online.get(player.getUniqueId());
            String userKingdom = userData.getKingdom();
            if(userKingdom.equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
                return;
            }

            String[] kingdomMembers = main.kingdomUtils.getMembers(userKingdom);
            if(kingdomMembers.length == 1){
                KingdomUtils.kingdomsChunks.remove(userKingdom);
                String[] allies = main.kingdomUtils.getAllies(userKingdom);
                for(String ally : allies){
                    StringBuilder newTargetAllies = new StringBuilder();
                    if(!ally.equalsIgnoreCase("none")){
                        String[] targetAllies = main.kingdomUtils.getAllies(ally.toLowerCase());
                        for(String targetAlly : targetAllies){
                            if(!targetAlly.equalsIgnoreCase(userKingdom)){
                                newTargetAllies.append(targetAlly).append("#");
                            }
                        }
                        if(newTargetAllies.length() == 0){
                            main.kingdomsTable.updateAllies(ally.toLowerCase(), "none");
                        }else{
                            main.kingdomsTable.updateAllies(ally.toLowerCase(), newTargetAllies.toString());
                        }
                    }
                }
                main.kingdomsTable.deleteKingdom(userKingdom);
                userData.setKingdom("none");
                player.sendMessage(main.textUtils.right + "You just left your kingdom and it was deleted.");
            }else{
                if(!main.kingdomsTable.getOwner(userKingdom).equalsIgnoreCase(player.getName())){
                    StringBuilder newMembers = new StringBuilder();
                    for(String member : kingdomMembers){
                        if(!member.equalsIgnoreCase(player.getName())){
                            newMembers.append(member).append("#");
                        }
                    }
                    main.kingdomsTable.updateMembers(userKingdom, newMembers.toString());
                    main.kingdomUtils.broadcastKingdom(userKingdom, "§5" + player.getName() + " §7just left the kingdom.");
                    userData.setKingdom("none");
                    player.sendMessage(main.textUtils.right + "§7You just left the kingdom.");
                }else{
                    player.sendMessage(main.textUtils.error + "To quit your kingdom you must transfer ownership.");
                }
            }
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
