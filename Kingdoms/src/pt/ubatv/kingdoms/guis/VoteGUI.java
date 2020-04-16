package pt.ubatv.kingdoms.guis;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import pt.ubatv.kingdoms.Main;

public class VoteGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    public VoteGUI() {
        this.inv = Bukkit.createInventory(this, 9, "§5Vote");
    }

    public void createGUI(Player player){
        ItemStack minecraftServer = main.itemAPI.item(Material.GRASS_BLOCK, "§5Minecraft-server.com", "Click to get link.");

        inv.setItem(0, minecraftServer);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Ores §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 0){
            player.closeInventory();
            player.sendMessage( main.textUtils.right + "Vote Here: §5https://minecraft-server.net/details/ubatv/");
        }
    }

    public void openInventory(Player player){
        createGUI(player);
        player.openInventory(inv);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }
}
