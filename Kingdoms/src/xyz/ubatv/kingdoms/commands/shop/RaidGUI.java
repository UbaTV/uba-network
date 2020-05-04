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
import org.bukkit.potion.PotionType;
import xyz.ubatv.kingdoms.Main;

public class RaidGUI implements InventoryHolder, Listener {

    private Main main = Main.getInstance();

    private final Inventory inv;

    public RaidGUI() {
        this.inv = Bukkit.createInventory(this, 9, "§5Raid §7Shop");
    }

    public void createGUI(Player player){
        ItemStack tnt = main.itemAPI.item(Material.TNT, "§5TNT", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.TNT) + main.textUtils.coinsSymbol);
        ItemStack flintNSteel = main.itemAPI.item(Material.FLINT_AND_STEEL, "§5Flint and Steel", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.FLINT_AND_STEEL) + main.textUtils.coinsSymbol);
        ItemStack creeperSpawnEgg = main.itemAPI.item(Material.CREEPER_SPAWN_EGG, "§5Creeper Spawn Egg", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.CREEPER_SPAWN_EGG) + main.textUtils.coinsSymbol);
        ItemStack potionInvisibility = main.itemAPI.potion(PotionType.INVISIBILITY, "§5Potion of Invisibility", "§7Buy: §5" + main.priceUtils.getPotionBuyPrice(PotionType.INVISIBILITY) + main.textUtils.coinsSymbol);
        ItemStack potionSpeed = main.itemAPI.potion(PotionType.SPEED, "§5Potion of Speed", "§7Buy: §5" + main.priceUtils.getPotionBuyPrice(PotionType.SPEED) + main.textUtils.coinsSymbol);
        ItemStack redstoneTorch = main.itemAPI.item(Material.REDSTONE_TORCH, "§5Redstone Torch", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.REDSTONE_TORCH) + main.textUtils.coinsSymbol);
        ItemStack sponge = main.itemAPI.item(Material.SPONGE, "§5Sponge", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.SPONGE) + main.textUtils.coinsSymbol);

        inv.setItem(1, tnt);
        inv.setItem(2, flintNSteel);
        inv.setItem(3, creeperSpawnEgg);
        inv.setItem(4, potionInvisibility);
        inv.setItem(5, potionSpeed);
        inv.setItem(6, redstoneTorch);
        inv.setItem(7, sponge);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Raid §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 1) main.shopUtils.buyItem(player, Material.TNT);
        if(slot == 2) main.shopUtils.buyItem(player, Material.FLINT_AND_STEEL);
        if(slot == 3) main.shopUtils.buyItem(player, Material.CREEPER_SPAWN_EGG);
        if(slot == 4) main.shopUtils.buyPotion(player, PotionType.INVISIBILITY);
        if(slot == 5) main.shopUtils.buyPotion(player, PotionType.SPEED);
        if(slot == 6) main.shopUtils.buyItem(player, Material.REDSTONE_TORCH);
        if(slot == 7) main.shopUtils.buyItem(player, Material.SPONGE);

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
