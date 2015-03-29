package mods.natura.blocks.tech;

import mods.natura.common.NContent;
import net.minecraft.block.BlockHopper;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlazeHopper extends BlockHopper
{

	public BlazeHopper()
	{
		super();
	}

	@Override
	public int getRenderType()
	{
		return 3;
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}
}
