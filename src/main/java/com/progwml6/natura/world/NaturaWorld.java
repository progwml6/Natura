package com.progwml6.natura.world;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.block.grass.BlockColoredGrass;
import com.progwml6.natura.world.block.grass.BlockColoredGrassSlab;
import com.progwml6.natura.world.block.leaves.nether.BlockNetherLeaves;
import com.progwml6.natura.world.block.leaves.nether.BlockNetherLeaves2;
import com.progwml6.natura.world.block.leaves.overworld.BlockOverworldLeaves;
import com.progwml6.natura.world.block.leaves.overworld.BlockOverworldLeaves2;
import com.progwml6.natura.world.block.leaves.overworld.BlockRedwoodLeaves;
import com.progwml6.natura.world.block.logs.nether.BlockNetherLog;
import com.progwml6.natura.world.block.logs.overworld.BlockOverworldLog;
import com.progwml6.natura.world.block.logs.overworld.BlockOverworldLog2;
import com.progwml6.natura.world.block.logs.overworld.BlockRedwoodLog;
import com.progwml6.natura.world.block.planks.nether.BlockNetherPlanks;
import com.progwml6.natura.world.block.planks.overworld.BlockOverworldPlanks;
import com.progwml6.natura.world.block.saplings.nether.BlockNetherSapling;
import com.progwml6.natura.world.block.saplings.overworld.BlockOverworldSapling;
import com.progwml6.natura.world.block.saplings.overworld.BlockOverworldSapling2;
import com.progwml6.natura.world.block.saplings.overworld.BlockRedwoodSapling;
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

    public static BlockNetherLog netherLog;
    public static BlockNetherLeaves netherLeaves;
    public static BlockNetherLeaves2 netherLeaves2;
    public static BlockNetherSapling netherSapling;
    public static BlockNetherPlanks netherPlanks;
    //@formatter:on

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        coloredGrass = registerEnumBlock(new BlockColoredGrass(), "colored_grass");
        coloredGrassSlab = registerEnumBlockSlab(new BlockColoredGrassSlab(), "colored_grass_slab");

        overworldLog = registerEnumBlock(new BlockOverworldLog(), "overworld_logs");
        overworldLog2 = registerEnumBlock(new BlockOverworldLog2(), "overworld_logs2");
        redwoodLog = registerEnumBlock(new BlockRedwoodLog(), "redwood_logs");
        netherLog = registerEnumBlock(new BlockNetherLog(), "nether_logs");

        overworldLeaves = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves()), "overworld_leaves");
        ItemBlockMeta.setMappingProperty(overworldLeaves, BlockOverworldLog.TYPE);
        overworldLeaves2 = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves2()), "overworld_leaves2");
        ItemBlockMeta.setMappingProperty(overworldLeaves2, BlockOverworldLog2.TYPE);
        redwoodLeaves = registerBlock(new ItemBlockLeaves(new BlockRedwoodLeaves()), "redwood_leaves");
        ItemBlockMeta.setMappingProperty(redwoodLeaves, BlockRedwoodLeaves.TYPE);
        netherLeaves = registerBlock(new ItemBlockLeaves(new BlockNetherLeaves()), "nether_leaves");
        ItemBlockMeta.setMappingProperty(netherLeaves, BlockNetherLeaves.TYPE);
        netherLeaves2 = registerBlock(new ItemBlockLeaves(new BlockNetherLeaves2()), "nether_leaves2");
        ItemBlockMeta.setMappingProperty(netherLeaves2, BlockNetherLeaves2.TYPE);

        overworldSapling = registerBlock(new BlockOverworldSapling(), "overworld_sapling", BlockOverworldSapling.FOLIAGE);
        overworldSapling2 = registerBlock(new BlockOverworldSapling2(), "overworld_sapling2", BlockOverworldSapling2.FOLIAGE);
        redwoodSapling = registerBlock(new BlockRedwoodSapling(), "redwood_sapling", BlockRedwoodSapling.FOLIAGE);// TODO: FIX REDWOOD
        netherSapling = registerBlock(new BlockNetherSapling(), "nether_sapling", BlockNetherSapling.FOLIAGE);

        overworldPlanks = registerEnumBlock(new BlockOverworldPlanks(), "overworld_planks");
        netherPlanks = registerEnumBlock(new BlockNetherPlanks(), "nether_planks");

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
        MinecraftForge.EVENT_BUS.register(new WorldEvents());

        proxy.postInit();
    }
}
