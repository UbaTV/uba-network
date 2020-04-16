package pt.ubatv.kingdoms.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pt.ubatv.kingdoms.Main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CooldownYML {

    private Main main = Main.getInstance();

    public static HashMap<String,Integer> starterCooldowns = new HashMap<>();
    public static HashMap<Player,Integer> wildCooldowns = new HashMap<>();

    private File file;
    public FileConfiguration config;

    public void cooldownStarterKit(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Map.Entry<Player,Integer> entry : wildCooldowns.entrySet()){
                    Player user = entry.getKey();
                    int cooldown = entry.getValue();

                    if(cooldown == 0){
                        wildCooldowns.remove(user);
                    }else{
                        wildCooldowns.put(user, cooldown - 1);
                    }
                }
            }
        }.runTaskTimerAsynchronously(main, 0, 20);
    }

    public void cooldownWild(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Map.Entry<Player,Integer> entry : wildCooldowns.entrySet()){
                    Player user = entry.getKey();
                    int cooldown = entry.getValue();

                    if(cooldown == 0){
                        wildCooldowns.remove(user);
                    }else{
                        wildCooldowns.put(user, cooldown - 1);
                    }
                }
            }
        }.runTaskTimerAsynchronously(main, 0, 20);
    }

    public void loadConfig(){
        for(String user : getConfig().getConfigurationSection("").getKeys(false)) {
            // Starter kit load config
            if(config.contains(user + ".starter")){
                if(config.get(user + ".starter") != null){
                    starterCooldowns.put(user, config.getInt(user + ".starter"));
                    config.set(user + ".starter", null);
                }
            }
        }
        saveConfig();
    }

    public void saveShutdown(){
        // Save Starter kit
        for(Map.Entry<String, Integer> entry : starterCooldowns.entrySet()){
            String user = entry.getKey();
            int cooldown = entry.getValue();
            config.set(user + ".starter", cooldown);
        }
        saveConfig();
    }

    public void createConfig(){
        file = new File(main.getDataFolder(), "cooldowns.yml");
        if(!file.exists()){
            file.getParentFile().mkdirs();
            main.saveResource("cooldowns.yml", false);
        }

        config = new YamlConfiguration();
        try{
            config.load(file);
        }catch (IOException | InvalidConfigurationException e){
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to load cooldowns.yml");
        }
        saveConfig();
    }

    public void reloadConfig(){
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to reload cooldowns.yml");
        }
    }

    public void saveConfig(){
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("Failed to save cooldowns.yml");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
