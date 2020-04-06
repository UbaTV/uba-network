package pt.ubatv.kingdoms.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.rankSystem.Permissions;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCommand implements CommandExecutor, Listener {

    private Main main = Main.getInstance();

    HashMap<Player, ArrayList<Chunk>> chunkList = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(main.rankManager.hasPermission(player, Permissions.TESTER)
            || player.getName().equalsIgnoreCase("andreubita")){
                /*UserData userData = main.userDataTable.online.get(player.getUniqueId());
                userData.setCoins(userData.getCoins() + 100);*/

                TextComponent mainComponent = new TextComponent( "Here's a question: " );
                TextComponent subComponent = new TextComponent( "Maybe u r noob?" );
                subComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click me!" ).create() ) );
                subComponent.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/wiki/the-chat-component-api/" ) );
                mainComponent.addExtra( subComponent );
                mainComponent.addExtra( " Does that answer your question?" );
                player.spigot().sendMessage(mainComponent);

                /*if(args.length == 1){
                    if(args[0].equalsIgnoreCase("save")){
                        if(!chunkList.containsKey(player)){
                            player.sendMessage("You dont have any chunks");
                            return false;
                        }

                        main.kingdomClaimYML.getConfig().set(player.getName(), null);
                        int i = 0;
                        main.kingdomClaimYML.getConfig().set(player.getName() + ".nChunks", chunkList.get(player).size());
                        for(Chunk chunk : chunkList.get(player)){
                            main.kingdomClaimYML.getConfig().set(player.getName() + ".chunk" + i + ".world", chunk.getWorld().getName());
                            main.kingdomClaimYML.getConfig().set(player.getName() + ".chunk" + i + ".x", chunk.getX());
                            main.kingdomClaimYML.getConfig().set(player.getName() + ".chunk" + i + ".z", chunk.getZ());
                            i++;
                        }
                        main.kingdomClaimYML.saveConfig();
                        player.sendMessage("chunks saved to config");
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("load")){
                        if(!main.kingdomClaimYML.getConfig().contains(player.getName())){
                            player.sendMessage("You don't have any claims");
                            return false;
                        }

                        if(main.kingdomClaimYML.getConfig().get(player.getName()) == null){
                            player.sendMessage("You don't have any claims");
                            return false;
                        }

                        int i = main.kingdomClaimYML.getConfig().getInt(player.getName() + ".nChunks");
                        ArrayList<Chunk> chunkArray = new ArrayList<>();
                        for(int j = 0; j < i; j++){
                            String world = main.kingdomClaimYML.getConfig().getString(player.getName() + ".chunk" + j + ".world");
                            int x = main.kingdomClaimYML.getConfig().getInt(player.getName() + ".chunk" + j + ".x");
                            int z = main.kingdomClaimYML.getConfig().getInt(player.getName() + ".chunk" + j + ".z");
                            player.sendMessage("config values world: " + world + " x: " + x + " z: " + z);
                            Chunk chunk = Bukkit.getWorld(world).getChunkAt(x,z);
                            player.sendMessage("chunk values world: " + world + " x: " + x + " z: " + z);
                            chunkArray.add(chunk);
                        }

                        chunkList.put(player, chunkArray);
                        player.sendMessage("chunks loaded");
                    }
                    return false;
                }

                Chunk chunk = player.getLocation().getChunk();
                if(!chunkList.containsKey(player)){
                    ArrayList<Chunk> chunks = new ArrayList<>();
                    chunks.add(chunk);
                    chunkList.put(player, chunks);
                    player.sendMessage("chunk claimed\nx: " + chunk.getX() + "\nz: " + chunk.getZ());
                    return false;
                }

                if(chunkList.get(player).contains(chunk)){
                    player.sendMessage("chunk already claimed by you");
                }else{
                    player.sendMessage("chunk claimed\nx: " + chunk.getX() + "\nz: " + chunk.getZ());
                    chunkList.get(player).add(chunk);
                }*/
                //freezeEntity(shop);
            }else{
                player.sendMessage(main.textUtils.noPerms);
            }
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }

    /*@EventHandler
    public void onPlace(BlockPlaceEvent event){
        Location loc = event.getBlock().getLocation();
        // PLACE Mystery Lunchbox Hologram
        if(event.getBlock().getType().equals(Material.PURPLE_SHULKER_BOX)){
            ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
            as.setGravity(true);
            as.setCanPickupItems(false);
            as.setCustomName(ChatColor.translateAlternateColorCodes('&', "&7Mystery &5&lLunchbox"));
            as.setCustomNameVisible(true);
            as.setVisible(false);
            as.setSmall(true);
            as.setCollidable(false);
            as.setSwimming(true);
        }
    }*/
}
