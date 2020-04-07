package pt.ubatv.kingdoms.commands.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.utils.UserData;

public class ShopUtils {

    private Main main = Main.getInstance();

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
