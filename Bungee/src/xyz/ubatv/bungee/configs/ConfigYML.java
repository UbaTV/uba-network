package xyz.ubatv.bungee.configs;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.ubatv.bungee.Main;

import java.io.File;
import java.io.IOException;

public class ConfigYML {

    private Main main = Main.getInstance();

    private File file;
    private Configuration configuration;

    public void loadConfig(){
        file = new File(ProxyServer.getInstance().getPluginsFolder() + "config.yml");

        try{
            if(!file.exists()) file.createNewFile();

            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            configuration.set("host", "localhost");
            configuration.set("port", 3306);
            configuration.set("database", "ubanetwork-main");
            configuration.set("username", "andreubita");
            configuration.set("password", "uba");
            saveConfig();
        }catch (IOException e){
            main.getLogger().severe("Failed to load config.yml");
        }
    }

    public void saveConfig(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            main.getLogger().severe("Failed to save config.yml");
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
