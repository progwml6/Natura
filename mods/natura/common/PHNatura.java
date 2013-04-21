package mods.natura.common;

import java.io.File;
import java.io.IOException;

import mods.natura.Natura;
import net.minecraftforge.common.Configuration;

public class PHNatura
{

	public static void initProps ()
	{
		/* Here we will set up the config file for the mod 
		 * First: Create a folder inside the config folder
		 * Second: Create the actual config file
		 * Note: Configs are a pain, but absolutely necessary for every mod.
		 */

		File newFile = new File(Natura.proxy.getMinecraftDir() + "/config/Natura.txt");

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

        omniRedwood = config.getBlock("Redwood Plank Stairslabs", 3270).getInt(3270);
        omniEucalyptus = config.getBlock("Eucalyptus Plank Stairslabs", 3271).getInt(3271);
        omniHopseed = config.getBlock("Hopseed Plank Stairslabs", 3272).getInt(3272);
        omniSakura = config.getBlock("Sakura Plank Stairslabs", 3273).getInt(3273);
        omniGhostwood = config.getBlock("Ghostwood Plank Stairslabs", 3274).getInt(3274);
        omniBloodwood = config.getBlock("Bloodwood Plank Stairslabs", 3275).getInt(3275);

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

		generateRedwood = config.get("Disabler", "Generate Redwood Trees", true).getBoolean(true);
		generateSakura = config.get("Disabler", "Generate Sakura Trees", true).getBoolean(true);
		generateSmallEucalyptus = config.get("Disabler", "Generate Small Eucalyptus Trees", true).getBoolean(true);
		generateBush = config.get("Disabler", "Generate Hopseed Trees", true).getBoolean(true);
		generateBloodwood = config.get("Disabler", "Generate Bloodwood Trees", true).getBoolean(true);
		generateGhost = config.get("Disabler", "Generate Ghost Trees", true).getBoolean(true);
		generateSaguaro = config.get("Disabler", "Generate Saguaro Cactus", true).getBoolean(true);

		generateBarley = config.get("Disabler", "Generate Barley Crops", true).getBoolean(true);
		generateCotton = config.get("Disabler", "Generate Cotton Crops", true).getBoolean(true);
		
		generateBlueberries = config.get("Disabler", "Generate Blueberry Bushes", true).getBoolean(true);
		generateBlackberries = config.get("Disabler", "Generate Blackberry Bushes", true).getBoolean(true);
		generateRaspberries = config.get("Disabler", "Generate Raspberry Bushes", true).getBoolean(true);
		generateMaloberries = config.get("Disabler", "Generate Maloberry Bushes", true).getBoolean(true);

		generateBlightberries = config.get("Disabler", "Generate Blightberry Bushes", true).getBoolean(true);
		generateDuskberries = config.get("Disabler", "Generate Duskberry Bushes", true).getBoolean(true);
		generateSkyberries = config.get("Disabler", "Generate Skyberry Bushes", true).getBoolean(true);
		generateStingberries = config.get("Disabler", "Generate Stingberry Bushes", true).getBoolean(true);

		redwoodSpawnRarity = config.get("Worldgen", "Redwood Tree Spawn Rarity", 150).getInt(150);
		bloodSpawnRarity = config.get("Worldgen", "Bloodwood Tree Spawn Rarity", 14).getInt(14);
		bloodSpawnHeight = config.get("Worldgen", "Bloodwood Tree Spawn Height", 32).getInt(32);
		bloodSpawnRange = config.get("Worldgen", "Bloodwood Tree Spawn Range", 64).getInt(64);
		eucalyptusShortSpawnRarity = config.get("Worldgen", "Small Eucalyptus Tree Spawn Rarity", 25).getInt(25);
		eucalyptusShortSpawnRange = config.get("Worldgen", "Small Eucalyptus Tree Spawn Range", 32).getInt(32);
		sakuraSpawnRarity = config.get("Worldgen", "Sakura Tree Spawn Rarity", 10).getInt(10);
		sakuraSpawnRange = config.get("Worldgen", "Sakura Tree Spawn Range", 32).getInt(32);
		ghostSpawnRarity = config.get("Worldgen", "Ghost Tree Spawn Rarity", 25).getInt(25);
		ghostSpawnHeight = config.get("Worldgen", "Ghost Tree Spawn Height", 16).getInt(16);
		ghostSpawnRange = config.get("Worldgen", "Ghost Tree Spawn Range", 80).getInt(80);
		bushSpawnRarity = config.get("Worldgen", "Bush Tree Spawn Rarity", 10).getInt(10);
		bushSpawnRange = config.get("Worldgen", "Bush Tree Spawn Range", 20).getInt(20);
		
		saguaroSpawnRarity = config.get("Worldgen", "Saguaro Cactus Spawn Rarity", 5).getInt(5);

		cloudSpawnRarity = config.get("Worldgen", "Cloud Spawn Rarity", 10).getInt(10);
		cloudSpawnHeight = config.get("Worldgen", "Cloud Spawn Height", 192).getInt(192);
		cloudSpawnRange = config.get("Worldgen", "Cloud Spawn Range", 48).getInt(48);
		darkCloudSpawnRarity = config.get("Worldgen", "Dark Cloud Spawn Rarity", 10).getInt(10);
		darkCloudSpawnHeight = config.get("Worldgen", "Dark Cloud Spawn Height", 64).getInt(64);
		darkCloudSpawnRange = config.get("Worldgen", "Dark Cloud Spawn Range", 128).getInt(128);
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

		seaLevel = config.get("general", "Sea level", 64).getInt(64);

		/* Save the configuration file */
		config.save();
	}

	/* Prototype fields, used elsewhere */

	public static int seaLevel;

	public static boolean generateBarley;
	public static boolean generateCotton;
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
	
	public static int omniRedwood;
	public static int omniEucalyptus;
	public static int omniHopseed;
	public static int omniSakura;
	public static int omniGhostwood;
	public static int omniBloodwood;

	public static boolean generateRedwood;
	public static boolean generateSakura;
	public static boolean generateSmallEucalyptus;
	public static boolean generateBloodwood;
	public static boolean generateGhost;
	public static boolean generateBush;
	public static boolean generateSaguaro;

	public static int redwoodSpawnRarity;
	public static int redwoodSpawnRange;
	public static int bloodSpawnRarity;
	public static int bloodSpawnHeight;
	public static int bloodSpawnRange;
	public static int eucalyptusShortSpawnRarity;
	public static int eucalyptusShortSpawnRange;
	public static int sakuraSpawnRarity;
	public static int sakuraSpawnRange;
	public static int ghostSpawnRarity;
	public static int ghostSpawnHeight;
	public static int ghostSpawnRange;
	public static int bushSpawnRarity;
	public static int bushSpawnRange;
}
