package mods.natura.common;

import java.io.File;

import cpw.mods.fml.common.Loader;
import mods.natura.Natura;
import net.minecraftforge.common.config.Configuration;

public class PHNatura
{

    public static void initProps (File confFile)
    {

        /* [Forge] Configuration class, used as config method */
        Configuration config = new Configuration(confFile);
        /* Load the configuration file */
        config.load();

        Natura.retrogen = config.get("Retrogen", "Retroactive Generation", false).getBoolean(false);

        boolean BoP = false;
        if(Loader.isModLoaded("BiomesOPlenty"))
            BoP = true;

        babyHeatscarMinimum = config.get("Mob Changes", "Minimum Baby Heatscar Spiders on Spider Death", 2).getInt(2);
        if (babyHeatscarMinimum < 0)
            babyHeatscarMinimum = 0;
        babyHeatscarMaximum = config.get("Mob Changes", "Maximum Baby Heatscar Spiders on Spider Death", 4).getInt(4);
        if (babyHeatscarMaximum < 0)
            babyHeatscarMaximum = 0;
        overrideNether = config.get("Disabler", "Override Nether", !BoP).getBoolean(!BoP);
        canRespawnInNether = config.get("Disabler", "Obelisks let players respawn in the Nether", true).getBoolean(true);

        generateRedwood = config.get("Disabler", "Generate Redwood Trees", true).getBoolean(true);
        generateSakura = config.get("Disabler", "Generate Sakura Trees", true).getBoolean(true);
        generateSmallEucalyptus = config.get("Disabler", "Generate Small Eucalyptus Trees", true).getBoolean(true);
        generateBush = config.get("Disabler", "Generate Hopseed Trees", true).getBoolean(true);
        generateBloodwood = config.get("Disabler", "Generate Bloodwood Trees", true).getBoolean(true);
        generateGhost = config.get("Disabler", "Generate Ghost Trees", true).getBoolean(true);
        generateSaguaro = config.get("Disabler", "Generate Saguaro Cactus", true).getBoolean(true);

        generateOverworldClouds = config.get("Disabler", "Generate Overworld Clouds", true).getBoolean(true);
        generateSulfurClouds = config.get("Disabler", "Generate Sulfur Clouds", true).getBoolean(true);
        generateAshClouds = config.get("Disabler", "Generate Ash Clouds", true).getBoolean(true);
        generateDarkClouds = config.get("Disabler", "Generate Dark Clouds", true).getBoolean(true);

        generatePurpleheart = config.get("Disabler", "Generate Amaranth Trees", true).getBoolean(true);
        generateWillow = config.get("Disabler", "Generate Willow Trees", true).getBoolean(true);
        generateTiger = config.get("Disabler", "Generate Tigerwood Trees", true).getBoolean(true);
        generateSilverbell = config.get("Disabler", "Generate Silverbell Trees", true).getBoolean(true);
        generateMaple = config.get("Disabler", "Generate Maple Trees", true).getBoolean(true);

        generateDarkwood = config.get("Disabler", "Generate Darkwood Trees", true).getBoolean(true);
        generateFusewood = config.get("Disabler", "Generate Fusewood Trees", true).getBoolean(true);
        generateThornvines = config.get("Disabler", "Generate Thornvines", true).getBoolean(true);

        generateBarley = config.get("Disabler", "Generate Barley Crops", true).getBoolean(true);
        generateCotton = config.get("Disabler", "Generate Cotton Crops", true).getBoolean(true);
        generateBluebells = config.get("Disabler", "Generate Bluebell Flowers", true).getBoolean(true);

        generateBlueberries = config.get("Disabler", "Generate Blueberry Bushes", true).getBoolean(true);
        generateBlackberries = config.get("Disabler", "Generate Blackberry Bushes", true).getBoolean(true);
        generateRaspberries = config.get("Disabler", "Generate Raspberry Bushes", true).getBoolean(true);
        generateMaloberries = config.get("Disabler", "Generate Maloberry Bushes", true).getBoolean(true);

        generateBlightberries = config.get("Disabler", "Generate Blightberry Bushes", true).getBoolean(true);
        generateDuskberries = config.get("Disabler", "Generate Duskberry Bushes", true).getBoolean(true);
        generateSkyberries = config.get("Disabler", "Generate Skyberry Bushes", true).getBoolean(true);
        generateStingberries = config.get("Disabler", "Generate Stingberry Bushes", true).getBoolean(true);

        generateGreenglowshroom = config.get("Disabler", "Generate Green Glowshroom", true).getBoolean(true);
        generatePurpleglowshroom = config.get("Disabler", "Generate Purple Glowshroom", true).getBoolean(true);
        generateBlueglowshroom = config.get("Disabler", "Generate Blue Glowshroom", true).getBoolean(true);
        generateGlowshroomtree = config.get("Disabler", "Generate Glowshroom Trees", true).getBoolean(true);
        dropCotton = config.get("Disabler", "Drop cotton seeds from grass", true).getBoolean(true);
        dropBarley = config.get("Disabler", "Drop barley seeds from grass", true).getBoolean(true);
        try
        {
            Class.forName("chococraft.common.ModChocoCraft");
            enableWheatRecipe = config.get("Disabler", "Enable wheat to flour recipe", false).getBoolean(false);
        }
        catch (Exception e)
        {
            enableWheatRecipe = config.get("Disabler", "Enable wheat to flour recipe", true).getBoolean(true);
        }

        redwoodSpawnRarity = config.get("Worldgen", "Redwood Tree Spawn Rarity", 150).getInt(150);
        bloodSpawnRarity = config.get("Worldgen", "Blood Tree Spawn Rarity", 14).getInt(14);
        eucalyptusShortSpawnRarity = config.get("Worldgen", "Small Eucalyptus Tree Spawn Rarity", 25).getInt(25);
        eucalyptusShortSpawnRange = config.get("Worldgen", "Small Eucalyptus Tree Spawn Range", 32).getInt(32);
        sakuraSpawnRarity = config.get("Worldgen", "Sakura Tree Spawn Rarity", 10).getInt(10);
        sakuraSpawnRange = config.get("Worldgen", "Sakura Tree Spawn Range", 32).getInt(32);
        ghostSpawnRarity = config.get("Worldgen", "Ghostwood Tree Spawn Rarity", 10).getInt(10);
        bushSpawnRarity = config.get("Worldgen", "Bush Tree Spawn Rarity", 10).getInt(10);
        bushSpawnRange = config.get("Worldgen", "Bush Tree Spawn Range", 20).getInt(20);

        willowRarity = config.get("Worldgen", "Willow Tree Spawn Rarity", 10).getInt(10);
        purpleheartRarity = config.get("Worldgen", "Amaranth Tree Spawn Rarity", 1).getInt(1);
        mapleRarity = config.get("Worldgen", "Maple Tree Spawn Rarity", 34).getInt(34);
        tigerRarity = config.get("Worldgen", "Tigerwood Tree Spawn Rarity", 30).getInt(30);
        silverbellRarity = config.get("Worldgen", "Silverbell Tree Spawn Rarity", 70).getInt(70);

        darkSpawnRarity = config.get("Worldgen", "Darkwood Tree Spawn Rarity", 10).getInt(10);
        fuseSpawnRarity = config.get("Worldgen", "Fusewood Tree Spawn Rarity", 50).getInt(50);

        saguaroSpawnRarity = config.get("Worldgen", "Saguaro Cactus Spawn Rarity", 5).getInt(5);

        cloudSpawnRarity = config.get("Worldgen", "Cloud Spawn Rarity", 10).getInt(10);
        cloudSpawnHeight = config.get("Worldgen", "Cloud Spawn Height", 192).getInt(192);
        cloudSpawnRange = config.get("Worldgen", "Cloud Spawn Range", 48).getInt(48);
        darkCloudSpawnRarity = config.get("Worldgen", "Dark Cloud Spawn Density", 10).getInt(10);
        darkCloudSpawnHeight = config.get("Worldgen", "Dark Cloud Spawn MinX", 0).getInt(64);
        darkCloudSpawnRange = config.get("Worldgen", "Dark Cloud Spawn Range", 256).getInt(256);
        sulfurSpawnRarity = config.get("Worldgen", "Sulfur Cloud Spawn Rarity", 8).getInt(8);
        sulfurSpawnHeight = config.get("Worldgen", "Sulfur Cloud Spawn Height", 40).getInt(40);
        sulfurSpawnRange = config.get("Worldgen", "Sulfur Cloud Spawn Range", 78).getInt(78);
        ashSpawnRarity = config.get("Worldgen", "Ash Cloud Spawn Rarity", 8).getInt(8);
        ashSpawnHeight = config.get("Worldgen", "Ash Cloud Spawn Height", 40).getInt(40);
        ashSpawnRange = config.get("Worldgen", "Ash Cloud Spawn Range", 78).getInt(78);

        raspSpawnRarity = config.get("Worldgen", "Raspberry Spawn Rarity", 30).getInt(30);
        raspSpawnRange = config.get("Worldgen", "Raspberry Spawn Range", 64).getInt(64);
        blueSpawnRarity = config.get("Worldgen", "Blueberry Spawn Rarity", 34).getInt(34);
        blueSpawnRange = config.get("Worldgen", "Blueberry Spawn Range", 64).getInt(64);
        blackSpawnRarity = config.get("Worldgen", "Blackberry Spawn Rarity", 48).getInt(48);
        blackSpawnRange = config.get("Worldgen", "Blackberry Spawn Range", 64).getInt(64);
        geoSpawnRarity = config.get("Worldgen", "Maloberry Spawn Rarity", 40).getInt(40);
        geoSpawnRange = config.get("Worldgen", "Maloberry Spawn Range", 64).getInt(64);

        blightSpawnRarity = config.get("Worldgen", "Blightberry Spawn Rarity", 18).getInt(18);
        blightSpawnRange = config.get("Worldgen", "Blightberry Spawn Range", 100).getInt(100);
        duskSpawnRarity = config.get("Worldgen", "Duskberry Spawn Rarity", 18).getInt(18);
        duskSpawnRange = config.get("Worldgen", "Duskberry Spawn Range", 100).getInt(100);
        skySpawnRarity = config.get("Worldgen", "Skyberry Spawn Rarity", 18).getInt(18);
        skySpawnRange = config.get("Worldgen", "Skyberry Spawn Range", 100).getInt(100);
        stingSpawnRarity = config.get("Worldgen", "Stingberry Spawn Rarity", 18).getInt(18);
        stingSpawnRange = config.get("Worldgen", "Stingberry Spawn Range", 100).getInt(100);

        thornSpawnRarity = config.get("Worldgen", "Thornvines Spawn Rarity", 40).getInt(40);
        darkCloudBlacklist = config.get("Worldgen","dimension blacklist(dark clouds)", new int[]{}).getIntList();
        cloudBlacklist = config.get("Worldgen","dimension blacklist(clouds)", new int[]{}).getIntList();
        sulfurCloudBlacklist = config.get("Worldgen","dimension blacklist(sulfur clouds)", new int[]{}).getIntList();

        seaLevel = config.get("general", "Sea level", 64).getInt(64);

        /* Save the configuration file */
        if(config.hasChanged())
             config.save();
    }

    public static int[] darkCloudBlacklist;
    public static int[] cloudBlacklist;
    public static int[] sulfurCloudBlacklist;
    /* Prototype fields, used elsewhere */

    public static int seaLevel;

    //Overworld
    public static boolean generateBarley;
    public static boolean generateCotton;
    public static boolean generateBluebells;
    public static boolean generateBlueberries;
    public static boolean generateBlackberries;
    public static boolean generateRaspberries;
    public static boolean generateMaloberries;

    public static boolean generateBlightberries;
    public static boolean generateDuskberries;
    public static boolean generateSkyberries;
    public static boolean generateStingberries;

    public static boolean generateGreenglowshroom;
    public static boolean generatePurpleglowshroom;
    public static boolean generateBlueglowshroom;
    public static boolean generateGlowshroomtree;

    public static int saguaroSpawnRarity;

    public static int raspSpawnRarity;
    public static int raspSpawnRange;
    public static int blueSpawnRarity;
    public static int blueSpawnRange;
    public static int blackSpawnRarity;
    public static int blackSpawnRange;
    public static int geoSpawnRarity;
    public static int geoSpawnRange;

    public static int blightSpawnRarity;
    public static int blightSpawnRange;
    public static int duskSpawnRarity;
    public static int duskSpawnRange;
    public static int skySpawnRarity;
    public static int skySpawnRange;
    public static int stingSpawnRarity;
    public static int stingSpawnRange;

    public static int thornSpawnRarity;

    //Clouds

    public static int cloudSpawnRarity;
    public static int cloudSpawnHeight;
    public static int cloudSpawnRange;
    public static int darkCloudSpawnRarity;
    public static int darkCloudSpawnHeight;
    public static int darkCloudSpawnRange;
    public static int sulfurSpawnRarity;
    public static int sulfurSpawnHeight;
    public static int sulfurSpawnRange;
    public static int ashSpawnRarity;
    public static int ashSpawnHeight;
    public static int ashSpawnRange;

    //Trees

    public static boolean generateRedwood;
    public static boolean generateSakura;
    public static boolean generateSmallEucalyptus;
    public static boolean generateBloodwood;
    public static boolean generateGhost;
    public static boolean generateBush;
    public static boolean generateSaguaro;

    public static boolean generatePurpleheart;
    public static boolean generateWillow;
    public static boolean generateTiger;
    public static boolean generateSilverbell;
    public static boolean generateMaple;

    public static boolean generateDarkwood;
    public static boolean generateFusewood;

    public static boolean generateThornvines;

    public static boolean generateOverworldClouds;
    public static boolean generateSulfurClouds;
    public static boolean generateAshClouds;
    public static boolean generateDarkClouds;
    public static boolean enableWheatRecipe;
    public static boolean dropBarley;
    public static boolean dropCotton;

    public static boolean overrideNether;
    public static boolean canRespawnInNether;

    public static int redwoodSpawnRarity;
    public static int bloodSpawnRarity;
    public static int eucalyptusShortSpawnRarity;
    public static int eucalyptusShortSpawnRange;
    public static int sakuraSpawnRarity;
    public static int sakuraSpawnRange;
    public static int ghostSpawnRarity;
    public static int bushSpawnRarity;
    public static int bushSpawnRange;
    public static int darkSpawnRarity;
    public static int fuseSpawnRarity;
    public static int purpleheartRarity;
    public static int mapleRarity;
    public static int willowRarity;
    public static int tigerRarity;
    public static int silverbellRarity;

    public static int babyHeatscarMinimum;
    public static int babyHeatscarMaximum;
}
