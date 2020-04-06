package pt.ubatv.kingdoms.commands.kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.commands.SubCommand;

public class MapSubcommand extends SubCommand {

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

            StringBuilder line1 = new StringBuilder();
            StringBuilder line2 = new StringBuilder();
            StringBuilder line3 = new StringBuilder();
            StringBuilder line4 = new StringBuilder();
            StringBuilder line5 = new StringBuilder();
            for(int x = chunk.getX() - 4; x <= chunk.getX() + 4; x++){
                Chunk chunk1 = chunk.getWorld().getChunkAt(x, chunk.getZ()-2);
                String chunkClaim1 = main.kingdomUtils.getChunkClaim(chunk1);
                if(chunkClaim1.equalsIgnoreCase("none")){
                    line1.append(noneChar);
                }else{
                    line1.append(claimedChar);
                }

                Chunk chunk2 = chunk.getWorld().getChunkAt(x, chunk.getZ()-1);
                String chunkClaim2 = main.kingdomUtils.getChunkClaim(chunk2);
                if(chunkClaim2.equalsIgnoreCase("none")){
                    line2.append(noneChar);
                }else{
                    line2.append(claimedChar);
                }

                Chunk chunk3 = chunk.getWorld().getChunkAt(x, chunk.getZ());
                String chunkClaim3 = main.kingdomUtils.getChunkClaim(chunk3);
                if(chunk3.equals(chunk)){
                    line3.append(playerChar);
                }else{
                    if(chunkClaim3.equalsIgnoreCase("none")){
                        line3.append(noneChar);
                    }else{
                        line3.append(claimedChar);
                    }
                }

                Chunk chunk4 = chunk.getWorld().getChunkAt(x, chunk.getZ()+1);
                String chunkClaim4 = main.kingdomUtils.getChunkClaim(chunk4);
                if(chunkClaim4.equalsIgnoreCase("none")){
                    line4.append(noneChar);
                }else{
                    line4.append(claimedChar);
                }

                Chunk chunk5 = chunk.getWorld().getChunkAt(x, chunk.getZ()+2);
                String chunkClaim5 = main.kingdomUtils.getChunkClaim(chunk5);
                if(chunkClaim5.equalsIgnoreCase("none")){
                    line5.append(noneChar);
                }else{
                    line5.append(claimedChar);
                }
            }

            player.sendMessage(line1.toString());
            player.sendMessage(line2.toString());
            player.sendMessage(line3.toString());
            player.sendMessage(line4.toString());
            player.sendMessage(line5.toString());
            return;
        }

        player.sendMessage(main.textUtils.error + "Wrong syntax.");
        player.sendMessage(main.textUtils.warning + getSyntax());
    }
}
