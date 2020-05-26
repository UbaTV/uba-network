package xyz.ubatv.pve.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.ubatv.pve.Main;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class LocationYML {

    private Main main = Main.getInstance();

    public Location lobby;
    public Location game;
    public Set<Location> mobSpawn;

    private File file;
    public FileConfiguration config;

    public void setupLocations(){
        for(String name : config.getConfigurationSection("").getKeys(false)) {
            if(name.contentEquals("mobspawn")){
                mobSpawn.add(getLocation(name));
            }
        }

        if(getConfig().contains("lobby.x")){
            lobby = getLocation("lobby");
        }else{
            Bukkit.getConsoleSender().sendMessage("Lobby location not defined.");
        }

        if(getConfig().contains("game.x")){
            game = getLocation("game");
        }else{
            Bukkit.getConsoleSender().sendMessage("Game location not defined.");
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
