package mods.natura.blocks.tech;

import net.minecraft.block.BlockDetectorRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class BlazeRailDetector extends BlockDetectorRail
{
    public BlazeRailDetector(int par1)
    {
        super(par1);
    }

    @Override
    public float getRailMaxSpeed (World world, EntityMinecart cart, int y, int x, int z)
    {
        return 0.65f;
    }
}
