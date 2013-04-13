package mods.natura.client;

import java.util.EnumSet;

import mods.natura.common.NaturaContent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/* Tick handler, used for rendering berry bushes between fast/fancy graphics */

public class NCropsTickHandler implements ITickHandler
{
	private Minecraft mc;
	
	/* Grabs the minecraft instance */
	public NCropsTickHandler()
	{
		mc = ModLoader.getMinecraftInstance();
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		NaturaContent.berryBush.setGraphicsLevel(Block.leaves.graphicsLevel);
        NaturaContent.netherBerryBush.setGraphicsLevel(Block.leaves.graphicsLevel);
		NaturaContent.floraLeaves.setGraphicsLevel(Block.leaves.graphicsLevel);
		NaturaContent.floraLeavesNoColor.setGraphicsLevel(Block.leaves.graphicsLevel);
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() 
	{
		return null;
	}

}
