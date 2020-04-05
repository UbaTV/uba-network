package pt.ubatv.kingdoms.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pt.ubatv.kingdoms.Main;

import java.io.File;
import java.io.IOException;

public class KingdomClaimYML {

    private Main main = Main.getInstance();

    private File file;
    public FileConfiguration config;

    public void createConfig(){
        file = new File(main.getDataFolder(), "claims.yml");
        if(!file.exists()){
            file.getParentFile().mkdirs();
            main.saveResource("claims.yml", false);
        }

        config = new YamlConfiguration();
        try{
            config.load(file);
        }catch (IOException | InvalidConfigurationException e){
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load claims.yml");
        }
        saveConfig();
    }

    public void reloadConfig(){
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to reload claims.yml");
        }
    }

    public void saveConfig(){
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to save claims.yml");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
