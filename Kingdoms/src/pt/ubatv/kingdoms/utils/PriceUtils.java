package pt.ubatv.kingdoms.utils;

import org.bukkit.Material;

public class PriceUtils {



    public int cobble = 3;
    public int stone = 5;
    public int granite = 3; 
    public int grass = 5;
    public int diorite = 3;
    public int andesite = 3;
    public int dirt = 5;
    public int podzol = 5;
    public int mycelium = 5;
    public int stoneBricks = 3;
    public int mossyStoneBrick = 3;
    public int crackedStoneBricks = 3;
    public int chiseledStoneBricks = 3;
    public int mossyCbble = 3;
    public int sand = 5;
    public int redSand = 5;
    public int sandstorm = 10;
    public int chiseldSandstorm = 10;
    public int oakLog = 32;
    public int spruceLog = 32;
    public int birchLog = 32;
    public int jungleLog = 32;
    public int acaciaLog = 32;
    public int darkOakLog = 32;
    public int gravel = 6;
    public int clay = 10;
    public int bricks = 25;
    public int oakPlank = 7;
    public int sprucePlank = 7;
    public int birchPlank = 7;
    public int junglePlanks = 7; 
    public int acaciaPlanks = 7;
    public int darkOakPlanks = 7;
    public int ice = 10;
    public int packedIce= 12;
    public int endStone = 8;
    public int netherrack = 5;
    public int netherBrick = 18;
    public int soulSand = 32;
    public int obsidian = 500;
    public int magmaBlock = 250;
    public int glowstone = 50;
    public int blockQuartz = 65;
    public int chiselesQuartzBlock = 65;
    public int quartzPillar = 65;
    public int prismarine = 100;
    public int prismarineBlock = 100;
    public int darkPrismarine = 100;
    public int seaLantern = 100;
    
    public int rawBeef = 35;
    public int rawPockchop = 35;
    public int rawMutton = 30;
    public int rawChicken = 25;
    public int rawRabit = 22;
    public int wheatSeeds = 250;
    public int wheat = 5;
    public int steak = 50;
    public int cookedPork = 50;
    public int cookedMutton = 50;
    public int cookedChick = 45;
    public int cookedRabit = 32;
    public int bread = 40;
    public int hayBale = 43;
    public int oakSap = 125;
    public int spruceSap = 500;
    public int birchSap = 125;
    public int jungleSap = 350;
    public int acaciaSap = 150;
    public int darkOakSap = 500;
    public int apple = 55;
    public int brownMush = 45;
    public int redMush = 45;
    public int melonSeed = 500;
    public int melonSlice = 500;
    public int beetrootSeed = 500;
    public int beetroot = 500;
    public int punpkinSeed = 500;
    public int carvedPump = 500;
    public int pumpkinPie = 40;
    public int carrot = 500;
    public int potato = 500;
    public int cactus = 550;
    public int sugarCane = 550;
    public int sugar = 25;
    public int chorusFlower = 750;
    public int netherWart = 600;
    
    public int getPrice(Material mat){
        /* MISC */
        if(mat.equals(Material.EXPERIENCE_BOTTLE)) return 25;
        else if(mat.equals(Material.WATER_BUCKET)) return 50;
        else if(mat.equals(Material.LAVA_BUCKET)) return 500;
        else if(mat.equals(Material.IRON_HORSE_ARMOR)) return 2000;
        else if(mat.equals(Material.GOLDEN_HORSE_ARMOR)) return 5000;
        else if(mat.equals(Material.DIAMOND_HORSE_ARMOR)) return 8500;
        else if(mat.equals(Material.NAME_TAG)) return 1000;
            /* MOB DROPS */
        else if(mat.equals(Material.GUNPOWDER)) return 100;
        else if(mat.equals(Material.STRING)) return 1;
        else if(mat.equals(Material.SPIDER_EYE)) return 40;
        else if(mat.equals(Material.ROTTEN_FLESH)) return 1;
        else if(mat.equals(Material.BONE)) return 75;
        else if(mat.equals(Material.ARROW)) return 15;
        else if(mat.equals(Material.SLIME_BALL)) return 40;
        else if(mat.equals(Material.MAGMA_CREAM)) return 50;
        else if(mat.equals(Material.ENDER_PEARL)) return 500;
        else if(mat.equals(Material.BLAZE_ROD)) return 45;
        else if(mat.equals(Material.GHAST_TEAR)) return 150;
        return 999999999;
    }
    
    public int emerald = 20;
    public int emeraldBlock = emerald*9;
    public int emeraldOre = emerald*4;
    public int diamond = 100;
    public int diamondBlock = diamond*9;
    public int diamondOre = diamond*4;
    public int gold = 30;
    public int goldBlock = gold*9;
    public int goldOre = 5;
    public int iron = 15;
    public int ironBlock = iron*9;
    public int ironOre = 5;
    public int quartz = 5;
    public int quartzBlock = quartz*9;
    public int quartzOre = 3;
    public int lapis = 10;
    public int lapisBlock = lapis*9;
    public int lapisOre = 5;
    public int redstone = 10;
    public int redstoneBlock = redstone*9;
    public int redstoneOre = 5;
    public int coal = 3;
    public int coalBlock = coal*9;
    public int coalOre = 3;
    public int charcoal = 1;
    
    //SELL ONLY ORES FINISH//
}
