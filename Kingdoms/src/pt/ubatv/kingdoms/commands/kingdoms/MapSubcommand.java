package pt.ubatv.kingdoms.commands.kingdoms;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;

public class MapSubcommand extends SubCommand {

    // TODO REDO COMMAND

    private Main main = Main.getInstance();

    private final char[] claimChars = "#?ç£$%=&^/ABCDEFGHIJKLMNOPQRSTUVXZ".toCharArray();
    private final char playerChar = '+';
    private final char noneChar = '-';
    private final char claimedChar = '#';

    @Override
    public String getName() {
        return "map";
    }

    @Override
    public String getDescription() {
        return "Shows nearby claimed chunks";
    }

    @Override
    public String getSyntax() {
        return "§7/kingdoms map";
    }

    /*
    north - z--
    south - z--
    west - x--
    east - x++
     */

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1){
            Chunk chunk = player.getLocation().getChunk();

            TextComponent line1 = new TextComponent("                              ");
            TextComponent line2 = new TextComponent("                              ");
            TextComponent line3 = new TextComponent("                              ");
            TextComponent line4 = new TextComponent("                              ");
            TextComponent line5 = new TextComponent("                              ");
            for(int x = chunk.getX() - 6; x <= chunk.getX() + 6; x++){
                Chunk chunk1 = chunk.getWorld().getChunkAt(x, chunk.getZ()-2);
                String chunkClaim1 = main.kingdomUtils.getChunkClaim(chunk1);
                if(chunkClaim1.equalsIgnoreCase("none")){
                    line1.addExtra("§7" + noneChar);
                }else{
                    TextComponent claimed = new TextComponent("§a" + claimedChar);
                    claimed.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Claimed by §5" + main.kingdomsTable.getDisplayName(chunkClaim1)).create()));
                    line1.addExtra(claimed);
                }

                Chunk chunk2 = chunk.getWorld().getChunkAt(x, chunk.getZ()-1);
                String chunkClaim2 = main.kingdomUtils.getChunkClaim(chunk2);
                if(chunkClaim2.equalsIgnoreCase("none")){
                    line2.addExtra("§7" + noneChar);
                }else{
                    TextComponent claimed = new TextComponent("§a" + claimedChar);
                    claimed.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Claimed by §5" + main.kingdomsTable.getDisplayName(chunkClaim2)).create()));
                    line2.addExtra(claimed);
                }

                Chunk chunk3 = chunk.getWorld().getChunkAt(x, chunk.getZ());
                String chunkClaim3 = main.kingdomUtils.getChunkClaim(chunk3);
                if(chunk3.equals(chunk)){
                    if(chunkClaim3.equalsIgnoreCase("none")){
                        line3.addExtra("§7" + playerChar);
                    }else{
                        TextComponent claimed = new TextComponent("§a" + playerChar);
                        claimed.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Claimed by §5" + main.kingdomsTable.getDisplayName(chunkClaim3)).create()));
                        line3.addExtra(claimed);
                    }
                }else{
                    if(chunkClaim3.equalsIgnoreCase("none")){
                        line3.addExtra("§7" + noneChar);
                    }else{
                        TextComponent claimed = new TextComponent("§a" + claimedChar);
                        claimed.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Claimed by §5" + main.kingdomsTable.getDisplayName(chunkClaim3)).create()));
                        line3.addExtra(claimed);
                    }
                }

                Chunk chunk4 = chunk.getWorld().getChunkAt(x, chunk.getZ()+1);
                String chunkClaim4 = main.kingdomUtils.getChunkClaim(chunk4);
                if(chunkClaim4.equalsIgnoreCase("none")){
                    line4.addExtra("§7" + noneChar);
                }else{
                    TextComponent claimed = new TextComponent("§a" + claimedChar);
                    claimed.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Claimed by §5" + main.kingdomsTable.getDisplayName(chunkClaim4)).create()));
                    line4.addExtra(claimed);
                }

                Chunk chunk5 = chunk.getWorld().getChunkAt(x, chunk.getZ()+2);
                String chunkClaim5 = main.kingdomUtils.getChunkClaim(chunk5);
                if(chunkClaim5.equalsIgnoreCase("none")){
                    line5.addExtra("§7" + noneChar);
                }else{
                    TextComponent claimed = new TextComponent("§a" + claimedChar);
                    claimed.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Claimed by §5" + main.kingdomsTable.getDisplayName(chunkClaim5)).create()));
                    line5.addExtra(claimed);
                }
            }

            player.sendMessage(" ");
            player.sendMessage("                  §7§m========[§5Kingdom §7Map§7§m]========");
            player.sendMessage(" ");
            player.spigot().sendMessage(line1);
            player.spigot().sendMessage(line2);
            player.spigot().sendMessage(line3);
            player.spigot().sendMessage(line4);
            player.spigot().sendMessage(line5);
            player.sendMessage(" ");
            main.textUtils.sendCenteredMessage(player, "§7Hover the §a# §7to see who claimed.");
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
