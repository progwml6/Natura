package mods.natura.client;

import net.minecraft.client.Minecraft;
import mods.natura.common.NContent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
