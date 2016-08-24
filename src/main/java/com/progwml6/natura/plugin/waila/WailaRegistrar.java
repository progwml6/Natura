package com.progwml6.natura.plugin.waila;

import static com.progwml6.natura.world.NaturaWorld.netherLeaves;
import static com.progwml6.natura.world.NaturaWorld.netherLeaves2;
import static com.progwml6.natura.world.NaturaWorld.netherLog;
import static com.progwml6.natura.world.NaturaWorld.netherSapling;
import static com.progwml6.natura.world.NaturaWorld.overworldLeaves;
import static com.progwml6.natura.world.NaturaWorld.overworldLeaves2;
import static com.progwml6.natura.world.NaturaWorld.overworldLog;
import static com.progwml6.natura.world.NaturaWorld.overworldLog2;
import static com.progwml6.natura.world.NaturaWorld.overworldSapling;
import static com.progwml6.natura.world.NaturaWorld.overworldSapling2;
import static com.progwml6.natura.world.NaturaWorld.redwoodLeaves;
import static com.progwml6.natura.world.NaturaWorld.redwoodSapling;

import com.progwml6.natura.common.NaturaPulse;

import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaRegistrar extends NaturaPulse
{
    public static void wailaCallback(IWailaRegistrar registrar)
    {
        BlocksDataProvider blockDataProvider = new BlocksDataProvider();

        if (isWorldLoaded())
        {
            registrar.registerStackProvider(blockDataProvider, overworldLog.getClass());
            registrar.registerStackProvider(blockDataProvider, overworldLeaves.getClass());
            registrar.registerStackProvider(blockDataProvider, overworldSapling.getClass());

            registrar.registerStackProvider(blockDataProvider, overworldLog2.getClass());
            registrar.registerStackProvider(blockDataProvider, overworldLeaves2.getClass());
            registrar.registerStackProvider(blockDataProvider, overworldSapling2.getClass());

            registrar.registerStackProvider(blockDataProvider, redwoodLeaves.getClass());
            registrar.registerStackProvider(blockDataProvider, redwoodSapling.getClass());

            registrar.registerStackProvider(blockDataProvider, netherLog.getClass());
            registrar.registerStackProvider(blockDataProvider, netherLeaves.getClass());
            registrar.registerStackProvider(blockDataProvider, netherSapling.getClass());

            registrar.registerStackProvider(blockDataProvider, netherLeaves2.getClass());
        }

    }
}
