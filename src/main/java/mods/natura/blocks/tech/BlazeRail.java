package mods.natura.blocks.tech;

import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class BlazeRail extends BlockRail
{
    public BlazeRail()
    {
        super();
        setCreativeTab(NaturaTab.tabNether);
    }
    
    @Override
    public float getRailMaxSpeed (World world, EntityMinecart cart, int y, int x, int z)
    {
        return 0.65f;
    }
}
