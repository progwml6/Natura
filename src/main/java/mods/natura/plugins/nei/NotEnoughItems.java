package mods.natura.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.Side;
import mantle.module.ILoadableModule;
import mods.natura.Natura;
import mods.natura.gui.WorkbenchGui;

public class NotEnoughItems implements ILoadableModule
{
    @SuppressWarnings("unused")
    public static String modId = "NotEnoughItems";

    @Override
    public void preInit ()
    {
        // Nothing
    }

    @Override
    public void init ()
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

    @Override
    public void postInit ()
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
