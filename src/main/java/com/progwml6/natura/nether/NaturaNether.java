package com.progwml6.natura.nether;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.item.ItemBlockLeaves;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.block.glass.BlockNetherGlass;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;
import com.progwml6.natura.nether.block.sand.BlockHeatSand;
import com.progwml6.natura.nether.block.saplings.BlockNetherSapling;
import com.progwml6.natura.nether.block.slabs.BlockNetherSlab;
import com.progwml6.natura.nether.block.soil.BlockTaintedSoil;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    public static BlockNetherLeaves netherLeaves;
    public static BlockNetherLeaves2 netherLeaves2;
    public static BlockNetherSapling netherSapling;
    public static BlockNetherPlanks netherPlanks;
    public static Block netherSlab;

    public static BlockHeatSand netherHeatSand;
    public static BlockTaintedSoil netherTaintedSoil;

    public static BlockNetherGlass netherGlass;

    public static Block netherStairsGhostwood;
    public static Block netherStairsBloodwood;
    public static Block netherStairsDarkwood;
    public static Block netherStairsFusewood;
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        netherLog = registerEnumBlock(new BlockNetherLog(), "nether_logs");

        netherLeaves = registerBlock(new ItemBlockLeaves(new BlockNetherLeaves()), "nether_leaves");
        ItemBlockMeta.setMappingProperty(netherLeaves, BlockNetherLeaves.TYPE);
        netherLeaves2 = registerBlock(new ItemBlockLeaves(new BlockNetherLeaves2()), "nether_leaves2");
        ItemBlockMeta.setMappingProperty(netherLeaves2, BlockNetherLeaves2.TYPE);

        netherSapling = registerBlock(new BlockNetherSapling(), "nether_sapling", BlockNetherSapling.FOLIAGE);

        netherPlanks = registerEnumBlock(new BlockNetherPlanks(), "nether_planks");

        netherSlab = registerEnumBlockSlab(new BlockNetherSlab(), "nether_slab");

        netherHeatSand = registerBlock(new BlockHeatSand(), "nether_heat_sand");
        netherTaintedSoil = registerEnumBlock(new BlockTaintedSoil(), "nether_tainted_soil");

        netherGlass = registerEnumBlock(new BlockNetherGlass(), "nether_glass");

        netherStairsGhostwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.GHOSTWOOD, "nether_stairs_ghostwood");
        netherStairsBloodwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.BLOODWOOD, "nether_stairs_bloodwood");
        netherStairsDarkwood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.DARKWOOD, "nether_stairs_darkwood");
        netherStairsFusewood = registerBlockStairsFrom(netherPlanks, BlockNetherPlanks.PlankType.FUSEWOOD, "nether_stairs_fusewood");

        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
