package mods.natura.common;

import java.io.File;
import java.io.IOException;

import mods.natura.Natura;
import net.minecraftforge.common.Configuration;

public class PHNatura
{

    public static void initProps (File location)
    {
        /* Here we will set up the config file for the mod 
         * First: Create a folder inside the config folder
         * Second: Create the actual config file
         * Note: Configs are a pain, but absolutely necessary for every mod.
         */

        File newFile = new File(location + "/Natura.txt");

        /* Some basic debugging will go a long way */
        try
        {
            newFile.createNewFile();
        }
        catch (IOException e)
        {
            System.out.println("Natura couldn't find its config! Reason:");
            System.out.println(e);
        }

        /* [Forge] Configuration class, used as config method */
        Configuration config = new Configuration(newFile);

        /* Load the configuration file */
        config.load();

        /* Define the mod's IDs. 
         * Avoid values below 4096 for items and in the 250-450 range for blocks
         */

        //infernalStone = config.getTerrainBlock("terrain", "Infernal Stone", 168, "Requires blockID < 256").getInt(168);
        taintedSoil = config.getTerrainBlock("terrain", "Tainted Soil", 250, "Requires blockID < 256").getInt(250);
        heatSand = config.getTerrainBlock("terrain", "Heat Sand", 249, "Requires blockID < 256").getInt(249);
        //sulfurair = config.getTerrainBlock("terrain", "Sulfurous Air", 168, "Requires blockID < 256").getInt(168);

        Natura.retrogen = config.get("Retrogen", "Retroactive Generation", false).getBoolean(false);

        treeID = config.getBlock("Wood Block", 3251).getInt(3251);
        redwoodDoor = config.getBlock("Redwood Door", 3252).getInt(3252);
        cloudID = config.getBlock("Cloud Block", 3253).getInt(3253);
        saguaroID = config.getBlock("Saguaro Cactus", 3254).getInt(3254);

        netherBerryBlock = config.getBlock("Nether Berry Bush", 3255).getInt(3255);
        floraSaplingID = config.getBlock("Sapling", 3256).getInt(3256);
        berryBlockID = config.getBlock("Berry_Bush", 3257).getInt(3257);
        cherryLeavesID = config.getBlock("Sakura Leaves", 3258).getInt(3258);
        floraLeavesID = config.getBlock("Flora Leaves", 3259).getInt(3259);
        floraCropsID = config.getBlock("Crops", 3260).getInt(3260);
        redwoodID = config.getBlock("Redwood Block", 3261).getInt(3261);
        planksID = config.getBlock("Planks Block", 3262).getInt(3262);
        bloodwoodID = config.getBlock("Bloodwood Block", 3263).getInt(3263);

        eucalyptusDoor = config.getBlock("Eucalyptus Door", 3264).getInt(3264);
        hopseedDoor = config.getBlock("Hopseed Door", 3265).getInt(3265);
        sakuraDoor = config.getBlock("Sakura Door", 3266).getInt(3266);
        ghostDoor = config.getBlock("Ghostwood Door", 3267).getInt(3267);
        bloodDoor = config.getBlock("Bloodwood Door", 3268).getInt(3268);
        redwoodBarkDoor = config.getBlock("Redwood Bark Door", 3269).getInt(3269);

        glowshroom = config.getBlock("Glowing Mushroom", 3270).getInt(3270);
        darkTree = config.getBlock("Darkwood Log", 3271).getInt(3271);
        darkLeaves = config.getBlock("Darkwood Leaves", 3272).getInt(3272);

        glowshroomBlue = config.getBlock("Blue Glowshroom", 3273).getInt(3273);
        glowshroomGreen = config.getBlock("Green Glowshroom", 3274).getInt(3274);
        glowshroomPurple = config.getBlock("Purple Glowshroom", 3275).getInt(3275);

        rareTree = config.getBlock("Rare Log", 3277).getInt(3277);
        rareLeaves = config.getBlock("Rare Leaves", 3278).getInt(3278);
        rareSapling = config.getBlock("Rare Sapling", 3279).getInt(3279);

        willow = config.getBlock("Willow Log", 3280).getInt(3280);
        bluebells = config.getBlock("Flower", 3281).getInt(3281);
        thornVines = config.getBlock("Thornvines", 3282).getInt(3282);

        alternateWorkbench = config.getBlock("Crafting Table", 3283).getInt(3283);
        alternateBookshelf = config.getBlock("Bookshelf", 3284).getInt(3284);
        alternateFence = config.getBlock("Fence", 3285).getInt(3285);

        grassBlock = config.getBlock("Topiary Grass Block", 3286).getInt(3286);
        grassSlab = config.getBlock("Topiary Grass Slab", 3287).getInt(3287);

        plankSlab1 = config.getBlock("Plank Slab One", 3288).getInt(3288);
        plankSlab2 = config.getBlock("Plank Slab Two", 3289).getInt(3289);
        logSlab = config.getBlock("Log Slab", 3290).getInt(3290);

        stairEucalyptus = config.getBlock("Eucalyputus Stairs", 3291).getInt(3291);
        stairSakura = config.getBlock("Sakura Stairs", 3292).getInt(3292);
        stairGhostwood = config.getBlock("Ghostwood Stairs", 3293).getInt(3293);
        stairRedwood = config.getBlock("Redwood Stairs", 3294).getInt(3294);
        stairBloodwood = config.getBlock("Bloodwood Stairs", 3295).getInt(3295);
        stairHopseed = config.getBlock("Hopseed Stairs", 3296).getInt(3296);
        stairMaple = config.getBlock("Maple Stairs", 3297).getInt(3297);
        stairAmaranth = config.getBlock("Amaranth Stairs", 3298).getInt(3298);
        stairSilverbell = config.getBlock("Silverbell Stairs", 3299).getInt(3299);
        stairTiger = config.getBlock("Tigerwood Stairs", 3300).getInt(3300);
        stairWillow = config.getBlock("Willow Stairs", 3301).getInt(3301);
        stairDarkwood = config.getBlock("Darkwood Stairs", 3302).getInt(3302);
        stairFusewood = config.getBlock("Fusewood Stairs", 3303).getInt(3303);

        pressurePlateEucalyptus = config.getBlock("Eucalyputus Pressure Plate", 3304).getInt(3304);
        pressurePlateSakura = config.getBlock("Sakura Pressure Plate", 3305).getInt(3305);
        pressurePlateGhostwood = config.getBlock("Ghostwood Pressure Plate", 3306).getInt(3306);
        pressurePlateRedwood = config.getBlock("Redwood Pressure Plate", 3307).getInt(3307);
        pressurePlateBloodwood = config.getBlock("Bloodwood Pressure Plate", 3308).getInt(3308);
        pressurePlateHopseed = config.getBlock("Hopseed Pressure Plate", 3309).getInt(3309);
        pressurePlateMaple = config.getBlock("Maple Pressure Plate", 3310).getInt(3310);
        pressurePlateAmaranth = config.getBlock("Amaranth Pressure Plate", 3311).getInt(3311);
        pressurePlateSilverbell = config.getBlock("Silverbell Pressure Plate", 3312).getInt(3312);
        pressurePlateTiger = config.getBlock("Tigerwood Pressure Plate", 3313).getInt(3313);
        pressurePlateWillow = config.getBlock("Willow Pressure Plate", 3314).getInt(3314);
        pressurePlateDarkwood = config.getBlock("Darkwood Pressure Plate", 3315).getInt(3315);
        pressurePlateFusewood = config.getBlock("Fusewood Pressure Plate", 3316).getInt(3316);

        trapdoorEucalyptus = config.getBlock("Eucalyputus Trapdoor", 3317).getInt(3317);
        trapdoorSakura = config.getBlock("Sakura Trapdoor", 3318).getInt(3318);
        trapdoorGhostwood = config.getBlock("Ghostwood Trapdoor", 3319).getInt(3319);
        trapdoorRedwood = config.getBlock("Redwood Trapdoor", 3320).getInt(3320);
        trapdoorBloodwood = config.getBlock("Bloodwood Trapdoor", 3321).getInt(3321);
        trapdoorHopseed = config.getBlock("Hopseed Trapdoor", 3322).getInt(3322);
        trapdoorMaple = config.getBlock("Maple Trapdoor", 3323).getInt(3323);
        trapdoorAmaranth = config.getBlock("Amaranth Trapdoor", 3324).getInt(3324);
        trapdoorSilverbell = config.getBlock("Silverbell Trapdoor", 3325).getInt(3325);
        trapdoorTiger = config.getBlock("Tigerwood Trapdoor", 3326).getInt(3326);
        trapdoorWillow = config.getBlock("Willow Trapdoor", 3327).getInt(3327);
        trapdoorDarkwood = config.getBlock("Darkwood Trapdoor", 3328).getInt(3328);
        trapdoorFusewood = config.getBlock("Fusewood Trapdoor", 3329).getInt(3329);

        buttonEucalyptus = config.getBlock("Eucalyputus Button", 3330).getInt(3330);
        buttonSakura = config.getBlock("Sakura Button", 3331).getInt(3331);
        buttonGhostwood = config.getBlock("Ghostwood Button", 3332).getInt(3332);
        buttonRedwood = config.getBlock("Redwood Button", 3333).getInt(3333);
        buttonBloodwood = config.getBlock("Bloodwood Button", 3334).getInt(3334);
        buttonHopseed = config.getBlock("Hopseed Button", 3335).getInt(3335);
        buttonMaple = config.getBlock("Maple Button", 3336).getInt(3336);
        buttonSilverbell = config.getBlock("Silverbell Button", 3337).getInt(3337);
        buttonAmaranth = config.getBlock("Amaranth Button", 3338).getInt(3338);
        buttonTiger = config.getBlock("Tigerwood Button", 3339).getInt(3339);
        buttonWillow = config.getBlock("Willow Button", 3340).getInt(3340);
        buttonDarkwood = config.getBlock("Darkwood Button", 3341).getInt(3341);
        buttonFusewood = config.getBlock("Fusewood Button", 3342).getInt(3342);

        fenceGateEucalyptus = config.getBlock("Eucalyputus Fence Gate", 3343).getInt(3343);
        fenceGateSakura = config.getBlock("Sakura Fence Gate", 3344).getInt(3344);
        fenceGateGhostwood = config.getBlock("Ghostwood Fence Gate", 3345).getInt(3345);
        fenceGateRedwood = config.getBlock("Redwood Fence Gate", 3346).getInt(3346);
        fenceGateBloodwood = config.getBlock("Bloodwood Fence Gate", 3347).getInt(3347);
        fenceGateHopseed = config.getBlock("Hopseed Fence Gate", 3348).getInt(3348);
        fenceGateMaple = config.getBlock("Maple Fence Gate", 3349).getInt(3349);
        fenceGateSilverbell = config.getBlock("Silverbell Fence Gate", 3350).getInt(3350);
        fenceGateAmaranth = config.getBlock("Amaranth Fence Gate", 3351).getInt(3351);
        fenceGateTiger = config.getBlock("Tigerwood Fence Gate", 3352).getInt(3352);
        fenceGateWillow = config.getBlock("Willow Fence Gate", 3353).getInt(3353);
        fenceGateDarkwood = config.getBlock("Darkwood Fence Gate", 3354).getInt(3354);
        fenceGateFusewood = config.getBlock("Fusewood Fence Gate", 3355).getInt(3355);

        netherBerryItem = config.getItem("Nether Berry Food", 12401).getInt(12401);
        berryItemID = config.getItem("Berry Food", 12402).getInt(12402);
        barleySeedID = config.getItem("Barley Seed", 12403).getInt(12403);
        foodID = config.getItem("Food Items", 12404).getInt(12404);
        boatItemID = config.getItem("Boat Item", "item", 12405).getInt(12405);
        doorItemID = config.getItem("Door Item", 12406).getInt(12406);
        cactusJuice = config.getItem("Cactus Juice", 12407).getInt(12407);

        wheatBagID = config.getItem("Wheat Seed Bag", 12411).getInt(12411);
        barleyBagID = config.getItem("Barley Seed Bag", 12412).getInt(12412);
        potatoBagID = config.getItem("Potato Bag", 12413).getInt(12413);
        carrotBagID = config.getItem("Carrot Bag", 12414).getInt(12414);
        netherWartBagID = config.getItem("Nether Wart Bag", 12415).getInt(12415);
        cottonBagID = config.getItem("Cotton Seed Bag", 12416).getInt(12416);

        berryMedley = config.getItem("Berry Medley", 12417).getInt(12417);
        seedFood = config.getItem("Saguaro Fruit", 12418).getInt(12418);
        boneBagID = config.getItem("Bonemeal Bag", 12419).getInt(12419);
        netherFood = config.getItem("Nether foodstuffs", 12420).getInt(12420);
        stickItem = config.getItem("Stick", 12421).getInt(12421);

        ghostwoodSword = config.getItem("Ghostwood Sword", 12422).getInt(12422);
        ghostwoodPickaxe = config.getItem("Ghostwood Pickaxe", 12423).getInt(12423);
        ghostwoodShovel = config.getItem("Ghostwood Shovel", 12424).getInt(12424);
        ghostwoodAxe = config.getItem("Ghostwood Hatchet", 12425).getInt(12425);

        bloodwoodSword = config.getItem("Bloodwood Sword", 12426).getInt(12426);
        bloodwoodPickaxe = config.getItem("Bloodwood Pickaxe", 12427).getInt(12427);
        bloodwoodShovel = config.getItem("Bloodwood Shovel", 12428).getInt(12428);
        bloodwoodAxe = config.getItem("Bloodwood Hatchet", 12429).getInt(12429);

        darkwoodSword = config.getItem("Darkwood Sword", 12430).getInt(12430);
        darkwoodPickaxe = config.getItem("Darkwood Pickaxe", 12431).getInt(12431);
        darkwoodShovel = config.getItem("Darkwood Shovel", 12432).getInt(12432);
        darkwoodAxe = config.getItem("Darkwood Hatchet", 12433).getInt(12433);

        fusewoodSword = config.getItem("Fusewood Sword", 12434).getInt(12434);
        fusewoodPickaxe = config.getItem("Fusewood Pickaxe", 12435).getInt(12435);
        fusewoodShovel = config.getItem("Fusewood Shovel", 12436).getInt(12436);
        fusewoodAxe = config.getItem("Fusewood Hatchet", 12437).getInt(12437);

        netherquartzSword = config.getItem("Quartz Sword", 12438).getInt(12438);
        netherquartzPickaxe = config.getItem("Quartz Pickaxe", 12439).getInt(12439);
        netherquartzShovel = config.getItem("Quartz Shovel", 12440).getInt(12440);
        netherquartzAxe = config.getItem("Quartz Axe", 12441).getInt(12441);

        bowlEmpty = config.getItem("Empty Bowl", 12442).getInt(12442);
        bowlStew = config.getItem("Stew Bowl", 12443).getInt(12443);

        ghostwoodKama = config.getItem("Ghostwood Kama", 12444).getInt(12444);
        bloodwoodKama = config.getItem("Bloodwood Kama", 12445).getInt(12445);
        darkwoodKama = config.getItem("Darkwood Kama", 12446).getInt(12446);
        fusewoodKama = config.getItem("Fusewood Kama", 12447).getInt(12447);
        netherquartzKama = config.getItem("Quartz Kama", 12448).getInt(12448);

        ghostwoodBow = config.getItem("Ghostwood Bow", 12449).getInt(12449);
        bloodwoodBow = config.getItem("Bloodwood Bow", 12450).getInt(12450);
        darkwoodBow = config.getItem("Darkwood Bow", 12451).getInt(12451);
        fusewoodBow = config.getItem("Fusewood Bow", 12452).getInt(12452);

        impHelmet = config.getItem("Imp Helmet", 12453).getInt(12453);
        impJerkin = config.getItem("Imp Jerkin", 12454).getInt(12454);
        impLeggings = config.getItem("Imp Leggings", 12455).getInt(12455);
        impBoots = config.getItem("Imp Boots", 12456).getInt(12456);

        impMeat = config.getItem("Imp Meat", 12457).getInt(12457);
        spawnEgg = config.getItem("Spawn Egg", 12458).getInt(12458);

        boolean BoP = false;
        try
        {
            Class c = Class.forName("biomesoplenty.BiomesOPlenty");
            BoP = true;
        }
        catch (Exception e)
        {
        }

        babyHeatscarMinimum = config.get("Mob Changes", "Minimum Baby Heatscar Spiders on Spider Death", 2).getInt(2);
        if (babyHeatscarMinimum < 0)
            babyHeatscarMinimum = 0;
        babyHeatscarMaximum = config.get("Mob Changes", "Maximum Baby Heatscar Spiders on Spider Death", 4).getInt(4);
        if (babyHeatscarMaximum < 0)
            babyHeatscarMaximum = 0;
        overrideNether = config.get("Disabler", "Override Nether", !BoP).getBoolean(!BoP);

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

        try
        {
            Class c = Class.forName("chococraft.common.ModChocoCraft");
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

        seaLevel = config.get("general", "Sea level", 64).getInt(64);

        /* Save the configuration file */
        config.save();
    }

    /* Prototype fields, used elsewhere */

    public static int seaLevel;
    public static int spawnEgg;

    //Nether
    public static int taintedSoil;
    public static int heatSand;
    public static int infernalStone;
    public static int glowshroom;
    public static int darkTree;
    public static int darkLeaves;
    public static int sulfurair;

    public static int glowshroomBlue;
    public static int glowshroomGreen;
    public static int glowshroomPurple;

    public static int netherFood;
    public static int stickItem;
    public static int bowlEmpty;
    public static int bowlStew;
    public static int impMeat;

    //Tools
    public static int ghostwoodSword;
    public static int ghostwoodPickaxe;
    public static int ghostwoodShovel;
    public static int ghostwoodAxe;

    public static int bloodwoodSword;
    public static int bloodwoodPickaxe;
    public static int bloodwoodShovel;
    public static int bloodwoodAxe;

    public static int darkwoodSword;
    public static int darkwoodPickaxe;
    public static int darkwoodShovel;
    public static int darkwoodAxe;

    public static int fusewoodSword;
    public static int fusewoodPickaxe;
    public static int fusewoodShovel;
    public static int fusewoodAxe;

    public static int netherquartzSword;
    public static int netherquartzPickaxe;
    public static int netherquartzShovel;
    public static int netherquartzAxe;

    public static int ghostwoodKama;
    public static int bloodwoodKama;
    public static int darkwoodKama;
    public static int fusewoodKama;
    public static int netherquartzKama;

    public static int ghostwoodBow;
    public static int bloodwoodBow;
    public static int darkwoodBow;
    public static int fusewoodBow;

    public static int impHelmet;
    public static int impJerkin;
    public static int impLeggings;
    public static int impBoots;

    //Rare trees
    public static int rareTree;
    public static int rareLeaves;
    public static int rareSapling;

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

    public static int wheatBagID;
    public static int barleyBagID;
    public static int potatoBagID;
    public static int carrotBagID;
    public static int netherWartBagID;
    public static int cottonBagID;

    public static int barleySeedID;

    public static int floraCropsID;
    public static int foodID;
    public static int seedFood;

    public static int boneBagID;

    public static int saguaroID;

    public static int saguaroSpawnRarity;
    public static int saguaroSpawnHeight;
    public static int saguaroSpawnRange;

    public static int cactusJuice;

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

    public static int netherBerryItem;
    public static int berryItemID;
    public static int berryMedley;
    public static int berryBlockID;
    public static int netherBerryBlock;

    public static int thornSpawnRarity;

    //Clouds

    public static int cloudID;

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

    public static int treeID;
    public static int redwoodID;
    public static int planksID;
    public static int bloodwoodID;
    public static int willow;

    public static int bluebells;
    public static int thornVines;

    public static int floraSaplingID;
    public static int floraLeavesID;
    public static int cherryLeavesID;

    public static int doorItemID;
    public static int redwoodDoor;
    public static int eucalyptusDoor;
    public static int hopseedDoor;
    public static int sakuraDoor;
    public static int ghostDoor;
    public static int bloodDoor;
    public static int redwoodBarkDoor;

    public static int boatItemID;

    public static int alternateWorkbench;
    public static int alternateBookshelf;
    public static int alternateFence;

    public static int grassBlock;
    public static int grassSlab;
    public static int miniDoor;

    public static int plankSlab1;
    public static int plankSlab2;

    public static int logSlab;

    public static int stairEucalyptus;
    public static int stairSakura;
    public static int stairGhostwood;
    public static int stairRedwood;
    public static int stairBloodwood;
    public static int stairHopseed;
    public static int stairMaple;
    public static int stairAmaranth;
    public static int stairSilverbell;
    public static int stairTiger;
    public static int stairWillow;
    public static int stairDarkwood;
    public static int stairFusewood;

    public static int pressurePlateEucalyptus;
    public static int pressurePlateSakura;
    public static int pressurePlateGhostwood;
    public static int pressurePlateRedwood;
    public static int pressurePlateBloodwood;
    public static int pressurePlateHopseed;
    public static int pressurePlateMaple;
    public static int pressurePlateAmaranth;
    public static int pressurePlateSilverbell;
    public static int pressurePlateTiger;
    public static int pressurePlateWillow;
    public static int pressurePlateDarkwood;
    public static int pressurePlateFusewood;

    public static int trapdoorEucalyptus;
    public static int trapdoorSakura;
    public static int trapdoorGhostwood;
    public static int trapdoorRedwood;
    public static int trapdoorBloodwood;
    public static int trapdoorHopseed;
    public static int trapdoorMaple;
    public static int trapdoorAmaranth;
    public static int trapdoorSilverbell;
    public static int trapdoorTiger;
    public static int trapdoorWillow;
    public static int trapdoorDarkwood;
    public static int trapdoorFusewood;

    public static int buttonEucalyptus;
    public static int buttonSakura;
    public static int buttonGhostwood;
    public static int buttonRedwood;
    public static int buttonBloodwood;
    public static int buttonHopseed;
    public static int buttonMaple;
    public static int buttonAmaranth;
    public static int buttonSilverbell;
    public static int buttonTiger;
    public static int buttonWillow;
    public static int buttonDarkwood;
    public static int buttonFusewood;

    public static int fenceGateEucalyptus;
    public static int fenceGateSakura;
    public static int fenceGateGhostwood;
    public static int fenceGateRedwood;
    public static int fenceGateBloodwood;
    public static int fenceGateHopseed;
    public static int fenceGateMaple;
    public static int fenceGateAmaranth;
    public static int fenceGateSilverbell;
    public static int fenceGateTiger;
    public static int fenceGateWillow;
    public static int fenceGateDarkwood;
    public static int fenceGateFusewood;

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

    public static boolean overrideNether;

    public static int redwoodSpawnRarity;
    public static int redwoodSpawnRange;
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
