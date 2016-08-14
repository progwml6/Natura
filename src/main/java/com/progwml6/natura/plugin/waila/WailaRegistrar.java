package com.progwml6.natura.plugin.waila;

import static com.progwml6.natura.world.NaturaWorld.overworldLeaves;
import static com.progwml6.natura.world.NaturaWorld.overworldLog;

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
            registrar.registerStackProvider(blockDataProvider, overworldLog.getClass());
        }

    }
}
