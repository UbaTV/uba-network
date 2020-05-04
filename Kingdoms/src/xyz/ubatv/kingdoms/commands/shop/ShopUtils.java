package xyz.ubatv.kingdoms.commands.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.utils.UserData;

public class ShopUtils {

    private Main main = Main.getInstance();

    public void buyPotion(Player player, PotionType potionType){
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        int balance = userData.getCoins();
        int price = main.priceUtils.getPotionBuyPrice(potionType);
        if(balance >= price){
            main.itemAPI.addPotionToInv(player, potionType);
            userData.setCoins(balance - price);
        }else{
            player.sendMessage(main.textUtils.error + "You don't have enough money.");
        }
    }

    public void buyItem(Player player, Material mat){
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        int balance = userData.getCoins();
        int price = main.priceUtils.getBuyPrice(mat);
        if(balance >= price){
            main.itemAPI.addItemToInv(player, mat);
            userData.setCoins(balance - price);
        }else{
            player.sendMessage(main.textUtils.error + "You don't have enough money.");
        }
    }

    public void sellItem(Player player, Material mat){
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        int balance = userData.getCoins();
        int price = main.priceUtils.getSellPrice(mat);
        Inventory inv = player.getInventory();
        if(inv.contains(mat)){
            for(ItemStack item : inv.getContents()){
                if(item != null){
                    if(item.getType().equals(mat)){
                        item.setAmount(item.getAmount() - 1);
                        userData.setCoins(balance + price);
                        return;
                    }
                }
            }
        }else{
            player.sendMessage(main.textUtils.error + "You don't have this item to sell.");
        }
    }
}
