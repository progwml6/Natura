package mods.natura.blocks.tech;

import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockRailDetector;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class BlazeRailDetector extends BlockRailDetector
{
    public BlazeRailDetector()
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
