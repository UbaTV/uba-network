package pt.ubatv.kingdoms.configs;

import org.bukkit.Bukkit;
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
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load locations.yml");
        }
        saveConfig();
    }

    public void defaultConfig(){
        if(!getConfig().contains("LEMORE.members")) getConfig().set("LEMORE.members", 0);
        if(!getConfig().contains("LEMORE.money")) getConfig().set("LEMORE.money", 0);
        if(!getConfig().contains("LEMORE.members")) getConfig().set("LEMORE.level", 0);

        if(!getConfig().contains("POTOR.members")) getConfig().set("POTOR.members", 0);
        if(!getConfig().contains("POTOR.money")) getConfig().set("POTOR.money", 0);
        if(!getConfig().contains("POTOR.members")) getConfig().set("POTOR.level", 0);

        if(!getConfig().contains("AUTICIA.members")) getConfig().set("AUTICIA.members", 0);
        if(!getConfig().contains("AUTICIA.money")) getConfig().set("AUTICIA.money", 0);
        if(!getConfig().contains("AUTICIA.members")) getConfig().set("AUTICIA.level", 0);

        if(!getConfig().contains("BRAGON.members")) getConfig().set("BRAGON.members", 0);
        if(!getConfig().contains("BRAGON.money")) getConfig().set("BRAGON.money", 0);
        if(!getConfig().contains("BRAGON.members")) getConfig().set("BRAGON.level", 0);

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
