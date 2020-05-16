package xyz.ubatv.minigames;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        setInstance(this);

        registerCommands();
        registerEvents();
        setInstances();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands(){

    }

    private void registerEvents(){

    }

    private void setInstances(){

    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }
}
