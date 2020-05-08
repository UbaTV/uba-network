package xyz.ubatv.kingdoms.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.ubatv.kingdoms.Main;

public class KingdomsShopGUI implements Listener {

    private Main main = Main.getInstance();

    private int flyPrice = 1000000;
    private int flyLevelUnlock = 3;

    public void openGUI(Player player){
        Inventory inv = Bukkit.createInventory(player, 9*3, "§5Kingdoms §7Shop");

        String userKingdom = main.mainUserData.online.get(player.getUniqueId()).getKingdom();
        int kingdomsLevel = main.kingdomsTable.getLevel(userKingdom);

        ItemStack fly;

        if(kingdomsLevel < 5){
            fly = main.itemAPI.item(Material.FEATHER, "§5Fly Mode", "§7This upgrade allows §7§nflight §r§7in claimed chunks.", "§cUnlocks at level " + flyLevelUnlock);
        }else{
            if(main.kingdomsYML.getConfig().getBoolean(userKingdom + ".fly")){
                fly = main.itemAPI.item(Material.FEATHER, "§5Fly Mode", "§7This upgrade allows §7§nflight §r§7in claimed chunks.", "§aBOUGHT");
            }else{
                fly = main.itemAPI.item(Material.FEATHER, "§5Fly Mode", "§7This upgrade allows §7§nflight §r§7in claimed chunks.", "§7Price: §5" + flyPrice + main.textUtils.coinsSymbol);
            }
        }

        inv.setItem(9, fly);
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equalsIgnoreCase("§5Kingdoms §7Shop")) {
            return;
        }
        if(event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if(item == null || item.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();
        String userKingdom = main.mainUserData.online.get(player.getUniqueId()).getKingdom();

        if(slot == 9){
            if(!main.kingdomsYML.getConfig().getBoolean(userKingdom + ".fly")){
                String king = main.kingdomsTable.getOwner(userKingdom);
                int kingdomLevel = main.kingdomsTable.getLevel(userKingdom);
                if(!king.equalsIgnoreCase(player.getName())){
                    player.sendMessage(main.textUtils.error + "You must be user kingdom to use the shop.");
                    return;
                }

                if(kingdomLevel < flyLevelUnlock){
                    player.sendMessage(main.textUtils.error + "§7Your kingdom must be level §5" + flyLevelUnlock + " §7to buy this upgrade.");
                    return;
                }

                int vault = main.kingdomsTable.getCoins(userKingdom);
                if(vault < flyPrice){
                    player.sendMessage(main.textUtils.error + "Your kingdoms vault does not have enough coins to buy this upgrade.");
                    return;
                }

                main.kingdomsTable.updateVault(userKingdom, vault - flyPrice);
                main.kingdomsYML.getConfig().set(userKingdom.toLowerCase() + ".fly", true);
                main.kingdomsYML.saveConfig();
                main.kingdomUtils.broadcastKingdom(userKingdom.toLowerCase(), "Your kingdom just unlocked §5flight§7.");
            }
        }
    }
}
