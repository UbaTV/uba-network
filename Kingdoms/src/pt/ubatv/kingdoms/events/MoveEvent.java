package pt.ubatv.kingdoms.events;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pt.ubatv.kingdoms.Main;

public class MoveEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void flyMode(PlayerMoveEvent event){
        Chunk oldChunk = event.getFrom().getChunk();
        Chunk newChunk = event.getTo().getChunk();
        if(newChunk != oldChunk){
            String chunkClaimOld = main.kingdomUtils.getChunkClaim(oldChunk);
            String chunkClaimNew = main.kingdomUtils.getChunkClaim(newChunk);
            Player player = event.getPlayer();
            if(!chunkClaimNew.equalsIgnoreCase(chunkClaimOld)){
                player.sendMessage("§7Leaving§8: §5" + main.kingdomsTable.getDisplayName(chunkClaimOld) + " §8| §7Entering§8: §5" + main.kingdomsTable.getDisplayName(chunkClaimNew));
            }
            String userKingdom = main.userDataTable.online.get(player.getUniqueId()).getKingdom();
            if(!userKingdom.equalsIgnoreCase("none")){
                if(chunkClaimNew.equalsIgnoreCase(userKingdom)){
                    if(main.kingdomsYML.getConfig().getBoolean(userKingdom + ".fly")){
                        player.setAllowFlight(true);
                        return;
                    }
                }
            }
            player.setAllowFlight(false);
        }
    }
}
