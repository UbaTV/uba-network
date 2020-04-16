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

public class OresGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    public OresGUI() {
        this.inv = Bukkit.createInventory(this, 9*3, "§5Ores §7Shop");
    }

    public void createGUI(Player player){
        ItemStack coal = main.itemAPI.item(Material.COAL, "§5Coal", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.COAL) + main.textUtils.coinsSymbol);
        ItemStack charcoal = main.itemAPI.item(Material.CHARCOAL, "§5Charcoal", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.CHARCOAL) + main.textUtils.coinsSymbol);
        ItemStack iron = main.itemAPI.item(Material.IRON_INGOT, "§5Iron Ingot", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.IRON_INGOT) + main.textUtils.coinsSymbol);
        ItemStack gold = main.itemAPI.item(Material.GOLD_INGOT, "§5Gold Ingot", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.GOLD_INGOT) + main.textUtils.coinsSymbol);
        ItemStack emerald = main.itemAPI.item(Material.EMERALD, "§5Emerald", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.EMERALD) + main.textUtils.coinsSymbol);
        ItemStack diamond = main.itemAPI.item(Material.DIAMOND, "§5Diamond", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.DIAMOND) + main.textUtils.coinsSymbol);
        ItemStack quartz = main.itemAPI.item(Material.QUARTZ, "§5Quartz", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.QUARTZ) + main.textUtils.coinsSymbol);
        ItemStack lapis = main.itemAPI.item(Material.LAPIS_LAZULI, "§5Lapis Lazulli", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.LAPIS_LAZULI) + main.textUtils.coinsSymbol);
        ItemStack redstone = main.itemAPI.item(Material.REDSTONE, "§5Redstone", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.REDSTONE) + main.textUtils.coinsSymbol);

        inv.setItem(9, coal);
        inv.setItem(10, charcoal);
        inv.setItem(11, iron);
        inv.setItem(12, gold);
        inv.setItem(13, emerald);
        inv.setItem(14, diamond);
        inv.setItem(15, quartz);
        inv.setItem(16, lapis);
        inv.setItem(17, redstone);
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

        if(slot == 9) main.shopUtils.sellItem(player, Material.COAL);
        if(slot == 10) main.shopUtils.sellItem(player, Material.CHARCOAL);
        if(slot == 11) main.shopUtils.sellItem(player, Material.IRON_INGOT);
        if(slot == 12) main.shopUtils.sellItem(player, Material.GOLD_INGOT);
        if(slot == 13) main.shopUtils.sellItem(player, Material.EMERALD);
        if(slot == 14) main.shopUtils.sellItem(player, Material.DIAMOND);
        if(slot == 15) main.shopUtils.sellItem(player, Material.QUARTZ);
        if(slot == 16) main.shopUtils.sellItem(player, Material.LAPIS_LAZULI);
        if(slot == 17) main.shopUtils.sellItem(player, Material.REDSTONE);
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
