package com.progwml6.natura.world;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.worldgen.CloudGenerator;
import com.progwml6.natura.world.worldgen.CropGenerator;
import com.progwml6.natura.world.worldgen.GlowshroomGenerator;
import com.progwml6.natura.world.worldgen.NetherBerryBushesGenerator;
import com.progwml6.natura.world.worldgen.NetherMinableGenerator;
import com.progwml6.natura.world.worldgen.NetherTreesGenerator;
import com.progwml6.natura.world.worldgen.OverworldBerryBushesGenerator;
import com.progwml6.natura.world.worldgen.OverworldTreesGenerator;
import com.progwml6.natura.world.worldgen.VineGenerator;
import com.progwml6.natura.world.worldgen.retrogen.TickHandlerWorldRetrogen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaWorld.PulseId, description = "Everything that's found in the world and worldgen including the netherite dimension")
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
        if (isOverworldLoaded())
        {
            GameRegistry.registerWorldGenerator(OverworldTreesGenerator.INSTANCE, 0);
            GameRegistry.registerWorldGenerator(OverworldBerryBushesGenerator.INSTANCE, 0);

            if (Config.enableCloudBlocks)
            {
                GameRegistry.registerWorldGenerator(CloudGenerator.INSTANCE, 0);
            }

            GameRegistry.registerWorldGenerator(CropGenerator.INSTANCE, 0);
        }

        if (isNetherLoaded())
        {
            GameRegistry.registerWorldGenerator(NetherTreesGenerator.INSTANCE, 0);
            GameRegistry.registerWorldGenerator(NetherBerryBushesGenerator.INSTANCE, 0);

            GameRegistry.registerWorldGenerator(GlowshroomGenerator.INSTANCE, 0);
            GameRegistry.registerWorldGenerator(VineGenerator.INSTANCE, 0);

            GameRegistry.registerWorldGenerator(NetherMinableGenerator.INSTANCE, 0);
        }

        MinecraftForge.EVENT_BUS.register(TickHandlerWorldRetrogen.INSTANCE);

        proxy.postInit();
    }
}
