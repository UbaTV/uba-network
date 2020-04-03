package pt.ubatv.kingdoms.commands.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.utils.UserData;

public class ShopUtils {

    private Main main = Main.getInstance();

    public void buyItem(Player player, Material mat){
        UserData userData = main.userDataTable.online.get(player.getUniqueId());
        int balance = userData.getCoins();
        int price = main.priceUtils.getPrice(mat);
        if(balance >= price){
            main.itemAPI.addItemToInv(player, mat);
            userData.setCoins(balance - price);
        }else{
            player.sendMessage(main.textUtils.error + "You don't have enough money.");
        }
    }
}
