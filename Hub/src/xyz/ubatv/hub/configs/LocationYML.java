package xyz.ubatv.hub.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.ubatv.hub.Main;

import java.io.File;
import java.io.IOException;

public class LocationYML {

    private Main main = Main.getInstance();

    public Location spawn;

    private File file;
    public FileConfiguration config;

    public void setupSpawn(){
        if(getConfig().contains("spawn.x")){
            spawn = getLocation("spawn");
        }else{
            Bukkit.getConsoleSender().sendMessage("Spawn location not defined.");
        }
    }

    public void setLocation(String locationName, Location location){
        String worldName = location.getWorld().getName();
        int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();
        float yaw = location.getYaw(), pitch = location.getPitch();

        getConfig().set(locationName + ".world", worldName);
        getConfig().set(locationName + ".x", x);
        getConfig().set(locationName + ".y", y);
        getConfig().set(locationName + ".z", z);
        getConfig().set(locationName + ".yaw", yaw);
        getConfig().set(locationName + ".pitch", pitch);
        saveConfig();
    }

    public Location getLocation(String locationName){
        World world = Bukkit.getWorld(getConfig().getString(locationName + ".world"));
        double x = getConfig().getDouble(locationName + ".x"),
                y = getConfig().getDouble(locationName + ".y"),
                z = getConfig().getDouble(locationName + ".z");
        float yaw = (float) getConfig().getDouble(locationName + ".yaw"),
                pitch = (float) getConfig().getDouble(locationName + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public void createConfig(){
        file = new File(main.getDataFolder(), "location.yml");
        if(!file.exists()){
            file.getParentFile().mkdirs();
            main.saveResource("location.yml", false);
        }

        config = new YamlConfiguration();
        try{
            config.load(file);
        }catch (IOException | InvalidConfigurationException e){
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load locations.yml");
        }
        saveConfig();
    }

    public void reloadConfig(){
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to reload locations.yml");
        }
    }

    public void saveConfig(){
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to save locations.yml");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
