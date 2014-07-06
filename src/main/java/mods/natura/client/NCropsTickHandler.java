package mods.natura.client;

import net.minecraft.client.Minecraft;
import mods.natura.common.NContent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/* Tick handler, used for rendering berry bushes between fast/fancy graphics */
public class NCropsTickHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void tickEnd (ClientTickEvent event)
    {
        NContent.berryBush.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
        NContent.netherBerryBush.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
        NContent.floraLeaves.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
        NContent.floraLeavesNoColor.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
        NContent.darkLeaves.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
        NContent.rareLeaves.setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);
    }
}
