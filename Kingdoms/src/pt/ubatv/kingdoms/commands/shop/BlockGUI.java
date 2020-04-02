package pt.ubatv.kingdoms.commands.shop;

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

public class BlockGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    public BlockGUI() {
        this.inv = Bukkit.createInventory(this, 9*3, "§5Blocks Shop");
    }

    public void createGUI(){
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory().getHolder() != this) return;
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();
    }

    public void openInventory(Player player){
        player.openInventory(inv);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }
}
