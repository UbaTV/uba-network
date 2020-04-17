package pt.ubatv.kingdoms.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pt.ubatv.kingdoms.Main;

import java.io.File;
import java.io.IOException;

public class KingdomsYML {

    private Main main = Main.getInstance();

    private File file;
    public FileConfiguration config;

    public void setKingdomHome(String kingdomName, Location location){
        String worldName = location.getWorld().getName();
        int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();
        float yaw = location.getYaw(), pitch = location.getPitch();

        getConfig().set(kingdomName + ".home.world", worldName);
        getConfig().set(kingdomName + ".home.x", x);
        getConfig().set(kingdomName + ".home.y", y);
        getConfig().set(kingdomName + ".home.z", z);
        getConfig().set(kingdomName + ".home.yaw", yaw);
        getConfig().set(kingdomName + ".home.pitch", pitch);
        saveConfig();
    }

    public Location getKingdomHome(String kingdomName){
        World world = Bukkit.getWorld(getConfig().getString(kingdomName + ".home.world"));
        double x = getConfig().getDouble(kingdomName + ".home.x"),
                y = getConfig().getDouble(kingdomName + ".home.y"),
                z = getConfig().getDouble(kingdomName + ".home.z");
        float yaw = (float) getConfig().getDouble(kingdomName + ".home.yaw"),
                pitch = (float) getConfig().getDouble(kingdomName + ".home.pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public void createConfig(){
        file = new File(main.getDataFolder(), "kingdoms.yml");
        if(!file.exists()){
            file.getParentFile().mkdirs();
            main.saveResource("kingdoms.yml", false);
        }

        config = new YamlConfiguration();
        try{
            config.load(file);
        }catch (IOException | InvalidConfigurationException e){
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load kingdoms.yml");
        }
        saveConfig();
    }

    public void reloadConfig(){
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to reload kingdoms.yml");
        }
    }

    public void saveConfig(){
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to save kingdoms.yml");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
