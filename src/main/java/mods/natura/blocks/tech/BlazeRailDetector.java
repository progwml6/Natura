package mods.natura.blocks.tech;

import net.minecraft.block.BlockRailDetector;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlazeRailDetector extends BlockRailDetector
{
    public BlazeRailDetector()
    {
        super();
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos)
    {
        return 0.65f;
    }
}
