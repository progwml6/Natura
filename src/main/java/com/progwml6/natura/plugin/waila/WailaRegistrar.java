package com.progwml6.natura.plugin.waila;

import static com.progwml6.natura.world.NaturaWorld.overworldLeaves;
import static com.progwml6.natura.world.NaturaWorld.overworldLeaves2;
import static com.progwml6.natura.world.NaturaWorld.overworldLog;
import static com.progwml6.natura.world.NaturaWorld.overworldLog2;

import com.progwml6.natura.common.NaturaPulse;

import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaRegistrar extends NaturaPulse
{
    public static void wailaCallback(IWailaRegistrar registrar)
    {
        BlocksDataProvider blockDataProvider = new BlocksDataProvider();
        if (isWorldLoaded())
        {
            registrar.registerStackProvider(blockDataProvider, overworldLeaves.getClass());
            registrar.registerStackProvider(blockDataProvider, overworldLeaves2.getClass());
            registrar.registerStackProvider(blockDataProvider, overworldLog.getClass());
            registrar.registerStackProvider(blockDataProvider, overworldLog2.getClass());
        }

    }
}
