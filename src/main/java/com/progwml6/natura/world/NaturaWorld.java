package com.progwml6.natura.world;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.worldgen.BerryBushGenerator;
import com.progwml6.natura.world.worldgen.TreeGenerator;
import com.progwml6.natura.world.worldgen.retrogen.TickHandlerWorldRetrogen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
        GameRegistry.registerWorldGenerator(TreeGenerator.INSTANCE, 0);
        GameRegistry.registerWorldGenerator(BerryBushGenerator.INSTANCE, 0);

        //GameRegistry.registerWorldGenerator(TreeGenerator.INSTANCE, 25);
        //GameRegistry.registerWorldGenerator(BerryBushGenerator.INSTANCE, 25);

        MinecraftForge.EVENT_BUS.register(new TickHandlerWorldRetrogen());

        proxy.postInit();
    }
}
