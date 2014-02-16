package mods.natura.client;

import cpw.mods.fml.client.FMLClientHandler;
import mods.natura.common.NContent;
import net.minecraft.client.Minecraft;
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
	public void onTick(ClientTickEvent event)
	{
		if (event.phase.equals(Phase.START) && event.type.equals(Type.RENDER))
		{
			NContent.berryBush.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
			NContent.netherBerryBush.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
			NContent.floraLeaves.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
			NContent.floraLeavesNoColor.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
			NContent.darkLeaves.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
			NContent.rareLeaves.setGraphicsLevel(NContent.floraLeaves.getRenderLevel());
		}
	}
}
