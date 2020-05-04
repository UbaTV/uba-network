package xyz.ubatv.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    public static Main instance;

    @Override
    public void onEnable() {
        setInstance(this);
        setInstances();

        registerCommands();
        registerEvents();
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
