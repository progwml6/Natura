package mods.natura.plugins.waila;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mods.natura.blocks.crops.CropBlock;

public class WailaRegistrar
{
    
    public static void wailaCallback (IWailaRegistrar registrar) {
        IWailaDataProvider cropProvider = new NaturaCropDataProvider();
        
        registrar.registerStackProvider(cropProvider, CropBlock.class);
        registrar.registerBodyProvider(cropProvider, CropBlock.class);
    }

}
