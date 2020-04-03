package pt.ubatv.kingdoms.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.w3c.dom.events.EventException;
import pt.ubatv.kingdoms.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemAPI {

    private Main main = Main.getInstance();

    public ItemStack item(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(null);
        item.setItemMeta(meta);
        return item;
    }


    public ItemStack item(Material material, String name, String...lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>(Arrays.asList(lore));
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack item(Material material, int quantity, String name) {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(null);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack item(Material material, int quantity, String name, String...lore) {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>(Arrays.asList(lore));
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack skull(Player player, String name, String...lore){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(player);
        meta.setDisplayName(name);
        ArrayList<String> metaLore = new ArrayList<String>(Arrays.asList(lore));
        meta.setLore(metaLore);
        skull.setItemMeta(meta);
        return skull;
    }

    public void addItemToInv(Player player, Material mat){
        Inventory inv = player.getInventory();
        ItemStack item = new ItemStack(mat, 1);

        if(inv.firstEmpty() != -1){
            player.getInventory().addItem(item);
            return;
        }

        try{
            for(ItemStack current : inv.getContents()) {
                if(current.getType() != Material.AIR){
                    if((current.getType().equals(mat) && current.getAmount() < current.getMaxStackSize())) {
                        player.getInventory().addItem(item);
                        return;
                    }
                }
            }
        }catch (NullPointerException | EventException ignored){
            player.sendMessage(main.textUtils.error + "Your inventory is full.");
        }
    }
}
