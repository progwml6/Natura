package com.progwml6.natura.nether;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.block.BlockNaturaDoor;
import com.progwml6.natura.common.item.ItemBlockLeaves;
import com.progwml6.natura.common.item.ItemNaturaDoor;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.block.bush.BlockNetherBerryBush;
import com.progwml6.natura.nether.block.glass.BlockNetherGlass;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.nether.block.logs.BlockNetherLog2;
import com.progwml6.natura.nether.block.obelisk.BlockRespawnObelisk;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;
import com.progwml6.natura.nether.block.sand.BlockHeatSand;
import com.progwml6.natura.nether.block.saplings.BlockNetherSapling;
import com.progwml6.natura.nether.block.saplings.BlockNetherSapling2;
import com.progwml6.natura.nether.block.shrooms.BlockNetherGlowshroom;
import com.progwml6.natura.nether.block.shrooms.BlockNetherLargeGlowshroom;
import com.progwml6.natura.nether.block.slabs.BlockNetherSlab;
import com.progwml6.natura.nether.block.soil.BlockTaintedSoil;
import com.progwml6.natura.nether.block.vine.BlockNetherThornVines;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaNether.PulseId, description = "All of the nether blocks including trees")
public class NaturaNether extends NaturaPulse
{
    public static final String PulseId = "NaturaNether";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.nether.NetherClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    public static BlockNetherLog netherLog;
    public static BlockNetherLog2 netherLog2;
    public static BlockNetherLeaves netherLeaves;
    public static BlockNetherLeaves2 netherLeaves2;
    public static BlockNetherSapling netherSapling;
    public static BlockNetherSapling2 netherSapling2;
    public static BlockNetherPlanks netherPlanks;
    public static Block netherSlab;

    public static BlockHeatSand netherHeatSand;
    public static BlockTaintedSoil netherTaintedSoil;
    public static BlockNetherThornVines netherThornVines;

    public static BlockNetherGlass netherGlass;

    public static Block netherStairsGhostwood;
    public static Block netherStairsBloodwood;
    public static Block netherStairsDarkwood;
    public static Block netherStairsFusewood;

    public static Block netherBerryBushBlightberry;
    public static Block netherBerryBushDuskberry;
    public static Block netherBerryBushSkyberry;
    public static Block netherBerryBushStingberry;

    public static BlockNetherGlowshroom netherGlowshroom;
    public static BlockNetherLargeGlowshroom netherLargeGreenGlowshroom;
    public static BlockNetherLargeGlowshroom netherLargeBlueGlowshroom;
    public static BlockNetherLargeGlowshroom netherLargePurpleGlowshroom;

    public static BlockRespawnObelisk respawnObelisk;

    public static BlockNaturaDoor ghostwoodDoor;
    public static BlockNaturaDoor bloodwoodDoor;

    // Items
    public static ItemNaturaDoor netherDoors;

    public static ItemStack ghostwood_door;
    public static ItemStack bloodwood_door;
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        netherLog = registerEnumBlock(new BlockNetherLog(), "nether_logs");
        netherLog2 = registerBlock(new BlockNetherLog2(), "nether_logs2");

        netherLeaves = registerBlock(new ItemBlockLeaves(new BlockNetherLeaves()), "nether_leaves");
        ItemBlockMeta.setMappingProperty(netherLeaves, BlockNetherLeaves.TYPE);
        netherLeaves2 = registerBlock(new ItemBlockLeaves(new BlockNetherLeaves2()), "nether_leaves2");
        ItemBlockMeta.setMappingProperty(netherLeaves2, BlockNetherLeaves2.TYPE);

        netherSapling = registerBlock(new BlockNetherSapling(), "nether_sapling", BlockNetherSapling.FOLIAGE);
        netherSapling2 = registerBlock(new BlockNetherSapling2(), "nether_sapling2", BlockNetherSapling2.FOLIAGE);

        netherPlanks = registerEnumBlock(new BlockNetherPlanks(), "nether_planks");

        netherSlab = registerEnumBlockSlab(new BlockNetherSlab(), "nether_slab");

        netherHeatSand = registerBlock(new BlockHeatSand(), "nether_heat_sand");
        netherTaintedSoil = registerEnumBlock(new BlockTaintedSoil(), "nether_tainted_soil");
        netherThornVines = registerBlock(new BlockNetherThornVines(), "nether_thorn_vines");

        netherGlass = registerEnumBlock(new BlockNetherGlass(), "nether_glass");

        netherStairsGhostwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.GHOSTWOOD, "nether_stairs_ghostwood");
        netherStairsBloodwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.BLOODWOOD, "nether_stairs_bloodwood");
        netherStairsDarkwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.DARKWOOD, "nether_stairs_darkwood");
        netherStairsFusewood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.FUSEWOOD, "nether_stairs_fusewood");

        netherBerryBushBlightberry = registerBlock(new BlockNetherBerryBush(NaturaCommons.blightberry), "nether_berrybush_blightberry");
        netherBerryBushDuskberry = registerBlock(new BlockNetherBerryBush(NaturaCommons.duskberry), "nether_berrybush_duskberry");
        netherBerryBushSkyberry = registerBlock(new BlockNetherBerryBush(NaturaCommons.skyberry), "nether_berrybush_skyberry");
        netherBerryBushStingberry = registerBlock(new BlockNetherBerryBush(NaturaCommons.stingberry), "nether_berrybush_stingberry");

        respawnObelisk = registerEnumBlock(new BlockRespawnObelisk(), "respawn_obelisk");

        netherGlowshroom = registerBlock(new BlockNetherGlowshroom(), "nether_glowshroom", BlockNetherGlowshroom.TYPE);
        netherLargeGreenGlowshroom = registerBlock(new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.GREEN.getMeta()), "nether_green_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);
        netherLargeBlueGlowshroom = registerBlock(new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.BLUE.getMeta()), "nether_blue_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);
        netherLargePurpleGlowshroom = registerBlock(new BlockNetherLargeGlowshroom(netherGlowshroom, BlockNetherGlowshroom.GlowshroomType.PURPLE.getMeta()), "nether_purple_large_glowshroom", BlockNetherLargeGlowshroom.VARIANT);

        ghostwoodDoor = registerBlock(new BlockNaturaDoor(), "nether_door_ghostwood");
        bloodwoodDoor = registerBlock(new BlockNaturaDoor(), "nether_door_bloodwood");

        netherDoors = registerItem(new ItemNaturaDoor(), "nether_doors");

        netherDoors.setCreativeTab(NaturaRegistry.tabGeneral);

        ghostwood_door = netherDoors.addMeta(0, "ghostwood_door", NaturaNether.ghostwoodDoor.getDefaultState());
        bloodwood_door = netherDoors.addMeta(1, "bloodwood_door", NaturaNether.bloodwoodDoor.getDefaultState());

        ghostwoodDoor.setDoor(NaturaNether.ghostwood_door);
        bloodwoodDoor.setDoor(NaturaNether.bloodwood_door);

        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();

        this.registerRecipes();
        this.registerSmelting();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    private void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(netherPlanks, 4, BlockNetherPlanks.PlankType.DARKWOOD.getMeta()), "w", 'w', new ItemStack(netherLog, 1, BlockNetherLog.LogType.DARKWOOD.getMeta()));
        //TODO enable this when fixed
        //GameRegistry.addRecipe(new ItemStack(netherPlanks, 4, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()), "w", 'w', new ItemStack(netherLog, 1, BlockNetherLog.LogType.BLOODWOOD.getMeta()));
        GameRegistry.addRecipe(new ItemStack(netherPlanks, 4, BlockNetherPlanks.PlankType.FUSEWOOD.getMeta()), "w", 'w', new ItemStack(netherLog, 1, BlockNetherLog.LogType.FUSEWOOD.getMeta()));
        GameRegistry.addRecipe(new ItemStack(netherPlanks, 4, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()), "w", 'w', new ItemStack(netherLog, 1, BlockNetherLog.LogType.GHOSTWOOD.getMeta()));

        //SLABS
        addSlabRecipe(netherSlab, BlockNetherSlab.PlankType.BLOODWOOD.getMeta(), new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()));
        addSlabRecipe(netherSlab, BlockNetherSlab.PlankType.DARKWOOD.getMeta(), new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.DARKWOOD.getMeta()));
        addSlabRecipe(netherSlab, BlockNetherSlab.PlankType.FUSEWOOD.getMeta(), new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.FUSEWOOD.getMeta()));
        addSlabRecipe(netherSlab, BlockNetherSlab.PlankType.GHOSTWOOD.getMeta(), new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()));

        //STAIRS
        addStairRecipe(netherStairsBloodwood, new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.getMeta()));
        addStairRecipe(netherStairsDarkwood, new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.DARKWOOD.getMeta()));
        addStairRecipe(netherStairsFusewood, new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.FUSEWOOD.getMeta()));
        addStairRecipe(netherStairsGhostwood, new ItemStack(netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.getMeta()));

        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.SOUL_SAND), netherHeatSand, netherTaintedSoil);

    }

    private void registerSmelting()
    {
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(Blocks.SOUL_SAND, 1, 0), new ItemStack(netherGlass, 1, 0), 0.3f);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(netherHeatSand, 1, 0), new ItemStack(netherGlass, 1, 1), 0.3f);

    }
}
