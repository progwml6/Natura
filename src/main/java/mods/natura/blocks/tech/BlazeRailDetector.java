package mods.natura.blocks.tech;

import net.minecraft.block.BlockDetectorRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class BlazeRailDetector extends BlockDetectorRail
{
    public BlazeRailDetector()
    {
        super();
    }

    @Override
    public float getRailMaxSpeed (World world, EntityMinecart cart, int y, int x, int z)
    {
        return 0.65f;
    }
}
