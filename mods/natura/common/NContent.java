package mods.natura.common;

import java.util.HashMap;
import java.util.List;

import mods.natura.blocks.*;
import mods.natura.blocks.crops.*;
import mods.natura.blocks.trees.*;
import mods.natura.items.*;
import mods.natura.items.blocks.*;
import mods.natura.worldgen.WillowGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

public class NContent
{

    public void preInit ()
    {
        //Crops
        plantItem = new PlantItem(PHNatura.foodID).setUnlocalizedName("barleyFood");
        crops = new CropBlock(PHNatura.floraCropsID);
        seeds = new NaturaSeeds(PHNatura.barleySeedID, crops.blockID, Block.tilledField.blockID).setUnlocalizedName("barley.seed");
        GameRegistry.registerBlock(crops, "N Crops");

        waterDrop = new CactusJuice(PHNatura.cactusJuice, false).setUnlocalizedName("waterdrop");

        wheatBag = new SeedBag(PHNatura.wheatBagID, Block.crops, 0, "wheat").setUnlocalizedName("wheatBag");
        barleyBag = new SeedBag(PHNatura.barleyBagID, crops, 0, "barley").setUnlocalizedName("barleyBag");
        potatoBag = new SeedBag(PHNatura.potatoBagID, Block.potato, 0, "potato").setUnlocalizedName("potatoBag");
        carrotBag = new SeedBag(PHNatura.carrotBagID, Block.carrot, 0, "carrot").setUnlocalizedName("carrotBag");
        netherWartBag = new SeedBag(PHNatura.netherWartBagID, Block.netherStalk, 0, "netherwart").setUnlocalizedName("wartBag");
        cottonBag = new SeedBag(PHNatura.cottonBagID, crops, 4, "cotton").setUnlocalizedName("cottonBag");
        boneBag = new BoneBag(PHNatura.boneBagID, "bone").setUnlocalizedName("boneBag");

        netherBerryItem = new NetherBerryItem(PHNatura.netherBerryItem, 1).setUnlocalizedName("berry.nether");
        berryItem = new BerryItem(PHNatura.berryItemID, 1).setUnlocalizedName("berry");
        berryMedley = new BerryMedley(PHNatura.berryMedley, 5).setUnlocalizedName("berryMedley");
        
        berryBush = new BerryBush(PHNatura.berryBlockID);
        GameRegistry.registerBlock(berryBush, BerryBushItem.class, "BerryBush");
        netherBerryBush = new NetherBerryBush(PHNatura.netherBerryBlock);
        GameRegistry.registerBlock(netherBerryBush, NetherBerryBushItem.class, "NetherBerryBush");

        //Clouds
        cloud = new CloudBlock(PHNatura.cloudID);
        GameRegistry.registerBlock(cloud, CloudItem.class, "Cloud");

        //Trees
        tree = new TreeBlock(PHNatura.treeID).setUnlocalizedName("natura.treeblock");
        redwood = new SimpleLog(PHNatura.redwoodID).setUnlocalizedName("natura.redwood");
        planks = new Planks(PHNatura.planksID).setUnlocalizedName("natura.planks");
        floraLeaves = (NLeaves) new NLeaves(PHNatura.floraLeavesID).setUnlocalizedName("natura.leaves");
        floraLeavesNoColor = (NLeaves) new NLeavesNocolor(PHNatura.cherryLeavesID).setUnlocalizedName("natura.leavesnocolor");
        floraSapling = (NSaplingBlock) new NSaplingBlock(PHNatura.floraSaplingID).setUnlocalizedName("natura.sapling");
        bloodwood = new LogTwoxTwo(PHNatura.bloodwoodID, 8f).setUnlocalizedName("bloodwood");
        willow = new WillowBlock(PHNatura.willow).setUnlocalizedName("willow");

        saguaro = new SaguaroBlock(PHNatura.saguaroID).setUnlocalizedName("saguaro.block");
        seedFood = new SeedFood(PHNatura.seedFood, 3, 0.3f, saguaro.blockID).setUnlocalizedName("saguaro.fruit");

        doorItem = new NDoorItem(PHNatura.doorItemID).setUnlocalizedName("redwoodDoorItem");
        redwoodDoor = new NDoor(PHNatura.redwoodDoor, Material.wood, 0, "redwood").setUnlocalizedName("door.redwood");
        eucalyptusDoor = new NDoor(PHNatura.eucalyptusDoor, Material.wood, 1, "eucalyptus").setUnlocalizedName("door.eucalyptus");
        hopseedDoor = new NDoor(PHNatura.hopseedDoor, Material.wood, 2, "hopseed").setUnlocalizedName("door.hopseed");
        sakuraDoor = new NDoor(PHNatura.sakuraDoor, Material.wood, 3, "sakura").setUnlocalizedName("door.sakura");
        ghostDoor = new NDoor(PHNatura.ghostDoor, Material.wood, 4, "ghostwood").setUnlocalizedName("door.ghostwood");
        bloodDoor = new NDoor(PHNatura.bloodDoor, Material.wood, 5, "bloodwood").setUnlocalizedName("door.bloodwood");
        redwoodBarkDoor = new NDoor(PHNatura.redwoodBarkDoor, Material.wood, 6, "redwoodbark").setUnlocalizedName("door.redwoodbark");
        
        MinecraftForge.addGrassSeed(new ItemStack(seeds, 1, 0), 3);
        MinecraftForge.addGrassSeed(new ItemStack(seeds, 1, 1), 3);

        //floraBoat = new NBoat(PHNatura.boatItemID).setIconCoord(0, 3).setUnlocalizedName("floraBoat");

        GameRegistry.registerBlock(tree, TreeItem.class, "tree");
        GameRegistry.registerBlock(redwood, RedwoodItem.class, "redwood");
        GameRegistry.registerBlock(planks, PlanksItem.class, "planks");
        GameRegistry.registerBlock(floraLeaves, NLeavesItem.class, "floraleaves");
        GameRegistry.registerBlock(floraLeavesNoColor, NoColorLeavesItem.class, "floraleavesnocolor");
        GameRegistry.registerBlock(floraSapling, NSaplingItem.class, "florasapling");
        GameRegistry.registerBlock(redwoodDoor, "Redwood Door");
        GameRegistry.registerBlock(bloodwood, LogTwoxTwoItem.class, "bloodwood");
        GameRegistry.registerBlock(saguaro, SaguaroItem.class, "Saguaro");
        GameRegistry.registerBlock(willow, WillowItem.class, "willow");
        
        //Nether
        taintedSoil = new TaintedSoil(PHNatura.taintedSoil).setUnlocalizedName("TaintedSoil");
        GameRegistry.registerBlock(taintedSoil, "soil.tainted");
        heatSand = new HeatSand(PHNatura.heatSand).setUnlocalizedName("HeatSand");
        GameRegistry.registerBlock(heatSand, "heatsand");
        glowshroom = new Glowshroom(PHNatura.glowshroom).setUnlocalizedName("Glowshroom").setLightValue(0.625f);
        GameRegistry.registerBlock(glowshroom, GlowshroomItem.class, "Glowshroom");
        darkTree = new DarkTreeBlock(PHNatura.darkTree).setUnlocalizedName("Darktree");
        GameRegistry.registerBlock(darkTree, DarkTreeItem.class, "Dark Tree");
        darkLeaves = (NLeaves) new NLeavesDark(PHNatura.darkLeaves).setUnlocalizedName("Darkleaves");
        GameRegistry.registerBlock(darkLeaves, NLeavesDarkItem.class, "Dark Leaves");

        potashApple = new NetherFoodItem(PHNatura.netherFood).setUnlocalizedName("Natura.netherfood");
        
        //Rare overworld
        rareTree = new OverworldTreeBlock(PHNatura.rareTree).setUnlocalizedName("RareTree");
        GameRegistry.registerBlock(rareTree, OverworldTreeItem.class, "Rare Tree");
        rareLeaves = (NLeaves) new OverworldLeaves(PHNatura.rareLeaves).setUnlocalizedName("RareLeaves");
        GameRegistry.registerBlock(rareLeaves, OverworldLeavesItem.class, "Rare Leaves");
        rareSapling = (OverworldSapling) new OverworldSapling(PHNatura.rareSapling).setUnlocalizedName("RareSapling");
        GameRegistry.registerBlock(rareSapling, OverworldSaplingItem.class, "Rare Sapling");
        //rareLeaves;
        bluebells = new FlowerBlock(PHNatura.bluebells).setUnlocalizedName("Bluebells");
        GameRegistry.registerBlock(bluebells, "Bluebells");

        //Material.vine.setRequiresTool();
        //BiomeGenBase.swampland.worldGeneratorSwamp = new WillowGen();
        addRecipes();
    }

    public void addRecipes ()
    {
        //Crops
        GameRegistry.addRecipe(new ItemStack(wheatBag, 1, 0), "sss", "sss", "sss", 's', Item.seeds);
        GameRegistry.addRecipe(new ItemStack(barleyBag, 1, 0), "sss", "sss", "sss", 's', seeds);
        GameRegistry.addRecipe(new ItemStack(potatoBag, 1, 0), "sss", "sss", "sss", 's', Item.potato);
        GameRegistry.addRecipe(new ItemStack(carrotBag, 1, 0), "sss", "sss", "sss", 's', Item.carrot);
        GameRegistry.addRecipe(new ItemStack(netherWartBag, 1, 0), "sss", "sss", "sss", 's', Item.netherStalkSeeds);
        GameRegistry.addRecipe(new ItemStack(cottonBag, 1, 0), "sss", "sss", "sss", 's', new ItemStack(seeds, 1, 1));
        GameRegistry.addRecipe(new ItemStack(boneBag, 1, 0), "sss", "sss", "sss", 's', new ItemStack(Item.dyePowder, 1, 15));

        GameRegistry.addRecipe(new ItemStack(Item.seeds, 9, 0), "s", 's', wheatBag);
        GameRegistry.addRecipe(new ItemStack(seeds, 9, 0), "s", 's', barleyBag);
        GameRegistry.addRecipe(new ItemStack(Item.potato, 9, 0), "s", 's', potatoBag);
        GameRegistry.addRecipe(new ItemStack(Item.carrot, 9, 0), "s", 's', carrotBag);
        GameRegistry.addRecipe(new ItemStack(Item.netherStalkSeeds, 9, 0), "s", 's', netherWartBag);
        GameRegistry.addRecipe(new ItemStack(seeds, 9, 1), "s", 's', cottonBag);
        GameRegistry.addRecipe(new ItemStack(Item.dyePowder, 9, 15), "s", 's', boneBag);

        GameRegistry.addRecipe(new ItemStack(Item.silk), "sss", 's', new ItemStack(plantItem, 1, 3));
        GameRegistry.addRecipe(new ItemStack(Block.cloth), "sss", "sss", "sss", 's', new ItemStack(plantItem, 1, 3));

        GameRegistry.addRecipe(new ItemStack(waterDrop, 1), "X", 'X', Block.cactus);
        GameRegistry.addRecipe(new ItemStack(Item.bucketWater, 1), "www", "wBw", "www", 'w', waterDrop, 'B', Item.bucketEmpty);

        GameRegistry.addRecipe(new ItemStack(Item.bread), "bbb", 'b', new ItemStack(plantItem, 1, 0));
        GameRegistry.addRecipe(new ItemStack(plantItem, 1, 1), "X", 'X', new ItemStack(plantItem, 1, 0));
        GameRegistry.addRecipe(new ItemStack(plantItem, 1, 2), "X", 'X', new ItemStack(Item.wheat));
        
        FurnaceRecipes.smelting().addSmelting(saguaro.blockID, 0, new ItemStack(Item.dyePowder, 1, 2), 0.2F);

        for (int i = 1; i <= 2; i++)
        {
            FurnaceRecipes.smelting().addSmelting(plantItem.itemID, i, new ItemStack(Item.bread, 1), 0.5f);
            GameRegistry.addRecipe(new ItemStack(Item.cake, 1), "AAA", "BEB", " C ", 'A', Item.bucketMilk, 'B', Item.sugar, 'C', new ItemStack(plantItem, 1, i), 'E', Item.egg);
        }

        //OreDictionary.registerOre("oreCobalt", new ItemStack(oreSlag, 1, 1));
        String[] berryTypes = new String[] { "cropRaspberry", "cropBlueberry", "cropBlackberry", "cropMaloberry", "cropStrawberry", "cropCranberry" };

        for (int iter1 = 0; iter1 < berryTypes.length - 2; iter1++)
            for (int iter2 = iter1 + 1; iter2 < berryTypes.length - 1; iter2++)
                for (int iter3 = iter2 + 1; iter3 < berryTypes.length; iter3++)
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(berryMedley, 1, 0), new ItemStack(Item.bowlEmpty), berryTypes[iter1], berryTypes[iter2], berryTypes[iter3]));

        for (int iter1 = 0; iter1 < berryTypes.length - 3; iter1++)
            for (int iter2 = iter1 + 1; iter2 < berryTypes.length - 2; iter2++)
                for (int iter3 = iter2 + 1; iter3 < berryTypes.length - 1; iter3++)
                    for (int iter4 = iter3 + 1; iter4 < berryTypes.length; iter4++)
                        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(berryMedley, 2, 0), new ItemStack(Item.bowlEmpty), new ItemStack(Item.bowlEmpty), berryTypes[iter1],
                                berryTypes[iter2], berryTypes[iter3], berryTypes[iter4]));

        //GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(toolStationWood, 1, 1), "p", "w", 'p', new ItemStack(blankPattern, 1, 0), 'w', "logWood"));
        /*GameRegistry.addShapelessRecipe(new ItemStack(berryMedley, 1), new ItemStack(berryItem, 1, 0), new ItemStack(berryItem, 1, 1), new ItemStack(berryItem, 1, 2), new ItemStack(Item.bowlEmpty));
        GameRegistry.addShapelessRecipe(new ItemStack(berryMedley, 1), new ItemStack(berryItem, 1, 0), new ItemStack(berryItem, 1, 1), new ItemStack(berryItem, 1, 3), new ItemStack(Item.bowlEmpty));
        GameRegistry.addShapelessRecipe(new ItemStack(berryMedley, 1), new ItemStack(berryItem, 1, 0), new ItemStack(berryItem, 1, 2), new ItemStack(berryItem, 1, 3), new ItemStack(Item.bowlEmpty));
        GameRegistry.addShapelessRecipe(new ItemStack(berryMedley, 1), new ItemStack(berryItem, 1, 1), new ItemStack(berryItem, 1, 2), new ItemStack(berryItem, 1, 3), new ItemStack(Item.bowlEmpty));
        GameRegistry.addShapelessRecipe(new ItemStack(berryMedley, 2), new ItemStack(berryItem, 1, 0), new ItemStack(berryItem, 1, 1), new ItemStack(berryItem, 1, 2), new ItemStack(berryItem, 1, 3),
        		new ItemStack(Item.bowlEmpty), new ItemStack(Item.bowlEmpty));*/
        //GameRegistry.addShapelessRecipe(new ItemStack(plantItem, 1, 2), new ItemStack(plantItem, 1, 1), Item.bucketWater );
        //GameRegistry.addShapelessRecipe(new ItemStack(plantItem, 2, 2), new ItemStack(plantItem, 1, 1), Item.egg );

        //Clouds
        GameRegistry.addRecipe(new ItemStack(Item.coal, 1, 1), "ccc", "ccc", "ccc", 'c', new ItemStack(cloud, 1, 2));
        GameRegistry.addRecipe(new ItemStack(plantItem, 1, 4), "cc", "cc", 'c', new ItemStack(cloud, 1, 3));
        GameRegistry.addRecipe(new ItemStack(Item.gunpowder, 1, 0), "cc", "cc", 'c', new ItemStack(plantItem, 1, 4));

        //Trees
        for (int i = 0; i < 3; i++)
            //Planks
            GameRegistry.addRecipe(new ItemStack(planks, 4, i), "w", 'w', new ItemStack(tree, 1, i));
        GameRegistry.addRecipe(new ItemStack(planks, 4, 3), "w", 'w', new ItemStack(redwood, 1, 1));
        GameRegistry.addRecipe(new ItemStack(planks, 4, 4), "w", 'w', new ItemStack(bloodwood, 1, Short.MAX_VALUE));
        GameRegistry.addRecipe(new ItemStack(planks, 4, 5), "w", 'w', new ItemStack(tree, 1, 3));

        ItemStack[] plankStacks = new ItemStack[] { new ItemStack(planks, 1, 0), new ItemStack(planks, 1, 1), new ItemStack(planks, 1, 2), new ItemStack(planks, 1, 3), new ItemStack(planks, 1, 5) };
        ItemStack[] logStacks = new ItemStack[] { new ItemStack(tree, 4, 0), new ItemStack(tree, 4, 1), new ItemStack(tree, 4, 2), new ItemStack(tree, 4, 3), new ItemStack(redwood, 1, 1) };

        List recipes = CraftingManager.getInstance().getRecipeList();
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 0), "##", "##", "##", '#', new ItemStack(planks, 1, 3));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 1), "##", "##", "##", '#', new ItemStack(planks, 1, 0));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 2), "##", "##", "##", '#', new ItemStack(planks, 1, 5));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 3), "##", "##", "##", '#', new ItemStack(planks, 1, 1));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 4), "##", "##", "##", '#', new ItemStack(planks, 1, 2));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 5), "##", "##", "##", '#', new ItemStack(planks, 1, 4));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 6), "##", "##", "##", '#', new ItemStack(redwood, 1, 0));

        for (int i = 0; i < plankStacks.length; i++)
        {
            OreDictionary.registerOre("plankWood", plankStacks[i]);
            OreDictionary.registerOre("logWood", logStacks[i]);
        }

        //Turn logs into charcoal
        FurnaceRecipes.smelting().addSmelting(tree.blockID, 0, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(tree.blockID, 1, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(tree.blockID, 2, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(tree.blockID, 3, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(redwood.blockID, 0, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(redwood.blockID, 1, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(redwood.blockID, 2, new ItemStack(Item.coal, 1, 1), 0.15f);
    }

    public void addShapedRecipeFirst (List recipeList, ItemStack itemstack, Object... objArray)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;

        if (objArray[var4] instanceof String[])
        {
            String[] var7 = (String[]) ((String[]) objArray[var4++]);

            for (int var8 = 0; var8 < var7.length; ++var8)
            {
                String var9 = var7[var8];
                ++var6;
                var5 = var9.length();
                var3 = var3 + var9;
            }
        }
        else
        {
            while (objArray[var4] instanceof String)
            {
                String var11 = (String) objArray[var4++];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        }

        HashMap var12;

        for (var12 = new HashMap(); var4 < objArray.length; var4 += 2)
        {
            Character var13 = (Character) objArray[var4];
            ItemStack var14 = null;

            if (objArray[var4 + 1] instanceof Item)
            {
                var14 = new ItemStack((Item) objArray[var4 + 1]);
            }
            else if (objArray[var4 + 1] instanceof Block)
            {
                var14 = new ItemStack((Block) objArray[var4 + 1], 1, Short.MAX_VALUE);
            }
            else if (objArray[var4 + 1] instanceof ItemStack)
            {
                var14 = (ItemStack) objArray[var4 + 1];
            }

            var12.put(var13, var14);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (int var16 = 0; var16 < var5 * var6; ++var16)
        {
            char var10 = var3.charAt(var16);

            if (var12.containsKey(Character.valueOf(var10)))
            {
                var15[var16] = ((ItemStack) var12.get(Character.valueOf(var10))).copy();
            }
            else
            {
                var15[var16] = null;
            }
        }

        ShapedRecipes var17 = new ShapedRecipes(var5, var6, var15, itemstack);
        recipeList.add(0, var17);
    }
    
    public void addLoot()
    {
        //ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(VanityBlocksStorage.StorageBlock,0,0),3,5,6));
    }

    public void addModSupport ()
    {
        OreDictionary.registerOre("cropRaspberry", new ItemStack(berryItem, 1, 0));
        OreDictionary.registerOre("cropBlueberry", new ItemStack(berryItem, 1, 1));
        OreDictionary.registerOre("cropBlackberry", new ItemStack(berryItem, 1, 2));
        OreDictionary.registerOre("cropMaloberry", new ItemStack(berryItem, 1, 3));

        OreDictionary.registerOre("cropBlightberry", new ItemStack(netherBerryItem, 1, 0));
        OreDictionary.registerOre("cropDuskberry", new ItemStack(netherBerryItem, 1, 1));
        OreDictionary.registerOre("cropSkyberry", new ItemStack(netherBerryItem, 1, 2));
        OreDictionary.registerOre("cropStingberry", new ItemStack(netherBerryItem, 1, 3));

        StringBuilder builder = new StringBuilder();
        String string = builder.append("farmBarley@").append(seeds.itemID).append(".0.").append(crops.blockID).append(".3").toString();
        FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", string);
        string = builder.append("farmCotton@").append(seeds.itemID).append(".1.").append(crops.blockID).append(".8").toString();
        FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", string);
    }

    //Crops
    public static Item wheatBag;
    public static Item barleyBag;
    public static Item potatoBag;
    public static Item carrotBag;
    public static Item netherWartBag;
    public static Item cottonBag;
    public static Item boneBag;

    public static Item seeds;
    public static Item plantItem;
    public static Item netherBerryItem;
    public static Item berryItem;
    public static Item berryMedley;
    public static Item seedFood;

    public static Item waterDrop;

    public static CropBlock crops;
    public static BerryBush berryBush;
    public static NetherBerryBush netherBerryBush;

    public static Block baseHerb;
    public static Block bloodyHerb;
    public static Block manaHerb;
    public static Block whiteHerb;
    public static Block poisonHerb;
    public static Block leafyHerb;
    public static Block orangeHerb;

    //Others
    public static Block cloud;
    public static Block ivy;
    public static Block flower;

    //Trees    
    public static Block tree;
    public static Block redwood;
    public static Block planks;
    public static Block bloodwood;
    public static Block willow;

    public static NLeaves floraLeaves;
    public static NLeaves floraLeavesNoColor;
    public static NSaplingBlock floraSapling;

    public static Block saguaro;

    public static Block redwoodDoor;
    public static Block eucalyptusDoor;
    public static Block hopseedDoor;
    public static Block sakuraDoor;
    public static Block ghostDoor;
    public static Block bloodDoor;
    public static Block redwoodBarkDoor;

    public static Item doorItem;
    public static Item floraBoat;
    
    //Nether
    public static Block taintedSoil;
    public static Block heatSand;
    public static Block glowshroom;
    public static Block darkTree;
    public static NLeaves darkLeaves;
    
    public static Item potashApple;
    
    //Extra overworld
    public static Block rareTree;
    public static NLeaves rareLeaves;
    public static OverworldSapling rareSapling;
    public static Block bluebells;
}
