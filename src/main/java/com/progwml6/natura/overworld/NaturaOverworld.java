package com.progwml6.natura.overworld;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.item.ItemBlockLeaves;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.overworld.block.grass.BlockColoredGrass;
import com.progwml6.natura.overworld.block.leaves.BlockOverworldLeaves;
import com.progwml6.natura.overworld.block.leaves.BlockOverworldLeaves2;
import com.progwml6.natura.overworld.block.leaves.BlockRedwoodLeaves;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.overworld.block.logs.BlockRedwoodLog;
import com.progwml6.natura.overworld.block.planks.BlockOverworldPlanks;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling2;
import com.progwml6.natura.overworld.block.saplings.BlockRedwoodSapling;
import com.progwml6.natura.overworld.block.slabs.BlockColoredGrassSlab;
import com.progwml6.natura.overworld.block.slabs.BlockOverworldSlab;
import com.progwml6.natura.overworld.block.slabs.BlockOverworldSlab2;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaOverworld.PulseId, description = "All of the overworld blocks including trees")
public class NaturaOverworld extends NaturaPulse
{
    public static final String PulseId = "NaturaOverworld";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.overworld.OverworldClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    public static BlockColoredGrass coloredGrass;
    public static BlockColoredGrassSlab coloredGrassSlab;

    public static BlockOverworldLog overworldLog;
    public static BlockOverworldLeaves overworldLeaves;
    public static BlockOverworldSapling overworldSapling;

    public static BlockOverworldLog2 overworldLog2;
    public static BlockOverworldLeaves2 overworldLeaves2;
    public static BlockOverworldSapling2 overworldSapling2;

    public static BlockOverworldPlanks overworldPlanks;

    public static BlockRedwoodLog redwoodLog;
    public static BlockRedwoodSapling redwoodSapling; //TODO: FIX REDWOOD
    public static BlockRedwoodLeaves redwoodLeaves;

    public static Block overworldSlab;
    public static Block overworldSlab2;

    public static Block overworldStairsMaple;
    public static Block overworldStairsSilverbell;
    public static Block overworldStairsAmaranth;
    public static Block overworldStairsTiger;
    public static Block overworldStairsWillow;
    public static Block overworldStairsEucalyptus;
    public static Block overworldStairsHopseed;
    public static Block overworldStairsSakura;
    public static Block overworldStairsRedwood;
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        coloredGrass = registerEnumBlock(new BlockColoredGrass(), "colored_grass");
        coloredGrassSlab = registerEnumBlockSlab(new BlockColoredGrassSlab(), "colored_grass_slab");

        overworldLog = registerEnumBlock(new BlockOverworldLog(), "overworld_logs");
        overworldLog2 = registerEnumBlock(new BlockOverworldLog2(), "overworld_logs2");
        redwoodLog = registerEnumBlock(new BlockRedwoodLog(), "redwood_logs");

        overworldLeaves = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves()), "overworld_leaves");
        ItemBlockMeta.setMappingProperty(overworldLeaves, BlockOverworldLog.TYPE);
        overworldLeaves2 = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves2()), "overworld_leaves2");
        ItemBlockMeta.setMappingProperty(overworldLeaves2, BlockOverworldLog2.TYPE);
        redwoodLeaves = registerBlock(new ItemBlockLeaves(new BlockRedwoodLeaves()), "redwood_leaves");
        ItemBlockMeta.setMappingProperty(redwoodLeaves, BlockRedwoodLeaves.TYPE);

        overworldSapling = registerBlock(new BlockOverworldSapling(), "overworld_sapling", BlockOverworldSapling.FOLIAGE);
        overworldSapling2 = registerBlock(new BlockOverworldSapling2(), "overworld_sapling2", BlockOverworldSapling2.FOLIAGE);
        redwoodSapling = registerBlock(new BlockRedwoodSapling(), "redwood_sapling", BlockRedwoodSapling.FOLIAGE);// TODO: FIX REDWOOD

        overworldPlanks = registerEnumBlock(new BlockOverworldPlanks(), "overworld_planks");

        overworldSlab = registerEnumBlockSlab(new BlockOverworldSlab(), "overworld_slab");
        overworldSlab2 = registerEnumBlockSlab(new BlockOverworldSlab2(), "overworld_slab2");

        overworldStairsMaple = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.MAPLE, "overworld_stairs_maple");
        overworldStairsSilverbell = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.SILVERBELL, "overworld_stairs_silverbell");
        overworldStairsAmaranth = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.AMARANTH, "overworld_stairs_amaranth");
        overworldStairsTiger = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.TIGER, "overworld_stairs_tiger");
        overworldStairsWillow = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.WILLOW, "overworld_stairs_willow");
        overworldStairsEucalyptus = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.EUCALYPTUS, "overworld_stairs_eucalyptus");
        overworldStairsHopseed = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.HOPSEED, "overworld_stairs_hopseed");
        overworldStairsSakura = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.SAKURA, "overworld_stairs_sakura");
        overworldStairsRedwood = registerBlockStairsFrom(overworldPlanks, BlockOverworldPlanks.PlankType.REDWOOD, "overworld_stairs_redwood");

        proxy.preInit();

        NaturaRegistry.tabWorld.setDisplayIcon(new ItemStack(coloredGrass));
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
