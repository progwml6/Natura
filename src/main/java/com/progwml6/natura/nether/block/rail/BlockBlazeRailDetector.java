package com.progwml6.natura.nether.block.rail;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockRailDetector;
import net.minecraft.block.SoundType;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBlazeRailDetector extends BlockRailDetector
{
    public BlockBlazeRailDetector()
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
