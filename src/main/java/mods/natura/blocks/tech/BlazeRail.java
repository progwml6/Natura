package mods.natura.blocks.tech;

import net.minecraft.block.BlockRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlazeRail extends BlockRail
{
	public BlazeRail()
	{
		super();
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos)
	{
		return 0.65f;
	}
}
