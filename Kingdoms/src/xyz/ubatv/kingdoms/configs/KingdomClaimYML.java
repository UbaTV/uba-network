package xyz.ubatv.kingdoms.configs;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.ubatv.kingdoms.Main;
import xyz.ubatv.kingdoms.commands.kingdoms.KingdomUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class KingdomClaimYML {

    private Main main = Main.getInstance();

    private File file;
    public FileConfiguration config;

    public void saveKingdomClaims(String kingdomName){
        ArrayList<Chunk> kingdomChunks = main.kingdomUtils.getKingdomClaims(kingdomName);
        main.kingdomClaimYML.getConfig().set(kingdomName, null);
        main.kingdomClaimYML.getConfig().set(kingdomName + ".Claimed Chunks", kingdomChunks.size());
        int i = 0;
        for(Chunk chunk : kingdomChunks){
            main.kingdomClaimYML.getConfig().set(kingdomName + ".chunk" + i + ".world", chunk.getWorld().getName());
            main.kingdomClaimYML.getConfig().set(kingdomName + ".chunk" + i + ".x", chunk.getX());
            main.kingdomClaimYML.getConfig().set(kingdomName + ".chunk" + i + ".z", chunk.getZ());
            i++;
        }
        main.kingdomClaimYML.saveConfig();
    }

    public void loadKingdomClaims(String kingdomName){
        if(main.kingdomClaimYML.getConfig().contains(kingdomName)){
            if(main.kingdomClaimYML.getConfig().get(kingdomName) != null){
                ArrayList<Chunk> kingdomChunks = new ArrayList<>();
                int p = main.kingdomClaimYML.getConfig().getInt(kingdomName + ".Claimed Chunks");
                for(int i = 0; i < p; i++){
                    String world = main.kingdomClaimYML.getConfig().getString(kingdomName + ".chunk" + i + ".world");
                    int x = main.kingdomClaimYML.getConfig().getInt(kingdomName + ".chunk" + i + ".x");
                    int z = main.kingdomClaimYML.getConfig().getInt(kingdomName + ".chunk" + i + ".z");
                    assert world != null;
                    Chunk chunk = Objects.requireNonNull(Bukkit.getWorld(world)).getChunkAt(x,z);
                    kingdomChunks.add(chunk);
                }
                KingdomUtils.kingdomsChunks.put(kingdomName, kingdomChunks);
            }
        }
    }

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
