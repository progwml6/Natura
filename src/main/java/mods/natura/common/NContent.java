package mods.natura.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mods.natura.Natura;
import mods.natura.blocks.CloudBlock;
import mods.natura.blocks.GrassBlock;
import mods.natura.blocks.GrassSlab;
import mods.natura.blocks.NButton;
import mods.natura.blocks.NFenceGate;
import mods.natura.blocks.NPressurePlate;
import mods.natura.blocks.NSlabBase;
import mods.natura.blocks.NStairs;
import mods.natura.blocks.NTrapdoor;
import mods.natura.blocks.crops.BerryBush;
import mods.natura.blocks.crops.CropBlock;
import mods.natura.blocks.crops.FlowerBlock;
import mods.natura.blocks.crops.Glowshroom;
import mods.natura.blocks.crops.LargeGlowshroom;
import mods.natura.blocks.crops.NetherBerryBush;
import mods.natura.blocks.crops.ThornVines;
import mods.natura.blocks.nether.HeatSand;
import mods.natura.blocks.nether.NetherGlass;
import mods.natura.blocks.nether.TaintedSoil;
import mods.natura.blocks.overrides.AlternateBookshelf;
import mods.natura.blocks.overrides.AlternateFence;
import mods.natura.blocks.overrides.AlternatePressurePlate;
import mods.natura.blocks.overrides.AlternateWorkbench;
import mods.natura.blocks.overrides.NetherLever;
import mods.natura.blocks.overrides.NetherrackButton;
import mods.natura.blocks.tech.BlazeHopper;
import mods.natura.blocks.tech.BlazeRail;
import mods.natura.blocks.tech.BlazeRailDetector;
import mods.natura.blocks.tech.BlazeRailPowered;
import mods.natura.blocks.tech.NetherPistonBase;
import mods.natura.blocks.tech.NetherPistonExtension;
import mods.natura.blocks.tech.NetherrackFurnaceBlock;
import mods.natura.blocks.tech.NetherrackFurnaceLogic;
import mods.natura.blocks.tech.RespawnObelisk;
import mods.natura.blocks.trees.DarkTreeBlock;
import mods.natura.blocks.trees.FloraSaplingBlock;
import mods.natura.blocks.trees.LogTwoxTwo;
import mods.natura.blocks.trees.NDoor;
import mods.natura.blocks.trees.NLeaves;
import mods.natura.blocks.trees.NLeavesDark;
import mods.natura.blocks.trees.NLeavesNocolor;
import mods.natura.blocks.trees.NetherSaplingBlock;
import mods.natura.blocks.trees.OverworldLeaves;
import mods.natura.blocks.trees.OverworldTreeBlock;
import mods.natura.blocks.trees.Planks;
import mods.natura.blocks.trees.RareSaplingBlock;
import mods.natura.blocks.trees.SaguaroBlock;
import mods.natura.blocks.trees.SimpleLog;
import mods.natura.blocks.trees.TreeBlock;
import mods.natura.blocks.trees.WillowBlock;
import mods.natura.entity.BabyHeatscarSpider;
import mods.natura.entity.FusewoodArrow;
import mods.natura.entity.HeatscarSpider;
import mods.natura.entity.ImpEntity;
import mods.natura.entity.NitroCreeper;
import mods.natura.items.BerryItem;
import mods.natura.items.BerryMedley;
import mods.natura.items.BoneBag;
import mods.natura.items.BowlEmpty;
import mods.natura.items.BowlStew;
import mods.natura.items.CactusJuice;
import mods.natura.items.ImpMeat;
import mods.natura.items.NaturaSeeds;
import mods.natura.items.NetherBerryItem;
import mods.natura.items.NetherFoodItem;
import mods.natura.items.PlantItem;
import mods.natura.items.SeedBag;
import mods.natura.items.SeedFood;
import mods.natura.items.SpawnEgg;
import mods.natura.items.StickItem;
import mods.natura.items.blocks.BerryBushItem;
import mods.natura.items.blocks.CloudItem;
import mods.natura.items.blocks.DarkTreeItem;
import mods.natura.items.blocks.FenceItem;
import mods.natura.items.blocks.FloraSaplingItem;
import mods.natura.items.blocks.GlowshroomItem;
import mods.natura.items.blocks.GrassBlockItem;
import mods.natura.items.blocks.GrassSlabItem;
import mods.natura.items.blocks.LogTwoxTwoItem;
import mods.natura.items.blocks.NAlternateItem;
import mods.natura.items.blocks.NDoorItem;
import mods.natura.items.blocks.NLeavesDarkItem;
import mods.natura.items.blocks.NLeavesItem;
import mods.natura.items.blocks.NetherBerryBushItem;
import mods.natura.items.blocks.NetherGlassItem;
import mods.natura.items.blocks.NetherSaplingItem;
import mods.natura.items.blocks.NoColorLeavesItem;
import mods.natura.items.blocks.OverworldLeavesItem;
import mods.natura.items.blocks.OverworldTreeItem;
import mods.natura.items.blocks.PlankSlab1Item;
import mods.natura.items.blocks.PlankSlab2Item;
import mods.natura.items.blocks.PlanksItem;
import mods.natura.items.blocks.RareSaplingItem;
import mods.natura.items.blocks.RedwoodItem;
import mods.natura.items.blocks.SaguaroItem;
import mods.natura.items.blocks.TreeItem;
import mods.natura.items.blocks.WillowItem;
import mods.natura.items.tools.FlintAndBlaze;
import mods.natura.items.tools.NaturaArmor;
import mods.natura.items.tools.NaturaBow;
import mods.natura.items.tools.NaturaHatchet;
import mods.natura.items.tools.NaturaKama;
import mods.natura.items.tools.NaturaPickaxe;
import mods.natura.items.tools.NaturaShovel;
import mods.natura.items.tools.NaturaSword;
import mods.natura.util.DispenserBehaviorSpawnEgg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockPressurePlate.Sensitivity;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tconstruct.library.crafting.PatternBuilder;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class NContent implements IFuelHandler
{

    public void preInit ()
    {
        spawnEgg = new SpawnEgg().setUnlocalizedName("natura.spawnegg");
        GameRegistry.registerItem(spawnEgg, "natura.spawnegg");
        //Crops
        plantItem = new PlantItem().setUnlocalizedName("barleyFood");
        GameRegistry.registerItem(plantItem, "barleyFood");
        crops = (CropBlock) new CropBlock().setBlockName("natura.crops");
        GameRegistry.registerBlock(crops, "crops");
        seeds = new NaturaSeeds(crops, Blocks.farmland).setUnlocalizedName("barley.seed");
        GameRegistry.registerItem(seeds, "barley.seed");
        GameRegistry.registerCustomItemStack("seedBarley", new ItemStack(seeds, 1, 0));
        GameRegistry.registerCustomItemStack("seedCotton", new ItemStack(seeds, 1, 1));

        waterDrop = new CactusJuice(false).setUnlocalizedName("waterdrop");
        GameRegistry.registerItem(waterDrop, "waterdrop");

        wheatBag = new SeedBag(Blocks.wheat, 0, "wheat").setUnlocalizedName("wheatBag");
        GameRegistry.registerItem(wheatBag, "wheatBag");
        GameRegistry.registerCustomItemStack("bagWheat", new ItemStack(wheatBag, 1, 0));
        barleyBag = new SeedBag(crops, 0, "barley").setUnlocalizedName("barleyBag");
        GameRegistry.registerItem(barleyBag, "barleyBag");
        GameRegistry.registerCustomItemStack("bagBarley", new ItemStack(barleyBag, 1, 0));
        potatoBag = new SeedBag(Blocks.potatoes, 0, "potato").setUnlocalizedName("potatoBag");
        GameRegistry.registerItem(potatoBag, "potatoBag");
        GameRegistry.registerCustomItemStack("bagPotato", new ItemStack(potatoBag, 1, 0));
        carrotBag = new SeedBag(Blocks.carrots, 0, "carrot").setUnlocalizedName("carrotBag");
        GameRegistry.registerItem(carrotBag, "carrotBag");
        GameRegistry.registerCustomItemStack("bagCarrot", new ItemStack(carrotBag, 1, 0));
        netherWartBag = new SeedBag(Blocks.nether_wart, 0, "netherwart").setUnlocalizedName("wartBag");
        GameRegistry.registerItem(netherWartBag, "wartBag");
        GameRegistry.registerCustomItemStack("bagNetherWart", new ItemStack(netherWartBag, 1, 0));
        cottonBag = new SeedBag(crops, 4, "cotton").setUnlocalizedName("cottonBag");
        GameRegistry.registerItem(cottonBag, "cottonBag");
        GameRegistry.registerCustomItemStack("bagCotton", new ItemStack(cottonBag, 1, 0));
        boneBag = new BoneBag("bone").setUnlocalizedName("boneBag");
        GameRegistry.registerItem(boneBag, "boneBag");
        GameRegistry.registerCustomItemStack("bagBone", new ItemStack(boneBag, 1, 0));

        netherBerryItem = new NetherBerryItem(1).setUnlocalizedName("berry.nether");
        GameRegistry.registerItem(netherBerryItem, "berry.nether");
        GameRegistry.registerCustomItemStack("berryBlight", new ItemStack(netherBerryItem, 1, 0));
        GameRegistry.registerCustomItemStack("berryDusk", new ItemStack(netherBerryItem, 1, 1));
        GameRegistry.registerCustomItemStack("berrySky", new ItemStack(netherBerryItem, 1, 2));
        GameRegistry.registerCustomItemStack("berrySting", new ItemStack(netherBerryItem, 1, 3));
        berryItem = new BerryItem(1).setUnlocalizedName("berry");
        GameRegistry.registerItem(berryItem, "berry");
        GameRegistry.registerCustomItemStack("berryRasp", new ItemStack(berryItem, 1, 0));
        GameRegistry.registerCustomItemStack("berryBlue", new ItemStack(berryItem, 1, 1));
        GameRegistry.registerCustomItemStack("berryBlack", new ItemStack(berryItem, 1, 2));
        GameRegistry.registerCustomItemStack("berryMalo", new ItemStack(berryItem, 1, 3));
        berryMedley = new BerryMedley(5).setUnlocalizedName("berryMedley");
        GameRegistry.registerItem(berryMedley, "berryMedley");
        GameRegistry.registerCustomItemStack("berryMedley", new ItemStack(berryMedley, 1, 0));

        berryBush = new BerryBush();
        GameRegistry.registerBlock(berryBush, BerryBushItem.class, "BerryBush");
        netherBerryBush = new NetherBerryBush();
        GameRegistry.registerBlock(netherBerryBush, NetherBerryBushItem.class, "NetherBerryBush");

        //Clouds
        cloud = new CloudBlock();
        GameRegistry.registerBlock(cloud, CloudItem.class, "Cloud");

        //Trees
        tree = new TreeBlock().setBlockName("natura.treeblock");
        redwood = new SimpleLog().setBlockName("natura.redwood");
        planks = new Planks().setBlockName("natura.planks");
        floraLeaves = (NLeaves) new NLeaves().setBlockName("natura.leaves");
        floraLeavesNoColor = (NLeaves) new NLeavesNocolor().setBlockName("natura.leavesnocolor");
        floraSapling = ((FloraSaplingBlock) new FloraSaplingBlock().setBlockName("natura.sapling"));
        willow = new WillowBlock().setBlockName("willow");

        saguaro = new SaguaroBlock().setBlockName("saguaro.block");
        seedFood = new SeedFood(3, 0.3f, saguaro).setUnlocalizedName("saguaro.fruit");
        GameRegistry.registerItem(seedFood, "saguaro.fruit");

        doorItem = new NDoorItem().setUnlocalizedName("redwoodDoorItem");
        GameRegistry.registerItem(doorItem, "redwoodDoorItem");
        redwoodDoor = new NDoor(Material.wood, 0, "redwood").setBlockName("door.redwood");
        eucalyptusDoor = new NDoor(Material.wood, 1, "eucalyptus").setBlockName("door.eucalyptus");
        hopseedDoor = new NDoor(Material.wood, 2, "hopseed").setBlockName("door.hopseed");
        sakuraDoor = new NDoor(Material.wood, 3, "sakura").setBlockName("door.sakura");
        ghostDoor = new NDoor(Material.wood, 4, "ghostwood").setBlockName("door.ghostwood");
        bloodDoor = new NDoor(Material.wood, 5, "bloodwood").setBlockName("door.bloodwood");
        redwoodBarkDoor = new NDoor(Material.wood, 6, "redwoodbark").setBlockName("door.redwoodbark");
        if(PHNatura.dropBarley)
            MinecraftForge.addGrassSeed(new ItemStack(seeds, 1, 0), 3);
        if(PHNatura.dropCotton)
            MinecraftForge.addGrassSeed(new ItemStack(seeds, 1, 1), 3);

        //floraBoat = new NBoat(PHNatura.boatItemID).setIconCoord(0, 3).setBlockName("floraBoat");

        GameRegistry.registerBlock(tree, TreeItem.class, "tree");
        GameRegistry.registerBlock(redwood, RedwoodItem.class, "redwood");
        GameRegistry.registerBlock(planks, PlanksItem.class, "planks");
        GameRegistry.registerBlock(floraLeaves, NLeavesItem.class, "floraleaves");
        GameRegistry.registerBlock(floraLeavesNoColor, NoColorLeavesItem.class, "floraleavesnocolor");
        GameRegistry.registerBlock(floraSapling, FloraSaplingItem.class, "florasapling");
        //GameRegistry.registerBlock(redwoodDoor, "Redwood Door");
        GameRegistry.registerBlock(saguaro, SaguaroItem.class, "Saguaro");
        GameRegistry.registerBlock(willow, WillowItem.class, "willow");

        GameRegistry.registerBlock(redwoodDoor, "door.redwood");
        GameRegistry.registerBlock(eucalyptusDoor, "door.eucalyptus");
        GameRegistry.registerBlock(hopseedDoor, "door.hopseed");
        GameRegistry.registerBlock(sakuraDoor, "door.sakura");
        GameRegistry.registerBlock(ghostDoor, "door.ghostwood");
        GameRegistry.registerBlock(bloodDoor, "door.bloodwood");
        GameRegistry.registerBlock(redwoodBarkDoor, "door.redwoodbark");

        //Nether
        bloodwood = new LogTwoxTwo(8f, Material.wood).setBlockName("bloodwood");
        GameRegistry.registerBlock(bloodwood, LogTwoxTwoItem.class, "bloodwood");
        taintedSoil = new TaintedSoil().setBlockName("TaintedSoil");
        GameRegistry.registerBlock(taintedSoil, "soil.tainted");
        heatSand = new HeatSand().setBlockName("HeatSand");//.setLightLevel(0.375f);
        GameRegistry.registerBlock(heatSand, "heatsand");
        /*infernalStone = new NBlock(PHNatura.infernalStone, Material.rock, 1.5f, new String[] { "infernal_stone" }).setBlockName("infernalStone");
        GameRegistry.registerBlock(infernalStone, "infernalStone");*/
        netherSapling = ((NetherSaplingBlock) new NetherSaplingBlock().setBlockName("natura.nethersapling"));
        GameRegistry.registerBlock(netherSapling, NetherSaplingItem.class, "nethersapling");

        darkTree = new DarkTreeBlock().setBlockName("Darktree");
        GameRegistry.registerBlock(darkTree, DarkTreeItem.class, "Dark Tree");
        darkLeaves = (NLeaves) new NLeavesDark().setBlockName("Darkleaves");
        GameRegistry.registerBlock(darkLeaves, NLeavesDarkItem.class, "Dark Leaves");
        thornVines = new ThornVines().setBlockName("Thornvines").setLightLevel(0.625f);
        GameRegistry.registerBlock(thornVines, "Thornvines");
        glowshroom = (Glowshroom) new Glowshroom().setBlockName("Glowshroom").setLightLevel(0.625f);
        GameRegistry.registerBlock(glowshroom, GlowshroomItem.class, "Glowshroom");
        glowshroomBlue = new LargeGlowshroom(Material.wood, "blue").setBlockName("blueGlowshroom").setLightLevel(0.625f);
        GameRegistry.registerBlock(glowshroomBlue, "blueGlowshroom");
        glowshroomPurple = new LargeGlowshroom(Material.wood, "purple").setBlockName("purpleGlowshroom").setLightLevel(0.5f);
        GameRegistry.registerBlock(glowshroomPurple, "purpleGlowshroom");
        glowshroomGreen = new LargeGlowshroom(Material.wood, "green").setBlockName("greenGlowshroom").setLightLevel(0.5f);
        GameRegistry.registerBlock(glowshroomGreen, "greenGlowshroom");

        Blocks.netherrack.setResistance(4f);

        brail = new BlazeRail().setHardness(0.7F).setBlockName("blazerail").setBlockTextureName("natura:brail_normal");
        GameRegistry.registerBlock(brail, "Blazerail");
        brailPowered = new BlazeRailPowered(false).setHardness(0.7F).setBlockName("blazerail.powered").setBlockTextureName("natura:brail_golden");
        GameRegistry.registerBlock(brailPowered, "BrailPowered");
        brailDetector = new BlazeRailDetector().setHardness(0.7F).setBlockName("blazerail.detector").setBlockTextureName("natura:brail_detector");
        GameRegistry.registerBlock(brailDetector, "BrailDetector");
        brailActivator = new BlazeRailPowered(true).setHardness(0.7F).setBlockName("blazerail.activator").setBlockTextureName("natura:brail_activator");
        GameRegistry.registerBlock(brailActivator, "BrailActivator");

        netherrackFurnace = new NetherrackFurnaceBlock().setHardness(3.5F).setCreativeTab(NaturaTab.netherTab).setBlockName("furnace.netherrack");
        GameRegistry.registerBlock(netherrackFurnace, "NetherFurnace");
        GameRegistry.registerTileEntity(NetherrackFurnaceLogic.class, "netherrackFurnace");
        respawnObelisk = new RespawnObelisk(Material.wood).setHardness(1.0F).setResistance(1000000F).setCreativeTab(NaturaTab.netherTab).setBlockName("nether.obelisk");
        GameRegistry.registerBlock(respawnObelisk, "Obelisk");
        netherGlass = (NetherGlass) new NetherGlass().setHardness(1.0F).setResistance(3000F).setStepSound(Block.soundTypeGlass).setCreativeTab(NaturaTab.netherTab).setBlockName("nether.glass");
        GameRegistry.registerBlock(netherGlass, NetherGlassItem.class, "NetherGlass");
        netherHopper = (BlazeHopper) new BlazeHopper().setHardness(3.0F).setResistance(8.0F).setCreativeTab(NaturaTab.netherTab).setBlockName("nether.hopper");
        GameRegistry.registerBlock(netherHopper, "NetherHopper");
        netherPressurePlate = new AlternatePressurePlate("netherrack", Material.rock, Sensitivity.mobs).setHardness(0.5F).setStepSound(Block.soundTypeStone).setBlockName("pressurePlate");
        GameRegistry.registerBlock(netherPressurePlate, "NetherPressurePlate");
        netherButton = new NetherrackButton().setHardness(0.5F).setStepSound(Block.soundTypeStone).setBlockName("button");
        GameRegistry.registerBlock(netherButton, "NetherButton");
        netherLever = new NetherLever().setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("lever").setBlockTextureName("natura:nlever");
        GameRegistry.registerBlock(netherLever, "NetherLever");
        /*public static Block netherDropper;
        public static Block netherDispenser;*/

        /*piston = (NetherPistonBase) new NetherPistonBase(PHNatura.piston, false).setBlockName("nether.piston");
        GameRegistry.registerBlock(piston, "natura.piston");
        pistonSticky = (NetherPistonBase) new NetherPistonBase(PHNatura.pistonSticky, true).setBlockName("nether.piston.sticky");
        GameRegistry.registerBlock(pistonSticky, "natura.piston.sticky");
        pistonExtension = new NetherPistonExtension(PHNatura.pistonExtension);
        GameRegistry.registerBlock(pistonExtension, "natura.piston.extension");*/

        potashApple = new NetherFoodItem().setUnlocalizedName("Natura.netherfood");
        GameRegistry.registerItem(potashApple, "Natura.netherfood");
        GameRegistry.registerCustomItemStack("applePotash", new ItemStack(potashApple, 1, 0));

        //Rare overworld
        rareTree = new OverworldTreeBlock().setBlockName("RareTree");
        GameRegistry.registerBlock(rareTree, OverworldTreeItem.class, "Rare Tree");
        rareLeaves = (NLeaves) new OverworldLeaves().setBlockName("RareLeaves");
        GameRegistry.registerBlock(rareLeaves, OverworldLeavesItem.class, "RareLeaves");
        rareSapling = (RareSaplingBlock) new RareSaplingBlock().setBlockName("RareSapling");
        GameRegistry.registerBlock(rareSapling, RareSaplingItem.class, "RareSapling");
        bluebells = (FlowerBlock) new FlowerBlock().setBlockName("bluebells");
        GameRegistry.registerBlock(bluebells, "Bluebells");

        // TODO 1.7 apparently this isn't so simple anymore
        //MinecraftForge.addGrassPlant(bluebells, 0, 18);

        //Vanilla overrides and alternates
        alternateWorkbench = new AlternateWorkbench().setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("workbench").setCreativeTab(NaturaTab.tab);
        GameRegistry.registerBlock(alternateWorkbench, NAlternateItem.class, "Natura.workbench");

        alternateBookshelf = new AlternateBookshelf().setHardness(1.5F).setStepSound(Block.soundTypeWood).setBlockName("bookshelf").setCreativeTab(NaturaTab.tab);
        GameRegistry.registerBlock(alternateBookshelf, NAlternateItem.class, "Natura.bookshelf");

        alternateFence = new AlternateFence(Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fence").setCreativeTab(NaturaTab.tab);
        GameRegistry.registerBlock(alternateFence, FenceItem.class, "Natura.fence");

        grassBlock = new GrassBlock().setBlockName("GrassBlock");
        grassBlock.stepSound = Block.soundTypeGrass;
        GameRegistry.registerBlock(grassBlock, GrassBlockItem.class, "GrassBlock");

        grassSlab = new GrassSlab().setBlockName("GrassSlab");
        grassSlab.stepSound = Block.soundTypeGrass;
        GameRegistry.registerBlock(grassSlab, GrassSlabItem.class, "GrassSlab");

        plankSlab1 = new NSlabBase(Material.wood, planks, 0, 8).setHardness(2.0f).setBlockName("plankSlab1");
        plankSlab1.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(plankSlab1, PlankSlab1Item.class, "plankSlab1");

        plankSlab2 = new NSlabBase(Material.wood, planks, 8, 5).setHardness(2.0f).setBlockName("plankSlab2");
        plankSlab2.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(plankSlab2, PlankSlab2Item.class, "plankSlab2");

        //Stairs
        stairEucalyptus = new NStairs(planks, 0).setBlockName("stair.eucalyptus");
        stairEucalyptus.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairEucalyptus, "stair.eucalyptus");

        stairSakura = new NStairs(planks, 1).setBlockName("stair.sakura");
        stairSakura.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairSakura, "stair.sakura");

        stairGhostwood = new NStairs(planks, 2).setBlockName("stair.ghostwood");
        stairGhostwood.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairGhostwood, "stair.ghostwood");

        stairRedwood = new NStairs(planks, 3).setBlockName("stair.redwood");
        stairRedwood.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairRedwood, "stair.redwood");

        stairBloodwood = new NStairs(planks, 4).setBlockName("stair.bloodwood");
        stairBloodwood.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairBloodwood, "stair.bloodwood");

        stairHopseed = new NStairs(planks, 5).setBlockName("stair.hopseed");
        stairHopseed.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairHopseed, "stair.hopseed");

        stairMaple = new NStairs(planks, 6).setBlockName("stair.maple");
        stairMaple.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairMaple, "stair.maple");

        stairSilverbell = new NStairs(planks, 7).setBlockName("stair.silverbell");
        stairSilverbell.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairSilverbell, "stair.silverbell");

        stairAmaranth = new NStairs(planks, 8).setBlockName("stair.amaranth");
        stairAmaranth.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairAmaranth, "stair.amaranth");

        stairTiger = new NStairs(planks, 9).setBlockName("stair.tiger");
        stairTiger.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairTiger, "stair.tiger");

        stairWillow = new NStairs(planks, 10).setBlockName("stair.willow");
        stairWillow.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairWillow, "stair.willow");

        stairDarkwood = new NStairs(planks, 11).setBlockName("stair.darkwood");
        stairDarkwood.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairDarkwood, "stair.darkwood");

        stairFusewood = new NStairs(planks, 12).setBlockName("stair.fusewood");
        stairFusewood.stepSound = Block.soundTypeWood;
        GameRegistry.registerBlock(stairFusewood, "stair.fusewood");

        //Eucalyptus
        pressurePlateEucalyptus = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 0);
        pressurePlateEucalyptus.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.eucalyptus");
        GameRegistry.registerBlock(pressurePlateEucalyptus, "pressureplate.eucalyptus");

        trapdoorEucalyptus = new NTrapdoor(Material.wood, "eucalyptus_trapdoor");
        trapdoorEucalyptus.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.eucalyptus");
        GameRegistry.registerBlock(trapdoorEucalyptus, "trapdoor.eucalyptus");

        buttonEucalyptus = new NButton(planks, 0);
        buttonEucalyptus.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.eucalyptus");
        GameRegistry.registerBlock(buttonEucalyptus, "button.eucalyptus");

        fenceGateEucalyptus = new NFenceGate(planks, 0);
        fenceGateEucalyptus.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.eucalyptus");
        GameRegistry.registerBlock(fenceGateEucalyptus, "fenceGate.eucalyptus");

        //Sakura
        pressurePlateSakura = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 1);
        pressurePlateSakura.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.sakura");
        GameRegistry.registerBlock(pressurePlateSakura, "pressureplate.sakura");

        trapdoorSakura = new NTrapdoor(Material.wood, "sakura_trapdoor");
        trapdoorSakura.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.sakura");
        GameRegistry.registerBlock(trapdoorSakura, "trapdoor.sakura");

        buttonSakura = new NButton(planks, 1);
        buttonSakura.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.sakura");
        GameRegistry.registerBlock(buttonSakura, "button.sakura");

        fenceGateSakura = new NFenceGate(planks, 1);
        fenceGateSakura.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.sakura");
        GameRegistry.registerBlock(fenceGateSakura, "fenceGate.sakura");

        //Ghostwood
        pressurePlateGhostwood = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 2);
        pressurePlateGhostwood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.ghostwood");
        GameRegistry.registerBlock(pressurePlateGhostwood, "pressureplate.ghostwood");

        trapdoorGhostwood = new NTrapdoor(Material.wood, "ghostwood_trapdoor");
        trapdoorGhostwood.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.ghostwood");
        GameRegistry.registerBlock(trapdoorGhostwood, "trapdoor.ghostwood");

        buttonGhostwood = new NButton(planks, 2);
        buttonGhostwood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.ghostwood");
        GameRegistry.registerBlock(buttonGhostwood, "button.ghostwood");

        fenceGateGhostwood = new NFenceGate(planks, 2);
        fenceGateGhostwood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.ghostwood");
        GameRegistry.registerBlock(fenceGateGhostwood, "fenceGate.ghostwood");

        //Redwood
        pressurePlateRedwood = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 3);
        pressurePlateRedwood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.redwood");
        GameRegistry.registerBlock(pressurePlateRedwood, "pressureplate.redwood");

        trapdoorRedwood = new NTrapdoor(Material.wood, "redwood_trapdoor");
        trapdoorRedwood.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.redwood");
        GameRegistry.registerBlock(trapdoorRedwood, "trapdoor.redwood");

        buttonRedwood = new NButton(planks, 3);
        buttonRedwood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.redwood");
        GameRegistry.registerBlock(buttonRedwood, "button.redwood");

        fenceGateRedwood = new NFenceGate(planks, 3);
        fenceGateRedwood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.redwood");
        GameRegistry.registerBlock(fenceGateRedwood, "fenceGate.redwood");

        //Bloodwood
        pressurePlateBloodwood = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 4);
        pressurePlateBloodwood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.bloodwood");
        GameRegistry.registerBlock(pressurePlateBloodwood, "pressureplate.bloodwood");

        trapdoorBloodwood = new NTrapdoor(Material.wood, "bloodwood_trapdoor");
        trapdoorBloodwood.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.bloodwood");
        GameRegistry.registerBlock(trapdoorBloodwood, "trapdoor.bloodwood");

        buttonBloodwood = new NButton(planks, 4);
        buttonBloodwood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.bloodwood");
        GameRegistry.registerBlock(buttonBloodwood, "button.bloodwood");

        fenceGateBloodwood = new NFenceGate(planks, 4);
        fenceGateBloodwood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.bloodwood");
        GameRegistry.registerBlock(fenceGateBloodwood, "fenceGate.bloodwood");

        //Hopseed
        pressurePlateHopseed = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 5);
        pressurePlateHopseed.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.hopseed");
        GameRegistry.registerBlock(pressurePlateHopseed, "pressureplate.hopseed");

        trapdoorHopseed = new NTrapdoor(Material.wood, "hopseed_trapdoor");
        trapdoorHopseed.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.hopseed");
        GameRegistry.registerBlock(trapdoorHopseed, "trapdoor.hopseed");

        buttonHopseed = new NButton(planks, 5);
        buttonHopseed.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.hopseed");
        GameRegistry.registerBlock(buttonHopseed, "button.hopseed");

        fenceGateHopseed = new NFenceGate(planks, 5);
        fenceGateHopseed.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.hopseed");
        GameRegistry.registerBlock(fenceGateHopseed, "fenceGate.hopseed");

        //Maple
        pressurePlateMaple = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 6);
        pressurePlateMaple.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.maple");
        GameRegistry.registerBlock(pressurePlateMaple, "pressureplate.maple");

        trapdoorMaple = new NTrapdoor(Material.wood, "maple_trapdoor");
        trapdoorMaple.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.maple");
        GameRegistry.registerBlock(trapdoorMaple, "trapdoor.maple");

        buttonMaple = new NButton(planks, 6);
        buttonMaple.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.maple");
        GameRegistry.registerBlock(buttonMaple, "button.maple");

        fenceGateMaple = new NFenceGate(planks, 6);
        fenceGateMaple.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.maple");
        GameRegistry.registerBlock(fenceGateMaple, "fenceGate.maple");

        //Amaranth
        pressurePlateAmaranth = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 8);
        pressurePlateAmaranth.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.amaranth");
        GameRegistry.registerBlock(pressurePlateAmaranth, "pressureplate.amaranth");

        trapdoorAmaranth = new NTrapdoor(Material.wood, "purpleheart_trapdoor");
        trapdoorAmaranth.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.amaranth");
        GameRegistry.registerBlock(trapdoorAmaranth, "trapdoor.amaranth");

        buttonAmaranth = new NButton(planks, 8);
        buttonAmaranth.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.amaranth");
        GameRegistry.registerBlock(buttonAmaranth, "button.amaranth");

        fenceGateAmaranth = new NFenceGate(planks, 8);
        fenceGateAmaranth.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.amaranth");
        GameRegistry.registerBlock(fenceGateAmaranth, "fenceGate.amaranth");

        //Silverbell
        pressurePlateSilverbell = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 7);
        pressurePlateSilverbell.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.silverbell");
        GameRegistry.registerBlock(pressurePlateSilverbell, "pressureplate.silverbell");

        trapdoorSilverbell = new NTrapdoor(Material.wood, "silverbell_trapdoor");
        trapdoorSilverbell.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.silverbell");
        GameRegistry.registerBlock(trapdoorSilverbell, "trapdoor.silverbell");

        buttonSilverbell = new NButton(planks, 7);
        buttonSilverbell.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.silverbell");
        GameRegistry.registerBlock(buttonSilverbell, "button.silverbell");

        fenceGateSilverbell = new NFenceGate(planks, 7);
        fenceGateSilverbell.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.silverbell");
        GameRegistry.registerBlock(fenceGateSilverbell, "fenceGate.silverbell");

        //Tigerwood
        pressurePlateTiger = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 9);
        pressurePlateTiger.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.tiger");
        GameRegistry.registerBlock(pressurePlateTiger, "pressureplate.tiger");

        trapdoorTiger = new NTrapdoor(Material.wood, "tiger_trapdoor");
        trapdoorTiger.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.tiger");
        GameRegistry.registerBlock(trapdoorTiger, "trapdoor.tiger");

        buttonTiger = new NButton(planks, 9);
        buttonTiger.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.tiger");
        GameRegistry.registerBlock(buttonTiger, "button.tiger");

        fenceGateTiger = new NFenceGate(planks, 9);
        fenceGateTiger.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.tiger");
        GameRegistry.registerBlock(fenceGateTiger, "fenceGate.tiger");

        //Willow
        pressurePlateWillow = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 10);
        pressurePlateWillow.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.willow");
        GameRegistry.registerBlock(pressurePlateWillow, "pressureplate.willow");

        trapdoorWillow = new NTrapdoor(Material.wood, "willow_trapdoor");
        trapdoorWillow.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.willow");
        GameRegistry.registerBlock(trapdoorWillow, "trapdoor.willow");

        buttonWillow = new NButton(planks, 10);
        buttonWillow.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.willow");
        GameRegistry.registerBlock(buttonWillow, "button.willow");

        fenceGateWillow = new NFenceGate(planks, 10);
        fenceGateWillow.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.willow");
        GameRegistry.registerBlock(fenceGateWillow, "fenceGate.willow");

        //Darkwood
        pressurePlateDarkwood = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 11);
        pressurePlateDarkwood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.darkwood");
        GameRegistry.registerBlock(pressurePlateDarkwood, "pressureplate.darkwood");

        trapdoorDarkwood = new NTrapdoor(Material.wood, "darkwood_trapdoor");
        trapdoorDarkwood.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.darkwood");
        GameRegistry.registerBlock(trapdoorDarkwood, "trapdoor.darkwood");

        buttonDarkwood = new NButton(planks, 11);
        buttonDarkwood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.darkwood");
        GameRegistry.registerBlock(buttonDarkwood, "button.darkwood");

        fenceGateDarkwood = new NFenceGate(planks, 11);
        fenceGateDarkwood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.darkwood");
        GameRegistry.registerBlock(fenceGateDarkwood, "fenceGate.darkwood");

        //Fusewood
        pressurePlateFusewood = new NPressurePlate(Material.wood, Sensitivity.everything, planks, 12);
        pressurePlateFusewood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("pressureplate.fusewood");
        GameRegistry.registerBlock(pressurePlateFusewood, "pressureplate.fusewood");

        trapdoorFusewood = new NTrapdoor(Material.wood, "fusewood_trapdoor");
        trapdoorFusewood.setHardness(3.0F).setStepSound(Block.soundTypeWood).setBlockName("trapdoor.fusewood");
        GameRegistry.registerBlock(trapdoorFusewood, "trapdoor.fusewood");

        buttonFusewood = new NButton(planks, 12);
        buttonFusewood.setHardness(0.5F).setStepSound(Block.soundTypeWood).setBlockName("button.fusewood");
        GameRegistry.registerBlock(buttonFusewood, "button.fusewood");

        fenceGateFusewood = new NFenceGate(planks, 12);
        fenceGateFusewood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setBlockName("fenceGate.fusewood");
        GameRegistry.registerBlock(fenceGateFusewood, "fenceGate.fusewood");

        //Item.itemsList[24] = null;
        //Item.stick = null;
        stickItem = (new StickItem()).setFull3D().setUnlocalizedName("natura.stick").setCreativeTab(NaturaTab.tab);
        GameRegistry.registerItem(stickItem, "natura.stick");

        ToolMaterial Bloodwood = EnumHelper.addToolMaterial("Bloodwood", 3, 350, 7f, 3, 24);
        ArmorMaterial Imp = EnumHelper.addArmorMaterial("Imp", 33, new int[] { 1, 3, 2, 1 }, 15);

        ghostwoodSword = new NaturaSword(ToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.sword.ghostwood");
        GameRegistry.registerItem(ghostwoodSword, "natura.sword.ghostwood");
        ghostwoodPickaxe = new NaturaPickaxe(ToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.pickaxe.ghostwood");
        GameRegistry.registerItem(ghostwoodPickaxe, "natura.pickaxe.ghostwood");
        ghostwoodShovel = new NaturaShovel(ToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.shovel.ghostwood");
        GameRegistry.registerItem(ghostwoodShovel, "natura.shovel.ghostwood");
        ghostwoodAxe = new NaturaHatchet(ToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.axe.ghostwood");
        GameRegistry.registerItem(ghostwoodAxe, "natura.axe.ghostwood");

        bloodwoodSword = new NaturaSword(Bloodwood, "bloodwood").setUnlocalizedName("natura.sword.bloodwood");
        GameRegistry.registerItem(bloodwoodSword, "natura.sword.bloodwood");
        bloodwoodPickaxe = new NaturaPickaxe(Bloodwood, "bloodwood").setUnlocalizedName("natura.pickaxe.bloodwood");
        GameRegistry.registerItem(bloodwoodPickaxe, "natura.pickaxe.bloodwood");
        bloodwoodShovel = new NaturaShovel(Bloodwood, "bloodwood").setUnlocalizedName("natura.shovel.bloodwood");
        GameRegistry.registerItem(bloodwoodShovel, "natura.shovel.bloodwood");
        bloodwoodAxe = new NaturaHatchet(Bloodwood, "bloodwood").setUnlocalizedName("natura.axe.bloodwood");
        GameRegistry.registerItem(bloodwoodAxe, "natura.axe.bloodwood");

        darkwoodSword = new NaturaSword(ToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.sword.darkwood");
        GameRegistry.registerItem(darkwoodSword, "natura.sword.darkwood");
        darkwoodPickaxe = new NaturaPickaxe(ToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.pickaxe.darkwood");
        GameRegistry.registerItem(darkwoodPickaxe, "natura.pickaxe.darkwood");
        darkwoodShovel = new NaturaShovel(ToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.shovel.darkwood");
        GameRegistry.registerItem(darkwoodShovel, "natura.shovel.darkwood");
        darkwoodAxe = new NaturaHatchet(ToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.axe.darkwood");
        GameRegistry.registerItem(darkwoodAxe, "natura.axe.darkwood");

        fusewoodSword = new NaturaSword(ToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.sword.fusewood");
        GameRegistry.registerItem(fusewoodSword, "natura.sword.fusewood");
        fusewoodPickaxe = new NaturaPickaxe(ToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.pickaxe.fusewood");
        GameRegistry.registerItem(fusewoodPickaxe, "natura.pickaxe.fusewood");
        fusewoodShovel = new NaturaShovel(ToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.shovel.fusewood");
        GameRegistry.registerItem(fusewoodShovel, "natura.shovel.fusewood");
        fusewoodAxe = new NaturaHatchet(ToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.axe.fusewood");
        GameRegistry.registerItem(fusewoodAxe, "natura.axe.fusewood");

        netherquartzSword = new NaturaSword(ToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.sword.netherquartz");
        GameRegistry.registerItem(netherquartzSword, "natura.sword.netherquartz");
        netherquartzPickaxe = new NaturaPickaxe(ToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.pickaxe.netherquartz");
        GameRegistry.registerItem(netherquartzPickaxe, "natura.pickaxe.netherquartz");
        netherquartzShovel = new NaturaShovel(ToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.shovel.netherquartz");
        GameRegistry.registerItem(netherquartzShovel, "natura.shovel.netherquartz");
        netherquartzAxe = new NaturaHatchet(ToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.axe.netherquartz");
        GameRegistry.registerItem(netherquartzAxe, "natura.axe.netherquartz");
        netherquartzKama = new NaturaKama(ToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.kama.netherquartz");
        GameRegistry.registerItem(netherquartzKama, "natura.kama.netherquartz");

        ghostwoodKama = new NaturaKama(ToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.kama.ghostwood");
        GameRegistry.registerItem(ghostwoodKama, "natura.kama.ghostwood");
        bloodwoodKama = new NaturaKama(Bloodwood, "bloodwood").setUnlocalizedName("natura.kama.bloodwood");
        GameRegistry.registerItem(bloodwoodKama, "natura.kama.bloodwood");
        darkwoodKama = new NaturaKama(ToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.kama.darkwood");
        GameRegistry.registerItem(darkwoodKama, "natura.kama.darkwood");
        fusewoodKama = new NaturaKama(ToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.kama.fusewood");
        GameRegistry.registerItem(fusewoodKama, "natura.kama.fusewood");

        ghostwoodBow = new NaturaBow(384, "ghostwood").setUnlocalizedName("natura.bow.ghostwood");
        GameRegistry.registerItem(ghostwoodBow, "natura.bow.ghostwood");
        bloodwoodBow = new NaturaBow(1501, "bloodwood").setUnlocalizedName("natura.bow.bloodwood");
        GameRegistry.registerItem(bloodwoodBow, "natura.bow.bloodwood");
        darkwoodBow = new NaturaBow(162, "darkwood").setUnlocalizedName("natura.bow.darkwood");
        GameRegistry.registerItem(darkwoodBow, "natura.bow.darkwood");
        fusewoodBow = new NaturaBow(28, "fusewood").setUnlocalizedName("natura.bow.fusewood");
        GameRegistry.registerItem(fusewoodBow, "natura.bow.fusewood");

        impHelmet = new NaturaArmor(Imp, 1, 0, "imp_helmet", "imp").setUnlocalizedName("natura.armor.imphelmet");
        GameRegistry.registerItem(impHelmet, "natura.armor.imphelmet");
        impJerkin = new NaturaArmor(Imp, 1, 1, "imp_body", "imp").setUnlocalizedName("natura.armor.impjerkin");
        GameRegistry.registerItem(impJerkin, "natura.armor.impjerkin");
        impLeggings = new NaturaArmor(Imp, 1, 2, "imp_leggings", "imp").setUnlocalizedName("natura.armor.impleggings");
        GameRegistry.registerItem(impLeggings, "natura.armor.impleggings");
        impBoots = new NaturaArmor(Imp, 1, 3, "imp_boots", "imp").setUnlocalizedName("natura.armor.impboots");
        GameRegistry.registerItem(impBoots, "natura.armor.impboots");

        flintAndBlaze = new FlintAndBlaze().setUnlocalizedName("flintandblaze").setTextureName("natura:flint_and_blaze");
        GameRegistry.registerItem(flintAndBlaze, "natura.flintandblaze");

        impMeat = new ImpMeat().setUnlocalizedName("impmeat");
        GameRegistry.registerItem(impMeat, "impmeat");
        GameRegistry.registerCustomItemStack("rawImphide", new ItemStack(impMeat, 1, 0));
        GameRegistry.registerCustomItemStack("cookedImphide", new ItemStack(impMeat, 1, 1));

        ghostwoodPickaxe.setHarvestLevel("pickaxe", 0);
        ghostwoodShovel.setHarvestLevel("shovel", 0);
        ghostwoodAxe.setHarvestLevel("axe", 0);

        bloodwoodPickaxe.setHarvestLevel("pickaxe", 2);
        bloodwoodShovel.setHarvestLevel("shovel", 2);
        bloodwoodAxe.setHarvestLevel("axe", 2);

        darkwoodPickaxe.setHarvestLevel("pickaxe", 1);
        darkwoodShovel.setHarvestLevel("shovel", 1);
        darkwoodAxe.setHarvestLevel("axe", 1);

        fusewoodPickaxe.setHarvestLevel("pickaxe", 2);
        fusewoodShovel.setHarvestLevel("shovel", 2);
        fusewoodAxe.setHarvestLevel("axe", 2);

        netherquartzPickaxe.setHarvestLevel("pickaxe", 1);
        netherquartzShovel.setHarvestLevel("shovel", 1);
        netherquartzAxe.setHarvestLevel("axe", 1);
        //Material.vine.setRequiresTool();
        bloodwood.setHarvestLevel("axe", 2);
        darkTree.setHarvestLevel("axe", 1, 1);
        darkTree.setHarvestLevel("axe", -1, 0);
        tree.setHarvestLevel("axe", -1);
        redwood.setHarvestLevel("axe", -1);
        taintedSoil.setHarvestLevel("shovel", 0);
        heatSand.setHarvestLevel("shovel", 0);

        bowlEmpty = new BowlEmpty().setUnlocalizedName("natura.emptybowl");
        GameRegistry.registerItem(bowlEmpty, "natura.emptybowl");
        bowlStew = new BowlStew().setUnlocalizedName("natura.stewbowl");
        GameRegistry.registerItem(bowlStew, "natura.stewbowl");
        addRecipes();
    }

    public void addRecipes ()
    {
        //Crops
        GameRegistry.addRecipe(new ItemStack(wheatBag, 1, 0), "sss", "sss", "sss", 's', Items.wheat_seeds);
        GameRegistry.addRecipe(new ItemStack(barleyBag, 1, 0), "sss", "sss", "sss", 's', seeds);
        GameRegistry.addRecipe(new ItemStack(potatoBag, 1, 0), "sss", "sss", "sss", 's', Items.potato);
        GameRegistry.addRecipe(new ItemStack(carrotBag, 1, 0), "sss", "sss", "sss", 's', Items.carrot);
        GameRegistry.addRecipe(new ItemStack(netherWartBag, 1, 0), "sss", "sss", "sss", 's', Items.nether_wart);
        GameRegistry.addRecipe(new ItemStack(cottonBag, 1, 0), "sss", "sss", "sss", 's', new ItemStack(seeds, 1, 1));
        GameRegistry.addRecipe(new ItemStack(boneBag, 1, 0), "sss", "sss", "sss", 's', new ItemStack(Items.dye, 1, 15));

        GameRegistry.addRecipe(new ItemStack(Items.wheat_seeds, 9, 0), "s", 's', wheatBag);
        GameRegistry.addRecipe(new ItemStack(seeds, 9, 0), "s", 's', barleyBag);
        GameRegistry.addRecipe(new ItemStack(Items.potato, 9, 0), "s", 's', potatoBag);
        GameRegistry.addRecipe(new ItemStack(Items.carrot, 9, 0), "s", 's', carrotBag);
        GameRegistry.addRecipe(new ItemStack(Items.nether_wart, 9, 0), "s", 's', netherWartBag);
        GameRegistry.addRecipe(new ItemStack(seeds, 9, 1), "s", 's', cottonBag);
        GameRegistry.addRecipe(new ItemStack(Items.dye, 9, 15), "s", 's', boneBag);

        GameRegistry.addRecipe(new ItemStack(Items.string), "sss", 's', new ItemStack(plantItem, 1, 3));
        GameRegistry.addRecipe(new ItemStack(Blocks.wool), "sss", "sss", "sss", 's', new ItemStack(plantItem, 1, 3));

        GameRegistry.addRecipe(new ItemStack(waterDrop, 1), "X", 'X', Blocks.cactus);
        GameRegistry.addRecipe(new ItemStack(Items.water_bucket, 1), "www", "wBw", "www", 'w', waterDrop, 'B', Items.bucket);

        GameRegistry.addRecipe(new ItemStack(Items.bread), "bbb", 'b', new ItemStack(plantItem, 1, 0));
        GameRegistry.addRecipe(new ItemStack(plantItem, 1, 1), "X", 'X', new ItemStack(plantItem, 1, 0));
        if (PHNatura.enableWheatRecipe)
            GameRegistry.addRecipe(new ItemStack(plantItem, 1, 2), "X", 'X', new ItemStack(Items.wheat));

        GameRegistry.addRecipe(new ItemStack(plantItem, 2, 8), "X", 'X', new ItemStack(bluebells));

        FurnaceRecipes.smelting().func_151394_a(new ItemStack(saguaro, 1, 0), new ItemStack(Items.dye, 1, 2), 0.2F);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(impMeat, 1, 0), new ItemStack(impMeat, 1, 1), 0.2F);

        for (int i = 1; i <= 2; i++)
        {
            FurnaceRecipes.smelting().func_151394_a(new ItemStack(plantItem, 1, i), new ItemStack(Items.bread, 1), 0.5f);
            GameRegistry.addRecipe(new ItemStack(Items.cake, 1), "AAA", "BEB", " C ", 'A', Items.milk_bucket, 'B', Items.sugar, 'C', new ItemStack(plantItem, 1, i), 'E', Items.egg);
        }

        String[] berryTypes = new String[] { "cropRaspberry", "cropBlueberry", "cropBlackberry", "cropMaloberry", "cropStrawberry", "cropCranberry" };

        for (int iter1 = 0; iter1 < berryTypes.length - 2; iter1++)
            for (int iter2 = iter1 + 1; iter2 < berryTypes.length - 1; iter2++)
                for (int iter3 = iter2 + 1; iter3 < berryTypes.length; iter3++)
                    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(berryMedley, 1, 0), new ItemStack(Items.bowl), berryTypes[iter1], berryTypes[iter2], berryTypes[iter3]));

        for (int iter1 = 0; iter1 < berryTypes.length - 3; iter1++)
            for (int iter2 = iter1 + 1; iter2 < berryTypes.length - 2; iter2++)
                for (int iter3 = iter2 + 1; iter3 < berryTypes.length - 1; iter3++)
                    for (int iter4 = iter3 + 1; iter4 < berryTypes.length; iter4++)
                        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(berryMedley, 2, 0), new ItemStack(Items.bowl), new ItemStack(Items.bowl), berryTypes[iter1], berryTypes[iter2],
                                berryTypes[iter3], berryTypes[iter4]));

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
        GameRegistry.addRecipe(new ItemStack(Items.coal, 1, 1), "ccc", "ccc", "ccc", 'c', new ItemStack(cloud, 1, 2));
        GameRegistry.addRecipe(new ItemStack(plantItem, 1, 4), "cc", "cc", 'c', new ItemStack(cloud, 1, 3));
        GameRegistry.addRecipe(new ItemStack(Items.gunpowder, 1, 0), "cc", "cc", 'c', new ItemStack(plantItem, 1, 4));

        //Trees
        for (int i = 0; i < 3; i++)
        {
            GameRegistry.addRecipe(new ItemStack(planks, 4, i), "w", 'w', new ItemStack(tree, 1, i));
        }
        for (int i = 0; i < 4; i++)
        {
            GameRegistry.addRecipe(new ItemStack(planks, 4, i + 6), "w", 'w', new ItemStack(rareTree, 1, i));
        }
        GameRegistry.addRecipe(new ItemStack(planks, 4, 3), "w", 'w', new ItemStack(redwood, 1, 1));
        GameRegistry.addRecipe(new ItemStack(planks, 4, 4), "w", 'w', new ItemStack(bloodwood, 1, Short.MAX_VALUE));
        GameRegistry.addRecipe(new ItemStack(planks, 4, 5), "w", 'w', new ItemStack(tree, 1, 3));
        GameRegistry.addRecipe(new ItemStack(planks, 4, 10), "w", 'w', new ItemStack(willow, 1, 0));
        GameRegistry.addRecipe(new ItemStack(planks, 4, 11), "w", 'w', new ItemStack(darkTree, 1, 0));
        GameRegistry.addRecipe(new ItemStack(planks, 4, 12), "w", 'w', new ItemStack(darkTree, 1, 1));

        GameRegistry.addRecipe(new ItemStack(plantItem, 1, 1), "X", 'X', new ItemStack(plantItem, 1, 0));

        /*ItemStack[] plankStacks = new ItemStack[] { new ItemStack(planks, 1, 0), new ItemStack(planks, 1, 1), new ItemStack(planks, 1, 2), new ItemStack(planks, 1, 3), new ItemStack(planks, 1, 5),
                new ItemStack(planks, 1, 6), new ItemStack(planks, 1, 7), new ItemStack(planks, 1, 8), new ItemStack(planks, 1, 9), new ItemStack(planks, 1, 10), new ItemStack(planks, 1, 11) };*/
        ItemStack[] logStacks = new ItemStack[] { new ItemStack(tree, 1, 0), new ItemStack(tree, 1, 1), new ItemStack(tree, 1, 2), new ItemStack(tree, 1, 3), new ItemStack(redwood, 1, 1),
                new ItemStack(rareTree, 1, 0), new ItemStack(rareTree, 1, 1), new ItemStack(rareTree, 1, 2), new ItemStack(rareTree, 1, 3), new ItemStack(willow, 1, 0), new ItemStack(darkTree, 1, 0),
                new ItemStack(darkTree, 1, 1) };

        GameRegistry.addRecipe(new ItemStack(plantItem, 1, 5), " s ", "#s#", "#s#", 's', new ItemStack(stickItem, 1, 2), '#', new ItemStack(floraLeavesNoColor, 1, 1));
        GameRegistry.addRecipe(new ItemStack(Items.arrow, 4, 0), " f ", "#s#", " # ", 's', new ItemStack(stickItem, 1, Short.MAX_VALUE), '#', new ItemStack(plantItem, 1, 5), 'f', Items.flint);
        GameRegistry.addRecipe(new ItemStack(Items.arrow, 4, 0), " f ", "#s#", " # ", 's', Items.stick, '#', new ItemStack(plantItem, 1, 5), 'f', Items.flint);

        List recipes = CraftingManager.getInstance().getRecipeList();
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 0), "##", "##", "##", '#', new ItemStack(planks, 1, 3));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 1), "##", "##", "##", '#', new ItemStack(planks, 1, 0));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 2), "##", "##", "##", '#', new ItemStack(planks, 1, 5));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 3), "##", "##", "##", '#', new ItemStack(planks, 1, 1));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 4), "##", "##", "##", '#', new ItemStack(planks, 1, 2));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 5), "##", "##", "##", '#', new ItemStack(planks, 1, 4));
        addShapedRecipeFirst(recipes, new ItemStack(doorItem, 1, 6), "##", "##", "##", '#', new ItemStack(redwood, 1, 0));

        for (int i = 0; i < woodTextureNames.length; i++)
        {
            addShapedRecipeFirst(recipes, new ItemStack(stickItem, 4, i), "#", "#", '#', new ItemStack(planks, 1, i));
            addShapedRecipeFirst(recipes, new ItemStack(alternateWorkbench, 1, i), "##", "##", '#', new ItemStack(planks, 1, i));
            addShapedRecipeFirst(recipes, new ItemStack(alternateBookshelf, 1, i), "###", "bbb", "###", '#', new ItemStack(planks, 1, i), 'b', Items.book);
            addShapedRecipeFirst(recipes, new ItemStack(alternateFence, 2, i), "###", "###", '#', new ItemStack(stickItem, 1, i));
            OreDictionary.registerOre("crafterWood", new ItemStack(alternateWorkbench, 1, i));
            OreDictionary.registerOre("craftingTableWood", new ItemStack(alternateWorkbench, 1, i));
            if (i != 12)
            {
                OreDictionary.registerOre("plankWood", new ItemStack(planks, 1, i));
                OreDictionary.registerOre("stickWood", new ItemStack(stickItem, 1, i));
            }
        }

        for (ItemStack logStack : logStacks) {
            OreDictionary.registerOre("logWood", logStack);
        }

        OreDictionary.registerOre("dyeBlue", new ItemStack(plantItem, 1, 8));
        OreDictionary.registerOre("glassSoul", new ItemStack(netherGlass, 1, 0));

        //Tools
        int[] toolMeta = { 2, 4, 11, 12 };
        Item[][] tools = { { ghostwoodSword, ghostwoodPickaxe, ghostwoodShovel, ghostwoodAxe, ghostwoodKama, ghostwoodBow },
                { bloodwoodSword, bloodwoodPickaxe, bloodwoodShovel, bloodwoodAxe, bloodwoodKama, bloodwoodBow },
                { darkwoodSword, darkwoodPickaxe, darkwoodShovel, darkwoodAxe, darkwoodKama, darkwoodBow }, { fusewoodSword, fusewoodPickaxe, fusewoodShovel, fusewoodAxe, fusewoodKama, fusewoodBow } };

        for (int i = 0; i < toolMeta.length; i++)
        {
            addShapedRecipeFirst(recipes, new ItemStack(tools[i][0], 1, 0), "#", "#", "s", '#', new ItemStack(planks, 1, toolMeta[i]), 's', new ItemStack(stickItem, 1, toolMeta[i]));
            addShapedRecipeFirst(recipes, new ItemStack(tools[i][1], 1, 0), "###", " s ", " s ", '#', new ItemStack(planks, 1, toolMeta[i]), 's', new ItemStack(stickItem, 1, toolMeta[i]));
            addShapedRecipeFirst(recipes, new ItemStack(tools[i][2], 1, 0), "#", "s", "s", '#', new ItemStack(planks, 1, toolMeta[i]), 's', new ItemStack(stickItem, 1, toolMeta[i]));
            addShapedRecipeFirst(recipes, new ItemStack(tools[i][3], 1, 0), "##", "#s", " s", '#', new ItemStack(planks, 1, toolMeta[i]), 's', new ItemStack(stickItem, 1, toolMeta[i]));
            addShapedRecipeFirst(recipes, new ItemStack(tools[i][4], 1, 0), "##", " s", " s", '#', new ItemStack(planks, 1, toolMeta[i]), 's', new ItemStack(stickItem, 1, toolMeta[i]));
            addShapedRecipeFirst(recipes, new ItemStack(tools[i][5], 1, 0), "#s ", "# s", "#s ", '#', new ItemStack(plantItem, 1, 7), 's', new ItemStack(stickItem, 1, toolMeta[i]));
        }

        GameRegistry.addRecipe(new ItemStack(netherquartzSword, 1, 0), "#", "#", "s", '#', new ItemStack(Blocks.quartz_block, 1, Short.MAX_VALUE), 's', new ItemStack(stickItem, 1, 2));
        GameRegistry.addRecipe(new ItemStack(netherquartzPickaxe, 1, 0), "###", " s ", " s ", '#', new ItemStack(Blocks.quartz_block, 1, Short.MAX_VALUE), 's', new ItemStack(stickItem, 1, 2));
        GameRegistry.addRecipe(new ItemStack(netherquartzShovel, 1, 0), "#", "s", "s", '#', new ItemStack(Blocks.quartz_block, 1, Short.MAX_VALUE), 's', new ItemStack(stickItem, 1, 2));
        GameRegistry.addRecipe(new ItemStack(netherquartzAxe, 1, 0), "##", "#s", " s", '#', new ItemStack(Blocks.quartz_block, 1, Short.MAX_VALUE), 's', new ItemStack(stickItem, 1, 2));

        GameRegistry.addRecipe(new ItemStack(Items.leather, 2), "##", "##", '#', new ItemStack(plantItem, 1, 6));

        impHelmetStack = new ItemStack(impHelmet);
        impHelmetStack.addEnchantment(Enchantment.protection, 1);
        impHelmetStack.addEnchantment(Enchantment.fireProtection, 1);
        GameRegistry.addRecipe(impHelmetStack.copy(), "###", "# #", '#', new ItemStack(plantItem, 1, 6));

        impJerkinStack = new ItemStack(impJerkin);
        impJerkinStack.addEnchantment(Enchantment.blastProtection, 1);
        impJerkinStack.addEnchantment(Enchantment.fireProtection, 1);
        GameRegistry.addRecipe(impJerkinStack.copy(), "# #", "###", "###", '#', new ItemStack(plantItem, 1, 6));

        impLeggingsStack = new ItemStack(impLeggings);
        impLeggingsStack.addEnchantment(Enchantment.projectileProtection, 1);
        impLeggingsStack.addEnchantment(Enchantment.fireProtection, 1);
        GameRegistry.addRecipe(impLeggingsStack.copy(), "###", "# #", "# #", '#', new ItemStack(plantItem, 1, 6));

        impBootsStack = new ItemStack(impBoots);
        impBootsStack.addEnchantment(Enchantment.featherFalling, 1);
        impBootsStack.addEnchantment(Enchantment.fireProtection, 1);
        GameRegistry.addRecipe(impBootsStack.copy(), "# #", "# #", '#', new ItemStack(plantItem, 1, 6));

        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.soul_sand, 1, 0), heatSand, taintedSoil);

        for (int i = 0; i < BowlEmpty.textureNames.length; i++)
        {
            if (!(BowlEmpty.textureNames[i].equals("")))
            {
                OreDictionary.registerOre("bowlWood", new ItemStack(bowlEmpty, 1, i));
                //TODO 1.7 test this and deal w/ recipes as well, make sure vanilla bowl is registered
                if (FluidRegistry.isFluidRegistered("mushroomsoup"))
                    FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid("mushroomsoup"), new ItemStack(bowlStew, 1, i));
                addShapedRecipeFirst(recipes, new ItemStack(bowlEmpty, 4, i), "# #", " # ", '#', new ItemStack(planks, 1, i));
                GameRegistry.addShapelessRecipe(new ItemStack(bowlStew, 1, i + 1), new ItemStack(bowlEmpty, 1, i), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.red_mushroom_block));
                GameRegistry.addShapelessRecipe(new ItemStack(bowlStew, 1, i + 15), new ItemStack(bowlEmpty, 1, i), new ItemStack(glowshroom, 1, 0), new ItemStack(glowshroom, 1, 1), new ItemStack(
                        glowshroom, 1, 2));
            }
        }

        addShapelessRecipeFirst(recipes, new ItemStack(bowlStew, 1, 0), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.red_mushroom), new ItemStack(Items.bowl));
        GameRegistry.addShapelessRecipe(new ItemStack(bowlStew, 1, 14), new ItemStack(glowshroom, 1, 0), new ItemStack(glowshroom, 1, 1), new ItemStack(glowshroom, 1, 2), new ItemStack(Items.bowl));

        /*bowlEmpty = new BowlEmpty(PHNatura.bowlEmpty).setUnlocalizedName("natura.emptybowl");
        bowlStew = new BowlStew(PHNatura.bowlStew).setUnlocalizedName("natura.stewbowl");*/

        //Turn logs into charcoal
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(tree, 1, 0), new ItemStack(Items.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(tree, 1, 1), new ItemStack(Items.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(tree, 1, 2), new ItemStack(Items.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(tree, 1, 3), new ItemStack(Items.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(redwood, 1, 0), new ItemStack(Items.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(redwood, 1, 1), new ItemStack(Items.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(redwood, 1, 2), new ItemStack(Items.coal, 1, 1), 0.15f);

        GameRegistry.addRecipe(new ItemStack(grassBlock, 1, 0), " s ", "s#s", " s ", 's', new ItemStack(Items.wheat_seeds), '#', new ItemStack(Blocks.dirt));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(grassBlock, 1, 1), new ItemStack(grassBlock, 1, 0), "dyeBlue"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(grassBlock, 1, 2), new ItemStack(grassBlock, 1, 0), "dyeRed"));

        for (int i = 0; i < 3; i++)
        {
            GameRegistry.addRecipe(new ItemStack(grassSlab, 6, i), "bbb", 'b', new ItemStack(grassBlock, 1, i));
        }

        //Extra wood things
        Block[] fenceGates = new Block[] { fenceGateEucalyptus, fenceGateSakura, fenceGateGhostwood, fenceGateRedwood, fenceGateBloodwood, fenceGateHopseed, fenceGateMaple, fenceGateSilverbell,
                fenceGateAmaranth, fenceGateTiger, fenceGateWillow, fenceGateDarkwood, fenceGateFusewood };
        Block[] buttons = new Block[] { buttonEucalyptus, buttonSakura, buttonGhostwood, buttonRedwood, buttonBloodwood, buttonHopseed, buttonMaple, buttonSilverbell, buttonAmaranth, buttonTiger,
                buttonWillow, buttonDarkwood, buttonFusewood };
        Block[] pressurePlates = new Block[] { pressurePlateEucalyptus, pressurePlateSakura, pressurePlateGhostwood, pressurePlateRedwood, pressurePlateBloodwood, pressurePlateHopseed,
                pressurePlateMaple, pressurePlateSilverbell, pressurePlateAmaranth, pressurePlateTiger, pressurePlateWillow, pressurePlateDarkwood, pressurePlateFusewood };
        Block[] stairs = new Block[] { stairEucalyptus, stairSakura, stairGhostwood, stairRedwood, stairBloodwood, stairHopseed, stairMaple, stairSilverbell, stairAmaranth, stairTiger, stairWillow,
                stairDarkwood, stairFusewood };
        Block[] trapdoors = new Block[] { trapdoorEucalyptus, trapdoorSakura, trapdoorGhostwood, trapdoorRedwood, trapdoorBloodwood, trapdoorHopseed, trapdoorMaple, trapdoorSilverbell,
                trapdoorAmaranth, trapdoorTiger, trapdoorWillow, trapdoorDarkwood, trapdoorFusewood };

        for (int i = 0; i < 13; i++)
        {
            addShapedRecipeFirst(recipes, new ItemStack(fenceGates[i], 1, i), "s#s", "s#s", '#', new ItemStack(planks, 1, i), 's', new ItemStack(stickItem, 1, i));
            addShapedRecipeFirst(recipes, new ItemStack(buttons[i], 1, i), "#", '#', new ItemStack(planks, 1, i));
            addShapedRecipeFirst(recipes, new ItemStack(pressurePlates[i], 1, i), "##", '#', new ItemStack(planks, 1, i));
            addShapedRecipeFirst(recipes, new ItemStack(stairs[i], 4, i), "#  ", "## ", "###", '#', new ItemStack(planks, 1, i));
            addShapedRecipeFirst(recipes, new ItemStack(trapdoors[i], 2, i), "###", "###", '#', new ItemStack(planks, 1, i));
        }
        for (int i = 0; i < 8; i++)
            addShapedRecipeFirst(recipes, new ItemStack(plankSlab1, 6, i), "###", '#', new ItemStack(planks, 1, i));
        for (int i = 0; i < 5; i++)
            addShapedRecipeFirst(recipes, new ItemStack(plankSlab2, 6, i), "###", '#', new ItemStack(planks, 1, 8 + i));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NContent.brail, 16), "X X", "X#X", "X X", 'X', Items.blaze_rod, '#', "stickWood"));
        GameRegistry.addRecipe(new ItemStack(NContent.brailPowered, 6), "X X", "X#X", "XRX", 'X', Items.blaze_rod, 'R', Items.redstone, '#', new ItemStack(darkTree, 1, 1));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NContent.brailActivator, 6), "XSX", "X#X", "XSX", 'X', Items.blaze_rod, '#', Blocks.redstone_torch, 'S', "stickWood"));
        GameRegistry.addRecipe(new ItemStack(NContent.brailDetector, 6), "X X", "X#X", "XRX", 'X', Items.blaze_rod, 'R', Items.redstone, '#', netherPressurePlate);

        GameRegistry.addRecipe(new ItemStack(NContent.netherrackFurnace), "###", "# #", "###", '#', Blocks.netherrack);
        GameRegistry.addRecipe(new ItemStack(NContent.respawnObelisk), "###", "# #", "###", '#', new ItemStack(tree, 1, 2));
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(Blocks.soul_sand, 1, 0), new ItemStack(netherGlass, 1, 0), 0.3f);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(heatSand, 1, 0), new ItemStack(netherGlass, 1, 1), 0.3f);
        OreDictionary.registerOre("chestWood", new ItemStack(Blocks.chest));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NContent.netherHopper), "# #", "#C#", " # ", '#', new ItemStack(Items.blaze_rod), 'C', "chestWood"));
        GameRegistry.addRecipe(new ItemStack(NContent.netherPressurePlate), "##", '#', new ItemStack(Blocks.netherrack));
        GameRegistry.addRecipe(new ItemStack(NContent.netherButton), "#", '#', new ItemStack(Blocks.netherrack));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NContent.netherLever), "S", "#", '#', new ItemStack(Blocks.netherrack), 'S', "stickWood"));

        OreDictionary.registerOre("glass", new ItemStack(Blocks.glass));
        OreDictionary.registerOre("glass", new ItemStack(netherGlass, 1, 0));
        OreDictionary.registerOre("glass", new ItemStack(netherGlass, 1, 1));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.glass_bottle, 3), "# #", " # ", '#', "glass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.daylight_detector), "GGG", "QQQ", "WWW", 'G', "glass", 'Q', Items.quartz, 'W', "slabWood"));

    }

    public void addShapedRecipeFirst (List recipeList, ItemStack itemstack, Object... objArray)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;

        if (objArray[var4] instanceof String[])
        {
            String[] var7 = ((String[]) objArray[var4++]);

            for (String var9 : var7) {
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

    public void addShapelessRecipeFirst (List recipeList, ItemStack par1ItemStack, Object... par2ArrayOfObj)
    {
        ArrayList arraylist = new ArrayList();
        Object[] aobject = par2ArrayOfObj;
        int i = par2ArrayOfObj.length;

        for (int j = 0; j < i; ++j)
        {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack)
            {
                arraylist.add(((ItemStack) object1).copy());
            }
            else if (object1 instanceof Item)
            {
                arraylist.add(new ItemStack((Item) object1));
            }
            else
            {
                if (!(object1 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        recipeList.add(0, new ShapelessRecipes(par1ItemStack, arraylist));
    }

    public void addLoot ()
    {
        //ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(VanityBlocksStorage.StorageBlock,0,0),3,5,6));
    }

    public void addOredictSupport ()
    {
        OreDictionary.registerOre("cropRaspberry", new ItemStack(berryItem, 1, 0));
        OreDictionary.registerOre("cropBlueberry", new ItemStack(berryItem, 1, 1));
        OreDictionary.registerOre("cropBlackberry", new ItemStack(berryItem, 1, 2));
        OreDictionary.registerOre("cropMaloberry", new ItemStack(berryItem, 1, 3));

        OreDictionary.registerOre("cropBlightberry", new ItemStack(netherBerryItem, 1, 0));
        OreDictionary.registerOre("cropDuskberry", new ItemStack(netherBerryItem, 1, 1));
        OreDictionary.registerOre("cropSkyberry", new ItemStack(netherBerryItem, 1, 2));
        OreDictionary.registerOre("cropStingberry", new ItemStack(netherBerryItem, 1, 3));

        OreDictionary.registerOre("taintedSoil", new ItemStack(taintedSoil, 1));

        OreDictionary.registerOre("slabWood", new ItemStack(plankSlab1, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("slabWood", new ItemStack(plankSlab2, 1, Short.MAX_VALUE));

        OreDictionary.registerOre("treeSapling", new ItemStack(floraSapling, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("saplingTree", new ItemStack(rareSapling, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("treeSapling", new ItemStack(rareSapling, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("saplingTree", new ItemStack(netherSapling, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("treeSapling", new ItemStack(netherSapling, 1, Short.MAX_VALUE));

        OreDictionary.registerOre("leavesTree", new ItemStack(floraLeaves, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("leavesTree", new ItemStack(floraLeavesNoColor, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("leavesTree", new ItemStack(rareLeaves, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("leavesTree", new ItemStack(darkLeaves, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(floraLeaves, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(floraLeavesNoColor, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(rareLeaves, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(darkLeaves, 1, Short.MAX_VALUE));
    }

    public void createEntities ()
    {
        EntityRegistry.registerModEntity(ImpEntity.class, "Imp", 0, Natura.instance, 32, 5, true);
        EntityRegistry.registerModEntity(HeatscarSpider.class, "FlameSpider", 1, Natura.instance, 32, 5, true);
        EntityRegistry.registerModEntity(NitroCreeper.class, "NitroCreeper", 2, Natura.instance, 64, 5, true);
        EntityRegistry.registerModEntity(FusewoodArrow.class, "FusewoodArrow", 3, Natura.instance, 64, 3, true);
        EntityRegistry.registerModEntity(BabyHeatscarSpider.class, "FlameSpiderBaby", 4, Natura.instance, 32, 5, true);

        BiomeGenBase[] nether = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.NETHER);

        EntityRegistry.addSpawn(ImpEntity.class, 10, 8, 12, EnumCreatureType.creature, nether);
        EntityRegistry.addSpawn(HeatscarSpider.class, 10, 4, 4, EnumCreatureType.monster, nether);
        EntityRegistry.addSpawn(NitroCreeper.class, 8, 4, 6, EnumCreatureType.monster, nether);
        EntityRegistry.addSpawn(BabyHeatscarSpider.class, 7, 4, 4, EnumCreatureType.monster, nether);

        BlockDispenser.dispenseBehaviorRegistry.putObject(spawnEgg, new DispenserBehaviorSpawnEgg());
    }

    public void modIntegration ()
    {
        try
        {
            Class.forName("tconstruct.TConstruct");
            PatternBuilder pb = PatternBuilder.instance;
            pb.registerMaterial(new ItemStack(saguaro), 2, "Cactus");
        }
        catch (Exception e)
        {

        }

    }

    public static Item spawnEgg;

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


    //Others
    public static Block cloud;

    //Trees    
    public static Block tree;
    public static Block redwood;
    public static Block planks;
    public static Block bloodwood;
    public static Block willow;

    public static NLeaves floraLeaves;
    public static NLeaves floraLeavesNoColor;
    public static FloraSaplingBlock floraSapling;

    public static Block saguaro;

    public static Block redwoodDoor;
    public static Block eucalyptusDoor;
    public static Block hopseedDoor;
    public static Block sakuraDoor;
    public static Block ghostDoor;
    public static Block bloodDoor;
    public static Block redwoodBarkDoor;

    public static Item doorItem;
    //public static Item floraBoat;

    //Nether
    public static Block taintedSoil;
    public static Block heatSand;
    //public static Block infernalStone;
    public static Glowshroom glowshroom;
    public static Block darkTree;
    public static NLeaves darkLeaves;
    public static Block thornVines;
    public static NetherSaplingBlock netherSapling;

    public static Block glowshroomBlue;
    public static Block glowshroomGreen;
    public static Block glowshroomPurple;

    public static Block brail;
    public static Block brailPowered;
    public static Block brailDetector;
    public static Block brailActivator;

    public static Block netherrackFurnace;
    public static Block respawnObelisk;
    public static NetherGlass netherGlass;
    public static BlazeHopper netherHopper;
    public static Block netherPressurePlate;
    public static Block netherButton;
    public static Block netherLever;

    public static NetherPistonBase piston;
    public static NetherPistonBase pistonSticky;
    public static NetherPistonExtension pistonExtension;

    public static Item potashApple;
    public static Item impMeat;

    //Tools
    public static Item ghostwoodSword;
    public static Item ghostwoodPickaxe;
    public static Item ghostwoodShovel;
    public static Item ghostwoodAxe;
    public static Item bloodwoodSword;
    public static Item bloodwoodPickaxe;
    public static Item bloodwoodShovel;
    public static Item bloodwoodAxe;
    public static Item darkwoodSword;
    public static Item darkwoodPickaxe;
    public static Item darkwoodShovel;
    public static Item darkwoodAxe;
    public static Item fusewoodSword;
    public static Item fusewoodPickaxe;
    public static Item fusewoodShovel;
    public static Item fusewoodAxe;
    public static Item netherquartzSword;
    public static Item netherquartzPickaxe;
    public static Item netherquartzShovel;
    public static Item netherquartzAxe;

    public static Item ghostwoodKama;
    public static Item bloodwoodKama;
    public static Item darkwoodKama;
    public static Item fusewoodKama;
    public static Item netherquartzKama;

    public static Item ghostwoodBow;
    public static Item bloodwoodBow;
    public static Item darkwoodBow;
    public static Item fusewoodBow;

    public static Item impHelmet;
    public static Item impJerkin;
    public static Item impLeggings;
    public static Item impBoots;

    public static Item flintAndBlaze;

    public static ItemStack impHelmetStack;
    public static ItemStack impJerkinStack;
    public static ItemStack impLeggingsStack;
    public static ItemStack impBootsStack;

    //Extra overworld
    public static Block rareTree;
    public static NLeaves rareLeaves;
    public static RareSaplingBlock rareSapling;
    public static FlowerBlock bluebells;

    public static Item stickItem;
    public static Item bowlEmpty;
    public static Item bowlStew;

    //Vanilla overrides and alternates
    public static final String woodTextureNames[] = { "eucalyptus", "sakura", "ghostwood", "redwood", "bloodwood", "hopseed", "maple", "silverbell", "purpleheart", "tiger", "willow", "darkwood",
            "fusewood" };
    public static Block alternateWorkbench;
    public static Block alternateBookshelf;
    public static Block alternateFence;

    //Golem type things
    public static Block grassBlock;
    public static Block grassSlab;

    public static Block plankSlab1;
    public static Block plankSlab2;

    public static Block stairEucalyptus;
    public static Block stairSakura;
    public static Block stairGhostwood;
    public static Block stairRedwood;
    public static Block stairBloodwood;
    public static Block stairHopseed;
    public static Block stairMaple;
    public static Block stairSilverbell;
    public static Block stairAmaranth;
    public static Block stairTiger;
    public static Block stairWillow;
    public static Block stairDarkwood;
    public static Block stairFusewood;

    public static Block pressurePlateEucalyptus;
    public static Block pressurePlateSakura;
    public static Block pressurePlateGhostwood;
    public static Block pressurePlateRedwood;
    public static Block pressurePlateBloodwood;
    public static Block pressurePlateHopseed;
    public static Block pressurePlateMaple;
    public static Block pressurePlateAmaranth;
    public static Block pressurePlateSilverbell;
    public static Block pressurePlateTiger;
    public static Block pressurePlateWillow;
    public static Block pressurePlateDarkwood;
    public static Block pressurePlateFusewood;

    public static Block trapdoorEucalyptus;
    public static Block trapdoorSakura;
    public static Block trapdoorGhostwood;
    public static Block trapdoorRedwood;
    public static Block trapdoorBloodwood;
    public static Block trapdoorHopseed;
    public static Block trapdoorMaple;
    public static Block trapdoorAmaranth;
    public static Block trapdoorSilverbell;
    public static Block trapdoorTiger;
    public static Block trapdoorWillow;
    public static Block trapdoorDarkwood;
    public static Block trapdoorFusewood;

    public static Block buttonEucalyptus;
    public static Block buttonSakura;
    public static Block buttonGhostwood;
    public static Block buttonRedwood;
    public static Block buttonBloodwood;
    public static Block buttonHopseed;
    public static Block buttonMaple;
    public static Block buttonAmaranth;
    public static Block buttonSilverbell;
    public static Block buttonTiger;
    public static Block buttonWillow;
    public static Block buttonDarkwood;
    public static Block buttonFusewood;

    public static Block fenceGateEucalyptus;
    public static Block fenceGateSakura;
    public static Block fenceGateGhostwood;
    public static Block fenceGateRedwood;
    public static Block fenceGateBloodwood;
    public static Block fenceGateHopseed;
    public static Block fenceGateMaple;
    public static Block fenceGateAmaranth;
    public static Block fenceGateSilverbell;
    public static Block fenceGateTiger;
    public static Block fenceGateWillow;
    public static Block fenceGateDarkwood;
    public static Block fenceGateFusewood;

    @Override
    public int getBurnTime (ItemStack fuel)
    {
        if (fuel.getItem() == new ItemStack(floraSapling).getItem() || fuel.getItem() == new ItemStack(rareSapling).getItem() || fuel.getItem() == new ItemStack(netherSapling).getItem())
            return 100;
        return 0;
    }
}
