package mods.natura.plugins.nei;

import net.minecraft.item.ItemStack;

import codechicken.nei.api.API;
import codechicken.nei.recipe.DefaultOverlayHandler;
import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.Side;
import mods.natura.common.NContent;
import mods.natura.gui.WorkbenchGui;

public class NotEnoughItems
{

    public static void registerNEICompat ()
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            API.registerGuiOverlay(WorkbenchGui.class, "crafting");
            API.registerGuiOverlayHandler(WorkbenchGui.class, new DefaultOverlayHandler(), "crafting");
			
            API.hideItem(new ItemStack(NContent.crops));
            API.hideItem(new ItemStack(NContent.redwoodDoor));
            API.hideItem(new ItemStack(NContent.eucalyptusDoor));
            API.hideItem(new ItemStack(NContent.hopseedDoor));
            API.hideItem(new ItemStack(NContent.sakuraDoor));
            API.hideItem(new ItemStack(NContent.ghostDoor));
            API.hideItem(new ItemStack(NContent.bloodDoor));
            API.hideItem(new ItemStack(NContent.redwoodBarkDoor));
        }
    }

}
