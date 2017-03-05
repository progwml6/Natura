package com.progwml6.natura.plugin;

import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class PluginWaila implements IWailaPlugin
{
    @Override
    public void register(IWailaRegistrar registrar)
    {
        HUDHandlerNatura.register(registrar);
    }

}
