package mods.natura.blocks.tech;

import net.minecraft.block.BlockRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class BlazeRail extends BlockRail
{
    public BlazeRail(int par1)
    {
        super(par1);
    }
    
    @Override
    public float getRailMaxSpeed (World world, EntityMinecart cart, int y, int x, int z)
    {
        return 0.65f;
    }
}
