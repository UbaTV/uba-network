package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;
import pt.ubatv.kingdoms.utils.UserData;

import java.util.ArrayList;

public class TagSubcommand extends SubCommand {

    private Main main = Main.getInstance();

    @Override
    public String getName() {
        return "tag";
    }

    @Override
    public String getDescription() {
        return "Change the kingdoms tag";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms tag §5<tag>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){
            UserData userData = main.userDataTable.online.get(player.getUniqueId());
            String userKingdom = userData.getKingdom();
            if(userKingdom.equalsIgnoreCase("none")){
                player.sendMessage(main.textUtils.error + "You are not in a kingdom.");
                return;
            }

            if(!main.kingdomsTable.getOwner(userKingdom).equalsIgnoreCase(player.getName())){
                player.sendMessage(main.textUtils.error + "You must be king to invite players.");
                return;
            }

            String tag = args[1];
            if(!(2 <= tag.length() && tag.length() <= 5)){
                player.sendMessage(main.textUtils.error + "Tag length must be between 2 and 5 characters");
                return;
            }

            ArrayList<String> bannedTags = bannedTags();
            if(bannedTags.contains(tag.toLowerCase())){
                player.sendMessage(main.textUtils.error + "This kingdom tag has been banned from being used.");
                return;
            }


            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }

    private ArrayList<String> bannedTags(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add("wood");
        tags.add("stone");
        tags.add("iron");
        tags.add("gold");
        tags.add("vip");
        tags.add("mvp");
        tags.add("admin");
        tags.add("ceo");
        tags.add("nigga");
        tags.add("negro");
        tags.add("faggs");
        tags.add("fag");
        tags.add("fags");
        tags.add("nigas");
        return tags;
    }
}
