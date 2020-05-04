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

public class MobDropsGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    public MobDropsGUI() {
        this.inv = Bukkit.createInventory(this, 9*3, "§5Mob §7Shop");
    }

    public void createGUI(Player player){
        ItemStack gunpowder = main.itemAPI.item(Material.GUNPOWDER, "§5Gunpowder", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.GUNPOWDER) + main.textUtils.coinsSymbol);
        ItemStack string = main.itemAPI.item(Material.STRING, "§5String", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.STRING) + main.textUtils.coinsSymbol);
        ItemStack spidereye = main.itemAPI.item(Material.SPIDER_EYE, "§5Spider Eye", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.SPIDER_EYE) + main.textUtils.coinsSymbol);
        ItemStack rottenflesh = main.itemAPI.item(Material.ROTTEN_FLESH, "§5Rotten Flesh", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.ROTTEN_FLESH) + main.textUtils.coinsSymbol);
        ItemStack bone = main.itemAPI.item(Material.BONE, "§5Bone", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.BONE) + main.textUtils.coinsSymbol);
        ItemStack arrow = main.itemAPI.item(Material.ARROW, "§5Arrow", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.ARROW) + main.textUtils.coinsSymbol);
        ItemStack slimeball = main.itemAPI.item(Material.SLIME_BALL, "§5Slime Ball", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.SLIME_BALL) + main.textUtils.coinsSymbol);
        ItemStack magmacream = main.itemAPI.item(Material.MAGMA_CREAM, "§5Magma Cream", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.MAGMA_CREAM) + main.textUtils.coinsSymbol);
        ItemStack enderpearl = main.itemAPI.item(Material.ENDER_PEARL, "§5Endear Pearl", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.ENDER_PEARL) + main.textUtils.coinsSymbol);
        ItemStack blazerod = main.itemAPI.item(Material.BLAZE_ROD, "§5Blaze Rod", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.BLAZE_ROD) + main.textUtils.coinsSymbol);
        ItemStack ghasttear = main.itemAPI.item(Material.GHAST_TEAR, "§5Ghast Tear", "§7Sell: §5" + main.priceUtils.getSellPrice(Material.GHAST_TEAR) + main.textUtils.coinsSymbol);

        inv.setItem(3, gunpowder);
        inv.setItem(4, string);
        inv.setItem(5, spidereye);
        inv.setItem(11, rottenflesh);
        inv.setItem(12, bone);
        inv.setItem(13, arrow);
        inv.setItem(14, slimeball);
        inv.setItem(15, magmacream);
        inv.setItem(21, enderpearl);
        inv.setItem(22, blazerod);
        inv.setItem(23, ghasttear);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Mob §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 3) main.shopUtils.sellItem(player, Material.GUNPOWDER);
        if(slot == 4) main.shopUtils.sellItem(player, Material.STRING);
        if(slot == 5) main.shopUtils.sellItem(player, Material.SPIDER_EYE);
        if(slot == 11) main.shopUtils.sellItem(player, Material.ROTTEN_FLESH);
        if(slot == 12) main.shopUtils.sellItem(player, Material.BONE);
        if(slot == 13) main.shopUtils.sellItem(player, Material.ARROW);
        if(slot == 14) main.shopUtils.sellItem(player, Material.SLIME_BALL);
        if(slot == 15) main.shopUtils.sellItem(player, Material.MAGMA_CREAM);
        if(slot == 21) main.shopUtils.sellItem(player, Material.ENDER_PEARL);
        if(slot == 22) main.shopUtils.sellItem(player, Material.BLAZE_ROD);
        if(slot == 23) main.shopUtils.sellItem(player, Material.GHAST_TEAR);

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
