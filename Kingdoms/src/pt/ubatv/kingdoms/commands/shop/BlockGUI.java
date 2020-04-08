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
        this.inv = Bukkit.createInventory(this, 9*3, "§5Block §7Shop");
    }

    public void createGUI(Player player){
        ItemStack cobble = main.itemAPI.item(Material.COBBLESTONE, "§5Cobblestone", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.COBBLESTONE) + main.textUtils.coinsSymbol);
        ItemStack stone = main.itemAPI.item(Material.STONE, "§5Stone", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.STONE) + main.textUtils.coinsSymbol);
        ItemStack grass = main.itemAPI.item(Material.GRASS_BLOCK, "§5Grass", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.GRASS_BLOCK) + main.textUtils.coinsSymbol);
        ItemStack dirt = main.itemAPI.item(Material.DIRT, "§5Dirt", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.DIRT) + main.textUtils.coinsSymbol);
        ItemStack sand = main.itemAPI.item(Material.SAND, "§5Sand", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.SAND) + main.textUtils.coinsSymbol);
        ItemStack oaklog = main.itemAPI.item(Material.OAK_LOG, "§5Oak Log", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.OAK_LOG) + main.textUtils.coinsSymbol);
        ItemStack sprucelog = main.itemAPI.item(Material.SPRUCE_LOG, "§5Spruce Log", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.SPRUCE_LOG) + main.textUtils.coinsSymbol);
        ItemStack birchlog = main.itemAPI.item(Material.BIRCH_LOG, "§5Birch Log", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.BIRCH_LOG) + main.textUtils.coinsSymbol);
        ItemStack junglelog = main.itemAPI.item(Material.JUNGLE_LOG, "§5Jungle Log", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.JUNGLE_LOG) + main.textUtils.coinsSymbol);
        ItemStack acacialog = main.itemAPI.item(Material.ACACIA_LOG, "§5Acacia Log", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.ACACIA_LOG) + main.textUtils.coinsSymbol);
        ItemStack darkoaklog = main.itemAPI.item(Material.DARK_OAK_LOG, "§5Dark Oak Log", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.DARK_OAK_LOG) + main.textUtils.coinsSymbol);
        ItemStack gravel = main.itemAPI.item(Material.GRAVEL, "§5Gravel", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.GRAVEL) + main.textUtils.coinsSymbol);
        ItemStack ice = main.itemAPI.item(Material.ICE, "§5Ice", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.ICE) + main.textUtils.coinsSymbol);
        ItemStack endStone = main.itemAPI.item(Material.END_STONE, "§5End Stone", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.END_STONE) + main.textUtils.coinsSymbol);
        ItemStack soulSand = main.itemAPI.item(Material.SOUL_SAND, "§5Soul Sand", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.SOUL_SAND) + main.textUtils.coinsSymbol);
        ItemStack obsidian = main.itemAPI.item(Material.OBSIDIAN, "§5Obsidian", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.OBSIDIAN) + main.textUtils.coinsSymbol);
        ItemStack magma = main.itemAPI.item(Material.MAGMA_BLOCK, "§5Magma Block", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.MAGMA_BLOCK) + main.textUtils.coinsSymbol);
        ItemStack glowstone = main.itemAPI.item(Material.GLOWSTONE, "§5Glowstone", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.GLOWSTONE) + main.textUtils.coinsSymbol);
        ItemStack prismarine = main.itemAPI.item(Material.PRISMARINE, "§5Prismarine", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.PRISMARINE) + main.textUtils.coinsSymbol);
        ItemStack darkPrismarine = main.itemAPI.item(Material.DARK_PRISMARINE, "§5Dark Prismarine", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.DARK_PRISMARINE) + main.textUtils.coinsSymbol);
        ItemStack seaLantern = main.itemAPI.item(Material.SEA_LANTERN, "§5Sea Lantern", "§7Buy: §5" + main.priceUtils.getBuyPrice(Material.SEA_LANTERN) + main.textUtils.coinsSymbol);


        inv.setItem(0, cobble);
        inv.setItem(1, stone);
        inv.setItem(2, grass);
        inv.setItem(3, dirt);
        inv.setItem(4, sand);
        inv.setItem(5, oaklog);
        inv.setItem(6, sprucelog);
        inv.setItem(7, birchlog);
        inv.setItem(8, junglelog);
        inv.setItem(9, acacialog);
        inv.setItem(10, darkoaklog);
        inv.setItem(11, gravel);
        inv.setItem(12, ice);
        inv.setItem(13, endStone);
        inv.setItem(14, soulSand);
        inv.setItem(15, obsidian);
        inv.setItem(16, magma);
        inv.setItem(17, glowstone);
        inv.setItem(18, prismarine);
        inv.setItem(19, darkPrismarine);
        inv.setItem(20, seaLantern);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Block §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if(slot == 0) main.shopUtils.buyItem(player, Material.COBBLESTONE);
        if(slot == 1) main.shopUtils.buyItem(player, Material.STONE);
        if(slot == 2) main.shopUtils.buyItem(player, Material.GRASS_BLOCK);
        if(slot == 3) main.shopUtils.buyItem(player, Material.DIRT);
        if(slot == 4) main.shopUtils.buyItem(player, Material.SAND);
        if(slot == 5) main.shopUtils.buyItem(player, Material.OAK_LOG);
        if(slot == 6) main.shopUtils.buyItem(player, Material.SPRUCE_LOG);
        if(slot == 7) main.shopUtils.buyItem(player, Material.BIRCH_LOG);
        if(slot == 8) main.shopUtils.buyItem(player, Material.JUNGLE_LOG);
        if(slot == 9) main.shopUtils.buyItem(player, Material.ACACIA_LOG);
        if(slot == 10) main.shopUtils.buyItem(player, Material.DARK_OAK_LOG);
        if(slot == 11) main.shopUtils.buyItem(player, Material.GRAVEL);
        if(slot == 12) main.shopUtils.buyItem(player, Material.ICE);
        if(slot == 13) main.shopUtils.buyItem(player, Material.END_STONE);
        if(slot == 14) main.shopUtils.buyItem(player, Material.SOUL_SAND);
        if(slot == 15) main.shopUtils.buyItem(player, Material.OBSIDIAN);
        if(slot == 16) main.shopUtils.buyItem(player, Material.MAGMA_BLOCK);
        if(slot == 17) main.shopUtils.buyItem(player, Material.GLOWSTONE);
        if(slot == 18) main.shopUtils.buyItem(player, Material.PRISMARINE);
        if(slot == 19) main.shopUtils.buyItem(player, Material.DARK_PRISMARINE);
        if(slot == 20) main.shopUtils.buyItem(player, Material.SEA_LANTERN);
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
