package mods.natura.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import mods.natura.Natura;
import mods.natura.gui.WorkbenchGui;

@Pulse(id = "Natura NEI Compatibility", modsRequired = NotEnoughItems.modId)
public class NotEnoughItems
{
    public static final String modId = "NotEnoughItems";

    @Handler
    public void preInit (FMLPreInitializationEvent evt)
    {
        // Nothing
    }

    @Handler
    public void init (FMLInitializationEvent evt)
    {
        if (FMLCommonHandler.instance().getSide().isServer())
            return;

        try
        {
            Natura.logger.debug("[NEI] Registering Natura NEI plugin.");
            registerNEICompat();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Handler
    public void postInit (FMLPostInitializationEvent evt)
    {

    }

    public static void registerNEICompat ()
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            API.registerGuiOverlay(WorkbenchGui.class, "crafting");
            API.registerGuiOverlayHandler(WorkbenchGui.class, new DefaultOverlayHandler(), "crafting");
        }
    }

}
