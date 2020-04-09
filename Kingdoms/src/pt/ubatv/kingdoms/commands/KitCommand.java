package pt.ubatv.kingdoms.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pt.ubatv.kingdoms.Main;
import pt.ubatv.kingdoms.configs.CooldownYML;

public class KitCommand implements CommandExecutor {

    private Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("starter")){
                    String uuidString = player.getUniqueId().toString();
                    if(CooldownYML.starterCooldowns.containsKey(uuidString)){
                        player.sendMessage(main.textUtils.error + "§7You need to wait " + main.textUtils.secondsToText(CooldownYML.starterCooldowns.get(uuidString)) + " §7before you can use this kit.");
                    }else{
                        ItemStack helmet = main.itemAPI.itemEnchanted(Material.CHAINMAIL_HELMET, 1, "§5STARTER §7KIT", Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL);
                        ItemStack chestplate = main.itemAPI.itemEnchanted(Material.CHAINMAIL_CHESTPLATE, 1, "§5STARTER §7KIT", Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL);
                        ItemStack legs = main.itemAPI.itemEnchanted(Material.CHAINMAIL_LEGGINGS, 1, "§5STARTER §7KIT", Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL);
                        ItemStack boots = main.itemAPI.itemEnchanted(Material.CHAINMAIL_BOOTS, 1, "§5STARTER §7KIT", Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL);
                        ItemStack sword = main.itemAPI.itemEnchanted(Material.STONE_SWORD, 1, "§5STARTER §7KIT", Enchantment.DURABILITY, Enchantment.DAMAGE_ALL);
                        ItemStack pick = main.itemAPI.itemEnchanted(Material.STONE_PICKAXE, 1, "§5STARTER §7KIT", Enchantment.DURABILITY, Enchantment.DIG_SPEED);
                        ItemStack axe = main.itemAPI.itemEnchanted(Material.STONE_AXE, 1, "§5STARTER §7KIT", Enchantment.DURABILITY, Enchantment.DIG_SPEED);
                        ItemStack shovel = main.itemAPI.itemEnchanted(Material.STONE_SHOVEL, 1, "§5STARTER §7KIT", Enchantment.DURABILITY, Enchantment.DIG_SPEED);
                        ItemStack bow = main.itemAPI.item(Material.BOW, 1, "§5STARTER §7KIT");
                        ItemStack arrow = main.itemAPI.item(Material.ARROW, 16, "§5STARTER §7KIT");
                        ItemStack apple = main.itemAPI.item(Material.APPLE, 24, "§5STARTER §7KIT");

                        main.itemAPI.addItemToInv(player, helmet);
                        main.itemAPI.addItemToInv(player, chestplate);
                        main.itemAPI.addItemToInv(player, legs);
                        main.itemAPI.addItemToInv(player, boots);
                        main.itemAPI.addItemToInv(player, sword);
                        main.itemAPI.addItemToInv(player, pick);
                        main.itemAPI.addItemToInv(player, axe);
                        main.itemAPI.addItemToInv(player, shovel);
                        main.itemAPI.addItemToInv(player, bow);
                        main.itemAPI.addItemToInv(player, arrow);
                        main.itemAPI.addItemToInv(player, apple);

                        CooldownYML.starterCooldowns.put(uuidString, 43200); // 12 horas
                        player.sendMessage(main.textUtils.right + "You just received the §5starter §7kit.");
                    }
                }
                return false;
            }

            player.sendMessage(main.textUtils.right + "§7Available kits: §5starter");
        }else{
            Bukkit.getServer().getConsoleSender().sendMessage(main.textUtils.playerOnly);
        }
        return false;
    }
}
