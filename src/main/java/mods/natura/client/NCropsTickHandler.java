package mods.natura.client;

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
        NContent.berryBush.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
        NContent.netherBerryBush.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
        NContent.floraLeaves.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
        NContent.floraLeavesNoColor.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
        NContent.darkLeaves.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
        NContent.rareLeaves.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
    }
}
