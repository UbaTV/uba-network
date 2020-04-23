package pt.ubatv.kingdoms.lunchbox;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pt.ubatv.kingdoms.Main;

public class LunchboxGUI implements Listener {

    private Main main = Main.getInstance();

    public void openGUI(Player player){
        Inventory inv = Bukkit.createInventory(player, 9*6, "§7Mystery §5§lLunchbox");

        ItemStack cobble = main.itemAPI.item(Material.COBBLESTONE, "§5Cobblestone", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.COBBLESTONE) + main.textUtils.coinsSymbol);

        inv.setItem(0, cobble);
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§7Mystery §5§lLunchbox")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 0) main.shopUtils.buyItem(player, Material.COBBLESTONE);
    }
}
