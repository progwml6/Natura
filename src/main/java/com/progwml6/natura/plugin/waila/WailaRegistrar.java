package com.progwml6.natura.plugin.waila;

import static com.progwml6.natura.nether.NaturaNether.netherLeaves;
import static com.progwml6.natura.nether.NaturaNether.netherLeaves2;
import static com.progwml6.natura.nether.NaturaNether.netherLog;
import static com.progwml6.natura.nether.NaturaNether.netherSapling;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldLeaves;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldLeaves2;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldLog;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldLog2;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldSapling;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldSapling2;
import static com.progwml6.natura.overworld.NaturaOverworld.redwoodLeaves;
import static com.progwml6.natura.overworld.NaturaOverworld.redwoodSapling;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaPulse;

import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaRegistrar extends NaturaPulse
{
    public static void wailaCallback(IWailaRegistrar registrar)
    {
        if (Natura.pulseManager.isPulseLoaded(PluginWaila.PulseId))
        {
            BlocksDataProvider blockDataProvider = new BlocksDataProvider();

            if (isOverworldLoaded())
            {
                registrar.registerStackProvider(blockDataProvider, overworldLog.getClass());
                registrar.registerStackProvider(blockDataProvider, overworldLeaves.getClass());
                registrar.registerStackProvider(blockDataProvider, overworldSapling.getClass());

                registrar.registerStackProvider(blockDataProvider, overworldLog2.getClass());
                registrar.registerStackProvider(blockDataProvider, overworldLeaves2.getClass());
                registrar.registerStackProvider(blockDataProvider, overworldSapling2.getClass());

                registrar.registerStackProvider(blockDataProvider, redwoodLeaves.getClass());
                registrar.registerStackProvider(blockDataProvider, redwoodSapling.getClass());
            }

            if (isNetherLoaded())
            {
                registrar.registerStackProvider(blockDataProvider, netherLog.getClass());

                registrar.registerStackProvider(blockDataProvider, netherLeaves.getClass());
                registrar.registerStackProvider(blockDataProvider, netherLeaves2.getClass());

                registrar.registerStackProvider(blockDataProvider, netherSapling.getClass());
            }
        }
    }
}
