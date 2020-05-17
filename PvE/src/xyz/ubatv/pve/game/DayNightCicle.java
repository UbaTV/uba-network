package xyz.ubatv.pve.game;

import xyz.ubatv.pve.Main;

public class DayNightCicle {

    private Main main = Main.getInstance();

    public void setWorldTime(int roundTime){
        long worldTime = ((roundTime * 24000) / (main.gameManager.roundDayTime + main.gameManager.roundNightTime));
        main.locationYML.game.getWorld().setTime(worldTime);
    }

}