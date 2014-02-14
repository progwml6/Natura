package mods.natura.client;

import java.util.EnumSet;

import cpw.mods.fml.client.FMLClientHandler;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;

/* Tick handler, used for rendering berry bushes between fast/fancy graphics */

public class NCropsTickHandler
{
    private Minecraft mc;

    /* Grabs the minecraft instance */
    public NCropsTickHandler()
    {
        mc = FMLClientHandler.instance().getClient();
    }

    @SubscribeEvent
    public void onTick (ClientTickEvent event)
    {

        if (event.phase.equals(Phase.START) && event.type.equals(Type.RENDER))
        NContent.berryBush.setGraphicsLevel(Blocks.leaves.field_150121_P);
        NContent.netherBerryBush.setGraphicsLevel(Blocks.leaves.field_150121_P);
        NContent.floraLeaves.setGraphicsLevel(Blocks.leaves.field_150121_P);
        NContent.floraLeavesNoColor.setGraphicsLevel(Blocks.leaves.field_150121_P);
        NContent.darkLeaves.setGraphicsLevel(Blocks.leaves.field_150121_P);
        NContent.rareLeaves.setGraphicsLevel(Blocks.leaves.field_150121_P);
    }

}
