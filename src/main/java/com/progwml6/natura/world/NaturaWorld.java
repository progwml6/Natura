package com.progwml6.natura.world;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.block.BlockCloud;
import com.progwml6.natura.world.block.leaves.BlockOverworldLeaves;
import com.progwml6.natura.world.block.logs.BlockOverworldLog;
import com.progwml6.natura.world.block.logs.BlockRedwoodLog;
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

    public static BlockCloud cloudBlock;

    public static BlockOverworldLog overworldLog;

    public static BlockOverworldLeaves overworldLeaves;

    public static BlockRedwoodLog redwoodLog;

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        cloudBlock = registerEnumBlock(new BlockCloud(), "clouds");
        overworldLog = registerEnumBlock(new BlockOverworldLog(), "overworld_logs");
        redwoodLog = registerEnumBlock(new BlockRedwoodLog(), "redwood_logs");
        overworldLeaves = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves()), "overworld_leaves");
        ItemBlockMeta.setMappingProperty(overworldLeaves, BlockOverworldLog.TYPE);

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
