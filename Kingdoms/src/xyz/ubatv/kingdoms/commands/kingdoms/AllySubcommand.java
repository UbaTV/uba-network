package xyz.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.SubCommand;
import xyz.ubatv.kingdoms.userData.UserData;

public class AllySubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "ally";
    }

    @Override
    public String getDescription() {
        return "Ally with a kingdom";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms ally §5[accept] <kingdom>";
    }

    @Override
    public void perform(Player player, String[] args) {
        UserData userData = main.mainUserData.online.get(player.getUniqueId());
        String userKingdom = userData.getKingdom();
        if(userKingdom.equalsIgnoreCase("none")){
            player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
            return;
        }

        if(!main.kingdomsTable.getOwner(userKingdom).equalsIgnoreCase(player.getName())){
            player.sendMessage(main.textUtils.error + "You must be king to invite players.");
            return;
        }


        if(args.length == 3){
            if(args[1].equalsIgnoreCase("accept")){
                if(!main.kingdomsTable.kingdomExists(args[2].toLowerCase())){
                    player.sendMessage(main.textUtils.error + "Kingdom does not exist.");
                    return;
                }

                String targetKingdom = args[2].toLowerCase();
                if(targetKingdom.equalsIgnoreCase(userKingdom)){
                    player.sendMessage(main.textUtils.warning + "You can't ally your own kingdom.");
                    return;
                }

                String[] allies = main.kingdomUtils.getAllies(userKingdom);
                for(String ally : allies){
                    if(ally.equalsIgnoreCase(targetKingdom)){
                        player.sendMessage(main.textUtils.warning + "You are already allied with this kingdom");
                        return;
                    }
                }

                String[] targetAllies = KingdomUtils.allyInvite.get(userKingdom);
                if(targetAllies == null){
                    player.sendMessage(main.textUtils.warning + "You don't have any ally invitation.");
                    return;
                }else{
                    for(String ally : targetAllies){
                        if(ally.equalsIgnoreCase(userKingdom)){
                            StringBuilder newAllies = new StringBuilder();
                            for(String alli : targetAllies){
                                if(!alli.equalsIgnoreCase(userKingdom)){
                                    newAllies.append(alli).append("#");
                                }
                            }
                            if(newAllies.length() == 0){
                                KingdomUtils.allyInvite.remove(userKingdom);
                            }else{
                                KingdomUtils.allyInvite.put(targetKingdom, main.textUtils.stringToList(newAllies.toString()));
                            }

                            String[] targetAlliess = main.kingdomUtils.getAllies(targetKingdom);
                            String targetDisplayName = main.kingdomsTable.getDisplayName(targetKingdom);
                            String userDisplayName = main.kingdomsTable.getDisplayName(userKingdom);
                            main.kingdomsTable.updateAllies(userKingdom, main.textUtils.listToString(allies) + targetDisplayName + "#");
                            main.kingdomsTable.updateAllies(targetKingdom, main.textUtils.listToString(targetAlliess) + userDisplayName + "#");
                            main.kingdomUtils.broadcastKingdom(userKingdom, "§7You are now allied with §5" + targetDisplayName + "§7.");
                            main.kingdomUtils.broadcastKingdom(targetKingdom, "§7You are now allied with §5" + userDisplayName + "§7.");
                            return;
                        }
                    }
                    player.sendMessage(main.textUtils.warning + "You don't have an ally invite from this kingdom.");
                    return;
                }
            }

            if(args[1].equalsIgnoreCase("deny")){
                if(!main.kingdomsTable.kingdomExists(args[2].toLowerCase())){
                    player.sendMessage(main.textUtils.error + "Kingdom does not exist.");
                    return;
                }

                String targetKingdom = args[2].toLowerCase();
                if(targetKingdom.equalsIgnoreCase(userKingdom)){
                    player.sendMessage(main.textUtils.warning + "Invalid alliance.");
                    return;
                }

                String[] alliaces = KingdomUtils.allyInvite.get(userKingdom);
                for(String ally : alliaces){
                    if(userKingdom.equalsIgnoreCase(ally)){
                        StringBuilder newAllyInvite = new StringBuilder();
                        for(String alli : alliaces){
                            if(!alli.equalsIgnoreCase(userKingdom)){
                                newAllyInvite.append(alli).append("#");
                            }
                        }
                        if(newAllyInvite.length() == 0){
                            KingdomUtils.allyInvite.remove(userKingdom);
                        }else{
                            KingdomUtils.allyInvite.put(userKingdom, main.textUtils.stringToList(newAllyInvite.toString()));
                        }
                        player.sendMessage(main.textUtils.right + "You denied alliance with §5" + main.kingdomsTable.getDisplayName(targetKingdom));
                        main.kingdomUtils.broadcastKingdom(targetKingdom, "§5" + main.kingdomsTable.getDisplayName(userKingdom) + " §7declined alliance with your kingdom.");
                        return;
                    }
                }

                player.sendMessage(main.textUtils.warning + "You don't have an ally invite from this kingdom.");
                return;
            }
            return;
        }

        if(args.length == 2){
            if(args[1].equalsIgnoreCase("accept")){
                player.sendMessage(main.textUtils.warning + "§7/kingdoms ally accept §5<kingdom>");
                return;
            }

            if(args[1].equalsIgnoreCase("deny")){
                player.sendMessage(main.textUtils.warning + "§7/kingdoms ally deny §5<kingdom>");
                return;
            }

            if(!main.kingdomsTable.kingdomExists(args[1].toLowerCase())){
                player.sendMessage(main.textUtils.error + "Kingdom does not exist.");
                return;
            }

            String targetKingdom = args[1].toLowerCase();
            if(targetKingdom.equalsIgnoreCase(userKingdom)){
                player.sendMessage(main.textUtils.warning + "You can't send an ally invite to your own kingdom.");
                return;
            }


            String[] allies = main.kingdomUtils.getAllies(userKingdom);
            for(String ally : allies){
                if(ally.equalsIgnoreCase(targetKingdom)){
                    player.sendMessage(main.textUtils.warning + "You are already allied with this kingdom");
                    return;
                }
            }

            String[] targetAllies = KingdomUtils.allyInvite.get(targetKingdom);
            if(targetAllies != null){
                for (String targetAlly : targetAllies) {
                    if(targetAlly.equalsIgnoreCase(userKingdom)){
                        player.sendMessage(main.textUtils.warning + "Ally invitation already sent.");
                        return;
                    }
                }
            }

            String newAllies;
            if(targetAllies != null){
                newAllies = main.textUtils.listToString(targetAllies) + targetKingdom.toLowerCase() + "#";
            }else{
                newAllies = targetKingdom.toLowerCase() + "#;";
            }

            KingdomUtils.allyInvite.put(targetKingdom, main.textUtils.stringToList(newAllies));
            main.kingdomUtils.broadcastKingdom(userKingdom, "§7Ally invited sent to §5" + main.kingdomsTable.getDisplayName(targetKingdom) + "§7.");
            main.kingdomUtils.broadcastKingdom(targetKingdom, "§7Ally request received from §5" + main.kingdomsTable.getDisplayName(userKingdom) + "§7.");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + "§7/kingdoms ally §5<kingdom>");
        player.sendMessage(main.textUtils.warning + "§7/kingdoms ally accept §5<kingdom>");
        player.sendMessage(main.textUtils.warning + "§7/kingdoms ally deny §5<kingdom>");
    }
}
