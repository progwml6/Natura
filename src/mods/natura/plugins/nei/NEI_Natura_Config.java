package mods.natura.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.FMLLog;
import mods.natura.gui.WorkbenchGui;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.awt.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 3/11/13
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class NEI_Natura_Config implements IConfigureNEI
{

    @Override
    public void loadConfig()
    {
        API.registerGuiOverlay(WorkbenchGui.class, "crafting");
        API.registerGuiOverlayHandler(WorkbenchGui.class, new DefaultOverlayHandler(), "crafting");
        TemplateRecipeHandler.RecipeTransferRectHandler.registerRectsToGuis(
                Arrays.<Class<? extends GuiContainer>>asList(WorkbenchGui.class),
                Arrays.asList(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84, 23, 24, 18), "crafting")));
        FMLLog.getLogger().info("Natura NEI plugin loaded");
    }

    @Override
    public String getName()
    {
        return "Natura Plugin";
    }

    @Override
    public String getVersion()
    {
        return "0.1";
    }
}
