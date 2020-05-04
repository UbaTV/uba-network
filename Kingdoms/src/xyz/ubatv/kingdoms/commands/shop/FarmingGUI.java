package xyz.ubatv.kingdoms.commands.shop;

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
import xyz.ubatv.kingdoms.Main;

public class FarmingGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    public FarmingGUI() {
        this.inv = Bukkit.createInventory(this, 9*3, "§5Farming §7Shop");
    }

    public void createGUI(Player player){
        ItemStack weathSeeds = main.itemAPI.item(Material.WHEAT_SEEDS, "§5Wheat Seeds", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.WHEAT_SEEDS) + main.textUtils.coinsSymbol);
        ItemStack wheat = main.itemAPI.item(Material.WHEAT, "§5Wheat", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.WHEAT) + main.textUtils.coinsSymbol);
        ItemStack spruceSap = main.itemAPI.item(Material.SPRUCE_SAPLING, "§5Spruce Sapling", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.SPRUCE_SAPLING) + main.textUtils.coinsSymbol);
        ItemStack oakSap = main.itemAPI.item(Material.OAK_SAPLING, "§5Oak Sapling", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.OAK_SAPLING) + main.textUtils.coinsSymbol);
        ItemStack birchSap = main.itemAPI.item(Material.BIRCH_SAPLING, "§5Birch Sapling", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.BIRCH_SAPLING) + main.textUtils.coinsSymbol);
        ItemStack jungleSap = main.itemAPI.item(Material.JUNGLE_SAPLING, "§5Jungle Sapling", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.JUNGLE_SAPLING) + main.textUtils.coinsSymbol);
        ItemStack acaciaSap = main.itemAPI.item(Material.ACACIA_SAPLING, "§5Acacia Sapling", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.ACACIA_SAPLING) + main.textUtils.coinsSymbol);
        ItemStack darkOakSap = main.itemAPI.item(Material.DARK_OAK_SAPLING, "§5Dark Oak Sapling", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.DARK_OAK_SAPLING) + main.textUtils.coinsSymbol);
        ItemStack cactus = main.itemAPI.item(Material.CACTUS, "§5Cactus", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.CACTUS) + main.textUtils.coinsSymbol);
        ItemStack netherwart = main.itemAPI.item(Material.NETHER_WART, "§5Netherwart", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.NETHER_WART) + main.textUtils.coinsSymbol);
        ItemStack sugarCane = main.itemAPI.item(Material.SUGAR_CANE, "§5Sugar Cane", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.SUGAR_CANE) + main.textUtils.coinsSymbol);

        inv.setItem(3, weathSeeds);
        inv.setItem(4, wheat);
        inv.setItem(5, spruceSap);
        inv.setItem(11, oakSap);
        inv.setItem(12, birchSap);
        inv.setItem(13, jungleSap);
        inv.setItem(14, acaciaSap);
        inv.setItem(15, darkOakSap);
        inv.setItem(21, cactus);
        inv.setItem(22, netherwart);
        inv.setItem(23, sugarCane);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Farming §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 3) main.shopUtils.buyItem(player, Material.WHEAT_SEEDS);
        if(slot == 4) main.shopUtils.buyItem(player, Material.WHEAT);
        if(slot == 5) main.shopUtils.buyItem(player, Material.SPRUCE_SAPLING);
        if(slot == 11) main.shopUtils.buyItem(player, Material.OAK_SAPLING);
        if(slot == 12) main.shopUtils.buyItem(player, Material.BIRCH_SAPLING);
        if(slot == 13) main.shopUtils.buyItem(player, Material.JUNGLE_SAPLING);
        if(slot == 14) main.shopUtils.buyItem(player, Material.ACACIA_SAPLING);
        if(slot == 15) main.shopUtils.buyItem(player, Material.DARK_OAK_SAPLING);
        if(slot == 21) main.shopUtils.buyItem(player, Material.CACTUS);
        if(slot == 22) main.shopUtils.buyItem(player, Material.NETHER_WART);
        if(slot == 23) main.shopUtils.buyItem(player, Material.SUGAR_CANE);
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
