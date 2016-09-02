package com.progwml6.natura.world;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.Util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaWorld.PulseId, description = "Everything that's found in the world and worldgen")
public class NaturaWorld extends NaturaPulse
{
    public static final String PulseId = "NaturaWorld";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.world.WorldClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
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
        MinecraftForge.EVENT_BUS.register(new WorldEvents());

        proxy.postInit();
    }
}
