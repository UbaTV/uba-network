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

public class MiscGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    public MiscGUI() {
        this.inv = Bukkit.createInventory(this, 9*3, "§5Misc §7Shop");
    }

    public void createGUI(Player player){
        ItemStack xpbottle = main.itemAPI.item(Material.EXPERIENCE_BOTTLE, "§5Experience Bottle", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.EXPERIENCE_BOTTLE) + main.textUtils.coinsSymbol);
        ItemStack waterBucket = main.itemAPI.item(Material.WATER_BUCKET, "§5Water Bucket", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.WATER_BUCKET) + main.textUtils.coinsSymbol);
        ItemStack lavaBucket = main.itemAPI.item(Material.LAVA_BUCKET, "§5Lava Bucket", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.LAVA_BUCKET) + main.textUtils.coinsSymbol);
        ItemStack ironHorseArmor = main.itemAPI.item(Material.IRON_HORSE_ARMOR, "§5Iron Horse Armor", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.IRON_HORSE_ARMOR) + main.textUtils.coinsSymbol);
        ItemStack goldHorseArmor = main.itemAPI.item(Material.GOLDEN_HORSE_ARMOR, "§5Gold Horse Armor", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.GOLDEN_HORSE_ARMOR) + main.textUtils.coinsSymbol);
        ItemStack diamondHorseArmor = main.itemAPI.item(Material.DIAMOND_HORSE_ARMOR, "§5Diamond Horse Armor", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.DIAMOND_HORSE_ARMOR) + main.textUtils.coinsSymbol);
        ItemStack nametag = main.itemAPI.item(Material.NAME_TAG, "§5Name Tag", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.NAME_TAG) + main.textUtils.coinsSymbol);

        inv.setItem(10, xpbottle);
        inv.setItem(11, waterBucket);
        inv.setItem(12, lavaBucket);
        inv.setItem(13, ironHorseArmor);
        inv.setItem(14, goldHorseArmor);
        inv.setItem(15, diamondHorseArmor);
        inv.setItem(16, nametag);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Misc §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 10) main.shopUtils.buyItem(player, Material.EXPERIENCE_BOTTLE);
        if(slot == 11) main.shopUtils.buyItem(player, Material.WATER_BUCKET);
        if(slot == 12) main.shopUtils.buyItem(player, Material.LAVA_BUCKET);
        if(slot == 13) main.shopUtils.buyItem(player, Material.IRON_HORSE_ARMOR);
        if(slot == 14) main.shopUtils.buyItem(player, Material.GOLDEN_HORSE_ARMOR);
        if(slot == 15) main.shopUtils.buyItem(player, Material.DIAMOND_HORSE_ARMOR);
        if(slot == 16) main.shopUtils.buyItem(player, Material.NAME_TAG);
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
