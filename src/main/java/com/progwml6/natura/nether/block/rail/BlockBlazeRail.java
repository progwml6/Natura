package com.progwml6.natura.nether.block.rail;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockRail;
import net.minecraft.block.SoundType;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBlazeRail extends BlockRail
{
    public BlockBlazeRail()
    {
        super();
        this.setHardness(0.7F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos)
    {
        return 0.65F;
    }
}
