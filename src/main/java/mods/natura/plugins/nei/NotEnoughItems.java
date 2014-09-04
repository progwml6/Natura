package mods.natura.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.Side;
import mods.natura.gui.WorkbenchGui;

public class NotEnoughItems
{

    public static void registerNEICompat ()
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            API.registerGuiOverlay(WorkbenchGui.class, "crafting");
            API.registerGuiOverlayHandler(WorkbenchGui.class, new DefaultOverlayHandler(), "crafting");
        }
    }

}
