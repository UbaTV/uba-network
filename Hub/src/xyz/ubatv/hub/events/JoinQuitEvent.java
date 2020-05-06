package xyz.ubatv.hub.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.hub.Main;

public class JoinQuitEvent implements Listener {

    private Main main = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage("§7[§a+§7] §7" + player.getName());
        player.setGameMode(GameMode.ADVENTURE);
        giveHotbar(player);

        main.textUtils.sendCenteredMessage(player, "§7Welcome to the §5UbaNetwork§7!");
        player.sendMessage(" ");
        main.textUtils.sendCenteredMessage(player, "§7Website: §5ubatv.xyz");
        main.textUtils.sendCenteredMessage(player, "§7Discord: §5discord.gg/AJxFu2C");
        player.sendMessage(" ");

        player.teleport(main.locationYML.spawn);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage("§7[§c-§7] §7" + player.getName());
    }

    public void giveHotbar(Player player){
        Inventory inv = player.getInventory();
        inv.clear();

        ItemStack servers = main.itemAPI.item(Material.COMPASS, "§5Join Server", "§7Right-click and choose a", "§7server to join.");

        inv.setItem(4, servers);
    }
}
