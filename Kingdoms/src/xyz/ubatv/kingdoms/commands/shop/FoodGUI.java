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

public class FoodGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    public FoodGUI() {
        this.inv = Bukkit.createInventory(this, 9*2, "§5Food §7Shop");
    }

    public void createGUI(Player player){
        ItemStack beef = main.itemAPI.item(Material.BEEF, "§5Beef", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.BEEF) + main.textUtils.coinsSymbol);
        ItemStack porkchop = main.itemAPI.item(Material.PORKCHOP, "§5Porkchop", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.PORKCHOP) + main.textUtils.coinsSymbol);
        ItemStack mutton = main.itemAPI.item(Material.MUTTON, "§5Mutton", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.MUTTON) + main.textUtils.coinsSymbol);
        ItemStack chicken = main.itemAPI.item(Material.CHICKEN, "§5Chicken", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.CHICKEN) + main.textUtils.coinsSymbol);
        ItemStack rabbit = main.itemAPI.item(Material.RABBIT, "§5Rabbit", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.RABBIT) + main.textUtils.coinsSymbol);
        ItemStack cbeef = main.itemAPI.item(Material.COOKED_BEEF, "§5Cooked Beef", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.COOKED_BEEF) + main.textUtils.coinsSymbol);
        ItemStack cporkchop = main.itemAPI.item(Material.COOKED_PORKCHOP, "§5Cooked Porkchop", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.COOKED_PORKCHOP) + main.textUtils.coinsSymbol);
        ItemStack cmutton = main.itemAPI.item(Material.COOKED_MUTTON, "§5Cooked Mutton", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.COOKED_MUTTON) + main.textUtils.coinsSymbol);
        ItemStack cchicken = main.itemAPI.item(Material.COOKED_CHICKEN, "§5Cooked Chicken", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.COOKED_CHICKEN) + main.textUtils.coinsSymbol);
        ItemStack crabbit = main.itemAPI.item(Material.COOKED_RABBIT, "§5Cooked Rabbit", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.COOKED_RABBIT) + main.textUtils.coinsSymbol);
        ItemStack bread = main.itemAPI.item(Material.BREAD, "§5Bread", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.BREAD) + main.textUtils.coinsSymbol);
        ItemStack beetroot = main.itemAPI.item(Material.BEETROOT, "§5Beetroot", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.BEETROOT) + main.textUtils.coinsSymbol);
        ItemStack melon = main.itemAPI.item(Material.MELON_SLICE, "§5Melon", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.MELON_SLICE) + main.textUtils.coinsSymbol);
        ItemStack carrot = main.itemAPI.item(Material.CARROT, "§5Carrot", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.CARROT) + main.textUtils.coinsSymbol);
        ItemStack apple = main.itemAPI.item(Material.APPLE, "§5Apple", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.APPLE) + main.textUtils.coinsSymbol);
        ItemStack potato = main.itemAPI.item(Material.POTATO, "§5Potato", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.POTATO) + main.textUtils.coinsSymbol);


        inv.setItem(0, beef);
        inv.setItem(1, porkchop);
        inv.setItem(2, mutton);
        inv.setItem(3, chicken);
        inv.setItem(4, rabbit);
        inv.setItem(9, cbeef);
        inv.setItem(10, cporkchop);
        inv.setItem(11, cmutton);
        inv.setItem(12, cchicken);
        inv.setItem(13, crabbit);
        inv.setItem(6, bread);
        inv.setItem(7, beetroot);
        inv.setItem(8, melon);
        inv.setItem(15, carrot);
        inv.setItem(16, apple);
        inv.setItem(17, potato);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Food §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 0) main.shopUtils.buyItem(player, Material.BEEF);
        if(slot == 1) main.shopUtils.buyItem(player, Material.PORKCHOP);
        if(slot == 2) main.shopUtils.buyItem(player, Material.MUTTON);
        if(slot == 3) main.shopUtils.buyItem(player, Material.CHICKEN);
        if(slot == 4) main.shopUtils.buyItem(player, Material.RABBIT);
        if(slot == 9) main.shopUtils.buyItem(player, Material.COOKED_BEEF);
        if(slot == 10) main.shopUtils.buyItem(player, Material.COOKED_PORKCHOP);
        if(slot == 11) main.shopUtils.buyItem(player, Material.COOKED_MUTTON);
        if(slot == 12) main.shopUtils.buyItem(player, Material.COOKED_CHICKEN);
        if(slot == 13) main.shopUtils.buyItem(player, Material.COOKED_RABBIT);
        if(slot == 6) main.shopUtils.buyItem(player, Material.BREAD);
        if(slot == 7) main.shopUtils.buyItem(player, Material.BEETROOT);
        if(slot == 8) main.shopUtils.buyItem(player, Material.MELON_SLICE);
        if(slot == 15) main.shopUtils.buyItem(player, Material.CARROT);
        if(slot == 16) main.shopUtils.buyItem(player, Material.APPLE);
        if(slot == 17) main.shopUtils.buyItem(player, Material.POTATO);
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
