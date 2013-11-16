package mods.natura.common;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.*;

import java.util.*;

import mods.natura.Natura;
import mods.natura.blocks.*;
import mods.natura.blocks.crops.*;
import mods.natura.blocks.overrides.*;
import mods.natura.blocks.trees.*;
import mods.natura.entity.*;
import mods.natura.items.*;
import mods.natura.items.blocks.*;
import mods.natura.items.tools.*;
import mods.natura.util.DispenserBehaviorSpawnEgg;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.*;
import net.minecraftforge.oredict.*;
import tconstruct.library.crafting.PatternBuilder;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class NContent implements IFuelHandler
{

    public void preInit ()
    {
        spawnEgg = new SpawnEgg(PHNatura.spawnEgg).setUnlocalizedName("natura.spawnegg");
        //Crops
        plantItem = new PlantItem(PHNatura.foodID).setUnlocalizedName("barleyFood");
        crops = new CropBlock(PHNatura.floraCropsID);
        GameRegistry.registerBlock(crops, "N Crops");
        seeds = new NaturaSeeds(PHNatura.barleySeedID, crops.blockID, Block.tilledField.blockID).setUnlocalizedName("barley.seed");
        GameRegistry.registerCustomItemStack("seedBarley", new ItemStack(seeds, 1, 0));
        GameRegistry.registerCustomItemStack("seedCotton", new ItemStack(seeds, 1, 1));

        waterDrop = new CactusJuice(PHNatura.cactusJuice, false).setUnlocalizedName("waterdrop");

        wheatBag = new SeedBag(PHNatura.wheatBagID, Block.crops, 0, "wheat").setUnlocalizedName("wheatBag");
        GameRegistry.registerCustomItemStack("bagWheat", new ItemStack(wheatBag, 1, 0));
        barleyBag = new SeedBag(PHNatura.barleyBagID, crops, 0, "barley").setUnlocalizedName("barleyBag");
        GameRegistry.registerCustomItemStack("bagBarley", new ItemStack(barleyBag, 1, 0));
        potatoBag = new SeedBag(PHNatura.potatoBagID, Block.potato, 0, "potato").setUnlocalizedName("potatoBag");
        GameRegistry.registerCustomItemStack("bagPotato", new ItemStack(potatoBag, 1, 0));
        carrotBag = new SeedBag(PHNatura.carrotBagID, Block.carrot, 0, "carrot").setUnlocalizedName("carrotBag");
        GameRegistry.registerCustomItemStack("bagCarrot", new ItemStack(carrotBag, 1, 0));
        netherWartBag = new SeedBag(PHNatura.netherWartBagID, Block.netherStalk, 0, "netherwart").setUnlocalizedName("wartBag");
        GameRegistry.registerCustomItemStack("bagNetherWart", new ItemStack(netherWartBag, 1, 0));
        cottonBag = new SeedBag(PHNatura.cottonBagID, crops, 4, "cotton").setUnlocalizedName("cottonBag");
        GameRegistry.registerCustomItemStack("bagCotton", new ItemStack(cottonBag, 1, 0));
        boneBag = new BoneBag(PHNatura.boneBagID, "bone").setUnlocalizedName("boneBag");
        GameRegistry.registerCustomItemStack("bagBone", new ItemStack(boneBag, 1, 0));

        netherBerryItem = new NetherBerryItem(PHNatura.netherBerryItem, 1).setUnlocalizedName("berry.nether");
        GameRegistry.registerCustomItemStack("berryBlight", new ItemStack(netherBerryItem, 1, 0));
        GameRegistry.registerCustomItemStack("berryDusk", new ItemStack(netherBerryItem, 1, 1));
        GameRegistry.registerCustomItemStack("berrySky", new ItemStack(netherBerryItem, 1, 2));
        GameRegistry.registerCustomItemStack("berrySting", new ItemStack(netherBerryItem, 1, 3));
        berryItem = new BerryItem(PHNatura.berryItemID, 1).setUnlocalizedName("berry");
        GameRegistry.registerCustomItemStack("berryRasp", new ItemStack(berryItem, 1, 0));
        GameRegistry.registerCustomItemStack("berryBlue", new ItemStack(berryItem, 1, 1));
        GameRegistry.registerCustomItemStack("berryBlack", new ItemStack(berryItem, 1, 2));
        GameRegistry.registerCustomItemStack("berryMalo", new ItemStack(berryItem, 1, 3));
        berryMedley = new BerryMedley(PHNatura.berryMedley, 5).setUnlocalizedName("berryMedley");
        GameRegistry.registerCustomItemStack("berryMedley", new ItemStack(berryMedley, 1, 0));

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
        MinecraftForge.addGrassPlant(bluebells, 0, 18);

        //floraBoat = new NBoat(PHNatura.boatItemID).setIconCoord(0, 3).setUnlocalizedName("floraBoat");

        GameRegistry.registerBlock(tree, TreeItem.class, "tree");
        GameRegistry.registerBlock(redwood, RedwoodItem.class, "redwood");
        GameRegistry.registerBlock(planks, PlanksItem.class, "planks");
        GameRegistry.registerBlock(floraLeaves, NLeavesItem.class, "floraleaves");
        GameRegistry.registerBlock(floraLeavesNoColor, NoColorLeavesItem.class, "floraleavesnocolor");
        GameRegistry.registerBlock(floraSapling, NSaplingItem.class, "florasapling");
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
        bloodwood = new LogTwoxTwo(PHNatura.bloodwoodID, 8f, Material.wood).setUnlocalizedName("bloodwood");
        GameRegistry.registerBlock(bloodwood, LogTwoxTwoItem.class, "bloodwood");
        taintedSoil = new TaintedSoil(PHNatura.taintedSoil).setUnlocalizedName("TaintedSoil");
        GameRegistry.registerBlock(taintedSoil, "soil.tainted");
        heatSand = new HeatSand(PHNatura.heatSand).setUnlocalizedName("HeatSand");//.setLightValue(0.375f);
        GameRegistry.registerBlock(heatSand, "heatsand");
        /*netherAir = new SulfurAirBlock(PHNatura.sulfurair).setUnlocalizedName("SulfurAir");
        GameRegistry.registerBlock(netherAir, "netherAir");*/
        /*infernalStone = new NBlock(PHNatura.infernalStone, Material.rock, 1.5f, new String[] { "infernal_stone" }).setUnlocalizedName("infernalStone");
        GameRegistry.registerBlock(infernalStone, "infernalStone");*/

        darkTree = new DarkTreeBlock(PHNatura.darkTree).setUnlocalizedName("Darktree");
        GameRegistry.registerBlock(darkTree, DarkTreeItem.class, "Dark Tree");
        darkLeaves = (NLeaves) new NLeavesDark(PHNatura.darkLeaves).setUnlocalizedName("Darkleaves");
        GameRegistry.registerBlock(darkLeaves, NLeavesDarkItem.class, "Dark Leaves");
        thornVines = new ThornVines(PHNatura.thornVines).setUnlocalizedName("Thornvines").setLightValue(0.625f);
        GameRegistry.registerBlock(thornVines, "Thornvines");
        glowshroom = (Glowshroom) new Glowshroom(PHNatura.glowshroom).setUnlocalizedName("Glowshroom").setLightValue(0.625f);
        GameRegistry.registerBlock(glowshroom, GlowshroomItem.class, "Glowshroom");
        glowshroomBlue = new LargeGlowshroom(PHNatura.glowshroomBlue, Material.wood, "blue").setUnlocalizedName("blueGlowshroom").setLightValue(0.625f);
        GameRegistry.registerBlock(glowshroomBlue, "blueGlowshroom");
        glowshroomPurple = new LargeGlowshroom(PHNatura.glowshroomPurple, Material.wood, "purple").setUnlocalizedName("purpleGlowshroom").setLightValue(0.5f);
        GameRegistry.registerBlock(glowshroomPurple, "purpleGlowshroom");
        glowshroomGreen = new LargeGlowshroom(PHNatura.glowshroomGreen, Material.wood, "green").setUnlocalizedName("greenGlowshroom").setLightValue(0.5f);
        GameRegistry.registerBlock(glowshroomGreen, "greenGlowshroom");

        Block.netherrack.setResistance(4f);

        /*public static Block glowshroomBlue;
        public static Block glowshroomGreen;
        public static Block glowshroomPurple;*/

        potashApple = new NetherFoodItem(PHNatura.netherFood).setUnlocalizedName("Natura.netherfood");
        GameRegistry.registerCustomItemStack("applePotash", new ItemStack(potashApple, 1, 0));

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

        //Vanilla overrides and alternates
        //Block.blocksList[58] = null;
        //Item.itemsList[58] = null;
        alternateWorkbench = new AlternateWorkbench(PHNatura.alternateWorkbench).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("workbench").setCreativeTab(NaturaTab.tab);
        GameRegistry.registerBlock(alternateWorkbench, NAlternateItem.class, "Natura.workbench");

        //Block.blocksList[47] = null;
        //Item.itemsList[47] = null;
        alternateBookshelf = new AlternateBookshelf(PHNatura.alternateBookshelf).setHardness(1.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("bookshelf").setCreativeTab(NaturaTab.tab);
        GameRegistry.registerBlock(alternateBookshelf, NAlternateItem.class, "Natura.bookshelf");

        alternateFence = new AlternateFence(PHNatura.alternateFence, Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fence")
                .setCreativeTab(NaturaTab.tab);
        GameRegistry.registerBlock(alternateFence, FenceItem.class, "Natura.fence");

        grassBlock = new GrassBlock(PHNatura.grassBlock).setUnlocalizedName("GrassBlock");
        grassBlock.stepSound = Block.soundGrassFootstep;
        GameRegistry.registerBlock(grassBlock, GrassBlockItem.class, "GrassBlock");

        grassSlab = new GrassSlab(PHNatura.grassSlab).setUnlocalizedName("GrassSlab");
        grassSlab.stepSound = Block.soundGrassFootstep;
        GameRegistry.registerBlock(grassSlab, GrassSlabItem.class, "GrassSlab");

        plankSlab1 = new NSlabBase(PHNatura.plankSlab1, Material.wood, planks, 0, 8).setHardness(2.0f).setUnlocalizedName("plankSlab1");
        plankSlab1.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(plankSlab1, PlankSlab1Item.class, "plankSlab1");

        plankSlab2 = new NSlabBase(PHNatura.plankSlab2, Material.wood, planks, 8, 5).setHardness(2.0f).setUnlocalizedName("plankSlab2");
        plankSlab2.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(plankSlab2, PlankSlab2Item.class, "plankSlab2");

        //Stairs
        stairEucalyptus = new NStairs(PHNatura.stairEucalyptus, planks, 0).setUnlocalizedName("stair.eucalyptus");
        stairEucalyptus.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairEucalyptus, "stair.eucalyptus");

        stairSakura = new NStairs(PHNatura.stairSakura, planks, 1).setUnlocalizedName("stair.sakura");
        stairSakura.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairSakura, "stair.sakura");

        stairGhostwood = new NStairs(PHNatura.stairGhostwood, planks, 2).setUnlocalizedName("stair.ghostwood");
        stairGhostwood.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairGhostwood, "stair.ghostwood");

        stairRedwood = new NStairs(PHNatura.stairRedwood, planks, 3).setUnlocalizedName("stair.redwood");
        stairRedwood.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairRedwood, "stair.redwood");

        stairBloodwood = new NStairs(PHNatura.stairBloodwood, planks, 4).setUnlocalizedName("stair.bloodwood");
        stairBloodwood.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairBloodwood, "stair.bloodwood");

        stairHopseed = new NStairs(PHNatura.stairHopseed, planks, 5).setUnlocalizedName("stair.hopseed");
        stairHopseed.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairHopseed, "stair.hopseed");

        stairMaple = new NStairs(PHNatura.stairMaple, planks, 6).setUnlocalizedName("stair.maple");
        stairMaple.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairMaple, "stair.maple");

        stairSilverbell = new NStairs(PHNatura.stairSilverbell, planks, 7).setUnlocalizedName("stair.silverbell");
        stairSilverbell.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairSilverbell, "stair.silverbell");

        stairAmaranth = new NStairs(PHNatura.stairAmaranth, planks, 8).setUnlocalizedName("stair.amaranth");
        stairAmaranth.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairAmaranth, "stair.amaranth");

        stairTiger = new NStairs(PHNatura.stairTiger, planks, 9).setUnlocalizedName("stair.tiger");
        stairTiger.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairTiger, "stair.tiger");

        stairWillow = new NStairs(PHNatura.stairWillow, planks, 10).setUnlocalizedName("stair.willow");
        stairWillow.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairWillow, "stair.willow");

        stairDarkwood = new NStairs(PHNatura.stairDarkwood, planks, 11).setUnlocalizedName("stair.darkwood");
        stairDarkwood.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairDarkwood, "stair.darkwood");

        stairFusewood = new NStairs(PHNatura.stairFusewood, planks, 12).setUnlocalizedName("stair.fusewood");
        stairFusewood.stepSound = Block.soundWoodFootstep;
        GameRegistry.registerBlock(stairFusewood, "stair.fusewood");

        //Eucalyptus
        pressurePlateEucalyptus = new NPressurePlate(PHNatura.pressurePlateEucalyptus, Material.wood, EnumMobType.everything, planks, 0);
        pressurePlateEucalyptus.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.eucalyptus");
        GameRegistry.registerBlock(pressurePlateEucalyptus, "pressureplate.eucalyptus");

        trapdoorEucalyptus = new NTrapdoor(PHNatura.trapdoorEucalyptus, Material.wood, "eucalyptus_trapdoor");
        trapdoorEucalyptus.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.eucalyptus");
        GameRegistry.registerBlock(trapdoorEucalyptus, "trapdoor.eucalyptus");

        buttonEucalyptus = new NButton(PHNatura.buttonEucalyptus, planks, 0);
        buttonEucalyptus.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.eucalyptus");
        GameRegistry.registerBlock(buttonEucalyptus, "button.eucalyptus");

        fenceGateEucalyptus = new NFenceGate(PHNatura.fenceGateEucalyptus, planks, 0);
        fenceGateEucalyptus.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.eucalyptus");
        GameRegistry.registerBlock(fenceGateEucalyptus, "fenceGate.eucalyptus");

        //Sakura
        pressurePlateSakura = new NPressurePlate(PHNatura.pressurePlateSakura, Material.wood, EnumMobType.everything, planks, 1);
        pressurePlateSakura.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.sakura");
        GameRegistry.registerBlock(pressurePlateSakura, "pressureplate.sakura");

        trapdoorSakura = new NTrapdoor(PHNatura.trapdoorSakura, Material.wood, "sakura_trapdoor");
        trapdoorSakura.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.sakura");
        GameRegistry.registerBlock(trapdoorSakura, "trapdoor.sakura");

        buttonSakura = new NButton(PHNatura.buttonSakura, planks, 1);
        buttonSakura.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.sakura");
        GameRegistry.registerBlock(buttonSakura, "button.sakura");

        fenceGateSakura = new NFenceGate(PHNatura.fenceGateSakura, planks, 1);
        fenceGateSakura.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.sakura");
        GameRegistry.registerBlock(fenceGateSakura, "fenceGate.sakura");

        //Ghostwood
        pressurePlateGhostwood = new NPressurePlate(PHNatura.pressurePlateGhostwood, Material.wood, EnumMobType.everything, planks, 2);
        pressurePlateGhostwood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.ghostwood");
        GameRegistry.registerBlock(pressurePlateGhostwood, "pressureplate.ghostwood");

        trapdoorGhostwood = new NTrapdoor(PHNatura.trapdoorGhostwood, Material.wood, "ghostwood_trapdoor");
        trapdoorGhostwood.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.ghostwood");
        GameRegistry.registerBlock(trapdoorGhostwood, "trapdoor.ghostwood");

        buttonGhostwood = new NButton(PHNatura.buttonGhostwood, planks, 2);
        buttonGhostwood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.ghostwood");
        GameRegistry.registerBlock(buttonGhostwood, "button.ghostwood");

        fenceGateGhostwood = new NFenceGate(PHNatura.fenceGateGhostwood, planks, 2);
        fenceGateGhostwood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.ghostwood");
        GameRegistry.registerBlock(fenceGateGhostwood, "fenceGate.ghostwood");

        //Redwood
        pressurePlateRedwood = new NPressurePlate(PHNatura.pressurePlateRedwood, Material.wood, EnumMobType.everything, planks, 3);
        pressurePlateRedwood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.redwood");
        GameRegistry.registerBlock(pressurePlateRedwood, "pressureplate.redwood");

        trapdoorRedwood = new NTrapdoor(PHNatura.trapdoorRedwood, Material.wood, "redwood_trapdoor");
        trapdoorRedwood.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.redwood");
        GameRegistry.registerBlock(trapdoorRedwood, "trapdoor.redwood");

        buttonRedwood = new NButton(PHNatura.buttonRedwood, planks, 3);
        buttonRedwood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.redwood");
        GameRegistry.registerBlock(buttonRedwood, "button.redwood");

        fenceGateRedwood = new NFenceGate(PHNatura.fenceGateRedwood, planks, 3);
        fenceGateRedwood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.redwood");
        GameRegistry.registerBlock(fenceGateRedwood, "fenceGate.redwood");

        //Bloodwood
        pressurePlateBloodwood = new NPressurePlate(PHNatura.pressurePlateBloodwood, Material.wood, EnumMobType.everything, planks, 4);
        pressurePlateBloodwood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.bloodwood");
        GameRegistry.registerBlock(pressurePlateBloodwood, "pressureplate.bloodwood");

        trapdoorBloodwood = new NTrapdoor(PHNatura.trapdoorBloodwood, Material.wood, "bloodwood_trapdoor");
        trapdoorBloodwood.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.bloodwood");
        GameRegistry.registerBlock(trapdoorBloodwood, "trapdoor.bloodwood");

        buttonBloodwood = new NButton(PHNatura.buttonBloodwood, planks, 4);
        buttonBloodwood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.bloodwood");
        GameRegistry.registerBlock(buttonBloodwood, "button.bloodwood");

        fenceGateBloodwood = new NFenceGate(PHNatura.fenceGateBloodwood, planks, 4);
        fenceGateBloodwood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.bloodwood");
        GameRegistry.registerBlock(fenceGateBloodwood, "fenceGate.bloodwood");

        //Hopseed
        pressurePlateHopseed = new NPressurePlate(PHNatura.pressurePlateHopseed, Material.wood, EnumMobType.everything, planks, 5);
        pressurePlateHopseed.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.hopseed");
        GameRegistry.registerBlock(pressurePlateHopseed, "pressureplate.hopseed");

        trapdoorHopseed = new NTrapdoor(PHNatura.trapdoorHopseed, Material.wood, "hopseed_trapdoor");
        trapdoorHopseed.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.hopseed");
        GameRegistry.registerBlock(trapdoorHopseed, "trapdoor.hopseed");

        buttonHopseed = new NButton(PHNatura.buttonHopseed, planks, 5);
        buttonHopseed.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.hopseed");
        GameRegistry.registerBlock(buttonHopseed, "button.hopseed");

        fenceGateHopseed = new NFenceGate(PHNatura.fenceGateHopseed, planks, 5);
        fenceGateHopseed.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.hopseed");
        GameRegistry.registerBlock(fenceGateHopseed, "fenceGate.hopseed");

        //Maple
        pressurePlateMaple = new NPressurePlate(PHNatura.pressurePlateMaple, Material.wood, EnumMobType.everything, planks, 6);
        pressurePlateMaple.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.maple");
        GameRegistry.registerBlock(pressurePlateMaple, "pressureplate.maple");

        trapdoorMaple = new NTrapdoor(PHNatura.trapdoorMaple, Material.wood, "maple_trapdoor");
        trapdoorMaple.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.maple");
        GameRegistry.registerBlock(trapdoorMaple, "trapdoor.maple");

        buttonMaple = new NButton(PHNatura.buttonMaple, planks, 6);
        buttonMaple.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.maple");
        GameRegistry.registerBlock(buttonMaple, "button.maple");

        fenceGateMaple = new NFenceGate(PHNatura.fenceGateMaple, planks, 6);
        fenceGateMaple.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.maple");
        GameRegistry.registerBlock(fenceGateMaple, "fenceGate.maple");

        //Amaranth
        pressurePlateAmaranth = new NPressurePlate(PHNatura.pressurePlateAmaranth, Material.wood, EnumMobType.everything, planks, 8);
        pressurePlateAmaranth.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.amaranth");
        GameRegistry.registerBlock(pressurePlateAmaranth, "pressureplate.amaranth");

        trapdoorAmaranth = new NTrapdoor(PHNatura.trapdoorAmaranth, Material.wood, "purpleheart_trapdoor");
        trapdoorAmaranth.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.amaranth");
        GameRegistry.registerBlock(trapdoorAmaranth, "trapdoor.amaranth");

        buttonAmaranth = new NButton(PHNatura.buttonAmaranth, planks, 8);
        buttonAmaranth.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.amaranth");
        GameRegistry.registerBlock(buttonAmaranth, "button.amaranth");

        fenceGateAmaranth = new NFenceGate(PHNatura.fenceGateAmaranth, planks, 8);
        fenceGateAmaranth.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.amaranth");
        GameRegistry.registerBlock(fenceGateAmaranth, "fenceGate.amaranth");

        //Silverbell
        pressurePlateSilverbell = new NPressurePlate(PHNatura.pressurePlateSilverbell, Material.wood, EnumMobType.everything, planks, 7);
        pressurePlateSilverbell.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.silverbell");
        GameRegistry.registerBlock(pressurePlateSilverbell, "pressureplate.silverbell");

        trapdoorSilverbell = new NTrapdoor(PHNatura.trapdoorSilverbell, Material.wood, "silverbell_trapdoor");
        trapdoorSilverbell.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.silverbell");
        GameRegistry.registerBlock(trapdoorSilverbell, "trapdoor.silverbell");

        buttonSilverbell = new NButton(PHNatura.buttonSilverbell, planks, 7);
        buttonSilverbell.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.silverbell");
        GameRegistry.registerBlock(buttonSilverbell, "button.silverbell");

        fenceGateSilverbell = new NFenceGate(PHNatura.fenceGateSilverbell, planks, 7);
        fenceGateSilverbell.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.silverbell");
        GameRegistry.registerBlock(fenceGateSilverbell, "fenceGate.silverbell");

        //Tigerwood
        pressurePlateTiger = new NPressurePlate(PHNatura.pressurePlateTiger, Material.wood, EnumMobType.everything, planks, 9);
        pressurePlateTiger.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.tiger");
        GameRegistry.registerBlock(pressurePlateTiger, "pressureplate.tiger");

        trapdoorTiger = new NTrapdoor(PHNatura.trapdoorTiger, Material.wood, "tiger_trapdoor");
        trapdoorTiger.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.tiger");
        GameRegistry.registerBlock(trapdoorTiger, "trapdoor.tiger");

        buttonTiger = new NButton(PHNatura.buttonTiger, planks, 9);
        buttonTiger.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.tiger");
        GameRegistry.registerBlock(buttonTiger, "button.tiger");

        fenceGateTiger = new NFenceGate(PHNatura.fenceGateTiger, planks, 9);
        fenceGateTiger.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.tiger");
        GameRegistry.registerBlock(fenceGateTiger, "fenceGate.tiger");

        //Willow
        pressurePlateWillow = new NPressurePlate(PHNatura.pressurePlateWillow, Material.wood, EnumMobType.everything, planks, 10);
        pressurePlateWillow.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.willow");
        GameRegistry.registerBlock(pressurePlateWillow, "pressureplate.willow");

        trapdoorWillow = new NTrapdoor(PHNatura.trapdoorWillow, Material.wood, "willow_trapdoor");
        trapdoorWillow.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.willow");
        GameRegistry.registerBlock(trapdoorWillow, "trapdoor.willow");

        buttonWillow = new NButton(PHNatura.buttonWillow, planks, 10);
        buttonWillow.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.willow");
        GameRegistry.registerBlock(buttonWillow, "button.willow");

        fenceGateWillow = new NFenceGate(PHNatura.fenceGateWillow, planks, 10);
        fenceGateWillow.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.willow");
        GameRegistry.registerBlock(fenceGateWillow, "fenceGate.willow");

        //Darkwood
        pressurePlateDarkwood = new NPressurePlate(PHNatura.pressurePlateDarkwood, Material.wood, EnumMobType.everything, planks, 11);
        pressurePlateDarkwood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.darkwood");
        GameRegistry.registerBlock(pressurePlateDarkwood, "pressureplate.darkwood");

        trapdoorDarkwood = new NTrapdoor(PHNatura.trapdoorDarkwood, Material.wood, "darkwood_trapdoor");
        trapdoorDarkwood.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.darkwood");
        GameRegistry.registerBlock(trapdoorDarkwood, "trapdoor.darkwood");

        buttonDarkwood = new NButton(PHNatura.buttonDarkwood, planks, 11);
        buttonDarkwood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.darkwood");
        GameRegistry.registerBlock(buttonDarkwood, "button.darkwood");

        fenceGateDarkwood = new NFenceGate(PHNatura.fenceGateDarkwood, planks, 11);
        fenceGateDarkwood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.darkwood");
        GameRegistry.registerBlock(fenceGateDarkwood, "fenceGate.darkwood");

        //Fusewood
        pressurePlateFusewood = new NPressurePlate(PHNatura.pressurePlateFusewood, Material.wood, EnumMobType.everything, planks, 12);
        pressurePlateFusewood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("pressureplate.fusewood");
        GameRegistry.registerBlock(pressurePlateFusewood, "pressureplate.fusewood");

        trapdoorFusewood = new NTrapdoor(PHNatura.trapdoorFusewood, Material.wood, "fusewood_trapdoor");
        trapdoorFusewood.setHardness(3.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("trapdoor.fusewood");
        GameRegistry.registerBlock(trapdoorFusewood, "trapdoor.fusewood");

        buttonFusewood = new NButton(PHNatura.buttonFusewood, planks, 12);
        buttonFusewood.setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("button.fusewood");
        GameRegistry.registerBlock(buttonFusewood, "button.fusewood");

        fenceGateFusewood = new NFenceGate(PHNatura.fenceGateFusewood, planks, 12);
        fenceGateFusewood.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("fenceGate.fusewood");
        GameRegistry.registerBlock(fenceGateFusewood, "fenceGate.fusewood");

        //Item.itemsList[24] = null;
        //Item.stick = null;
        stickItem = (new StickItem(PHNatura.stickItem)).setFull3D().setUnlocalizedName("natura.stick").setCreativeTab(NaturaTab.tab);
        GameRegistry.registerItem(stickItem, "natura.stick");

        EnumToolMaterial Bloodwood = EnumHelper.addToolMaterial("Bloodwood", 2, 350, 7f, 3, 24);
        EnumArmorMaterial Imp = EnumHelper.addArmorMaterial("Imp", 33, new int[] { 1, 3, 2, 1 }, 15);

        ghostwoodSword = new NaturaSword(PHNatura.ghostwoodSword, EnumToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.sword.ghostwood");
        ghostwoodPickaxe = new NaturaPickaxe(PHNatura.ghostwoodPickaxe, EnumToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.pickaxe.ghostwood");
        ghostwoodShovel = new NaturaShovel(PHNatura.ghostwoodShovel, EnumToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.shovel.ghostwood");
        ghostwoodAxe = new NaturaHatchet(PHNatura.ghostwoodAxe, EnumToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.axe.ghostwood");

        bloodwoodSword = new NaturaSword(PHNatura.bloodwoodSword, Bloodwood, "bloodwood").setUnlocalizedName("natura.sword.bloodwood");
        bloodwoodPickaxe = new NaturaPickaxe(PHNatura.bloodwoodPickaxe, Bloodwood, "bloodwood").setUnlocalizedName("natura.pickaxe.bloodwood");
        bloodwoodShovel = new NaturaShovel(PHNatura.bloodwoodShovel, Bloodwood, "bloodwood").setUnlocalizedName("natura.shovel.bloodwood");
        bloodwoodAxe = new NaturaHatchet(PHNatura.bloodwoodAxe, Bloodwood, "bloodwood").setUnlocalizedName("natura.axe.bloodwood");

        darkwoodSword = new NaturaSword(PHNatura.darkwoodSword, EnumToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.sword.darkwood");
        darkwoodPickaxe = new NaturaPickaxe(PHNatura.darkwoodPickaxe, EnumToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.pickaxe.darkwood");
        darkwoodShovel = new NaturaShovel(PHNatura.darkwoodShovel, EnumToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.shovel.darkwood");
        darkwoodAxe = new NaturaHatchet(PHNatura.darkwoodAxe, EnumToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.axe.darkwood");

        fusewoodSword = new NaturaSword(PHNatura.fusewoodSword, EnumToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.sword.fusewood");
        fusewoodPickaxe = new NaturaPickaxe(PHNatura.fusewoodPickaxe, EnumToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.pickaxe.fusewood");
        fusewoodShovel = new NaturaShovel(PHNatura.fusewoodShovel, EnumToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.shovel.fusewood");
        fusewoodAxe = new NaturaHatchet(PHNatura.fusewoodAxe, EnumToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.axe.fusewood");

        netherquartzSword = new NaturaSword(PHNatura.netherquartzSword, EnumToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.sword.netherquartz");
        netherquartzPickaxe = new NaturaPickaxe(PHNatura.netherquartzPickaxe, EnumToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.pickaxe.netherquartz");
        netherquartzShovel = new NaturaShovel(PHNatura.netherquartzShovel, EnumToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.shovel.netherquartz");
        netherquartzAxe = new NaturaHatchet(PHNatura.netherquartzAxe, EnumToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.axe.netherquartz");
        netherquartzKama = new NaturaKama(PHNatura.netherquartzKama, EnumToolMaterial.STONE, "netherquartz").setUnlocalizedName("natura.kama.netherquartz");

        ghostwoodKama = new NaturaKama(PHNatura.ghostwoodKama, EnumToolMaterial.WOOD, "ghostwood").setUnlocalizedName("natura.kama.ghostwood");
        bloodwoodKama = new NaturaKama(PHNatura.bloodwoodKama, Bloodwood, "bloodwood").setUnlocalizedName("natura.kama.bloodwood");
        darkwoodKama = new NaturaKama(PHNatura.darkwoodKama, EnumToolMaterial.STONE, "darkwood").setUnlocalizedName("natura.kama.darkwood");
        fusewoodKama = new NaturaKama(PHNatura.fusewoodKama, EnumToolMaterial.IRON, "fusewood").setUnlocalizedName("natura.kama.fusewood");

        ghostwoodBow = new NaturaBow(PHNatura.ghostwoodBow, 384, "ghostwood").setUnlocalizedName("natura.bow.ghostwood");
        bloodwoodBow = new NaturaBow(PHNatura.bloodwoodBow, 1501, "bloodwood").setUnlocalizedName("natura.bow.bloodwood");
        darkwoodBow = new NaturaBow(PHNatura.darkwoodBow, 162, "darkwood").setUnlocalizedName("natura.bow.darkwood");
        fusewoodBow = new NaturaBow(PHNatura.fusewoodBow, 28, "fusewood").setUnlocalizedName("natura.bow.fusewood");

        impHelmet = new NaturaArmor(PHNatura.impHelmet, Imp, 1, 0, "imp_helmet", "imp").setUnlocalizedName("natura.armor.imphelmet");
        impJerkin = new NaturaArmor(PHNatura.impJerkin, Imp, 1, 1, "imp_body", "imp").setUnlocalizedName("natura.armor.impjerkin");
        impLeggings = new NaturaArmor(PHNatura.impLeggings, Imp, 1, 2, "imp_leggings", "imp").setUnlocalizedName("natura.armor.impleggings");
        impBoots = new NaturaArmor(PHNatura.impBoots, Imp, 1, 3, "imp_boots", "imp").setUnlocalizedName("natura.armor.impboots");

        impMeat = new ImpMeat(PHNatura.impMeat).setUnlocalizedName("impmeat");
        GameRegistry.registerCustomItemStack("rawImphide", new ItemStack(impMeat, 1, 0));
        GameRegistry.registerCustomItemStack("cookedImphide", new ItemStack(impMeat, 1, 1));

        MinecraftForge.setToolClass(ghostwoodPickaxe, "pickaxe", 0);
        MinecraftForge.setToolClass(ghostwoodShovel, "shovel", 0);
        MinecraftForge.setToolClass(ghostwoodAxe, "axe", 0);

        MinecraftForge.setToolClass(bloodwoodPickaxe, "pickaxe", 2);
        MinecraftForge.setToolClass(bloodwoodShovel, "shovel", 2);
        MinecraftForge.setToolClass(bloodwoodAxe, "axe", 2);

        MinecraftForge.setToolClass(darkwoodPickaxe, "pickaxe", 1);
        MinecraftForge.setToolClass(darkwoodShovel, "shovel", 1);
        MinecraftForge.setToolClass(darkwoodAxe, "axe", 1);

        MinecraftForge.setToolClass(fusewoodPickaxe, "pickaxe", 2);
        MinecraftForge.setToolClass(fusewoodShovel, "shovel", 2);
        MinecraftForge.setToolClass(fusewoodAxe, "axe", 2);

        MinecraftForge.setToolClass(netherquartzPickaxe, "pickaxe", 1);
        MinecraftForge.setToolClass(netherquartzShovel, "shovel", 1);
        MinecraftForge.setToolClass(netherquartzAxe, "axe", 1);
        //Material.vine.setRequiresTool();

        MinecraftForge.setBlockHarvestLevel(bloodwood, "axe", 2);
        MinecraftForge.setBlockHarvestLevel(darkTree, 1, "axe", 1);
        MinecraftForge.setBlockHarvestLevel(darkTree, 0, "axe", -1);
        MinecraftForge.setBlockHarvestLevel(tree, "axe", -1);
        MinecraftForge.setBlockHarvestLevel(redwood, "axe", -1);

        MinecraftForge.setBlockHarvestLevel(taintedSoil, "shovel", 0);
        MinecraftForge.setBlockHarvestLevel(heatSand, "shovel", 0);

        bowlEmpty = new BowlEmpty(PHNatura.bowlEmpty).setUnlocalizedName("natura.emptybowl");
        bowlStew = new BowlStew(PHNatura.bowlStew).setUnlocalizedName("natura.stewbowl");
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
        if (PHNatura.enableWheatRecipe)
            GameRegistry.addRecipe(new ItemStack(plantItem, 1, 2), "X", 'X', new ItemStack(Item.wheat));

        GameRegistry.addRecipe(new ItemStack(plantItem, 2, 8), "X", 'X', new ItemStack(bluebells));

        FurnaceRecipes.smelting().addSmelting(saguaro.blockID, 0, new ItemStack(Item.dyePowder, 1, 2), 0.2F);
        FurnaceRecipes.smelting().addSmelting(impMeat.itemID, 0, new ItemStack(impMeat, 1, 1), 0.2F);

        for (int i = 1; i <= 2; i++)
        {
            FurnaceRecipes.smelting().addSmelting(plantItem.itemID, i, new ItemStack(Item.bread, 1), 0.5f);
            GameRegistry.addRecipe(new ItemStack(Item.cake, 1), "AAA", "BEB", " C ", 'A', Item.bucketMilk, 'B', Item.sugar, 'C', new ItemStack(plantItem, 1, i), 'E', Item.egg);
        }

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
                new ItemStack(rareTree, 1, 0), new ItemStack(rareTree, 1, 1), new ItemStack(rareTree, 1, 2), new ItemStack(rareTree, 1, 3), new ItemStack(willow, 1, 0), new ItemStack(darkTree, 1, 0) };
        int[] exclusions = { 4, 11 };

        GameRegistry.addRecipe(new ItemStack(plantItem, 1, 5), " s ", "#s#", "#s#", 's', new ItemStack(stickItem, 1, 2), '#', new ItemStack(floraLeavesNoColor, 1, 1));
        GameRegistry.addRecipe(new ItemStack(Item.arrow, 4, 0), " f ", "#s#", " # ", 's', new ItemStack(stickItem, 1, Short.MAX_VALUE), '#', new ItemStack(plantItem, 1, 5), 'f', Item.flint);
        GameRegistry.addRecipe(new ItemStack(Item.arrow, 4, 0), " f ", "#s#", " # ", 's', Item.stick, '#', new ItemStack(plantItem, 1, 5), 'f', Item.flint);

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
            addShapedRecipeFirst(recipes, new ItemStack(alternateBookshelf, 1, i), "###", "bbb", "###", '#', new ItemStack(planks, 1, i), 'b', Item.book);
            addShapedRecipeFirst(recipes, new ItemStack(alternateFence, 2, i), "###", "###", '#', new ItemStack(stickItem, 1, i));
        }

        int meta = 0;
        for (int i = 0; i < logStacks.length; i++)
        {
            OreDictionary.registerOre("crafterWood", new ItemStack(alternateWorkbench, 1, meta));
            OreDictionary.registerOre("craftingTableWood", new ItemStack(alternateWorkbench, 1, meta));
            for (int e = 0; e < exclusions.length; e++)
            {
                if (exclusions[e] == i)
                    meta++;
            }
            OreDictionary.registerOre("plankWood", new ItemStack(planks, 1, meta));
            OreDictionary.registerOre("logWood", logStacks[i]);
            OreDictionary.registerOre("stickWood", new ItemStack(stickItem, 1, meta));
            meta++;
        }

        OreDictionary.registerOre("dyeBlue", new ItemStack(plantItem, 1, 8));
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

        GameRegistry.addRecipe(new ItemStack(netherquartzSword, 1, 0), "#", "#", "s", '#', new ItemStack(Block.blockNetherQuartz, 1, Short.MAX_VALUE), 's', new ItemStack(stickItem, 1, 2));
        GameRegistry.addRecipe(new ItemStack(netherquartzPickaxe, 1, 0), "###", " s ", " s ", '#', new ItemStack(Block.blockNetherQuartz, 1, Short.MAX_VALUE), 's', new ItemStack(stickItem, 1, 2));
        GameRegistry.addRecipe(new ItemStack(netherquartzShovel, 1, 0), "#", "s", "s", '#', new ItemStack(Block.blockNetherQuartz, 1, Short.MAX_VALUE), 's', new ItemStack(stickItem, 1, 2));
        GameRegistry.addRecipe(new ItemStack(netherquartzAxe, 1, 0), "##", "#s", " s", '#', new ItemStack(Block.blockNetherQuartz, 1, Short.MAX_VALUE), 's', new ItemStack(stickItem, 1, 2));

        GameRegistry.addRecipe(new ItemStack(Item.leather, 2), "##", "##", '#', new ItemStack(plantItem, 1, 6));

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

        //Crafting overrides
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.swordWood, 1, 0), "##", "#s", " s", 's', new ItemStack(stickItem, 1, Short.MAX_VALUE), '#', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.pickaxeWood, 1, 0), "##", "#s", " s", 's', new ItemStack(stickItem, 1, Short.MAX_VALUE), '#', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.shovelWood, 1, 0), "##", "#s", " s", 's', new ItemStack(stickItem, 1, Short.MAX_VALUE), '#', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.axeWood, 1, 0), "##", "#s", " s", 's', new ItemStack(stickItem, 1, Short.MAX_VALUE), '#', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.hoeWood, 1, 0), "##", "#s", " s", 's', new ItemStack(stickItem, 1, Short.MAX_VALUE), '#', "plankWood"));

        GameRegistry.addShapelessRecipe(new ItemStack(Block.slowSand, 1, 0), heatSand, taintedSoil);

        for (int i = 0; i < BowlEmpty.textureNames.length; i++)
        {
            if (!(BowlEmpty.textureNames[i].equals("")))
            {
                addShapedRecipeFirst(recipes, new ItemStack(bowlEmpty, 4, i), "# #", " # ", '#', new ItemStack(planks, 1, i));
                GameRegistry.addShapelessRecipe(new ItemStack(bowlStew, 1, i + 1), new ItemStack(bowlEmpty, 1, i), new ItemStack(Block.mushroomBrown), new ItemStack(Block.mushroomRed));
                GameRegistry.addShapelessRecipe(new ItemStack(bowlStew, 1, i + 15), new ItemStack(bowlEmpty, 1, i), new ItemStack(glowshroom, 1, 0), new ItemStack(glowshroom, 1, 1), new ItemStack(
                        glowshroom, 1, 2));
            }
        }

        addShapelessRecipeFirst(recipes, new ItemStack(bowlStew, 1, 0), new ItemStack(Block.mushroomBrown), new ItemStack(Block.mushroomRed), new ItemStack(Item.bowlEmpty));
        GameRegistry.addShapelessRecipe(new ItemStack(bowlStew, 1, 14), new ItemStack(glowshroom, 1, 0), new ItemStack(glowshroom, 1, 1), new ItemStack(glowshroom, 1, 2),
                new ItemStack(Item.bowlEmpty));

        /*bowlEmpty = new BowlEmpty(PHNatura.bowlEmpty).setUnlocalizedName("natura.emptybowl");
        bowlStew = new BowlStew(PHNatura.bowlStew).setUnlocalizedName("natura.stewbowl");*/

        //Turn logs into charcoal
        FurnaceRecipes.smelting().addSmelting(tree.blockID, 0, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(tree.blockID, 1, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(tree.blockID, 2, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(tree.blockID, 3, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(redwood.blockID, 0, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(redwood.blockID, 1, new ItemStack(Item.coal, 1, 1), 0.15f);
        FurnaceRecipes.smelting().addSmelting(redwood.blockID, 2, new ItemStack(Item.coal, 1, 1), 0.15f);

        GameRegistry.addRecipe(new ItemStack(grassBlock, 1, 0), " s ", "s#s", " s ", 's', new ItemStack(Item.seeds), '#', new ItemStack(Block.dirt));
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

    public void intermodCommunication ()
    {
        //Thaumcraft
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(berryBush, 1, 12));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(berryBush, 1, 13));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(berryBush, 1, 14));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(berryBush, 1, 15));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(netherBerryBush, 1, 12));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(netherBerryBush, 1, 13));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(netherBerryBush, 1, 14));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(netherBerryBush, 1, 15));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestClickableCrop", new ItemStack(crops, 1, 8));

        //Forestry
        StringBuilder builder = new StringBuilder();
        String string = builder.append("farmWheat@").append(seeds.itemID).append(".0.").append(crops.blockID).append(".3").toString();
        FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", string);
        builder = new StringBuilder();
        string = builder.append("farmWheat@").append(seeds.itemID).append(".1.").append(crops.blockID).append(".8").toString();
        FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", string);

        //TreeCapitator
        if (Loader.isModLoaded("TreeCapitator"))
        {
            NBTTagCompound tpModCfg = new NBTTagCompound();
            tpModCfg.setString("modID", "Natura");
            tpModCfg.setString("axeIDList", String.format("%d; %d; %d; %d; %d", ghostwoodAxe.itemID, bloodwoodAxe.itemID, darkwoodAxe.itemID, fusewoodAxe.itemID, netherquartzAxe.itemID));
            tpModCfg.setBoolean("useShiftedItemID", false);

            NBTTagList treeList = new NBTTagList();

            // amaranth
            NBTTagCompound treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "amaranth");
            treeDef.setString("logs", String.format("%d,2; %d,6; %d,10", rareTree.blockID, rareTree.blockID, rareTree.blockID));
            treeDef.setString("leaves", String.format("%d,2; %d,10", rareLeaves.blockID, rareLeaves.blockID));
            treeList.appendTag(treeDef);
            // bloodwood
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "bloodwood");
            treeDef.setString("logs", String.format("%d", bloodwood.blockID));
            treeDef.setString("leaves", String.format("%d,2; %d,10", floraLeavesNoColor.blockID, floraLeavesNoColor.blockID));
            treeList.appendTag(treeDef);
            // darkwood
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "darkwood");
            treeDef.setString("logs", String.format("%d,0; %d,4; %d,8", darkTree.blockID, darkTree.blockID, darkTree.blockID));
            treeDef.setString("leaves", String.format("%d", darkLeaves.blockID));
            treeList.appendTag(treeDef);
            // eucalyptus
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "eucalyptus");
            treeDef.setString("logs", String.format("%d,0; %d,4; %d,8", tree.blockID, tree.blockID, tree.blockID));
            treeDef.setString("leaves", String.format("%d,1; %d,9", floraLeaves.blockID, floraLeaves.blockID));
            treeList.appendTag(treeDef);
            // ghostwood
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "ghostwood");
            treeDef.setString("logs", String.format("%d,2; %d,6; %d,10", tree.blockID, tree.blockID, tree.blockID));
            treeDef.setString("leaves", String.format("%d,1; %d,9", floraLeavesNoColor.blockID, floraLeavesNoColor.blockID));
            treeList.appendTag(treeDef);
            // hopseed
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "hopseed");
            treeDef.setString("logs", String.format("%d,3; %d,7; %d,11", tree.blockID, tree.blockID, tree.blockID));
            treeDef.setString("leaves", String.format("%d,2; %d,10", floraLeaves.blockID, floraLeaves.blockID));
            treeList.appendTag(treeDef);
            // maple
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "maple");
            treeDef.setString("logs", String.format("%d,0; %d,4; %d,8", rareTree.blockID, rareTree.blockID, rareTree.blockID));
            treeDef.setString("leaves", String.format("%d,0; %d,8", rareLeaves.blockID, rareLeaves.blockID));
            treeList.appendTag(treeDef);
            // redwood
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "redwood");
            treeDef.setString("logs", String.format("%d", redwood.blockID));
            treeDef.setString("leaves", String.format("%d,0; %d,8", floraLeaves.blockID, floraLeaves.blockID));
            treeList.appendTag(treeDef);
            // sakura
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "sakura");
            treeDef.setString("logs", String.format("%d,1; %d,5; %d,9", tree.blockID, tree.blockID, tree.blockID));
            treeDef.setString("leaves", String.format("%d,0; %d,8", floraLeavesNoColor.blockID, floraLeavesNoColor.blockID));
            treeList.appendTag(treeDef);
            // siverbell
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "siverbell");
            treeDef.setString("logs", String.format("%d,1; %d,5; %d,9", rareTree.blockID, rareTree.blockID, rareTree.blockID));
            treeDef.setString("leaves", String.format("%d,1; %d,9", rareLeaves.blockID, rareLeaves.blockID));
            treeList.appendTag(treeDef);
            // tigerwood
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "tigerwood");
            treeDef.setString("logs", String.format("%d,3; %d,7; %d,11", rareTree.blockID, rareTree.blockID, rareTree.blockID));
            treeDef.setString("leaves", String.format("%d,3; %d,11", rareLeaves.blockID, rareLeaves.blockID));
            treeList.appendTag(treeDef);
            // willow
            treeDef = new NBTTagCompound();
            treeDef.setString("treeName", "willow");
            treeDef.setString("logs", String.format("%d", willow.blockID));
            treeDef.setString("leaves", String.format("%d,3; %d,7; %d,11; %d,15", floraLeavesNoColor.blockID, floraLeavesNoColor.blockID, floraLeavesNoColor.blockID, floraLeavesNoColor.blockID));
            treeDef.setInteger("maxHorLeafBreakDist", 5);
            treeList.appendTag(treeDef);

            tpModCfg.setTag("trees", treeList);

            FMLInterModComms.sendMessage("TreeCapitator", "ThirdPartyModConfig", tpModCfg);
        }
    }

    public void postIntermodCommunication ()
    {
        //Buildcraft
        for (int i = 0; i < 13; i++)
        {
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", planks.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", alternateWorkbench.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", alternateBookshelf.blockID + "@" + i);
        }
        for (int i = 0; i < 4; i++)
        {
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", tree.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", rareTree.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", rareLeaves.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", darkLeaves.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", cloud.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", berryBush.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", netherBerryBush.blockID + "@" + i);
        }
        for (int i = 0; i < 3; i++)
        {
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", redwood.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", floraLeaves.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", floraLeavesNoColor.blockID + "@" + i);
            FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", taintedSoil.blockID + "@" + i);
        }

        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", willow.blockID + "@" + 0);
        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", bloodwood.blockID + "@" + 15);
        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", saguaro.blockID + "@" + 0);
        FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", heatSand.blockID + "@" + 0);
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
        OreDictionary.registerOre("saplingTree", new ItemStack(floraSapling, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("saplingTree", new ItemStack(rareSapling, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("leavesTree", new ItemStack(floraLeaves, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("leavesTree", new ItemStack(floraLeavesNoColor, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("leavesTree", new ItemStack(rareLeaves, 1, Short.MAX_VALUE));
        OreDictionary.registerOre("leavesTree", new ItemStack(darkLeaves, 1, Short.MAX_VALUE));
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

        try
        {
            Class.forName("thaumcraft.api.ThaumcraftApi");

            /* Registering seeds */
            AspectList seedTags = new AspectList();
            seedTags.add(Aspect.PLANT, 1);
            seedTags.add(Aspect.EXCHANGE, 1);
            ThaumcraftApi.registerObjectTag(seeds.itemID, 0, seedTags);
            ThaumcraftApi.registerObjectTag(seeds.itemID, 1, seedTags);

            /* Registering plants */
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 0, new AspectList().add(Aspect.LIFE, 2).add(Aspect.CROP, 2));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 3, new AspectList().add(Aspect.CLOTH, 1));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 4, new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 1));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 5, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 6, new AspectList().add(Aspect.BEAST, 1).add(Aspect.FLESH, 1).add(Aspect.CLOTH, 1).add(Aspect.ARMOR, 1).add(Aspect.FIRE, 1));
            ThaumcraftApi.registerObjectTag(plantItem.itemID, 7, new AspectList().add(Aspect.BEAST, 1).add(Aspect.CLOTH, 1).add(Aspect.TRAP, 1).add(Aspect.FIRE, 1));

            /* Registering wood */
            AspectList logTags = new AspectList();
            logTags.add(Aspect.TREE, 4);
            ThaumcraftApi.registerObjectTag(tree.blockID, 0, logTags);
            ThaumcraftApi.registerObjectTag(tree.blockID, 1, logTags);
            ThaumcraftApi.registerObjectTag(tree.blockID, 3, logTags);
            ThaumcraftApi.registerObjectTag(willow.blockID, 0, logTags);
            ThaumcraftApi.registerObjectTag(willow.blockID, 1, logTags);
            ThaumcraftApi.registerObjectTag(redwood.blockID, 0, new AspectList().add(Aspect.ARMOR, 1).add(Aspect.TREE, 3));
            ThaumcraftApi.registerObjectTag(redwood.blockID, 1, logTags);
            ThaumcraftApi.registerObjectTag(redwood.blockID, 2, new AspectList().add(Aspect.EARTH, 1).add(Aspect.TREE, 3));

            /* Leafy goodness */
            AspectList leafTags = new AspectList();
            leafTags.add(Aspect.PLANT, 2);
            ThaumcraftApi.registerObjectTag(floraLeavesNoColor.blockID, 0, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeavesNoColor.blockID, 1, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeavesNoColor.blockID, 2, new AspectList().add(Aspect.TREE, 2).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(floraLeaves.blockID, 0, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeaves.blockID, 1, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeaves.blockID, 2, leafTags);
            ThaumcraftApi.registerObjectTag(floraLeaves.blockID, 3, leafTags);

            /* And rare trees, too. */
            for (int i = 0; i < 4; i++)
            {
                ThaumcraftApi.registerObjectTag(rareTree.blockID, i, logTags);
                ThaumcraftApi.registerObjectTag(rareLeaves.blockID, i, leafTags);
            }

            /* Add mushrooms */
            AspectList shroomTag = new AspectList();
            shroomTag.add(Aspect.PLANT, 4);
            shroomTag.add(Aspect.LIGHT, 1);
            shroomTag.add(Aspect.SOUL, 1);
            for (int i = 0; i < 3; i++)
            {
                ThaumcraftApi.registerObjectTag(glowshroom.blockID, i, shroomTag);
            }

            /* Adding berries! */
            AspectList berryTag = new AspectList();
            berryTag.add(Aspect.LIFE, 1);
            berryTag.add(Aspect.CROP, 1);
            for (int i = 0; i < 4; i++)
            {
                ThaumcraftApi.registerObjectTag(berryItem.itemID, i, berryTag);
            }

            /* Adding berry bushes */
            AspectList berryBushTag = new AspectList();
            berryBushTag.add(Aspect.PLANT, 1);
            for (int i = 0; i < 4; i++)
            {
                ThaumcraftApi.registerObjectTag(berryBush.blockID, i, berryBushTag);
            }

            /* Adding bowls, bowls of stew, and other bowl-based goodies! */
            AspectList bowlEmptyTag = new AspectList();
             bowlEmptyTag.add(Aspect.VOID, 1);
             AspectList bowlStewTag = new AspectList();
             bowlStewTag.add(Aspect.PLANT, 6);
             bowlStewTag.add(Aspect.AIR, 1);
             bowlStewTag.add(Aspect.LIFE, 4);
             AspectList glowStewTag = new AspectList();
             glowStewTag.add(Aspect.PLANT, 8);
             glowStewTag.add(Aspect.AIR, 1);
             glowStewTag.add(Aspect.LIFE, 4);
             glowStewTag.add(Aspect.LIGHT, 4);

             for (int i = 0; i < 13; i++)
             {
                 ThaumcraftApi.registerObjectTag(bowlEmpty.itemID, i, bowlEmptyTag);
                 ThaumcraftApi.registerObjectTag(bowlStew.itemID, i, bowlStewTag);
                 ThaumcraftApi.registerObjectTag(bowlStew.itemID, i + 13, glowStewTag);
             }

            /* Adding other overworld saplings */
            AspectList saplingTag = new AspectList();
            saplingTag.add(Aspect.TREE, 2);
            saplingTag.add(Aspect.PLANT, 2);
            ThaumcraftApi.registerObjectTag(rareSapling.blockID, 4, saplingTag);
            for (int i = 0; i < 4; i++)
            {
                ThaumcraftApi.registerObjectTag(floraSapling.blockID, i, saplingTag);
                ThaumcraftApi.registerObjectTag(rareSapling.blockID, i, saplingTag);
            }

            /* Cactus Stuff */
            AspectList cactusTag = new AspectList();
            cactusTag.add(Aspect.WATER, 1);
            cactusTag.add(Aspect.PLANT, 2);
            cactusTag.add(Aspect.WEAPON, 1);
            cactusTag.add(Aspect.TREE, 2);

            ThaumcraftApi.registerObjectTag(saguaro.blockID, 0, cactusTag);
            ThaumcraftApi.registerObjectTag(waterDrop.itemID, 0, new AspectList().add(Aspect.WATER, 1));
            ThaumcraftApi.registerObjectTag(seedFood.itemID, 0, new AspectList().add(Aspect.CROP, 2).add(Aspect.PLANT, 1).add(Aspect.WATER, 1));

            /* Overworld Clouds */
            ThaumcraftApi.registerObjectTag(cloud.blockID, 0, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1).add(Aspect.WATER, 1).add(Aspect.WEATHER, 1));

            /* Nether saplings */
            ThaumcraftApi.registerObjectTag(floraSapling.blockID, 4, new AspectList().add(Aspect.SOUL, 1).add(Aspect.PLANT, 2).add(Aspect.TREE, 2));
            ThaumcraftApi.registerObjectTag(floraSapling.blockID, 5, new AspectList().add(Aspect.TREE, 2).add(Aspect.PLANT, 2));
            ThaumcraftApi.registerObjectTag(floraSapling.blockID, 6, new AspectList().add(Aspect.TREE, 2).add(Aspect.PLANT, 2));
            ThaumcraftApi.registerObjectTag(floraSapling.blockID, 7, new AspectList().add(Aspect.TREE, 2).add(Aspect.PLANT, 2).add(Aspect.ENTROPY, 2));

            /* Nether blocks */
            ThaumcraftApi.registerObjectTag(heatSand.blockID, 0, new AspectList().add(Aspect.FIRE, 2).add(Aspect.STONE, 1));
            ThaumcraftApi.registerObjectTag(taintedSoil.blockID, 0, new AspectList().add(Aspect.STONE, 2));

            /* Nether trees and leaves */
            ThaumcraftApi.registerObjectTag(tree.blockID, 2, new AspectList().add(Aspect.TREE, 3).add(Aspect.SOUL, 1));
            ThaumcraftApi.registerObjectTag(planks.blockID, 2, new AspectList().add(Aspect.TREE, 1));
            ThaumcraftApi.registerObjectTag(planks.blockID, 4, new AspectList().add(Aspect.TREE, 1).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(darkTree.blockID, 0, new AspectList().add(Aspect.TREE, 4));
            ThaumcraftApi.registerObjectTag(darkTree.blockID, 1, new AspectList().add(Aspect.TREE, 4).add(Aspect.ENTROPY, 2));
            ThaumcraftApi.registerObjectTag(bloodwood.blockID, 0, new AspectList().add(Aspect.TREE, 2).add(Aspect.ENERGY, 2).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(bloodwood.blockID, 15, new AspectList().add(Aspect.TREE, 2).add(Aspect.ENERGY, 2).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(darkLeaves.blockID, 0, leafTags);
            ThaumcraftApi.registerObjectTag(darkLeaves.blockID, 1, leafTags);
            ThaumcraftApi.registerObjectTag(darkLeaves.blockID, 2, new AspectList().add(Aspect.PLANT, 2).add(Aspect.CROP, 2));
            ThaumcraftApi.registerObjectTag(darkLeaves.blockID, 3, new AspectList().add(Aspect.PLANT, 2).add(Aspect.ENTROPY, 1));

            /*Nether vines and bushes */
            ThaumcraftApi.registerObjectTag(netherBerryBush.blockID, 0, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.POISON, 4).add(Aspect.PLANT, 1));
            ThaumcraftApi.registerObjectTag(netherBerryBush.blockID, 1, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.DARKNESS, 4).add(Aspect.PLANT, 1));
            ThaumcraftApi.registerObjectTag(netherBerryBush.blockID, 2, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.FLIGHT, 4).add(Aspect.PLANT, 1));
            ThaumcraftApi.registerObjectTag(netherBerryBush.blockID, 3, new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.PLANT, 1));
            ThaumcraftApi.registerObjectTag(thornVines.blockID, 0, new AspectList().add(Aspect.FIRE, 1).add(Aspect.PLANT, 1));

            /* Nether and End clouds */
            ThaumcraftApi.registerObjectTag(cloud.blockID, 2, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1).add(Aspect.FIRE, 1).add(Aspect.WEATHER, 1));
            ThaumcraftApi.registerObjectTag(cloud.blockID, 3, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1).add(Aspect.POISON, 1).add(Aspect.WEATHER, 1));
            ThaumcraftApi.registerObjectTag(cloud.blockID, 1, new AspectList().add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1).add(Aspect.ELDRITCH, 1).add(Aspect.WEATHER, 1));

            /*Other nether items/plants */
            ThaumcraftApi.registerObjectTag(potashApple.itemID, 0, new AspectList().add(Aspect.CROP, 2).add(Aspect.POISON, 2));
            ThaumcraftApi.registerObjectTag(netherBerryItem.itemID, 0, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.POISON, 4).add(Aspect.LIFE, 1).add(Aspect.CROP, 1));
            ThaumcraftApi.registerObjectTag(netherBerryItem.itemID, 1, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.DARKNESS, 4).add(Aspect.LIFE, 1).add(Aspect.CROP, 1));
            ThaumcraftApi.registerObjectTag(netherBerryItem.itemID, 2, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.FLIGHT, 4).add(Aspect.LIFE, 1).add(Aspect.CROP, 1));
            ThaumcraftApi.registerObjectTag(netherBerryItem.itemID, 3, new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.LIFE, 1).add(Aspect.CROP, 1));
        }
        catch (Exception e)
        {
            System.out.println("ThaumCraft integration failed.");
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
    //public static Item floraBoat;

    //Nether
    public static Block taintedSoil;
    public static Block heatSand;
    //public static Block infernalStone;
    public static Glowshroom glowshroom;
    public static Block darkTree;
    public static NLeaves darkLeaves;
    public static Block thornVines;

    public static Block glowshroomBlue;
    public static Block glowshroomGreen;
    public static Block glowshroomPurple;

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

    public static ItemStack impHelmetStack;
    public static ItemStack impJerkinStack;
    public static ItemStack impLeggingsStack;
    public static ItemStack impBootsStack;

    //Extra overworld
    public static Block rareTree;
    public static NLeaves rareLeaves;
    public static OverworldSapling rareSapling;
    public static Block bluebells;

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
    public static Block miniDoor;

    public static Block plankSlab1;
    public static Block plankSlab2;

    public static Block logSlab;

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
        if (fuel.itemID == floraSapling.blockID || fuel.itemID == rareSapling.blockID)
            return 100;
        return 0;
    }
}
