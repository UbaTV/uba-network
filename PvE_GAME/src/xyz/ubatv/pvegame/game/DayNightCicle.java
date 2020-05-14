package xyz.ubatv.pvegame.game;

import xyz.ubatv.pvegame.Main;

public class DayNightCicle {

    private Main main = Main.getInstance();

    public void setRoundTime(int roundTime){
        long worldTime = ((roundTime * 24000) / (main.gameManager.roundDayTime + main.gameManager.roundNightTime));
        main.locationYML.game.getWorld().setTime(worldTime);
    }
}