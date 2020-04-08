package pt.ubatv.kingdoms.utils;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;

public class PriceUtils {

    //public int granite = 3;
    //public int diorite = 3;
    //public int andesite = 3;
    //public int podzol = 5;
    //public int mycelium = 5;
    //public int stoneBricks = 3;
    //public int mossyStoneBrick = 3;
    //public int crackedStoneBricks = 3;
    //public int chiseledStoneBricks =3;
    //public int mossyCbble = 3;
    //public int redSand = 5;
    //public int sandstorm = 10;
    //public int chiseldSandstorm = 10;
    //public int clay = 10;
    //public int bricks = 25;
    //public int oakPlank = 7;
    //public int sprucePlank = 7;
    //public int birchPlank = 7;
    //public int junglePlanks = 7; 
    //public int acaciaPlanks = 7;
    //public int darkOakPlanks = 7;
    //public int packedIce= 12;
    //public int netherrack = 5;
    //public int netherBrick = 18;
    //public int blockQuartz = 65;
    //public int chiselesQuartzBlock = 65;
    //public int quartzPillar = 65;
    //public int prismarineBlock = 100;

    public int getPotionBuyPrice(PotionType potionType){
        if(potionType.equals(PotionType.INVISIBILITY)) return 1000;
        else if(potionType.equals(PotionType.SPEED)) return 1000;
        return 999999999;
    }

    public int getBuyPrice(Material mat){
        /* BLOCKS */
        if(mat.equals(Material.COBBLESTONE)) return 3;
        else if(mat.equals(Material.STONE)) return 5;
        else if(mat.equals(Material.GRASS_BLOCK)) return 5;
        else if(mat.equals(Material.DIRT)) return 5;
        else if(mat.equals(Material.SAND)) return 5;
        else if(mat.equals(Material.OAK_LOG)) return 32;
        else if(mat.equals(Material.SPRUCE_LOG)) return 32;
        else if(mat.equals(Material.BIRCH_LOG)) return 32;
        else if(mat.equals(Material.JUNGLE_LOG)) return 32;
        else if(mat.equals(Material.ACACIA_LOG)) return 32;
        else if(mat.equals(Material.DARK_OAK_LOG)) return 32;
        else if(mat.equals(Material.GRAVEL)) return 6;
        else if(mat.equals(Material.ICE)) return 10;
        else if(mat.equals(Material.END_STONE)) return 8;
        else if(mat.equals(Material.SOUL_SAND)) return 32;
        else if(mat.equals(Material.OBSIDIAN)) return 500;
        else if(mat.equals(Material.MAGMA_BLOCK)) return 250;
        else if(mat.equals(Material.GLOWSTONE)) return 50;
        else if(mat.equals(Material.PRISMARINE)) return 100;
        else if(mat.equals(Material.DARK_PRISMARINE)) return 100;
        else if(mat.equals(Material.SEA_LANTERN)) return 100;
        /* MISC */
        else if(mat.equals(Material.EXPERIENCE_BOTTLE)) return 25;
        else if(mat.equals(Material.WATER_BUCKET)) return 50;
        else if(mat.equals(Material.LAVA_BUCKET)) return 500;
        else if(mat.equals(Material.IRON_HORSE_ARMOR)) return 2000;
        else if(mat.equals(Material.GOLDEN_HORSE_ARMOR)) return 5000;
        else if(mat.equals(Material.DIAMOND_HORSE_ARMOR)) return 8500;
        else if(mat.equals(Material.NAME_TAG)) return 1000;
        /* FOOD */
        else if(mat.equals(Material.BEEF)) return 35;
        else if(mat.equals(Material.PORKCHOP)) return 35;
        else if(mat.equals(Material.MUTTON)) return 30;
        else if(mat.equals(Material.CHICKEN)) return 25;
        else if(mat.equals(Material.RABBIT)) return 22;
        else if(mat.equals(Material.COOKED_BEEF)) return 50;
        else if(mat.equals(Material.COOKED_PORKCHOP)) return 50;
        else if(mat.equals(Material.COOKED_MUTTON)) return 45;
        else if(mat.equals(Material.COOKED_CHICKEN)) return 45;
        else if(mat.equals(Material.COOKED_RABBIT)) return 32;
        else if(mat.equals(Material.BREAD)) return 15;
        else if(mat.equals(Material.BEETROOT)) return 7;
        else if(mat.equals(Material.MELON_SLICE)) return 200;
        else if(mat.equals(Material.CARROT)) return 200;
        else if(mat.equals(Material.APPLE)) return 5;
        else if(mat.equals(Material.POTATO)) return 5;
        /* FARMING */
        else if(mat.equals(Material.WHEAT_SEEDS)) return 250;
        else if(mat.equals(Material.WHEAT)) return 5;
        else if(mat.equals(Material.SPRUCE_SAPLING)) return 500;
        else if(mat.equals(Material.OAK_SAPLING)) return 125;
        else if(mat.equals(Material.BIRCH_SAPLING)) return 125;
        else if(mat.equals(Material.JUNGLE_SAPLING)) return 350;
        else if(mat.equals(Material.ACACIA_SAPLING)) return 150;
        else if(mat.equals(Material.DARK_OAK_SAPLING)) return 500;
        else if(mat.equals(Material.CACTUS)) return 550;
        else if(mat.equals(Material.NETHER_WART)) return 500;
        else if(mat.equals(Material.SUGAR_CANE)) return 550;
        /* RAID */
        else if(mat.equals(Material.TNT)) return 200;
        else if(mat.equals(Material.FLINT_AND_STEEL)) return 50;
        else if(mat.equals(Material.CREEPER_SPAWN_EGG)) return 15000;
        else if(mat.equals(Material.REDSTONE_TORCH)) return 100;
        else if(mat.equals(Material.SPONGE)) return 500;
        return 999999999;
    }

    public int TNT = 200;
    public int flintAndSteel = 50;
    public int creeperEgg = 5000;
    public int invisPotion = 1000;
    public int speedPotion = 1000;
    public int redsoneTorch = 100;
    public int sponge = 500;

    public int hayBale = 43;
    public int brownMush = 45;
    public int redMush = 45;
    public int punpkinSeed = 500;
    public int carvedPump = 500;
    public int pumpkinPie = 40;
    public int sugar = 25;
    public int chorusFlower = 750;

    public int getSellPrice(Material mat){
        /* MOB DROPS */
        if(mat.equals(Material.GUNPOWDER)) return 100;
        else if(mat.equals(Material.STRING)) return 1;
        else if(mat.equals(Material.SPIDER_EYE)) return 40;
        else if(mat.equals(Material.ROTTEN_FLESH)) return 1;
        else if(mat.equals(Material.BONE)) return 75;
        else if(mat.equals(Material.ARROW)) return 15;
        else if(mat.equals(Material.SLIME_BALL)) return 40;
        else if(mat.equals(Material.MAGMA_CREAM)) return 50;
        else if(mat.equals(Material.ENDER_PEARL)) return 500;
        else if(mat.equals(Material.BLAZE_ROD)) return 45;
        else if(mat.equals(Material.GHAST_TEAR)) return 400;
        /* ORES */
        else if(mat.equals(Material.GOLD_INGOT)) return 20;
        else if(mat.equals(Material.DIAMOND)) return 100;
        else if(mat.equals(Material.EMERALD)) return 30;
        else if(mat.equals(Material.IRON_INGOT)) return 15;
        else if(mat.equals(Material.QUARTZ)) return 5;
        else if(mat.equals(Material.LAPIS_LAZULI)) return 10;
        else if(mat.equals(Material.REDSTONE)) return 10;
        else if(mat.equals(Material.COAL)) return 3;
        else if(mat.equals(Material.CHARCOAL)) return 1;
        return 0;
    }


}