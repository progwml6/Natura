package com.progwml6.natura.world;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.block.clouds.BlockCloud;
import com.progwml6.natura.world.block.grass.BlockColoredGrass;
import com.progwml6.natura.world.block.grass.BlockColoredGrassSlab;
import com.progwml6.natura.world.block.leaves.BlockOverworldLeaves;
import com.progwml6.natura.world.block.leaves.BlockOverworldLeaves2;
import com.progwml6.natura.world.block.logs.BlockOverworldLog;
import com.progwml6.natura.world.block.logs.BlockOverworldLog2;
import com.progwml6.natura.world.block.logs.BlockRedwoodLog;
import com.progwml6.natura.world.block.planks.BlockOverworldPlanks;
import com.progwml6.natura.world.block.saplings.BlockOverworldSapling;
import com.progwml6.natura.world.block.saplings.BlockOverworldSapling2;
import com.progwml6.natura.world.item.ItemBlockLeaves;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaWorld.PulseId, description = "Everything that's found in the world and worldgen")
public class NaturaWorld extends NaturaPulse
{
    public static final String PulseId = "NaturaWorld";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.world.WorldClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    public static BlockCloud cloudBlock;
    public static BlockColoredGrass coloredGrass;
    public static BlockColoredGrassSlab coloredGrassSlab;
    public static BlockOverworldLog overworldLog;
    public static BlockOverworldLog2 overworldLog2;
    public static BlockOverworldLeaves overworldLeaves;
    public static BlockOverworldLeaves2 overworldLeaves2;
    public static BlockOverworldSapling overworldSapling;
    public static BlockOverworldSapling2 overworldSapling2;
    public static BlockOverworldPlanks overworldPlanks;
    public static BlockRedwoodLog redwoodLog;
    //public static BlockRedwoodSapling redwoodSapling; TODO: FIX REDWOOD
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        cloudBlock = registerEnumBlock(new BlockCloud(), "clouds");

        coloredGrass = registerEnumBlock(new BlockColoredGrass(), "colored_grass");
        coloredGrassSlab = registerEnumBlockSlab(new BlockColoredGrassSlab(), "colored_grass_slab");

        overworldLog = registerEnumBlock(new BlockOverworldLog(), "overworld_logs");
        overworldLog2 = registerEnumBlock(new BlockOverworldLog2(), "overworld_logs2");
        redwoodLog = registerEnumBlock(new BlockRedwoodLog(), "redwood_logs");

        overworldLeaves = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves()), "overworld_leaves");
        ItemBlockMeta.setMappingProperty(overworldLeaves, BlockOverworldLog.TYPE);
        overworldLeaves2 = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves2()), "overworld_leaves2");
        ItemBlockMeta.setMappingProperty(overworldLeaves2, BlockOverworldLog2.TYPE);

        overworldSapling = registerBlock(new BlockOverworldSapling(), "overworld_sapling", BlockOverworldSapling.FOLIAGE);
        overworldSapling2 = registerBlock(new BlockOverworldSapling2(), "overworld_sapling2", BlockOverworldSapling2.FOLIAGE);
        //redwoodSapling = registerBlock(new BlockRedwoodSapling(), "redwood_sapling", BlockRedwoodSapling.FOLIAGE); TODO: FIX REDWOOD

        overworldPlanks = registerEnumBlock(new BlockOverworldPlanks(), "overworld_planks");

        proxy.preInit();

        NaturaRegistry.tabWorld.setDisplayIcon(new ItemStack(cloudBlock));
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new WorldEvents());

        proxy.postInit();
    }
}
